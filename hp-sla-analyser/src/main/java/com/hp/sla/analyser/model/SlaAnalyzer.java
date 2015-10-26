package com.hp.sla.analyser.model;

import com.hp.sla.analyser.model.util.Criticality;
import com.hp.sla.analyser.model.util.Priority;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.log4j.Logger;

/**
 * Determines if an Incident have compliance with the SLA defined by HP
 *
 * @author Benjamin Cisneros Barraza
 */
public class SlaAnalyzer {

    private static final Logger logger = Logger.getLogger(SlaAnalyzer.class);
    private static final ServiceLevelAgreement[][] serviceLevelAgreements
            = new ServiceLevelAgreement[][]{
                new ServiceLevelAgreement[]{
                    ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_TOP,
                    ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_HIGH,
                    ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_MEDIUM,
                    ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_LOW},
                new ServiceLevelAgreement[]{
                    ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_TOP,
                    ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_HIGH,
                    ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_MEDIUM,
                    ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_LOW},
                new ServiceLevelAgreement[]{
                    ServiceLevelAgreement.HP_IT_NORMAL_TOP,
                    ServiceLevelAgreement.HP_IT_NORMAL_HIGH,
                    ServiceLevelAgreement.HP_IT_NORMAL_MEDIUM,
                    ServiceLevelAgreement.HP_IT_NORMAL_LOW}};

    /**
     * For each incident provided determines if it is in compliance with the SLA
     *
     * @param incidents The list of <code>Incident</code> objects that are in
     * the input report
     * @param observer
     * @return A <code>List</code> object of <code>ReportDetail</code>
     */
    public List<ReportDetail> analyze(List<Incident> incidents, SlaReportGeneratorObserver observer) {
        if (observer == null) {
            observer = new DefaultReportGeneratorObserver();
        }
        if (incidents == null || incidents.isEmpty()) {
            throw new IllegalArgumentException("The list of incidents cannot be null or empty");
        }

        observer.setTotalIncidents(incidents.size());
        List<ReportDetail> details = new ArrayList<>();
        int incidentCount = 1;
        for (Incident incident : incidents) {
            try {
                observer.reportCurrentIncident(incident, incidentCount++);
                details.add(analizeIncident(incident));
            } catch (SlaAnalysisException ex) {
                ReportDetail detailError = new ReportDetail();
                detailError.setIncident(incident);
                detailError.setDetailException(ex);
                details.add(detailError);
                logger.error("Error during the SLA Analysis of incident " + incident, ex);
            }
        }
        return details;
    }

    //TODO: Change to read from file
    protected List<String> getAssignmentGroupsListToAnalize() {
        List<String> assignmentGroups = new ArrayList<>();
        assignmentGroups.add("W-INCLV4-FAIT-AP-AR");
        assignmentGroups.add("W-INCLV4-FAIT-LEGAL");
        assignmentGroups.add("W-INCLV4-FAIT-TAX");
        assignmentGroups.add("W-INCLV4-FAIT-MASTERDATA");
        assignmentGroups.add("W-INCLV4-FAIT-INTERCOMPANY");
        assignmentGroups.add("W-INCLV4-FAIT-CREDITCOLLECTIONS-EEM");
        assignmentGroups.add("W-INCLV4-FAIT-FP");
        assignmentGroups.add("W-INCLV4-FAIT-CTE");
        assignmentGroups.add("W-INCLV4-FAIT-INTERNALAUDIT");
        assignmentGroups.add("W-INCLV4-FAIT-SAP-GL-FA");
        assignmentGroups.add("W-INCLV4-FAIT-CFMS");
        assignmentGroups.add("W-INCLV4-FAIT-CONSOLIDATION");
        assignmentGroups.add("W-INCLV4-FAIT-GL");
        return assignmentGroups;
    }

    protected ReportDetail analizeIncident(Incident incident) throws SlaAnalysisException {
        logger.info("Analyzing the incident " + incident.getId());
        incident.searchAndSetLastAssignmentGroupAudit(getAssignmentGroupsListToAnalize());

        if (incident.getLastAssignmentGroupAudit() == null) {
            String errorMessage = "It was not encountered the last Assignment Group Audit";
            logger.error(errorMessage);
            throw new SlaAnalysisException(errorMessage);
        }
        ServiceLevelAgreement serviceLevelAgreement = getServiceLevelAgreementByIncident(incident);
        if (serviceLevelAgreement.getTimeToOwn() == null) {
            throw new SlaAnalysisException("No Service Level Agreement was found");
        }

        Timestamp incidentBurnedOutDate = incident.calculateBurnedOutDate(serviceLevelAgreement);
        Timestamp incidentTimeToFixDeadline = incident.calculateTimeToFixDeadLine(serviceLevelAgreement);

        boolean isBurnedOut = incidentBurnedOutDate.compareTo(incident.getLastAssignmentGroupAudit().getSystemModifiedTime()) < 0;
        boolean isCompliantWithSLA = incidentTimeToFixDeadline.compareTo(incident.getCloseTimestamp() != null ? incident.getCloseTimestamp() : new Date()) > 0;
        ReportDetail detail = new ReportDetail();
        detail.setIncident(incident);
        detail.setCompliantWithSLA(isCompliantWithSLA);
        detail.setBurnedOut(isBurnedOut);
        return detail;
    }

    protected static ServiceLevelAgreement getServiceLevelAgreementByIncident(Incident incident) throws SlaAnalysisException {
        String criticality = incident.getCriticalityDescription();

        logger.debug("Criticality: " + criticality);
        Integer criticalityIndex;
        try {
            criticalityIndex = Criticality.valueOf(criticality.toUpperCase().replace(" ", "_")).ordinal();
        } catch (NullPointerException | IllegalArgumentException npe) {
            throw new SlaAnalysisException("Criticatility \"" + criticality + "\" is not recognized as valid value.");
        }

        String priority = incident.getPriority();
        Integer priorityIndex;
        try {
            priorityIndex = Priority.valueOf(priority.toUpperCase().replace(" ", "_")).ordinal();
        } catch (NullPointerException | IllegalArgumentException npe) {
            throw new SlaAnalysisException("Criticatility \"" + criticality + "\" is not recognized as valid value.");
        }
        return serviceLevelAgreements[criticalityIndex][priorityIndex];
    }

    List<ReportDetail> analyze(List<Incident> integrateIncidents) {
        return analyze(integrateIncidents, null);
    }

    private static class DefaultReportGeneratorObserver extends BaseSlaReportGeneratorObserver {

    }
}
