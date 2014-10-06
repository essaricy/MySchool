package com.myschool.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.employee.domain.EmployeeSubjectManager;
import com.myschool.employee.dto.EmployeeSubjectDto;

/**
 * The Class EmployeeSubjectServiceImpl.
 */
@Service
public class EmployeeSubjectServiceImpl implements EmployeeSubjectService {

    /** The employee subject manager. */
    @Autowired
    private EmployeeSubjectManager employeeSubjectManager;

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(EmployeeSubjectDto employeeSubject) throws ServiceException {
        throw new ServiceException("Use create(employeeNumber, EmployeeSubjectDto)");
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeSubjectService#create(java.lang.String, com.myschool.employee.dto.EmployeeSubjectDto)
     */
    @Override
    public boolean create(String employeeNumber, EmployeeSubjectDto employeeSubject) throws ServiceException {
        try {
            return employeeSubjectManager.create(employeeNumber, employeeSubject);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#getAll()
     */
    @Override
    public List<EmployeeSubjectDto> getAll() throws ServiceException {
        throw new ServiceException("Use get(employeeNumber)");
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#get(int)
     */
    @Override
    public EmployeeSubjectDto get(int employeeSubjectId) throws ServiceException {
        try {
            return employeeSubjectManager.get(employeeSubjectId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int employeeSubjectId, EmployeeSubjectDto employeeSubject)
            throws ServiceException {
        try {
            return employeeSubjectManager.update(employeeSubjectId, employeeSubject);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#delete(int)
     */
    @Override
    public boolean delete(int employeeSubjectId) throws ServiceException {
        try {
            return employeeSubjectManager.delete(employeeSubjectId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeSubjectService#validate(com.myschool.employee.dto.EmployeeSubjectDto)
     */
    @Override
    public void validate(EmployeeSubjectDto employeeSubject) throws ServiceException {
        try {
            employeeSubjectManager.validate(employeeSubject);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeSubjectService#validate(java.util.List)
     */
    @Override
    public void validate(List<EmployeeSubjectDto> registeredSubjects) throws ServiceException {
        try {
            employeeSubjectManager.validate(registeredSubjects);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeSubjectService#getByEmployee(java.lang.String)
     */
    @Override
    public List<EmployeeSubjectDto> getByEmployee(String employeeNumber) throws ServiceException {
        try {
            return employeeSubjectManager.get(employeeNumber);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

}
