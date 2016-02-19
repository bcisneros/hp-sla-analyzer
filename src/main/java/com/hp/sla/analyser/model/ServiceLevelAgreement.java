package com.hp.sla.analyser.model;

/**
 * Represents a Service Level Agreement record provided by HP depending on the
 * application and the priority of the ticket
 *
 * @author Benjamin Cisneros Barraza
 */
public enum ServiceLevelAgreement {

    //HP SLA's
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
    HP_IT_NORMAL_LOW(8F, 144F, 144F * 0.75F, "HP-IT Normal - Low"),
    //HPI SLA's
    //TODO: Change to real values
    HPI_IT_MISSION_CRITICAL_TOP(0.25F, 8F, 8F * 0.75F, "HPI-IT Mission Critical - Top"),
    HPI_IT_MISSION_CRITICAL_HIGH(1F, 24F, 24F * 0.75F, "HPI-IT Mission Critical - High"),
    HPI_IT_MISSION_CRITICAL_MEDIUM(4F, 72F, 72F * 0.75F, "HPI-IT Mission Critical - Medium"),
    HPI_IT_MISSION_CRITICAL_LOW(8F, 96F, 96F * 0.75F, "HPI-IT Mission Critical - Low"),
    HPI_IT_ENTITY_ESSENTIAL_TOP("HPI-IT Entity Essential - Top"),
    HPI_IT_ENTITY_ESSENTIAL_HIGH(1F, 24F, 24F * 0.75F, "HPI-IT Entity Essential - High"),
    HPI_IT_ENTITY_ESSENTIAL_MEDIUM(4F, 72F, 72F * 0.75F, "HPI-IT Entity Essential - Medium"),
    HPI_IT_ENTITY_ESSENTIAL_LOW(8F, 96F, 96F * 0.75F, "HPI-IT Entity Essential - Low"),
    HPI_IT_NORMAL_TOP("HPI-IT Normal - Top"),
    HPI_IT_NORMAL_HIGH("HPI-IT Normal - High"),
    HPI_IT_NORMAL_MEDIUM(4F, 72F, 72F * 0.75F, "HPI-IT Normal - Medium"),
    HPI_IT_NORMAL_LOW(8F, 96F, 96F * 0.75F, "HPI-IT Normal - Low"),
    //HPE SLA's
    HPE_IT_MISSION_CRITICAL_TOP(0.25F, 3F, 3F, "HPE-IT Mission Critical - Top"),
    HPE_IT_MISSION_CRITICAL_HIGH(1F, 6F, 6F, "HPE-IT Mission Critical - High"),
    HPE_IT_MISSION_CRITICAL_MEDIUM(4F, 72F, 72F * 0.75F, "HPE-IT Mission Critical - Medium"),
    HPE_IT_MISSION_CRITICAL_LOW(8F, 96F, 96F * 0.75F, "HPE-IT Mission Critical - Low"),
    HPE_IT_ENTITY_ESSENTIAL_TOP("HPE-IT Entity Essential - Top"),
    HPE_IT_ENTITY_ESSENTIAL_HIGH(1F, 6F, 6F, "HPE-IT Entity Essential - High"),
    HPE_IT_ENTITY_ESSENTIAL_MEDIUM(4F, 72F, 72F * 0.75F, "HPE-IT Entity Essential - Medium"),
    HPE_IT_ENTITY_ESSENTIAL_LOW(8F, 96F, 96F * 0.75F, "HPE-IT Entity Essential - Low"),
    HPE_IT_NORMAL_TOP("HPE-IT Normal - Top"),
    HPE_IT_NORMAL_HIGH("HPE-IT Normal - High"),
    HPE_IT_NORMAL_MEDIUM(4F, 96F, 96F * 0.75F, "HPE-IT Normal - Medium"),
    HPE_IT_NORMAL_LOW(8F, 144F, 144F * 0.75F, "HPE-IT Normal - Low"),;

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
