package com.hp.sla.analyser.model;

import com.hp.sla.analyser.model.util.ExcelReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public void generateReport(String incidentFile, String auditFile, String d)
            throws SlaReportGenerationException {
        if(incidentFile.isEmpty() || auditFile.isEmpty()){
            throw new SlaReportGenerationException("Please select an incident file and an audit file");
        }
        ExcelReader incidentExcel=new ExcelReader();
        ExcelReader auditExcel=new ExcelReader();
        try {
            incidentExcel.setInputFile(new FileInputStream(new File(incidentFile)));
            auditExcel.setInputFile(new FileInputStream(new File(auditFile)));
            
            XSSFSheet incidentSheet = incidentExcel.read();
            XSSFSheet auditSheet = auditExcel.read();
            
            
            
        } catch (FileNotFoundException ex) {
            throw new SlaReportGenerationException(ex.getLocalizedMessage());
        } catch (IOException ioe){
            throw new SlaReportGenerationException(ioe.getLocalizedMessage());
        }

    }
   

}
