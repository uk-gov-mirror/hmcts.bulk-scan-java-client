package uk.gov.hmcts.bulkscan.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.bulkscan.client.api.BulkScanApiClient;
import uk.gov.hmcts.bulkscan.client.model.Envelope;
import uk.gov.hmcts.bulkscan.client.model.Lease;
import uk.gov.hmcts.bulkscan.client.model.LeasedEnvelope;
import uk.gov.hmcts.bulkscan.client.model.StatusUpdate;

import java.util.List;

@Service
public class BulkScanService {
    private static final Logger logger = LoggerFactory.getLogger(BulkScanService.class);

    private final BulkScanApiClient bulkScanApiClient;
    private final String teamName;

    public BulkScanService(BulkScanApiClient bulkScanApiClient, String teamName) {
        this.bulkScanApiClient = bulkScanApiClient;
        this.teamName = teamName;
    }

    public ResponseEntity<List<Envelope>> getTeamEnvelopes(String serviceAuthHeader) {
        return this.bulkScanApiClient.getTeamEnvelopes(this.teamName, serviceAuthHeader);
    }

    public void getEnvelopeData(Envelope envelope, String serviceAuthHeader) {
        Lease lease = new Lease();
        ResponseEntity<Void> response = this.bulkScanApiClient.aquireEnvelopeLease(
                this.teamName,
                envelope.getId(),
                lease.getId(),
                serviceAuthHeader,
                lease
        );

        // get stream from blob store

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
