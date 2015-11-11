package com.hp.sla.analyser.model;

import com.hp.sla.analyser.util.DateTimeUtil;
import static com.hp.sla.analyser.util.DateTimeUtil.FIRST_DAY_2015_YEAR_TIMESTAMP;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.log4j.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author cisnerob
 */
@RunWith(JUnitParamsRunner.class)
public class IncidentTest {

    private final static Logger logger = Logger.getLogger(SlaReportGenerator.class);

    /**
     * Test of calculateBurnedOutDate method, of class Incident.
     *
     * @param sla
     * @param incident
     * @param expectedTimeStamp
     */
    @Test
    @Parameters(method = "getIncidentsForCalculateBurnedOutDate")
    public void testCalculateBurnedOutDate(ServiceLevelAgreement sla, Incident incident, Timestamp expectedTimeStamp) {
        try {
            assertEquals(expectedTimeStamp, incident.calculateBurnedOutDate(sla));
        } catch (SlaAnalysisException ex) {
            fail("No exception is expected: " + ex);
        }
    }

    @Test(expected = SlaAnalysisException.class)
    @Parameters(method = "getServiceLevelAgreementsWithNullValues")
    public void testCalulateBurnedOutDateWithNullBurnedOutValue(ServiceLevelAgreement sla) throws SlaAnalysisException {
        new Incident().calculateBurnedOutDate(sla);
    }

    /**
     * Test of calculateTimeToFixDeadLine method, of class Incident.
     *
     * @param sla
     * @param incident
     * @param expectedTimeStamp
     */
    @Test
    @Parameters(method = "getIncidentsForCalculateTimeToFixDeadLine")
    public void testCalculateTimeToFixDeadLine(ServiceLevelAgreement sla, Incident incident, Timestamp expectedTimeStamp) {
        try {
            assertEquals(expectedTimeStamp, incident.calculateTimeToFixDeadLine(sla));
        } catch (SlaAnalysisException ex) {
            fail("No exception is expected: " + ex);
        }
    }

    @Test
    public void testSearchAndSetLastAssignmentGroupAudit() {
        Incident incident = new Incident();
        List<Audit> audits = new ArrayList<>();

        Audit audit1 = new Audit();
        audit1.setNewVaueText("A");
        audit1.setSystemModifiedTime(Timestamp.valueOf("2015-01-01 00:00:00"));

        Audit audit2 = new Audit();
        audit2.setNewVaueText("B");
        audit2.setSystemModifiedTime(Timestamp.valueOf("2015-01-15 00:00:00"));

        Audit audit3 = new Audit();
        audit3.setNewVaueText("X");
        audit3.setSystemModifiedTime(Timestamp.valueOf("2015-01-29 00:00:00"));

        audits.add(audit1);
        audits.add(audit2);

        incident.setAudits(audits);

        List<String> assignmentGroups;
        assignmentGroups = new ArrayList<>();
        assignmentGroups.add("A");
        assignmentGroups.add("B");
        assignmentGroups.add("C");

        incident.searchAndSetLastAssignmentGroupAudit(assignmentGroups);

        assertNotNull("The last asssignment group audit must be not null", incident.getLastAssignmentGroupAudit());
        assertSame("The last asssignment group audit must be the same object as Audit 2", incident.getLastAssignmentGroupAudit(), audit1);
    }

    @Test
    public void testClone() {
        try {
            Incident incident = new Incident();
            assertEquals(incident, incident.clone());
            assertNotSame(incident, incident.clone());
        } catch (CloneNotSupportedException ex) {
            fail("No exception was expected: " + ex);
        }
    }

