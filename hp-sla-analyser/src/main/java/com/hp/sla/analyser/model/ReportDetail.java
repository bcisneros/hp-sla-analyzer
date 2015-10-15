package com.hp.sla.analyser.model;

/**
 *
 * @author ramirmal
 */
public class ReportDetail {

    private Incident incident;
    private boolean compliantWithSLA;
    private boolean burnedOut;

    public Incident getIncident() {
        return incident;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
    }

    public String getIncidentIdentifier() {
        return incident.getId();
    }

    public boolean isCompliantWithSLA() {
        return compliantWithSLA;
    }

    public void setCompliantWithSLA(boolean compliantWithSLA) {
        this.compliantWithSLA = compliantWithSLA;
    }

    public boolean isBurnedOut() {
        return burnedOut;
    }

    public void setBurnedOut(boolean burnedOut) {
        this.burnedOut = burnedOut;
    }
}
