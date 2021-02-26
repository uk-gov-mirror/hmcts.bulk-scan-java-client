package uk.gov.hmcts.bulkscan.client.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.openapitools.configuration.ClientConfiguration;

@FeignClient(name="${bulkScan.name:bulkScan}", url="${bulkScan.url:http://localhost}", configuration = ClientConfiguration.class)
public interface BulkScanApiClient extends BulkScanApi {
}
