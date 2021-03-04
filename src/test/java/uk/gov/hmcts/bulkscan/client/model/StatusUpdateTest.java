package uk.gov.hmcts.bulkscan.client.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatusUpdateTest {

    @Test
    void testStatusEmum() {
        StatusUpdate.StatusEnum se = StatusUpdate.StatusEnum.fromValue("pending");
        assertThat(se).isEqualTo(StatusUpdate.StatusEnum.PENDING);
        StatusUpdate.StatusEnum sep = StatusUpdate.StatusEnum.fromValue("processed");
        assertThat(sep).isEqualTo(StatusUpdate.StatusEnum.PROCESSED);
        StatusUpdate.StatusEnum see = StatusUpdate.StatusEnum.fromValue("errored");
        assertThat(see).isEqualTo(StatusUpdate.StatusEnum.ERRORED);
    }

    @Test
    void testStatusEnumEx() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            StatusUpdate.StatusEnum.fromValue("foobar");
        });
        String expectedMessage = "Unexpected value 'foobar'";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testToJson() throws JsonProcessingException {
        StatusUpdate su = new StatusUpdate();
        ObjectMapper objectMapper = new ObjectMapper();

        assertThat(objectMapper.writeValueAsString(su)).isEqualTo("{\"status\":null,\"reason\":null}");

        su.setReason("because");
        su.setStatus(StatusUpdate.StatusEnum.PROCESSED);
        assertThat(objectMapper.writeValueAsString(su)).isEqualTo(
                "{\"status\":\"processed\",\"reason\":\"because\"}");
    }

    @Test
    void testChainingSyntax() {
        StatusUpdate su = new StatusUpdate()
                .status(StatusUpdate.StatusEnum.PROCESSED)
                .reason("because");

        assertThat(su.toString()).isEqualTo("class StatusUpdate {\n" +
                "    status: processed\n" +
                "    reason: because\n" +
                "}");

        su.setReason(null);

        assertThat(su.toString()).isEqualTo("class StatusUpdate {\n" +
                "    status: processed\n" +
                "    reason: null\n" +
                "}");
    }

    @Test
    void testComparison() {
        StatusUpdate su = new StatusUpdate()
                .status(StatusUpdate.StatusEnum.PROCESSED)
                .reason("because");
        StatusUpdate su2 = new StatusUpdate()
                .status(StatusUpdate.StatusEnum.PROCESSED)
                .reason("because");
        StatusUpdate su3 = new StatusUpdate()
                .status(StatusUpdate.StatusEnum.PROCESSED)
                .reason("cos");

        assertThat(su).isEqualTo(su2);
        assertThat(su).isNotEqualTo(su3);
        assertThat(su).isNotEqualTo(null);

        assertThat(su.hashCode()).isEqualTo(su2.hashCode());
        assertThat(su.hashCode()).isNotEqualTo(su3.hashCode());
    }
}
