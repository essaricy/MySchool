package com.myschool.employee.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.employee.dto.EmployeeDocument;

/**
 * The Interface EmployeeDocumentDao.
 */
public interface EmployeeDocumentDao {

    /**
     * Gets the.
     * 
     * @param employeeDocumentId the employee document id
     * @return the employee document
     * @throws DaoException the dao exception
     */
    EmployeeDocument get(int employeeDocumentId) throws DaoException;

    /**
     * Gets the by employee.
     * 
     * @param employeeId the employee id
     * @return the by employee
     * @throws DaoException the dao exception
     */
    List<EmployeeDocument> getByEmployee(int employeeId) throws DaoException;

    /**
     * Gets the.
     * 
     * @param employeeId the employee id
     * @param documentId the document id
     * @return the employee document
     * @throws DaoException the dao exception
     */
    EmployeeDocument get(int employeeId, int documentId) throws DaoException;

    /**
     * Creates the.
     * 
     * @param employeeId the employee id
     * @param employeeDocument the employee document
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(int employeeId, EmployeeDocument employeeDocument) throws DaoException;

    /**
     * Creates the.
     * 
     * @param employeeId the employee id
     * @param employeeDocuments the employee documents
     * @throws DaoException the dao exception
     */
    void create(int employeeId, List<EmployeeDocument> employeeDocuments) throws DaoException;

    /**
     * Update.
     * 
     * @param employeeDocumentId the employee document id
     * @param employeeDocument the employee document
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int employeeDocumentId, EmployeeDocument employeeDocument)
            throws DaoException;

    /**
     * Delete.
     * 
     * @param employeeDocumentId the employee document id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(int employeeDocumentId) throws DaoException;

    /**
     * Gets the by employee.
     * 
     * @param employeeNumber the employee number
     * @return the by employee
     * @throws DaoException the dao exception
     */
    List<EmployeeDocument> getByEmployee(String employeeNumber) throws DaoException;

    /**
     * Update.
     * 
     * @param employeeDocuments the employee documents
     * @throws DaoException the dao exception
     */
    void update(List<EmployeeDocument> employeeDocuments) throws DaoException;

}
