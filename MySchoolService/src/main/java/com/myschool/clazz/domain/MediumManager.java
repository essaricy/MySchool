package com.myschool.clazz.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.clazz.dao.MediumDaoImpl;
import com.myschool.clazz.dto.MediumDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.InsufficientInputException;
import com.myschool.common.exception.InvalidDataException;

@Component
public class MediumManager {

    /** The medium dao. */
    @Autowired
    private MediumDaoImpl mediumDao;

    /**
     * Creates the.
     *
     * @param mediumDto the medium dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(MediumDto mediumDto) throws DataException {
        boolean created = false;
        try {
            if (mediumDto == null) {
                throw new InsufficientInputException("mediumDto is null");
            }
           /* String mediumCode = mediumDto.getMediumCode();
            if (mediumCode == null || mediumCode.trim().length() == 0) {
                throw new InsufficientInputException("medium Code is a required value.");
            }*/
            String description = mediumDto.getDescription();
            if (description == null || description.trim().length() == 0) {
                throw new InsufficientInputException("medium description is a required value.");
            }
            created = (mediumDao.createMedium(mediumDto) > 0) ? true : false;
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }

    /**
     * Delete.
     *
     * @param mediumId the medium id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int mediumId) throws DataException {
        boolean deleted = false;
        try {
            if (mediumId <= 0) {
                throw new InvalidDataException("Invalid Medium ID.");
            }
            deleted = mediumDao.deleteMedium(mediumId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return deleted;
    }

    /**
     * Gets the.
     *
     * @param mediumId the medium id
     * @return the medium dto
     * @throws DataException the data exception
     */
    public MediumDto get(int mediumId) throws DataException {
        MediumDto medium = null;
        try {
            if (mediumId <= 0) {
                throw new InvalidDataException("Invalid Medium ID.");
            }
            medium = mediumDao.get(mediumId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return medium;
    }

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DataException the data exception
     */
    public List<MediumDto> getAll() throws DataException {
        List<MediumDto> mediums = null;
        try {
            mediums = mediumDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return mediums;
    }

    /**
     * Update.
     *
     * @param mediumId the medium id
     * @param mediumDto the medium dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int mediumId, MediumDto mediumDto) throws DataException {
        boolean created = false;
        try {
            if (mediumDto == null) {
                throw new InsufficientInputException("mediumDto is null");
            }
           /* String mediumCode = mediumDto.getMediumCode();
            if (mediumCode == null || mediumCode.trim().length() == 0) {
                throw new InsufficientInputException("medium Code is a required value.");
            }*/
            String description = mediumDto.getDescription();
            if (description == null || description.trim().length() == 0) {
                throw new InsufficientInputException("medium description is a required value.");
            }
            created = mediumDao.updateMedium(mediumId, mediumDto);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }
}
