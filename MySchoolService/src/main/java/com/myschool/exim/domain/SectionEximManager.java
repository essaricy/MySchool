package com.myschool.exim.domain;

import java.text.MessageFormat;

import org.springframework.stereotype.Component;

import com.myschool.clazz.dto.SectionDto;
import com.myschool.clazz.fields.SectionFieldNames;
import com.myschool.common.dto.Rule;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.ImportRecordStatusDto;

/**
 * The Class SectionEximManager.
 */
@Component
public class SectionEximManager extends AbstractEximManager {

    /** The Constant ADD_SUCCESS. */
    private static final String ADD_SUCCESS = "Section ({0}) added successfully.";

    /** The Constant ADD_FAILED. */
    private static final String ADD_FAILED = "System encountered problems while adding Section ({0}).";

    /** The Constant ALREADY_EXISTS. */
    private static final String ALREADY_EXISTS = "Section ({0}) has already been defined in the system.";

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
        if (eximPolicy == EximPolicy.MASTER_SECTIONS) {
            if (content == null) {
                content = new SectionDto();
            }
            content = updateSection((SectionDto) content, rule, fieldValue);
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
            if (eximPolicy == EximPolicy.MASTER_SECTIONS) {
                SectionDto section = (SectionDto) importRecordStatus.getContent();
                String sectionName = section.getSectionName();
                if (sectionDao.get(sectionName) == null) {
                    if (sectionDao.create(sectionName) > 0) {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(ADD_SUCCESS, sectionName));
                    } else {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(ADD_FAILED, sectionName));
                    }
                } else {
                    importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UNPROCESSED);
                    importRecordStatus.setStatusDescription(MessageFormat.format(ALREADY_EXISTS, sectionName));
                }
            }
        } catch (DaoException daoException) {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
            importRecordStatus.setStatusDescription(daoException.getMessage());
        }
    }

    /**
     * Update section.
     *
     * @param section the section
     * @param rule the rule
     * @param fieldValue the field value
     * @return the section dto
     * @throws DataException the data exception
     */
    private SectionDto updateSection(SectionDto section, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();
        if (fieldName.equals(SectionFieldNames.SECTION_NAME)) {
            section.setSectionName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        }
        return section;
    }

}
