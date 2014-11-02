package com.myschool.exim.domain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.DivisionDto;
import com.myschool.clazz.dto.ClassDto;
import com.myschool.clazz.dto.MediumDto;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.clazz.dto.SectionDto;
import com.myschool.common.constants.CacheKeyConstants;
import com.myschool.common.dto.DocumentDto;
import com.myschool.common.dto.FamilyMemberDto;
import com.myschool.common.dto.PersonalDetailsDto;
import com.myschool.common.dto.Relationship;
import com.myschool.common.dto.Rule;
import com.myschool.common.dto.StatusDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.util.ConversionUtil;
import com.myschool.common.util.ResourceUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.ExportStatusDto;
import com.myschool.exim.dto.ImportRecordStatusDto;
import com.myschool.infra.cache.exception.CacheException;
import com.myschool.infra.filesystem.exception.RuleException;
import com.myschool.school.dto.SchoolDto;
import com.myschool.student.dao.AdmissionStatusDao;
import com.myschool.student.dto.AdmissionStatus;
import com.myschool.student.dto.StudentDocument;
import com.myschool.student.dto.StudentDto;
import com.myschool.student.fields.StudentDocumentFieldNames;
import com.myschool.student.fields.StudentFamilyFieldNames;
import com.myschool.student.fields.StudentFieldNames;

/**
 * The Class StudentEximManager.
 */
@Component
public class StudentEximManager extends AbstractEximManager {

    /** The STUDENT_DOCUMENT_ADD_SUCCESS. */
    private static String STUDENT_DOCUMENT_ADD_SUCCESS = "Document ({0}) has been successfully added to Student ({1})";

    /** The STUDENT_DOCUMENT_ADD_FAIL. */
    private static String STUDENT_DOCUMENT_ADD_FAIL = "System encountered problems while adding Document ({0}) has been successfully added to Student ({1})";

    /** The STUDENT_DOCUMENT_UPDATE_SUCCESS. */
    private static String STUDENT_DOCUMENT_UPDATE_SUCCESS = "Document ({0}) has been successfully updated to Student ({1})";

    /** The STUDENT_DOCUMENT_UPDATE_FAIL. */
    private static String STUDENT_DOCUMENT_UPDATE_FAIL = "System encountered problems while updating Document ({0}) has been successfully added to Student ({1})";

    /** The admission status dao. */
    @Autowired
    private AdmissionStatusDao admissionStatusDao;

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
        if (eximPolicy == EximPolicy.STUDENTS) {
            if (content == null) {
                content = new StudentDto();
            }
            content = updateStudent((StudentDto) content, rule, fieldValue);
        } else if (eximPolicy == EximPolicy.STUDENT_FAMILY) {
            if (content == null) {
                content = new StudentDto();
            }
            content = updateStudent((StudentDto) content, rule, fieldValue);
        } else if (eximPolicy == EximPolicy.STUDENT_DOCUMENT) {
            if (content == null) {
                content = new StudentDocument();
            }
            content = updateStudentDocument((StudentDocument) content, rule, fieldValue);
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
            if (eximPolicy == EximPolicy.STUDENTS) {
                validateStudent((StudentDto) content);
            } else if (eximPolicy == EximPolicy.STUDENT_FAMILY) {
                validateStudentFamily((StudentDto) content);
            } else if (eximPolicy == EximPolicy.STUDENT_DOCUMENT) {
                validateStudentDocument((StudentDocument) content);
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
            if (eximPolicy == EximPolicy.STUDENTS) {
                handleStudentData(importRecordStatus);
            } else if (eximPolicy == EximPolicy.STUDENT_FAMILY) {
                handleStudentFamilyData(importRecordStatus);
            } else if (eximPolicy == EximPolicy.STUDENT_DOCUMENT) {
                handleStudentDocument(importRecordStatus);
            }
        } catch (Exception exception) {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
            importRecordStatus.setStatusDescription(exception.getMessage());
        }
    }

