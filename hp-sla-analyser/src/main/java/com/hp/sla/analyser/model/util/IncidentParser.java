package com.hp.sla.analyser.model.util;

import com.hp.sla.analyser.model.Incident;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author ramirmal
 */
public class IncidentParser extends ExcelParser<Incident> {

    private static final Logger logger = Logger.getLogger(IncidentParser.class);

    @Override
    public List<Incident> parseDocument(Sheet sheet) {
        List<Incident> incidents = new ArrayList<>();
        // Get iterator to all the rows in current sheet 
        Iterator<Row> rowIterator = sheet.iterator();
        logger.info("Start parsing document.");
        boolean dataStart = false;
        // Traversing over each row of XLSX file 
        logger.info("Iterating the XLSX file.");
        int rowCount = 1;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator();
            logger.debug("Iterating the row " + rowCount++);
            Cell cell = cellIterator.next();
            if (cell != null && "Incident Identifier".equals(cell.getStringCellValue())) {
                dataStart = true;
                continue;
            }

            if (cell != null && "Grand Total".equals(cell.getStringCellValue())) {
                break;
            }
            if (dataStart) {
                Incident incident = new Incident();
                incident.setId(row.getCell(0).getStringCellValue());
                Date incidentCreatedDate = row.getCell(1).getDateCellValue();
                incident.setCreationTimestamp(new Timestamp(incidentCreatedDate.getTime()));
                Date incidentClosedDate = row.getCell(2).getDateCellValue();
                if (incidentClosedDate != null) {
                    incident.setCloseTimestamp(new Timestamp(incidentClosedDate.getTime()));
                }
                incident.setTimeToFixDuration(row.getCell(3).getNumericCellValue());
                incident.setConfigurationItemLogicalName(row.getCell(4).getStringCellValue());
                incident.setCurrentStatusPhaseDescription(row.getCell(5).getStringCellValue());
                incident.setProductionActive("y".equals(row.getCell(6).getStringCellValue()));
                incident.setPriority(row.getCell(7).getStringCellValue());
                incident.setAgingDurationInDays(row.getCell(8).getNumericCellValue());
                incident.setClosureCodeDescription(row.getCell(9).getStringCellValue());
                incident.setOpenedByEmailName(row.getCell(10).getStringCellValue());
                incident.setAssigneeEmailName(row.getCell(11).getStringCellValue());
                incident.setAssigneeManagerEmailName(row.getCell(12).getStringCellValue());
                incident.setAssigneeOrganizationUnitName(row.getCell(13).getStringCellValue());
                incident.setCurrentAssignmentGroup(row.getCell(14).getStringCellValue());
                incident.setCriticalityDescription(row.getCell(15).getStringCellValue());
                incident.setCurrentAssignmentGroupSupportLevelDescription(row.getCell(16).getStringCellValue());
                incident.setClosedAssignmentGroupSupportLevelDescription(row.getCell(17).getStringCellValue());
                incident.setConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel4Name(row.getCell(18).getStringCellValue());
                incident.setTitle(row.getCell(19).getStringCellValue());
                incident.setRelatedRootConfigurationItemApplicationPortfolioIdentifier(new Double(row.getCell(20).getNumericCellValue()).longValue());
                incident.setRelatedRootConfigurationItemBusinessFriendlyName(row.getCell(21).getStringCellValue());
                incident.setConfigurationItemStatus(row.getCell(22).getStringCellValue());
                incident.setConfigurationItemEnvironment(row.getCell(23).getStringCellValue());
                incident.setCurrentStatus(row.getCell(24).getStringCellValue());
                incident.setConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel1Name(row.getCell(25).getStringCellValue());
                incidents.add(incident);
            }

        }
        if (!dataStart) {
            throw new UnsupportedOperationException("Required columns not found"); //To change body of generated methods, choose Tools | Templates.
        }
        return incidents;
    }

}
