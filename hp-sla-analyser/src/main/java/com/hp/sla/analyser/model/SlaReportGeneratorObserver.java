package com.hp.sla.analyser.model;

/**
 *
 * @author cisnerob
 */
public interface SlaReportGeneratorObserver {

    public void onStartReportGeneration(SlaReportGenerator slaReportGenerator);

    public void onFinalizeReportGeneration(SlaReportGenerator slaReportGenerator);

    public void notifyProcessPhase(SlaReportGenerator aThis, String string);

    public void setTotalIncidents(int size);

    public void reportCurrentIncident(Incident incident, int i);
}
