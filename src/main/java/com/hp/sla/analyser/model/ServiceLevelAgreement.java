package com.hp.sla.analyser.model;

import static com.hp.sla.analyser.util.DateTimeUtil.EIGHT_HOURS;
import static com.hp.sla.analyser.util.DateTimeUtil.ONE_HUNDRED_TEN_HOURS;
import static com.hp.sla.analyser.util.DateTimeUtil.FIVE_TEEN_MINUTES;
import static com.hp.sla.analyser.util.DateTimeUtil.FOUR_DAYS;
import static com.hp.sla.analyser.util.DateTimeUtil.FOUR_HOURS;
import static com.hp.sla.analyser.util.DateTimeUtil.ONE_DAY;
import static com.hp.sla.analyser.util.DateTimeUtil.ONE_HOUR;
import static com.hp.sla.analyser.util.DateTimeUtil.SEVENTY_FIVE_PERCENT;
import static com.hp.sla.analyser.util.DateTimeUtil.SIX_DAYS;
import static com.hp.sla.analyser.util.DateTimeUtil.SIX_HOURS;
import static com.hp.sla.analyser.util.DateTimeUtil.THREE_DAYS;
import static com.hp.sla.analyser.util.DateTimeUtil.THREE_HOURS;

/**
 * Represents a Service Level Agreement record provided by HP depending on the
 * application and the priority of the ticket
 *
 * @author Benjamin Cisneros Barraza
 */
public enum ServiceLevelAgreement {

    // HP SLA's
    HP_IT_MISSION_CRITICAL_TOP(FIVE_TEEN_MINUTES, THREE_HOURS, THREE_HOURS, "HP-IT Mission Critical - Top"),
    HP_IT_MISSION_CRITICAL_HIGH(ONE_HOUR, SIX_HOURS, SIX_HOURS, "HP-IT Mission Critical - High"),
    HP_IT_MISSION_CRITICAL_MEDIUM(FOUR_HOURS, THREE_DAYS, THREE_DAYS * SEVENTY_FIVE_PERCENT,
	    "HP-IT Mission Critical - Medium"),
    HP_IT_MISSION_CRITICAL_LOW(EIGHT_HOURS, FOUR_DAYS, FOUR_DAYS * SEVENTY_FIVE_PERCENT,
	    "HP-IT Mission Critical - Low"),

    HP_IT_ENTITY_ESSENTIAL_TOP("HP-IT Entity Essential - Top"),
    HP_IT_ENTITY_ESSENTIAL_HIGH(ONE_HOUR, SIX_HOURS, SIX_HOURS, "HP-IT Entity Essential - High"),
    HP_IT_ENTITY_ESSENTIAL_MEDIUM(FOUR_HOURS, THREE_DAYS, THREE_DAYS * SEVENTY_FIVE_PERCENT,
	    "HP-IT Entity Essential - Medium"),
    HP_IT_ENTITY_ESSENTIAL_LOW(EIGHT_HOURS, FOUR_DAYS, FOUR_DAYS * SEVENTY_FIVE_PERCENT,
	    "HP-IT Entity Essential - Low"),

    HP_IT_NORMAL_TOP("HP-IT Normal - Top"),
    HP_IT_NORMAL_HIGH("HP-IT Normal - High"),
    HP_IT_NORMAL_MEDIUM(FOUR_HOURS, FOUR_DAYS, FOUR_DAYS * SEVENTY_FIVE_PERCENT, "HP-IT Normal - Medium"),
    HP_IT_NORMAL_LOW(EIGHT_HOURS, SIX_DAYS, SIX_DAYS * SEVENTY_FIVE_PERCENT, "HP-IT Normal - Low"),

    // HPI SLA's
    HPI_IT_MISSION_CRITICAL_TOP(FIVE_TEEN_MINUTES, EIGHT_HOURS, EIGHT_HOURS * SEVENTY_FIVE_PERCENT,
	    "HPI-IT Mission Critical - Top"),
    HPI_IT_MISSION_CRITICAL_HIGH(ONE_HOUR, ONE_DAY, ONE_DAY * SEVENTY_FIVE_PERCENT, "HPI-IT Mission Critical - High"),
    HPI_IT_MISSION_CRITICAL_MEDIUM(FOUR_HOURS, THREE_DAYS, THREE_DAYS * SEVENTY_FIVE_PERCENT,
	    "HPI-IT Mission Critical - Medium"),
    HPI_IT_MISSION_CRITICAL_LOW(EIGHT_HOURS, FOUR_DAYS, FOUR_DAYS * SEVENTY_FIVE_PERCENT,
	    "HPI-IT Mission Critical - Low"),

