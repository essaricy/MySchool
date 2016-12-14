package com.myschool.employee.validator;

import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.AbstractValidator;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.employee.dto.DesignationDto;
import com.myschool.employee.dto.EmployeePromotion;
import com.myschool.infra.cache.exception.CacheException;

/**
 * The Class EmployeePromotionValidator.
 */
@Component
public class EmployeePromotionValidator extends AbstractValidator<EmployeePromotion> {

    /* (non-Javadoc)
     * @see com.myschool.common.validator.AbstractValidator#doValidate(java.lang.Object)
     */
    @Override
    protected void doValidate(EmployeePromotion employeePromotion)
            throws ValidationException {
        try {
            DesignationDto priorDesignation = employeePromotion.getPriorDesignation();
            validateDesignation(priorDesignation);
            DesignationDto currentDesignation = employeePromotion.getCurrentDesignation();
            validateDesignation(currentDesignation);
            if (priorDesignation.getDesignationId() == currentDesignation.getDesignationId()) {
                throw new ValidationException("Prior and current designations are same.");
            }
            validate(employeePromotion.getEffectiveFrom(), "Effective From", DataTypeValidator.DATE, true);
        } catch (DaoException daoException) {
            throw new ValidationException(daoException.getMessage(), daoException);
        } catch (CacheException cacheException) {
            throw new ValidationException(cacheException.getMessage(), cacheException);
        }
    }

}
