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
        return new URL(responseLease.getBlobSasUrl()).openStream();
    }

    public Envelope setEnvelopeStatus(LeasedEnvelope leaseEnvelope, StatusUpdate.StatusEnum status, String serviceAuthHeader) {
        return this.setEnvelopeStatus(leaseEnvelope, status, "", serviceAuthHeader);
    }

    public Envelope setEnvelopeStatus(LeasedEnvelope leasedEnvelope, StatusUpdate.StatusEnum status, String statusReason, String serviceAuthHeader) {
        StatusUpdate statusUpdate = new StatusUpdate();
        statusUpdate.setStatus(status);
        Envelope updatedEnvelope = this.bulkScanApiClient.setEnvelopeStatus(
                this.teamName,
                leasedEnvelope.getId(),
                leasedEnvelope.getLease().getId(),
                serviceAuthHeader,
                statusUpdate);
        this.bulkScanApiClient.breakEnvelopeLease(
                this.teamName,
                leasedEnvelope.getId(),
                leasedEnvelope.getLease().getId(),
                serviceAuthHeader);

        return updatedEnvelope;
    }
}
