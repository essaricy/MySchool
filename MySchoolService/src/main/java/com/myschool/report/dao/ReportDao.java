package com.myschool.report.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.report.dto.ReportCriteriaToken;
import com.myschool.report.dto.ReportDto;

/**
 * The Interface ReportDao.
 */
public interface ReportDao {

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DaoException the dao exception
     */
    List<ReportDto> getAll() throws DaoException;

    /**
     * Gets the.
     * 
     * @param reportKey the report key
     * @return the report dto
     * @throws DaoException the dao exception
     */
    ReportDto get(String reportKey) throws DaoException;

    /**
     * Gets the report criteria tokens.
     * 
     * @param reportKey the report key
     * @return the report criteria tokens
     * @throws DaoException the dao exception
     */
    List<ReportCriteriaToken> getReportCriteriaTokens(String reportKey) throws DaoException;

}
