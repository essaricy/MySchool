package com.myschool.employee.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.ValidationException;
import com.myschool.employee.dao.EmployeeSubjectDao;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.dto.EmployeeSubjectDto;
import com.quasar.core.exception.DataException;

/**
 * The Class EmployeeSubjectManager.
 */
@Component
public class EmployeeSubjectManager {

    /** The employee manager. */
    @Autowired
    private EmployeeManager employeeManager;

    /** The employee subject dao. */
    @Autowired
    private EmployeeSubjectDao employeeSubjectDao;

    /** The employee subject validator. */
    @Autowired
    private EmployeeSubjectValidator employeeSubjectValidator;

    /**
     * Creates the.
     * 
     * @param employeeNumber the employee number
     * @param employeeSubject the employee subject
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(String employeeNumber,
            EmployeeSubjectDto employeeSubject) throws DataException {
        try {
            EmployeeDto employee = employeeManager.get(employeeNumber);
            if (employee == null) {
                throw new DataException("There is no employee with number '" + employeeNumber + "'");
            }
            employeeSubjectValidator.validate(employeeSubject);
            return (employeeSubjectDao.create(employee.getEmployeeId(), employeeSubject) > 0);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the.
     * 
     * @param employeeNumber the employee number
     * @return the list
     * @throws DataException the data exception
     */
    public List<EmployeeSubjectDto> get(String employeeNumber) throws DataException {
        try {
            return employeeSubjectDao.getByEmployee(employeeNumber);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the.
     * 
     * @param employeeSubjectId the employee subject id
     * @return the employee subject dto
     * @throws DataException the data exception
     */
    public EmployeeSubjectDto get(int employeeSubjectId) throws DataException {
        try {
            return employeeSubjectDao.get(employeeSubjectId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Update.
     * 
     * @param employeeSubjectId the employee subject id
     * @param employeeSubject the employee subject
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int employeeSubjectId,
            EmployeeSubjectDto employeeSubject) throws DataException {
        try {
            employeeSubjectValidator.validate(employeeSubject);
            return employeeSubjectDao.update(employeeSubjectId, employeeSubject);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Delete.
     * 
     * @param employeeSubjectId the employee subject id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int employeeSubjectId) throws DataException {
        try {
            return employeeSubjectDao.delete(employeeSubjectId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Validate.
     * 
     * @param employeeSubject the employee subject
     * @throws DataException the data exception
     */
    public void validate(EmployeeSubjectDto employeeSubject) throws DataException {
        try {
            employeeSubjectValidator.validate(employeeSubject);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Validate.
     * 
     * @param employeeSubjects the employee subjects
     * @throws DataException the data exception
     */
    public void validate(List<EmployeeSubjectDto> employeeSubjects) throws DataException {
        EmployeeSubjectDto employeeSubject = null;
        EmployeeSubjectDto employeeSubject2 = null;

        try {
            int size = (employeeSubjects == null) ? 0 : employeeSubjects.size();
            for (int index=0; index < size; index++) {
                employeeSubject = employeeSubjects.get(index);
                // Check if all the documents are valid or not.
                employeeSubjectValidator.validate(employeeSubject);
                // Check if any duplicate documents exist.
                for (int jindex=0; jindex < employeeSubjects.size(); jindex++) {
                    if (index != jindex) {
                        employeeSubject2 = employeeSubjects.get(jindex);
                        if (employeeSubject.getRegisteredSubject().getSubjectId() == employeeSubject2.getRegisteredSubject().getSubjectId()) {
                            throw new DataException("Subject is already assigned to Employee.");
                        }
                    }
                }
            }
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

}
