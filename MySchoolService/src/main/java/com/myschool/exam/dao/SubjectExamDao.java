package com.myschool.exam.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.exam.dto.SubjectExamDto;

/**
 * The Interface SubjectExamDao.
 */
public interface SubjectExamDao {

    /**
     * Creates the.
     *
     * @param examId the exam id
     * @param subjectExams the subject exams
     * @throws DaoException the dao exception
     */
    void create(int examId, List<SubjectExamDto> subjectExams) throws DaoException;

    /**
     * Delete.
     *
     * @param subjectExams the subject exams
     * @throws DaoException the dao exception
     */
    void delete(List<SubjectExamDto> subjectExams) throws DaoException;

    /**
     * Update.
     *
     * @param subjectExams the subject exams
     * @throws DaoException the dao exception
     */
    void update(List<SubjectExamDto> subjectExams) throws DaoException;

    /**
     * Gets the subject exams.
     *
     * @param examId the exam id
     * @return the subject exams
     * @throws DaoException the dao exception
     */
    List<SubjectExamDto> getSubjectExams(int examId) throws DaoException;

    /**
     * Gets the subject exam.
     * 
     * @param examId the exam id
     * @param registeredSubjectId the registered subject id
     * @return the subject exam
     * @throws DaoException the dao exception
     */
    SubjectExamDto getSubjectExam(int examId, int registeredSubjectId) throws DaoException;

    /**
     * Creates the.
     * 
     * @param examId the exam id
     * @param registeredSubjectId the registered subject id
     * @param maximumMarks the maximum marks
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(int examId, int registeredSubjectId, int maximumMarks) throws DaoException;

    /**
     * Update.
     * 
     * @param subjectExamId the subject exam id
     * @param examId the exam id
     * @param registeredSubjectId the registered subject id
     * @param maximumMarks the maximum marks
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int subjectExamId, int examId, int registeredSubjectId, int maximumMarks) throws DaoException;
}
