/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hp.sla.analyser.model.util;

/**
 *
 * @author Benjamin Cisneros Barraza
 */
public enum OrganizationLevel1Name {

    HP_IT("hp it"),
    HPI_IT("hpi-it"),
    HPE_IT("hpe-it");
    private final String name;

    private OrganizationLevel1Name(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
