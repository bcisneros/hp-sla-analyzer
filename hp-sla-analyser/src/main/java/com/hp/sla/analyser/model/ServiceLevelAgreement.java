package com.hp.sla.analyser.model;

/**
 * Represents a Service Level Agreement record provided by HP depending on the
 * application and the priority of the ticket
 *
 * @author Benjamin Cisneros Barraza
 */
public class ServiceLevelAgreement {

    public static final ServiceLevelAgreement HP_IT_MISSION_CRITICAL_TOP = new ServiceLevelAgreement(0.25F, 3F, 3F, "HP-IT Mission Critical - Top");
    public static final ServiceLevelAgreement HP_IT_MISSION_CRITICAL_HIGH = new ServiceLevelAgreement(1F, 6F, 6F, "HP-IT Mission Critical - High");
    public static final ServiceLevelAgreement HP_IT_MISSION_CRITICAL_MEDIUM = new ServiceLevelAgreement(4F, 72F, 72F * 0.75F, "HP-IT Mission Critical - Medium");
    public static final ServiceLevelAgreement HP_IT_MISSION_CRITICAL_LOW = new ServiceLevelAgreement(8F, 96F, 96F * 0.75F, "HP-IT Mission Critical - Low");
    public static final ServiceLevelAgreement HP_IT_ENTITY_ESSENTIAL_TOP = null;
    public static final ServiceLevelAgreement HP_IT_ENTITY_ESSENTIAL_HIGH = new ServiceLevelAgreement(1F, 6F, 6F, "HP-IT Entity Essential - High");
    public static final ServiceLevelAgreement HP_IT_ENTITY_ESSENTIAL_MEDIUM = new ServiceLevelAgreement(4F, 72F, 72F * 0.75F, "HP-IT Entity Essential - Medium");
    public static final ServiceLevelAgreement HP_IT_ENTITY_ESSENTIAL_LOW = new ServiceLevelAgreement(8F, 96F, 96F * 0.75F, "HP-IT Entity Essential - Low");
    public static final ServiceLevelAgreement HP_IT_NORMAL_TOP = null;
    public static final ServiceLevelAgreement HP_IT_NORMAL_HIGH = null;
    public static final ServiceLevelAgreement HP_IT_NORMAL_MEDIUM = new ServiceLevelAgreement(4F, 96F, 96F * 0.75F, "HP-IT Normal - Medium");
    public static final ServiceLevelAgreement HP_IT_NORMAL_LOW = new ServiceLevelAgreement(8F, 144F, 144F * 0.75F, "HP-IT Normal - Medium");
    private final Float timeToOwn;
    private final Float timeToFix;
    private final Float burnedOut;
    private final String name;

    public ServiceLevelAgreement(Float timeToOwn, Float timeToFix, Float burnedOut, String name) {
        this.timeToOwn = timeToOwn;
        this.timeToFix = timeToFix;
        this.burnedOut = burnedOut;
        this.name = name;
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

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ServiceLevelAgreement: " + getName();
    }
}