    protected List<ServiceLevelAgreement> getServiceLevelAgreementsWithNullValues() {
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

    public static Collection<Object[]> getIncidentsForCalculateBurnedOutDate() {
        Incident incident = new Incident();
        incident.setCreationTimestamp(FIRST_DAY_2015_YEAR_TIMESTAMP);
        return Arrays.asList(new Object[][]{
            //HP-IT
            {ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_TOP, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 3.0)},
            {ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_HIGH, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 6.0)},
            {ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_MEDIUM, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 72 * 0.75)},
            {ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_LOW, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 96 * 0.75)},
            {ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_HIGH, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 6.0)},
            {ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_MEDIUM, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 72 * 0.75)},
            {ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_LOW, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 96 * 0.75)},
            {ServiceLevelAgreement.HP_IT_NORMAL_MEDIUM, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 96 * 0.75)},
            {ServiceLevelAgreement.HP_IT_NORMAL_LOW, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 144 * 0.75)},
            //HPI-IT
            {ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_TOP, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 8 * 0.75)},
            {ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_HIGH, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 24 * 0.75)},
            {ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_MEDIUM, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 72 * 0.75)},
            {ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_LOW, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 96 * 0.75)},
            {ServiceLevelAgreement.HPI_IT_ENTITY_ESSENTIAL_HIGH, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 24.0 * 0.75)},
            {ServiceLevelAgreement.HPI_IT_ENTITY_ESSENTIAL_MEDIUM, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 72 * 0.75)},
            {ServiceLevelAgreement.HPI_IT_ENTITY_ESSENTIAL_LOW, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 96 * 0.75)},
            {ServiceLevelAgreement.HPI_IT_NORMAL_MEDIUM, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 72 * 0.75)},
            {ServiceLevelAgreement.HPI_IT_NORMAL_LOW, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 96 * 0.75)},
            //HPE-IT (Same values as HP-IT)
            {ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_TOP, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 3.0)},
            {ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_HIGH, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 6.0)},
            {ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_MEDIUM, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 72 * 0.75)},
            {ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_LOW, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 96 * 0.75)},
            {ServiceLevelAgreement.HPE_IT_ENTITY_ESSENTIAL_HIGH, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 6.0)},
            {ServiceLevelAgreement.HPE_IT_ENTITY_ESSENTIAL_MEDIUM, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 72 * 0.75)},
            {ServiceLevelAgreement.HPE_IT_ENTITY_ESSENTIAL_LOW, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 96 * 0.75)},
            {ServiceLevelAgreement.HPE_IT_NORMAL_MEDIUM, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 96 * 0.75)},
            {ServiceLevelAgreement.HPE_IT_NORMAL_LOW, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 144 * 0.75)},});

    }

    public static Collection<Object[]> getIncidentsForCalculateTimeToFixDeadLine() {
        Incident incident = new Incident();
        incident.setCreationTimestamp(FIRST_DAY_2015_YEAR_TIMESTAMP);
        return Arrays.asList(new Object[][]{
            //HP-IT
            {ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_TOP, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 3.0)},
            {ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_HIGH, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 6.0)},
            {ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_MEDIUM, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 72)},
            {ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_LOW, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 96)},
            {ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_HIGH, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 6.0)},
            {ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_MEDIUM, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 72)},
            {ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_LOW, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 96)},
            {ServiceLevelAgreement.HP_IT_NORMAL_MEDIUM, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 96)},
            {ServiceLevelAgreement.HP_IT_NORMAL_LOW, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 144)},
            //HPI-IT
            {ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_TOP, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 8)},
            {ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_HIGH, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 24)},
            {ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_MEDIUM, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 72)},
            {ServiceLevelAgreement.HPI_IT_MISSION_CRITICAL_LOW, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 96)},
            {ServiceLevelAgreement.HPI_IT_ENTITY_ESSENTIAL_HIGH, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 24.0)},
            {ServiceLevelAgreement.HPI_IT_ENTITY_ESSENTIAL_MEDIUM, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 72)},
            {ServiceLevelAgreement.HPI_IT_ENTITY_ESSENTIAL_LOW, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 96)},
            {ServiceLevelAgreement.HPI_IT_NORMAL_MEDIUM, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 72)},
            {ServiceLevelAgreement.HPI_IT_NORMAL_LOW, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 96)},
            //HPE-IT (Same values as HP-IT)
            {ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_TOP, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 3.0)},
            {ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_HIGH, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 6.0)},
            {ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_MEDIUM, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 72)},
            {ServiceLevelAgreement.HPE_IT_MISSION_CRITICAL_LOW, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 96)},
            {ServiceLevelAgreement.HPE_IT_ENTITY_ESSENTIAL_HIGH, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 6.0)},
            {ServiceLevelAgreement.HPE_IT_ENTITY_ESSENTIAL_MEDIUM, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 72)},
            {ServiceLevelAgreement.HPE_IT_ENTITY_ESSENTIAL_LOW, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 96)},
            {ServiceLevelAgreement.HPE_IT_NORMAL_MEDIUM, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 96)},
            {ServiceLevelAgreement.HPE_IT_NORMAL_LOW, incident, DateTimeUtil.addHours(FIRST_DAY_2015_YEAR_TIMESTAMP, 144)},});

    }

}
