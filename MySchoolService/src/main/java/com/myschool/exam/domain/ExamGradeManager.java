package com.myschool.exam.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.InsufficientInputException;
import com.myschool.common.exception.InvalidDataException;
import com.myschool.exam.dao.ExamGradeDao;
import com.myschool.exam.dto.ExamGradeDto;

/**
 * The Class ExamGradesManager.
 */
@Component
public class ExamGradeManager {

    /** The exam grade dao. */
    @Autowired
    private ExamGradeDao examGradeDao;

    /**
     * Gets the grades.
     *
     * @return the grades
     * @throws DataException the data exception
     */
    public List<ExamGradeDto> getGrades() throws DataException {
        List<ExamGradeDto> examGrades = null;
        try {
            examGrades = examGradeDao.getGrades();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return examGrades;
    }

    /**
     * Creates the.
     *
     * @param examGrade the exam grade
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(ExamGradeDto examGrade) throws DataException {
        boolean created = false;
        try {
            if (examGrade == null) {
                throw new InsufficientInputException("examGrade is null");
            }
            String gradeName = examGrade.getGradeName();
            if (gradeName == null || gradeName.trim().length() == 0) {
                throw new InsufficientInputException("Grade Name is a required value.");
            }
            int qualifyingPercentage = examGrade.getQualifyingPercentage();
            if (qualifyingPercentage < 0) {
                throw new InsufficientInputException("Invalid value for Qualifying Percentage.");
            }
            // TODO Percentage overlap should not happen for the grades
            created = examGradeDao.create(examGrade) > 0;
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }

    /**
     * Delete.
     *
     * @param examGradeId the exam grade id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int examGradeId) throws DataException {
        boolean deleted = false;
        try {
            if (examGradeId <= 0) {
                throw new InvalidDataException("Invalid Exam Grade ID.");
            }
            deleted = examGradeDao.delete(examGradeId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return deleted;
    }

    /**
     * Gets the.
     *
     * @param examGradeId the exam grade id
     * @return the exam grade dto
     * @throws DataException the data exception
     */
    public ExamGradeDto get(int examGradeId) throws DataException {
        ExamGradeDto examGrade = null;
        try {
            if (examGradeId <= 0) {
                throw new InvalidDataException("Invalid Exam Grade ID.");
            }
            examGrade = examGradeDao.get(examGradeId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return examGrade;
    }

    /**
     * Update.
     *
     * @param examGradeId the exam grade id
     * @param examGrade the exam grade
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int examGradeId, ExamGradeDto examGrade) throws DataException {
        boolean updated = false;
        try {
            if (examGrade == null) {
                throw new InsufficientInputException("examGrade is null");
            }
            String gradeName = examGrade.getGradeName();
            if (gradeName == null || gradeName.trim().length() == 0) {
                throw new InsufficientInputException("Grade Name is a required value.");
            }
            int qualifyingPercentage = examGrade.getQualifyingPercentage();
            if (qualifyingPercentage < 0) {
                throw new InsufficientInputException("Invalid value for Qualifying Percentage.");
            }
            // TODO Percentage overlap should not happen for the grades
            updated = examGradeDao.update(examGradeId, examGrade);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return updated;
    }

}
