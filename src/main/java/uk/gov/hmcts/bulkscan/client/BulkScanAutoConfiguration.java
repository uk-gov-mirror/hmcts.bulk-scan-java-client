package uk.gov.hmcts.bulkscan.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
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
        return new JacksonDecoder(setConfigureObjectMapper(objectMapper));
    }

    @Bean
    Encoder feignEncoder(ObjectMapper objectMapper) {
        return new JacksonEncoder(setConfigureObjectMapper(objectMapper));
    }

    @Bean
    public BulkScanService bulkScanService(BulkScanApiClient bulkScanApiClient) {
        return new BulkScanService(bulkScanApiClient);
    }

    private ObjectMapper setConfigureObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }
}
