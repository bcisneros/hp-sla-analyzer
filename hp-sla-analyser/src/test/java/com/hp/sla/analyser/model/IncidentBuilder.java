package com.hp.sla.analyser.model;

import java.sql.Timestamp;

public class IncidentBuilder {

	public static IncidentBuilder anIncident() {
		return new IncidentBuilder();
	}

	private String id;
	private String organization;
	private String criticality;
	private String priority;
	private Timestamp creationTime;
	private Timestamp closeTime;
	private Audit[] audits = new Audit[0];

	public IncidentBuilder id(String generatedId) {
		this.id = generatedId;
		return this;
	}

	public IncidentBuilder organization(String organizationLevel1Name) {
		this.organization = organizationLevel1Name;
		return this;
	}

	public IncidentBuilder criticality(String criticality) {
		this.criticality = criticality;
		return this;
	}

	public IncidentBuilder priority(String priority) {
		this.priority = priority;
		return this;
	}

	public IncidentBuilder creationTime(Timestamp valueOf) {
		this.creationTime = valueOf;
		return this;
	}

	public IncidentBuilder closeTime(Timestamp closeTime) {
		this.closeTime = closeTime;
		return this;
	}

	public IncidentBuilder withAudits(Audit... audits) {
		this.audits  = audits;
		return this;
	}

	public Incident build() {
		Incident incident = new Incident();
		incident.setId(id);
		incident.setConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel1Name(organization);
		incident.setCriticalityDescription(criticality);
		incident.setPriority(priority);
		incident.setCreationTimestamp(creationTime);
		incident.setCloseTimestamp(closeTime);
		for(Audit audit:audits) {
			audit.setIncidentID(id);
			incident.addAudit(audit);
		}
		return incident;
	}

}
