package com.myschool.exim.domain;

import java.text.MessageFormat;

import org.springframework.stereotype.Component;

import com.myschool.branch.dto.RegionDto;
import com.myschool.branch.dto.StateDto;
import com.myschool.branch.fields.RegionFieldNames;
import com.myschool.common.dto.Rule;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.ImportRecordStatusDto;

/**
 * The Class RegionEximManager.
 */
@Component
public class RegionEximManager extends AbstractEximManager {

    /** The Constant ADD_SUCCESS. */
    private static final String ADD_SUCCESS = "Region ({0}) has been added successfully.";

    /** The Constant ADD_FAILED. */
    private static final String ADD_FAILED = "System encountered problems while adding Region ({0})";

    /** The Constant ALREADY_EXISTS. */
    private static final String ALREADY_EXISTS = "Region ({0}) has already been defined.";

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
        if (eximPolicy == EximPolicy.REGIONS) {
            if (content == null) {
                content = new RegionDto();
            }
            content = updateRegion((RegionDto) content, rule, fieldValue);
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
            if (eximPolicy == EximPolicy.REGIONS) {
                RegionDto region = (RegionDto) content;
                String stateName = region.getState().getStateName();
                StateDto state = stateDao.get(stateName);
                if (state == null || state.getStateId() == 0) {
                    throw new ValidationException("State (" + stateName + ") must be present to add a Region.");
                }
                region.setState(state);
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
            if (eximPolicy == EximPolicy.REGIONS) {
                RegionDto region = (RegionDto) importRecordStatus.getContent();
                String regionName = region.getRegionName();
                int stateId = region.getState().getStateId();
                if (regionDao.get(regionName, stateId) == null) {
                    if (regionDao.create(regionName, stateId) > 0) {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(ADD_SUCCESS, regionName));
                    } else {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(ADD_FAILED, regionName));
                    }
                } else {
                    importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UNPROCESSED);
                    importRecordStatus.setStatusDescription(MessageFormat.format(ALREADY_EXISTS, regionName));
                }
            }
        } catch (DaoException daoException) {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
            importRecordStatus.setStatusDescription(daoException.getMessage());
        }
    }

    /**
     * Update region.
     *
     * @param region the region
     * @param rule the rule
     * @param fieldValue the field value
     * @return the region dto
     * @throws DataException the data exception
     */
    private RegionDto updateRegion(RegionDto region, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();
        if (fieldName.equals(RegionFieldNames.REGION_NAME)) {
            region.setRegionName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(RegionFieldNames.STATE_NAME)) {
            StateDto state = new StateDto();
            state.setStateName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            region.setState(state);
        }
        return region;
    }

}
