package com.myschool.employee.service;

import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.common.exception.ServiceException;
import com.myschool.employee.dto.EmployeeDocument;

/**
 * The Interface EmployeeDocumentService.
 */
public interface EmployeeDocumentService extends Servicable<EmployeeDocument> {

    /**
     * Validate.
     * 
     * @param employeeDocument the employee document
     * @throws ServiceException the service exception
     */
    void validate(EmployeeDocument employeeDocument) throws ServiceException;

    /**
     * Creates the.
     * 
     * @param employeeNumber the employee number
     * @param employeeDocument the employee document
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean create(String employeeNumber, EmployeeDocument employeeDocument)
            throws ServiceException;

    /**
     * Validate.
     * 
     * @param employeeDocuments the employee documents
     * @throws ServiceException the service exception
     */
    void validate(List<EmployeeDocument> employeeDocuments) throws ServiceException;

    /**
     * Gets the by employee.
     * 
     * @param employeeNumber the employee number
     * @return the by employee
     * @throws ServiceException the service exception
     */
    List<EmployeeDocument> getByEmployee(String employeeNumber) throws ServiceException;

}
