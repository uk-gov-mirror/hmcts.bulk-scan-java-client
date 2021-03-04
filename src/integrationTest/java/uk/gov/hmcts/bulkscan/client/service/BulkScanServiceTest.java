package uk.gov.hmcts.bulkscan.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.hmcts.bulkscan.client.BulkScanAutoConfiguration;
import uk.gov.hmcts.bulkscan.client.model.Envelope;
import uk.gov.hmcts.bulkscan.client.model.Lease;
import uk.gov.hmcts.bulkscan.client.model.LeasedEnvelope;
import uk.gov.hmcts.bulkscan.client.model.StatusUpdate;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = {BulkScanAutoConfiguration.class},
        properties = {
            "bulk-scan.url=localhost:6401",
            "bulk-scan.team-name=foo"
        }
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BulkScanServiceTest {
    private static WireMockServer wireMockServer;

    @Autowired
    private BulkScanService bulkScanService;

    @Autowired
    private ObjectMapper mapper;

    @Value("${bulk-scan.team-name}")
    private String teamName;

    private SimpleDateFormat sdf;

    @BeforeAll
    public void spinUp() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(6401));
        wireMockServer.start();
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    }

    @BeforeEach
    public void beforeEach() throws JsonProcessingException {
        wireMockServer.resetAll();
    }

    @AfterAll
    public void shutDown() {
        wireMockServer.stop();
    }

    @Test
    public void getTeamEnvelopesEmpty() throws JsonProcessingException {
        wireMockServer.stubFor(get(urlMatching(
                "/envelopes/" + this.teamName))
                .willReturn(WireMock.aResponse().withStatus(200).withBody("[]")));

        Assertions.assertThat(bulkScanService.getTeamEnvelopes("s2sToken"))
                .isInstanceOf(Envelope[].class)
                .hasSize(0);
    }

    @Test
    public void getTeamEnvelopesSome() throws JsonProcessingException, ParseException {
        stubTeamEnvelopes();
        Envelope[] result = bulkScanService.getTeamEnvelopes("s2sToken");
        Assertions.assertThat(result)
                .isInstanceOf(Envelope[].class)
                .hasSize(2)
                .extracting("dateProcessed").asList().first().isNull();
        for (int i = 0; i < 2; i++) {
            Assertions.assertThat(result[i].getId()).isNotNull();
            Assertions.assertThat(result[i].getDateReceived().compareTo(new Date())).isLessThanOrEqualTo(0);
            Assertions.assertThat(result[i].getStatus()).isEqualTo(Envelope.StatusEnum.PENDING);
            Assertions.assertThat(result[i].getIsLeased()).isFalse();
            Assertions.assertThat(result[i].getAuditTrail()).isNotNull();
        }        
    }
    
    @Test
    public void getEnvelopeData() throws IOException, ParseException {
        UUID leaseId = UUID.randomUUID();
        stubTeamEnvelopes();
        Envelope e = bulkScanService.getTeamEnvelopes("s2sToken")[0];

        long time = System.currentTimeMillis();

        wireMockServer.stubFor(put(urlMatching(
                "/envelopes/" + this.teamName + "/envelope/" + e.getId() + "/lease/.+"))
                .willReturn(WireMock.aResponse().withStatus(201).withBody("{\n"
                       + "            \"id\": \"" + leaseId + "\",\n"
                       + "            \"expiresAt\": \"" + sdf.format(new Date((time + 3600 * 1000))) + "\",\n"
                       + "            \"blobSasUrl\": \"http://localhost:6401/mahblob?sas=withatoken\"\n"
                       + "        }")));

        wireMockServer.stubFor(get("/mahblob?sas=withatoken")
                .willReturn(aResponse().withBodyFile("testStreamingFile.txt")));

        InputStream stream = bulkScanService.getEnvelopeData(e, "s2sToken");
        byte[] streamContents = stream.readAllBytes();
        stream.close();
        Assertions.assertThat(streamContents).isEqualTo("Hello World".getBytes());
    }

    @Test
    public void setEnvelopeStatus() {
        LeasedEnvelope le = new LeasedEnvelope(new Lease());
        le.setIsLeased(true);
        le.setId(UUID.randomUUID());
        le.setDateReceived(new Date());
        le.setStatus(Envelope.StatusEnum.PENDING);

        long now = System.currentTimeMillis();

        wireMockServer.stubFor(patch(urlMatching("/envelopes/" + this.teamName + "/envelope/" + le.getId()))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withBody("{\n"
                                + "    \"id\": \"" + UUID.randomUUID() + "\",\n"
                                + "    \"dateReceived\": \"" + sdf.format(new Date(now - 3600 * 1000)) + "\",\n"
                                + "    \"dateProcessed\": \"" + sdf.format(new Date(now)) + "\",\n"
                                + "    \"status\": \"" + Envelope.StatusEnum.PROCESSED + "\",\n"
                                + "    \"isLeased\": \"false\",\n"
                                + "    \"auditTrail\": \"https://someendpoint/\"\n"
                                + "}")
                )
        );
        wireMockServer.stubFor(delete(
                urlEqualTo("/envelopes/" + this.teamName + "/envelope/" + le.getId() + "/lease/" + le.getLease().getId()))
                .willReturn(WireMock.aResponse().withStatus(204)));
        Envelope updatedEnvelope = bulkScanService.setEnvelopeStatus(le, StatusUpdate.StatusEnum.PROCESSED, "s2sToken");
        Assertions.assertThat(updatedEnvelope.getStatus()).isEqualTo(Envelope.StatusEnum.PROCESSED);
        Assertions.assertThat(updatedEnvelope.getIsLeased()).isFalse();
        Assertions.assertThat(updatedEnvelope.getDateProcessed()).isNotNull();
    }

    private void stubTeamEnvelopes() throws JsonProcessingException, ParseException {

        wireMockServer.stubFor(get(urlMatching(
                "/envelopes/" + this.teamName))
                .willReturn(WireMock.aResponse().withStatus(200).withBody("[\n"
                        + "                        {\n"
                        + "                            \"id\": \"" + UUID.randomUUID() + "\",\n"
                        + "                            \"dateReceived\": \"" + sdf.format(new Date(System.currentTimeMillis())) + "\",\n"
                        + "                            \"dateProcessed\": null,\n"
                        + "                            \"status\": \"" + Envelope.StatusEnum.PENDING + "\",\n"
                        + "                            \"isLeased\": \"false\",\n"
                        + "                            \"auditTrail\": \"https://someendpoint/\"\n"
                        + "                        },"
                        + "                        {\n"
                        + "                            \"id\": \"" + UUID.randomUUID() + "\",\n"
                        + "                            \"dateReceived\": \"" + sdf.format(new Date(System.currentTimeMillis())) + "\",\n"
                        + "                            \"dateProcessed\": null,\n"
                        + "                            \"status\": \"" + Envelope.StatusEnum.PENDING + "\",\n"
                        + "                            \"isLeased\": \"false\",\n"
                        + "                            \"auditTrail\": \"https://someendpoint/\"\n"
                        + "                        }"
                        + "]")));
    }
}
