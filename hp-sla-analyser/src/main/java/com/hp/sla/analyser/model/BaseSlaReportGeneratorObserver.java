package com.hp.sla.analyser.model;

/**
 *
 * @author Benjamin Cisneros Barraza
 */
public abstract class BaseSlaReportGeneratorObserver implements SlaReportGeneratorObserver {

    private int incidentsTotal;

    @Override
    public void onReportGenerationError(Exception ex) {

    }

    @Override
    public int getTotal() {
        return incidentsTotal;
    }

    @Override
    public void onStartReportGeneration(SlaReportGenerator slaReportGenerator) {
    }

    @Override
    public void onFinalizeReportGeneration(SlaReportGenerator slaReportGenerator) {
    }

    @Override
    public void notifyProcessPhase(SlaReportGenerator aThis, String string) {
    }

    @Override
    public void setTotalIncidents(int size) {
        incidentsTotal = size;
    }

    @Override
    public void reportCurrentIncident(Incident incident, int i) {
    }

}
