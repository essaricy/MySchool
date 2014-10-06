package com.myschool.exim.domain;

import java.text.MessageFormat;

import org.springframework.stereotype.Component;

import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.DivisionDto;
import com.myschool.clazz.dto.ClassDto;
import com.myschool.clazz.dto.MediumDto;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.clazz.dto.RegisteredSubjectDto;
import com.myschool.clazz.dto.SectionDto;
import com.myschool.clazz.dto.SubjectDto;
import com.myschool.clazz.fields.SubjectFieldNames;
import com.myschool.clazz.fields.SubjectsInClassesFieldNames;
import com.myschool.common.dto.Rule;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.ImportRecordStatusDto;
import com.myschool.school.dto.SchoolDto;

/**
 * The Class SubjectEximManager.
 */
@Component
public class SubjectEximManager extends AbstractEximManager {

    /** The Constant MASTER_SUBJECT_ADD_SUCCESS. */
    private static final String MASTER_SUBJECT_ADD_SUCCESS = "Subject ({0}) has been added successfully.";

    /** The Constant MASTER_SUBJECT_ADD_FAILED. */
    private static final String MASTER_SUBJECT_ADD_FAILED = "System encountered problems while adding State ({0})";

    /** The Constant MASTER_SUBJECT_ALREADY_EXISTS. */
    private static final String MASTER_SUBJECT_ALREADY_EXISTS = "Subject ({0}) has already been defined.";

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
        if (eximPolicy == EximPolicy.MASTER_SUBJECTS) {
            if (content == null) {
                content = new SubjectDto();
            }
            content = updateSubject((SubjectDto) content, rule, fieldValue);
        } else if (eximPolicy == EximPolicy.REGISTERED_SUBJECTS) {
            if (content == null) {
                content = new RegisteredSubjectDto();
            }
            content = updateRegisteredSubject((RegisteredSubjectDto) content, rule, fieldValue);
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
            if (eximPolicy == EximPolicy.MASTER_SUBJECTS) {
            } else if (eximPolicy == EximPolicy.REGISTERED_SUBJECTS) {
                RegisteredSubjectDto registeredSubject = (RegisteredSubjectDto) content;
                registeredSubject.setRegisteredClass(
                        validateRegisteredClass(registeredSubject.getRegisteredClass()));
                registeredSubject.setSubject(validateSubject(registeredSubject.getSubject()));
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
            if (eximPolicy == EximPolicy.MASTER_SUBJECTS) {
                handleSubjectData(importRecordStatus);
            } else if (eximPolicy == EximPolicy.REGISTERED_SUBJECTS) {
                handleRegisteredSubject(importRecordStatus);
            }
        } catch (DaoException daoException) {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
            importRecordStatus.setStatusDescription(daoException.getMessage());
        }
    }

    /**
     * Handle subject data.
     *
     * @param importRecordStatusDto the import record status dto
     * @throws DaoException the dao exception
     */
    private void handleSubjectData(ImportRecordStatusDto importRecordStatusDto)
            throws DaoException {
        String subjectName;
        SubjectDto subject;
        subject = (SubjectDto) importRecordStatusDto.getContent();
        subjectName = subject.getSubjectName();
        if (subjectDao.get(subjectName) == null) {
            if (subjectDao.create(subjectName) > 0) {
                importRecordStatusDto.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                importRecordStatusDto.setStatusDescription(MessageFormat.format(MASTER_SUBJECT_ADD_SUCCESS, subjectName));
            } else {
                importRecordStatusDto.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                importRecordStatusDto.setStatusDescription(MessageFormat.format(MASTER_SUBJECT_ADD_FAILED, subjectName));
            }
        } else {
            importRecordStatusDto.setStatusCode(ImportRecordStatusDto.STATUS_UNPROCESSED);
            importRecordStatusDto.setStatusDescription(MessageFormat.format(MASTER_SUBJECT_ALREADY_EXISTS, subjectName));
        }
    }

    /**
     * Handle registered subject.
     * 
     * @param importRecordStatus the import record status
     * @throws DaoException the dao exception
     */
    private void handleRegisteredSubject(
            ImportRecordStatusDto importRecordStatus) throws DaoException {
        String subjectName;
        RegisteredSubjectDto registeredSubject = (RegisteredSubjectDto) importRecordStatus.getContent();
        RegisteredClassDto registeredClass = registeredSubject.getRegisteredClass();
        SubjectDto subject = registeredSubject.getSubject();
        subjectName = subject.getSubjectName();
        // If subject does not exist then add this subject to the class
        RegisteredSubjectDto gotRegisteredSubject = registeredSubjectDao.get(registeredClass.getClassId(), subject.getSubjectId());
        if (gotRegisteredSubject == null) {
            if (registeredSubjectDao.create(registeredSubject)) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                importRecordStatus.setStatusDescription("Subject (" + subjectName + ") assigned successfully.");
            } else {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                importRecordStatus.setStatusDescription("System encountered problems while assigning subject (" + subjectName + ").");
            }
        } else {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UNPROCESSED);
            importRecordStatus.setStatusDescription("Subject (" + subjectName + ") has already been assigned.");
        }
    }

    /**
     * Update subject.
     *
     * @param subject the subject
     * @param rule the rule
     * @param fieldValue the field value
     * @return the subject dto
     * @throws DataException the data exception
     */
    private SubjectDto updateSubject(SubjectDto subject, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();
        if (fieldName.equals(SubjectFieldNames.SUBJECT_NAME)) {
            subject.setSubjectName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        }
        return subject;
    }

    /**
     * Update registered subject.
     * 
     * @param registeredSubject the registered subject
     * @param rule the rule
     * @param fieldValue the field value
     * @return the object
     * @throws DataException the data exception
     */
    private Object updateRegisteredSubject(RegisteredSubjectDto registeredSubject, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();
        
        RegisteredClassDto registeredClass = registeredSubject.getRegisteredClass();
        if (registeredClass == null) {
            registeredClass = new RegisteredClassDto();
        }

        SchoolDto school = registeredClass.getSchool();
        if (school == null) {
            school = new SchoolDto();
        }

        if (fieldName.equals(SubjectsInClassesFieldNames.BRANCH_CODE)) {
            BranchDto branch = new BranchDto();
            branch.setBranchCode(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            school.setBranch(branch);
        } else if (fieldName.equals(SubjectsInClassesFieldNames.DIVISION_CODE)) {
            DivisionDto division = new DivisionDto();
            division.setDivisionCode(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            school.setDivision(division);
        } else if (fieldName.equals(SubjectsInClassesFieldNames.SCHOOL_NAME)) {
            school.setSchoolName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(SubjectsInClassesFieldNames.CLASS_NAME)) {
            ClassDto classDto = new ClassDto();
            classDto.setClassName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            registeredClass.setClassDto(classDto);
        } else if (fieldName.equals(SubjectsInClassesFieldNames.MEDIUM_NAME)) {
            MediumDto medium = new MediumDto();
            medium.setDescription(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            registeredClass.setMedium(medium);
        } else if (fieldName.equals(SubjectsInClassesFieldNames.SECTION_NAME)) {
            SectionDto section = new SectionDto();
            section.setSectionName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            registeredClass.setSection(section);
        } else if (fieldName.equals(SubjectsInClassesFieldNames.SUBJECT_NAME)) {
            SubjectDto subject = new SubjectDto();
            subject.setSubjectName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            registeredSubject.setSubject(subject);
        }
        registeredClass.setSchool(school);
        registeredSubject.setRegisteredClass(registeredClass);
        return registeredSubject;
    }

}

