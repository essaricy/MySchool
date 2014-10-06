package com.myschool.student.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.student.dao.AdmissionStatusDao;
import com.myschool.student.dto.AdmissionStatus;
import com.myschool.student.validator.AdmissionStatusValidator;

/**
 * The Class AdmissionStatusManager.
 */
@Component
public class AdmissionStatusManager {

    /** The admission status validator. */
    @Autowired
    private AdmissionStatusValidator admissionStatusValidator;

    /** The admission status dao. */
    @Autowired
    private AdmissionStatusDao admissionStatusDao;

    /**
     * Gets the all.
     * 
     * @return the all
     * @throws DataException the data exception
     */
    public List<AdmissionStatus> getAll() throws DataException {
        try {
            return admissionStatusDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the.
     * 
     * @param statusId the status id
     * @return the admission status
     * @throws DataException the data exception
     */
    public AdmissionStatus get(int statusId) throws DataException {
        try {
            return admissionStatusDao.get(statusId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Creates the.
     * 
     * @param admissionStatus the admission status
     * @return the int
     * @throws DataException the data exception
     */
    public int create(AdmissionStatus admissionStatus) throws DataException {
        try {
            admissionStatusValidator.validate(admissionStatus);
            String description = admissionStatus.getDescription();
            List<AdmissionStatus> admissionStatusList = getAll();
            if (admissionStatusList != null && !admissionStatusList.isEmpty()) {
                for (AdmissionStatus existingAdmissionStatus : admissionStatusList) {
                    String existingDescription = existingAdmissionStatus.getDescription();
                    if (description.trim().equalsIgnoreCase(existingDescription.trim())) {
                        throw new ValidationException("Admission Status '" + description + "' already exists.");
                    }
                }
            }
            return admissionStatusDao.create(admissionStatus);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Update.
     * 
     * @param admissionStatusId the admission status id
     * @param admissionStatus the admission status
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int admissionStatusId, AdmissionStatus admissionStatus) throws DataException {
        try {
            admissionStatusValidator.validate(admissionStatusId, admissionStatus);
            return admissionStatusDao.update(admissionStatusId, admissionStatus);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Delete.
     * 
     * @param admissionStatusId the admission status id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int admissionStatusId) throws DataException {
        try {
            return admissionStatusDao.delete(admissionStatusId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

}
