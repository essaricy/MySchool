package com.myschool.employee.service;

import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.common.exception.ServiceException;
import com.myschool.employee.dto.EmployeeSubjectDto;

/**
 * The Interface EmployeeSubjectService.
 */
public interface EmployeeSubjectService extends Servicable<EmployeeSubjectDto> {

    /**
     * Creates the.
     * 
     * @param employeeNumber the employee number
     * @param employeeSubject the employee subject
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean create(String employeeNumber, EmployeeSubjectDto employeeSubject) throws ServiceException;

    /**
     * Validate.
     * 
     * @param employeeSubject the employee subject
     * @throws ServiceException the service exception
     */
    void validate(EmployeeSubjectDto employeeSubject) throws ServiceException;

    /**
     * Validate.
     * 
     * @param employeeSubjects the employee subjects
     * @throws ServiceException the service exception
     */
    void validate(List<EmployeeSubjectDto> employeeSubjects) throws ServiceException;

    /**
     * Gets the by employee.
     * 
     * @param employeeNumber the employee number
     * @return the by employee
     * @throws ServiceException the service exception
     */
    List<EmployeeSubjectDto> getByEmployee(String employeeNumber) throws ServiceException;

}
