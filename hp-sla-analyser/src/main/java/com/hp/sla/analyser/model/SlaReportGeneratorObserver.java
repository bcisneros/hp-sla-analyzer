package com.hp.sla.analyser.model;

/**
 * Defines the methods to report the status of the SLA Report Generation process
 *
 * @author cisnerob
 */
public interface SlaReportGeneratorObserver {

    /**
     * Report when the process starts
     *
     * @param slaReportGenerator The instance of SlaReportGenerated
     */
    public void onStartReportGeneration(SlaReportGenerator slaReportGenerator);

    /**
     * Reports when the process finalizes
     *
     * @param slaReportGenerator The instance of SlaReportGenerated
     */
    public void onFinalizeReportGeneration(SlaReportGenerator slaReportGenerator);

    /**
     * Notify middle processes or status of some phases
     *
     * @param slaReportGenerator The instance of SlaReportGenerated
     * @param string The status phase message
     */
    public void notifyProcessPhase(SlaReportGenerator slaReportGenerator, String string);

    /**
     * Sets the total of incidents to report (process)
     *
     * @param size The size of elements
     */
    public void setTotalIncidents(int size);

    /**
     * Notify current incident analysis process
     *
     * @param incident The current analyzed incident
     * @param counter The current counter (index)
     */
    public void reportCurrentIncident(Incident incident, int counter);

    /**
     * Get the total number of incidents processed
     *
     * @return The number of incidents
     */
    public int getTotal();

    /**
     * Report an exception during the Report Generation Process
     *
     * @param ex The thrown exception
     */
    public void onReportGenerationError(Exception ex);
}
