package com.myschool.exim.domain;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.stereotype.Component;

import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.DivisionDto;
import com.myschool.clazz.dto.ClassDto;
import com.myschool.clazz.dto.MediumDto;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.clazz.dto.SectionDto;
import com.myschool.clazz.fields.ClassFieldNames;
import com.myschool.clazz.fields.ClassesInSchoolFieldNames;
import com.myschool.common.dto.Rule;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.ImportRecordStatusDto;
import com.myschool.school.dto.SchoolDto;

/**
 * The Class ClassEximManager.
 */
@Component
public class ClassEximManager extends AbstractEximManager {

    /** The Constant ADD_SUCCESS. */
    private static final String ADD_SUCCESS = "Class ({0}) has been added successfully.";
    
    /** The Constant ADD_FAILED. */
    private static final String ADD_FAILED = "System encountered a problem while adding Class ({0})";
    
    /** The Constant UPDATE_SUCCESS. */
    private static final String UPDATE_SUCCESS = "Class ({0}) has been updated successfully.";
    
    /** The Constant UPDATE_FAILED. */
    private static final String UPDATE_FAILED = "System encountered a problem while updating Class ({0})";
    
    /** The Constant ALREADY_DEFINED. */
    private static final String ALREADY_DEFINED = "A Class ({0}) is already defined with the same promotion order ({1}).";

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
        if (eximPolicy == EximPolicy.MASTER_CLASSES) {
            if (content == null) {
                content = new ClassDto();
            }
            content = updateClass((ClassDto) content, rule, fieldValue);
        } else if (eximPolicy == EximPolicy.REGISTERED_CLASSES) {
            if (content == null) {
                content = new RegisteredClassDto();
            }
            content = updateRegisteredClass((RegisteredClassDto) content, rule, fieldValue);
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
            if (eximPolicy == EximPolicy.MASTER_CLASSES) {
                ClassDto classDto = (ClassDto) content;
                int promotionOrder = classDto.getPromotionOrder();
                List<ClassDto> allClasses = classDao.getAll();
                if (allClasses != null) {
                    for (ClassDto existingClassDto : allClasses) {
                        if (existingClassDto != null) {
                            if (promotionOrder == existingClassDto.getPromotionOrder()) {
                                throw new ValidationException(MessageFormat.format(
                                        ALREADY_DEFINED, existingClassDto.getClassName(), promotionOrder));
                            }
                        }
                    }
                }
            } else if (eximPolicy == EximPolicy.REGISTERED_CLASSES) {
                RegisteredClassDto registeredClass = (RegisteredClassDto) content;
                registeredClass.setClassDto(validateClass(registeredClass.getClassDto()));
                registeredClass.setMedium(validateMedium(registeredClass.getMedium()));
                registeredClass.setSection(validateSection(registeredClass.getSection()));
                registeredClass.setSchool(validateSchool(registeredClass.getSchool()));
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
            if (eximPolicy == EximPolicy.MASTER_CLASSES) {
                handleClassData(importRecordStatus);
            } else if (eximPolicy == EximPolicy.REGISTERED_CLASSES) {
                handleRegisteredClassesData(importRecordStatus);
            }
        } catch (DaoException daoException) {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
            importRecordStatus.setStatusDescription(daoException.getMessage());
        }
    }

