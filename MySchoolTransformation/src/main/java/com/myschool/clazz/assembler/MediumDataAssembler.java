package com.myschool.clazz.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import com.myschool.clazz.dto.MediumDto;

/**
 * The Class MediumDataAssembler.
 */
public class MediumDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the medium dto
     * @throws SQLException the sQL exception
     */
    public static MediumDto create(ResultSet resultSet) throws SQLException {
        return create(resultSet, false);
    }

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @param aliased the include table name
     * @return the medium dto
     * @throws SQLException the sQL exception
     */
    public static MediumDto create(ResultSet resultSet, boolean aliased) throws SQLException {
        MediumDto medium = new MediumDto();
        if (aliased) {
            medium.setMediumId(resultSet.getInt("REF_MEDIUM_MEDIUM_ID"));
            medium.setDescription(resultSet.getString("REF_MEDIUM_DESCRIPTION"));
        } else {
            medium.setMediumId(resultSet.getInt("MEDIUM_ID"));
            medium.setDescription(resultSet.getString("DESCRIPTION"));
        }
        return medium;
    }

    /**
     * Creates the.
     * 
     * @param medium the medium
     * @return the jSON object
     */
    public static JSONObject create(MediumDto medium) {
        JSONObject jsonObject = null;
        if (medium != null) {
            jsonObject = new JSONObject();
            jsonObject.put("MediumId", medium.getMediumId());
            jsonObject.put("Description", medium.getDescription());
        }
        return jsonObject;
    }

}
