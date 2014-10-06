package com.myschool.branch.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONObject;

import com.myschool.branch.dto.RegionDto;
import com.myschool.report.assembler.ReportDataAssembler;
import com.myschool.report.constants.ReportCriteriaTokenConstants;
import com.myschool.report.dto.ReportCriteriaToken;

/**
 * The Class RegionDataAssembler.
 */
public class RegionDataAssembler {

    /**
     * Creates the.
     * 
     * @param region the region
     * @return the jSON object
     */
    public static JSONObject create(RegionDto region) {
        JSONObject jsonObject = null;
        if (region != null) {
            jsonObject = new JSONObject();
            jsonObject.put("RegionId", region.getRegionId());
            jsonObject.put("RegionName", region.getRegionName());
            jsonObject.put("State", StateDataAssembler.create(region.getState()));
        }
        return jsonObject;
    }

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the region dto
     * @throws SQLException the sQL exception
     */
    public static RegionDto create(ResultSet resultSet) throws SQLException {
        return create(resultSet, false);
    }

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @param aliased the aliased
     * @return the region dto
     * @throws SQLException the sQL exception
     */
    public static RegionDto create(ResultSet resultSet, boolean aliased) throws SQLException {
        RegionDto region = new RegionDto();
        if (aliased) {
            region.setRegionId(resultSet.getInt("REF_REGION_REGION_ID"));
            region.setRegionName(resultSet.getString("REF_REGION_REGION_NAME"));
        } else {
            region.setRegionId(resultSet.getInt("REGION_ID"));
            region.setRegionName(resultSet.getString("REGION_NAME"));
        }
        region.setState(StateDataAssembler.create(resultSet, true));
        return region;
    }

    /**
     * Creates the.
     * 
     * @param reportCriteriaValues the report criteria values
     * @return the region dto
     */
    public static RegionDto create(Map<ReportCriteriaToken, String> reportCriteriaValues) {
        RegionDto region = null;
        if (reportCriteriaValues != null) {
            region = new RegionDto();
            region.setRegionName(ReportDataAssembler.getString(reportCriteriaValues, ReportCriteriaTokenConstants.REGION));
            region.setState(StateDataAssembler.create(reportCriteriaValues));
        }
        return region;
    }

}
