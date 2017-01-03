package com.myschool.application.domain;

import org.springframework.stereotype.Component;

import com.myschool.common.constants.DocumentApplicability;
import com.myschool.common.dto.DocumentDto;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.AbstractValidator;
import com.myschool.common.validator.DataTypeValidator;
import com.quasar.core.util.CollectionUtil;

/**
 * The Class DocumentValidator.
 */
@Component
public class DocumentValidator extends AbstractValidator<DocumentDto> {

    /* (non-Javadoc)
     * @see com.myschool.common.validator.AbstractValidator#doValidate(java.lang.Object)
     */
    @Override
    protected void doValidate(DocumentDto document) throws ValidationException {
        validate(document.getName(), "Document Name", DataTypeValidator.ANY_CHARACTER, true);
        validate(document.getDescription(), "Description", DataTypeValidator.ANY_CHARACTER, true);

        DocumentApplicability applicabilityForEmployee = document.getApplicabilityForEmployee();
        if (applicabilityForEmployee == null) {
            throw new ValidationException("Document applicability for employee must be one of "
                    + CollectionUtil.toString(DocumentApplicability.values()));
        }

        DocumentApplicability applicabilityForStudent = document.getApplicabilityForStudent();
        if (applicabilityForStudent == null) {
            throw new ValidationException("Document applicability for employee must be one of "
                    + CollectionUtil.toString(DocumentApplicability.values()));
        }
    }

}
