package com.myschool.exim.domain;

import java.text.MessageFormat;

import org.springframework.stereotype.Component;

import com.myschool.branch.dto.DivisionDto;
import com.myschool.branch.fields.DivisionFieldNames;
import com.myschool.common.dto.Rule;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.ImportRecordStatusDto;
import com.quasar.core.exception.DataException;

/**
 * The Class DivisionEximManager.
 */
@Component
public class DivisionEximManager extends AbstractEximManager {

    /** The Constant ADD_SUCCESS. */
    private static final String ADD_SUCCESS = "Division ({0}) has been added successfully.";

    /** The Constant ADD_FAILED. */
    private static final String ADD_FAILED = "System encountered problems while adding Division ({0})";

    /** The Constant UPDATE_SUCCESS. */
    private static final String UPDATE_SUCCESS = "Division ({0}) has been updated successfully.";

    /** The Constant UPDATE_FAILED. */
    private static final String UPDATE_FAILED = "System encountered problems while updating Division ({0})";

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
        if (eximPolicy == EximPolicy.DIVISIONS) {
            if (content == null) {
                content = new DivisionDto();
            }
            content = updateDivision((DivisionDto) content, rule, fieldValue);
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
        DivisionDto division = null;
        try {
            if (eximPolicy == EximPolicy.DIVISIONS) {
                division = (DivisionDto) importRecordStatus.getContent();
                String divisionCode = division.getDivisionCode();
                DivisionDto existingDivision = divisionDao.get(divisionCode);
                if (existingDivision == null) {
                    if (divisionDao.create(division) > 0) {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(ADD_SUCCESS, divisionCode));
                    } else {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(ADD_FAILED, divisionCode));
                    }
                } else {
                    int divisionId = existingDivision.getDivisionId();
                    if (divisionDao.update(divisionId, division)) {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UPDATED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(UPDATE_SUCCESS, divisionCode));
                    } else {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(UPDATE_FAILED, divisionCode));
                    }
                }
            }
        } catch (DaoException daoException) {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
            importRecordStatus.setStatusDescription(daoException.getMessage());
        }
    }

    /**
     * Update division.
     *
     * @param division the division
     * @param rule the rule
     * @param fieldValue the field value
     * @return the division dto
     * @throws DataException the data exception
     */
    private DivisionDto updateDivision(DivisionDto division, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();
        if (fieldName.equals(DivisionFieldNames.DIVISION_CODE)) {
            division.setDivisionCode(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(DivisionFieldNames.DESCRIPTION)) {
            division.setDescription(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        }
        return division;
    }

}
