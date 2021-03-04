package uk.gov.hmcts.bulkscan.client.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class LeasedEnvelopeTest {

    @Test
    void testGettingLease() {
        Lease l = new Lease();
        LeasedEnvelope le = new LeasedEnvelope(l);
        assertThat(le.getLease()).isEqualTo(l);
        assertThat(le).isInstanceOf(Envelope.class);
    }
}
