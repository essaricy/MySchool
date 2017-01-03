package com.myschool.exim.domain;

import java.text.MessageFormat;

import org.springframework.stereotype.Component;

import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.DivisionDto;
import com.myschool.common.dto.Rule;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.ImportRecordStatusDto;
import com.myschool.school.dto.SchoolDto;
import com.myschool.school.fields.SchoolFieldNames;
import com.quasar.core.exception.DataException;

/**
 * The Class SchoolEximManager.
 */
@Component
public class SchoolEximManager extends AbstractEximManager {

    /** The Constant ADD_SUCCESS. */
    private static final String ADD_SUCCESS = "School ({0}) has been added successfully.";

    /** The Constant ADD_FAILED. */
    private static final String ADD_FAILED = "System encountered problems while adding School ({0})";

    /** The Constant UPDATE_SUCCESS. */
    private static final String UPDATE_SUCCESS = "School ({0}) has been updated successfully.";

    /** The Constant ADD_FAILED. */
    private static final String UPDATE_FAILED = "System encountered problems while updating School ({0})";

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
        if (eximPolicy == EximPolicy.SCHOOLS) {
            if (content == null) {
                content = new SchoolDto();
            }
            content = updateSchool((SchoolDto) content, rule, fieldValue);
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
            if (eximPolicy == EximPolicy.SCHOOLS) {
                SchoolDto school= (SchoolDto) content;
                school.setBranch(validateBranch(school.getBranch()));
                school.setDivision(validateDivision(school.getDivision()));
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
            if (eximPolicy == EximPolicy.SCHOOLS) {
                SchoolDto school = (SchoolDto) importRecordStatus.getContent();
                String schoolName = school.getSchoolName();
                SchoolDto gotSchool = schoolDao.get(school);
                if (gotSchool == null) {
                    if (schoolDao.create(school) > 0) {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(ADD_SUCCESS, schoolName));
                    } else {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(ADD_FAILED, schoolName));
                    }
                } else {
                    if (schoolDao.update(gotSchool.getSchoolId(), school)) {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UPDATED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(UPDATE_SUCCESS, schoolName));
                    } else {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(UPDATE_FAILED, schoolName));
                    }
                }
            }
        } catch (DaoException daoException) {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
            importRecordStatus.setStatusDescription(daoException.getMessage());
        }
    }

    /**
     * Update school.
     *
     * @param school the school
     * @param rule the rule
     * @param fieldValue the field value
     * @return the school dto
     * @throws DataException the data exception
     */
    private SchoolDto updateSchool(SchoolDto school, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();
        if (fieldName.equals(SchoolFieldNames.BRANCH_CODE)) {
            BranchDto branch = new BranchDto();
            branch.setBranchCode(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            school.setBranch(branch);
        } else if (fieldName.equals(SchoolFieldNames.DIVISION_CODE)) {
            DivisionDto division = new DivisionDto();
            division.setDivisionCode(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            school.setDivision(division);
        } else if (fieldName.equals(SchoolFieldNames.SCHOOL_NAME)) {
            school.setSchoolName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(SchoolFieldNames.ADDRESS)) {
            school.setAddress(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(SchoolFieldNames.PRIMARY_PHONE_NUMBER)) {
            school.setPrimaryPhoneNumber(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(SchoolFieldNames.SECONDARY_PHONE_NUMBER)) {
            school.setSecondaryPhoneNumber(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(SchoolFieldNames.MOBILE_NUMBER)) {
            school.setMobileNumber(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(SchoolFieldNames.FAX_NUMBER)) {
            school.setFaxNumber(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(SchoolFieldNames.EMAIL_ID)) {
            school.setEmailId(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        }
        return school;
    }

}
