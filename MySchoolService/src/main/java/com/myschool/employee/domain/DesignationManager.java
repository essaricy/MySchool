package com.myschool.employee.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.employee.dao.DesignationDao;
import com.myschool.employee.dto.DesignationDto;
import com.quasar.core.exception.DataException;
import com.quasar.core.exception.InsufficientInputException;
import com.quasar.core.exception.InvalidDataException;

/**
 * The Class DesignationManager.
 */
@Component
public class DesignationManager {

    /** The designation dao. */
    @Autowired
    private DesignationDao designationDao;

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DataException the data exception
     */
    public List<DesignationDto> getAll() throws DataException {
        List<DesignationDto> designations = null;
        try {
            designations = designationDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return designations;
    }

    /**
     * Gets the.
     *
     * @param designationId the designation id
     * @return the designation dto
     * @throws DataException the data exception
     */
    public DesignationDto get(int designationId) throws DataException {
        DesignationDto designation = null;
        try {
            designation = designationDao.get(designationId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return designation;
    }

    /**
     * Creates the.
     *
     * @param designation the designation
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(DesignationDto designation) throws DataException {
        boolean created = false;
        try {
            if (designation == null) {
                throw new InsufficientInputException("designation is null");
            }
            int designationId = designation.getDesignationId();
            if (designationId <=0 ) {
                throw new InsufficientInputException("Invalid value for designation id: " + designationId);
            }
            String designationName = designation.getDesignation();
            if (designationName == null || designationName.trim().length() == 0) {
                throw new InsufficientInputException("Designation is a required value.");
            }
            created = designationDao.create(designation);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }

    /**
     * Delete.
     *
     * @param designationId the designation id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int designationId) throws DataException {
        boolean deleted = false;
        try {
            if (designationId <= 0) {
                throw new InvalidDataException("Invalid Designation ID.");
            }
            deleted = designationDao.delete(designationId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return deleted;
    }

    /**
     * Update.
     *
     * @param designationId the designation id
     * @param designation the designation
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int designationId, DesignationDto designation) throws DataException {
        boolean updated = false;
        try {
            if (designation == null) {
                throw new InsufficientInputException("designation is null");
            }
            String designationName = designation.getDesignation();
            if (designationName == null || designationName.trim().length() == 0) {
                throw new InsufficientInputException("Designation is a required value.");
            }
            updated = designationDao.update(designationId, designation);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return updated;
    }

}
