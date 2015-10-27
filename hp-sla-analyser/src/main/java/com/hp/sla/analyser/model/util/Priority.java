/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hp.sla.analyser.model.util;

/**
 * The kinds of priority of a ticket
 *
 * @author Mallinali Ramirez Corona
 */
public enum Priority {

    TOP("top"),
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low");

    private final String name;   // The string that represents the criticality

    Priority(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
