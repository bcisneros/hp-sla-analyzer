package com.hp.sla.analyser.model;

import static com.hp.sla.analyser.util.StringsUtil.isNullOrEmpty;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hp.sla.analyser.model.util.AuditParser;
import com.hp.sla.analyser.model.util.BurnedOut;
import com.hp.sla.analyser.model.util.ExcelReader;
import com.hp.sla.analyser.model.util.ExcelWritter;
import com.hp.sla.analyser.model.util.IncidentParser;
import com.hp.sla.analyser.util.ResourcesUtil;

/**
 * Performs all the process of generate a report file
 *
 * @author Benjamin Cisneros Barraza
 */
public class SlaReportGenerator {

	private final static Logger logger = Logger.getLogger(SlaReportGenerator.class);
	private final static Workbook workbookTemplate;
	private final static CellStyle defaultCellStyle;
	private final static CellStyle dateCellStyle;
	private final static CellStyle doubleNumberCellStyle;
	private final static CellStyle integerNumberCellStyle;
	private final static CellStyle greenCellStyle;
	private final static CellStyle redCellStyle;

	static {
		workbookTemplate = getWorkBook();
		defaultCellStyle = workbookTemplate.createCellStyle();
		defaultCellStyle.cloneStyleFrom(workbookTemplate.getSheetAt(0).getRow(1).getCell(0).getCellStyle());
		dateCellStyle = workbookTemplate.createCellStyle();
		dateCellStyle.cloneStyleFrom(defaultCellStyle);
		final CreationHelper creationHelper = workbookTemplate.getCreationHelper();
		dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("mm/dd/yyyy hh:mm:ss"));
		doubleNumberCellStyle = workbookTemplate.createCellStyle();
		doubleNumberCellStyle.cloneStyleFrom(defaultCellStyle);
		doubleNumberCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("0.00"));
		integerNumberCellStyle = workbookTemplate.createCellStyle();
		integerNumberCellStyle.cloneStyleFrom(defaultCellStyle);
		integerNumberCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("0"));
		greenCellStyle = workbookTemplate.createCellStyle();
		greenCellStyle.cloneStyleFrom(defaultCellStyle);
		greenCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		greenCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		redCellStyle = workbookTemplate.createCellStyle();
		redCellStyle.cloneStyleFrom(defaultCellStyle);
		redCellStyle.setFillForegroundColor(IndexedColors.CORAL.getIndex());
		redCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	}

	@SuppressWarnings("resource")
	static Workbook getWorkBook() {
		Workbook w = new XSSFWorkbook();
		try {
			w = ExcelReader.read(ResourcesUtil.getResourceFromProjectClasspath("files/reportTemplate.xlsx"));
		} catch (Exception ex) {
			logger.error("No template were used. A blank report would be used insted of.", ex);
		}
		return w;
	}

	private String destination = "C:\\temp\\";
	private SlaReportGeneratorObserver observer;
	private String generatedReportFile;

	/**
	 * Creates a SlaReportGenerator with a default SlaReporGenerationObserver
	 * implementation
	 */
	public SlaReportGenerator() {
		observer = new DefaultSlaReportObserver();
	}

	/**
	 * Takes the two files and parse, analyze and create the output report
	 *
	 * @param incidentsFile
	 *            The path of the file who contains the list of incidents
	 * @param auditsFile
	 *            The path of the file who contains the list of audits
	 * @param destinationPath
	 *            The output destination path
	 * @throws SlaReportGenerationException
	 *             If there is an error during this process
	 */
	public void generateReport(String incidentsFile, String auditsFile, String destinationPath)
			throws SlaReportGenerationException {
		destination = destinationPath;
		logger.info("Report Generation Process initialized!");
		observer.onStartReportGeneration(this);
		if (isNullOrEmpty(incidentsFile) || isNullOrEmpty(auditsFile) || isNullOrEmpty(destinationPath)) {
			throw new SlaReportGenerationException("Input and Output data is required.");
		}
		try {
			observer.notifyProcessPhase(this, "Reading incidents file: " + incidentsFile);
			File file = new File(incidentsFile);
			Workbook wb = ExcelReader.read(new FileInputStream(file));
			XSSFSheet incidentSheet = (XSSFSheet) wb.getSheetAt(0);
			observer.notifyProcessPhase(this, "Incidents file was read correctly.");

			observer.notifyProcessPhase(this, "Reading audits file: " + auditsFile);
			file = new File(auditsFile);
			Workbook excel = ExcelReader.read(new FileInputStream(file));
			XSSFSheet auditSheet = (XSSFSheet) excel.getSheetAt(0);
			observer.notifyProcessPhase(this, "Audits file was read correctly.");

			IncidentParser ip = new IncidentParser();
			AuditParser ap = new AuditParser();

			observer.notifyProcessPhase(this, "Parsing incidents file: " + incidentsFile);
			List<Incident> incidents = ip.parseDocument(incidentSheet);
			observer.notifyProcessPhase(this, "Incidents file was parsed correctly.");

			observer.notifyProcessPhase(this, "Parsing audits file: " + auditsFile);
			List<Audit> audits = ap.parseDocument(auditSheet);
			observer.notifyProcessPhase(this, "Audits file was read correctly.");

			SlaAnalyzer slaa = new SlaAnalyzer();
			List<ReportDetail> report = slaa.analyze(integrateIncidents(incidents, audits), observer);
			observer.notifyProcessPhase(this, "Generating the report");
			generateWorkbook(report);

			logger.info("Report Generation Process completed!");
			observer.onFinalizeReportGeneration(this);

		} catch (Exception ex) {
			logger.error("Error during the generation of the report", ex);
			observer.onReportGenerationError(ex);
			throw new SlaReportGenerationException(ex.getMessage());
		}

	}

	public void setObserver(SlaReportGeneratorObserver observer) {
		this.observer = observer;
	}

	public String getGeneratedReportFile() {
		return generatedReportFile;

	}

	/**
	 * Combine the list of incidents with the list of audits
	 *
	 * @param incidents
	 *            The list of incidents
	 * @param audits
	 *            The list of audits
	 * @return A combined list of incidents with audits included
	 */
	protected List<Incident> integrateIncidents(List<Incident> incidents, List<Audit> audits) {
		Map<String, Incident> maps = new HashMap<>();
		for (Incident incident : incidents) {
			maps.put(incident.getId(), incident);
		}

		for (Audit audit : audits) {
			Incident incident = maps.get(audit.getIncidentID());
			if (incident != null) {
				incident.addAudit(audit);
			}

		}
		return incidents;
	}

	/**
	 * Create the final excel file taking a list of ReporDetail objects
	 *
	 * @param data
	 *            The list of details (records) of the report
	 * @throws Exception
	 *             If there is an exception during this process
	 */
	protected void generateWorkbook(List<ReportDetail> data) throws Exception {

		Sheet determinedIncidentsSheet = workbookTemplate.getSheetAt(0);
		Sheet undeterminedIncidentsSheet = workbookTemplate.getSheetAt(1);

		List<ReportDetail> determinedIncidents = new ArrayList<>();
		List<ReportDetail> undeterminedIncidents = new ArrayList<>();
		logger.info("Creating determine and undetermined lists");
		for (ReportDetail detail : data) {
			if (BurnedOut.UNDETERMINED.name().equalsIgnoreCase(detail.getBurnedOutComplianceString())) {
				undeterminedIncidents.add(detail);
			} else {
				determinedIncidents.add(detail);
			}
		}
		logger.info("Filled determined and undetermined lists");
		loadData(determinedIncidentsSheet, determinedIncidents);
		loadData(undeterminedIncidentsSheet, undeterminedIncidents);
		logger.info("Filled filled sheets");
		ExcelWritter ew = new ExcelWritter();

		try {
			logger.info("Writing Report File");
			generatedReportFile = ew.write(workbookTemplate, generateFileName());
			logger.info("Finished Writting Report File");
		} catch (FileNotFoundException fnfe) {
			throw new SlaReportGenerationException("File not found");
		} catch (IOException ioe) {
			throw new SlaReportGenerationException("Error writting the file");
		}
		observer.notifyProcessPhase(this, "File " + generatedReportFile + " was created!");
	}

	/**
	 * Load a list of ReportDetail objects in a determined Sheet object
	 *
	 * @param sheet
	 *            The Sheet object to load data
	 * @param data
	 *            The list of report details who need to be loaded in the sheet
	 */
	protected void loadData(Sheet sheet, List<ReportDetail> data) {
		sheet.createFreezePane(0, 1);
		Row row;
		int rownum = 1;
		for (ReportDetail reportDetail : data) {
			row = sheet.createRow(rownum++);
			createCellValue(reportDetail.getIncidentIdentifier(), row, 0);
			createCellValue(reportDetail.getIncidentCreatedTimestamp(), row, 1);
			createCellValue(reportDetail.getIncidentClosedTimestamp(), row, 2);
			createCellValue(reportDetail.getIncidentAuditSystemModifiedTime(), row, 3);
			createCellValue(reportDetail.getBurnedOutComplianceString(), row, 4);
			createCellValue(reportDetail.getIncidentTimeToFixDurationHours(), row, 5);
			createCellValue(reportDetail.getIncidentCurrentAssignmentGroupName(), row, 6);
			createCellValue(reportDetail.getIncidentAuditNewValueText(), row, 7);
			createCellValue(reportDetail.getConfigurationItemLogicalName(), row, 8);
			createCellValue(reportDetail.getRelatedRootConfigurationItemBusinessFriendlyName(), row, 9);
			createCellValue(reportDetail.getRelatedRootConfigurationItemApplicationPortfolioIdentifier(), row, 10);
			createCellValue(reportDetail.getIncidentCriticalityDescription(), row, 11);
			createCellValue(reportDetail.getIncidentPriorityDescription(), row, 12);
			createCellValue(reportDetail.getIncidentCurrentStatusPhaseDescription(), row, 13);
			createCellValue(reportDetail.getIncidentCurrentStatusDescription(), row, 14);
			createCellValue(reportDetail.getIncidentAgingDurationInDays(), row, 15);
			createCellValue(reportDetail.getIncidentOpenedByEmailName(), row, 16);
			createCellValue(reportDetail.getIncidentAssigneeEmailName(), row, 17);
			createCellValue(reportDetail.getIncidentAssigneeManagerEmailName(), row, 18);
			createCellValue(reportDetail.getIncidentAssigneeOrganizationUnitName(), row, 19);
			createCellValue(reportDetail.getIncidentCurrentAssignmentGroupSupportLevelDescription(), row, 20);
			createCellValue(reportDetail.getIncidentClosedAssignmentGroupSupportLevelDescription(), row, 21);
			createCellValue(reportDetail.getConfigurationItemStatusDescription(), row, 22);
			createCellValue(reportDetail.getConfigurationItemEnvironmentDescription(), row, 23);
			createCellValue(
					reportDetail.getIncidentConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel1Name(), row,
					24);
			createCellValue(reportDetail.getErrorMessage(), row, 25);
		}
	}

	/**
	 * Create a cell in a determined row and index
	 *
	 * @param dataToStore
	 *            The data object to store
	 * @param row
	 *            The row where the cell is going to be created
	 * @param cellIndex
	 *            The cell index number where you need to create the value
	 */
	protected void createCellValue(Object dataToStore, Row row, int cellIndex) {
		Cell cell = row.createCell(cellIndex);
		cell.setCellStyle(defaultCellStyle);
		if (dataToStore == null) {
			cell.setCellValue("");
			return;
		}
		if (dataToStore instanceof Date) {
			cell.setCellValue((Date) dataToStore);
			cell.setCellStyle(dateCellStyle);
		} else if (dataToStore instanceof Double) {
			cell.setCellValue((Double) dataToStore);
			cell.setCellStyle(doubleNumberCellStyle);
		} else if (dataToStore instanceof Integer) {
			cell.setCellValue((Integer) dataToStore);
			cell.setCellStyle(integerNumberCellStyle);
		} else if (dataToStore instanceof Long) {
			cell.setCellValue((Long) dataToStore);
			cell.setCellStyle(integerNumberCellStyle);
		} else {
			cell.setCellValue((String) dataToStore);
			if (cellIndex == 4) {
				if (dataToStore.equals("yes")) {
					cell.setCellStyle(greenCellStyle);
				} else if (dataToStore.equals("no")) {
					cell.setCellStyle(redCellStyle);
				}
			}
		}
	}

	/**
	 * Generate a filename taking the current timestamp
	 *
	 * @return The name of the next generated report
	 */
	protected String generateFileName() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
		String fileNameSuffix = format.format(new Date());
		return destination + "SLAAnalysisReport-" + fileNameSuffix;
	}

	/**
	 * A default implementation of SlaReportGeneratorObserver
	 */
	static class DefaultSlaReportObserver extends BaseSlaReportGeneratorObserver {

		@Override
		public void reportCurrentIncident(Incident incident, int i) {
			logger.info("Current Incident is: " + incident);
			logger.info(i + " incidents analized of " + getTotal());
		}

		@Override
		public void notifyProcessPhase(SlaReportGenerator aThis, String string) {
			logger.info(string);
		}

		@Override
		public void onFinalizeReportGeneration(SlaReportGenerator slaReportGenerator) {
			logger.info("Report created: " + slaReportGenerator.getGeneratedReportFile());
		}

		@Override
		public void onStartReportGeneration(SlaReportGenerator slaReportGenerator) {
			logger.info("Starting process...");
		}

		@Override
		public void onReportGenerationError(Exception ex) {
			logger.error("Error during the creation of the file", ex);
		}

	}
}
