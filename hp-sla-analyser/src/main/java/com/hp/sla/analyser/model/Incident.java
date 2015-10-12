package com.hp.sla.analyser.model;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Represents an Incident object that have all the details about an incident
 * into the Incidents Report Spreadsheet
 *
 * @author ramirmal
 * @updatedby Benjamín Cisneros Barraza
 */
public class Incident {

    private String id;
    private Timestamp creationTimestamp;
    private Timestamp closeTimestamp;
    private double timeToFixDuration;
    private String configurationItemLogicalName;
    private String currentStatusPhaseDescription;
    private boolean productionActive;
    private String priority;
    private double agingDurationInDays;
    private String closureCodeDescription;
    private String openedByEmailName;
    private String assigneeEmailName;
    private String assigneeManagerEmailName;
    private String assigneeOrganizationUnitName;
    private String currentAssignmentGroup;
    private String criticalityDescription;
    private String currentAssignmentGroupSupportLevelDescription;
    private String closedAssignmentGroupSupportLevelDescription;
    private String configurationItemITAssetOwnerAssignmentGroupOrganizationLevel4Name;
    private String title;
    private long relatedRootConfigurationItemApplicationPortfolioIdentifier;
    private String relatedRootConfigurationItemBusinessFriendlyName;
    private String configurationItemStatus;
    private String configurationItemEnvironment;
    private String currentStatus;
    private String configurationItemITAssetOwnerAssignmentGroupOrganizationLevel1Name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Timestamp creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public Timestamp getCloseTimestamp() {
        return closeTimestamp;
    }

    public void setCloseTimestamp(Timestamp closeTimestamp) {
        this.closeTimestamp = closeTimestamp;
    }

    public double getTimeToFixDuration() {
        return timeToFixDuration;
    }

    public void setTimeToFixDuration(double timeToFixDuration) {
        this.timeToFixDuration = timeToFixDuration;
    }

    public String getConfigurationItemLogicalName() {
        return configurationItemLogicalName;
    }

    public void setConfigurationItemLogicalName(String configurationItemLogicalName) {
        this.configurationItemLogicalName = configurationItemLogicalName;
    }

    public String getCurrentStatusPhaseDescription() {
        return currentStatusPhaseDescription;
    }

    public void setCurrentStatusPhaseDescription(String currentStatusPhaseDescription) {
        this.currentStatusPhaseDescription = currentStatusPhaseDescription;
    }

    public boolean isProductionActive() {
        return productionActive;
    }

    public void setProductionActive(boolean productionActive) {
        this.productionActive = productionActive;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public double getAgingDurationInDays() {
        return agingDurationInDays;
    }

    public void setAgingDurationInDays(double agingDurationInDays) {
        this.agingDurationInDays = agingDurationInDays;
    }

    public String getClosureCodeDescription() {
        return closureCodeDescription;
    }

    public void setClosureCodeDescription(String closureCodeDescription) {
        this.closureCodeDescription = closureCodeDescription;
    }

    public String getOpenedByEmailName() {
        return openedByEmailName;
    }

    public void setOpenedByEmailName(String openedByEmailName) {
        this.openedByEmailName = openedByEmailName;
    }

    public String getAssigneeEmailName() {
        return assigneeEmailName;
    }

    public void setAssigneeEmailName(String assigneeEmailName) {
        this.assigneeEmailName = assigneeEmailName;
    }

    public String getAssigneeManagerEmailName() {
        return assigneeManagerEmailName;
    }

    public void setAssigneeManagerEmailName(String assigneeManagerEmailName) {
        this.assigneeManagerEmailName = assigneeManagerEmailName;
    }

    public String getAssigneeOrganizationUnitName() {
        return assigneeOrganizationUnitName;
    }

    public void setAssigneeOrganizationUnitName(String assigneeOrganizationUnitName) {
        this.assigneeOrganizationUnitName = assigneeOrganizationUnitName;
    }

    public String getCurrentAssignmentGroup() {
        return currentAssignmentGroup;
    }

    public void setCurrentAssignmentGroup(String currentAssignmentGroup) {
        this.currentAssignmentGroup = currentAssignmentGroup;
    }

    public String getCriticalityDescription() {
        return criticalityDescription;
    }

    public void setCriticalityDescription(String criticalityDescription) {
        this.criticalityDescription = criticalityDescription;
    }

    public String getCurrentAssignmentGroupSupportLevelDescription() {
        return currentAssignmentGroupSupportLevelDescription;
    }

    public void setCurrentAssignmentGroupSupportLevelDescription(String currentAssignmentGroupSupportLevelDescription) {
        this.currentAssignmentGroupSupportLevelDescription = currentAssignmentGroupSupportLevelDescription;
    }

    public String getClosedAssignmentGroupSupportLevelDescription() {
        return closedAssignmentGroupSupportLevelDescription;
    }

    public void setClosedAssignmentGroupSupportLevelDescription(String closedAssignmentGroupSupportLevelDescription) {
        this.closedAssignmentGroupSupportLevelDescription = closedAssignmentGroupSupportLevelDescription;
    }

    public String getConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel4Name() {
        return configurationItemITAssetOwnerAssignmentGroupOrganizationLevel4Name;
    }

    public void setConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel4Name(String configurationItemITAssetOwnerAssignmentGroupOrganizationLevel4Name) {
        this.configurationItemITAssetOwnerAssignmentGroupOrganizationLevel4Name = configurationItemITAssetOwnerAssignmentGroupOrganizationLevel4Name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getRelatedRootConfigurationItemApplicationPortfolioIdentifier() {
        return relatedRootConfigurationItemApplicationPortfolioIdentifier;
    }

    public void setRelatedRootConfigurationItemApplicationPortfolioIdentifier(long relatedRootConfigurationItemApplicationPortfolioIdentifier) {
        this.relatedRootConfigurationItemApplicationPortfolioIdentifier = relatedRootConfigurationItemApplicationPortfolioIdentifier;
    }

    public String getRelatedRootConfigurationItemBusinessFriendlyName() {
        return relatedRootConfigurationItemBusinessFriendlyName;
    }

    public void setRelatedRootConfigurationItemBusinessFriendlyName(String relatedRootConfigurationItemBusinessFriendlyName) {
        this.relatedRootConfigurationItemBusinessFriendlyName = relatedRootConfigurationItemBusinessFriendlyName;
    }

    public String getConfigurationItemStatus() {
        return configurationItemStatus;
    }

    public void setConfigurationItemStatus(String configurationItemStatus) {
        this.configurationItemStatus = configurationItemStatus;
    }

    public String getConfigurationItemEnvironment() {
        return configurationItemEnvironment;
    }

    public void setConfigurationItemEnvironment(String configurationItemEnvironment) {
        this.configurationItemEnvironment = configurationItemEnvironment;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel1Name() {
        return configurationItemITAssetOwnerAssignmentGroupOrganizationLevel1Name;
    }

    public void setConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel1Name(String configurationItemITAssetOwnerAssignmentGroupOrganizationLevel1Name) {
        this.configurationItemITAssetOwnerAssignmentGroupOrganizationLevel1Name = configurationItemITAssetOwnerAssignmentGroupOrganizationLevel1Name;
    }

    @Override
    public String toString() {
        return "Incident{" + "id=" + id + ", title=" + title + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Incident other = (Incident) obj;
        return Objects.equals(this.id, other.id);
    }

}
