package uk.gov.hmcts.bulkscan.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.hmcts.bulkscan.client.api.BulkScanApiClient;
import uk.gov.hmcts.bulkscan.client.service.BulkScanService;

import static org.assertj.core.api.Assertions.assertThat;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = BulkScanAutoConfiguration.class,
    properties = {
        "bulk-scan.team-name=bar",
        "bulk-scan.url=localhost"
    }
)
public class AutoConfigurationTest {

    @Autowired
    private ApplicationContext context;

    @DisplayName("Should have Api configured")
    @Test
    public void haveApi() {
        assertThat(context.getBeanNamesForType(BulkScanApiClient.class)).hasSize(1);
        assertThat(context.getBeanNamesForType(BulkScanService.class)).hasSize(1);
    }
}
