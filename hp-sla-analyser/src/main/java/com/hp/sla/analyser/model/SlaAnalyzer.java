package com.hp.sla.analyser.model;

import java.util.List;

/**
 * Determines if an Incident have compliance with the SLA defined by HP
 * @author Benjamin Cisneros Barraza
 */
public class SlaAnalyzer {
    /**
     * For each incident provided determines if it is in compliance with the SLA
     * @param incidents The list of <code>Incident</code> objects that are in the
     * input report
     * @return A <code>List</code> object of <code>ReportDetail</code>
     */
    public List<ReportDetail> analyze(List<Incident> incidents) {
        return null;
    }
}
