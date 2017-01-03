package com.myschool.employee.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.ValidationException;
import com.myschool.employee.dao.EmployeePromotionDao;
import com.myschool.employee.dto.DesignationDto;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.dto.EmployeePromotion;
import com.myschool.employee.validator.EmployeePromotionValidator;
import com.quasar.core.exception.DataException;

/**
 * The Class EmployeePromotionManager.
 */
@Component
public class EmployeePromotionManager {

    /** The employee manager. */
    @Autowired
    private EmployeeManager employeeManager;

    /** The employee promotion validator. */
    @Autowired
    private EmployeePromotionValidator employeePromotionValidator;

    /** The employee promotion dao. */
    @Autowired
    private EmployeePromotionDao employeePromotionDao;

    /**
     * Creates the.
     * 
     * @param employeeNumber the employee number
     * @param employeePromotion the employee promotion
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(String employeeNumber,
            EmployeePromotion employeePromotion) throws DataException {
        try {
            EmployeeDto employee = employeeManager.get(employeeNumber);
            if (employee == null) {
                throw new DataException("There is no employee with number '" + employeeNumber + "'");
            }
            employeePromotionValidator.validate(employeePromotion);
            return employeePromotionDao.create(employee.getEmployeeId(), employeePromotion) > 0;
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the.
     * 
     * @param promotionId the promotion id
     * @return the employee promotion
     * @throws DataException the data exception
     */
    public EmployeePromotion get(int promotionId) throws DataException {
        try {
            return employeePromotionDao.get(promotionId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Update.
     * 
     * @param promotionId the promotion id
     * @param employeePromotion the employee promotion
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int promotionId, EmployeePromotion employeePromotion)
            throws DataException {
        try {
            employeePromotionValidator.validate(promotionId, employeePromotion);
            return employeePromotionDao.update(promotionId, employeePromotion);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Delete.
     * 
     * @param promotionId the promotion id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int promotionId) throws DataException {
        try {
            employeePromotionValidator.validate(promotionId);
            return employeePromotionDao.delete(promotionId);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Validate.
     * 
     * @param employeePromotion the employee promotion
     * @throws DataException the data exception
     */
    public void validate(EmployeePromotion employeePromotion) throws DataException {
        try {
            employeePromotionValidator.validate(employeePromotion);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Validate.
     * 
     * @param employeePromotions the employee promotions
     * @throws DataException the data exception
     */
    public void validate(List<EmployeePromotion> employeePromotions) throws DataException {
        EmployeePromotion employeePromotion = null;
        EmployeePromotion employeePromotion2 = null;
        try {
            int size = (employeePromotions == null) ? 0 : employeePromotions.size();
            for (int index=0; index < size; index++) {
                employeePromotion = employeePromotions.get(index);
                // Check if all the employee Promotion are valid or not.
                employeePromotionValidator.validate(employeePromotion);
                DesignationDto priorDesignation = employeePromotion.getPriorDesignation();
                DesignationDto currentDesignation = employeePromotion.getCurrentDesignation();
                String effectiveFrom = employeePromotion.getEffectiveFrom();
                // Check if any duplicate employee Promotion records exist.
                for (int jindex=0; jindex < employeePromotions.size(); jindex++) {
                    if (index != jindex) {
                        employeePromotion2 = employeePromotions.get(jindex);
                        if (priorDesignation.getDesignationId() == employeePromotion2.getPriorDesignation().getDesignationId()) {
                            throw new DataException("Employee promotion with previous designation '" + priorDesignation.getDesignationId() + "' is already present.");
                        } else if (currentDesignation.getDesignationId() == employeePromotion2.getCurrentDesignation().getDesignationId()) {
                            throw new DataException("Employee promotion with next designation '" + priorDesignation.getDesignationId() + "' is already present.");
                        } else if (effectiveFrom.equalsIgnoreCase(employeePromotion2.getEffectiveFrom())) {
                            throw new DataException("Employee promotion with effective from '" + effectiveFrom + "' is already present.");
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
    public List<EmployeePromotion> getByEmployee(String employeeNumber) throws DataException {
        try {
            return employeePromotionDao.getByEmployee(employeeNumber);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

}
