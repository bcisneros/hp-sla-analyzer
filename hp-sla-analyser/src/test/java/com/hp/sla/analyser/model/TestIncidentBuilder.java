package com.hp.sla.analyser.model;

import com.hp.sla.analyser.util.DateTimeUtil;
import java.text.DecimalFormat;
import org.apache.log4j.Logger;

/**
 * Utility class to ease the test incidents objects
 *
 * @author Benjamin Cisneros Barraza
 */
public class TestIncidentBuilder {

    private Incident incident;
    private final static Logger logger = Logger.getLogger(TestIncidentBuilder.class);
    private final static TestIncidentBuilder INSTANCE = null;

    public DecimalFormat FORMATTER = new DecimalFormat("IM0000000000");

    private TestIncidentBuilder() {
        incident = getDefaultIncident();
    }

    public static TestIncidentBuilder getInstance() {
        if (INSTANCE == null) {
            return new TestIncidentBuilder();
        }
        return INSTANCE;
    }

    public Incident build() {
        try {
            return (Incident) incident.clone();
        } catch (CloneNotSupportedException ex) {
            logger.error("Error cloning incident object", ex);
        }
        return null;
    }

    public TestIncidentBuilder id(String id) {
        incident.setId(id);
        return this;
    }

    public TestIncidentBuilder reset() {
        incident = getDefaultIncident();
        return this;
    }

    private Incident getDefaultIncident() {
        Incident defaultIncident = new Incident();
        defaultIncident.setId("IM00001");
        defaultIncident.setCreationTimestamp(DateTimeUtil.FIRST_DAY_2015_YEAR_TIMESTAMP);
        defaultIncident.setCloseTimestamp(DateTimeUtil.addHours(DateTimeUtil.FIRST_DAY_2015_YEAR_TIMESTAMP, 3.0d));
        defaultIncident.setTimeToFixDuration(3.0d);
        defaultIncident.setConfigurationItemLogicalName("hpit:whatever");
        defaultIncident.setCurrentStatusPhaseDescription("Closed");
        defaultIncident.setProductionActive(true);
        defaultIncident.setPriority(Incident.MEDIUM_PRIORITY);
        defaultIncident.setAgingDurationInDays(10);
        defaultIncident.setClosureCodeDescription("Closure Code Description");
        defaultIncident.setOpenedByEmailName("openby@test.com");
        defaultIncident.setAssigneeEmailName("assignee@test.com");
        defaultIncident.setAssigneeManagerEmailName("assigneemanager@test.com");
        defaultIncident.setAssigneeOrganizationUnitName("Assignee Organization Unit Name");
        defaultIncident.setCurrentAssignmentGroup("TEST-AG");
        defaultIncident.setCriticalityDescription(Incident.NORMAL_CRITICALITY);
        defaultIncident.setCurrentAssignmentGroupSupportLevelDescription("L3");
        defaultIncident.setClosedAssignmentGroupSupportLevelDescription("L3");
        defaultIncident.setConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel4Name("not avail");
        defaultIncident.setTitle("Test Incident");
        defaultIncident.setRelatedRootConfigurationItemApplicationPortfolioIdentifier(1234567890);
        defaultIncident.setRelatedRootConfigurationItemBusinessFriendlyName("Test Related Root Configuration Item Businnes");
        defaultIncident.setConfigurationItemStatus("active");
        defaultIncident.setConfigurationItemEnvironment("Development");
        defaultIncident.setCurrentStatus("Closed");
        defaultIncident.setConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel1Name("hpe-it");
        return defaultIncident;
    }

}
