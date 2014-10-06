package com.myschool.employee.domain;

import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.AbstractValidator;
import com.myschool.employee.dto.EmployeeSubjectDto;

/**
 * The Class EmployeeSubjectValidator.
 */
@Component
public class EmployeeSubjectValidator extends AbstractValidator<EmployeeSubjectDto> {

    /* (non-Javadoc)
     * @see com.myschool.common.validator.AbstractValidator#doValidate(java.lang.Object)
     */
    @Override
    protected void doValidate(EmployeeSubjectDto employeeSubject)
            throws ValidationException {
        try {
            validateRegisteredSubject(employeeSubject.getRegisteredSubject());
        } catch (DaoException daoException) {
            throw new ValidationException(daoException.getMessage(), daoException);
        }
    }

}
