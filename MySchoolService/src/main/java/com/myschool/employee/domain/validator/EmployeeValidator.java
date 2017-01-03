package com.myschool.employee.domain.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.branch.domain.BranchManager;
import com.myschool.branch.dto.BranchDto;
import com.myschool.common.dto.Relationship;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.AbstractValidator;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.employee.constant.EmployeeNotificationTo;
import com.myschool.employee.dao.EmployeeDao;
import com.myschool.employee.domain.DesignationManager;
import com.myschool.employee.domain.EmployeeDocumentManager;
import com.myschool.employee.domain.EmployeeEducationManager;
import com.myschool.employee.domain.EmployeeExperienceManager;
import com.myschool.employee.domain.EmployeePromotionManager;
import com.myschool.employee.domain.EmployeeSubjectManager;
import com.myschool.employee.domain.EmploymentStatusManager;
import com.myschool.employee.dto.DesignationDto;
import com.myschool.employee.dto.EmployeeContact;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.dto.EmploymentStatus;
import com.quasar.core.exception.DataException;
import com.quasar.core.util.StringUtil;

/**
 * The Class EmployeeValidator.
 */
@Component
public class EmployeeValidator extends AbstractValidator<EmployeeDto> {

    /** The branch manager. */
    @Autowired
    private BranchManager branchManager;

    /** The designation manager. */
    @Autowired
    private DesignationManager designationManager;

    /** The employment status manager. */
    @Autowired
    private EmploymentStatusManager employmentStatusManager;

    /** The employee document manager. */
    @Autowired
    private EmployeeDocumentManager employeeDocumentManager;

    /** The employee education manager. */
    @Autowired
    private EmployeeEducationManager employeeEducationManager;

    /** The employee experience manager. */
    @Autowired
    private EmployeeExperienceManager employeeExperienceManager;

    /** The employee promotion manager. */
    @Autowired
    private EmployeePromotionManager employeePromotionManager;

    /** The employee subject manager. */
    @Autowired
    private EmployeeSubjectManager employeeSubjectManager;

    /** The employee dao. */
    @Autowired
    private EmployeeDao employeeDao;

