package com.myschool.employee.validator;

import org.springframework.stereotype.Component;

import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.AbstractValidator;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.employee.dto.EmploymentStatus;

/**
 * The Class EmploymentStatusValidator.
 */
@Component
public class EmploymentStatusValidator extends AbstractValidator<EmploymentStatus> {

    /* (non-Javadoc)
     * @see com.myschool.common.validator.AbstractValidator#doValidate(java.lang.Object)
     */
    @Override
    protected void doValidate(EmploymentStatus employmentStatus) throws ValidationException {
        String description = employmentStatus.getDescription();
        validate(description, "Employment Status Description", DataTypeValidator.ANY_CHARACTER, true);
    }

}
