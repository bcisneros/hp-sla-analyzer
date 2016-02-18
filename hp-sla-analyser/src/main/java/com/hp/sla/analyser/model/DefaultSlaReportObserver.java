package com.hp.sla.analyser.model;

/**
 * A default implementation of SlaReportGeneratorObserver
 */
class DefaultSlaReportObserver extends BaseSlaReportGeneratorObserver {

	@Override
	public void notifyProcessPhase(SlaReportGenerator aThis, String string) {
		SlaReportGenerator.logger.info(string);
	}

	@Override
	public void onFinalizeReportGeneration(SlaReportGenerator slaReportGenerator) {
		SlaReportGenerator.logger.info("Report created: " + slaReportGenerator.getGeneratedReportFile());
	}

	@Override
	public void onReportGenerationError(Exception ex) {
		SlaReportGenerator.logger.error("Error during the creation of the file", ex);
	}

	@Override
	public void onStartReportGeneration(SlaReportGenerator slaReportGenerator) {
		SlaReportGenerator.logger.info("Starting process...");
	}

	@Override
	public void reportCurrentIncident(Incident incident, int i) {
		SlaReportGenerator.logger.info("Current Incident is: " + incident);
		SlaReportGenerator.logger.info(i + " incidents analized of " + getTotal());
	}

}