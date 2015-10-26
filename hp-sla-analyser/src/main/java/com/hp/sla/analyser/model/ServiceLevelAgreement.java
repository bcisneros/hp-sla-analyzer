package com.hp.sla.analyser.model;

/**
 * Represents a Service Level Agreement record provided by HP depending on the
 * application and the priority of the ticket
 *
 * @author Benjamin Cisneros Barraza
 */
public enum ServiceLevelAgreement {

    HP_IT_MISSION_CRITICAL_TOP(0.25F, 3F, 3F, "HP-IT Mission Critical - Top"),
    HP_IT_MISSION_CRITICAL_HIGH(1F, 6F, 6F, "HP-IT Mission Critical - High"),
    HP_IT_MISSION_CRITICAL_MEDIUM(4F, 72F, 72F * 0.75F, "HP-IT Mission Critical - Medium"),
    HP_IT_MISSION_CRITICAL_LOW(8F, 96F, 96F * 0.75F, "HP-IT Mission Critical - Low"),
    HP_IT_ENTITY_ESSENTIAL_TOP("HP-IT Entity Essential - Top"),
    HP_IT_ENTITY_ESSENTIAL_HIGH(1F, 6F, 6F, "HP-IT Entity Essential - High"),
    HP_IT_ENTITY_ESSENTIAL_MEDIUM(4F, 72F, 72F * 0.75F, "HP-IT Entity Essential - Medium"),
    HP_IT_ENTITY_ESSENTIAL_LOW(8F, 96F, 96F * 0.75F, "HP-IT Entity Essential - Low"),
    HP_IT_NORMAL_TOP("HP-IT Normal - Top"),
    HP_IT_NORMAL_HIGH("HP-IT Normal - High"),
    HP_IT_NORMAL_MEDIUM(4F, 96F, 96F * 0.75F, "HP-IT Normal - Medium"),
    HP_IT_NORMAL_LOW(8F, 144F, 144F * 0.75F, "HP-IT Normal - Medium");

    private final Float timeToOwn;
    private final Float timeToFix;
    private final Float burnedOut;
    private final String name;

    ServiceLevelAgreement(Float timeToOwn, Float timeToFix, Float burnedOut, String name) {
        this.timeToOwn = timeToOwn;
        this.timeToFix = timeToFix;
        this.burnedOut = burnedOut;
        this.name = name;
    }

    ServiceLevelAgreement(String name) {
        this.timeToOwn = null;
        this.timeToFix = null;
        this.burnedOut = null;
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
