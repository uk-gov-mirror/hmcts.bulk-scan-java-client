package uk.gov.hmcts.bulkscan.client.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.jackson.JacksonEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnvelopeTest {

    @Test
    void testStatusEmum() {
        Envelope.StatusEnum se = Envelope.StatusEnum.fromValue("pending");
        assertThat(se).isEqualTo(Envelope.StatusEnum.PENDING);
        Envelope.StatusEnum sep = Envelope.StatusEnum.fromValue("processed");
        assertThat(sep).isEqualTo(Envelope.StatusEnum.PROCESSED);
        Envelope.StatusEnum see = Envelope.StatusEnum.fromValue("errored");
        assertThat(see).isEqualTo(Envelope.StatusEnum.ERRORED);
    }

    @Test
    void testStatusEnumEx() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Envelope.StatusEnum.fromValue("foobar");
        });
        String expectedMessage = "Unexpected value 'foobar'";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testToJson() throws JsonProcessingException {
        Envelope e = new Envelope();
        ObjectMapper objectMapper = new ObjectMapper();

        assertThat(objectMapper.writeValueAsString(e)).isEqualTo("{\"id\":null,\"dateReceived\":null,\"dateProcessed\":null,\"status\":null,\"isLeased\":null,\"auditTrail\":null}");

        UUID id = UUID.randomUUID();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date now = new Date();

        e.setAuditTrail("https://someurl");
        e.setDateProcessed(now);
        e.setDateReceived(now);
        e.setId(id);
        e.setIsLeased(false);
        e.setStatus(Envelope.StatusEnum.PROCESSED);
        assertThat(objectMapper.writeValueAsString(e)).isEqualTo(
                "{\"id\":\"" + id + "\"," +
                "\"dateReceived\":\"" + sdf.format(now) + "\"," +
                "\"dateProcessed\":\"" + sdf.format(now) + "\"," +
                "\"status\":\"processed\"," +
                "\"isLeased\":false," +
                "\"auditTrail\":\"https://someurl\"}");
    }

    @Test
    void testChainingSyntax() {
        Date now = new Date();
        UUID id = UUID.randomUUID();
        Envelope e = new Envelope()
                .auditTrail("https://someurl")
                .dateProcessed(now)
                .dateReceived(now)
                .id(id)
                .isLeased(false)
                .status(Envelope.StatusEnum.ERRORED);

        assertThat(e.toString()).isEqualTo("class Envelope {\n" +
                "    id: " + id + "\n" +
                "    dateReceived: " + now + "\n" +
                "    dateProcessed: " + now + "\n" +
                "    status: errored\n" +
                "    isLeased: false\n" +
                "    auditTrail: https://someurl\n" +
                "}");

        e.setStatus(null);
        assertThat(e.toString()).isEqualTo("class Envelope {\n" +
                "    id: " + id + "\n" +
                "    dateReceived: " + now + "\n" +
                "    dateProcessed: " + now + "\n" +
                "    status: null\n" +
                "    isLeased: false\n" +
                "    auditTrail: https://someurl\n" +
                "}");
    }

    @Test
    void testComparison() {
        Date now = new Date();
        UUID id = UUID.randomUUID();
        Envelope e = new Envelope()
                .auditTrail("https://someurl")
                .dateProcessed(now)
                .dateReceived(now)
                .id(id)
                .isLeased(false)
                .status(Envelope.StatusEnum.ERRORED);
        Envelope e2 = new Envelope()
                .auditTrail("https://someurl")
                .dateProcessed(now)
                .dateReceived(now)
                .id(id)
                .isLeased(false)
                .status(Envelope.StatusEnum.ERRORED);
        Envelope e3 = new Envelope()
                .auditTrail("https://someurl")
                .dateProcessed(new Date())
                .dateReceived(now)
                .id(id)
                .isLeased(false)
                .status(Envelope.StatusEnum.ERRORED);

        assertThat(e).isEqualTo(e2);
        assertThat(e).isNotEqualTo(e3);
        assertThat(e).isNotEqualTo(null);

        assertThat(e.hashCode()).isEqualTo(e2.hashCode());
        assertThat(e.hashCode()).isNotEqualTo(e3.hashCode());
    }
}
