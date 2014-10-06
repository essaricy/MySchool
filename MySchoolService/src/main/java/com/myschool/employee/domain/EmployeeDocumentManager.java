package com.myschool.employee.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.dto.DocumentDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.employee.dao.EmployeeDocumentDao;
import com.myschool.employee.dto.EmployeeDocument;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.validator.EmployeeDocumentValidator;

/**
 * The Class EmployeeDocumentManager.
 */
@Component
public class EmployeeDocumentManager {

    /** The employee document dao. */
    @Autowired
    private EmployeeDocumentDao employeeDocumentDao;

    /** The employee manager. */
    @Autowired
    private EmployeeManager employeeManager;

    /** The employee document validator. */
    @Autowired
    private EmployeeDocumentValidator employeeDocumentValidator;

    /**
     * Gets the.
     * 
     * @param employeeDocumentId the employee document id
     * @return the employee document
     * @throws DataException the data exception
     */
    public EmployeeDocument get(int employeeDocumentId) throws DataException {
        try {
            return employeeDocumentDao.get(employeeDocumentId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Creates the.
     * 
     * @param employeeNumber the employee number
     * @param employeeDocument the employee document
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(String employeeNumber, EmployeeDocument employeeDocument) throws DataException {
        try {
            EmployeeDto employee = employeeManager.get(employeeNumber);
            if (employee == null) {
                throw new DataException("There is no employee with number '" + employeeNumber + "'");
            }
            employeeDocumentValidator.validate(employeeDocument);
            return employeeDocumentDao.create(employee.getEmployeeId(), employeeDocument) > 0;
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Validate.
     * 
     * @param employeeDocument the employee document
     * @throws DataException the data exception
     */
    public void validate(EmployeeDocument employeeDocument) throws DataException {
        try {
            employeeDocumentValidator.validate(employeeDocument);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Update.
     * 
     * @param employeeDocumentId the employee document id
     * @param employeeDocument the employee document
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int employeeDocumentId,
            EmployeeDocument employeeDocument) throws DataException {
        try {
            employeeDocumentValidator.validate(employeeDocumentId, employeeDocument);
            return employeeDocumentDao.update(employeeDocumentId, employeeDocument);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Delete.
     * 
     * @param employeeDocumentId the employee document id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int employeeDocumentId) throws DataException {
        try {
            employeeDocumentValidator.validate(employeeDocumentId);
            return employeeDocumentDao.delete(employeeDocumentId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Validate.
     * 
     * @param employeeDocuments the employee documents
     * @throws DataException the data exception
     */
    public void validate(List<EmployeeDocument> employeeDocuments) throws DataException {
        DocumentDto document = null;
        EmployeeDocument employeeDocument = null;
        EmployeeDocument employeeDocument2 = null;
        try {
            int documentsSize = (employeeDocuments == null) ? 0 : employeeDocuments.size();
            for (int index=0; index < documentsSize; index++) {
                employeeDocument = employeeDocuments.get(index);
                // Check if all the documents are valid or not.
                employeeDocumentValidator.validate(employeeDocument);
                document = employeeDocument.getDocument();
                int documentId = document.getDocumentId();
                // Check if any duplicate documents exist.
                for (int jindex=0; jindex < employeeDocuments.size(); jindex++) {
                    if (index != jindex) {
                        employeeDocument2 = employeeDocuments.get(jindex);
                        if (documentId == employeeDocument2.getDocument().getDocumentId()) {
                            throw new DataException("Document '" + document.getName() + "' is already present.");
                        }
                    }
                }
            }
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Gets the by employee.
     * 
     * @param employeeNumber the employee number
     * @return the by employee
     * @throws DataException the data exception
     */
    public List<EmployeeDocument> getByEmployee(String employeeNumber) throws DataException {
        try {
            return employeeDocumentDao.getByEmployee(employeeNumber);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

}
