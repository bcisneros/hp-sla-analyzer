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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 *
 * @author Benjamin Cisneros Barraza
 */
public class SlaReportGenerator {

    final static Logger logger = Logger.getLogger(SlaReportGenerator.class);
    private String destination = "C:\\temp\\";

    public void generateReport(String incidentsFile, String auditsFile, String destinationPath) throws SlaReportGenerationException {
        destination = destinationPath;
        logger.info("Report Generation Process initialized!");
        debugIfEnabled(logger, "Incidents File: " + incidentsFile);
        debugIfEnabled(logger, "Audits File: " + auditsFile);
        debugIfEnabled(logger, "Destination Path: " + destinationPath);
        if (isNullOrEmpty(incidentsFile) || isNullOrEmpty(auditsFile) || isNullOrEmpty(destinationPath)) {

            throw new SlaReportGenerationException("Input and Output data is required.");
        }
        //TODO: convert read method to static who receives a String with the filename  
        ExcelReader incidentExcel = new ExcelReader();
        ExcelReader auditExcel = new ExcelReader();
        try {
            incidentExcel.setInputFile(new FileInputStream(new File(incidentsFile)));
            auditExcel.setInputFile(new FileInputStream(new File(auditsFile)));

            XSSFSheet incidentSheet = (XSSFSheet) incidentExcel.read().getSheetAt(0);
            XSSFSheet auditSheet = (XSSFSheet) auditExcel.read().getSheetAt(0);

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
        ExcelReader reader = new ExcelReader();
        reader.setInputFile(ResourcesUtil.getResourceFromProjectClasspath("files/reportTemplate.xlsx"));
        Workbook wb = reader.read();
        Sheet sheet1 = wb.getSheetAt(0);
        Sheet sheet2 = wb.getSheetAt(1);
        List<ReportDetail> determinedIncidents = new ArrayList<>();
        List<ReportDetail> undeterminedIncidents = new ArrayList<>();

        for (ReportDetail detail : data) {
            if (ReportDetail.BURNED_OUT_INDETERMINED.equalsIgnoreCase(detail.getBurnedOutComplianceString())) {
                undeterminedIncidents.add(detail);
            } else {
                determinedIncidents.add(detail);
            }
        }
        loadData(sheet1, determinedIncidents);
        loadData(sheet2, undeterminedIncidents);
        ExcelWritter ew = new ExcelWritter();
        ew.write(wb, generateFileName());
    }

    protected void loadData(Sheet sheet, List<ReportDetail> data) {
        sheet.createFreezePane(0, 1);
        Row row;
        int rownum = 1;
        CellStyle dataStyle = sheet.getRow(0).getCell(0).getCellStyle();
        dataStyle.setFillBackgroundColor(CellStyle.NO_FILL);
        for (int i = 0; i < data.size(); i++, rownum++) {
            row = sheet.createRow(rownum);
            row.setRowStyle(dataStyle);
            ReportDetail reportDetail = data.get(i);
            row.createCell(0).setCellValue(reportDetail.getIncidentIdentifier());
            row.getCell(0).setCellStyle(dataStyle);
            createDateCellValue(reportDetail.getIncidentCreatedTimestamp(), row, sheet, 1);
            createDateCellValue(reportDetail.getIncidentClosedTimestamp(), row, sheet, 2);
            createDateCellValue(reportDetail.getIncidentAuditSystemModifiedTime(), row, sheet, 3);
            row.createCell(4).setCellValue(reportDetail.getBurnedOutComplianceString());
            row.createCell(5).setCellValue(reportDetail.getIncidentTimeToFixDurationHours());
            row.createCell(6).setCellValue(reportDetail.getIncidentCurrentAssignmentGroupName());
            row.createCell(7).setCellValue(reportDetail.getIncidentAuditNewValueText());
            row.createCell(8).setCellValue(reportDetail.getConfigurationItemLogicalName());
            row.createCell(9).setCellValue(reportDetail.getRelatedRootConfigurationItemBusinessFriendlyName());
            row.createCell(10).setCellValue(reportDetail.getRelatedRootConfigurationItemApplicationPortfolioIdentifier());
            row.createCell(11).setCellValue(reportDetail.getIncidentCriticalityDescription());
            row.createCell(12).setCellValue(reportDetail.getIncidentPriorityDescription());
            row.createCell(13).setCellValue(reportDetail.getIncidentCurrentStatusPhaseDescription());
            row.createCell(14).setCellValue(reportDetail.getIncidentCurrentStatusDescription());
            row.createCell(15).setCellValue(reportDetail.getIncidentAgingDurationInDays());
            row.createCell(16).setCellValue(reportDetail.getIncidentOpenedByEmailName());
            row.createCell(17).setCellValue(reportDetail.getIncidentAssigneeEmailName());
            row.createCell(18).setCellValue(reportDetail.getIncidentAssigneeManagerEmailName());
            row.createCell(19).setCellValue(reportDetail.getIncidentAssigneeOrganizationUnitName());
            row.createCell(20).setCellValue(reportDetail.getIncidentCurrentAssignmentGroupSupportLevelDescription());
            row.createCell(21).setCellValue(reportDetail.getIncidentClosedAssignmentGroupSupportLevelDescription());
            row.createCell(22).setCellValue(reportDetail.getConfigurationItemStatusDescription());
            row.createCell(23).setCellValue(reportDetail.getConfigurationItemEnvironmentDescription());
            row.createCell(24).setCellValue(reportDetail.getIncidentConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel1Name());
            row.createCell(25).setCellValue(reportDetail.getErrorMessage());
        }
    }

    protected void createDateCellValue(Date dateToStore, Row row, Sheet sheet, int cellIndex) {
        if (dateToStore == null) {
            row.createCell(cellIndex).setCellValue("");
        } else {
            CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
            cellStyle.setDataFormat(sheet.getWorkbook().getCreationHelper().createDataFormat().getFormat("mm/dd/yyyy h:mm:ss"));
            Cell cell = row.createCell(cellIndex);
            cell.setCellValue(dateToStore);
            cell.setCellStyle(cellStyle);
        }
    }

    protected String generateFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
        String fileNameSuffix = format.format(new Date());
        return destination + "SLAAnalysisReport-" + fileNameSuffix;
    }
}
