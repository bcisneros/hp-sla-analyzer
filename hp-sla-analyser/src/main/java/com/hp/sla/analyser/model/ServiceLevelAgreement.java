package com.hp.sla.analyser.model;

/**
 * Represents a Service Level Agreement record provided by HP depending on the
 * application and the priority of the ticket
 *
 * @author Benjamin Cisneros Barraza
 */
public class ServiceLevelAgreement {

    private final Float timeToOwn;
    private final Float timeToFix;
    private final Float burnedOut;

    public ServiceLevelAgreement(Float timeToOwn, Float timeToFix, Float burnedOut) {
        this.timeToOwn = timeToOwn;
        this.timeToFix = timeToFix;
        this.burnedOut = burnedOut;
    }

    public Float getTimeToOwn() {
        return timeToOwn;
    }

    public Float getTimeToFix() {
        return timeToFix;
    }

    public Float getBurnedOut() {
        return burnedOut;
    }

}
