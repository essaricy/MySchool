package com.myschool.employee.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.InvalidDataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.util.DateUtil;
import com.myschool.employee.dao.EmployeeExperienceDao;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.dto.EmployeeExperience;
import com.myschool.employee.validator.EmployeeExperienceValidator;

/**
 * The Class EmployeeExperienceManager.
 */
@Component
public class EmployeeExperienceManager {

    /** The employee manager. */
    @Autowired
    private EmployeeManager employeeManager;

    /** The employee experience validator. */
    @Autowired
    private EmployeeExperienceValidator employeeExperienceValidator;

    /** The employee experience dao. */
    @Autowired
    private EmployeeExperienceDao employeeExperienceDao;

    /**
     * Gets the.
     * 
     * @param experienceId the experience id
     * @return the employee experience
     * @throws DataException the data exception
     */
    public EmployeeExperience get(int experienceId) throws DataException {
        try {
            return employeeExperienceDao.get(experienceId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Update.
     * 
     * @param experienceId the experience id
     * @param employeeExperience the employee experience
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int experienceId,
            EmployeeExperience employeeExperience) throws DataException {
        try {
            employeeExperienceValidator.validate(experienceId, employeeExperience);
            return employeeExperienceDao.update(experienceId, employeeExperience);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Delete.
     * 
     * @param experienceId the experience id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int experienceId) throws DataException {
        try {
            employeeExperienceValidator.validate(experienceId);
            return employeeExperienceDao.delete(experienceId);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Validate.
     * 
     * @param employeeExperience the employee experience
     * @throws DataException the data exception
     */
    public void validate(EmployeeExperience employeeExperience) throws DataException {
        try {
            employeeExperienceValidator.validate(employeeExperience);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Creates the.
     * 
     * @param employeeNumber the employee number
     * @param employeeExperience the employee experience
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(String employeeNumber,
            EmployeeExperience employeeExperience) throws DataException {
        try {
            EmployeeDto employee = employeeManager.get(employeeNumber);
            if (employee == null) {
                throw new DataException("There is no employee with number '" + employeeNumber + "'");
            }
            employeeExperienceValidator.validate(employeeExperience);
            return employeeExperienceDao.create(employee.getEmployeeId(), employeeExperience) > 0;
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Validate.
     * 
     * @param employeeExperiences the employee experiences
     * @throws DataException the data exception
     */
    public void validate(List<EmployeeExperience> employeeExperiences) throws DataException {
        EmployeeExperience employeeExperience = null;
        EmployeeExperience employeeExperience2 = null;

        try {
            int size = (employeeExperiences == null) ? 0 : employeeExperiences.size();
            for (int index=0; index < size; index++) {
                employeeExperience = employeeExperiences.get(index);
                // Check if all the experience records are valid or not.
                employeeExperienceValidator.validate(employeeExperience);
                String fromDate = employeeExperience.getFromDate();
                String toDate = employeeExperience.getToDate();
                // Check if any duplicate experience records exist.
                for (int jindex=0; jindex < employeeExperiences.size(); jindex++) {
                    if (index != jindex) {
                        employeeExperience2 = employeeExperiences.get(jindex);
                        String fromDate2 = employeeExperience2.getFromDate();
                        String toDate2 = employeeExperience2.getToDate();
                        DateUtil.checkDateOverlap(fromDate, toDate, fromDate2, toDate2);
                    }
                }
            }
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (InvalidDataException invalidDataException) {
            invalidDataException.printStackTrace();
            throw new DataException("Employee Experience dates clash.");
        }
    }

    /**
     * Gets the by employee.
     * 
     * @param employeeNumber the employee number
     * @return the by employee
     * @throws DataException the data exception
     */
    public List<EmployeeExperience> getByEmployee(String employeeNumber) throws DataException {
        try {
            return employeeExperienceDao.getByEmployee(employeeNumber);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

}
