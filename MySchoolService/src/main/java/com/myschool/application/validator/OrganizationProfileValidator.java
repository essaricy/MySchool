package com.myschool.application.validator;

import org.springframework.stereotype.Component;

import com.myschool.application.dto.OrganizationProfileDto;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.AbstractValidator;
import com.myschool.common.validator.DataTypeValidator;

/**
 * The Class OrganizationProfileValidator.
 */
@Component
public class OrganizationProfileValidator extends AbstractValidator<OrganizationProfileDto> {

    /* (non-Javadoc)
     * @see com.myschool.common.validator.AbstractValidator#doValidate(java.lang.Object)
     */
    @Override
    protected void doValidate(OrganizationProfileDto organizationProfile) throws ValidationException {
        String address = organizationProfile.getAddress();
        String phoneNumber = organizationProfile.getPhoneNumber();
        String faxNumber = organizationProfile.getFaxNumber();
        validate(address, "Address", DataTypeValidator.ANY_CHARACTER, true);
        validate(phoneNumber, "Phone Number", DataTypeValidator.PHONE_NUMBER, true);
        validate(faxNumber, "Fax Number", DataTypeValidator.PHONE_NUMBER, false);
    }

}
