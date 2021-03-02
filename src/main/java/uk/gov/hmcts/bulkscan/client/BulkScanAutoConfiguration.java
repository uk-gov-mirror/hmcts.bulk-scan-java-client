package uk.gov.hmcts.bulkscan.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.hmcts.bulkscan.client.api.BulkScanApiClient;
import uk.gov.hmcts.bulkscan.client.service.BulkScanService;

@Configuration
@ConditionalOnProperty(prefix = "bulk-scan", name = "url")
@EnableFeignClients(basePackages = "uk.gov.hmcts.bulkscan.client")
public class BulkScanAutoConfiguration {

    @Bean
    Decoder feignDecoder(ObjectMapper objectMapper) {
        return new JacksonDecoder(objectMapper);
    }

    @Bean
    public BulkScanService bulkScanService(BulkScanApiClient bulkScanApiClient) {
        return new BulkScanService(bulkScanApiClient);
    }
}
