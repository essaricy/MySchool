package com.myschool.academic.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import com.myschool.academic.dto.AcademicDto;
import com.myschool.common.util.JsonUtil;
import com.quasar.core.util.ConversionUtil;

/**
 * The Class AcademicDataAssembler.
 */
public class AcademicDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the academic dto
     * @throws SQLException the sQL exception
     */
    public static AcademicDto create(ResultSet resultSet) throws SQLException {
        return create(resultSet, false);
    }

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @param aliased the aliased
     * @return the academic dto
     * @throws SQLException the sQL exception
     */
    public static AcademicDto create(ResultSet resultSet, boolean aliased) throws SQLException {
        AcademicDto academic = new AcademicDto();
        if (aliased) {
            academic.setAcademicYearName(resultSet.getString("ACADEMICS_ACADEMIC_YEAR_NAME"));
            academic.setAcademicYearStartDate(
                    ConversionUtil.toApplicationDateFromStorageDate(
                            resultSet.getString("ACADEMICS_AY_START_DATE")));
            academic.setAcademicYearEndDate(
                    ConversionUtil.toApplicationDateFromStorageDate(
                            resultSet.getString("ACADEMICS_AY_END_DATE")));
        } else {
            academic.setAcademicYearName(resultSet.getString("ACADEMIC_YEAR_NAME"));
            academic.setAcademicYearStartDate(
                    ConversionUtil.toApplicationDateFromStorageDate(resultSet.getString("AY_START_DATE")));
            academic.setAcademicYearEndDate(
                    ConversionUtil.toApplicationDateFromStorageDate(resultSet.getString("AY_END_DATE")));
        }
        return academic;
    }

    /**
     * Creates the.
     * 
     * @param academic the academic
     * @return the jSON object
     */
    public static JSONObject create(AcademicDto academic) {
        JSONObject jsonObject = null;
        if (academic != null) {
            jsonObject = new JSONObject();
            jsonObject.put("AcademicYearName", academic.getAcademicYearName());
            jsonObject.put("AcademicYearStartDate", academic.getAcademicYearStartDate());
            jsonObject.put("AcademicYearEndDate", academic.getAcademicYearEndDate());
        }
        return jsonObject;
    }

    /**
     * Creates the.
     * 
     * @param jsonObject the json object
     * @return the academic dto
     */
    public static AcademicDto create(JSONObject jsonObject) {
        AcademicDto academic = null;
        if (jsonObject != null) {
            academic = new AcademicDto();
            academic.setAcademicYearName(JsonUtil.getString(jsonObject, "AcademicYearName"));
            academic.setAcademicYearStartDate(JsonUtil.getString(jsonObject, "AcademicYearStartDate"));
            academic.setAcademicYearEndDate(JsonUtil.getString(jsonObject, "AcademicYearEndDate"));
        }
        return academic;
    }

}
