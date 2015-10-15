package com.hp.sla.analyser.model;

import com.hp.sla.analyser.model.util.AuditSystemModifiedTimeComparator;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Determines if an Incident have compliance with the SLA defined by HP
 *
 * @author Benjamin Cisneros Barraza
 */
public class SlaAnalyzer {

    private final Logger logger = Logger.getLogger(SlaAnalyzer.class);
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
     * @return A <code>List</code> object of <code>ReportDetail</code>
     */
    public List<ReportDetail> analyze(List<Incident> incidents) {
        if (incidents == null || incidents.isEmpty()) {
            throw new IllegalArgumentException("The list of incidents cannot be null or empty");
        }
        List<ReportDetail> details = new ArrayList<>();
        for (Incident incident : incidents) {
            try {
                details.add(analizeIncident(incident));
            } catch (SlaAnalysisException ex) {
                logger.error("Error during the SLA Analysis of incident " + incident, ex);
            }
        }
        return details;
    }

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
        List<Audit> audits = incident.getAudits();
        Collections.sort(audits, new AuditSystemModifiedTimeComparator());
        Audit lastAssignmentGroupAudit = null;
        for (Audit tempAudit : audits) {
            if (getAssignmentGroupsListToAnalize().contains(tempAudit.getNewVaueText())) {
                lastAssignmentGroupAudit = tempAudit;
                logger.debug("Last AG Audit: " + lastAssignmentGroupAudit.getNewVaueText());
                break;
            }
        }
        if (lastAssignmentGroupAudit == null) {
            throw new SlaAnalysisException("It was not encountered the last Assignment Group Audit");
        }
        incident.setLastAssignmentGroupAudit(lastAssignmentGroupAudit);
        ServiceLevelAgreement serviceLevelAgreement = getServiceLevelAgreementByIncident(incident);
        if (serviceLevelAgreement == null) {
            throw new SlaAnalysisException("No Service Level Agreement was found");
        }
        
        Timestamp incidentBurnedOutDate = incident.calculateBurnedOutDate(serviceLevelAgreement);
        Timestamp incidentTimeToFixDeadline = incident.calculateTimeToFixDeadLine(serviceLevelAgreement);
        
        boolean isBurnedOut = incidentBurnedOutDate.compareTo(incident.getLastAssignmentGroupAudit().getSystemModifiedTime()) < 0;
        boolean isCompliantWithSLA = incidentTimeToFixDeadline.compareTo(incident.getCloseTimestamp()) > 0;
        ReportDetail detail = new ReportDetail();
        detail.setIncident(incident);
        detail.setCompliantWithSLA(isCompliantWithSLA);
        detail.setBurnedOut(isBurnedOut);
        return detail;
    }

    protected ServiceLevelAgreement getServiceLevelAgreementByIncident(Incident incident) throws SlaAnalysisException {
        String criticality = incident.getCriticalityDescription();

        if (criticality == null) {
            throw new SlaAnalysisException("Incident Critically Description is required to get the SLA");
        }

        Integer criticalityIndex = null;
        switch (criticality.toUpperCase()) {
            case "MISSION CRITICAL":
                criticalityIndex = 0;
                break;
            case "ENTITY ESSENTIAL":
                criticalityIndex = 1;
                break;
            case "NORMAL":
                criticalityIndex = 2;
                break;
            default:
                throw new SlaAnalysisException("Criticatility \"" + criticality + "\" is not recognized as valid value.");
        }
        String priority = incident.getPriority();
        if (priority == null) {
            throw new SlaAnalysisException("Incident Priority Description is required to get the SLA");
        }
        Integer priorityIndex = null;
        switch (priority.toUpperCase()) {
            case "TOP":
                priorityIndex = 0;
                break;
            case "HIGH":
                priorityIndex = 1;
                break;
            case "MEDIUM":
                priorityIndex = 2;
                break;
            case "LOW":
                priorityIndex = 3;
                break;
            default:
                throw new SlaAnalysisException("Priority \"" + priority + "\" is not recognized as valid value.");
        }

        return serviceLevelAgreements[criticalityIndex][priorityIndex];
    }
}
