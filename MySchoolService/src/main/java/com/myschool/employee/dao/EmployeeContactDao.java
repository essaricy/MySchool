package com.myschool.employee.dao;

import com.myschool.common.exception.DaoException;
import com.myschool.employee.dto.EmployeeContact;

/**
 * The Interface EmployeeContactDao.
 */
public interface EmployeeContactDao {

    /**
     * Gets the.
     * 
     * @param employeeId the employee id
     * @return the employee contact
     * @throws DaoException the dao exception
     */
    EmployeeContact get(int employeeId) throws DaoException;

    /**
     * Creates the.
     * 
     * @param employeeId the employee id
     * @param employeeContact the employee contact
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean create(int employeeId, EmployeeContact employeeContact) throws DaoException;

    /**
     * Update.
     * 
     * @param employeeContactId the employee contact id
     * @param employeeContact the employee contact
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int employeeContactId, EmployeeContact employeeContact) throws DaoException;

}
