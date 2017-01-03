package com.myschool.application.domain;

import org.springframework.stereotype.Component;

import com.myschool.application.dto.IssueDto;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.AbstractValidator;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.user.constants.UserType;
import com.quasar.core.util.ConversionUtil;

/**
 * The Class IssueValidator.
 */
@Component
public class IssueValidator extends AbstractValidator<IssueDto> {

    /* (non-Javadoc)
     * @see com.myschool.common.validator.AbstractValidator#doValidate(java.lang.Object)
     */
    @Override
    protected void doValidate(IssueDto issue) throws ValidationException {
        if (issue == null) {
            throw new ValidationException("Issue details are not present.");
        }
        String contactEmailId = issue.getContactEmailId();
        String issueSubject = issue.getSubject();
        String description = issue.getDescription();
        issue.setContactEmailId(validate(contactEmailId, "Email ID", DataTypeValidator.EMAIL_ID, true));
        issue.setSubject(validate(issueSubject, "Issue Subject", DataTypeValidator.ANY_CHARACTER, true));
        issue.setDescription(validate(description, "Description", DataTypeValidator.ANY_CHARACTER, true));

        String closedDate = issue.getClosedDate();
        if (closedDate != null) {
            issue.setClosedDate(ConversionUtil.toStorageDateFromApplicationDate(
                    validate(contactEmailId, "Closed Date", DataTypeValidator.DATE, false)));
        }
        UserType userType = issue.getUserType();
        if (userType == null) {
            throw new ValidationException("Unknown users cannot create an issue.");
        }
        if (userType == UserType.ADMIN) {
            throw new ValidationException(UserType.ADMIN + " cannot create an issue.");
        }
    }

}
