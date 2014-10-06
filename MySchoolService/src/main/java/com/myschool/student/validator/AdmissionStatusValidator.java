package com.myschool.student.validator;

import org.springframework.stereotype.Component;

import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.AbstractValidator;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.student.dto.AdmissionStatus;

/**
 * The Class AdmissionStatusValidator.
 */
@Component
public class AdmissionStatusValidator extends AbstractValidator<AdmissionStatus> {

    /* (non-Javadoc)
     * @see com.myschool.common.validator.AbstractValidator#doValidate(java.lang.Object)
     */
    @Override
    protected void doValidate(AdmissionStatus admissionStatus) throws ValidationException {
        String description = admissionStatus.getDescription();
        validate(description, "Admission Status Description", DataTypeValidator.ANY_CHARACTER, true);
    }

}
