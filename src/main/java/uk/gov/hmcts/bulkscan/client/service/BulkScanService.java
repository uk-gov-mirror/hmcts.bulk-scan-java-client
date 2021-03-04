package uk.gov.hmcts.bulkscan.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.bulkscan.client.api.BulkScanApi;
import uk.gov.hmcts.bulkscan.client.model.Envelope;
import uk.gov.hmcts.bulkscan.client.model.Lease;
import uk.gov.hmcts.bulkscan.client.model.LeasedEnvelope;
import uk.gov.hmcts.bulkscan.client.model.StatusUpdate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class BulkScanService {
    private static final Logger logger = LoggerFactory.getLogger(BulkScanService.class);

    private final BulkScanApi bulkScanApiClient;

    @Value("${bulk-scan.team-name}")
    private String teamName;

    public BulkScanService(BulkScanApi bulkScanApiClient) {
        this.bulkScanApiClient = bulkScanApiClient;
    }

    public Envelope[] getTeamEnvelopes(String serviceAuthHeader) {
        return this.bulkScanApiClient.getTeamEnvelopes(this.teamName, serviceAuthHeader);
    }

    public InputStream getEnvelopeData(Envelope envelope, String serviceAuthHeader) throws IOException {
        Lease lease = new Lease();
        Lease responseLease = this.bulkScanApiClient.acquireEnvelopeLease(
                this.teamName,
                envelope.getId(),
                lease.getId(),
                serviceAuthHeader,
                lease
        );

        // get stream from blob store
//        String url = "https://bulkscansandbox.blob.core.windows.net/jasontest/test.txt?sp=r&st=2021-03-01T09:54:27Z&se=2021-03-01T17:54:27Z&spr=https&sv=2020-02-10&sr=b&sig=HSdFQZ8yWG1r5KwOsQWzUQXBI38o9QKWhHDM9%2B4yies%3D";
        return new URL(responseLease.getBlobSasUrl()).openStream();
    }

    public void setEnvelopeStatus(LeasedEnvelope leaseEnvelope, StatusUpdate.StatusEnum status, String serviceAuthHeader) {
        this.setEnvelopeStatus(leaseEnvelope, status, "", serviceAuthHeader);
    }

    public void setEnvelopeStatus(LeasedEnvelope leasedEnvelope, StatusUpdate.StatusEnum status, String statusReason, String serviceAuthHeader) {
        StatusUpdate statusUpdate = new StatusUpdate();
        statusUpdate.setStatus(status);
        this.bulkScanApiClient.setEnvelopeStatus(this.teamName, leasedEnvelope.getId(), leasedEnvelope.getLease().getId(), serviceAuthHeader, statusUpdate);
        this.bulkScanApiClient.breakEnvelopeLease(this.teamName, leasedEnvelope.getId(), leasedEnvelope.getLease().getId(), serviceAuthHeader);
    }
}
