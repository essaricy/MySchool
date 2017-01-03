package com.myschool.student.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.dto.FamilyMemberDto;
import com.myschool.common.dto.Relationship;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.AbstractValidator;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.student.domain.StudentFamilyManager;
import com.quasar.core.exception.DataException;

/**
 * The Class StudentFamilyValidator.
 */
@Component
public class StudentFamilyValidator extends AbstractValidator<FamilyMemberDto> {

    /** The student family manager. */
    @Autowired
    private StudentFamilyManager studentFamilyManager;

    /* (non-Javadoc)
     * @see com.myschool.common.validator.AbstractValidator#doValidate(java.lang.Object)
     */
    @Override
    protected void doValidate(FamilyMemberDto familyMember) throws ValidationException {
        familyMember.setEmailId(validate(familyMember.getEmailId(),
                "Email ID", DataTypeValidator.EMAIL_ID, false));
        familyMember.setMobileNumber(validate(
                familyMember.getMobileNumber(), "Mobile Number",
                DataTypeValidator.PHONE_NUMBER, false));
        familyMember.setName(validate(familyMember.getName(), "Name",
                DataTypeValidator.NAME, true));
        familyMember.setOccupation(validate(familyMember.getOccupation(),
                "Occupation", DataTypeValidator.ANY_CHARACTER, false));
        Relationship relationship = familyMember.getRelationship();
        if (relationship == null) {
            throw new ValidationException("Relationship code is a required value.");
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.common.validator.AbstractValidator#validate(int)
     */
    @Override
    public void validate(int familyMemberId) throws ValidationException {
        try {
            super.validate(familyMemberId);
            FamilyMemberDto familyMember = studentFamilyManager.get(familyMemberId);
            if (familyMember == null) {
                throw new ValidationException("There is no such family member for student to update.");
            }
        } catch (DataException dataException) {
            throw new ValidationException(dataException.getMessage(), dataException);
        }
    }

}
