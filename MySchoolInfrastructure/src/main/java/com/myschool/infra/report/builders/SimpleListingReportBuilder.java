package com.myschool.infra.report.builders;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Component;

import com.myschool.infra.report.exception.ReportException;
import com.myschool.organization.dto.Organization;
import com.myschool.report.dto.ReportCriteria;
import com.myschool.report.dto.ReportDto;

/**
 * The Class SimpleListingReportBuilder.
 * 
 */
@Component
public abstract class SimpleListingReportBuilder extends AbstractReportBuilder {

    /** The number of columns. */
    protected static int numberOfColumns;

    /**
     * Gets the listing headers.
     * 
     * @return the listing headers
     * @throws ReportException the report exception
     */
    public abstract String[] getListingHeaders() throws ReportException;

    /**
     * Gets the listing data.
     * 
     * @param reportCriteria the report criteria
     * @return the listing data
     * @throws ReportException the report exception
     */
    public abstract List<Object[]> getListingData(ReportCriteria reportCriteria) throws ReportException;

    /* (non-Javadoc)
     * @see com.myschool.infra.report.builders.ReportBuilder#generateReport(com.myschool.organization.dto.Organization, com.myschool.report.dto.ReportDto, com.myschool.report.dto.ReportCriteria)
     */
    public File generateReport(Organization organization,
            ReportDto report, ReportCriteria reportCriteria)
            throws ReportException {
        throw new ReportException("User Implementing version of generateReport()");
    }

}
