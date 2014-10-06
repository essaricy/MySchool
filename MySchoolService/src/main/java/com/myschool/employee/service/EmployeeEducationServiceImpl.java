package com.myschool.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.employee.domain.EmployeeEducationManager;
import com.myschool.employee.dto.EmployeeEducation;

/**
 * The Class EmployeeEducationServiceImpl.
 */
@Service
public class EmployeeEducationServiceImpl implements EmployeeEducationService {

    /** The employee education manager. */
    @Autowired
    private EmployeeEducationManager employeeEducationManager;

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(EmployeeEducation employeeEducation) throws ServiceException {
        throw new ServiceException("Use create(employeeNumber, employeeEducation)");
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeEducationService#create(java.lang.String, com.myschool.employee.dto.EmployeeEducation)
     */
    @Override
    public boolean create(String employeeNumber,
            EmployeeEducation employeeEducation) throws ServiceException {
        try {
            return employeeEducationManager.create(employeeNumber, employeeEducation);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#getAll()
     */
    @Override
    public List<EmployeeEducation> getAll() throws ServiceException {
        throw new ServiceException("Use get(educationId)");
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#get(int)
     */
    @Override
    public EmployeeEducation get(int educationId) throws ServiceException {
        try {
            return employeeEducationManager.get(educationId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int educationId, EmployeeEducation employeeEducation)
            throws ServiceException {
        try {
            return employeeEducationManager.update(educationId, employeeEducation);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#delete(int)
     */
    @Override
    public boolean delete(int educationId) throws ServiceException {
        try {
            return employeeEducationManager.delete(educationId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeEducationService#validate(com.myschool.employee.dto.EmployeeEducation)
     */
    @Override
    public void validate(EmployeeEducation employeeEducation) throws ServiceException {
        try {
            employeeEducationManager.validate(employeeEducation);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeEducationService#validate(java.util.List)
     */
    public void validate(List<EmployeeEducation> employeeEducations) throws ServiceException {
        try {
            employeeEducationManager.validate(employeeEducations);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeEducationService#getByEmployee(java.lang.String)
     */
    @Override
    public List<EmployeeEducation> getByEmployee(String employeeNumber) throws ServiceException {
        try {
            return employeeEducationManager.getByEmployee(employeeNumber);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

}
