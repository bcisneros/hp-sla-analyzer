package com.hp.sla.analyser.model;

import com.hp.sla.analyser.model.util.BurnedOut;
import java.sql.Timestamp;
import java.util.Date;

/**
 * This class represents the rows of data that will contain the SLA report
 * @author Mallinali Ramirez Corona
 */
public class ReportDetail implements Cloneable {

    private Incident incident;
    private boolean compliantWithSLA;
    private boolean burnedOut;
    private Exception detailException;

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

    public Timestamp getIncidentCreatedTimestamp() {
        return incident.getCreationTimestamp();
    }

    public Timestamp getIncidentClosedTimestamp() {
        return incident.getCloseTimestamp();
    }

    public Date getIncidentAuditSystemModifiedTime() {
        return incident.getLastAssignmentGroupAudit() != null ? incident.getLastAssignmentGroupAudit().getSystemModifiedTime() : null;
    }

    public String getBurnedOutComplianceString() {
        if (detailException != null) {
            return BurnedOut.UNDETERMINED.name();
        }
        return isBurnedOut() ? BurnedOut.NON_COMPLIANCE.name() : BurnedOut.COMPLIANCE.name();
    }

    public Double getIncidentTimeToFixDurationHours() {
        return incident.getTimeToFixDuration();
    }

    public String getIncidentCurrentAssignmentGroupName() {
        return incident.getCurrentAssignmentGroup();
    }

    public String getIncidentAuditNewValueText() {
        return incident.getLastAssignmentGroupAudit() != null ? incident.getLastAssignmentGroupAudit().getNewVaueText() : null;
    }

    public String getConfigurationItemLogicalName() {
        return incident.getConfigurationItemLogicalName();
    }

    public String getRelatedRootConfigurationItemBusinessFriendlyName() {
        return incident.getRelatedRootConfigurationItemBusinessFriendlyName();
    }

    public long getRelatedRootConfigurationItemApplicationPortfolioIdentifier() {
        return incident.getRelatedRootConfigurationItemApplicationPortfolioIdentifier();
    }

    public String getIncidentCriticalityDescription() {
        return incident.getCriticalityDescription();
    }

    public String getIncidentPriorityDescription() {
        return incident.getPriority();
    }

    public String getIncidentCurrentStatusPhaseDescription() {
        return incident.getCurrentStatusPhaseDescription();
    }

    public String getIncidentCurrentStatusDescription() {
        return incident.getCurrentStatus();
    }

    public double getIncidentAgingDurationInDays() {
        return incident.getAgingDurationInDays();
    }

    public String getIncidentOpenedByEmailName() {
        return incident.getOpenedByEmailName();
    }

    public String getIncidentAssigneeEmailName() {
        return incident.getAssigneeEmailName();
    }

    public String getIncidentAssigneeManagerEmailName() {
        return incident.getAssigneeManagerEmailName();
    }

    public String getIncidentAssigneeOrganizationUnitName() {
        return incident.getAssigneeOrganizationUnitName();
    }

    public String getIncidentCurrentAssignmentGroupSupportLevelDescription() {
        return incident.getCurrentAssignmentGroupSupportLevelDescription();
    }

    public String getIncidentClosedAssignmentGroupSupportLevelDescription() {
        return incident.getClosedAssignmentGroupSupportLevelDescription();
    }

    public String getConfigurationItemStatusDescription() {
        return incident.getConfigurationItemStatus();
    }

    public String getConfigurationItemEnvironmentDescription() {
        return incident.getConfigurationItemEnvironment();
    }

    public String getIncidentConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel1Name() {
        return incident.getConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel1Name();
    }

    public Exception getDetailException() {
        return detailException;
    }

    public void setDetailException(Exception detailException) {
        this.detailException = detailException;
    }

    public String getErrorMessage() {
        return detailException == null ? "" : detailException.getLocalizedMessage();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
