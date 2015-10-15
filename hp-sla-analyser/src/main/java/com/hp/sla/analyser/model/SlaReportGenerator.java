package com.hp.sla.analyser.model;

import com.hp.sla.analyser.model.util.AuditParser;
import com.hp.sla.analyser.model.util.ExcelReader;
import com.hp.sla.analyser.model.util.ExcelWritter;
import com.hp.sla.analyser.model.util.IncidentParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Benjamin Cisneros Barraza
 */
public class SlaReportGenerator {

    public void generateReport(String incidentFile, String auditFile, String d) throws SlaReportGenerationException{
        if (incidentFile.isEmpty() || auditFile.isEmpty()) {
            throw new SlaReportGenerationException("Please select an incident file and an audit file");
        }
        ExcelReader incidentExcel = new ExcelReader();
        ExcelReader auditExcel = new ExcelReader();
        try {
            incidentExcel.setInputFile(new FileInputStream(new File(incidentFile)));
            auditExcel.setInputFile(new FileInputStream(new File(auditFile)));

            XSSFSheet incidentSheet = incidentExcel.read();
            XSSFSheet auditSheet = auditExcel.read();

            IncidentParser ip = new IncidentParser();
            AuditParser ap = new AuditParser();

            List<Incident> incidents = ip.parseDocument(incidentSheet);
            List<Audit> audits = ap.parseDocument(auditSheet);

            SlaAnalyzer slaa = new SlaAnalyzer();
            List<ReportDetail> report = slaa.analyze(integrateIncidents(incidents, audits));
            
            
            
        } catch (FileNotFoundException ex) {
            throw new SlaReportGenerationException(ex.getLocalizedMessage());
        } catch (IOException ioe) {
            throw new SlaReportGenerationException(ioe.getLocalizedMessage());
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(SlaReportGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Incident> integrateIncidents(List<Incident> incidents, List<Audit> audits) {
        Collections.sort(incidents);
        Collections.sort(audits);
        List<Audit> inc_audits;
        int index=0;
        for (Incident incident : incidents) {
            inc_audits=new LinkedList();
            while(index<audits.size() && audits.get(index).getIncidentID().equals(incident.getId())){
                inc_audits.add(audits.get(index));
                index++;
            }
            incident.setAudits(inc_audits);
        }
        return incidents;
    }
    
    public void generateWorkbook(List<ReportDetail> data) throws IllegalArgumentException, IllegalAccessException{
        Workbook wb= new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Report C - Salida");

        String[] titles=readHeaders(); 
        Map<String, CellStyle> styles= createStyles(wb);
        //Write header row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < titles.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(styles.get("header"));
        }
        
        //freeze the first row
        sheet.createFreezePane(0, 1);

        //Write the data
        Row row;
        int rownum = 1;
        String burnedOut="";
        for (int i = 0; i < data.size(); i++, rownum++) {
            row = sheet.createRow(rownum);
            
            ReportDetail repDet = data.get(i);
            int j=0;
            createBodyCell(j, row, styles.get("body"), repDet.getIncidentIdentifier()); j++;
            createBodyCell(j, row, styles.get("body"), repDet.getIncidentCreatedTimestamp()+""); j++;
            createBodyCell(j, row, styles.get("body"), repDet.getIncidentClosedTimestamp()+""); j++;
            createBodyCell(j, row, styles.get("body"), repDet.getIncidentAuditSystemModifiedTime()+""); j++;
            burnedOut=repDet.getBurnedOutComplianceString();
            if(burnedOut.equals("yes")){
                createBodyCell(j, row, styles.get("compliance"), burnedOut); 
            }else{
                createBodyCell(j, row, styles.get("nonCompliance"), burnedOut); 
            }
            j++;
            createBodyCell(j, row, styles.get("body"), repDet.getIncidentTimeToFixDurationHours()+""); j++;
            createBodyCell(j, row, styles.get("body"), repDet.getIncidentCurrentAssignmentGroupName()); j++;
            createBodyCell(j, row, styles.get("body"), repDet.getIncidentAuditNewValueText()); j++;
            createBodyCell(j, row, styles.get("body"), repDet.getConfigurationItemLogicalName()); j++;
            createBodyCell(j, row, styles.get("body"), repDet.getIncidentAuditNewValueText()+""); j++;
            createBodyCell(j, row, styles.get("body"), repDet.getConfigurationItemLogicalName()); j++;
            createBodyCell(j, row, styles.get("body"), repDet.getRelatedRootConfigurationItemBusinessFriendlyName()); j++;
            createBodyCell(j, row, styles.get("body"), repDet.getRelatedRootConfigurationItemApplicationPortfolioIdentifier()+""); j++;
            createBodyCell(j, row, styles.get("body"), repDet.getIncidentCriticalityDescription()); j++;
            createBodyCell(j, row, styles.get("body"), repDet.getIncidentCurrentStatusPhaseDescription()); j++;
            createBodyCell(j, row, styles.get("body"), repDet.getIncidentCurrentStatusDescription()); j++;
            createBodyCell(j, row, styles.get("body"), repDet.getIncidentAgingDurationInDays()+""); j++;
            createBodyCell(j, row, styles.get("body"), repDet.getIncidentOpenedByEmailName()); j++;
            createBodyCell(j, row, styles.get("body"), repDet.getIncidentAssigneeEmailName()); j++;
            createBodyCell(j, row, styles.get("body"), repDet.getIncidentAssigneeManagerEmailName()+""); j++;
            createBodyCell(j, row, styles.get("body"),repDet.getIncidentAssigneeOrganizationUnitName()); j++;
            createBodyCell(j, row, styles.get("body"),repDet.getIncidentCurrentAssignmentGroupSupportLevelDescription()); j++;
            createBodyCell(j, row, styles.get("body"),repDet.getIncidentClosedAssignmentGroupSupportLevelDescription()); j++;
            createBodyCell(j, row, styles.get("body"),repDet.getConfigurationItemStatusDescription()); j++;
            createBodyCell(j, row, styles.get("body"),repDet.getConfigurationItemEnvironmentDescription()); j++;
            createBodyCell(j, row, styles.get("body"),repDet.getIncidentConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel1Name()); j++;
            createBodyCell(j, row, styles.get("body"),repDet.getErrorMessage());
        }
        ExcelWritter ew=new ExcelWritter();
        ew.write(wb, "Report");
    }
    
    private String[] readHeaders(){
        ClassLoader classLoader = getClass().getClassLoader(); 
        String[] headers=null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(classLoader.getResource("reportHeaders.txt").getFile()));

            String content = br.readLine();
            headers =  content.split(",");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SlaReportGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SlaReportGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return headers;
    }
    
    
    private void createBodyCell(int j,Row row, CellStyle style, String content){
        Cell cell = row.createCell(j);
        cell.setCellValue(content+"");
        cell.setCellStyle(style);
    }

    private Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles= new HashMap();
        CellStyle style = wb.createCellStyle();
        Font headerFont = wb.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("header", style);

        style = wb.createCellStyle();
        headerFont = wb.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("body", style);
        
        style = wb.createCellStyle();
        headerFont = wb.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.ROSE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("nonCompliance", style);
        
         style = wb.createCellStyle();
        headerFont = wb.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("compliance", style);
        return styles;
    }

}
