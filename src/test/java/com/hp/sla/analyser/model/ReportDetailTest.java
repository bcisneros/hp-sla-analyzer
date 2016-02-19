package com.hp.sla.analyser.model;

import com.hp.sla.analyser.model.util.BurnedOut;
import static com.hp.sla.analyser.util.DateTimeUtil.FIRST_DAY_2015_YEAR_TIMESTAMP;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author cisnerob
 */
public class ReportDetailTest {

    final static String AUDIT_VALUE = "ANY VALUE";

    @Test
    public void testGetIncidentAuditNewValueText() {
        ReportDetail instance = new ReportDetail();
        instance.setIncident(getIncidentToTest());
        assertEquals(AUDIT_VALUE, instance.getIncidentAuditNewValueText());
    }

    @Test
    public void testGetIncidentAuditSystemModifiedTime() {
        ReportDetail instance = new ReportDetail();
        instance.setIncident(getIncidentToTest());
        assertEquals(FIRST_DAY_2015_YEAR_TIMESTAMP, instance.getIncidentAuditSystemModifiedTime());
    }

    @Test
    public void testGetBurnedOutComplianceStringWithBurnedOutIncident() {
        ReportDetail instance = new ReportDetail();
        instance.setBurnedOut(true);
        assertEquals(BurnedOut.NON_COMPLIANCE.getName(), instance.getBurnedOutComplianceString());
    }

    @Test
    public void testGetBurnedOutComplianceStringWithNotBurnedOutIncident() {
        ReportDetail instance = new ReportDetail();
        instance.setBurnedOut(false);
        assertEquals(BurnedOut.COMPLIANCE.getName(), instance.getBurnedOutComplianceString());
    }

    @Test
    public void testGetBurnedOutComplianceStringWithIndeterminatedBurnedOutIncidentStatus() {
        ReportDetail instance = new ReportDetail();
        instance.setDetailException(new Exception());
        assertEquals(BurnedOut.UNDETERMINED.getName(), instance.getBurnedOutComplianceString());
    }

    private Incident getIncidentToTest() {
        Incident incident = new Incident();
        Audit audit = new Audit();
        audit.setSystemModifiedTime(FIRST_DAY_2015_YEAR_TIMESTAMP);
        audit.setNewVaueText(AUDIT_VALUE);
        incident.getAudits().add(audit);
        incident.setLastAssignmentGroupAudit(audit);
        return incident;
    }
}
