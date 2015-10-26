package com.hp.sla.analyser.model.util;

/**
 * The kinds of criticality of a ticket
 *
 * @author Mallinali Ramirez Corona
 */
public enum Criticality {

    MISSION_CRITICAL("Mission Critical"),
    ENTITY_ESSENTIAL("Entity Essential"),
    NORMAL("Normal");

    private final String name;   // The string that represents the criticality

    Criticality(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
