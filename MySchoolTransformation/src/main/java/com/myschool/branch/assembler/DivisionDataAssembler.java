package com.myschool.branch.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONObject;

import com.myschool.branch.dto.DivisionDto;
import com.myschool.report.assembler.ReportDataAssembler;
import com.myschool.report.constants.ReportCriteriaTokenConstants;
import com.myschool.report.dto.ReportCriteriaToken;

/**
 * The Class DivisionDataAssembler.
 */
public class DivisionDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the division dto
     * @throws SQLException the sQL exception
     */
    public static DivisionDto create(ResultSet resultSet) throws SQLException {
        return create(resultSet, false);
    }

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @param aliased the aliased
     * @return the division dto
     * @throws SQLException the sQL exception
     */
    public static DivisionDto create(ResultSet resultSet, boolean aliased) throws SQLException {
        DivisionDto divisionDto = new DivisionDto();

        if (aliased) {
            divisionDto.setDivisionId(resultSet.getInt("REF_DIVISION_DIVISION_ID"));
            divisionDto.setDivisionCode(resultSet.getString("REF_DIVISION_DIVISION_CODE"));
            divisionDto.setDescription(resultSet.getString("REF_DIVISION_DESCRIPTION"));
        } else {
            divisionDto.setDivisionId(resultSet.getInt("DIVISION_ID"));
            divisionDto.setDivisionCode(resultSet.getString("DIVISION_CODE"));
            divisionDto.setDescription(resultSet.getString("DESCRIPTION"));
        }
        return divisionDto;
    }

    /**
     * Creates the.
     * 
     * @param division the division
     * @return the jSON object
     */
    public static JSONObject create(DivisionDto division) {
        JSONObject jsonObject = null;
        if (division != null) {
            jsonObject = new JSONObject();
            jsonObject.put("DivisionId", division.getDivisionId());
            jsonObject.put("Description", division.getDescription());
            jsonObject.put("DivisionCode", division.getDivisionCode());
        }
        return jsonObject;
    }

    /**
     * Creates the.
     * 
     * @param reportCriteriaValues the report criteria values
     * @return the division dto
     */
    public static DivisionDto create(
            Map<ReportCriteriaToken, String> reportCriteriaValues) {
        DivisionDto division = null;
        if (reportCriteriaValues != null && !reportCriteriaValues.isEmpty()) {
            division = new DivisionDto();
            division.setDivisionCode(ReportDataAssembler.getString(reportCriteriaValues, ReportCriteriaTokenConstants.DIVISION));
        }
        return division;
    }

}
