package com.myschool.exim.domain;

import java.text.MessageFormat;

import org.springframework.stereotype.Component;

import com.myschool.branch.fields.DocumentFieldNames;
import com.myschool.common.constants.DocumentApplicability;
import com.myschool.common.dto.DocumentDto;
import com.myschool.common.dto.Rule;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.ImportRecordStatusDto;
import com.quasar.core.exception.DataException;

/**
 * The Class DocumentEximManager.
 */
@Component
public class DocumentEximManager extends AbstractEximManager {

    /** The Constant ADD_SUCCESS. */
    private static final String ADD_SUCCESS = "Document ({0}) has been added successfully.";

    /** The Constant ADD_FAILED. */
    private static final String ADD_FAILED = "System encountered problems while adding document ({0}).";

    /** The Constant UPDATE_SUCCESS. */
    private static final String UPDATE_SUCCESS = "Document ({0}) has been updated successfully.";

    /** The Constant UPDATE_FAILED. */
    private static final String UPDATE_FAILED = "System encountered problems while updating document ({0}).";

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
        if (eximPolicy == EximPolicy.DOCUMENTS) {
            if (content == null) {
                content = new DocumentDto();
            }
            content = updateDocument((DocumentDto) content, rule, fieldValue);
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
    protected void processRecord(EximPolicy eximPolicy,
            ImportRecordStatusDto importRecordStatus) {
        try {
            if (eximPolicy == EximPolicy.DOCUMENTS) {
                DocumentDto document = (DocumentDto) importRecordStatus.getContent();
                String documentName = document.getName();
                DocumentDto existingDocument = documentDao.get(documentName);
                if (existingDocument == null) {
                    int create = documentDao.create(document);
                    if (create>0) {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(ADD_SUCCESS, documentName));
                    } else {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(ADD_FAILED, documentName));
                    }
                } else {
                    int documentId = existingDocument.getDocumentId();
                    boolean updated = documentDao.update(documentId, document);
                    if (updated) {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UPDATED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(UPDATE_SUCCESS, documentName));
                    } else {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(UPDATE_FAILED, documentName));
                    }
                }
            }
        } catch (DaoException daoException) {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
            importRecordStatus.setStatusDescription(daoException.getMessage());
        }
    }

    /**
     * Update document.
     * 
     * @param document the document
     * @param rule the rule
     * @param fieldValue the field value
     * @return the document dto
     * @throws DataException the data exception
     */
    private DocumentDto updateDocument(DocumentDto document, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();
        if (fieldName.equals(DocumentFieldNames.DOCUMENT_NAME)) {
            document.setName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(DocumentFieldNames.DESCRIPTION)) {
            document.setDescription(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(DocumentFieldNames.APPLICABILITY_FOR_EMPLOYEE)) {
            document.setApplicabilityForEmployee(
                    DocumentApplicability.getByCode(
                            DataTypeValidator.validate(fieldValue, dataType, fieldName)));
        } else if (fieldName.equals(DocumentFieldNames.APPLICABILITY_FOR_STUDENT)) {
            document.setApplicabilityForStudent(
                    DocumentApplicability.getByCode(
                            DataTypeValidator.validate(fieldValue, dataType, fieldName)));
        }
        return document;
    }

}
