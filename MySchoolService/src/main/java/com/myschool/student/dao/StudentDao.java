package com.myschool.student.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.student.dto.StudentDto;
import com.myschool.student.dto.StudentSearchCriteriaDto;

/**
 * The Interface StudentDao.
 */
public interface StudentDao {

    /**
     * Gets the.
     * 
     * @param admissionNumber the admission number
     * @return the student
     * @throws DaoException the dao exception
     */
    StudentDto get(String admissionNumber) throws DaoException;

    /**
     * Update.
     * 
     * @param studentId the student id
     * @param studentDto the student dto
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int studentId, StudentDto studentDto) throws DaoException;

    /**
     * Creates the.
     * 
     * @param studentDto the student dto
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    int create(StudentDto studentDto) throws DaoException;

    /**
     * Delete.
     *
     * @param admissionNumber the admission number
     * @throws DaoException the dao exception
     */
    void delete(String admissionNumber) throws DaoException;

    /**
     * Gets the current ay students.
     *
     * @param classId the class id
     * @return the current ay students
     * @throws DaoException the dao exception
     */
    List<StudentDto> getCurrentAyStudents(int classId) throws DaoException;

    /**
     * Promote student.
     *
     * @param studentId the student id
     * @param nextClassId the next class id
     * @param nextAcademicYear the next academic year
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean promoteStudent(int studentId, int nextClassId, String nextAcademicYear) throws DaoException;

    /**
     * Gets the.
     * 
     * @param studentId the student id
     * @return the student
     * @throws DaoException the dao exception
     */
    StudentDto get(int studentId) throws DaoException;

    /**
     * Terminate admission.
     * 
     * @param admissionNumber the admission number
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean terminateAdmission(String admissionNumber) throws DaoException;

    /**
     * Gets the last admission number.
     *
     * @return the last admission number
     * @throws DaoException the dao exception
     */
    String getLastAdmissionNumber() throws DaoException;

    /**
     * Search.
     *
     * @param studentSearchCriteriaDto the student search criteria dto
     * @return the list
     * @throws DaoException the dao exception
     */
    List<StudentDto> search(StudentSearchCriteriaDto studentSearchCriteriaDto) throws DaoException;


}
