package com.myschool.exim.domain;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.dto.Rule;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.util.StringUtil;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.employee.dao.EmploymentStatusDao;
import com.myschool.employee.dto.EmploymentStatus;
import com.myschool.employee.fields.EmploymentStatusFieldNames;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.ImportRecordStatusDto;
import com.myschool.infra.cache.exception.CacheException;

/**
 * The Class EmploymentStatusEximManager.
 */
@Component
public class EmploymentStatusEximManager extends AbstractEximManager {

    /** The Constant ADD_SUCCESS. */
    private static final String ADD_SUCCESS = "Employment status ({0}) has been added successfully.";

    /** The Constant ADD_FAILED. */
    private static final String ADD_FAILED = "System encountered problems while adding employment status ({0})";

    /** The Constant ALREADY_EXISTS. */
    private static final String ALREADY_EXISTS = "Employment status ({0}) has already been defined.";

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
    protected Object updateContent(EximPolicy eximPolicy, Object content,
            Rule rule, String fieldValue) throws DataException,
            ValidationException {
        if (eximPolicy == EximPolicy.EMPLOYMENT_STATUS) {
            if (content == null) {
                content = new EmploymentStatus();
            }
            content = updateEmploymentStatus((EmploymentStatus) content, rule, fieldValue);
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
            if (eximPolicy == EximPolicy.EMPLOYMENT_STATUS) {
                validateEmploymentStatus((EmploymentStatus) content);
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
    protected void processRecord(EximPolicy eximPolicy,
            ImportRecordStatusDto importRecordStatus) {
        try {
            if (eximPolicy == EximPolicy.EMPLOYMENT_STATUS) {
                handleEmploymentStatusData(importRecordStatus);
            }
        } catch (Exception exception) {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
            importRecordStatus.setStatusDescription(exception.getMessage());
        }
    }

    /**
     * Update employment status.
     * 
     * @param employmentStatus the employment status
     * @param rule the rule
     * @param fieldValue the field value
     * @return the employment status
     * @throws DataException the data exception
     */
    private EmploymentStatus updateEmploymentStatus(EmploymentStatus employmentStatus, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();

        String validatedFieldValue = DataTypeValidator.validate(fieldValue, dataType, fieldName);
        if (fieldName.equals(EmploymentStatusFieldNames.DESCRIPTION)) {
            employmentStatus.setDescription(validatedFieldValue);
        }
        return employmentStatus;
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
        return inEmploymentStatus;
    }

    /**
     * Handle employment status data.
     * 
     * @param importRecordStatus the import record status
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    private void handleEmploymentStatusData(
            ImportRecordStatusDto importRecordStatus)
            throws ValidationException, DaoException, CacheException {
        EmploymentStatus employmentStatus = (EmploymentStatus) importRecordStatus.getContent();
        String description = employmentStatus.getDescription();
        EmploymentStatus existingEmploymentStatus = employmentStatusDao.get(description);
        if (existingEmploymentStatus == null) {
            int employmentStatusId = employmentStatusDao.create(employmentStatus);
            if (employmentStatusId > 0) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                importRecordStatus.setStatusDescription(
                        MessageFormat.format(ADD_SUCCESS, description));
            } else {
                importRecordStatus.setStatusDescription(
                        MessageFormat.format(ADD_FAILED, description));
            }
        } else {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UNPROCESSED);
            importRecordStatus.setStatusDescription(MessageFormat.format(ALREADY_EXISTS, description));
        }
    }

}
