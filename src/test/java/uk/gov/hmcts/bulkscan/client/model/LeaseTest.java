package uk.gov.hmcts.bulkscan.client.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class LeaseTest {

    @Test
    void testToJson() throws JsonProcessingException {
        Lease l = new Lease();
        ObjectMapper objectMapper = new ObjectMapper();

        assertThat(objectMapper.writeValueAsString(l)).isEqualTo("{\"id\":\"" + l.getId() + "\",\"expiresAt\":null,\"blobSasUrl\":null}");

        UUID id = UUID.randomUUID();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date now = new Date();

        l.setBlobSasUrl("https://someurl");
        l.setExpiresAt(now);
        l.setId(id);
        assertThat(objectMapper.writeValueAsString(l)).isEqualTo(
                "{\"id\":\"" + id + "\"," +
                "\"expiresAt\":\"" + sdf.format(now) + "\"," +
                "\"blobSasUrl\":\"https://someurl\"}");
    }

    @Test
    void testChainingSyntax() {
        Date now = new Date();
        UUID id = UUID.randomUUID();
        Lease l = new Lease()
                .blobSasUrl("https://someurl")
                .expiresAt(now)
                .id(id);

        assertThat(l.toString()).isEqualTo("class Lease {\n" +
                "    id: " + id + "\n" +
                "    expiresAt: " + now + "\n" +
                "    blobSasUrl: https://someurl\n" +
                "}");

        l.setBlobSasUrl(null);
        assertThat(l.toString()).isEqualTo("class Lease {\n" +
                "    id: " + id + "\n" +
                "    expiresAt: " + now + "\n" +
                "    blobSasUrl: null\n" +
                "}");
    }

    @Test
    void testComparison() {
        Date now = new Date();
        UUID id = UUID.randomUUID();
        Lease l = new Lease(id)
                .blobSasUrl("https://someurl")
                .expiresAt(now);
        Lease l2 = new Lease(id)
                .blobSasUrl("https://someurl")
                .expiresAt(now);
        Lease l3 = new Lease(id)
                .blobSasUrl("https://someurl")
                .expiresAt(new Date());

        assertThat(l).isEqualTo(l2);
        assertThat(l).isNotEqualTo(l3);
        assertThat(l).isNotEqualTo(null);

        assertThat(l.hashCode()).isEqualTo(l2.hashCode());
        assertThat(l.hashCode()).isNotEqualTo(l3.hashCode());
    }
}
