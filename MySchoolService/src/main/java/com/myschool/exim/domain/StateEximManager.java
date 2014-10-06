package com.myschool.exim.domain;

import java.text.MessageFormat;

import org.springframework.stereotype.Component;

import com.myschool.branch.dto.StateDto;
import com.myschool.branch.fields.StateFieldNames;
import com.myschool.common.dto.Rule;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.ImportRecordStatusDto;

/**
 * The Class StateEximManager.
 */
@Component
public class StateEximManager extends AbstractEximManager {

    /** The Constant ADD_SUCCESS. */
    private static final String ADD_SUCCESS = "State ({0}) has been added successfully.";

    /** The Constant ADD_FAILED. */
    private static final String ADD_FAILED = "System encountered problems while adding State ({0})";

    /** The Constant ALREADY_EXISTS. */
    private static final String ALREADY_EXISTS = "State ({0}) has already been defined.";

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
        if (eximPolicy == EximPolicy.STATES) {
            if (content == null) {
                content = new StateDto();
            }
            content = updateState((StateDto) content, rule, fieldValue);
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
            if (eximPolicy == EximPolicy.STATES) {
                StateDto state = (StateDto) importRecordStatus.getContent();
                String stateName = state.getStateName();
                StateDto exisstingState = stateDao.get(stateName);
                if (exisstingState == null) {
                    if (stateDao.create(stateName) > 0) {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(ADD_SUCCESS, stateName));
                    } else {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(ADD_FAILED, stateName));
                    }
                } else {
                    importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UNPROCESSED);
                    importRecordStatus.setStatusDescription(MessageFormat.format(ALREADY_EXISTS, stateName));
                }
            }
        } catch (DaoException daoException) {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
            importRecordStatus.setStatusDescription(daoException.getMessage());
        }
    }

    /**
     * Update state.
     *
     * @param state the state
     * @param rule the rule
     * @param fieldValue the field value
     * @return the state dto
     * @throws DataException the data exception
     */
    private StateDto updateState(StateDto state, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();
        if (fieldName.equals(StateFieldNames.STATE_NAME)) {
            state.setStateName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        }
        return state;
    }

}
