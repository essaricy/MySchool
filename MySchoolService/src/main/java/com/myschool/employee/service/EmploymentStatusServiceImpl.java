package com.myschool.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.ServiceException;
import com.myschool.employee.domain.EmploymentStatusManager;
import com.myschool.employee.dto.EmploymentStatus;
import com.quasar.core.exception.DataException;

/**
 * The Class EmploymentStatusServiceImpl.
 */
@Service
public class EmploymentStatusServiceImpl implements EmploymentStatusService {

    /** The employment status manager. */
    @Autowired
    private EmploymentStatusManager employmentStatusManager;

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(EmploymentStatus employmentStatus) throws ServiceException {
        try {
            return employmentStatusManager.create(employmentStatus) > 0;
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#getAll()
     */
    @Override
    public List<EmploymentStatus> getAll() throws ServiceException {
        try {
            return employmentStatusManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#get(int)
     */
    @Override
    public EmploymentStatus get(int employmentStatusId) throws ServiceException {
        try {
            return employmentStatusManager.get(employmentStatusId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int employmentStatusId,
            EmploymentStatus employmentStatus) throws ServiceException {
        try {
            return employmentStatusManager.update(employmentStatusId, employmentStatus);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#delete(int)
     */
    @Override
    public boolean delete(int employmentStatusId) throws ServiceException {
        try {
            return employmentStatusManager.delete(employmentStatusId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

}
