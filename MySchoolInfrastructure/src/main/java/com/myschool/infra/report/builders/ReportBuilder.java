package com.myschool.infra.report.builders;

import java.io.File;

import com.myschool.application.dto.OrganizationProfileDto;
import com.myschool.infra.application.dto.MySchoolDto;
import com.myschool.infra.report.exception.ReportException;
import com.myschool.report.dto.ReportCriteria;
import com.myschool.report.dto.ReportDto;

/**
 * The Interface ReportBuilder.
 */
public interface ReportBuilder {

    /**
     * Builds the report.
     * 
     * @param organizationProfile the organization profile
     * @param mySchoolDto the my school dto
     * @param report the report
     * @param reportCriteria the report criteria
     * @return the file
     * @throws ReportException the report exception
     */
    File generateReport(OrganizationProfileDto organizationProfile,
            MySchoolDto mySchoolDto, ReportDto report,
            ReportCriteria reportCriteria) throws ReportException;

}
