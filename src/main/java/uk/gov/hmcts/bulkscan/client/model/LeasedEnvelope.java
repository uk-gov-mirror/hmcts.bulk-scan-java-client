package uk.gov.hmcts.bulkscan.client.model;

public class LeasedEnvelope extends Envelope {

    private final Lease lease;

    public LeasedEnvelope(Lease lease) {
        this.lease = lease;
        this.setIsLeased(true);
    }

    public Lease getLease() {
        return lease;
    }
}