    /**
     * Handle class data.
     *
     * @param importRecordStatus the import record status
     * @throws DaoException the dao exception
     */
    private void handleClassData(ImportRecordStatusDto importRecordStatus)
            throws DaoException {
        String className;
        ClassDto classDto = (ClassDto) importRecordStatus.getContent();
        className = classDto.getClassName();
        ClassDto existingClass = classDao.get(className);
        if (existingClass == null) {
            if (classDao.create(classDto) > 0) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                importRecordStatus.setStatusDescription(MessageFormat.format(ADD_SUCCESS, className));
            } else {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                importRecordStatus.setStatusDescription(MessageFormat.format(ADD_FAILED, className));
            }
        } else {
            int classId = existingClass.getClassId();
            if (classDao.update(classId, classDto)) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UPDATED);
                importRecordStatus.setStatusDescription(MessageFormat.format(UPDATE_SUCCESS, className));
            } else {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                importRecordStatus.setStatusDescription(MessageFormat.format(UPDATE_FAILED, className));
            }
        }
    }

    /**
     *
     * @param importRecordStatus the import record status
     * @throws DaoException the dao exception
     */
    private void handleRegisteredClassesData(
            ImportRecordStatusDto importRecordStatus) throws DaoException {
        String className;
        RegisteredClassDto registeredClass = (RegisteredClassDto) importRecordStatus.getContent();
        // If class does not exist then add class to this school
        className = registeredClass.getClassDto().getClassName();
        RegisteredClassDto gotRegisteredClass = registeredClassDao.get(registeredClass);
        if (gotRegisteredClass == null) {
            if (registeredClassDao.createBySchool(registeredClass.getSchool().getSchoolId(), registeredClass)) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                importRecordStatus.setStatusDescription("Class (" + className + ") added successfully.");
            } else {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                importRecordStatus.setStatusDescription("System encountered problems while adding class (" + className + ").");
            }
        } else {
            if (registeredClassDao.update(gotRegisteredClass.getClassId(), registeredClass)) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UPDATED);
                importRecordStatus.setStatusDescription("Class (" + className + ") updated successfully.");
            } else {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                importRecordStatus.setStatusDescription("System encountered problems while updating class (" + className + ").");
            }
        }
    }

    /**
     * Update class.
     *
     * @param classDto the class dto
     * @param rule the rule
     * @param fieldValue the field value
     * @return the class dto
     * @throws DataException the data exception
     */
    private ClassDto updateClass(ClassDto classDto, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();
        if (fieldName.equals(ClassFieldNames.CLASS_NAME)) {
            classDto.setClassName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(ClassFieldNames.PROMOTION_ORDER)) {
            classDto.setPromotionOrder(Integer.parseInt(DataTypeValidator.validate(fieldValue, dataType, fieldName)));
        }
        return classDto;
    }

    /**
     * Update registered class.
     *
     * @param registeredClass the registered class
     * @param rule the rule
     * @param fieldValue the field value
     * @return the registered class dto
     * @throws DataException the data exception
     */
    private RegisteredClassDto updateRegisteredClass(RegisteredClassDto registeredClass,
            Rule rule, String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();
        SchoolDto school = registeredClass.getSchool();
        if (school == null) {
            school = new SchoolDto();
        }
        
        if (fieldName.equals(ClassesInSchoolFieldNames.BRANCH_CODE)) {
            BranchDto branch = new BranchDto();
            branch.setBranchCode(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            school.setBranch(branch);
        } else if (fieldName.equals(ClassesInSchoolFieldNames.DIVISION_CODE)) {
            DivisionDto division = new DivisionDto();
            division.setDivisionCode(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            school.setDivision(division);
        } else if (fieldName.equals(ClassesInSchoolFieldNames.SCHOOL_NAME)) {
            school.setSchoolName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(ClassesInSchoolFieldNames.CLASS_NAME)) {
            ClassDto classDto = new ClassDto();
            classDto.setClassName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            registeredClass.setClassDto(classDto);
        } else if (fieldName.equals(ClassesInSchoolFieldNames.MEDIUM_NAME)) {
            MediumDto medium = new MediumDto();
            medium.setDescription(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            registeredClass.setMedium(medium);
        } else if (fieldName.equals(ClassesInSchoolFieldNames.SECTION_NAME)) {
            SectionDto section = new SectionDto();
            section.setSectionName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            registeredClass.setSection(section);
        }
        registeredClass.setSchool(school);
        return registeredClass;
    }

}
