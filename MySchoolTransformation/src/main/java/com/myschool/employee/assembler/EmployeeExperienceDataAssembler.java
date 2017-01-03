package com.myschool.employee.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.common.util.JsonUtil;
import com.myschool.employee.dto.EmployeeExperience;
import com.quasar.core.util.ConversionUtil;

/**
 * The Class EmployeeExperienceDataAssembler.
 */
public class EmployeeExperienceDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the employee experience
     * @throws SQLException the sQL exception
     */
    public static EmployeeExperience create(ResultSet resultSet) throws SQLException {
        EmployeeExperience employeeExperience = new EmployeeExperience();
        employeeExperience.setEmployer(resultSet.getString("EMPLOYER"));
        employeeExperience.setExperieceInMonth(resultSet.getInt("EXPERIENCE_IN_MONTHS"));
        employeeExperience.setExperienceId(resultSet.getInt("EXPERIENCE_ID"));
        employeeExperience.setJobTitle(resultSet.getString("JOB_TITLE"));
        employeeExperience.setFromDate(ConversionUtil.toApplicationDateFromStorageDate(
                resultSet.getString("FROM_DATE")));
        employeeExperience.setToDate(ConversionUtil.toApplicationDateFromStorageDate(
                resultSet.getString("TO_DATE")));
        return employeeExperience;
    }

    /**
     * Creates the.
     * 
     * @param employeeExperienceData the employee experience data
     * @return the employee experience
     */
    public static EmployeeExperience create(JSONObject employeeExperienceData) {
        EmployeeExperience employeeExperience = null;
        if (employeeExperienceData != null) {
            employeeExperience = new EmployeeExperience();

            employeeExperience.setExperienceId(JsonUtil.getInt(employeeExperienceData, "EmployeeExperienceId"));
            employeeExperience.setEmployer(JsonUtil.getString(employeeExperienceData, "Employer"));
            employeeExperience.setJobTitle(JsonUtil.getString(employeeExperienceData, "JobTitle"));
            //employeeExperience.setExperieceInMonth(JsonUtil.getString(employeeExperienceData, "experieceInMonth"));
            employeeExperience.setFromDate(JsonUtil.getString(employeeExperienceData, "FromDate"));
            employeeExperience.setToDate(JsonUtil.getString(employeeExperienceData, "ToDate"));
        }
        return employeeExperience;
    }

    /**
     * Creates the.
     * 
     * @param jsonObjectArray the json object array
     * @return the employee experiences
     */
    public static List<EmployeeExperience> create(JSONArray jsonObjectArray) {
        List<EmployeeExperience> employeeExperiences = null;
        if (jsonObjectArray != null && jsonObjectArray.length() != 0) {
            employeeExperiences = new ArrayList<EmployeeExperience>();
            for (int index = 0; index < jsonObjectArray.length(); index++) {
                JSONObject jsonObject = (JSONObject) jsonObjectArray.get(index);
                if (jsonObject != null) {
                    employeeExperiences.add(create(jsonObject));
                }
            }
        }
        return employeeExperiences;
    }

    /**
     * Creates the.
     * 
     * @param employeeExperiences the employee experiences
     * @return the jSON array
     */
    public static JSONArray create(List<EmployeeExperience> employeeExperiences) {
        JSONArray jsonArray = null;
        if (employeeExperiences != null && !employeeExperiences.isEmpty()) {
            jsonArray = new JSONArray();
            for (EmployeeExperience employeeExperience : employeeExperiences) {
                jsonArray.put(create(employeeExperience));
            }
        }
        return jsonArray;
    }

    /**
     * Creates the.
     * 
     * @param employeeExperience the employee experience
     * @return the jSON array
     */
    private static JSONArray create(EmployeeExperience employeeExperience) {
        JSONArray jsonArray = null;
        if (employeeExperience != null) {
            jsonArray = new JSONArray();
            jsonArray.put(employeeExperience.getExperienceId());
            jsonArray.put(employeeExperience.getEmployer());
            jsonArray.put(employeeExperience.getJobTitle());
            jsonArray.put(employeeExperience.getFromDate());
            jsonArray.put(employeeExperience.getToDate());
            jsonArray.put(employeeExperience.getExperieceInMonth());

            /*jsonObject = new JSONObject();
            jsonObject.put("ExperienceId", employeeExperience.getExperienceId());
            jsonObject.put("ExperieceInMonth", employeeExperience.getExperieceInMonth());
            jsonObject.put("Employer", employeeExperience.getEmployer());
            jsonObject.put("FromDate", employeeExperience.getFromDate());
            jsonObject.put("JobTitle", employeeExperience.getJobTitle());
            jsonObject.put("ToDate", employeeExperience.getToDate());*/
        }
        return jsonArray;
    }

}
