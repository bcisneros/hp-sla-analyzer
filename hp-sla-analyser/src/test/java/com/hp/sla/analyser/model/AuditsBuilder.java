package com.hp.sla.analyser.model;

import java.sql.Timestamp;

public class AuditsBuilder {

	public static AuditsBuilder anAudit() {
		return new AuditsBuilder();
	}

	private String ag;
	private Timestamp systemTime;
	private String fieldDisplayName = "Assignment Group";
	private String fieldName = "assignment";
	private boolean logicalDeleteFlag;
	private String previousAssignmentGroup;
	private int recordNumber;
	private String systemModifiedUser = "HPOO";
	private String incidentID;

	public AuditsBuilder currentAssignmentGroup(String string) {
		this.ag = string;
		return this;
	}

	public AuditsBuilder systemTime(Timestamp systemModifiedTime) {
		this.systemTime = systemModifiedTime;
		return this;
	}

	public AuditsBuilder fieldDisplayName(String fieldDisplayName) {
		this.fieldDisplayName = fieldDisplayName;
		return this;
	}

	public AuditsBuilder fieldName(String fieldName) {
		this.fieldName = fieldName;
		return this;
	}

	public AuditsBuilder logicalDeleteFlag(boolean logicalDeleteFlag) {
		this.logicalDeleteFlag = logicalDeleteFlag;
		return this;
	}

	public AuditsBuilder previousAssignmentGroup(String previousAssignmentGroup) {
		this.previousAssignmentGroup = previousAssignmentGroup;
		return this;
	}

	public AuditsBuilder recordNumber(int recordNumber) {
		this.recordNumber = recordNumber;
		return this;
	}

	public AuditsBuilder systemModifiedUser(String systemModifiedUser) {
		this.systemModifiedUser = systemModifiedUser;
		return this;
	}

	public AuditsBuilder incidentID(String incidentID) {
		this.incidentID = incidentID;
		return this;
	}

	public Audit build() {
		Audit audit = new Audit();
		audit.setIncidentID(incidentID);
		audit.setSystemModifiedTime(systemTime);
		audit.setNewVaueText(ag);
		audit.setFieldDisplayName(fieldDisplayName);
		audit.setFieldName(fieldName);
		audit.setLogicalDeleteFlag(logicalDeleteFlag);
		audit.setPreviousValueText(previousAssignmentGroup);
		audit.setRecordNumber(recordNumber);
		audit.setSystemModifiedUser(systemModifiedUser);
		return audit;
	}

}
