package com.myschool.employee.service;

import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.common.exception.ServiceException;
import com.myschool.employee.dto.EmployeePromotion;

/**
 * The Interface EmployeePromotionService.
 */
public interface EmployeePromotionService extends Servicable<EmployeePromotion> {

    /**
     * Creates the.
     * 
     * @param employeeNumber the employee number
     * @param employeePromotion the employee promotion
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean create(String employeeNumber, EmployeePromotion employeePromotion)
            throws ServiceException;

    /**
     * Validate.
     * 
     * @param employeePromotion the employee promotion
     * @throws ServiceException the service exception
     */
    public void validate(EmployeePromotion employeePromotion) throws ServiceException;

    /**
     * Validate.
     * 
     * @param employeePromotions the employee promotions
     * @throws ServiceException the service exception
     */
    void validate(List<EmployeePromotion> employeePromotions) throws ServiceException;

    /**
     * Gets the by employee.
     * 
     * @param employeeNumber the employee number
     * @return the by employee
     * @throws ServiceException the service exception
     */
    List<EmployeePromotion> getByEmployee(String employeeNumber) throws ServiceException;

}
