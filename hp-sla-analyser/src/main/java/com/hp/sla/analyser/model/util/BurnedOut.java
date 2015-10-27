/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hp.sla.analyser.model.util;

/**
 *
 * @author ramirmal
 */
public enum BurnedOut {

    COMPLIANCE("yes"),
    NON_COMPLIANCE("no"),
    UNDETERMINED("undetermined");

    private String name;

    BurnedOut(String name) {
        this.name = name;
    }
   
    public String getName(){
        return this.name;
    }
    

}
