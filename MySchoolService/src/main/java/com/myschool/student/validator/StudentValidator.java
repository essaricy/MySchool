package com.myschool.student.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.dto.FamilyMemberDto;
import com.myschool.common.dto.PersonalDetailsDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.AbstractValidator;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.student.domain.StudentDocumentManager;
import com.myschool.student.domain.StudentFamilyManager;
import com.myschool.student.dto.AdmissionStatus;
import com.myschool.student.dto.StudentDocument;
import com.myschool.student.dto.StudentDto;
import com.quasar.core.exception.DataException;

/**
 * The Class StudentValidator.
 */
@Component
public class StudentValidator extends AbstractValidator<StudentDto> {

    /** The student family manager. */
    @Autowired
    private StudentFamilyManager studentFamilyManager;

    /** The student document manager. */
    @Autowired
    private StudentDocumentManager studentDocumentManager;

    /* (non-Javadoc)
     * @see com.myschool.common.validator.AbstractValidator#doValidate(java.lang.Object)
     */
    @Override
    protected void doValidate(StudentDto student) throws ValidationException {
        try {
            String admissionNumber = student.getAdmissionNumber();
            validate(admissionNumber, "Admission Number", DataTypeValidator.ANY_CHARACTER, true);
            RegisteredClassDto registeredClass = student.getRegisteredClassDto();
            validateRegisteredClass(registeredClass);
            String dateOfJoining = student.getDateOfJoining();
            validate(dateOfJoining, "Date Of Joining", DataTypeValidator.DATE, true);
            AdmissionStatus admissionStatus = student.getAdmissionStatus();
            if (admissionStatus == null || admissionStatus.getStatusId() == 0) {
                throw new ValidationException("Admission Status is a required value.");
            }
            String remarks = student.getRemarks();
            validate(remarks, "Remarks", DataTypeValidator.ANY_CHARACTER, false);

            // Validate personal details.
            PersonalDetailsDto personalDetails = student.getPersonalDetails();
            if (personalDetails == null) {
                throw new ValidationException("Missing Personal Information.");
            }
            String firstName = personalDetails.getFirstName();
            validate(firstName, "First Name", DataTypeValidator.NAME, true);
            String middleName = personalDetails.getMiddleName();
            validate(middleName, "Middle Name", DataTypeValidator.NAME, true);
            String lastName = personalDetails.getLastName();
            validate(lastName, "Last Name", DataTypeValidator.NAME, true);
            String gender = personalDetails.getGender();
            validate(gender, "Gender", DataTypeValidator.GENDER, true);
            String dateOfBirth = personalDetails.getDateOfBirth();
            validate(dateOfBirth, "Date Of Birth", DataTypeValidator.DATE, true);
            String religion = personalDetails.getReligion();
            validate(religion, "Religion", DataTypeValidator.ANY_CHARACTER, true);
            String caste = personalDetails.getCaste();
            validate(caste, "Caste", DataTypeValidator.ANY_CHARACTER, false);
            String nationality = personalDetails.getNationality();
            validate(nationality, "Nationality", DataTypeValidator.ANY_CHARACTER, false);
            String motherTongue = personalDetails.getMotherTongue();
            validate(motherTongue, "Mother Tongue", DataTypeValidator.ANY_CHARACTER, false);
            String mobileNumber = personalDetails.getMobileNumber();
            validate(mobileNumber, "Mobile Number", DataTypeValidator.PHONE_NUMBER, false);
            String bloodGroup = personalDetails.getBloodGroup();
            validate(bloodGroup, "Blood Group", DataTypeValidator.ANY_CHARACTER, false);
            String correspondenceAddress = personalDetails.getCorrespondenceAddress();
            validate(correspondenceAddress, "Correspondence Address", DataTypeValidator.ANY_CHARACTER, true);
            String permanentAddress = personalDetails.getPermanentAddress();
            validate(permanentAddress, "Permanent Address", DataTypeValidator.ANY_CHARACTER, true);
            String identificationMarks = personalDetails.getIdentificationMarks();
            validate(identificationMarks, "Identification Marks", DataTypeValidator.ANY_CHARACTER, false);

            // Validate family members information.
            List<FamilyMemberDto> familyMembers = student.getFamilyMembers();
            if (familyMembers == null || familyMembers.isEmpty()) {
                throw new ValidationException("At least one family member information is required.");
            }
            studentFamilyManager.validate(familyMembers);
            studentFamilyManager.validateParent(familyMembers);
            // Validate student documents
            List<StudentDocument> documentsSubmitted = student.getDocumentsSubmitted();
            studentDocumentManager.validate(documentsSubmitted);
        } catch (DataException dataException) {
            throw new ValidationException(dataException.getMessage(), dataException);
        } catch (DaoException daoException) {
            throw new ValidationException(daoException.getMessage(), daoException);
        }
    }

}
