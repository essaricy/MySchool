package com.myschool.exam.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.exam.dto.ExamDto;

/**
 * The Interface ExamDao.
 */
public interface ExamDao {

    /**
     * Gets the by class.
     *
     * @param classId the class id
     * @return the exam names in class
     * @throws DaoException the dao exception
     */
    List<ExamDto> getByClass(int classId) throws DaoException;

    /**
     * Gets the.
     * 
     * @param examId the exam id
     * @return the exam
     * @throws DaoException the dao exception
     */
    ExamDto get(int examId) throws DaoException;

    /**
     * Delete.
     *
     * @param examId the exam id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(int examId) throws DaoException;

    /**
     * Update.
     * 
     * @param examId the exam id
     * @param exam the exam
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int examId, ExamDto exam) throws DaoException;

    /**
     * Gets the.
     *
     * @param classId the class id
     * @param examName the exam name
     * @param examDate the exam date
     * @return the exam dto
     * @throws DaoException the dao exception
     */
    ExamDto get(int classId, String examName, String examDate) throws DaoException;

    /**
     * Creates the.
     *
     * @param exam the exam
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(ExamDto exam) throws DaoException;

    /**
     * Gets the exam.
     *
     * @param classId the class id
     * @param examName the exam name
     * @return the exam
     * @throws DaoException the dao exception
     */
    ExamDto get(int classId, String examName) throws DaoException;

    /**
     * Gets the exams in current academic by class.
     *
     * @param classId the class id
     * @return the current academic by class
     * @throws DaoException the dao exception
     */
    List<ExamDto> getExamsInCurrentAcademicByClass(int classId) throws DaoException;

    /**
     * Gets the exams in current academic.
     *
     * @return the current academic
     * @throws DaoException the dao exception
     */
    List<ExamDto> getExamsInCurrentAcademic() throws DaoException;

    /**
     * Gets the latest by class.
     * 
     * @param classId the class id
     * @return the latest by class
     * @throws DaoException the dao exception
     */
    ExamDto getLatestByClass(int classId) throws DaoException;

}
