package com.myschool.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.employee.domain.EmployeeExperienceManager;
import com.myschool.employee.dto.EmployeeExperience;

/**
 * The Class EmployeeExperienceServiceImpl.
 */
@Service
public class EmployeeExperienceServiceImpl implements EmployeeExperienceService {

    /** The employee experience manager. */
    @Autowired
    private EmployeeExperienceManager employeeExperienceManager;

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(EmployeeExperience employeeExperience) throws ServiceException {
        throw new ServiceException("Use create(employeeNumber, EmployeeExperience)");
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeExperienceService#create(java.lang.String, com.myschool.employee.dto.EmployeeExperience)
     */
    @Override
    public boolean create(String employeeNumber,
            EmployeeExperience employeeExperience) throws ServiceException {
        try {
            return employeeExperienceManager.create(employeeNumber, employeeExperience);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#getAll()
     */
    @Override
    public List<EmployeeExperience> getAll() throws ServiceException {
        throw new ServiceException("Use get(experienceId)");
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#get(int)
     */
    @Override
    public EmployeeExperience get(int experienceId) throws ServiceException {
        try {
            return employeeExperienceManager.get(experienceId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int experienceId, EmployeeExperience employeeExperience)
            throws ServiceException {
        try {
            return employeeExperienceManager.update(experienceId, employeeExperience);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#delete(int)
     */
    @Override
    public boolean delete(int experienceId) throws ServiceException {
        try {
            return employeeExperienceManager.delete(experienceId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeExperienceService#validate(com.myschool.employee.dto.EmployeeExperience)
     */
    @Override
    public void validate(EmployeeExperience employeeExperience) throws ServiceException {
        try {
            employeeExperienceManager.validate(employeeExperience);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeExperienceService#validate(java.util.List)
     */
    @Override
    public void validate(List<EmployeeExperience> employeeExperiences) throws ServiceException {
        try {
            employeeExperienceManager.validate(employeeExperiences);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeExperienceService#getByEmployee(java.lang.String)
     */
    @Override
    public List<EmployeeExperience> getByEmployee(String employeeNumber) throws ServiceException {
        try {
            return employeeExperienceManager.getByEmployee(employeeNumber);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

}
