package com.myschool.report.domain;

import java.io.File;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.domain.ProfileManager;
import com.myschool.application.dto.OrganizationProfileDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.util.StringUtil;
import com.myschool.infra.report.agent.ReportAgent;
import com.myschool.infra.report.builders.ReportBuilder;
import com.myschool.infra.report.builders.SimpleListingReportBuilder;
import com.myschool.infra.report.exception.ReportException;
import com.myschool.report.constants.ReportKey;
import com.myschool.report.dao.ReportDao;
import com.myschool.report.dto.ReportCriteria;
import com.myschool.report.dto.ReportDto;
import com.myschool.report.factory.ReportBuilderFactory;

/**
 * The Class ReportManager.
 */
@Component
public class ReportManager {

    /** The report agent. */
    @Autowired
    private ReportAgent reportAgent;

    /** The profile manager. */
    @Autowired
    private ProfileManager profileManager;

    /** The report dao. */
    @Autowired
    private ReportDao reportDao;

    /** The report builder factory. */
    @Autowired
    private ReportBuilderFactory reportBuilderFactory;

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DataException the data exception
     */
    public List<ReportDto> getAll() throws DataException {
        List<ReportDto> reports = null;
        try {
            reports = reportDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return reports;
    }

    /**
     * Gets the.
     * 
     * @param reportKey the report key
     * @return the report dto
     * @throws DataException the data exception
     */
    public ReportDto get(String reportKey) throws DataException {
        ReportDto report = null;
        try {
            report = reportDao.get(reportKey);
            if (report != null) {
                report.setReportCriteriaTokens(reportDao.getReportCriteriaTokens(reportKey));
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return report;
    }

    /**
     * Generate report.
     * 
     * @param reportCriteria the report criteria
     * @return the file
     * @throws DataException the data exception
     */
    public File generateReport(ReportCriteria reportCriteria) throws DataException {
        File reportFile = null;
        try {
            if (reportCriteria == null) {
                throw new DataException("No Criteria specified for the report.");
            }
            ReportKey reportKey = reportCriteria.getReportKey();
            if (reportKey == null) {
                throw new DataException("Report Name is not specified.");
            }
            ReportDto report = reportDao.get(reportKey.toString());
            if (report == null) {
                throw new DataException("Report (" + reportKey + ") not found.");
            }
            OrganizationProfileDto organizationProfile = profileManager.getOrganizationProfile();

            // get report builder instance by the report name
            ReportBuilder reportBuilder = reportBuilderFactory.getReportBuilder(reportKey);
            if (reportBuilder == null) {
                throw new ReportException("There is no Report Builder found for " + reportKey);
            }
            ReportBuilder reportBuilderImpl = reportAgent.getReportBuilderImplementor(reportBuilder);
            if (reportBuilderImpl == null) {
                throw new ReportException("There is no Report Builder implementation found for " + reportKey);
            }
            Hashtable<String, Object> reportParameters = report.getReportParameters();
            if (reportParameters == null) {
                reportParameters = new Hashtable<String, Object>();
                report.setReportParameters(reportParameters);
            }
            if (reportBuilder instanceof SimpleListingReportBuilder) {
                String[] listingHeaders = ((SimpleListingReportBuilder) reportBuilder).getListingHeaders();
                if (listingHeaders != null) {
                    reportParameters.put("LISTING_HEADERS", listingHeaders);
                }
                List<Object[]> listingData = ((SimpleListingReportBuilder) reportBuilder).getListingData(reportCriteria);
                if (listingData != null) {
                    reportParameters.put("LISTING_DATA", listingData);
                }
            }
            reportFile = reportBuilderImpl.generateReport(organizationProfile, report, reportCriteria);
        } catch (ReportException reportException) {
            throw new DataException(reportException.getMessage(), reportException);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return reportFile;
    }

    /**
     * Gets the report criteria.
     * 
     * @param reportKey the report key
     * @return the report criteria
     * @throws DataException the data exception
     */
    public ReportCriteria getReportCriteria(String reportKey) throws DataException {
        ReportCriteria reportCriteria = null;
        try {
            if (StringUtil.isNullOrBlank(reportKey)) {
                throw new DataException("Report Key is missing.");
            }
            reportCriteria = new ReportCriteria();
            ReportDto report = reportDao.get(reportKey);
            if (report == null) {
                throw new DataException("There is no such report.");
            }
            reportCriteria.setReportKey(ReportKey.get(report.getReportKey()));
            reportCriteria.setReportName(report.getReportName());
            reportCriteria.setReportCriteriaTokens(reportDao.getReportCriteriaTokens(reportKey));
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return reportCriteria;
    }

}
