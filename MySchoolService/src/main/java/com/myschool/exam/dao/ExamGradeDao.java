package com.myschool.exam.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.exam.dto.ExamGradeDto;

/**
 * The Interface ExamGradeDao.
 */
public interface ExamGradeDao {

    /**
     * Gets the grades.
     *
     * @return the grades
     * @throws DaoException the dao exception
     */
    List<ExamGradeDto> getGrades() throws DaoException;

    /**
     * Creates the.
     *
     * @param examGrade the exam grade
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(ExamGradeDto examGrade) throws DaoException;

    /**
     * Gets the.
     *
     * @param examGradeName the exam grade name
     * @return the exam grade dto
     * @throws DaoException the dao exception
     */
    ExamGradeDto get(String examGradeName) throws DaoException;

    /**
     * Delete.
     *
     * @param examGradeId the exam grade id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(int examGradeId) throws DaoException;

    /**
     * Gets the.
     *
     * @param examGradeId the exam grade id
     * @return the exam grade dto
     * @throws DaoException the dao exception
     */
    ExamGradeDto get(int examGradeId) throws DaoException;

    /**
     * Update.
     *
     * @param examGradeId the exam grade id
     * @param examGrade the exam grade
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int examGradeId, ExamGradeDto examGrade) throws DaoException;

}
