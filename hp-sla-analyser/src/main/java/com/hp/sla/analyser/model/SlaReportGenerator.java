package com.hp.sla.analyser.model;

import com.hp.sla.analyser.model.util.AuditParser;
import com.hp.sla.analyser.model.util.ExcelReader;
import com.hp.sla.analyser.model.util.ExcelWritter;
import com.hp.sla.analyser.model.util.IncidentParser;
import static com.hp.sla.analyser.util.LoggerUtil.debugIfEnabled;
import com.hp.sla.analyser.util.ResourcesUtil;
import static com.hp.sla.analyser.util.StringsUtil.isNullOrEmpty;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 *
 * @author Benjamin Cisneros Barraza
 */
public class SlaReportGenerator {

    final static Logger logger = Logger.getLogger(SlaReportGenerator.class);
    private String destination = "C:\\temp\\";
    Workbook workbookTemplate;
    private CellStyle defaultCellStyle = null;
    private CellStyle dateCellStyle = null;
    private CellStyle doubleNumberCellStyle = null;
    private CellStyle integerNumberCellStyle = null;

    public SlaReportGenerator() {
        try {
            this.workbookTemplate = ExcelReader.read(ResourcesUtil.getResourceFromProjectClasspath("files/reportTemplate.xlsx"));
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
        } catch (Exception ex) {
            logger.error("Error generating Cell Styles from template", ex);
            //TODO: Add logic to generate a runtime template
        }
    }

    public void generateReport(String incidentsFile, String auditsFile, String destinationPath) throws SlaReportGenerationException {
        destination = destinationPath;
        logger.info("Report Generation Process initialized!");
        debugIfEnabled(logger, "Incidents File: " + incidentsFile);
        debugIfEnabled(logger, "Audits File: " + auditsFile);
        debugIfEnabled(logger, "Destination Path: " + destinationPath);
        if (isNullOrEmpty(incidentsFile) || isNullOrEmpty(auditsFile) || isNullOrEmpty(destinationPath)) {

            throw new SlaReportGenerationException("Input and Output data is required.");
        }
        try {
            XSSFSheet incidentSheet = (XSSFSheet) ExcelReader.read(new FileInputStream(new File(incidentsFile))).getSheetAt(0);
            XSSFSheet auditSheet = (XSSFSheet) ExcelReader.read(new FileInputStream(new File(auditsFile))).getSheetAt(0);

            IncidentParser ip = new IncidentParser();
            AuditParser ap = new AuditParser();

            List<Incident> incidents = ip.parseDocument(incidentSheet);
            List<Audit> audits = ap.parseDocument(auditSheet);

            SlaAnalyzer slaa = new SlaAnalyzer();
            List<ReportDetail> report = slaa.analyze(integrateIncidents(incidents, audits));
            generateWorkbook(report);

            logger.info("Report Generation Process completed!");

        } catch (Exception ex) {
            logger.error("Error during the generation of the report", ex);
            throw new SlaReportGenerationException(ex.getMessage());
        }

    }

    protected List<Incident> integrateIncidents(List<Incident> incidents, List<Audit> audits) {
        Collections.sort(incidents);
        Collections.sort(audits);
        List<Audit> inc_audits;
        int index = 0;
        for (Incident incident : incidents) {
            inc_audits = new LinkedList();
            while (index < audits.size() && audits.get(index).getIncidentID().equals(incident.getId())) {
                inc_audits.add(audits.get(index));
                index++;
            }
            incident.setAudits(inc_audits);
        }
        return incidents;
    }

    protected void generateWorkbook(List<ReportDetail> data) throws Exception {

        Sheet determinedIncidentsSheet = workbookTemplate.getSheetAt(0);
        Sheet undeterminedIncidentsSheet = workbookTemplate.getSheetAt(1);

        List<ReportDetail> determinedIncidents = new ArrayList<>();
        List<ReportDetail> undeterminedIncidents = new ArrayList<>();

        for (ReportDetail detail : data) {
            if (ReportDetail.BURNED_OUT_INDETERMINED.equalsIgnoreCase(detail.getBurnedOutComplianceString())) {
                undeterminedIncidents.add(detail);
            } else {
                determinedIncidents.add(detail);
            }
        }
        loadData(determinedIncidentsSheet, determinedIncidents);
        loadData(undeterminedIncidentsSheet, undeterminedIncidents);
        ExcelWritter ew = new ExcelWritter();
        ew.write(workbookTemplate, generateFileName());
    }

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
            createCellValue(reportDetail.getIncidentConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel1Name(), row, 24);
            createCellValue(reportDetail.getErrorMessage(), row, 25);
        }
    }

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
        }
    }

    protected String generateFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
        String fileNameSuffix = format.format(new Date());
        return destination + "SLAAnalysisReport-" + fileNameSuffix;
    }
}
