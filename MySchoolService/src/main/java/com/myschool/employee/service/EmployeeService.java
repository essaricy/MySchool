package com.myschool.employee.service;

import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.common.exception.ServiceException;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.dto.EmployeeSearchCriteriaDto;

/**
 * The Interface EmployeeService.
 */
public interface EmployeeService extends Servicable<EmployeeDto> {

    /**
     * Gets the.
     *
     * @param employeeNumber the employee number
     * @return the employee dto
     * @throws ServiceException the service exception
     */
    EmployeeDto get(String employeeNumber) throws ServiceException;

    /**
     * Update employee image.
     *
     * @param secureToken the secure token
     * @param employeeNumber the employee number
     * @throws ServiceException the service exception
     */
    void updateEmployeeImage(String secureToken, String employeeNumber) throws ServiceException;

    /**
     * Delete.
     *
     * @param employeeNumber the employee number
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean delete(String employeeNumber) throws ServiceException;

    /**
     * Gets the last employee number.
     *
     * @return the next employee number
     * @throws ServiceException the service exception
     */
    String getLastEmployeeNumber() throws ServiceException;

    /**
     * Gets the all.
     *
     * @param employeeSearchCriteriaDto the employee search criteria dto
     * @return the all
     * @throws ServiceException the service exception
     */
    List<EmployeeDto> getAll(EmployeeSearchCriteriaDto employeeSearchCriteriaDto) throws ServiceException;

}
