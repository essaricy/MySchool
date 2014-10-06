package com.myschool.branch.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONObject;

import com.myschool.branch.dto.StateDto;
import com.myschool.report.assembler.ReportDataAssembler;
import com.myschool.report.constants.ReportCriteriaTokenConstants;
import com.myschool.report.dto.ReportCriteriaToken;

/**
 * The Class StateDataAssembler.
 */
public class StateDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the state dto
     * @throws SQLException the sQL exception
     */
    public static StateDto create(ResultSet resultSet) throws SQLException {
        return create(resultSet, false);
    }

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @param aliased the aliased
     * @return the state dto
     * @throws SQLException the sQL exception
     */
    public static StateDto create(ResultSet resultSet, boolean aliased) throws SQLException {
        StateDto state = new StateDto();
        if (aliased) {
            state.setStateId(resultSet.getInt("REF_STATE_STATE_ID"));
            state.setStateName(resultSet.getString("REF_STATE_STATE_NAME"));
        } else {
            state.setStateId(resultSet.getInt("STATE_ID"));
            state.setStateName(resultSet.getString("STATE_NAME"));
        }
        return state;
    }

    /**
     * Creates the.
     * 
     * @param state the state
     * @return the object
     */
    public static JSONObject create(StateDto state) {
        JSONObject jsonObject = null;
        if (state != null) {
            jsonObject = new JSONObject();
            jsonObject.put("StateId", state.getStateId());
            jsonObject.put("StateName", state.getStateName());
        }
        return jsonObject;
    }

    /**
     * Creates the.
     * 
     * @param reportCriteriaValues the report criteria values
     * @return the state dto
     */
    public static StateDto create(Map<ReportCriteriaToken, String> reportCriteriaValues) {
        StateDto state = null;
        if (reportCriteriaValues != null) {
            state = new StateDto();
            state.setStateName(ReportDataAssembler.getString(reportCriteriaValues, ReportCriteriaTokenConstants.STATE));
        }
        return state;
    }

}
