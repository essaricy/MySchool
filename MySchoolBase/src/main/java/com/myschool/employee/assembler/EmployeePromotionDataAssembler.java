package com.myschool.employee.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.common.util.ConversionUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.employee.dto.DesignationDto;
import com.myschool.employee.dto.EmployeePromotion;

/**
 * The Class EmployeePromotionDataAssembler.
 */
public class EmployeePromotionDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the employee promotion
     * @throws SQLException the sQL exception
     */
    public static EmployeePromotion create(ResultSet resultSet) throws SQLException {
        EmployeePromotion employeePromotion = new EmployeePromotion();

        DesignationDto priorDesignation = new DesignationDto();
        priorDesignation.setDesignationId(resultSet.getInt("PRIOR_DESIGNATION_DESIGNATION_ID"));
        priorDesignation.setDesignation(resultSet.getString("PRIOR_DESIGNATION_DESIGNATION"));
        
        DesignationDto currentDesignation = new DesignationDto();
        currentDesignation.setDesignationId(resultSet.getInt("CURRENT_DESIGNATION_DESIGNATION_ID"));
        currentDesignation.setDesignation(resultSet.getString("CURRENT_DESIGNATION_DESIGNATION"));

        employeePromotion.setPromotionId(resultSet.getInt("PROMOTION_ID"));
        employeePromotion.setPriorDesignation(priorDesignation);
        employeePromotion.setCurrentDesignation(currentDesignation);
        employeePromotion.setEffectiveFrom(ConversionUtil.toApplicationDateFromStorageDate(
                resultSet.getString("EFFECTIVE_FROM")));
        return employeePromotion;
    }

    /**
     * Creates the.
     * 
     * @param employeePromotionData the employee promotion data
     * @return the employee promotion
     */
    public static EmployeePromotion create(JSONObject employeePromotionData) {
        EmployeePromotion employeePromotion = null;
        if (employeePromotionData != null) {
            employeePromotion = new EmployeePromotion();

            String employeePromotionId = employeePromotionData.getString("EmployeePromotionId");
            if (!StringUtil.isNullOrBlank(employeePromotionId)) {
                employeePromotion.setPromotionId(Integer.parseInt(employeePromotionId));
            }
            DesignationDto priorDesignation = new DesignationDto();
            priorDesignation.setDesignationId(Integer.parseInt(employeePromotionData.getString("PriorDesignationId")));
            employeePromotion.setPriorDesignation(priorDesignation);
            DesignationDto currentDesignation = new DesignationDto();
            currentDesignation.setDesignationId(Integer.parseInt(employeePromotionData.getString("CurrentDesignationId")));
            employeePromotion.setCurrentDesignation(currentDesignation);
            employeePromotion.setEffectiveFrom(employeePromotionData.getString("EffectiveFrom"));
        }
        return employeePromotion;
    }

    /**
     * Creates the.
     * 
     * @param jsonObjectArray the json object array
     * @return the employee promotions
     */
    public static List<EmployeePromotion> create(JSONArray jsonObjectArray) {
        List<EmployeePromotion> employeePromotions = null;
        if (jsonObjectArray != null && jsonObjectArray.length() != 0) {
            employeePromotions = new ArrayList<EmployeePromotion>();
            for (int index = 0; index < jsonObjectArray.length(); index++) {
                JSONObject jsonObject = (JSONObject) jsonObjectArray.get(index);
                if (jsonObject != null) {
                    employeePromotions.add(create(jsonObject));
                }
            }
        }
        return employeePromotions;
    }

    /**
     * Creates the.
     * 
     * @param employeePromotions the employee promotions
     * @return the jSON array
     */
    public static JSONArray create(List<EmployeePromotion> employeePromotions) {
        JSONArray jsonArray = null;
        if (employeePromotions != null && !employeePromotions.isEmpty()) {
            jsonArray = new JSONArray();
            for (EmployeePromotion employeePromotion : employeePromotions) {
                jsonArray.put(create(employeePromotion));
            }
        }
        return jsonArray;
    }

    /**
     * Creates the.
     * 
     * @param employeePromotion the employee promotion
     * @return the jSON array
     */
    private static JSONArray create(EmployeePromotion employeePromotion) {
        JSONArray jsonArray = null;
        if (employeePromotion != null) {
            jsonArray = new JSONArray();
            DesignationDto priorDesignation = employeePromotion.getPriorDesignation();
            DesignationDto currentDesignation = employeePromotion.getCurrentDesignation();
            jsonArray.put(employeePromotion.getPromotionId());
            jsonArray.put(priorDesignation.getDesignationId());
            jsonArray.put(priorDesignation.getDesignation());
            jsonArray.put(currentDesignation.getDesignationId());
            jsonArray.put(currentDesignation.getDesignation());
            jsonArray.put(employeePromotion.getEffectiveFrom());

            /*jsonObject = new JSONObject();
            jsonObject.put("PromotionId", employeePromotion.getPromotionId());
            jsonObject.put("CurrentDesignation", DesignationDataAssembler.create(employeePromotion.getCurrentDesignation()));
            jsonObject.put("PriorDesignation", DesignationDataAssembler.create(employeePromotion.getPriorDesignation()));
            jsonObject.put("EffectiveFrom", employeePromotion.getEffectiveFrom());*/
        }
        return jsonArray;
    }

}
