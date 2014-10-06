package com.myschool.employee.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.dto.EmployeeSearchCriteriaDto;

/**
 * The Interface EmployeeDao.
 */
public interface EmployeeDao {
    
    /**
     * Gets the.
     * 
     * @param employeeNumber the admission number
     * @return the Employee
     * @throws DaoException the dao exception
     */
    EmployeeDto get(String employeeNumber) throws DaoException;

    /**
     * Update.
     * 
     * @param employeeId the employee id
     * @param employeeDto the employee dto
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int employeeId, EmployeeDto employeeDto) throws DaoException;

    /**
     * Creates the.
     * 
     * @param employeeDto the employee dto
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    int create(EmployeeDto employeeDto) throws DaoException;

    /**
     * Delete.
     *
     * @param employeeNumber the employee number
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(String employeeNumber) throws DaoException;

    /**
     * Gets the.
     * 
     * @param employeeId the employee id
     * @return the employee
     * @throws DaoException the dao exception
     */
    EmployeeDto get(int employeeId) throws DaoException;

    /**
     * Gets the last employee number.
     *
     * @return the next employee number
     * @throws DaoException the dao exception
     */
    String getLastEmployeeNumber() throws DaoException;

    /**
     * Gets the all.
     *
     * @param employeeSearchCriteriaDto the employee search criteria dto
     * @return the all
     * @throws DaoException the dao exception
     */
    List<EmployeeDto> getAll(EmployeeSearchCriteriaDto employeeSearchCriteriaDto) throws DaoException;
}
