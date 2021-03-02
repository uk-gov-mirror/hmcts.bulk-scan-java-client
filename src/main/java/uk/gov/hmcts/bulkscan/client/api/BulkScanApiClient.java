package uk.gov.hmcts.bulkscan.client.api;

import org.springframework.cloud.openfeign.FeignClient;
import uk.gov.hmcts.bulkscan.client.BulkScanAutoConfiguration;

@FeignClient(name="bulk-scan-api", url="${bulk-scan.url:http://localhost}", configuration = BulkScanAutoConfiguration.class)
public interface BulkScanApiClient extends BulkScanApi {
}
