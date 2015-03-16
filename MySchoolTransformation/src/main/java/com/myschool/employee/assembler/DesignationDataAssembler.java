package com.myschool.employee.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import com.myschool.employee.dto.DesignationDto;

/**
 * The Class DesignationDataAssembler.
 */
public class DesignationDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the designation dto
     * @throws SQLException the sQL exception
     */
    public static DesignationDto create(ResultSet resultSet)
            throws SQLException {
        return create(resultSet, false);
    }

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @param aliased the aliased
     * @return the designation dto
     * @throws SQLException the sQL exception
     */
    public static DesignationDto create(ResultSet resultSet, boolean aliased)
            throws SQLException {
        DesignationDto designation = new DesignationDto();
        if (aliased) {
            designation.setDesignationId(resultSet.getInt("REF_DESIGNATION_DESIGNATION_ID"));
            designation.setDesignation(resultSet.getString("REF_DESIGNATION_DESIGNATION"));
        } else {
            designation.setDesignationId(resultSet.getInt("DESIGNATION_ID"));
            designation.setDesignation(resultSet.getString("DESIGNATION"));
        }
        return designation;
    }

    /**
     * Creates the.
     * 
     * @param currentDesignation the current designation
     * @return the jSON object
     */
    public static JSONObject create(DesignationDto currentDesignation) {
        JSONObject jsonObject = null;
        if (currentDesignation != null) {
            jsonObject = new JSONObject();
            jsonObject.put("DesignationId", currentDesignation.getDesignationId());
            jsonObject.put("Designation", currentDesignation.getDesignation());
        }
        return jsonObject;
    }

}
