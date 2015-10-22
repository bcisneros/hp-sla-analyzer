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
    public static final ServiceLevelAgreement HP_IT_MISSION_CRITICAL_TOP = new ServiceLevelAgreement(0.25F, 3F, 3F);
    public static final ServiceLevelAgreement HP_IT_MISSION_CRITICAL_HIGH = new ServiceLevelAgreement(1F, 6F, 6F);
    public static final ServiceLevelAgreement HP_IT_MISSION_CRITICAL_MEDIUM = new ServiceLevelAgreement(4F, 72F, 72F * 0.75F);
    public static final ServiceLevelAgreement HP_IT_MISSION_CRITICAL_LOW = new ServiceLevelAgreement(8F, 96F, 96F * 0.75F);
    public static final ServiceLevelAgreement HP_IT_ENTITY_ESSENTIAL_TOP = null;
    public static final ServiceLevelAgreement HP_IT_ENTITY_ESSENTIAL_HIGH = new ServiceLevelAgreement(1F, 6F, 6F);
    public static final ServiceLevelAgreement HP_IT_ENTITY_ESSENTIAL_MEDIUM = new ServiceLevelAgreement(4F, 72F, 72F * 0.75F);
    public static final ServiceLevelAgreement HP_IT_ENTITY_ESSENTIAL_LOW = new ServiceLevelAgreement(8F, 96F, 96F * 0.75F);
    public static final ServiceLevelAgreement HP_IT_NORMAL_TOP = null;
    public static final ServiceLevelAgreement HP_IT_NORMAL_HIGH = null;
    public static final ServiceLevelAgreement HP_IT_NORMAL_MEDIUM = new ServiceLevelAgreement(4F, 96F, 96F * 0.75F);
    public static final ServiceLevelAgreement HP_IT_NORMAL_LOW = new ServiceLevelAgreement(8F, 114F, 114F * 0.75F);

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

    @Override
    public String toString() {
        return "ServiceLevelAgreement{" + "timeToOwn=" + timeToOwn + ", timeToFix=" + timeToFix + ", burnedOut=" + burnedOut + '}';
    }

}
