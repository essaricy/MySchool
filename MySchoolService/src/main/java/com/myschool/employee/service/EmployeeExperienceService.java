package com.myschool.employee.service;

import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.common.exception.ServiceException;
import com.myschool.employee.dto.EmployeeExperience;

/**
 * The Interface EmployeeExperienceService.
 */
public interface EmployeeExperienceService extends Servicable<EmployeeExperience> {

    /**
     * Creates the.
     * 
     * @param employeeNumber the employee number
     * @param employeeExperience the employee experience
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean create(String employeeNumber, EmployeeExperience employeeExperience)
            throws ServiceException;

    /**
     * Validate.
     * 
     * @param employeeExperience the employee experience
     * @throws ServiceException the service exception
     */
    public void validate(EmployeeExperience employeeExperience) throws ServiceException;

    /**
     * Validate.
     * 
     * @param employeeExperiences the employee experiences
     * @throws ServiceException the service exception
     */
    void validate(List<EmployeeExperience> employeeExperiences) throws ServiceException;

    /**
     * Gets the by employee.
     * 
     * @param employeeNumber the employee number
     * @return the by employee
     * @throws ServiceException the service exception
     */
    List<EmployeeExperience> getByEmployee(String employeeNumber) throws ServiceException;

}
