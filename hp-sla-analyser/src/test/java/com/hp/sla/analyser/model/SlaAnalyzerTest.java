package com.hp.sla.analyser.model;

import com.hp.sla.analyser.model.util.Criticality;
import com.hp.sla.analyser.model.util.IncidentParser;
import com.hp.sla.analyser.model.util.OrganizationLevel1Name;
import com.hp.sla.analyser.model.util.Priority;
import com.hp.sla.analyser.util.DateTimeUtil;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.log4j.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Benjamin Cisneros Barraza
 */
@RunWith(JUnitParamsRunner.class)
public class SlaAnalyzerTest {

	private SlaAnalyzer instance;
	protected static final Logger logger = Logger.getLogger(IncidentParser.class);
	
	private static final AtomicInteger sequence = new AtomicInteger();

	@Before
	public void setUp() {
		instance = new SlaAnalyzer();
	}

	/**
	 * Test of analyze method, of class SlaAnalyzer.
	 */
	@Test
	public void testAnalyze() {
		List<Incident> incidentsToAnalize = new ArrayList<>();
		incidentsToAnalize.addAll(getCompliantIncidents());
		incidentsToAnalize.addAll(getNonCompliantIncidents());
		incidentsToAnalize.addAll(getBurnedOutIncidents());
		incidentsToAnalize.addAll(getNonBurnedOutIncidents());
		final List<ReportDetail> result = instance.analyze(incidentsToAnalize);
		assertNotNull("The resulting analized list must be not null", result);
		assertEquals("The size of the resulting list must be the equals to the input incident list",
				incidentsToAnalize.size(), result.size());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testAnalyzeWithNullIncidentList() {
		instance.analyze(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAnalyzeWithEmptyIncidentList() {
		instance.analyze(new ArrayList<Incident>());
	}

	/**
	 * Test of getAssignmentGroupsListToAnalize method, of class SlaAnalyzer.
	 */
	@Test
	public void testGetAssignmentGroupsListToAnalize() {
		List<String> result = instance.getAssignmentGroupsListToAnalize();
		assertNotNull("The result list must be not null", result);
		assertFalse("The result list must be not empty", result.isEmpty());
	}

	@Test
	@Parameters(method = "getCompliantIncidents")
	public void testAnalizeIncidentWithCompliantIncidents(Incident incident) {
		try {
			assertTrue("This incident must be compliant with the SLA",
					instance.analizeIncident(incident).isCompliantWithSLA());
		} catch (SlaAnalysisException ex) {
			fail("No exception was expected: " + ex);
		}
	}

	@Test
	@Parameters(method = "getNonCompliantIncidents")
	public void testAnalizeIncidentWithNonCompliantIncidents(Incident incident) {
		try {
			assertFalse("This incident must not be compliant with the SLA",
					instance.analizeIncident(incident).isCompliantWithSLA());
		} catch (SlaAnalysisException ex) {
			fail("No exception was expected: " + ex);
		}
	}

	@Test
	@Parameters(method = "getBurnedOutIncidents")
	public void testAnalizeIncidentWithBurnedOutIncidents(Incident incident) {
		try {
			assertTrue("This incident must be burned out", instance.analizeIncident(incident).isBurnedOut());
		} catch (SlaAnalysisException ex) {
			fail("No exception was expected: " + ex);
		}
	}

	@Test
	@Parameters(method = "getNonBurnedOutIncidents")
	public void testAnalizeIncidentWithNonBurnedOutIncidents(Incident incident) {
		try {
			assertFalse("This incident must be not burned out", instance.analizeIncident(incident).isBurnedOut());
		} catch (SlaAnalysisException ex) {
			fail("No exception was expected: " + ex);
		}
	}

	@Test
	public void testAnalizeIncidentWithNullValuesOnNotRequiredFields() {
		Incident incident = getCompliantIncidents().get(0);
		incident.setCloseTimestamp(null);
		try {
			ReportDetail result = instance.analizeIncident(incident);
			assertNotNull("The object must be not null", result);
		} catch (Exception exception) {
			fail("This test must not throw an exception: " + exception);
		}
	}

	@Test(expected = SlaAnalysisException.class)
	public void testAnalizeIncidentWithNullServiceLevelAgreement() throws SlaAnalysisException {
		Incident incident = getCompliantIncidents().get(0);
		incident.setCriticalityDescription(Criticality.ENTITY_ESSENTIAL.getName());
		incident.setPriority(Priority.TOP.getName());
		instance.analizeIncident(incident);
	}

	@Test
	@Parameters(method = "getExpectedServiceLevelAgreementByIncident")
	public void testGetServiceLevelAgreementByIncident(Incident incident,
			ServiceLevelAgreement expectedServiceLevelAgreement) throws Exception {
		assertSame(expectedServiceLevelAgreement, SlaAnalyzer.getServiceLevelAgreementByIncident(incident));
	}

	@Test(expected = SlaAnalysisException.class)
	public void testGetServiceLevelAgreementByIncidentWithNullCriticality() throws SlaAnalysisException {
		Incident incident = new Incident();
		incident.setCriticalityDescription(null);
		SlaAnalyzer.getServiceLevelAgreementByIncident(incident);
	}

	@Test(expected = SlaAnalysisException.class)
	public void testGetServiceLevelAgreementByIncidentWithNullPriority() throws Exception {
		Incident incident = new Incident();
		incident.setCriticalityDescription("Mission Critical");
		incident.setPriority(null);
		SlaAnalyzer.getServiceLevelAgreementByIncident(incident);
	}

	@Test(expected = SlaAnalysisException.class)
	public void testGetServiceLevelAgreementByIncidentWithNotValidCriticality() throws Exception {
		Incident incident = new Incident();
		incident.setCriticalityDescription("An invalid criticality value");
		SlaAnalyzer.getServiceLevelAgreementByIncident(incident);
	}

	@Test(expected = SlaAnalysisException.class)
	public void testGetServiceLevelAgreementByIncidentWithNotValidPriority() throws Exception {
		Incident incident = new Incident();
		incident.setCriticalityDescription("Mission Critical");
		incident.setPriority("An invalid priority");
		SlaAnalyzer.getServiceLevelAgreementByIncident(incident);
	}

	protected static List<Incident> getCompliantIncidents() {
		return testIncidentValues(true, true);
	}

	public static List<Incident> getNonCompliantIncidents() {
		return testIncidentValues(false, false);
	}

	public static List<Incident> getBurnedOutIncidents() {
		return testIncidentValues(true, false);
	}

	public static List<Incident> getNonBurnedOutIncidents() {
		return testIncidentValues(false, true);
	}

	public static List<Incident> getIncidentsWithNullServiceLevelAgreement() {
		List<Incident> incidents = new ArrayList<>();
		incidents.add(generateIncident(null, null, true, true, null));
		incidents.add(
				generateIncident(Criticality.ENTITY_ESSENTIAL.getName(), Priority.TOP.getName(), true, true, null));
		incidents.add(generateIncident(Criticality.NORMAL.getName(), Priority.TOP.getName(), true, true, null));
		incidents.add(generateIncident(Criticality.NORMAL.getName(), Priority.HIGH.getName(), true, true, null));
		return incidents;
	}

	public static Collection<Object[]> getExpectedServiceLevelAgreementByIncident() {
		return Arrays.asList(new Object[][] {
				// HP Parameters
				{ new Incident(OrganizationLevel1Name.HP_IT.getName(), Criticality.MISSION_CRITICAL.getName(),
						Priority.TOP.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_TOP },
				{ new Incident(OrganizationLevel1Name.HP_IT.getName(), Criticality.MISSION_CRITICAL.getName(),
						Priority.HIGH.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_HIGH },
				{ new Incident(OrganizationLevel1Name.HP_IT.getName(), Criticality.MISSION_CRITICAL.getName(),
						Priority.MEDIUM.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_MEDIUM },
				{ new Incident(OrganizationLevel1Name.HP_IT.getName(), Criticality.MISSION_CRITICAL.getName(),
						Priority.LOW.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_LOW },
				{ new Incident(OrganizationLevel1Name.HP_IT.getName(), Criticality.ENTITY_ESSENTIAL.getName(),
						Priority.TOP.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_TOP },
				{ new Incident(OrganizationLevel1Name.HP_IT.getName(), Criticality.ENTITY_ESSENTIAL.getName(),
						Priority.HIGH.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_HIGH },
				{ new Incident(OrganizationLevel1Name.HP_IT.getName(), Criticality.ENTITY_ESSENTIAL.getName(),
						Priority.MEDIUM.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_MEDIUM },
				{ new Incident(OrganizationLevel1Name.HP_IT.getName(), Criticality.ENTITY_ESSENTIAL.getName(),
						Priority.LOW.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_LOW },
				{ new Incident(OrganizationLevel1Name.HP_IT.getName(), Criticality.NORMAL.getName(),
						Priority.TOP.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HP_IT_NORMAL_TOP },
				{ new Incident(OrganizationLevel1Name.HP_IT.getName(), Criticality.NORMAL.getName(),
						Priority.HIGH.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HP_IT_NORMAL_HIGH },
				{ new Incident(OrganizationLevel1Name.HP_IT.getName(), Criticality.NORMAL.getName(),
						Priority.MEDIUM.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HP_IT_NORMAL_MEDIUM },
				{ new Incident(OrganizationLevel1Name.HP_IT.getName(), Criticality.NORMAL.getName(),
						Priority.LOW.getName()), (ServiceLevelAgreement) ServiceLevelAgreement.HP_IT_NORMAL_LOW },
				// HPI Parameters
				{ new Incident(OrganizationLevel1Name.HPI_IT.getName(), Criticality.MISSION_CRITICAL.getName(),
						Priority.TOP.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_TOP },
				{ new Incident(OrganizationLevel1Name.HPI_IT.getName(), Criticality.MISSION_CRITICAL.getName(),
						Priority.HIGH.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_HIGH },
				{ new Incident(OrganizationLevel1Name.HPI_IT.getName(), Criticality.MISSION_CRITICAL.getName(),
						Priority.MEDIUM.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_MEDIUM },
				{ new Incident(OrganizationLevel1Name.HPI_IT.getName(), Criticality.MISSION_CRITICAL.getName(),
						Priority.LOW.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_LOW },
				{ new Incident(OrganizationLevel1Name.HPI_IT.getName(), Criticality.ENTITY_ESSENTIAL.getName(),
						Priority.TOP.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPI_IT_ENTITY_ESSENTIAL_TOP },
				{ new Incident(OrganizationLevel1Name.HPI_IT.getName(), Criticality.ENTITY_ESSENTIAL.getName(),
						Priority.HIGH.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPI_IT_ENTITY_ESSENTIAL_HIGH },
				{ new Incident(OrganizationLevel1Name.HPI_IT.getName(), Criticality.ENTITY_ESSENTIAL.getName(),
						Priority.MEDIUM.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPI_IT_ENTITY_ESSENTIAL_MEDIUM },
				{ new Incident(OrganizationLevel1Name.HPI_IT.getName(), Criticality.ENTITY_ESSENTIAL.getName(),
						Priority.LOW.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPI_IT_ENTITY_ESSENTIAL_LOW },
				{ new Incident(OrganizationLevel1Name.HPI_IT.getName(), Criticality.NORMAL.getName(),
						Priority.TOP.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPI_IT_NORMAL_TOP },
				{ new Incident(OrganizationLevel1Name.HPI_IT.getName(), Criticality.NORMAL.getName(),
						Priority.HIGH.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPI_IT_NORMAL_HIGH },
				{ new Incident(OrganizationLevel1Name.HPI_IT.getName(), Criticality.NORMAL.getName(),
						Priority.MEDIUM.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPI_IT_NORMAL_MEDIUM },
				{ new Incident(OrganizationLevel1Name.HPI_IT.getName(), Criticality.NORMAL.getName(),
						Priority.LOW.getName()), (ServiceLevelAgreement) ServiceLevelAgreement.HPI_IT_NORMAL_LOW },
				// HPE Parameters
				{ new Incident(OrganizationLevel1Name.HPE_IT.getName(), Criticality.MISSION_CRITICAL.getName(),
						Priority.TOP.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_TOP },
				{ new Incident(OrganizationLevel1Name.HPE_IT.getName(), Criticality.MISSION_CRITICAL.getName(),
						Priority.HIGH.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_HIGH },
				{ new Incident(OrganizationLevel1Name.HPE_IT.getName(), Criticality.MISSION_CRITICAL.getName(),
						Priority.MEDIUM.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_MEDIUM },
				{ new Incident(OrganizationLevel1Name.HPE_IT.getName(), Criticality.MISSION_CRITICAL.getName(),
						Priority.LOW.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_LOW },
				{ new Incident(OrganizationLevel1Name.HPE_IT.getName(), Criticality.ENTITY_ESSENTIAL.getName(),
						Priority.TOP.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPE_IT_ENTITY_ESSENTIAL_TOP },
				{ new Incident(OrganizationLevel1Name.HPE_IT.getName(), Criticality.ENTITY_ESSENTIAL.getName(),
						Priority.HIGH.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPE_IT_ENTITY_ESSENTIAL_HIGH },
				{ new Incident(OrganizationLevel1Name.HPE_IT.getName(), Criticality.ENTITY_ESSENTIAL.getName(),
						Priority.MEDIUM.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPE_IT_ENTITY_ESSENTIAL_MEDIUM },
				{ new Incident(OrganizationLevel1Name.HPE_IT.getName(), Criticality.ENTITY_ESSENTIAL.getName(),
						Priority.LOW.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPE_IT_ENTITY_ESSENTIAL_LOW },
				{ new Incident(OrganizationLevel1Name.HPE_IT.getName(), Criticality.NORMAL.getName(),
						Priority.TOP.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPE_IT_NORMAL_TOP },
				{ new Incident(OrganizationLevel1Name.HPE_IT.getName(), Criticality.NORMAL.getName(),
						Priority.HIGH.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPE_IT_NORMAL_HIGH },
				{ new Incident(OrganizationLevel1Name.HPE_IT.getName(), Criticality.NORMAL.getName(),
						Priority.MEDIUM.getName()),
						(ServiceLevelAgreement) ServiceLevelAgreement.HPE_IT_NORMAL_MEDIUM },
				{ new Incident(OrganizationLevel1Name.HPE_IT.getName(), Criticality.NORMAL.getName(),
						Priority.LOW.getName()), (ServiceLevelAgreement) ServiceLevelAgreement.HPE_IT_NORMAL_LOW }, });
	}

	/**
	 * Obtains a Collection of incident cases that has a certain value of
	 * burnedOut and compliant
	 *
	 * @param burnedOut
	 *            will the incidents be burned out
	 * @param compliant
	 *            will the incidents be compliant with sla
	 * @return a collection of objects in the form {incident, burnedOut,
	 *         compliant}
	 * @throws SlaAnalysisException
	 */
	private static List<Incident> testIncidentValues(boolean burnedOut, boolean compliant) {
		String[] organizationLevel1Name = { OrganizationLevel1Name.HP_IT.getName(),
				OrganizationLevel1Name.HPI_IT.getName(), OrganizationLevel1Name.HPE_IT.getName() };
		String[] priorities = { Priority.TOP.getName(), Priority.HIGH.getName(), Priority.MEDIUM.getName(),
				Priority.LOW.getName() };
		String[] criticalities = { Criticality.MISSION_CRITICAL.getName(), Criticality.ENTITY_ESSENTIAL.getName(),
				Criticality.NORMAL.getName() };
		List<Incident> values = new ArrayList<Incident>();
		for (String organizationLevel1Name1 : organizationLevel1Name) {
			for (int j = 0; j < priorities.length; j++) {
				for (int k = 0; k <= j && k < criticalities.length; k++) {
					Incident incident = generateIncident(criticalities[k], priorities[j], burnedOut, compliant,
							organizationLevel1Name1);
					if (incident.getCloseTimestamp() != null) {
						values.add(incident);
					}
				}
			}
		}
		return values;
	}

	/**
	 * getCompliantWithSLAIncident
	 *
	 * @param criticality
	 *            the criticality of the incident
	 * @param priority
	 *            the priority of the incident
	 * @param burnedOut
	 *            will the incident be burned out
	 * @param compliant
	 *            will the incident be compliant
	 * @return an Incident with the values input in the parameters
	 * @throws SlaAnalysisException
	 */
	private static Incident generateIncident(String criticality, String priority, boolean burnedOut, boolean compliant,
			String organizationLevel1Name) {
		
		int timeBurnedOut;
		if (burnedOut) {
			timeBurnedOut = 1;
		} else {
			timeBurnedOut = -1;
		}
		int timeCompliant;
		if (compliant) {
			timeCompliant = -1;
		} else {
			timeCompliant = 1;
		}

		String generatedId = "IM00" + sequence.incrementAndGet();
		
		Incident incident = new Incident();
		incident.setCriticalityDescription(criticality);
		incident.setPriority(priority);
		incident.setCreationTimestamp(Timestamp.valueOf("2015-01-01 00:00:00.00"));
		
		ServiceLevelAgreement sla = null;
		try {
			sla = SlaAnalyzer.getServiceLevelAgreement(organizationLevel1Name, criticality, priority);
		} catch (SlaAnalysisException ex) {
			logger.info("Skipping this error", ex);
			return IncidentBuilder.anIncident().id(generatedId).build();
		}
		
		Timestamp burnedOutTimestamp = null;
		Timestamp complianceLimitTimestamp = null;
		try {
			burnedOutTimestamp = incident.calculateBurnedOutDate(sla);
			complianceLimitTimestamp = incident.calculateTimeToFixDeadLine(sla);
		} catch (SlaAnalysisException ex) {
			fail("No exception is expected: " + ex);
		}
		double compliantTimeAdded = timeCompliant * 1.0 / 60;
		double burnedOutTimeAdded = timeBurnedOut * 1.0 / 60;
		
		Timestamp closeTime = DateTimeUtil.addHours(complianceLimitTimestamp, compliantTimeAdded);
		Timestamp systemModifiedTime = DateTimeUtil.addHours(incident.getCreationTimestamp(), 0.5);
		Timestamp burnedTime = DateTimeUtil.addHours(burnedOutTimestamp, burnedOutTimeAdded);

		Incident generatedIncident = IncidentBuilder.anIncident().id(generatedId).organization(organizationLevel1Name)
				.criticality(criticality).priority(priority).creationTime(DateTimeUtil.FIRST_DAY_2015_YEAR_TIMESTAMP)
				.closeTime(closeTime)
				.withAudits(
						AuditsBuilder.anAudit().currentAssignmentGroup("ANOTHER-NON-APLYABLE-AG").systemTime(systemModifiedTime).build(),
						AuditsBuilder.anAudit().currentAssignmentGroup("W-INCLV4-FAIT-CTE").systemTime(burnedTime).build())
				.build();

		return generatedIncident;
	}
	

}