    HPI_IT_ENTITY_ESSENTIAL_TOP("HPI-IT Entity Essential - Top"),
    HPI_IT_ENTITY_ESSENTIAL_HIGH(ONE_HOUR, ONE_DAY, ONE_DAY * SEVENTY_FIVE_PERCENT, "HPI-IT Entity Essential - High"),
    HPI_IT_ENTITY_ESSENTIAL_MEDIUM(FOUR_HOURS, THREE_DAYS, THREE_DAYS * SEVENTY_FIVE_PERCENT,
	    "HPI-IT Entity Essential - Medium"),
    HPI_IT_ENTITY_ESSENTIAL_LOW(EIGHT_HOURS, FOUR_DAYS, FOUR_DAYS * SEVENTY_FIVE_PERCENT,
	    "HPI-IT Entity Essential - Low"),

    HPI_IT_NORMAL_TOP("HPI-IT Normal - Top"),
    HPI_IT_NORMAL_HIGH("HPI-IT Normal - High"),
    HPI_IT_NORMAL_MEDIUM(FOUR_HOURS, THREE_DAYS, THREE_DAYS * SEVENTY_FIVE_PERCENT, "HPI-IT Normal - Medium"),
    HPI_IT_NORMAL_LOW(EIGHT_HOURS, FOUR_DAYS, FOUR_DAYS * SEVENTY_FIVE_PERCENT, "HPI-IT Normal - Low"),

    // HPE SLA's
    HPE_IT_MISSION_CRITICAL_TOP(FIVE_TEEN_MINUTES, EIGHT_HOURS, EIGHT_HOURS, "HPE-IT Mission Critical - Top"),
    HPE_IT_MISSION_CRITICAL_HIGH(ONE_HOUR, ONE_DAY, ONE_DAY, "HPE-IT Mission Critical - High"),
    HPE_IT_MISSION_CRITICAL_MEDIUM(FOUR_HOURS, FOUR_DAYS, FOUR_DAYS * SEVENTY_FIVE_PERCENT,
	    "HPE-IT Mission Critical - Medium"),
    HPE_IT_MISSION_CRITICAL_LOW(EIGHT_HOURS, ONE_HUNDRED_TEN_HOURS, ONE_HUNDRED_TEN_HOURS * SEVENTY_FIVE_PERCENT,
	    "HPE-IT Mission Critical - Low"),

    HPE_IT_ENTITY_ESSENTIAL_TOP("HPE-IT Entity Essential - Top"),
    HPE_IT_ENTITY_ESSENTIAL_HIGH(ONE_HOUR, ONE_DAY, ONE_DAY, "HPE-IT Entity Essential - High"),
    HPE_IT_ENTITY_ESSENTIAL_MEDIUM(FOUR_HOURS, FOUR_DAYS, FOUR_DAYS * SEVENTY_FIVE_PERCENT,
	    "HPE-IT Entity Essential - Medium"),
    HPE_IT_ENTITY_ESSENTIAL_LOW(EIGHT_HOURS, ONE_HUNDRED_TEN_HOURS, ONE_HUNDRED_TEN_HOURS * SEVENTY_FIVE_PERCENT,
	    "HPE-IT Entity Essential - Low"),

    HPE_IT_NORMAL_TOP("HPE-IT Normal - Top"),
    HPE_IT_NORMAL_HIGH("HPE-IT Normal - High"),
    HPE_IT_NORMAL_MEDIUM(FOUR_HOURS, FOUR_DAYS, FOUR_DAYS * SEVENTY_FIVE_PERCENT, "HPE-IT Normal - Medium"),
    HPE_IT_NORMAL_LOW(EIGHT_HOURS, ONE_HUNDRED_TEN_HOURS, ONE_HUNDRED_TEN_HOURS * SEVENTY_FIVE_PERCENT, "HPE-IT Normal - Low"),;

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
