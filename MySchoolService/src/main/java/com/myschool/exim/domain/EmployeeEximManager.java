package com.myschool.exim.domain;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.DivisionDto;
import com.myschool.clazz.dto.ClassDto;
import com.myschool.clazz.dto.MediumDto;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.clazz.dto.RegisteredSubjectDto;
import com.myschool.clazz.dto.SectionDto;
import com.myschool.clazz.dto.SubjectDto;
import com.myschool.common.constants.CacheKeyConstants;
import com.myschool.common.dto.DocumentDto;
import com.myschool.common.dto.Relationship;
import com.myschool.common.dto.Rule;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.InvalidDataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.util.DateUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.employee.dao.EmployeeContactDao;
import com.myschool.employee.dao.EmployeeDao;
import com.myschool.employee.dao.EmployeeDocumentDao;
import com.myschool.employee.dao.EmployeeEducationDao;
import com.myschool.employee.dao.EmployeeExperienceDao;
import com.myschool.employee.dao.EmployeePromotionDao;
import com.myschool.employee.dao.EmployeeSubjectDao;
import com.myschool.employee.dao.EmploymentStatusDao;
import com.myschool.employee.domain.EmployeeManager;
import com.myschool.employee.dto.DesignationDto;
import com.myschool.employee.dto.EmployeeContact;
import com.myschool.employee.dto.EmployeeDocument;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.dto.EmployeeEducation;
import com.myschool.employee.dto.EmployeeExperience;
import com.myschool.employee.dto.EmployeePromotion;
import com.myschool.employee.dto.EmployeeSubjectDto;
import com.myschool.employee.dto.EmploymentStatus;
import com.myschool.employee.fields.EmployeeContactFieldNames;
import com.myschool.employee.fields.EmployeeDocumentFieldNames;
import com.myschool.employee.fields.EmployeeEducationFieldNames;
import com.myschool.employee.fields.EmployeeExperienceFieldNames;
import com.myschool.employee.fields.EmployeeFieldNames;
import com.myschool.employee.fields.EmployeePromotionFieldNames;
import com.myschool.employee.fields.EmployeeSubjectsFieldNames;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.ImportRecordStatusDto;
import com.myschool.infra.cache.exception.CacheException;
import com.myschool.school.dto.SchoolDto;
import com.myschool.user.constants.UserType;

/**
 * The Class EmployeeEximManager.
 */
@Component
public class EmployeeEximManager extends AbstractEximManager {

    /** The Constant EMPLOYEE_SUBJECT_ADD_SUCCESS. */
    private static final String EMPLOYEE_SUBJECT_ADD_SUCCESS = "Subject ({0}) in Class ({1}) assigned to Employee ({2})";

    /** The Constant EMPLOYEE_SUBJECT_ADD_FAIL. */
    private static final String EMPLOYEE_SUBJECT_ADD_FAIL = "System encountered problems while adding Subject ({0}) in Class ({1}) to Employee ({2})";

    /** The Constant EMPLOYEE_SUBJECT_UPDATE_FAIL. */
    private static final String EMPLOYEE_SUBJECT_UPDATE_FAIL = "Subject ({0}) in Class ({1}) already assigned to Employee ({2})";

    /** The employee manager. */
    @Autowired
    private EmployeeManager employeeManager;

    /** The employee dao. */
    @Autowired
    private EmployeeDao employeeDao;

    /** The employee contact dao. */
    @Autowired
    private EmployeeContactDao employeeContactDao;

    /** The employee education dao. */
    @Autowired
    private EmployeeEducationDao employeeEducationDao;

    /** The employee experience dao. */
    @Autowired
    private EmployeeExperienceDao employeeExperienceDao;

    /** The employee document dao. */
    @Autowired
    private EmployeeDocumentDao employeeDocumentDao;

    /** The employee promotion dao. */
    @Autowired
    private EmployeePromotionDao employeePromotionDao;

    /** The employee subject dao. */
    @Autowired
    private EmployeeSubjectDao employeeSubjectDao;

