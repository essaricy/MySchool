package com.myschool.employee.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.common.util.JsonUtil;
import com.myschool.employee.dto.EmployeeEducation;

/**
 * The Class EmployeeEducationDataAssembler.
 */
public class EmployeeEducationDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the employee education
     * @throws SQLException the sQL exception
     */
    public static EmployeeEducation create(ResultSet resultSet) throws SQLException {
        EmployeeEducation employeeEducation = new EmployeeEducation();
        employeeEducation.setCollege(resultSet.getString("COLLEGE"));
        employeeEducation.setDegree(resultSet.getString("DEGREE"));
        employeeEducation.setEducationId(resultSet.getInt("EDUCATION_ID"));
        employeeEducation.setPercentage(resultSet.getInt("PERCENTAGE"));
        employeeEducation.setSpecialization(resultSet.getString("SPECIALIZATION"));
        employeeEducation.setUniversity(resultSet.getString("UNIVERSITY"));
        employeeEducation.setYearOfGraduation(resultSet.getInt("YEAR_OF_GRADUATION"));
        return employeeEducation;
    }

    /**
     * Creates the.
     * 
     * @param employeeEducationData the employee education data
     * @return the employee education
     */
    public static EmployeeEducation create(JSONObject employeeEducationData) {
        EmployeeEducation employeeEducation = null;
        if (employeeEducationData != null) {
            employeeEducation = new EmployeeEducation();

            employeeEducation.setEducationId(JsonUtil.getInt(employeeEducationData, "EmployeeEducationId"));
            employeeEducation.setDegree(JsonUtil.getString(employeeEducationData, "Degree"));
            employeeEducation.setSpecialization(JsonUtil.getString(employeeEducationData, "Specialization"));
            employeeEducation.setCollege(JsonUtil.getString(employeeEducationData, "College"));
            employeeEducation.setUniversity(JsonUtil.getString(employeeEducationData, "University"));
            employeeEducation.setYearOfGraduation(JsonUtil.getInt(employeeEducationData, "YearOfGraduation"));
            employeeEducation.setPercentage(JsonUtil.getInt(employeeEducationData, "Percentage"));
        }
        return employeeEducation;
    }

    /**
     * Creates the.
     * 
     * @param jsonObjectArray the json object array
     * @return the employee educations
     */
    public static List<EmployeeEducation> create(JSONArray jsonObjectArray) {
        List<EmployeeEducation> employeeEducations = null;
        if (jsonObjectArray != null && jsonObjectArray.length() != 0) {
            employeeEducations = new ArrayList<EmployeeEducation>();
            for (int index = 0; index < jsonObjectArray.length(); index++) {
                JSONObject jsonObject = (JSONObject) jsonObjectArray.get(index);
                if (jsonObject != null) {
                    employeeEducations.add(create(jsonObject));
                }
            }
        }
        return employeeEducations;
    }

    /**
     * Creates the.
     * 
     * @param employeeEducations the employee educations
     * @return the jSON array
     */
    public static JSONArray create(List<EmployeeEducation> employeeEducations) {
        JSONArray jsonArray = null;
        if (employeeEducations != null && !employeeEducations.isEmpty()) {
            jsonArray = new JSONArray();
            for (EmployeeEducation employeeEducation : employeeEducations) {
                jsonArray.put(create(employeeEducation));
            }
        }
        return jsonArray;
    }

    /**
     * Creates the.
     * 
     * @param employeeEducation the employee education
     * @return the jSON array
     */
    private static JSONArray create(EmployeeEducation employeeEducation) {
        JSONArray jsonArray = null;
        if (employeeEducation != null) {
            jsonArray = new JSONArray();
            jsonArray.put(employeeEducation.getEducationId());
            jsonArray.put(employeeEducation.getDegree());
            jsonArray.put(employeeEducation.getSpecialization());
            jsonArray.put(employeeEducation.getCollege());
            jsonArray.put(employeeEducation.getUniversity());
            jsonArray.put(employeeEducation.getYearOfGraduation());
            jsonArray.put(employeeEducation.getPercentage());
        }
        return jsonArray;
    }

}
