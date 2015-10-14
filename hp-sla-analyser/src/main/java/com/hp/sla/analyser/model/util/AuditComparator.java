/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hp.sla.analyser.model.util;

import com.hp.sla.analyser.model.Audit;
import java.util.Comparator;

/**
 *
 * @author ramirmal
 */
public class AuditComparator implements Comparator<Audit>{
    @Override
    public int compare(Audit o1, Audit o2) {
        return o1.getIncidentID().compareTo(o2.getIncidentID());
    }
}
