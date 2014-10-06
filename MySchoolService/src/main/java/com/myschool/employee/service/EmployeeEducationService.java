package com.myschool.employee.service;

import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.common.exception.ServiceException;
import com.myschool.employee.dto.EmployeeEducation;

/**
 * The Interface EmployeeEducationService.
 */
public interface EmployeeEducationService extends Servicable<EmployeeEducation> {

    /**
     * Validate.
     * 
     * @param employeeEducation the employee education
     * @throws ServiceException the service exception
     */
    public void validate(EmployeeEducation employeeEducation) throws ServiceException;

    /**
     * Creates the.
     * 
     * @param employeeNumber the employee number
     * @param employeeEducation the employee education
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean create(String employeeNumber, EmployeeEducation employeeEducation)
            throws ServiceException;

    /**
     * Validate.
     * 
     * @param employeeEducations the employee educations
     * @throws ServiceException the service exception
     */
    void validate(List<EmployeeEducation> employeeEducations) throws ServiceException;

    /**
     * Gets the by employee.
     * 
     * @param employeeNumber the employee number
     * @return the by employee
     * @throws ServiceException the service exception
     */
    List<EmployeeEducation> getByEmployee(String employeeNumber) throws ServiceException;

}
