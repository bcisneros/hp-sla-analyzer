package com.hp.sla.analyser.model.util;

import com.hp.sla.analyser.model.Incident;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;

/**
 *
 * @author ramirmal
 */
public class IncidentParser extends ExcelParser<Incident> {

    @Override
    public Incident createObject(Iterator<Cell> cellIterator) {
        Incident incident = new Incident();
        incident.setId(cellIterator.next().getStringCellValue());
        Date incidentCreatedDate = cellIterator.next().getDateCellValue();
        incident.setCreationTimestamp(new Timestamp(incidentCreatedDate.getTime()));
        Date incidentClosedDate = cellIterator.next().getDateCellValue();
        if (incidentClosedDate != null) {
            incident.setCloseTimestamp(new Timestamp(incidentClosedDate.getTime()));
        }
        incident.setTimeToFixDuration(cellIterator.next().getNumericCellValue());
        incident.setConfigurationItemLogicalName(cellIterator.next().getStringCellValue());
        incident.setCurrentStatusPhaseDescription(cellIterator.next().getStringCellValue());
        incident.setProductionActive("y".equals(cellIterator.next().getStringCellValue()));
        incident.setPriority(cellIterator.next().getStringCellValue());
        incident.setAgingDurationInDays(cellIterator.next().getNumericCellValue());
        incident.setClosureCodeDescription(cellIterator.next().getStringCellValue());
        incident.setOpenedByEmailName(cellIterator.next().getStringCellValue());
        incident.setAssigneeEmailName(cellIterator.next().getStringCellValue());
        incident.setAssigneeManagerEmailName(cellIterator.next().getStringCellValue());
        incident.setAssigneeOrganizationUnitName(cellIterator.next().getStringCellValue());
        incident.setCurrentAssignmentGroup(cellIterator.next().getStringCellValue());
        incident.setCriticalityDescription(cellIterator.next().getStringCellValue());
        incident.setCurrentAssignmentGroupSupportLevelDescription(cellIterator.next().getStringCellValue());
        incident.setClosedAssignmentGroupSupportLevelDescription(cellIterator.next().getStringCellValue());
        incident.setConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel4Name(cellIterator.next().getStringCellValue());
        incident.setTitle(cellIterator.next().getStringCellValue());
        incident.setRelatedRootConfigurationItemApplicationPortfolioIdentifier(Double.valueOf(cellIterator.next().getNumericCellValue()).longValue());
        incident.setRelatedRootConfigurationItemBusinessFriendlyName(cellIterator.next().getStringCellValue());
        incident.setConfigurationItemStatus(cellIterator.next().getStringCellValue());
        incident.setConfigurationItemEnvironment(cellIterator.next().getStringCellValue());
        incident.setCurrentStatus(cellIterator.next().getStringCellValue());
        incident.setConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel1Name(cellIterator.next().getStringCellValue());
        return incident;
    }

    @Override
    public String getStartDataIndicator() {
        return "Incident Identifier";
    }
}