    /**
     * Handle student data.
     * 
     * @param importRecordStatus the import record status
     * @return the int
     * @throws DaoException the dao exception
     * @throws DataException the data exception
     * @throws CacheException the cache exception
     */
    private void handleStudentData(ImportRecordStatusDto importRecordStatus)
            throws DaoException, DataException, CacheException {
        int studentId = 0;
        StudentDto student = (StudentDto) importRecordStatus.getContent();
        String admissionNumber = student.getAdmissionNumber();

        // Update if student already exists or add if the student does not exist.
        StudentDto existingStudent = studentDao.get(admissionNumber);
        // Create a new student
        if (existingStudent == null) {
            //OrganizationProfileDto organizationProfile = profileManager.getOrganizationProfile();
            //student.setAcademicYear(organizationProfile.getCurrentAcademicYear());
            studentId = studentDao.create(student);
            if (studentId > 0) {
                // Only Verified employees will receive notifications.
                // There are possibilities that incomplete data is uploaded.
                // These records must be verified. Hence dropping notification
                // functionality from here and notification will be sent when
                // the employee is verified.
                /*// create a login ID for the student that has been created
                student.setStudentId(studentId);
                userDao.createUser(UserDataAssembler.createUser(student));*/
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                importRecordStatus.setStatusDescription("Student with admisssion Number (" + admissionNumber + ") has been added successfully.");
            } else {
                importRecordStatus.setStatusDescription("System encountered problems while adding with admission Number ("
                        + admissionNumber + ").");
            }
        } else {
            // Update the existing student.
            if (studentDao.update(existingStudent.getStudentId(), student)) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UPDATED);
                importRecordStatus.setStatusDescription("Student with admission Number (" + admissionNumber + ") has been updated successfully.");
            } else {
                importRecordStatus.setStatusDescription("System encountered problems while updating student with admission Number (" + admissionNumber + ").");
            }
        }
    }

    /**
     * Handle student family data.
     * 
     * @param importRecordStatus the import record status
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     * @throws DataException the data exception
     */
    private void handleStudentFamilyData(ImportRecordStatusDto importRecordStatus)
            throws DaoException, CacheException, DataException {

        int familyMemberId = 0;
        StudentDto student = (StudentDto) importRecordStatus.getContent();
        int studentId = student.getStudentId();
        String admissionNumber = student.getAdmissionNumber();

        List<FamilyMemberDto> familyMembers = student.getFamilyMembers();
        // Update if student family already exists or add if the student family if does not exist.
        // Create a student family details
        for (FamilyMemberDto familyMember : familyMembers) {
            // get the family member by student id, relationship and name
            FamilyMemberDto existingFamilyMember = getFamiyMember(studentId,
                    familyMember.getRelationship(), familyMember.getName());
            if (existingFamilyMember == null) {
                familyMemberId = studentFamilyDao.create(studentId, familyMember);
                if (familyMemberId > 0) {
                    importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                    importRecordStatus.setStatusDescription("Student Family member has been created for student (" + admissionNumber + ").");
                } else {
                    importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                    importRecordStatus.setStatusDescription("System encountered problems while adding family member for student ("
                            + admissionNumber + ").");
                }
            } else {
                familyMemberId = familyMember.getFamilyMemberId();
                boolean updated = studentFamilyDao.update(familyMemberId, familyMember);
                if (updated) {
                    importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UPDATED);
                    importRecordStatus.setStatusDescription("Student Family member has been updated for student (" + admissionNumber + ").");
                } else {
                    importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                    importRecordStatus.setStatusDescription("System encountered problems while updating family member for student ("
                            + admissionNumber + ").");
                }
            }
            
        }
    }

    /**
     * Handle student document.
     * 
     * @param importRecordStatus the import record status
     * @throws DaoException the dao exception
     */
    private void handleStudentDocument(ImportRecordStatusDto importRecordStatus) throws DaoException {
        StudentDocument studentDocument = (StudentDocument) importRecordStatus.getContent();
        StudentDto student = studentDocument.getStudent();
        DocumentDto document = studentDocument.getDocument();
        int studentId = student.getStudentId();
        String admissionNumber = student.getAdmissionNumber();
        int documentId = document.getDocumentId();

        StudentDocument existingStudentDocument = studentDocumentDao.get(studentId, documentId);
        // Create a student family details
        if (existingStudentDocument == null) {
            if (studentDocumentDao.create(studentId, studentDocument) > 0) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                importRecordStatus.setStatusDescription(MessageFormat.format(
                        STUDENT_DOCUMENT_ADD_SUCCESS, document.getName(), admissionNumber));
            } else {
                importRecordStatus.setStatusDescription(MessageFormat.format(
                        STUDENT_DOCUMENT_ADD_FAIL, document.getName(), admissionNumber));
            }
        } else {
            // Update the existing student.
            studentDocument.setStudentDocumentId(existingStudentDocument.getStudentDocumentId());
            if (studentDocumentDao.update(studentId, studentDocument)) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UPDATED);
                importRecordStatus.setStatusDescription(MessageFormat.format(
                        STUDENT_DOCUMENT_UPDATE_SUCCESS, document.getName(), admissionNumber));
            } else {
                importRecordStatus.setStatusDescription(MessageFormat.format(
                        STUDENT_DOCUMENT_UPDATE_FAIL, document.getName(), admissionNumber));
            }
        }
    }

    /**
     * Validate admission status.
     * 
     * @param inAdmissionStatus the in admission status
     * @return the admission status
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    private AdmissionStatus validateAdmissionStatus(
            AdmissionStatus inAdmissionStatus) throws ValidationException,
            DaoException, CacheException {
        if (inAdmissionStatus == null) {
            throw new ValidationException("Admission Status is not specified.");
        }
        String description = inAdmissionStatus.getDescription();
        if (StringUtil.isNullOrBlank(description)) {
            throw new ValidationException("Admission Status must be specified.");
        }
        AdmissionStatus outAdmissionStatus = getAdmissionStatus(description);
        if (outAdmissionStatus == null || outAdmissionStatus.getStatusId() == 0) {
            throw new ValidationException("Admission Status (" + description + ") does not exists.");
        }
        return outAdmissionStatus;
    }

    /**
     * Validate student.
     * 
     * @param student the student
     * @return the student dto
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     * @throws ValidationException the validation exception
     */
    private StudentDto validateStudent(StudentDto student) throws DaoException,
            CacheException, ValidationException {
        student.setRegisteredClassDto(validateRegisteredClass(student.getRegisteredClassDto()));
        student.setAdmissionStatus(validateAdmissionStatus(student.getAdmissionStatus()));
        return student;
    }

    /**
     * Validate student family.
     * 
     * @param student the student
     * @return the student dto
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     * @throws ValidationException the validation exception
     */
    private StudentDto validateStudentFamily(StudentDto student)
            throws DaoException, CacheException, ValidationException {
        
        String admissionNumber = student.getAdmissionNumber();
        // Update if student already exists or add if the student does not exist.
        StudentDto existingStudent = getStudent(admissionNumber);
        // Create a new student
        if (existingStudent == null || existingStudent.getStudentId() == 0) {
            throw new ValidationException("Student (" + admissionNumber + ") not found.");
        }
        student.setStudentId(existingStudent.getStudentId());
        return student;
    }

    /**
     * Validate student document.
     * 
     * @param studentDocument the student document
     * @return the student document
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     * @throws ValidationException the validation exception
     */
    private StudentDocument validateStudentDocument(StudentDocument studentDocument)
            throws DaoException, CacheException, ValidationException {

        StudentDto student = studentDocument.getStudent();
        String admissionNumber = student.getAdmissionNumber();
        // Update if student already exists or add if the student does not exist.
        StudentDto existingStudent = getStudent(admissionNumber);
        // Create a new student
        if (existingStudent == null || existingStudent.getStudentId() == 0) {
            throw new ValidationException("Student (" + admissionNumber + ") not found.");
        }
        student.setStudentId(existingStudent.getStudentId());
        DocumentDto document = studentDocument.getDocument();
        DocumentDto existingDocument = getDocument(document.getName());
        if (existingDocument == null) {
            throw new ValidationException("Document (" + admissionNumber + ") not found.");
        }
        studentDocument.setDocument(existingDocument);
        return studentDocument;
    }
    
    /**
     * Update student.
     *
     * @param student the student
     * @param rule the rule
     * @param fieldValue the field value
     * @return the object
     * @throws DataException the data exception
     */
    public Object updateStudent(StudentDto student, Rule rule,
            String fieldValue) throws DataException{
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();

        FamilyMemberDto familyMember = null;

        PersonalDetailsDto personalDetails = student.getPersonalDetails();
        if (personalDetails == null) {
            personalDetails = new PersonalDetailsDto();
        }

        List<FamilyMemberDto> familyMembers = student.getFamilyMembers();
        if (familyMembers == null) {
            familyMembers = new ArrayList<FamilyMemberDto>();
            familyMembers.add(new FamilyMemberDto());
        }
        familyMember = familyMembers.get(0);

        RegisteredClassDto registeredClass = student.getRegisteredClassDto();
        if (registeredClass == null) {
            registeredClass = new RegisteredClassDto();
        }

        SchoolDto school = registeredClass.getSchool();
        if (school == null) {
            school = new SchoolDto();
        }

        List<StudentDocument> documentsSubmitted = student.getDocumentsSubmitted();
        if (documentsSubmitted == null) {
            documentsSubmitted = new ArrayList<StudentDocument>();
        }

        String validatedValue = DataTypeValidator.validate(fieldValue, dataType, fieldName);
        // Fill up the Student DTO
        if (fieldName.equals(StudentFieldNames.BRANCH_CODE)) {
            BranchDto branch = new BranchDto();
            branch.setBranchCode(validatedValue);
            school.setBranch(branch);
        } else if (fieldName.equals(StudentFieldNames.DIVISION_CODE)) {
            DivisionDto division = new DivisionDto();
            division.setDivisionCode(validatedValue);
            school.setDivision(division);
        } else if (fieldName.equals(StudentFieldNames.SCHOOL_NAME)) {
            school.setSchoolName(validatedValue);
        } else if (fieldName.equals(StudentFieldNames.CLASS_NAME)) {
            ClassDto classDto = new ClassDto();
            classDto.setClassName(validatedValue);
            registeredClass.setClassDto(classDto);
        } else if (fieldName.equals(StudentFieldNames.MEDIUM_NAME)) {
            MediumDto medium = new MediumDto();
            medium.setDescription(validatedValue);
            registeredClass.setMedium(medium);
        } else if (fieldName.equals(StudentFieldNames.SECTION_NAME)) {
            SectionDto section = new SectionDto();
            section.setSectionName(validatedValue);
            registeredClass.setSection(section);
        } else if (fieldName.equals(StudentFieldNames.ADMISSION_NUMBER)) {
            student.setAdmissionNumber(validatedValue);
        } else if (fieldName.equals(StudentFieldNames.DATE_OF_JOINING)) {
            student.setDateOfJoining(validatedValue);
        } else if (fieldName.equals(StudentFieldNames.FIRST_NAME)) {
            personalDetails.setFirstName(validatedValue);
        } else if (fieldName.equals(StudentFieldNames.MIDDLE_NAME)) {
            personalDetails.setMiddleName(validatedValue);
        } else if (fieldName.equals(StudentFieldNames.LAST_NAME)) {
            personalDetails.setLastName(validatedValue);
        } else if (fieldName.equals(StudentFieldNames.GENDER)) {
            personalDetails.setGender(validatedValue);
        } else if (fieldName.equals(StudentFieldNames.DATE_OF_BIRTH)) {
            personalDetails.setDateOfBirth(validatedValue);
        } else if (fieldName.equals(StudentFieldNames.RELIGION)) {
            personalDetails.setReligion(validatedValue);
        } else if (fieldName.equals(StudentFieldNames.CASTE)) {
            personalDetails.setCaste(validatedValue);
        } else if (fieldName.equals(StudentFieldNames.NATIONALITY)) {
            personalDetails.setNationality(validatedValue);
        } else if (fieldName.equals(StudentFieldNames.MOTHER_TONGUE)) {
            personalDetails.setMotherTongue(validatedValue);
        } else if (fieldName.equals(StudentFieldNames.PERMANENT_ADDRESS)) {
            personalDetails.setPermanentAddress(validatedValue);
        } else if (fieldName.equals(StudentFieldNames.CORRESPONDENCE_ADDRESS)) {
            personalDetails.setCorrespondenceAddress(validatedValue);
        } else if (fieldName.equals(StudentFieldNames.MOBILE_NUMBER)) {
            personalDetails.setMobileNumber(validatedValue);
        } else if (fieldName.equals(StudentFieldNames.IDENTIFICATION_MARKS)) {
            personalDetails.setIdentificationMarks(validatedValue);
        } else if (fieldName.equals(StudentFieldNames.BLOOD_GROUP)) {
            personalDetails.setBloodGroup(validatedValue);
        } else if (fieldName.equals(StudentFieldNames.REMARKS)) {
            student.setRemarks(validatedValue);
        } else if (fieldName.equals(StudentFieldNames.ADMISSION_STATUS)) {
            AdmissionStatus admissionStatus = new AdmissionStatus();
            admissionStatus.setDescription(validatedValue);
            student.setAdmissionStatus(admissionStatus);
        }
        // Student family information starts from here.
        else if (fieldName.equals(StudentFamilyFieldNames.AVAIL_EMAIL)) {
            familyMember.setAvailEmail(ConversionUtil.toBoolean(validatedValue));
        } else if (fieldName.equals(StudentFamilyFieldNames.AVAIL_SMS)) {
            familyMember.setAvailSMS(ConversionUtil.toBoolean(validatedValue));
        } else if (fieldName.equals(StudentFamilyFieldNames.FAMILY_MEMBER_OCCUPATION)) {
            familyMember.setOccupation(validatedValue);
        } else if (fieldName.equals(StudentFamilyFieldNames.FAMILY_MEMBER_EMAIL_ID)) {
            familyMember.setEmailId(validatedValue);
        } else if (fieldName.equals(StudentFamilyFieldNames.FAMILY_MEMBER_MOBILE_NUMBER)) {
            familyMember.setMobileNumber(validatedValue);
        } else if (fieldName.equals(StudentFamilyFieldNames.FAMILY_MEMBER_NAME)) {
            familyMember.setName(validatedValue);
        } else if (fieldName.equals(StudentFamilyFieldNames.RELATIONSHIP)) {
            Relationship relationship = new Relationship();
            relationship.setCode(validatedValue);
            familyMember.setRelationship(relationship);
        }
        registeredClass.setSchool(school);
        student.setRegisteredClassDto(registeredClass);
        student.setPersonalDetails(personalDetails);
        student.setFamilyMembers(familyMembers);
        student.setDocumentsSubmitted(documentsSubmitted);
        return student;
    }

    /**
     * Update student document.
     * 
     * @param studentDocument the student document
     * @param rule the rule
     * @param fieldValue the field value
     * @return the student document
     * @throws DataException the data exception
     */
    private StudentDocument updateStudentDocument(
            StudentDocument studentDocument, Rule rule, String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();
        String value = DataTypeValidator.validate(fieldValue, dataType, fieldName);
        if (fieldName.equals(StudentFieldNames.ADMISSION_NUMBER)) {
            StudentDto student = new StudentDto();
            student.setAdmissionNumber(value);
            studentDocument.setStudent(student);
        } else if (fieldName.equals(StudentDocumentFieldNames.DOCUMENT_NAME)) {
            DocumentDto document = new DocumentDto();
            document.setName(value);
            studentDocument.setDocument(document);
        } else if (fieldName.equals(StudentDocumentFieldNames.DOCUMENT_NUMBER)) {
            studentDocument.setDocumentNumber(value);
        } else if (fieldName.equals(StudentDocumentFieldNames.DOCUMENT_EXPIRY_DATE)) {
            studentDocument.setDocumentExpiryDate(value);
        } else if (fieldName.equals(StudentDocumentFieldNames.ISSUED_BY)) {
            studentDocument.setDocumentIssuedBy(value);
        }
        return studentDocument;
    }

    /**
     * Update student.
     *
     * @param importRules the import rules
     * @param student the student
     * @return the student dto
     * @throws DataException the data exception
     */
    public StudentDto updateStudent(List<Rule> importRules, StudentDto student)
            throws DataException {

        String fieldName = null;
        String fieldValue = null;

        for (Rule rule : importRules) {
            fieldName = rule.getFieldName();
            fieldValue = getFieldValue(student, fieldName);
            student = (StudentDto) updateStudent(student, rule, fieldValue);
        }
        return student;
    }

    /**
     * Gets the field value.
     *
     * @param student the student
     * @param fieldName the field name
     * @return the field value
     */
    private String getFieldValue(StudentDto student, String fieldName) {

        String fieldValue = null;
        PersonalDetailsDto personalDetails = student.getPersonalDetails();

        if (fieldName != null) {
            if (fieldName.equals(StudentFieldNames.CLASS_NAME)) {
                fieldValue = String.valueOf(student.getRegisteredClassDto().getClassId());
            } else if (fieldName.equals(StudentFieldNames.ADMISSION_NUMBER)) {
                fieldValue = student.getAdmissionNumber();
            } else if (fieldName.equals(StudentFieldNames.DATE_OF_JOINING)) {
                fieldValue = student.getDateOfJoining();
            } else if (fieldName.equals(StudentFieldNames.FIRST_NAME)) {
                fieldValue = personalDetails.getFirstName();
            } else if (fieldName.equals(StudentFieldNames.MIDDLE_NAME)) {
                fieldValue = personalDetails.getMiddleName();
            } else if (fieldName.equals(StudentFieldNames.LAST_NAME)) {
                fieldValue = personalDetails.getLastName();
            } else if (fieldName.equals(StudentFieldNames.GENDER)) {
                fieldValue = personalDetails.getGender();
            } else if (fieldName.equals(StudentFieldNames.DATE_OF_BIRTH)) {
                fieldValue = personalDetails.getDateOfBirth();
            } else if (fieldName.equals(StudentFieldNames.RELIGION)) {
                fieldValue = personalDetails.getReligion();
            } else if (fieldName.equals(StudentFieldNames.CASTE)) {
                fieldValue = personalDetails.getCaste();
            } else if (fieldName.equals(StudentFieldNames.NATIONALITY)) {
                fieldValue = personalDetails.getNationality();
            } else if (fieldName.equals(StudentFieldNames.MOTHER_TONGUE)) {
                fieldValue = personalDetails.getMotherTongue();
            } else if (fieldName.equals(StudentFieldNames.PERMANENT_ADDRESS)) {
                fieldValue = personalDetails.getPermanentAddress();
            } else if (fieldName.equals(StudentFieldNames.CORRESPONDENCE_ADDRESS)) {
                fieldValue = personalDetails.getCorrespondenceAddress();
            } else if (fieldName.equals(StudentFieldNames.MOBILE_NUMBER)) {
                fieldValue = personalDetails.getMobileNumber();
            } else if (fieldName.equals(StudentFieldNames.IDENTIFICATION_MARKS)) {
                fieldValue = personalDetails.getIdentificationMarks();
            } else if (fieldName.equals(StudentFieldNames.BLOOD_GROUP)) {
                fieldValue = personalDetails.getBloodGroup();
            } else if (fieldName.equals(StudentFieldNames.REMARKS)) {
                fieldValue = student.getRemarks();
            } else if (fieldName.equals(StudentFieldNames.ADMISSION_STATUS)) {
                fieldValue = String.valueOf(student.getAdmissionStatus().getStatusId());
            }
        }
        return fieldValue;
    }

    /**
     * Export data.
     *
     * @param students the students
     * @return the export status dto
     * @throws DataException the data exception
     */
    public ExportStatusDto exportData(List<StudentDto> students) throws DataException {
        HSSFWorkbook workbook = null;
        HSSFSheet currentSheet = null;
        FileOutputStream fileOutputStream = null;

        ExportStatusDto exportStatusDto = new ExportStatusDto();
        try {
            exportStatusDto.setStatusCode(StatusDto.FAILED);
            workbook = new HSSFWorkbook();
            currentSheet = workbook.createSheet();
            List<Rule> rules = rulesFileSystem.getRules(EximPolicy.STUDENTS);
            if (students != null && !students.isEmpty()) {
                fillSheet(students, currentSheet, rules);
            }
            File exportedFile = null; //tempFileSystem.createStudentFile("EXPORT_STUDENT_" + System.currentTimeMillis() + ".xls");
            fileOutputStream = new FileOutputStream(exportedFile);
            workbook.write(fileOutputStream);
            exportStatusDto.setStatusCode(StatusDto.SUCCESSFUL);
            exportStatusDto.setExportedFile(exportedFile);
        } catch (IOException ioException) {
            throw new DataException(ioException.getMessage(), ioException);
        } catch (RuleException ruleException) {
            throw new DataException(ruleException.getMessage(), ruleException);
        }/* catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        }*/ finally {
            try {
                ResourceUtil.releaseResource(fileOutputStream);
            } catch (FileSystemException fileSystemException) {
                throw new DataException(fileSystemException.getMessage(), fileSystemException);
            }
        }
        return exportStatusDto;
    }

    /**
     * Fill sheet.
     *
     * @param students the students
     * @param currentSheet the current sheet
     * @param rules the rules
     */
    private void fillSheet(List<StudentDto> students, HSSFSheet currentSheet, List<Rule> rules) {
        Rule rule = null;
        StudentDto student = null;
        HSSFRow currentRow = null;
        HSSFCell currentCell = null;

        // File up the header row
        currentRow = currentSheet.createRow(0);
        for (int ruleIndex=0; ruleIndex < rules.size(); ruleIndex++) {
            rule = rules.get(ruleIndex);
            currentCell = currentRow.createCell(ruleIndex);
            currentCell.setCellValue(rule.getFieldName());
        }

        for (int studentIndex=0; studentIndex<students.size(); studentIndex++) {
            student = students.get(studentIndex);
            currentRow = currentSheet.createRow(studentIndex + 1);
            fillRow(student, currentRow, rules);
        }
    }

    /**
     * Fill row.
     *
     * @param student the student
     * @param currentRow the current row
     * @param rules the rules
     */
    private void fillRow(StudentDto student, HSSFRow currentRow, List<Rule> rules) {
        Rule rule = null;
        String fieldValue = null;
        HSSFCell currentCell = null;

        for (int ruleIndex=0; ruleIndex < rules.size(); ruleIndex++) {
            rule = rules.get(ruleIndex);
            currentCell = currentRow.createCell(ruleIndex);
            fieldValue = getFieldValue(student, rule.getFieldName());
            if (fieldValue == null) {
                currentCell.setCellValue("");
            } else {
                currentCell.setCellValue(fieldValue);
            }
        }
    }

    /**
     * Gets the admission status.
     * 
     * @param admissionStatusCode the admission status code
     * @return the admission status
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    private AdmissionStatus getAdmissionStatus(String admissionStatusCode)
            throws DaoException, CacheException {
        AdmissionStatus admissionStatus = null;
        Object entry = getEntry(CacheKeyConstants.ADMISSION_STATUS, admissionStatusCode);
        if (entry == null) {
            admissionStatus = admissionStatusDao.get(admissionStatusCode);
            putEntry(CacheKeyConstants.ADMISSION_STATUS, admissionStatusCode, admissionStatus);
        } else {
            admissionStatus = (AdmissionStatus) entry;
        }
        return admissionStatus;
    }
}
