package com.myschool.infra.report.builders.jasper;

import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.io.File;
import java.util.Hashtable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.myschool.infra.report.constants.ReportStyles;
import com.myschool.infra.report.exception.ReportException;
import com.myschool.organization.dto.Organization;
import com.myschool.report.dto.ReportCriteria;
import com.myschool.report.dto.ReportDto;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.ColumnBuilders;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.ListOfArrayDataSource;

/**
 * The Class JasperSimpleListingReportBuilder.
 */
@Component
public class JasperSimpleListingReportBuilder extends JasperAbstractReportBuilder {

    /** The Constant EMPTY_DATA_SOURCE. */
    private static final JREmptyDataSource EMPTY_DATA_SOURCE = new JREmptyDataSource();

    /* (non-Javadoc)
     * @see com.myschool.infra.report.builders.ReportBuilder#generateReport(com.myschool.organization.dto.Organization, com.myschool.report.dto.ReportDto, com.myschool.report.dto.ReportCriteria)
     */
    @Override
    public final File generateReport(Organization organization,
            ReportDto report, ReportCriteria reportCriteria) throws ReportException {

        File reportFile = null;
        Hashtable<String, Object> reportParameters = report.getReportParameters();
        JasperReportBuilder jasperReportBuilder = getBaseReport(organization, report, reportCriteria);
        String[] listingHeaders = (String[]) reportParameters.get("LISTING_HEADERS");
        List<Object[]> reportData = (List<Object[]>) reportParameters.get("LISTING_DATA");
        if (listingHeaders == null) {
            throw new ReportException("Listing Headers are not provided.");
        }
        addReportHeaders(jasperReportBuilder, listingHeaders);
        if (reportData == null) {
            jasperReportBuilder.setDataSource(EMPTY_DATA_SOURCE);
        } else {
            ListOfArrayDataSource listOfArrayDataSource = new ListOfArrayDataSource(reportData, listingHeaders);
            jasperReportBuilder.setDataSource(listOfArrayDataSource);
        }
        jasperReportBuilder.setColumnTitleStyle(ReportStyles.TABLE_HEADER_STYLE).highlightDetailEvenRows();
        reportFile = generatePdfFile(jasperReportBuilder);
        return reportFile;
    }

    /**
     * Adds the report headers.
     * 
     * @param jasperReportBuilder the jasper report builder
     * @param reportHeaders the report headers
     */
    private void addReportHeaders(JasperReportBuilder jasperReportBuilder,
            String[] reportHeaders) {
        if (jasperReportBuilder != null && reportHeaders != null) {
            ColumnBuilders columnBuilders = DynamicReports.col;
            for (String reportHeader : reportHeaders) {
                if (reportHeader != null) {
                    jasperReportBuilder.columns().addColumn(
                            columnBuilders.column(reportHeader, reportHeader, type.stringType()));
                }
            }
        }
    }

}