package com.myschool.infra.report.factory.jasper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.infra.report.builders.ReportBuilder;
import com.myschool.infra.report.builders.SimpleListingReportBuilder;
import com.myschool.infra.report.builders.jasper.JasperSimpleListingReportBuilder;

@Component
public class JasperReportBuilderFactory {

    @Autowired
    private JasperSimpleListingReportBuilder jasperSimpleListingReportBuilder;

    public ReportBuilder getReportBuilder(ReportBuilder reportBuilder) {
        if (reportBuilder instanceof SimpleListingReportBuilder) {
            return jasperSimpleListingReportBuilder;
        }
        return reportBuilder;
    }

}