    /**
     * Do validate.
     * 
     * @param employee the employee
     * @throws ValidationException the validation exception
     */
    protected void doValidate(EmployeeDto employee) throws ValidationException {
        try {
            String employeeNumber = employee.getEmployeeNumber();
            validate(employeeNumber, "Employee Number", DataTypeValidator.ANY_CHARACTER, true);
            BranchDto employedAtBranch = employee.getEmployedAtBranch();
            if (employedAtBranch == null || employedAtBranch.getBranchId() == 0) {
                throw new ValidationException("Employed At Branch is a required value.");
            }
            BranchDto branch = branchManager.get(employedAtBranch.getBranchId());
            if (branch == null) {
                throw new ValidationException("There is no Branch with branch code " + employedAtBranch.getBranchCode());
            }
            DesignationDto designation = employee.getDesignation();
            if (designation == null || designation.getDesignationId() == 0) {
                throw new ValidationException("Designation is a required value.");
            }
            DesignationDto designationDto = designationManager.get(designation.getDesignationId());
            if (designationDto == null) {
                throw new ValidationException("There is no Designation with designation ID " + designation.getDesignationId());
            }
            EmploymentStatus employmentStatus = employee.getEmploymentStatus();
            if (employmentStatus == null || employmentStatus.getStatusId() == 0) {
                throw new ValidationException("Employment Status is a required value.");
            }
            EmploymentStatus employmentStatus2 = employmentStatusManager.get(employmentStatus.getStatusId());
            if (employmentStatus2 == null) {
                throw new ValidationException("There is no Employment Status with status ID " + employmentStatus.getStatusId());
            }
            String employmentStartDate = employee.getEmploymentStartDate();
            validate(employmentStartDate, "Employment Start Date", DataTypeValidator.DATE, true);
            String employmentEndDate = employee.getEmploymentEndDate();
            validate(employmentEndDate, "Employment End Date", DataTypeValidator.DATE, false);
            EmployeeDto reportingTo = employee.getReportingTo();
            if (reportingTo != null && !StringUtil.isNullOrBlank(reportingTo.getEmployeeNumber())) {
                EmployeeDto employeeDto = employeeDao.get(reportingTo.getEmployeeNumber());
                if (employeeDto == null) {
                    throw new ValidationException("There is no Reporting To Employee with employee number " + reportingTo.getEmployeeNumber());
                }
            }
            String remarks = employee.getRemarks();
            validate(remarks, "Remarks", DataTypeValidator.ANY_CHARACTER, false);
            // Validate personal details.
            String firstName = employee.getFirstName();
            validate(firstName, "First Name", DataTypeValidator.NAME, true);
            String middleName = employee.getMiddleName();
            validate(middleName, "Middle Name", DataTypeValidator.NAME, true);
            String lastName = employee.getLastName();
            validate(lastName, "Last Name", DataTypeValidator.NAME, true);
            String gender = employee.getGender();
            validate(gender, "Gender", DataTypeValidator.GENDER, true);
            String dateOfBirth = employee.getDateOfBirth();
            validate(dateOfBirth, "Date Of Birth", DataTypeValidator.DATE, true);
            String nationality = employee.getNationality();
            validate(nationality, "Nationality", DataTypeValidator.ANY_CHARACTER, false);
            String maritalStatus = employee.getMaritalStatus();
            validate(maritalStatus, "Marital Status", DataTypeValidator.ANY_CHARACTER, true);
            String weddingDay = employee.getWeddingDay();
            validate(weddingDay, "Wedding Day", DataTypeValidator.DATE, false);
            // Validate contact details.
            EmployeeContact employeeContact = employee.getEmployeeContact();
            if (employeeContact == null) {
                throw new ValidationException("Employee Contact Information is required.");
            }
            String presentAddress = employeeContact.getPresentAddress();
            validate(presentAddress, "Present Address", DataTypeValidator.ANY_CHARACTER, true);
            String permanentAddress = employeeContact.getPermanentAddress();
            validate(permanentAddress, "Permanent Address", DataTypeValidator.ANY_CHARACTER, false);
            String personalMobileNumber = employeeContact.getPersonalMobileNumber();
            validate(personalMobileNumber, "Personal Mobile Number", DataTypeValidator.PHONE_NUMBER, true);
            String personalEmailId = employeeContact.getPersonalEmailId();
            validate(personalEmailId, "Personal Email ID", DataTypeValidator.EMAIL_ID, false);
            String emergencyContactNumber = employeeContact.getEmergencyContactNumber();
            validate(emergencyContactNumber, "Emergency Contact Number", DataTypeValidator.PHONE_NUMBER, true);
            Relationship emergencyContactRelationship = employeeContact.getEmergencyContactRelationship();
            if (emergencyContactRelationship == null) {
                throw new ValidationException("Employee Contact Relationship is required.");
            }
            String officeDeskPhoneNumber = employeeContact.getOfficeDeskPhoneNumber();
            validate(officeDeskPhoneNumber, "Office Desk Phone Number", DataTypeValidator.PHONE_NUMBER, false);
            String officeDeskExtension = employeeContact.getOfficeDeskExtension();
            validate(officeDeskExtension, "Office Desk Extension", DataTypeValidator.PHONE_NUMBER, false);
            String officeMobileNumber = employeeContact.getOfficeMobileNumber();
            validate(officeMobileNumber, "Office MobileNumber", DataTypeValidator.PHONE_NUMBER, false);
            String officeEmailId = employeeContact.getOfficeEmailId();
            validate(officeEmailId, "Office Email ID", DataTypeValidator.EMAIL_ID, false);
            EmployeeNotificationTo emailNotificationTo = employeeContact.getEmailNotificationTo();
            if (emailNotificationTo != null
                    && emailNotificationTo == EmployeeNotificationTo.WORK
                    && StringUtil.isNullOrBlank(officeMobileNumber)) {
                throw new ValidationException("Office mobile number is required when chosen to send Email notifications to Work Phone number.");
            }
            EmployeeNotificationTo smsNotificationTo = employeeContact.getSmsNotificationTo();
            if (smsNotificationTo != null
                    && smsNotificationTo == EmployeeNotificationTo.WORK
                    && StringUtil.isNullOrBlank(officeMobileNumber)) {
                throw new ValidationException("Office mobile number is required when selected to send SMS notifications to Work Phone number.");
            }
            employeeDocumentManager.validate(employee.getEmployeeDocuments());
            employeeEducationManager.validate(employee.getEmployeeEducations());
            employeeExperienceManager.validate(employee.getEmployeeExperiences());
            employeePromotionManager.validate(employee.getEmployeePromotions());
            employeeSubjectManager.validate(employee.getEmployeeSubjects());
        } catch (DataException dataException) {
            throw new ValidationException(dataException.getMessage(), dataException);
        } catch (DaoException daoException) {
            throw new ValidationException(daoException.getMessage(), daoException);
        }
    }

}
