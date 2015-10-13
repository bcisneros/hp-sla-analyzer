package com.hp.sla.analyser.model;

import com.hp.sla.analyser.model.util.AuditSystemModifiedTimeComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;


/**
 * Determines if an Incident have compliance with the SLA defined by HP
 *
 * @author Benjamin Cisneros Barraza
 */
public class SlaAnalyzer {
    
    private final Logger logger = Logger.getLogger(SlaAnalyzer.class);

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
    
    public List<String> getAssignmentGroupsListToAnalize(){
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
        for(Audit tempAudit:audits) {
            if (getAssignmentGroupsListToAnalize().contains(tempAudit.getNewVaueText())){
                lastAssignmentGroupAudit = tempAudit;
                logger.debug("Last AG Audit: " + lastAssignmentGroupAudit.getNewVaueText());
                break;
            }
        }
        if (lastAssignmentGroupAudit == null) {
            throw new SlaAnalysisException("It was not encountered the last Assignment Group Audit");
        }
        ReportDetail detail = new ReportDetail();
        detail.setIncident(incident);
        detail.setComplianceWithSLA(true);
        return detail;
    }
}
