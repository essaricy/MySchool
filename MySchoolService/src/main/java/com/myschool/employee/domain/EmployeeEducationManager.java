package com.myschool.employee.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.employee.dao.EmployeeEducationDao;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.dto.EmployeeEducation;
import com.myschool.employee.validator.EmployeeEducationValidator;

/**
 * The Class EmployeeEducationManager.
 */
@Component
public class EmployeeEducationManager {

    /** The employee manager. */
    @Autowired
    private EmployeeManager employeeManager;

    /** The employee education validator. */
    @Autowired
    private EmployeeEducationValidator employeeEducationValidator;

    /** The employee education dao. */
    @Autowired
    private EmployeeEducationDao employeeEducationDao;

    /**
     * Creates the.
     * 
     * @param employeeNumber the employee number
     * @param employeeEducation the employee education
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(String employeeNumber, EmployeeEducation employeeEducation)
            throws DataException {
        try {
            EmployeeDto employee = employeeManager.get(employeeNumber);
            if (employee == null) {
                throw new DataException("There is no employee with number '" + employeeNumber + "'");
            }
            employeeEducationValidator.validate(employeeEducation);
            return employeeEducationDao.create(employee.getEmployeeId(), employeeEducation) > 0;
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the.
     * 
     * @param educationId the education id
     * @return the employee education
     * @throws DataException the data exception
     */
    public EmployeeEducation get(int educationId) throws DataException {
        try {
            return employeeEducationDao.get(educationId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Update.
     * 
     * @param educationId the education id
     * @param employeeEducation the employee education
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int educationId, EmployeeEducation employeeEducation)
            throws DataException {
        try {
            employeeEducationValidator.validate(educationId, employeeEducation);
            return employeeEducationDao.update(educationId, employeeEducation);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Delete.
     * 
     * @param educationId the education id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int educationId) throws DataException {
        try {
            employeeEducationValidator.validate(educationId);
            return employeeEducationDao.delete(educationId);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Validate.
     * 
     * @param employeeEducation the employee education
     * @throws DataException the data exception
     */
    public void validate(EmployeeEducation employeeEducation) throws DataException {
        try {
            employeeEducationValidator.validate(employeeEducation);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Validate.
     * 
     * @param employeeEducations the employee educations
     * @throws DataException the data exception
     */
    public void validate(List<EmployeeEducation> employeeEducations)
            throws DataException {
        EmployeeEducation employeeEducation = null;
        EmployeeEducation employeeEducation2 = null;
        try {
            int size = (employeeEducations == null) ? 0 : employeeEducations.size();
            if (size == 0) {
                throw new DataException("Employee Education details must be entered.");
            }
            for (int index=0; index < size; index++) {
                employeeEducation = employeeEducations.get(index);
                // Check if all the education records are valid or not.
                employeeEducationValidator.validate(employeeEducation);
                int yearOfGraduation = employeeEducation.getYearOfGraduation();
                // Check if any duplicate education records exist.
                for (int jindex = 0; jindex < employeeEducations.size(); jindex++) {
                    if (index != jindex) {
                        employeeEducation2 = employeeEducations.get(jindex);
                        if (yearOfGraduation == employeeEducation2.getYearOfGraduation()) {
                            throw new DataException("Education details for the year '" + yearOfGraduation + "' is already present.");
                        }
                    }
                }
            }
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(),
                    validationException);
        }
    }

    /**
     * Gets the by employee.
     * 
     * @param employeeNumber the employee number
     * @return the by employee
     * @throws DataException the data exception
     */
    public List<EmployeeEducation> getByEmployee(String employeeNumber) throws DataException {
        try {
            return employeeEducationDao.getByEmployee(employeeNumber);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

}
