package com.hp.sla.analyser.model;

/**
 *
 * @author ramirmal
 */
public class ReportDetail {

    private Incident incident;
    private boolean complianceWithSLA;

    public Incident getIncident() {
        return incident;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
    }

    public String getIncidentIdentifier() {
        return incident.getId();
    }

    public boolean isComplianceWithSLA() {
        return complianceWithSLA;
    }

    public void setComplianceWithSLA(boolean complianceWithSLA) {
        this.complianceWithSLA = complianceWithSLA;
    }
}