    /** The employment status dao. */
    @Autowired
    private EmploymentStatusDao employmentStatusDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.myschool.exim.domain.AbstractEximManager#updateContent(com.myschool
     * .exim.constants.EximPolicy, java.lang.Object,
     * com.myschool.common.dto.Rule, java.lang.String)
     */
    @Override
    protected Object updateContent(EximPolicy eximPolicy, Object content, Rule rule,
            String fieldValue) throws DataException, ValidationException {
        if (eximPolicy == EximPolicy.EMPLOYEES) {
            if (content == null) {
                content = new EmployeeDto();
            }
            content = updateEmployee((EmployeeDto) content, rule, fieldValue);
        } else if (eximPolicy == EximPolicy.EMPLOYEE_CONTACT) {
            if (content == null) {
                content = new EmployeeContact();
            }
            content = updateEmployeeContact((EmployeeContact) content, rule, fieldValue);
        } else if (eximPolicy == EximPolicy.EMPLOYEE_EDUCATION) {
            if (content == null) {
                content = new EmployeeEducation();
            }
            content = updateEmployeeEducation((EmployeeEducation) content, rule, fieldValue);
        } else if (eximPolicy == EximPolicy.EMPLOYEE_EXPERIENCE) {
            if (content == null) {
                content = new EmployeeExperience();
            }
            content = updateEmployeeExperience((EmployeeExperience) content, rule, fieldValue);
        } else if (eximPolicy == EximPolicy.EMPLOYEE_DOCUMENT) {
            if (content == null) {
                content = new EmployeeDocument();
            }
            content = updateEmployeeDocument((EmployeeDocument) content, rule, fieldValue);
        } else if (eximPolicy == EximPolicy.EMPLOYEE_PROMOTION) {
            if (content == null) {
                content = new EmployeePromotion();
            }
            content = updateEmployeePromotion((EmployeePromotion) content, rule, fieldValue);
        } else if (eximPolicy == EximPolicy.EMPLOYEE_SUBJECT) {
            if (content == null) {
                content = new EmployeeSubjectDto();
            }
            content = updateEmployeeSubject((EmployeeSubjectDto) content, rule, fieldValue);
        }
        return content;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.myschool.exim.domain.AbstractEximManager#validateRecord(com.myschool
     * .exim.constants.EximPolicy, java.lang.Object)
     */
    @Override
    protected ImportRecordStatusDto validateRecord(EximPolicy eximPolicy,
            Object content) {
        ImportRecordStatusDto importRecordStatus = new ImportRecordStatusDto();
        try {
            if (eximPolicy == EximPolicy.EMPLOYEES) {
                validateEmployee((EmployeeDto) content);
            } else if (eximPolicy == EximPolicy.EMPLOYEE_CONTACT) {
                validateEmployeeContact((EmployeeContact)content);
            } else if (eximPolicy == EximPolicy.EMPLOYEE_EDUCATION) {
                validateEmployeeEducation((EmployeeEducation)content);
            } else if (eximPolicy == EximPolicy.EMPLOYEE_EXPERIENCE) {
                validateEmployeeExperience((EmployeeExperience) content);
            } else if (eximPolicy == EximPolicy.EMPLOYEE_DOCUMENT) {
                validateEmployeeDocument((EmployeeDocument) content);
            } else if (eximPolicy == EximPolicy.EMPLOYEE_PROMOTION) {
                validateEmployeePromotion((EmployeePromotion) content);
            } else if (eximPolicy == EximPolicy.EMPLOYEE_SUBJECT) {
                validateEmployeeSubject((EmployeeSubjectDto) content);
            }
        } catch (Exception exception) {
            importRecordStatus.setActionCode(ImportRecordStatusDto.ACTION_CODE_SKIP);
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_INVALID_DATA);
            importRecordStatus.setStatusDescription(exception.getMessage());
        }
        return importRecordStatus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.myschool.exim.domain.AbstractEximManager#processRecord(com.myschool
     * .exim.constants.EximPolicy, com.myschool.exim.dto.ImportRecordStatusDto)
     */
    @Override
    protected void processRecord(EximPolicy eximPolicy, ImportRecordStatusDto importRecordStatus) {
        try {
            if (eximPolicy == EximPolicy.EMPLOYEES) {
                handleEmployeeData(importRecordStatus);
            } else if (eximPolicy == EximPolicy.EMPLOYEE_CONTACT) {
                handleEmployeeContactData(importRecordStatus);
            } else if (eximPolicy == EximPolicy.EMPLOYEE_EDUCATION) {
                handleEmployeeEducation(importRecordStatus);
            } else if (eximPolicy == EximPolicy.EMPLOYEE_EXPERIENCE) {
                handleEmployeeExperience(importRecordStatus);
            } else if (eximPolicy == EximPolicy.EMPLOYEE_DOCUMENT) {
                handleEmployeeDocument(importRecordStatus);
            } else if (eximPolicy == EximPolicy.EMPLOYEE_PROMOTION) {
                handleEmployeePromotion(importRecordStatus);
            } else if (eximPolicy == EximPolicy.EMPLOYEE_SUBJECT) {
                handleEmployeeSubject(importRecordStatus);
            }
        } catch (Exception exception) {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
            importRecordStatus.setStatusDescription(exception.getMessage());
        }
    }

    /**
     * Update employee.
     * 
     * @param employee the employee
     * @param rule the rule
     * @param fieldValue the field value
     * @return the employee dto
     * @throws DataException the data exception
     */
    public EmployeeDto updateEmployee(EmployeeDto employee, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();

        // Fill up the employee DTO
        String validatedFieldValue = DataTypeValidator.validate(fieldValue, dataType, fieldName);
        if (fieldName.equals(EmployeeFieldNames.EMPLOYEE_NUMBER)) {
            employee.setEmployeeNumber(validatedFieldValue);
        } else if (fieldName.equals(EmployeeFieldNames.FIRST_NAME)) {
            employee.setFirstName(validatedFieldValue);
        } else if (fieldName.equals(EmployeeFieldNames.MIDDLE_NAME)) {
            employee.setMiddleName(validatedFieldValue);
        } else if (fieldName.equals(EmployeeFieldNames.LAST_NAME)) {
            employee.setLastName(validatedFieldValue);
        } else if (fieldName.equals(EmployeeFieldNames.GENDER)) {
            employee.setGender(validatedFieldValue);
        } else if (fieldName.equals(EmployeeFieldNames.DATE_OF_BIRTH)) {
            employee.setDateOfBirth(validatedFieldValue);
        } else if (fieldName.equals(EmployeeFieldNames.BLOOD_GROUP)) {
            employee.setBloodGroup(validatedFieldValue);            
        } else if (fieldName.equals(EmployeeFieldNames.NATIONALITY)) {
            employee.setNationality(validatedFieldValue); 
        } else if (fieldName.equals(EmployeeFieldNames.MARITAL_STATUS)) {
            employee.setMaritalStatus(validatedFieldValue); 
        } else if (fieldName.equals(EmployeeFieldNames.WEDDING_DAY)) {
            employee.setWeddingDay(validatedFieldValue); 
        } else if (fieldName.equals(EmployeeFieldNames.EMPLOYED_AT)) {
            BranchDto branch = new BranchDto();
            branch.setBranchCode(validatedFieldValue);
            employee.setEmployedAtBranch(branch); 
        } else if (fieldName.equals(EmployeeFieldNames.DESIGNATION_CODE)) {
            DesignationDto designation = employee.getDesignation();
            if (designation == null) {
                designation = new DesignationDto();
            }
            designation.setDesignationId(Integer.parseInt(validatedFieldValue));
            employee.setDesignation(designation);
        } else if (fieldName.equals(EmployeeFieldNames.EMPLOYEE_STATUS)) {
            EmploymentStatus employmentStatus = new EmploymentStatus();
            employmentStatus.setDescription(validatedFieldValue);
            employee.setEmploymentStatus(employmentStatus);
        } else if (fieldName.equals(EmployeeFieldNames.EMPLOYMENT_START_DATE)) {
            employee.setEmploymentStartDate(validatedFieldValue);
        } else if (fieldName.equals(EmployeeFieldNames.EMPLOYMENT_END_DATE)) {
            employee.setEmploymentEndDate(validatedFieldValue);
        } else if (fieldName.equals(EmployeeFieldNames.REPORTING_TO)) {
            EmployeeDto reportingTo = new EmployeeDto();
            reportingTo.setEmployeeNumber(validatedFieldValue);
            employee.setReportingTo(reportingTo);
        } else if (fieldName.equals(EmployeeFieldNames.REMARKS)) {
            employee.setRemarks(validatedFieldValue);
        }
        return employee;
    }

    /**
     * Update employee contact.
     * 
     * @param employeeContact the employee contact
     * @param rule the rule
     * @param fieldValue the field value
     * @return the employee contact
     * @throws DataException the data exception
     */
    private EmployeeContact updateEmployeeContact(EmployeeContact employeeContact, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();

        String validatedFieldValue = DataTypeValidator.validate(fieldValue, dataType, fieldName);
        if (fieldName.equals(EmployeeContactFieldNames.EMERGENCY_CONTACT_NUMBER)) {
            employeeContact.setEmergencyContactNumber(validatedFieldValue);
        } else if (fieldName.equals(EmployeeContactFieldNames.EMERGENCY_CONTACT_RELATIONSHIP)) {
            Relationship relationship = new Relationship();
            relationship.setCode(validatedFieldValue);
            employeeContact.setEmergencyContactRelationship(relationship);
        } else if (fieldName.equals(EmployeeContactFieldNames.EMPLOYEE_NUMBER)) {
            EmployeeDto employee = new EmployeeDto();
            employee.setEmployeeNumber(validatedFieldValue);
            employeeContact.setEmployee(employee);
        } else if (fieldName.equals(EmployeeContactFieldNames.OFFICE_DESK_EXTENSION)) {
            employeeContact.setOfficeDeskExtension(validatedFieldValue);
        } else if (fieldName.equals(EmployeeContactFieldNames.OFFICE_DESK_PHONE_NUMBER)) {
            employeeContact.setOfficeDeskPhoneNumber(validatedFieldValue);
        } else if (fieldName.equals(EmployeeContactFieldNames.OFFICE_EMAIL_ID)) {
            employeeContact.setOfficeEmailId(validatedFieldValue);
        } else if (fieldName.equals(EmployeeContactFieldNames.OFFICE_MOBILE_NUMBER)) {
            employeeContact.setOfficeMobileNumber(validatedFieldValue);
        } else if (fieldName.equals(EmployeeContactFieldNames.PERMANENT_ADDRESS)) {
            employeeContact.setPermanentAddress(validatedFieldValue);
        } else if (fieldName.equals(EmployeeContactFieldNames.PERSONAL_EMAIL_ID)) {
            employeeContact.setPersonalEmailId(validatedFieldValue);
        } else if (fieldName.equals(EmployeeContactFieldNames.PERSONAL_MOBILE_NUMBER)) {
            employeeContact.setPersonalMobileNumber(validatedFieldValue);
        } else if (fieldName.equals(EmployeeContactFieldNames.PRESENT_ADDRESS)) {
            employeeContact.setPresentAddress(validatedFieldValue);
        }
        return employeeContact;
    }

    /**
     * Update employee education.
     * 
     * @param employeeEducation the employee education
     * @param rule the rule
     * @param fieldValue the field value
     * @return the employee education
     * @throws DataException the data exception
     */
    private EmployeeEducation updateEmployeeEducation(EmployeeEducation employeeEducation,
            Rule rule, String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();

        String validatedFieldValue = DataTypeValidator.validate(fieldValue, dataType, fieldName);
        if (fieldName.equals(EmployeeEducationFieldNames.COLLEGE)) {
            employeeEducation.setCollege(validatedFieldValue);
        } else if (fieldName.equals(EmployeeEducationFieldNames.DEGREE)) {
            employeeEducation.setDegree(validatedFieldValue);
        } else if (fieldName.equals(EmployeeEducationFieldNames.EMPLOYEE_NUMBER)) {
            EmployeeDto employee = new EmployeeDto();
            employee.setEmployeeNumber(validatedFieldValue);
            employeeEducation.setEmployee(employee);
        } else if (fieldName.equals(EmployeeEducationFieldNames.PERCENTAGE)) {
            employeeEducation.setPercentage(Integer.parseInt(validatedFieldValue));
        } else if (fieldName.equals(EmployeeEducationFieldNames.SPECIALIZATION)) {
            employeeEducation.setSpecialization(validatedFieldValue);
        } else if (fieldName.equals(EmployeeEducationFieldNames.UNIVERSITY)) {
            employeeEducation.setUniversity(validatedFieldValue);
        } else if (fieldName.equals(EmployeeEducationFieldNames.YEAR_OF_GRADUATION)) {
            employeeEducation.setYearOfGraduation(Integer.parseInt(validatedFieldValue));
        }
        return employeeEducation;
    }

    /**
     * Update employee experience.
     * 
     * @param employeeExperience the employee experience
     * @param rule the rule
     * @param fieldValue the field value
     * @return the employee experience
     * @throws DataException the data exception
     */
    private EmployeeExperience updateEmployeeExperience(EmployeeExperience employeeExperience,
            Rule rule, String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();

        String validatedFieldValue = DataTypeValidator.validate(fieldValue, dataType, fieldName);
        if (fieldName.equals(EmployeeExperienceFieldNames.EMPLOYEE_NUMBER)) {
            EmployeeDto employee = new EmployeeDto();
            employee.setEmployeeNumber(validatedFieldValue);
            employeeExperience.setEmployee(employee);
        } else if (fieldName.equals(EmployeeExperienceFieldNames.EMPLOYER)) {
            employeeExperience.setEmployer(validatedFieldValue);
        } else if (fieldName.equals(EmployeeExperienceFieldNames.FROM_DATE)) {
            employeeExperience.setFromDate(validatedFieldValue);
        } else if (fieldName.equals(EmployeeExperienceFieldNames.JOB_TITLE)) {
            employeeExperience.setJobTitle(validatedFieldValue);
        } else if (fieldName.equals(EmployeeExperienceFieldNames.TO_DATE)) {
            employeeExperience.setToDate(validatedFieldValue);
        }
        return employeeExperience;
    }

    /**
     * Update employee document.
     * 
     * @param employeeDocument the employee document
     * @param rule the rule
     * @param fieldValue the field value
     * @return the employee document
     * @throws DataException the data exception
     */
    private EmployeeDocument updateEmployeeDocument(
            EmployeeDocument employeeDocument, Rule rule, String fieldValue)
            throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();

        String validatedFieldValue = DataTypeValidator.validate(fieldValue, dataType, fieldName);
        if (fieldName.equals(EmployeeDocumentFieldNames.DOCUMENT_EXPIRY_DATE)) {
            employeeDocument.setDocumentExpiryDate(validatedFieldValue);
        } else if (fieldName.equals(EmployeeDocumentFieldNames.DOCUMENT_NAME)) {
            DocumentDto document = new DocumentDto();
            document.setName(validatedFieldValue);
            employeeDocument.setDocument(document);
        } else if (fieldName.equals(EmployeeDocumentFieldNames.DOCUMENT_NUMBER)) {
            employeeDocument.setDocumentNumber(validatedFieldValue);
        } else if (fieldName.equals(EmployeeDocumentFieldNames.EMPLOYEE_NUMBER)) {
            EmployeeDto employee = new EmployeeDto();
            employee.setEmployeeNumber(validatedFieldValue);
            employeeDocument.setEmployee(employee);
        } else if (fieldName.equals(EmployeeDocumentFieldNames.ISSUED_BY)) {
            employeeDocument.setDocumentIssuedBy(validatedFieldValue);
        }
        return employeeDocument;
    }

    /**
     * Update employee promotion.
     * 
     * @param employeePromotion the employee promotion
     * @param rule the rule
     * @param fieldValue the field value
     * @return the employee promotion
     * @throws DataException the data exception
     */
    private EmployeePromotion updateEmployeePromotion(
            EmployeePromotion employeePromotion, Rule rule, String fieldValue)
            throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();

        String validatedFieldValue = DataTypeValidator.validate(fieldValue, dataType, fieldName);
        if (fieldName.equals(EmployeePromotionFieldNames.CURRENT_DESIGNATION)) {
            DesignationDto currentDesignation = new DesignationDto();
            currentDesignation.setDesignationId(Integer.parseInt(validatedFieldValue));
            employeePromotion.setCurrentDesignation(currentDesignation);
        } else if (fieldName.equals(EmployeePromotionFieldNames.EFFECTIVE_FROM)) {
            employeePromotion.setEffectiveFrom(validatedFieldValue);
        } else if (fieldName.equals(EmployeePromotionFieldNames.EMPLOYEE_NUMBER)) {
            EmployeeDto employee = new EmployeeDto();
            employee.setEmployeeNumber(validatedFieldValue);
            employeePromotion.setEmployee(employee);
        } else if (fieldName.equals(EmployeePromotionFieldNames.PRIOR_DESIGNATION)) {
            DesignationDto priorDesignation = new DesignationDto();
            priorDesignation.setDesignationId(Integer.parseInt(validatedFieldValue));
            employeePromotion.setPriorDesignation(priorDesignation);
        }
        return employeePromotion;
    }

    /**
     * Update employee subject.
     * 
     * @param employeeSubject the employee subject
     * @param rule the rule
     * @param fieldValue the field value
     * @return the employee subject dto
     * @throws DataException the data exception
     */
    private EmployeeSubjectDto updateEmployeeSubject(
            EmployeeSubjectDto employeeSubject, Rule rule, String fieldValue)
            throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();

        RegisteredSubjectDto registeredSubject = employeeSubject.getRegisteredSubject();
        if (registeredSubject == null) {
            registeredSubject = new RegisteredSubjectDto();
        }
        RegisteredClassDto registeredClass = registeredSubject.getRegisteredClass();
        if (registeredClass == null) {
            registeredClass = new RegisteredClassDto();
        }
        SchoolDto school = registeredClass.getSchool();
        if (school == null) {
            school = new SchoolDto();
        }
        BranchDto branch = school.getBranch();
        if (branch == null) {
            branch = new BranchDto();
        }
        DivisionDto division = school.getDivision();
        if (division == null) {
            division = new DivisionDto();
        }

        String validatedFieldValue = DataTypeValidator.validate(fieldValue, dataType, fieldName);
        if (fieldName.equals(EmployeeSubjectsFieldNames.BRANCH_CODE)) {
            branch.setBranchCode(validatedFieldValue);
            school.setBranch(branch);
        } else if (fieldName.equals(EmployeeSubjectsFieldNames.CLASS_NAME)) {
            ClassDto classDto = new ClassDto();
            classDto.setClassName(validatedFieldValue);
            registeredClass.setClassDto(classDto);
        } else if (fieldName.equals(EmployeeSubjectsFieldNames.DIVISION_CODE)) {
            division.setDivisionCode(validatedFieldValue);
            school.setDivision(division);
        } else if (fieldName.equals(EmployeeSubjectsFieldNames.EMPLOYEE_NUMBER)) {
            EmployeeDto employee = new EmployeeDto();
            employee.setEmployeeNumber(validatedFieldValue);
            employeeSubject.setEmployee(employee);
        } else if (fieldName.equals(EmployeeSubjectsFieldNames.MEDIUM_NAME)) {
            MediumDto medium = new MediumDto();
            medium.setDescription(validatedFieldValue);
            registeredClass.setMedium(medium);
        } else if (fieldName.equals(EmployeeSubjectsFieldNames.SCHOOL_NAME)) {
            school.setSchoolName(validatedFieldValue);
            registeredClass.setSchool(school);
        } else if (fieldName.equals(EmployeeSubjectsFieldNames.SECTION_NAME)) {
            SectionDto section = new SectionDto();
            section.setSectionName(validatedFieldValue);
            registeredClass.setSection(section);
        } else if (fieldName.equals(EmployeeSubjectsFieldNames.SUBJECT_NAME)) {
            SubjectDto subject = new SubjectDto();
            subject.setSubjectName(validatedFieldValue);
            registeredSubject.setSubject(subject );
        }
        school.setBranch(branch);
        school.setDivision(division);
        registeredClass.setSchool(school);
        registeredSubject.setRegisteredClass(registeredClass);
        employeeSubject.setRegisteredSubject(registeredSubject);
        return employeeSubject;
    }

    /**
     * Validate designation.
     * 
     * @param inDesignation the in designation
     * @return the designation dto
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    public DesignationDto validateDesignation(DesignationDto inDesignation)
            throws ValidationException, DaoException, CacheException {
        if (inDesignation == null) {
            throw new ValidationException("Designation information is not specified.");
        }
        int designationId = inDesignation.getDesignationId();
        if (designationId <= 0) {
            throw new ValidationException("Designation is a required value.");
        }
        DesignationDto outDesignation = getDesignation(designationId);
        if (outDesignation == null || outDesignation.getDesignationId() == 0) {
            throw new ValidationException("Designation (" + designationId + ") does not exists.");
        }
        return outDesignation;
    }

    /**
     * Validate employment status.
     * 
     * @param inEmploymentStatus the in employment status
     * @return the employment status
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    private EmploymentStatus validateEmploymentStatus(
            EmploymentStatus inEmploymentStatus) throws ValidationException,
            DaoException, CacheException {
        if (inEmploymentStatus == null) {
            throw new ValidationException("Employment Status is not specified.");
        }
        String description = inEmploymentStatus.getDescription();
        if (StringUtil.isNullOrBlank(description)) {
            throw new ValidationException("Employment Status must be specified.");
        }
        EmploymentStatus outEmploymentStatus = getEmploymentStatus(description);
        if (outEmploymentStatus == null || outEmploymentStatus.getStatusId() == 0) {
            throw new ValidationException("Employment Status (" + description + ") does not exists.");
        }
        return outEmploymentStatus;
    }

    /**
     * Validate employee number.
     * 
     * @param inEmployee the in employee
     * @param cache the cache
     * @return the employee dto
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    public EmployeeDto validateEmployeeNumber(EmployeeDto inEmployee, boolean cache)
            throws ValidationException, DaoException, CacheException {
        if (inEmployee == null) {
            throw new ValidationException("Employee is not specified.");
        }
        String employeeNumber = inEmployee.getEmployeeNumber();
        return validateEmployeeNumber(employeeNumber, cache);
    }

    /**
     * Validate employee number.
     * 
     * @param employeeNumber the employee number
     * @return the employee dto
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     * @throws ValidationException the validation exception
     */
    public EmployeeDto validateEmployeeNumber(String employeeNumber)
            throws DaoException, CacheException, ValidationException {
        return validateEmployeeNumber(employeeNumber, false);
    }

    /**
     * Validate employee number.
     * 
     * @param employeeNumber the employee number
     * @param cache the cache
     * @return the employee dto
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     * @throws ValidationException the validation exception
     */
    public EmployeeDto validateEmployeeNumber(String employeeNumber,
            boolean cache) throws DaoException, CacheException,
            ValidationException {
        EmployeeDto outEmployee = null;
        if (StringUtil.isNullOrBlank(employeeNumber)) {
            throw new ValidationException("Employee Number must be specified.");
        }
        if (cache) {
            outEmployee = getEmployee(employeeNumber);
        } else {
            outEmployee = employeeDao.get(employeeNumber);
        }
        if (outEmployee == null || outEmployee.getEmployeeId() == 0) {
            throw new ValidationException("Employee (" + employeeNumber + ") does not exists.");
        }
        return outEmployee;
    }

    /**
     * Validate employee number.
     * 
     * @param inEmployee the in employee
     * @return the employee dto
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    private EmployeeDto validateEmployeeNumber(EmployeeDto inEmployee)
            throws ValidationException, DaoException, CacheException {
        return validateEmployeeNumber(inEmployee, false);
    }

    /**
     * Validate employee.
     * 
     * @param employee the employee
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     * @throws InvalidDataException the invalid data exception
     */
    private void validateEmployee(EmployeeDto employee)
            throws ValidationException, DaoException, CacheException,
            InvalidDataException {
        // Validate branch.
        employee.setEmployedAtBranch(validateBranch(employee.getEmployedAtBranch()));
        // validate designation
        employee.setDesignation(validateDesignation(employee.getDesignation()));
        EmployeeDto reportingTo = employee.getReportingTo();
        String reportingToEmployeeNumber = reportingTo.getEmployeeNumber();
        if (StringUtil.isNullOrBlank(reportingToEmployeeNumber)
                || reportingToEmployeeNumber.trim().equals("0")) {
            reportingTo.setEmployeeNumber(null);
        } else {
            employee.setReportingTo(validateEmployeeNumber(reportingTo));
        }
        
        if (!StringUtil.isNullOrBlank(reportingTo.getEmployeeNumber())
                && !reportingTo.getEmployeeNumber().trim().equals("0")) {
            employee.setReportingTo(validateEmployeeNumber(reportingTo));
        }
        // Validate employment status
        employee.setEmploymentStatus(validateEmploymentStatus(employee.getEmploymentStatus()));
        DateUtil.checkFromDateToDateSequence(
                employee.getEmploymentEndDate(), employee.getEmploymentEndDate());
    }

    /**
     * Validate employee contact.
     * 
     * @param employeeContact the employee contact
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    private void validateEmployeeContact(EmployeeContact employeeContact)
            throws ValidationException, DaoException, CacheException {
        employeeContact.setEmployee(validateEmployeeNumber(employeeContact.getEmployee()));
        Relationship emergencyContactRelationship = employeeContact.getEmergencyContactRelationship();
        validateRelationship(emergencyContactRelationship);
        if (emergencyContactRelationship == null) {
            throw new ValidationException("Invalide relationship code: " + emergencyContactRelationship);
        }
    }


    /**
     * Validate employee education.
     * 
     * @param employeeEducation the employee education
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    public void validateEmployeeEducation(EmployeeEducation employeeEducation)
            throws ValidationException, DaoException, CacheException {
        employeeEducation.setEmployee(validateEmployeeNumber(employeeEducation.getEmployee()));
    }

    /**
     * Validate employee experience.
     * 
     * @param employeeExperience the employee experience
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     * @throws InvalidDataException the invalid data exception
     */
    public void validateEmployeeExperience(EmployeeExperience employeeExperience)
            throws ValidationException, DaoException, CacheException,
            InvalidDataException {
        if (employeeExperience == null) {
            throw new ValidationException("Employment Experience is not specified.");
        }
        employeeExperience.setEmployee(validateEmployeeNumber(employeeExperience.getEmployee()));
        /*employeeExperience.setFromDate(
                DataTypeConversionUtil.toStringDate(
                        employeeExperience.getFromDate(), FormatUtil.dd_MM_yyyy, FormatUtil.yyyy_MM_dd));
        employeeExperience.setToDate(
                DataTypeConversionUtil.toStringDate(
                        employeeExperience.getToDate(), FormatUtil.dd_MM_yyyy, FormatUtil.yyyy_MM_dd));*/
        DateUtil.checkFromDateToDateSequence(employeeExperience.getFromDate(), employeeExperience.getToDate());
        employeeExperience.setExperieceInMonth(
                DateUtil.dateDiffInMonths(
                        employeeExperience.getFromDate(), employeeExperience.getToDate()));
    }

    /**
     * Gets the employment status.
     * 
     * @param employmentStatusCode the employment status code
     * @return the employment status
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    private EmploymentStatus getEmploymentStatus(String employmentStatusCode)
            throws DaoException, CacheException {
        EmploymentStatus employmentStatus = null;
        Object entry = getEntry(CacheKeyConstants.EMPLOYMENT_STATUS, employmentStatusCode);
        if (entry == null) {
            employmentStatus = employmentStatusDao.get(employmentStatusCode);
            putEntry(CacheKeyConstants.EMPLOYMENT_STATUS, employmentStatusCode, employmentStatus);
        } else {
            employmentStatus = (EmploymentStatus) entry;
        }
        return employmentStatus;
    }

    /**
     * Validate employee document.
     * 
     * @param employeeDocument the employee document
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     * @throws InvalidDataException the invalid data exception
     */
    public void validateEmployeeDocument(EmployeeDocument employeeDocument)
            throws ValidationException, DaoException, CacheException,
            InvalidDataException {
        if (employeeDocument == null) {
            throw new ValidationException("Employment Document is not specified.");
        }
        employeeDocument.setEmployee(validateEmployeeNumber(employeeDocument.getEmployee()));
        // check if the document is valid
        employeeDocument.setDocument(validateDocument(employeeDocument.getDocument(), UserType.EMPLOYEE));
        /*employeeDocument.setDocumentExpiryDate(
                DataTypeConversionUtil.toStringDate(
                        employeeDocument.getDocumentExpiryDate(), FormatUtil.dd_MM_yyyy, FormatUtil.yyyy_MM_dd));*/
        // ensure expiry date is a future date.
        if (!DateUtil.isFutureDate(employeeDocument.getDocumentExpiryDate())) {
            throw new ValidationException("Document Expiry Date must be a date in future");
        }
    }

    /**
     * Validate employee promotion.
     * 
     * @param employeePromotion the employee promotion
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     * @throws InvalidDataException the invalid data exception
     */
    public void validateEmployeePromotion(EmployeePromotion employeePromotion)
            throws ValidationException, DaoException, CacheException,
            InvalidDataException {
        if (employeePromotion == null) {
            throw new ValidationException("Employment Experience is not specified.");
        }
        employeePromotion.setEmployee(validateEmployeeNumber(employeePromotion.getEmployee()));
        // check if current and previous designations are valid
        employeePromotion.setPriorDesignation(validateDesignation(employeePromotion.getPriorDesignation()));
        employeePromotion.setCurrentDesignation(validateDesignation(employeePromotion.getCurrentDesignation()));
        // Check if both designations are not same.
        DesignationDto priorDesignation = employeePromotion.getPriorDesignation();
        DesignationDto currentDesignation = employeePromotion.getCurrentDesignation();
        if (priorDesignation.getDesignationId() == currentDesignation.getDesignationId()) {
            throw new ValidationException("Prior Designation and Current Designation are same.");
        }
        /*employeePromotion.setEffectiveFrom(DataTypeConversionUtil.toStringDate(
                employeePromotion.getEffectiveFrom(), FormatUtil.dd_MM_yyyy, FormatUtil.yyyy_MM_dd));*/
        /*if (!DateUtil.isFutureDate(employeePromotion.getEffectiveFrom())) {
            throw new ValidationException("Effective From date must be a date in future");
        }*/
    }

    /**
     * Validate employee subject.
     * 
     * @param employeeSubject the employee subject
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    public void validateEmployeeSubject(EmployeeSubjectDto employeeSubject)
            throws ValidationException, DaoException, CacheException {
        if (employeeSubject == null) {
            throw new ValidationException("Employee Subject is not specified.");
        }
        employeeSubject.setEmployee(validateEmployeeNumber(employeeSubject.getEmployee(), true));
        RegisteredSubjectDto registeredSubject = employeeSubject.getRegisteredSubject();

        // check if registered subject is present or not
        employeeSubject.setRegisteredSubject(validateRegisteredSubject(registeredSubject));
    }

    /**
     * Handle employee data.
     * 
     * @param importRecordStatusDto the import record status dto
     * @throws DaoException the dao exception
     * @throws DataException the data exception
     * @throws CacheException the cache exception
     */
    private void handleEmployeeData(ImportRecordStatusDto importRecordStatusDto)
            throws DaoException, DataException, CacheException {

        EmployeeDto employee = (EmployeeDto) importRecordStatusDto.getContent();
        String employeeNumber = employee.getEmployeeNumber();

        // Update if employee already exists or add if the employee does not exist.
        EmployeeDto existingEmployee = employeeDao.get(employeeNumber);

        // Create a new employee
        if (existingEmployee == null) {
            int employeeId = employeeDao.create(employee);
            if (employeeId > 0) {
                // Only Verified employees will receive notifications.
                // There are possibilities that incomplete data is uploaded.
                // These records must be verified. Hence dropping notification
                // functionality from here and notification will be sent when
                // the employee is verified.
                importRecordStatusDto.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                importRecordStatusDto.setStatusDescription("Employee with employee Number (" + employeeNumber + ") has been added successfully.");
            } else {
                importRecordStatusDto.setStatusDescription("System encountered problems while adding with employee Number ("
                        + employee.getEmployeeNumber() + ").");
            }
        } else {
            // Update the existing employee.
            if (employeeDao.update(existingEmployee.getEmployeeId(), employee)) {
                importRecordStatusDto.setStatusCode(ImportRecordStatusDto.STATUS_UPDATED);
                importRecordStatusDto.setStatusDescription("Employee with employee Number (" + employeeNumber + ") has been updated successfully.");
            } else {
                importRecordStatusDto.setStatusDescription("System encountered problems while updating employee with employee Number (" + employeeNumber + ").");
            }
        }
    }

    /**
     * Handle employee contact data.
     * 
     * @param importRecordStatus the import record status
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    private void handleEmployeeContactData(
            ImportRecordStatusDto importRecordStatus)
            throws ValidationException, DaoException, CacheException {
        EmployeeContact employeeContact = (EmployeeContact) importRecordStatus.getContent();
        EmployeeDto existingEmployee =  validateEmployeeNumber(employeeContact.getEmployee(), true);
        int employeeId = existingEmployee.getEmployeeId();
        String employeeNumber = existingEmployee.getEmployeeNumber();
        EmployeeContact existingEmployeeContact = employeeContactDao.get(employeeId);
        // If exists update else create
        if (existingEmployeeContact == null) {
            boolean created = employeeContactDao.create(employeeId, employeeContact);
            if (created) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                importRecordStatus.setStatusDescription("Employee (" + employeeNumber + ") contact has been added successfully.");
            } else {
                importRecordStatus.setStatusDescription("System encountered problems while adding contact for employee Number ("
                        + employeeNumber + ").");
            }
        } else {
            boolean updated = employeeContactDao.update(employeeId, employeeContact);
            if (updated) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UPDATED);
                importRecordStatus.setStatusDescription("Employee (" + employeeNumber + ") contact has been updated successfully.");
            } else {
                importRecordStatus.setStatusDescription("System encountered problems while updating contact for employee Number ("
                        + employeeNumber + ").");
            }
        }
    }

    /**
     * Handle employee education.
     * 
     * @param importRecordStatus the import record status
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    private void handleEmployeeEducation(
            ImportRecordStatusDto importRecordStatus)
            throws ValidationException, DaoException, CacheException {
        EmployeeEducation employeeEducation = (EmployeeEducation) importRecordStatus.getContent();
        EmployeeDto existingEmployee =  validateEmployeeNumber(employeeEducation.getEmployee(), true);
        int employeeId = existingEmployee.getEmployeeId();
        String employeeNumber = existingEmployee.getEmployeeNumber();
        // check if it already exists or not.
        EmployeeEducation existingEmployeeEducation = employeeEducationDao.get(employeeId, employeeEducation);
        if (existingEmployeeEducation == null) {
            int educationId = employeeEducationDao.create(employeeId, employeeEducation);
            if (educationId > 0) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                importRecordStatus.setStatusDescription("Employee (" + employeeNumber + ") education has been added successfully.");
            } else {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                importRecordStatus.setStatusDescription("System encountered problems while adding education for employee Number ("
                        + employeeNumber + ").");
            }
        } else {
            int educationId = existingEmployeeEducation.getEducationId();
            boolean updated = employeeEducationDao.update(educationId, employeeEducation);
            if (updated) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UPDATED);
                importRecordStatus.setStatusDescription("Employee (" + employeeNumber + ") education has been updated successfully.");
            } else {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                importRecordStatus.setStatusDescription("System encountered problems while updating education for employee Number ("
                        + employeeNumber + ").");
            }
        }
    }

    /**
     * Handle employee experience.
     * 
     * @param importRecordStatus the import record status
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    private void handleEmployeeExperience(
            ImportRecordStatusDto importRecordStatus)
            throws ValidationException, DaoException, CacheException {
        EmployeeExperience employeeExperience = (EmployeeExperience) importRecordStatus.getContent();
        EmployeeDto existingEmployee =  validateEmployeeNumber(employeeExperience.getEmployee(), true);
        int employeeId = existingEmployee.getEmployeeId();
        String employeeNumber = existingEmployee.getEmployeeNumber();
        // check if it already exists or not.
        EmployeeExperience existingEmployeeExperience = employeeExperienceDao.get(employeeId, employeeExperience);
        if (existingEmployeeExperience == null) {
            int educationId = employeeExperienceDao.create(employeeId, employeeExperience);
            if (educationId > 0) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                importRecordStatus.setStatusDescription("Employee (" + employeeNumber + ") experience has been added successfully.");
            } else {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                importRecordStatus.setStatusDescription("System encountered problems while adding experience for employee Number ("
                        + employeeNumber + ").");
            }
        } else {
            int educationId = existingEmployeeExperience.getExperienceId();
            boolean updated = employeeExperienceDao.update(educationId, employeeExperience);
            if (updated) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UPDATED);
                importRecordStatus.setStatusDescription("Employee (" + employeeNumber + ") experience has been updated successfully.");
            } else {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                importRecordStatus.setStatusDescription("System encountered problems while updating experience for employee Number ("
                        + employeeNumber + ").");
            }
        }
    }

    /**
     * Handle employee document.
     * 
     * @param importRecordStatus the import record status
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    private void handleEmployeeDocument(ImportRecordStatusDto importRecordStatus)
            throws ValidationException, DaoException, CacheException {

        EmployeeDocument employeeDocument = (EmployeeDocument) importRecordStatus.getContent();
        EmployeeDto existingEmployee =  validateEmployeeNumber(employeeDocument.getEmployee(), true);
        int employeeId = existingEmployee.getEmployeeId();
        String employeeNumber = existingEmployee.getEmployeeNumber();
        DocumentDto document = employeeDocument.getDocument();
        EmployeeDocument existingEmployeeDocument = employeeDocumentDao.get(employeeId, document.getDocumentId());

        // If exists update else create
        if (existingEmployeeDocument == null) {
            int employeeDocumentId = employeeDocumentDao.create(employeeId, employeeDocument);
            if (employeeDocumentId > 0) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                importRecordStatus.setStatusDescription("Employee (" + employeeNumber + ") document has been added successfully.");
            } else {
                importRecordStatus.setStatusDescription("System encountered problems while adding document for employee Number ("
                        + employeeNumber + ").");
            }
        } else {
            int employeeDocumentId = existingEmployeeDocument.getEmployeeDocumentId();
            boolean updated = employeeDocumentDao.update(employeeDocumentId, employeeDocument);
            if (updated) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UPDATED);
                importRecordStatus.setStatusDescription("Employee (" + employeeNumber + ") document has been updated successfully.");
            } else {
                importRecordStatus.setStatusDescription("System encountered problems while updating document for employee Number ("
                        + employeeNumber + ").");
            }
        }
    }

    /**
     * Handle employee promotion.
     * 
     * @param importRecordStatus the import record status
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    private void handleEmployeePromotion(
            ImportRecordStatusDto importRecordStatus)
            throws ValidationException, DaoException, CacheException {
        EmployeePromotion employeePromotion = (EmployeePromotion) importRecordStatus.getContent();
        EmployeeDto existingEmployee =  validateEmployeeNumber(employeePromotion.getEmployee(), true);
        int employeeId = existingEmployee.getEmployeeId();
        String employeeNumber = existingEmployee.getEmployeeNumber();
        // check if it already exists or not.
        EmployeePromotion existingEmployeePromotion = employeePromotionDao.get(employeeId, employeePromotion);
        if (existingEmployeePromotion == null) {
            int educationId = employeePromotionDao.create(employeeId, employeePromotion);
            if (educationId > 0) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                importRecordStatus.setStatusDescription("Employee (" + employeeNumber + ") promotion has been added successfully.");
            } else {
                importRecordStatus.setStatusDescription("System encountered problems while adding promotion for employee Number ("
                        + employeeNumber + ").");
            }
        } else {
            int promotionId = existingEmployeePromotion.getPromotionId();
            boolean updated = employeePromotionDao.update(promotionId, employeePromotion);
            if (updated) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UPDATED);
                importRecordStatus.setStatusDescription("Employee (" + employeeNumber + ") promotion has been updated successfully.");
            } else {
                importRecordStatus.setStatusDescription("System encountered problems while updating promotion for employee Number ("
                        + employeeNumber + ").");
            }
        }
    }

    /**
     * Handle employee subject.
     * 
     * @param importRecordStatus the import record status
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    private void handleEmployeeSubject(ImportRecordStatusDto importRecordStatus)
            throws ValidationException, DaoException, CacheException {
        EmployeeSubjectDto employeeSubject = (EmployeeSubjectDto) importRecordStatus.getContent();
        EmployeeDto employee = employeeSubject.getEmployee();
        RegisteredSubjectDto registeredSubject = employeeSubject.getRegisteredSubject();
        int employeeId = employee.getEmployeeId();
        int registeredSubjectId = registeredSubject.getSubjectId();
        String employeeNumber = employee.getEmployeeNumber();
        SubjectDto subject = registeredSubject.getSubject();
        RegisteredClassDto registeredClass = registeredSubject.getRegisteredClass();
        ClassDto classDto = registeredClass.getClassDto();
        String className = classDto.getClassName();
        String subjectName = subject.getSubjectName();

        EmployeeSubjectDto existingEmployeeSubject = employeeSubjectDao.get(employeeId, registeredSubjectId);
        if (existingEmployeeSubject == null) {
            int employeeSubjectId = employeeSubjectDao.create(employeeId, employeeSubject);
            if (employeeSubjectId > 0) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                importRecordStatus.setStatusDescription(
                        MessageFormat.format(EMPLOYEE_SUBJECT_ADD_SUCCESS, subjectName, className, employeeNumber));
            } else {
                importRecordStatus.setStatusDescription(
                        MessageFormat.format(EMPLOYEE_SUBJECT_ADD_FAIL, subjectName, className, employeeNumber));
            }
        } else {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UNPROCESSED);
            importRecordStatus.setStatusDescription(
                    MessageFormat.format(EMPLOYEE_SUBJECT_UPDATE_FAIL, subjectName, className, employeeNumber));
        }
    }

    /**
     * Gets the employee.
     * 
     * @param employeeNumber the employee number
     * @return the employee
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected EmployeeDto getEmployee(String employeeNumber) throws DaoException, CacheException {
        EmployeeDto employee = null;
        Object entry = getEntry(CacheKeyConstants.EMPLOYEE, employeeNumber);
        if (entry == null) {
            employee = employeeDao.get(employeeNumber);
            putEntry(CacheKeyConstants.EMPLOYEE, employeeNumber, employee);
        } else {
            employee = (EmployeeDto) entry;
        }
        return employee;
    }

}
