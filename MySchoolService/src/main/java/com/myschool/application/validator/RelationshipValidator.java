package com.myschool.application.validator;

import org.springframework.stereotype.Component;

import com.myschool.common.dto.Relationship;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.AbstractValidator;
import com.myschool.common.validator.DataTypeValidator;

/**
 * The Class RelationshipValidator.
 */
@Component
public class RelationshipValidator extends AbstractValidator<Relationship> {

    /* (non-Javadoc)
     * @see com.myschool.common.validator.AbstractValidator#doValidate(java.lang.Object)
     */
    @Override
    protected void doValidate(Relationship relationship) throws ValidationException {
        String code = relationship.getCode();
        validate(relationship.getCode(), "Relationship Code", DataTypeValidator.ALPHABETS, true);
        if (code.length() != 1) {
            throw new ValidationException("Relationship code must be a single alphabet.");
        }
        validate(relationship.getName(), "Relationship Name", DataTypeValidator.ANY_CHARACTER, true);
    }

}
