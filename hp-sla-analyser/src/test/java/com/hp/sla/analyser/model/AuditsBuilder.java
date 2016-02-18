package com.hp.sla.analyser.model;

import java.sql.Timestamp;

public class AuditsBuilder {

	public static AuditsBuilder anAudit() {
		return new AuditsBuilder();
	}

	private String ag;
	private Timestamp systemTime;

	public AuditsBuilder ag(String string) {
		this.ag = string;
		return this;
	}

	public AuditsBuilder systemTime(Timestamp systemModifiedTime) {
		this.systemTime = systemModifiedTime;
		return this;
	}

	public Audit build() {
		Audit audit = new Audit();
		audit.setSystemModifiedTime(systemTime);
		audit.setNewVaueText(ag);
		return audit;
	}

}
