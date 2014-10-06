package com.myschool.exim.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.exam.dto.StudentExamDto;
import com.myschool.exam.dto.StudentInExamDto;

/**
 * The Interface StudentExamDao.
 */
public interface StudentExamDao {

    /**
     * Gets the students in exam.
     *
     * @param examId the exam id
     * @param classId the class id
     * @return the students in exam
     * @throws DaoException the dao exception
     */
    List<StudentInExamDto> getStudentsInExam(int examId, int classId) throws DaoException;

    /**
     * Gets the students in exam.
     * 
     * @param studentId the student id
     * @param examId the exam id
     * @param classId the class id
     * @return the students in exam
     * @throws DaoException the dao exception
     */
    StudentInExamDto getStudentsInExam(int studentId, int examId, int classId) throws DaoException;

    /**
     * Creates the.
     *
     * @param studentId the student id
     * @param studentExam the student exam
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(int studentId, StudentExamDto studentExam) throws DaoException;

    /**
     * Update.
     *
     * @param studentExam the student exam
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(StudentExamDto studentExam) throws DaoException;

    /**
     * Gets the student exam.
     *
     * @param studentId the student id
     * @param subjectExamId the subject exam id
     * @return the student exam
     * @throws DaoException the dao exception
     */
    StudentExamDto getStudentExam(int studentId, int subjectExamId) throws DaoException;

    /**
     * Gets the student subject marks in exam.
     *
     * @param examId the exam id
     * @param admissionNumber the admission number
     * @return the student subject marks in exam
     * @throws DaoException the dao exception
     */
    List<StudentExamDto> getStudentSubjectMarksInExam(int examId, String admissionNumber) throws DaoException;

    /**
     * Gets the student subject marks.
     * 
     * @param admissionNumber the admission number
     * @param subjectId the subject id
     * @return the student subject marks
     * @throws DaoException the dao exception
     */
    List<StudentExamDto> getStudentSubjectMarks(String admissionNumber,
            int subjectId) throws DaoException;

}
