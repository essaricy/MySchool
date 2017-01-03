package com.myschool.clazz.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.clazz.dao.SectionDao;
import com.myschool.clazz.dto.SectionDto;
import com.myschool.common.exception.DaoException;
import com.quasar.core.exception.DataException;
import com.quasar.core.exception.InsufficientInputException;
import com.quasar.core.exception.InvalidDataException;

/**
 * The Section SectionManager.
 */
@Component
public class SectionManager {

    /** The section dao. */
    @Autowired
    private SectionDao sectionDao;

    /**
     * Creates the.
     *
     * @param sectionDto the section dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(SectionDto sectionDto) throws DataException {
        boolean created = false;
        try {
            if (sectionDto == null) {
                throw new InsufficientInputException("sectionDto is null");
            }
            String sectionName = sectionDto.getSectionName();
            if (sectionName == null || sectionName.trim().length() == 0) {
                throw new InsufficientInputException("Section Name is a required value.");
            }
            created = (sectionDao.createSection(sectionDto) > 0) ? true : false;
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }

    /**
     * Delete.
     *
     * @param sectionId the section id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int sectionId) throws DataException {
        boolean deleted = false;
        try {
            if (sectionId <= 0) {
                throw new InvalidDataException("Invalid Section ID.");
            }
            deleted = sectionDao.deleteSection(sectionId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return deleted;
    }

    /**
     * Gets the.
     *
     * @param sectionId the section id
     * @return the section dto
     * @throws DataException the data exception
     */
    public SectionDto get(int sectionId) throws DataException {
        SectionDto sectionDto = null;
        try {
            if (sectionId <= 0) {
                throw new InvalidDataException("Invalid Section ID.");
            }
            sectionDto = sectionDao.get(sectionId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return sectionDto;
    }

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DataException the data exception
     */
    public List<SectionDto> getAll() throws DataException {
        List<SectionDto> sections = null;
        try {
            sections = sectionDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return sections;
    }

    /**
     * Update.
     *
     * @param sectionId the section id
     * @param sectionDto the section dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int sectionId, SectionDto sectionDto) throws DataException {
        boolean created = false;
        try {
            if (sectionDto == null) {
                throw new InsufficientInputException("sectionDto is null");
            }
            String sectionName = sectionDto.getSectionName();
            if (sectionName == null || sectionName.trim().length() == 0) {
                throw new InsufficientInputException("Section Name is a required value.");
            }
            created = sectionDao.updateSection(sectionId, sectionDto);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }
}
