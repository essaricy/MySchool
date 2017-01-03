package com.myschool.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.ServiceException;
import com.myschool.employee.domain.EmployeePromotionManager;
import com.myschool.employee.dto.EmployeePromotion;
import com.quasar.core.exception.DataException;

/**
 * The Class EmployeePromotionServiceImpl.
 */
@Service
public class EmployeePromotionServiceImpl implements EmployeePromotionService {

    /** The employee promotion manager. */
    @Autowired
    private EmployeePromotionManager employeePromotionManager;

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(EmployeePromotion employeePromotion) throws ServiceException {
        throw new ServiceException("Use create(employeeNumber, EmployeePromotion)");
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeePromotionService#create(java.lang.String, com.myschool.employee.dto.EmployeePromotion)
     */
    @Override
    public boolean create(String employeeNumber,
            EmployeePromotion employeePromotion) throws ServiceException {
        try {
            return employeePromotionManager.create(employeeNumber, employeePromotion);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#getAll()
     */
    @Override
    public List<EmployeePromotion> getAll() throws ServiceException {
        throw new ServiceException("Use get(promotionId)");
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#get(int)
     */
    @Override
    public EmployeePromotion get(int promotionId) throws ServiceException {
        try {
            return employeePromotionManager.get(promotionId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int promotionId, EmployeePromotion employeePromotion)
            throws ServiceException {
        try {
            return employeePromotionManager.update(promotionId, employeePromotion);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#delete(int)
     */
    @Override
    public boolean delete(int promotionId) throws ServiceException {
        try {
            return employeePromotionManager.delete(promotionId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeePromotionService#validate(com.myschool.employee.dto.EmployeePromotion)
     */
    @Override
    public void validate(EmployeePromotion employeePromotion) throws ServiceException {
        try {
            employeePromotionManager.validate(employeePromotion);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeePromotionService#validate(java.util.List)
     */
    @Override
    public void validate(List<EmployeePromotion> employeePromotions) throws ServiceException {
        try {
            employeePromotionManager.validate(employeePromotions);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeePromotionService#getByEmployee(java.lang.String)
     */
    @Override
    public List<EmployeePromotion> getByEmployee(String employeeNumber) throws ServiceException {
        try {
            return employeePromotionManager.getByEmployee(employeeNumber);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

}
