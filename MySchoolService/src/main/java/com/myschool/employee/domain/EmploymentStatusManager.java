package com.myschool.employee.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.ValidationException;
import com.myschool.employee.dao.EmploymentStatusDao;
import com.myschool.employee.dto.EmploymentStatus;
import com.myschool.employee.validator.EmploymentStatusValidator;
import com.quasar.core.exception.DataException;

/**
 * The Class EmploymentStatusManager.
 */
@Component
public class EmploymentStatusManager {

    /** The employment status validator. */
    @Autowired
    private EmploymentStatusValidator employmentStatusValidator;

    /** The employment status dao. */
    @Autowired
    private EmploymentStatusDao employmentStatusDao;

    /**
     * Gets the all.
     * 
     * @return the all
     * @throws DataException the data exception
     */
    public List<EmploymentStatus> getAll() throws DataException {
        try {
            return employmentStatusDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the.
     * 
     * @param statusId the status id
     * @return the employment status
     * @throws DataException the data exception
     */
    public EmploymentStatus get(int statusId) throws DataException {
        try {
            return employmentStatusDao.get(statusId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Creates the.
     * 
     * @param employmentStatus the employment status
     * @return the int
     * @throws DataException the data exception
     */
    public int create(EmploymentStatus employmentStatus) throws DataException {
        try {
            employmentStatusValidator.validate(employmentStatus);
            String description = employmentStatus.getDescription();
            List<EmploymentStatus> employmentStatusList = getAll();
            if (employmentStatusList != null && !employmentStatusList.isEmpty()) {
                for (EmploymentStatus existingEmploymentStatus : employmentStatusList) {
                    String existingDescription = existingEmploymentStatus.getDescription();
                    if (description.trim().equalsIgnoreCase(existingDescription.trim())) {
                        throw new ValidationException("Employment Status '" + description + "' already exists.");
                    }
                }
            }
            return employmentStatusDao.create(employmentStatus);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Update.
     * 
     * @param employmentStatusId the employment status id
     * @param employmentStatus the employment status
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int employmentStatusId, EmploymentStatus employmentStatus) throws DataException {
        try {
            employmentStatusValidator.validate(employmentStatusId, employmentStatus);
            return employmentStatusDao.update(employmentStatusId, employmentStatus);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Delete.
     * 
     * @param employmentStatusId the employment status id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int employmentStatusId) throws DataException {
        try {
            return employmentStatusDao.delete(employmentStatusId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

}
