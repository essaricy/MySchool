package com.myschool.report.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.ServiceException;
import com.myschool.report.domain.ReportManager;
import com.myschool.report.dto.ReportCriteria;
import com.myschool.report.dto.ReportDto;
import com.quasar.core.exception.DataException;

/**
 * The Class ReportServiceImpl.
 */
@Service
public class ReportServiceImpl implements ReportService {

    /** The report manager. */
    @Autowired
    private ReportManager reportManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(ReportDto dto) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int id) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public ReportDto get(int id) throws ServiceException {
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<ReportDto> getAll() throws ServiceException {
        List<ReportDto> reports = null;
        try {
            reports = reportManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return reports;
    }

    /* (non-Javadoc)
     * @see com.myschool.report.service.ReportService#get(java.lang.String)
     */
    @Override
    public ReportDto get(String reportKey) throws ServiceException {
        ReportDto report = null;
        try {
            report = reportManager.get(reportKey);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return report;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int id, ReportDto dto) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.report.service.ReportService#generateReport(com.myschool.infra.report.dto.ReportCriteria)
     */
    @Override
    public File generateReport(ReportCriteria reportCriteria) throws ServiceException {
        File report = null;
        try {
            report = reportManager.generateReport(reportCriteria);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return report;
    }

    /* (non-Javadoc)
     * @see com.myschool.report.service.ReportService#getReportCriteria(java.lang.String)
     */
    @Override
    public ReportCriteria getReportCriteria(String reportKey)
            throws ServiceException {
        ReportCriteria reportCriteria = null;
        try {
            reportCriteria = reportManager.getReportCriteria(reportKey);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return reportCriteria;
    }

}
