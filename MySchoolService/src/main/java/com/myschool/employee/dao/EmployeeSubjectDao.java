package com.myschool.employee.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.employee.dto.EmployeeSubjectDto;

/**
 * The Interface EmployeeSubjectDao.
 */
public interface EmployeeSubjectDao {

    /**
     * Gets the by employee.
     * 
     * @param employeeId the employee id
     * @return the by employee
     * @throws DaoException the dao exception
     */
    List<EmployeeSubjectDto> getByEmployee(int employeeId) throws DaoException;

    /**
     * Creates the.
     * 
     * @param employeeId the employee id
     * @param employeeSubject the employee subject
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(int employeeId, EmployeeSubjectDto employeeSubject) throws DaoException;

    /**
     * Creates the.
     * 
     * @param employeeId the employee id
     * @param employeeSubjects the employee subjects
     * @throws DaoException the dao exception
     */
    void create(int employeeId, List<EmployeeSubjectDto> employeeSubjects) throws DaoException;

    /**
     * Update.
     * 
     * @param employeeSubjectId the employee subject id
     * @param employeeSubject the employee subject
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int employeeSubjectId, EmployeeSubjectDto employeeSubject) throws DaoException;

    /**
     * Update.
     * 
     * @param employeeSubjects the employee subjects
     * @throws DaoException the dao exception
     */
    void update(List<EmployeeSubjectDto> employeeSubjects) throws DaoException;

    /**
     * Gets the.
     * 
     * @param employeeSubjectId the employee subject id
     * @return the employee subject dto
     * @throws DaoException the dao exception
     */
    EmployeeSubjectDto get(int employeeSubjectId) throws DaoException;

    /**
     * Delete.
     * 
     * @param employeeSubjectId the employee subject id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(int employeeSubjectId) throws DaoException;

    /**
     * Gets the by employee.
     * 
     * @param employeeNumber the employee number
     * @return the by employee
     * @throws DaoException the dao exception
     */
    List<EmployeeSubjectDto> getByEmployee(String employeeNumber) throws DaoException;

    /**
     * Gets the.
     * 
     * @param employeeId the employee id
     * @return the employee subject dto
     * @throws DaoException the dao exception
     */
    EmployeeSubjectDto get(int employeeId, int registeredSubjectId) throws DaoException;

}
