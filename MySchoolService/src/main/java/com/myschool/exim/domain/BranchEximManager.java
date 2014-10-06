package com.myschool.exim.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.branch.domain.BranchManager;
import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.RegionDto;
import com.myschool.branch.fields.BranchFieldNames;
import com.myschool.common.dto.Rule;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.ImportRecordStatusDto;

/**
 * The Class BranchEximManager.
 */
@Component
public class BranchEximManager extends AbstractEximManager {

    @Autowired
    private BranchManager branchManager;

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
        if (eximPolicy == EximPolicy.BRANCHES) {
            if (content == null) {
                content = new BranchDto();
            }
            content = updateBranch((BranchDto) content, rule, fieldValue);
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
        String regionName = null;
        ImportRecordStatusDto importRecordStatus = new ImportRecordStatusDto();

        try {
            if (eximPolicy == EximPolicy.BRANCHES) {
                BranchDto branch = (BranchDto) content;
                regionName = branch.getRegion().getRegionName();
                RegionDto region = regionDao.get(regionName);
                if (region == null || region.getRegionId() == 0) {
                    importRecordStatus.setActionCode(ImportRecordStatusDto.ACTION_CODE_SKIP);
                    importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_INVALID_DATA);
                    importRecordStatus.setStatusDescription("Region (" + regionName + ") must be present to add a Branch.");
                }
                branch.setRegion(region);
                importRecordStatus.setContent(branch);
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
            BranchDto branch = (BranchDto) importRecordStatus.getContent();
            String branchCode = branch.getBranchCode();
            if (branchDao.get(branchCode) == null) {
                if (branchManager.create(branch)) {
                    importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                    importRecordStatus.setStatusDescription("Branch (" + branchCode + ") added successfully.");
                } else {
                    importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                    importRecordStatus.setStatusDescription("System encountered problems while creating Branch (" + branchCode + ").");
                }
            } else {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UNPROCESSED);
                importRecordStatus.setStatusDescription("Branch (" + branchCode + ") has already been defined in the system.");
            }
        } catch (Exception exception) {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
            importRecordStatus.setStatusDescription(exception.getMessage());
        }
    }

    /**
     * Update branch.
     *
     * @param branch the branch
     * @param rule the rule
     * @param fieldValue the field value
     * @return the branch dto
     * @throws DataException the data exception
     */
    private BranchDto updateBranch(BranchDto branch, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();
        if (fieldName.equals(BranchFieldNames.BRANCH_CODE)) {
            branch.setBranchCode(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(BranchFieldNames.DESCRIPTION)) {
            branch.setDescription(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(BranchFieldNames.ADDRESS)) {
            branch.setAddress(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(BranchFieldNames.REGION)) {
            RegionDto region = new RegionDto();
            region.setRegionName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            branch.setRegion(region);
        } else if (fieldName.equals(BranchFieldNames.PHONE_NUMBER)) {
            branch.setPhoneNumber(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(BranchFieldNames.EMAIL_ID)) {
            branch.setEmailId(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        }
        return branch;
    }

}
