package com.myschool.exim.domain;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.dto.Rule;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.employee.dao.DesignationDao;
import com.myschool.employee.dto.DesignationDto;
import com.myschool.employee.fields.DesignationFieldNames;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.ImportRecordStatusDto;
import com.quasar.core.exception.DataException;

/**
 * The Class DesignationEximManager.
 */
@Component
public class DesignationEximManager extends AbstractEximManager {

    /** The Constant ADD_SUCCESS. */
    private static final String ADD_SUCCESS = "Designation ({0}) has been added successfully.";

    /** The Constant UPDATE_SUCCESS. */
    private static final String UPDATE_SUCCESS = "Designation ({0}) has been updated successfully.";

    /** The Constant ADD_FAILED. */
    private static final String ADD_FAILED = "System encountered problems while adding Designation ({0}).";

    /** The Constant UPDATE_FAILED. */
    private static final String UPDATE_FAILED = "System encountered problems while updating Designation ({0}).";

    /** The designation dao. */
    @Autowired
    private DesignationDao designationDao;

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
        if (eximPolicy == EximPolicy.DESIGNATIONS) {
            if (content == null) {
                content = new DesignationDto();
            }
            content = updateDesignation((DesignationDto) content, rule, fieldValue);
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
        try {
            if (eximPolicy == EximPolicy.DESIGNATIONS) {
                DesignationDto designation = (DesignationDto) importRecordStatus.getContent();
                int designationId = designation.getDesignationId();
                if (designationDao.get(designationId) == null) {
                    if (designationDao.create(designation)) {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(ADD_SUCCESS, designationId));
                    } else {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(ADD_FAILED, designationId));
                    }
                } else {
                    boolean updated = designationDao.update(designationId, designation);
                    // update designation
                    if (updated) {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UPDATED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(UPDATE_SUCCESS, designationId));
                    } else {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(UPDATE_FAILED, designationId));
                    }
                }
            }
        } catch (DaoException daoException) {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
            importRecordStatus.setStatusDescription(daoException.getMessage());
        }
    }

    /**
     * Update designation.
     *
     * @param designation the designation
     * @param rule the rule
     * @param fieldValue the field value
     * @return the designation dto
     * @throws DataException the data exception
     */
    private DesignationDto updateDesignation(DesignationDto designation, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();
        if (fieldName.equals(DesignationFieldNames.DESIGNATION_ID)) {
            designation.setDesignationId(Integer.parseInt(DataTypeValidator.validate(fieldValue, dataType, fieldName)));
        } else if (fieldName.equals(DesignationFieldNames.DESIGNATION_NAME)) {
            designation.setDesignation(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        }
        return designation;
    }

}
