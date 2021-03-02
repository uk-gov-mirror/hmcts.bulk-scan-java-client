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

import java.time.LocalDateTime;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

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

    @BeforeAll
    public void spinUp() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(6401));
        wireMockServer.start();
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
    public void getTeamEnvelopesSome() throws JsonProcessingException {
        stubTeamEnvelopes();
        Envelope[] result = bulkScanService.getTeamEnvelopes("s2sToken");
        Assertions.assertThat(result)
                .isInstanceOf(Envelope[].class)
                .hasSize(2)
                .extracting("dateProcessed").asList().first().isNull();
        for (int i = 0; i < 2; i++) {
            Assertions.assertThat(result[i].getId()).isNotNull();
            Assertions.assertThat(LocalDateTime.parse(result[i].getDateReceived()).compareTo(LocalDateTime.now()))
                    .isLessThanOrEqualTo(0);
            Assertions.assertThat(result[i].getStatus()).isEqualTo(Envelope.StatusEnum.PENDING);
            Assertions.assertThat(result[i].getIsLeased()).isFalse();
            Assertions.assertThat(result[i].getAuditTrail()).isNotNull();
        }        
    }

    private void stubTeamEnvelopes() throws JsonProcessingException {

        wireMockServer.stubFor(get(urlMatching(
                "/envelopes/" + this.teamName))
                .willReturn(WireMock.aResponse().withStatus(200).withBody("[\n"
                        + "                        {\n"
                        + "                            \"id\": \"" + UUID.randomUUID() + "\",\n"
                        + "                            \"dateReceived\": \"" + LocalDateTime.now() + "\",\n"
                        + "                            \"dateProcessed\": null,\n"
                        + "                            \"status\": \"" + Envelope.StatusEnum.PENDING + "\",\n"
                        + "                            \"isLeased\": \"false\",\n"
                        + "                            \"auditTrail\": \"https://someendpoint/\"\n"
                        + "                        },"
                        + "                        {\n"
                        + "                            \"id\": \"" + UUID.randomUUID() + "\",\n"
                        + "                            \"dateReceived\": \"" + LocalDateTime.now() + "\",\n"
                        + "                            \"dateProcessed\": null,\n"
                        + "                            \"status\": \"" + Envelope.StatusEnum.PENDING + "\",\n"
                        + "                            \"isLeased\": \"false\",\n"
                        + "                            \"auditTrail\": \"https://someendpoint/\"\n"
                        + "                        }"
                        + "]")));
    }
}
