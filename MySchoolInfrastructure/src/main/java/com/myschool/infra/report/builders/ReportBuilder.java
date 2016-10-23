package com.myschool.infra.report.builders;

import java.io.File;

import com.myschool.infra.report.exception.ReportException;
import com.myschool.organization.dto.Organization;
import com.myschool.report.dto.ReportCriteria;
import com.myschool.report.dto.ReportDto;

/**
 * The Interface ReportBuilder.
 */
public interface ReportBuilder {

    /**
     * Builds the report.
     *
     * @param organization the organization
     * @param report the report
     * @param reportCriteria the report criteria
     * @return the file
     * @throws ReportException the report exception
     */
    File generateReport(Organization organization,
            ReportDto report, ReportCriteria reportCriteria) throws ReportException;

}
