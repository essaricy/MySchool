package com.myschool.report.service;

import java.io.File;

import com.myschool.application.service.Servicable;
import com.myschool.common.exception.ServiceException;
import com.myschool.report.dto.ReportCriteria;
import com.myschool.report.dto.ReportDto;

/**
 * The Interface ReportService.
 */
public interface ReportService extends Servicable<ReportDto>{

    /**
     * Generate report.
     * 
     * @param reportCriteria the report criteria
     * @return the file
     * @throws ServiceException the service exception
     */
    File generateReport(ReportCriteria reportCriteria) throws ServiceException;

    /**
     * Gets the.
     * 
     * @param reportKey the report key
     * @return the report dto
     * @throws ServiceException the service exception
     */
    ReportDto get(String reportKey) throws ServiceException;

    /**
     * Gets the report criteria.
     * 
     * @param reportKey the report key
     * @return the report criteria
     * @throws ServiceException the service exception
     */
    ReportCriteria getReportCriteria(String reportKey) throws ServiceException;

}
