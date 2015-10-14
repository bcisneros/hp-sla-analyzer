package com.hp.sla.analyser.model;

import com.hp.sla.analyser.model.util.AuditComparator;
import com.hp.sla.analyser.model.util.AuditParser;
import com.hp.sla.analyser.model.util.ExcelReader;
import com.hp.sla.analyser.model.util.ExcelWritter;
import com.hp.sla.analyser.model.util.IncidentParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.RowSet;
import javax.sql.rowset.Predicate;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 *
 * @author Benjamin Cisneros Barraza
 */
public class SlaReportGenerator {
    private Object CollectionUtils;

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
            
            String[] titles={"", "", "", ""};
            ExcelWritter ew=new ExcelWritter();
            ew.write(titles, report, "SLAReport");
            

        } catch (FileNotFoundException ex) {
            throw new SlaReportGenerationException(ex.getLocalizedMessage());
        } catch (IOException ioe) {
            throw new SlaReportGenerationException(ioe.getLocalizedMessage());
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(SlaReportGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
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

}
