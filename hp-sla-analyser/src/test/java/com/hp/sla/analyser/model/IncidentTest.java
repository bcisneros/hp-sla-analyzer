package com.hp.sla.analyser.model;

import static com.hp.sla.analyser.model.AuditsBuilder.anAudit;
import static com.hp.sla.analyser.model.IncidentBuilder.anIncident;
import static com.hp.sla.analyser.util.DateTimeUtil.FIRST_DAY_2015_YEAR_TIMESTAMP;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.hp.sla.analyser.util.DateTimeUtil;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class IncidentTest {

	private static final double SEVENTY_FIVE_PERCENT = 0.75;
	private static final int SIX_DAYS = 144;
	private static final double SIX_HOURS = 6.0;
	private static final double FOUR_DAYS = 96.0;
	private static final double THREE_DAYS = 72.0;
	private static final double ONE_DAY = 24.0;
	private static final double EIGHT_HOURS = 8.0;
	private static final double THREE_HOURS = 3.0;

	@Test
	@Parameters(method = "getIncidentsForCalculateBurnedOutDate")
	public void shouldCalculateTheExpectedBurnedOutDate(IncidentTestCase testcase) throws Exception {
		assertEquals(testcase.getTimestamp(), anIncident().creationTime(FIRST_DAY_2015_YEAR_TIMESTAMP).build()
				.calculateBurnedOutDate(testcase.getLevelAgreement()));
	}

	@Test(expected = SlaAnalysisException.class)
	@Parameters(method = "getServiceLevelAgreementsWithNullValues")
	public void shouldThrowAnExceptionWhenBurnedOutDateCouldNotBeCalculated(ServiceLevelAgreement sla)
			throws SlaAnalysisException {
		new Incident().calculateBurnedOutDate(sla);
	}

	@Test
	@Parameters(method = "getIncidentsForCalculateTimeToFixDeadLine")
	public void shouldCalculateTheExpectedTimeToFixDeadLine(IncidentTestCase testcase) throws Exception {
		assertEquals(testcase.getTimestamp(), anIncident().creationTime(FIRST_DAY_2015_YEAR_TIMESTAMP).build()
				.calculateTimeToFixDeadLine(testcase.getLevelAgreement()));
	}

	@Test
	public void shouldResolveWhatItsTheLastAuditByAssignmentGroup() {
		final String FIRST_ASSIGNMENT_GROUP = "A";
		final String SECOND_ASSIGNMENT_GROUP = "B";
		final String THIRD_ASSIGNMENT_GROUP = "C";
		final String NOT_IN_LIST_ASSIGNMENT_GROUP = "X";

		Audit audit1 = anAudit().currentAssignmentGroup(FIRST_ASSIGNMENT_GROUP)
				.systemTime(Timestamp.valueOf("2015-01-01 00:00:00")).build();
		Audit audit2 = anAudit().currentAssignmentGroup(SECOND_ASSIGNMENT_GROUP)
				.systemTime(Timestamp.valueOf("2015-01-15 00:00:00")).build();
		Audit audit3 = anAudit().currentAssignmentGroup(NOT_IN_LIST_ASSIGNMENT_GROUP)
				.systemTime(Timestamp.valueOf("2015-01-29 00:00:00")).build();
		Incident incident = anIncident().withAudits(audit1, audit2, audit3).build();
		String[] assignmentGroups = { FIRST_ASSIGNMENT_GROUP, SECOND_ASSIGNMENT_GROUP, THIRD_ASSIGNMENT_GROUP };
		incident.searchAndSetLastAssignmentGroupAudit(new ArrayList<>(Arrays.asList(assignmentGroups)));
		assertNotNull("The last asssignment group audit must be not null", incident.getLastAssignmentGroupAudit());
		assertSame("The last asssignment group audit must be the same object as Audit 2",
				incident.getLastAssignmentGroupAudit(), audit1);
	}

	@Test
	public void shouldCloneItself() throws Exception {
		Incident incident = new Incident();
		assertEquals(incident, incident.clone());
		assertNotSame(incident, incident.clone());
	}

	@SuppressWarnings("unused")
	private List<ServiceLevelAgreement> getServiceLevelAgreementsWithNullValues() {
		List<ServiceLevelAgreement> list = new ArrayList<>();
		list.add(ServiceLevelAgreement.HP_IT_NORMAL_TOP);
		list.add(ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_TOP);
		list.add(ServiceLevelAgreement.HP_IT_NORMAL_HIGH);
		list.add(ServiceLevelAgreement.HPI_IT_NORMAL_TOP);
		list.add(ServiceLevelAgreement.HPI_IT_ENTITY_ESSENTIAL_TOP);
		list.add(ServiceLevelAgreement.HPI_IT_NORMAL_HIGH);
		list.add(ServiceLevelAgreement.HPE_IT_NORMAL_TOP);
		list.add(ServiceLevelAgreement.HPE_IT_ENTITY_ESSENTIAL_TOP);
		list.add(ServiceLevelAgreement.HPE_IT_NORMAL_HIGH);
		return list;
	}

	@SuppressWarnings("unused")
	private static List<IncidentTestCase> getIncidentsForCalculateTimeToFixDeadLine() {
		List<IncidentTestCase> testCases = new ArrayList<>();
		testCases.addAll(hpIncidentsForTimeToFix());
		testCases.addAll(hpiIncidentsForTimeToFix());
		testCases.addAll(hpeIncidentsForTimeToFix());
		return testCases;
	}

	@SuppressWarnings("unused")
	private static List<IncidentTestCase> getIncidentsForCalculateBurnedOutDate() {
		List<IncidentTestCase> testCases = new ArrayList<>();
		testCases.addAll(hpIncidentsForBurnedOutDate());
		testCases.addAll(hpiIncidentsForBurnedOutDate());
		testCases.addAll(hpeIncidentsForBurnedOutDate());
		return testCases;
	}

	private static List<IncidentTestCase> hpeIncidentsForBurnedOutDate() {
		List<IncidentTestCase> testCases = new ArrayList<>();
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_TOP, addHours(THREE_HOURS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_HIGH, addHours(SIX_HOURS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_MEDIUM,
				addHours(THREE_DAYS * SEVENTY_FIVE_PERCENT)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_LOW,
				addHours(FOUR_DAYS * SEVENTY_FIVE_PERCENT)));

		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPE_IT_ENTITY_ESSENTIAL_HIGH, addHours(SIX_HOURS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPE_IT_ENTITY_ESSENTIAL_MEDIUM,
				addHours(THREE_DAYS * SEVENTY_FIVE_PERCENT)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPE_IT_ENTITY_ESSENTIAL_LOW,
				addHours(FOUR_DAYS * SEVENTY_FIVE_PERCENT)));

		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPE_IT_NORMAL_MEDIUM,
				addHours(FOUR_DAYS * SEVENTY_FIVE_PERCENT)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPE_IT_NORMAL_LOW,
				addHours(SIX_DAYS * SEVENTY_FIVE_PERCENT)));
		return testCases;
	}

	private static List<IncidentTestCase> hpiIncidentsForBurnedOutDate() {
		List<IncidentTestCase> testCases = new ArrayList<>();
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_TOP,
				addHours(EIGHT_HOURS * SEVENTY_FIVE_PERCENT)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_HIGH,
				addHours(ONE_DAY * SEVENTY_FIVE_PERCENT)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_MEDIUM,
				addHours(THREE_DAYS * SEVENTY_FIVE_PERCENT)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_LOW,
				addHours(FOUR_DAYS * SEVENTY_FIVE_PERCENT)));

		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPI_IT_ENTITY_ESSENTIAL_HIGH,
				addHours(ONE_DAY * SEVENTY_FIVE_PERCENT)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPI_IT_ENTITY_ESSENTIAL_MEDIUM,
				addHours(THREE_DAYS * SEVENTY_FIVE_PERCENT)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPI_IT_ENTITY_ESSENTIAL_LOW,
				addHours(FOUR_DAYS * SEVENTY_FIVE_PERCENT)));

		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPI_IT_NORMAL_MEDIUM,
				addHours(THREE_DAYS * SEVENTY_FIVE_PERCENT)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPI_IT_NORMAL_LOW,
				addHours(FOUR_DAYS * SEVENTY_FIVE_PERCENT)));
		return testCases;
	}

	private static List<IncidentTestCase> hpIncidentsForBurnedOutDate() {
		List<IncidentTestCase> testCases = new ArrayList<>();
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_TOP, addHours(THREE_HOURS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_HIGH, addHours(SIX_HOURS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_MEDIUM,
				addHours(THREE_DAYS * SEVENTY_FIVE_PERCENT)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_LOW,
				addHours(FOUR_DAYS * SEVENTY_FIVE_PERCENT)));

		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_HIGH, addHours(SIX_HOURS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_MEDIUM,
				addHours(THREE_DAYS * SEVENTY_FIVE_PERCENT)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_LOW,
				addHours(FOUR_DAYS * SEVENTY_FIVE_PERCENT)));

		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HP_IT_NORMAL_MEDIUM,
				addHours(FOUR_DAYS * SEVENTY_FIVE_PERCENT)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HP_IT_NORMAL_LOW,
				addHours(SIX_DAYS * SEVENTY_FIVE_PERCENT)));
		return testCases;
	}

	private static List<IncidentTestCase> hpIncidentsForTimeToFix() {
		List<IncidentTestCase> testCases = new ArrayList<>();
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_TOP, addHours(THREE_HOURS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_HIGH, addHours(SIX_HOURS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_MEDIUM, addHours(THREE_DAYS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_LOW, addHours(FOUR_DAYS)));

		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_HIGH, addHours(SIX_HOURS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_MEDIUM, addHours(THREE_DAYS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_LOW, addHours(FOUR_DAYS)));

		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HP_IT_NORMAL_MEDIUM, addHours(FOUR_DAYS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HP_IT_NORMAL_LOW, addHours(SIX_DAYS)));
		return testCases;
	}

	private static List<IncidentTestCase> hpiIncidentsForTimeToFix() {
		List<IncidentTestCase> testCases = new ArrayList<>();
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_TOP, addHours(EIGHT_HOURS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_HIGH, addHours(ONE_DAY)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_MEDIUM, addHours(THREE_DAYS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_LOW, addHours(FOUR_DAYS)));

		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPI_IT_ENTITY_ESSENTIAL_HIGH, addHours(ONE_DAY)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPI_IT_ENTITY_ESSENTIAL_MEDIUM, addHours(THREE_DAYS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPI_IT_ENTITY_ESSENTIAL_LOW, addHours(FOUR_DAYS)));

		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPI_IT_NORMAL_MEDIUM, addHours(THREE_DAYS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPI_IT_NORMAL_LOW, addHours(FOUR_DAYS)));
		return testCases;
	}

	private static List<IncidentTestCase> hpeIncidentsForTimeToFix() {
		List<IncidentTestCase> testCases = new ArrayList<>();
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_TOP, addHours(THREE_HOURS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_HIGH, addHours(SIX_HOURS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_MEDIUM, addHours(THREE_DAYS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_LOW, addHours(FOUR_DAYS)));

		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPE_IT_ENTITY_ESSENTIAL_HIGH, addHours(SIX_HOURS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPE_IT_ENTITY_ESSENTIAL_MEDIUM, addHours(THREE_DAYS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPE_IT_ENTITY_ESSENTIAL_LOW, addHours(FOUR_DAYS)));

		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPE_IT_NORMAL_MEDIUM, addHours(FOUR_DAYS)));
		testCases.add(new IncidentTestCase(ServiceLevelAgreement.HPE_IT_NORMAL_LOW, addHours(SIX_DAYS)));
		return testCases;
	}

	private static Timestamp addHours(double addedHours) {
		return DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, addedHours);
	}

	static class IncidentTestCase {
		ServiceLevelAgreement levelAgreement;
		Timestamp timestamp;

		public IncidentTestCase(ServiceLevelAgreement levelAgreement, Timestamp timestamp) {
			super();
			this.levelAgreement = levelAgreement;
			this.timestamp = timestamp;
		}

		public ServiceLevelAgreement getLevelAgreement() {
			return levelAgreement;
		}

		public void setLevelAgreement(ServiceLevelAgreement levelAgreement) {
			this.levelAgreement = levelAgreement;
		}

		public Timestamp getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(Timestamp timestamp) {
			this.timestamp = timestamp;
		}

		@Override
		public String toString() {
			return "IncidentTestCase [levelAgreement=" + levelAgreement + ", timestamp=" + timestamp + "]";
		}

	}

}
