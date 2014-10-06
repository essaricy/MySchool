package com.myschool.branch.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.branch.dao.DivisionDao;
import com.myschool.branch.dto.DivisionDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.InsufficientInputException;
import com.myschool.common.exception.InvalidDataException;

/**
 * The Class DivisionManager.
 */

@Component
public class DivisionManager {

    /** The division dao. */
    @Autowired
    private DivisionDao divisionDao;

    /**
     * Creates the.
     * 
     * @param divisionDto the division dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(DivisionDto divisionDto) throws DataException {
        boolean created = false;
        try {
            if (divisionDto == null) {
                throw new InsufficientInputException("DivisionDto is null");
            }
            String divisionCode = divisionDto.getDivisionCode();
            if (divisionCode == null || divisionCode.trim().length() == 0) {
                throw new InsufficientInputException(
                        "Division code is a required value.");
            }
            if (divisionCode.trim().length() > 8) {
                throw new InvalidDataException(
                        "Division code cannot exceed more than 8 characters.");
            }
            String description = divisionDto.getDescription();
            if (description == null || description.trim().length() == 0) {
                throw new InsufficientInputException(
                        "Description is a required value.");
            }
            created = (divisionDao.create(divisionDto) > 0) ? true : false;
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }

    /**
     * Gets the all.
     * 
     * @return the all
     * @throws DataException the data exception
     */
    public List<DivisionDto> getAll() throws DataException {
        List<DivisionDto> divisions = null;
        try {
            divisions = divisionDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return divisions;
    }

    /**
     * Gets the.
     * 
     * @param divisionId the division id
     * @return the division dto
     * @throws DataException the data exception
     */
    public DivisionDto get(int divisionId) throws DataException {
        DivisionDto division = null;
        try {
            if (divisionId <= 0) {
                throw new InvalidDataException("Invalid division ID.");
            }
            division = divisionDao.get(divisionId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return division;
    }

    /**
     * Update.
     * 
     * @param divisionId the division id
     * @param divisionDto the division dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int divisionId, DivisionDto divisionDto)
            throws DataException {
        boolean updated = false;
        try {
            if (divisionId <= 0) {
                throw new InvalidDataException("Invalid division ID.");
            }
            if (divisionDto == null) {
                throw new InsufficientInputException("DivisionDto is null");
            }
            String divisionCode = divisionDto.getDivisionCode();
            if (divisionCode == null || divisionCode.trim().length() == 0) {
                throw new InsufficientInputException(
                        "Division code is a required value.");
            }
            if (divisionCode.trim().length() > 8) {
                throw new InvalidDataException(
                        "Division code cannot exceed more than 8 characters.");
            }
            String description = divisionDto.getDescription();
            if (description == null || description.trim().length() == 0) {
                throw new InsufficientInputException(
                        "Description is a required value.");
            }
            updated = divisionDao.update(divisionId, divisionDto);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return updated;
    }

    /**
     * Delete.
     *
     * @param divisionId the division id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int divisionId) throws DataException {
        boolean deleted = false;
        try {
            if (divisionId <= 0) {
                throw new InvalidDataException("Invalid division ID.");
            }
            deleted = divisionDao.delete(divisionId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return deleted;
    }

}
