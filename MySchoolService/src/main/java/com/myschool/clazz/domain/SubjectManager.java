package com.myschool.clazz.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.clazz.dao.SubjectDao;
import com.myschool.clazz.dto.SubjectDto;
import com.myschool.common.exception.DaoException;
import com.quasar.core.exception.DataException;
import com.quasar.core.exception.InsufficientInputException;
import com.quasar.core.exception.InvalidDataException;

/**
 * The Class SubjectManager.
 */
@Component
public class SubjectManager {

    /** The subject dao. */
    @Autowired
    private SubjectDao subjectDao;

    /**
     * Creates the.
     *
     * @param subjectDto the subject dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(SubjectDto subjectDto) throws DataException {
        boolean created = false;
        try {
            if (subjectDto == null) {
                throw new InsufficientInputException("subjectDto is null");
            }
            String subjectName = subjectDto.getSubjectName();
            if (subjectName == null || subjectName.trim().length() == 0) {
                throw new InsufficientInputException("subject subjectName is a required value.");
            }
            created = (subjectDao.createSubject(subjectDto) > 0) ? true : false;
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }

    /**
     * Delete.
     *
     * @param subjectId the subject id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int subjectId) throws DataException {
        boolean deleted = false;
        try {
            if (subjectId <= 0) {
                throw new InvalidDataException("Invalid Subject ID.");
            }
            deleted = subjectDao.deleteSubject(subjectId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return deleted;
    }

    /**
     * Gets the.
     *
     * @param subjectId the subject id
     * @return the subject dto
     * @throws DataException the data exception
     */
    public SubjectDto get(int subjectId) throws DataException {
        SubjectDto subject = null;
        try {
            if (subjectId <= 0) {
                throw new InvalidDataException("Invalid Subject ID.");
            }
            subject = subjectDao.get(subjectId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return subject;
    }

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DataException the data exception
     */
    public List<SubjectDto> getAll() throws DataException {
        List<SubjectDto> subjects = null;
        try {
            subjects = subjectDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return subjects;
    }

    /**
     * Update.
     *
     * @param subjectId the subject id
     * @param subjectDto the subject dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int subjectId, SubjectDto subjectDto) throws DataException {
        boolean created = false;
        try {
            if (subjectDto == null) {
                throw new InsufficientInputException("subjectDto is null");
            }
            String subjectName = subjectDto.getSubjectName();
            if (subjectName == null || subjectName.trim().length() == 0) {
                throw new InsufficientInputException("subject name is a required value.");
            }
            created = subjectDao.updateSubject(subjectId, subjectDto);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }

}
