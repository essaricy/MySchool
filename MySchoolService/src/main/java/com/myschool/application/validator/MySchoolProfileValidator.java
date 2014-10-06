package com.myschool.application.validator;

import org.springframework.stereotype.Component;

import com.myschool.application.dto.MySchoolProfileDto;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.AbstractValidator;

/**
 * The Class MySchoolProfileValidator.
 */
@Component
public class MySchoolProfileValidator extends AbstractValidator<MySchoolProfileDto> {

    /* (non-Javadoc)
     * @see com.myschool.common.validator.AbstractValidator#doValidate(java.lang.Object)
     */
    @Override
    protected void doValidate(MySchoolProfileDto mySchoolProfile) throws ValidationException {
        if (!mySchoolProfile.isEmailActive()) {
            mySchoolProfile.setEmailEmployees(false);
            mySchoolProfile.setEmailStudents(false);
        }
        if (!mySchoolProfile.isSmsActive()) {
            mySchoolProfile.setSmsEmployees(false);
            mySchoolProfile.setSmsStudents(false);
        }
    }

}
