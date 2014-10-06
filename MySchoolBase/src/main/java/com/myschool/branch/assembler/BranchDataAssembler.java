package com.myschool.branch.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONObject;

import com.myschool.branch.dto.BranchDto;
import com.myschool.report.assembler.ReportDataAssembler;
import com.myschool.report.constants.ReportCriteriaTokenConstants;
import com.myschool.report.dto.ReportCriteriaToken;

/**
 * The Class BranchDataAssembler.
 */
public class BranchDataAssembler {

    /**
     * Creates the branch.
     *
     * @param branch the branch
     * @return the jSON object
     */
    public static JSONObject create(BranchDto branch) {
        JSONObject jsonObject = null;
        if (branch != null) {
            jsonObject = new JSONObject();
            jsonObject.put("BranchId", branch.getBranchId());
            jsonObject.put("Address", branch.getAddress());
            jsonObject.put("BranchCode", branch.getBranchCode());
            jsonObject.put("Description", branch.getDescription());
            jsonObject.put("EmailId", branch.getEmailId());
            jsonObject.put("MapUrl", branch.getMapUrl());
            jsonObject.put("PhoneNumber", branch.getPhoneNumber());
            jsonObject.put("Region", RegionDataAssembler.create(branch.getRegion()));
        }
        return jsonObject;
    }

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the branch dto
     * @throws SQLException the sQL exception
     */
    public static BranchDto create(ResultSet resultSet) throws SQLException {
        return create(resultSet, false);
    }

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @param aliased the aliased
     * @return the branch dto
     * @throws SQLException the sQL exception
     */
    public static BranchDto create(ResultSet resultSet, boolean aliased) throws SQLException {
        BranchDto branch = new BranchDto();
        if (aliased) {
            branch.setAddress(resultSet.getString("BRANCH_ADDRESS"));
            branch.setBranchCode(resultSet.getString("BRANCH_BRANCH_CODE"));
            branch.setBranchId(resultSet.getInt("BRANCH_BRANCH_ID"));
            branch.setDescription(resultSet.getString("BRANCH_DESCRIPTION"));
            branch.setEmailId(resultSet.getString("BRANCH_EMAIL_ID"));
        } else {
            branch.setBranchId(resultSet.getInt("BRANCH_ID"));
            branch.setBranchCode(resultSet.getString("BRANCH_CODE"));
            branch.setDescription(resultSet.getString("DESCRIPTION"));
            branch.setAddress(resultSet.getString("ADDRESS"));
            branch.setPhoneNumber(resultSet.getString("PHONE_NUMBER"));
            branch.setEmailId(resultSet.getString("EMAIL_ID"));
            branch.setMapUrl(resultSet.getString("MAP_URL"));
        }
        branch.setRegion(RegionDataAssembler.create(resultSet, true));
        return branch;
    }

    /**
     * Creates the.
     * 
     * @param reportCriteriaValues the report criteria values
     * @return the branch dto
     */
    public static BranchDto create(Map<ReportCriteriaToken, String> reportCriteriaValues) {
        BranchDto branch = null;
        if (reportCriteriaValues != null) {
            branch = new BranchDto();
            branch.setBranchId(ReportDataAssembler.getInt(reportCriteriaValues, ReportCriteriaTokenConstants.BRANCH));
            branch.setRegion(RegionDataAssembler.create(reportCriteriaValues));
        }
        return branch;
    }

}
