package com.myschool.organization.validator;

import org.springframework.stereotype.Component;

import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.AbstractValidator;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.organization.dto.Organization;

/**
 * The Class OrganizationValidator.
 */
@Component
public class OrganizationValidator extends AbstractValidator<Organization> {

    /* (non-Javadoc)
     * @see com.myschool.common.validator.AbstractValidator#doValidate(java.lang.Object)
     */
    @Override
    protected void doValidate(Organization organization) throws ValidationException {
        String address = organization.getAddress();
        String phoneNumber = organization.getPhoneNumber();
        String faxNumber = organization.getFaxNumber();
        validate(address, "Address", DataTypeValidator.ANY_CHARACTER, true);
        validate(phoneNumber, "Phone Number", DataTypeValidator.PHONE_NUMBER, true);
        validate(faxNumber, "Fax Number", DataTypeValidator.PHONE_NUMBER, false);
    }

}
