package com.myschool.employee.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.employee.dto.EmployeeEducation;

/**
 * The Interface EmployeeEducationDao.
 */
public interface EmployeeEducationDao {

    /**
     * Gets the.
     * 
     * @param employeeEducationId the employee education id
     * @return the employee education
     * @throws DaoException the dao exception
     */
    EmployeeEducation get(int employeeEducationId) throws DaoException;

    /**
     * Gets the by employee.
     * 
     * @param employeeId the employee id
     * @return the employee educations
     * @throws DaoException the dao exception
     */
    List<EmployeeEducation> getByEmployee(int employeeId) throws DaoException;

    /**
     * Creates the.
     * 
     * @param employeeId the employee id
     * @param employeeEducation the employee education
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(int employeeId, EmployeeEducation employeeEducation) throws DaoException;

    /**
     * Creates the.
     * 
     * @param employeeId the employee id
     * @param employeeEducationList the employee education list
     * @throws DaoException the dao exception
     */
    void create(int employeeId, List<EmployeeEducation> employeeEducationList) throws DaoException;

    /**
     * Update.
     * 
     * @param employeeEducationId the employee education id
     * @param employeeEducation the employee education
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int employeeEducationId, EmployeeEducation employeeEducation) throws DaoException;

    /**
     * Gets the.
     * 
     * @param employeeId the employee id
     * @param employeeEducation the employee education
     * @return the employee education
     * @throws DaoException the dao exception
     */
    EmployeeEducation get(int employeeId, EmployeeEducation employeeEducation) throws DaoException;

    /**
     * Delete.
     * 
     * @param educationId the education id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(int educationId) throws DaoException;

    /**
     * Gets the by employee.
     * 
     * @param employeeNumber the employee number
     * @return the by employee
     * @throws DaoException the dao exception
     */
    List<EmployeeEducation> getByEmployee(String employeeNumber) throws DaoException;

    /**
     * Update.
     * 
     * @param employeeEducations the employee educations
     * @throws DaoException the dao exception
     */
    void update(List<EmployeeEducation> employeeEducations) throws DaoException;

}
