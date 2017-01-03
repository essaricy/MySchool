package com.myschool.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.ServiceException;
import com.myschool.employee.domain.EmployeeDocumentManager;
import com.myschool.employee.dto.EmployeeDocument;
import com.quasar.core.exception.DataException;

/**
 * The Class EmployeeDocumentServiceImpl.
 */
@Service
public class EmployeeDocumentServiceImpl implements EmployeeDocumentService {

    /** The employee document manager. */
    @Autowired
    private EmployeeDocumentManager employeeDocumentManager;

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(EmployeeDocument employeeDocument) throws ServiceException {
        throw new ServiceException("Use create(employeeNumber, EmployeeDocument)");
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeDocumentService#create(java.lang.String, com.myschool.employee.dto.EmployeeDocument)
     */
    @Override
    public boolean create(String employeeNumber, EmployeeDocument employeeDocument) throws ServiceException {
        try {
            return employeeDocumentManager.create(employeeNumber, employeeDocument);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#getAll()
     */
    @Override
    public List<EmployeeDocument> getAll() throws ServiceException {
        throw new ServiceException("Use get(employeeDocumentId)");
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#get(int)
     */
    @Override
    public EmployeeDocument get(int employeeDocumentId) throws ServiceException {
        try {
            return employeeDocumentManager.get(employeeDocumentId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int employeeDocumentId,
            EmployeeDocument employeeDocument) throws ServiceException {
        try {
            return employeeDocumentManager.update(employeeDocumentId, employeeDocument);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#delete(int)
     */
    @Override
    public boolean delete(int employeeDocumentId) throws ServiceException {
        try {
            return employeeDocumentManager.delete(employeeDocumentId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeDocumentService#validate(com.myschool.employee.dto.EmployeeDocument)
     */
    @Override
    public void validate(EmployeeDocument employeeDocument) throws ServiceException {
        try {
            employeeDocumentManager.validate(employeeDocument);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeDocumentService#validate(java.util.List)
     */
    @Override
    public void validate(List<EmployeeDocument> employeeDocuments) throws ServiceException {
        try {
            employeeDocumentManager.validate(employeeDocuments);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeDocumentService#getByEmployee(java.lang.String)
     */
    @Override
    public List<EmployeeDocument> getByEmployee(String employeeNumber) throws ServiceException {
        try {
            return employeeDocumentManager.getByEmployee(employeeNumber);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }
}
