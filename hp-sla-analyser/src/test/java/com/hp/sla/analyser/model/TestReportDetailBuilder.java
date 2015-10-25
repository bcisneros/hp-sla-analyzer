package com.hp.sla.analyser.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Utility class to generate ReportDetail objects to test
 *
 * @author Benjamin Cisneros Barraza
 */
public class TestReportDetailBuilder {

    private ReportDetail reportDetail;
    private final static Logger logger = Logger.getLogger(TestReportDetailBuilder.class);
    private final static TestReportDetailBuilder INSTANCE = null;
    private static int reportDetailCounter = 0;

    private TestReportDetailBuilder() {
        this.reportDetail = getDefaultReportDetail();
    }

    public static TestReportDetailBuilder getInstance() {
        if (INSTANCE == null) {
            return new TestReportDetailBuilder();
        }
        return INSTANCE;
    }

    public ReportDetail build() {
        try {
            Incident incident = TestIncidentBuilder.getInstance().build();
            incident.setId(TestIncidentBuilder.getInstance().FORMATTER.format(++reportDetailCounter));
            reportDetail.setIncident(incident);
            return (ReportDetail) reportDetail.clone();
        } catch (CloneNotSupportedException ex) {
            logger.error("Error cloning incident object", ex);
        }
        return null;
    }

    public List<ReportDetail> buildList(int size) {
        List<ReportDetail> list = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            list.add(build());
        }
        return list;
    }

    public TestReportDetailBuilder incident(Incident incident) {
        reportDetail.setIncident(incident);
        return this;
    }

    public TestReportDetailBuilder burn(boolean burn) {
        reportDetail.setBurnedOut(burn);
        return this;
    }

    public TestReportDetailBuilder compliant(boolean compliant) {
        reportDetail.setCompliantWithSLA(compliant);
        return this;
    }

    public TestReportDetailBuilder reset() {
        reportDetail = getDefaultReportDetail();
        reportDetailCounter = 0;
        return this;
    }

    private ReportDetail getDefaultReportDetail() {
        ReportDetail detail = new ReportDetail();
        detail.setBurnedOut(false);
        detail.setCompliantWithSLA(true);
        detail.setDetailException(null);
        detail.setIncident(TestIncidentBuilder.getInstance().build());
        return detail;
    }
}
