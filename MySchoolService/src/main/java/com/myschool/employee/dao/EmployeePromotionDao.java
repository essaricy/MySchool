package com.myschool.employee.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.employee.dto.EmployeePromotion;

/**
 * The Interface EmployeePromotionDao.
 */
public interface EmployeePromotionDao {

    /**
     * Gets the.
     * 
     * @param employeePromotionId the employee promotion id
     * @return the employee promotion
     * @throws DaoException the dao exception
     */
    EmployeePromotion get(int employeePromotionId) throws DaoException;

    /**
     * Gets the by employee.
     * 
     * @param employeeId the employee id
     * @return the employee promotions
     * @throws DaoException the dao exception
     */
    List<EmployeePromotion> getByEmployee(int employeeId) throws DaoException;

    /**
     * Creates the.
     * 
     * @param employeeId the employee id
     * @param employeePromotion the employee promotion
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(int employeeId, EmployeePromotion employeePromotion)
            throws DaoException;

    /**
     * Creates the.
     * 
     * @param employeeId the employee id
     * @param employeePromotionList the employee promotion list
     * @throws DaoException the dao exception
     */
    void create(int employeeId, List<EmployeePromotion> employeePromotionList)
            throws DaoException;

    /**
     * Update.
     * 
     * @param employeePromotionId the employee promotion id
     * @param employeePromotion the employee promotion
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int employeePromotionId, EmployeePromotion employeePromotion)
            throws DaoException;

    /**
     * Gets the.
     * 
     * @param employeeId the employee id
     * @param employeePromotion the employee promotion
     * @return the employee promotions
     * @throws DaoException the dao exception
     */
    EmployeePromotion get(int employeeId, EmployeePromotion employeePromotion)
            throws DaoException;

    /**
     * Delete.
     * 
     * @param promotionId the promotion id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(int promotionId) throws DaoException;

    /**
     * Gets the by employee.
     * 
     * @param employeeNumber the employee number
     * @return the by employee
     * @throws DaoException the dao exception
     */
    List<EmployeePromotion> getByEmployee(String employeeNumber) throws DaoException;

    /**
     * Update.
     * 
     * @param employeePromotions the employee promotions
     * @throws DaoException the dao exception
     */
    void update(List<EmployeePromotion> employeePromotions) throws DaoException;

}
