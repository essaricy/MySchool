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
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.ImportRecordStatusDto;
import com.myschool.infra.cache.exception.CacheException;
import com.myschool.student.dao.AdmissionStatusDao;
import com.myschool.student.dto.AdmissionStatus;
import com.myschool.student.fields.AdmissionStatusFieldNames;

/**
 * The Class AdmissionStatusEximManager.
 */
@Component
public class AdmissionStatusEximManager extends AbstractEximManager {

    /** The Constant ADD_SUCCESS. */
    private static final String ADD_SUCCESS = "Admission status ({0}) has been added successfully.";

    /** The Constant ADD_FAILED. */
    private static final String ADD_FAILED = "System encountered problems while adding Admission status ({0})";

    /** The Constant ALREADY_EXISTS. */
    private static final String ALREADY_EXISTS = "Admission status ({0}) has already been defined.";

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
    protected Object updateContent(EximPolicy eximPolicy, Object content,
            Rule rule, String fieldValue) throws DataException,
            ValidationException {
        if (eximPolicy == EximPolicy.ADMISSION_STATUS) {
            if (content == null) {
                content = new AdmissionStatus();
            }
            content = updateAdmissionStatus((AdmissionStatus) content, rule, fieldValue);
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
            if (eximPolicy == EximPolicy.ADMISSION_STATUS) {
                validateAdmissionStatus((AdmissionStatus) content);
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
            if (eximPolicy == EximPolicy.ADMISSION_STATUS) {
                handleAdmissionStatusData(importRecordStatus);
            }
        } catch (Exception exception) {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
            importRecordStatus.setStatusDescription(exception.getMessage());
        }
    }

    /**
     * 
     * @param rule the rule
     * @param fieldValue the field value
     * @throws DataException the data exception
     */
    private AdmissionStatus updateAdmissionStatus(AdmissionStatus admissionStatus, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();

        String validatedFieldValue = DataTypeValidator.validate(fieldValue, dataType, fieldName);
        if (fieldName.equals(AdmissionStatusFieldNames.DESCRIPTION)) {
            admissionStatus.setDescription(validatedFieldValue);
        }
        return admissionStatus;
    }

    /**
     * 
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
        return inAdmissionStatus;
    }

    /**
     * 
     * @param importRecordStatus the import record status
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    private void handleAdmissionStatusData(
            ImportRecordStatusDto importRecordStatus)
            throws ValidationException, DaoException, CacheException {
        AdmissionStatus admissionStatus = (AdmissionStatus) importRecordStatus.getContent();
        String description = admissionStatus.getDescription();
        AdmissionStatus existingAdmissionStatus = admissionStatusDao.get(description);
        if (existingAdmissionStatus == null) {
            int admissionStatusId = admissionStatusDao.create(admissionStatus);
            if (admissionStatusId > 0) {
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
