package com.myschool.report.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.report.constants.ReportKey;
import com.myschool.report.dto.ReportCriteria;
import com.myschool.report.dto.ReportCriteriaToken;
import com.myschool.report.dto.ReportDto;
import com.quasar.core.util.ConversionUtil;
import com.quasar.core.util.StringUtil;

/**
 * The Class ReportDataAssembler.
 */
public class ReportDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the report dto
     * @throws SQLException the sQL exception
     */
    public static ReportDto create(ResultSet resultSet) throws SQLException {
        ReportDto report = new ReportDto();
        report.setReportKey(resultSet.getString("REPORT_KEY"));
        report.setReportName(resultSet.getString("REPORT_NAME"));
        report.setCanAdminView(ConversionUtil.toBoolean(resultSet.getString("CAN_ADMIN_VIEW")));
        report.setCanEmployeeView(ConversionUtil.toBoolean(resultSet.getString("CAN_EMPLOYEE_VIEW")));
        report.setCanStudentView(ConversionUtil.toBoolean(resultSet.getString("CAN_STUDENT_VIEW")));
        return report;
    }

    /**
     * Creates the.
     * 
     * @param reportData the report data
     * @return the report criteria
     */
    public static ReportCriteria create(JSONObject reportData) {
        ReportCriteria reportCriteria = new ReportCriteria();
        if (reportData != null) {
            reportCriteria.setReportKey(ReportKey.get(reportData.getString("ReportKey")));
            reportCriteria.setReportCriteriaValues(create(reportData.getJSONArray("ReportCriteriaTokens")));
        }
        return reportCriteria;
    }

    /**
     * Creates the.
     * 
     * @param reportCriteriaTokensArray the report criteria tokens array
     * @return the map
     */
    private static Map<ReportCriteriaToken, String> create(JSONArray reportCriteriaTokensArray) {
        JSONObject reportCriteriaTokenJsonObject = null;
        Map<ReportCriteriaToken, String> reportCriteriaTokenValueMap = null;
        ReportCriteriaToken reportCriteriaToken = null;
        if (reportCriteriaTokensArray != null && reportCriteriaTokensArray.length() != 0) {
            reportCriteriaTokenValueMap = new HashMap<ReportCriteriaToken, String>();
            for (int index = 0; index < reportCriteriaTokensArray.length(); index++) {
                reportCriteriaToken = new ReportCriteriaToken();
                reportCriteriaTokenJsonObject = (JSONObject) reportCriteriaTokensArray.get(index);
                reportCriteriaToken.setCriteriaName(reportCriteriaTokenJsonObject.getString("CriteriaName"));
                //reportCriteriaToken.setControlType(reportCriteriaTokenJsonObject.getString("ControlType"));
                //reportCriteriaToken.setReference(reportCriteriaTokenJsonObject.getString("Reference"));
                String criteriaValue = reportCriteriaTokenJsonObject.getString("CriteriaValue");
                if (StringUtil.isNullOrBlank(criteriaValue)) {
                    reportCriteriaTokenValueMap.put(reportCriteriaToken, null);
                } else {
                    reportCriteriaTokenValueMap.put(reportCriteriaToken, criteriaValue);
                }
            }
        }
        return reportCriteriaTokenValueMap;
    }

    /**
     * Creates the criteria token.
     * 
     * @param resultSet the result set
     * @return the report criteria token
     * @throws SQLException the sQL exception
     */
    public static ReportCriteriaToken createCriteriaToken(ResultSet resultSet) throws SQLException {
        ReportCriteriaToken reportCriteriaToken = new ReportCriteriaToken();
        reportCriteriaToken.setCriteriaName(resultSet.getString("CRITERIA_NAME"));
        reportCriteriaToken.setControlType(resultSet.getString("CONTROL_TYPE"));
        reportCriteriaToken.setReference(resultSet.getString("REFERENCE"));
        reportCriteriaToken.setUse(resultSet.getString("USE"));
        return reportCriteriaToken;
    }

    /**
     * Gets the string.
     * 
     * @param reportCriteriaValues the report criteria values
     * @param criteriaName the criteria name
     * @return the token value
     */
    public static String getString(Map<ReportCriteriaToken, String> reportCriteriaValues,
            String criteriaName) {
        String value = null;
        if (reportCriteriaValues != null && !reportCriteriaValues.isEmpty() && !StringUtil.isNullOrBlank(criteriaName)) {
            Set<ReportCriteriaToken> keySet = reportCriteriaValues.keySet();
            for (Iterator<ReportCriteriaToken> iterator = keySet.iterator(); iterator.hasNext();) {
                ReportCriteriaToken reportCriteriaToken = iterator.next();
                if (criteriaName.equalsIgnoreCase(reportCriteriaToken.getCriteriaName())) {
                    value = reportCriteriaValues.get(reportCriteriaToken);
                    break;
                }
                
            }
        }
        return value;
    }

    /**
     * Gets the strings.
     * 
     * @param reportCriteriaValues the report criteria values
     * @param criteriaName the criteria name
     * @return the token values
     */
    public static String[] getStrings(
            Map<ReportCriteriaToken, String> reportCriteriaValues,
            String criteriaName) {
        String[] tokenValues = null;
        String tokenValue = getString(reportCriteriaValues, criteriaName);
        if (tokenValue != null && tokenValue.indexOf(",") != -1) {
            tokenValues = new String[2];
            String[] split = tokenValue.split(",");
            if (split == null || split.length == 0) {
                tokenValues[0] = null;
                tokenValues[1] = null;
            } else if (split.length == 1) {
                tokenValues[0] = split[0];
                tokenValues[1] = null;
            } else if (split.length == 2) {
                tokenValues[0] = split[0];
                tokenValues[1] = split[1];
            }
        } else {
            tokenValues = new String[2];
            tokenValues[0] = null;
            tokenValues[1] = null;
        }
        return tokenValues;
    }

    /**
     * Gets the dates.
     * 
     * @param reportCriteriaValues the report criteria values
     * @param criteriaName the criteria name
     * @return the dates
     */
    public static String[] getDates(
            Map<ReportCriteriaToken, String> reportCriteriaValues,
            String criteriaName) {
        String dateValue = null;
        String[] dateValues = getStrings(reportCriteriaValues, criteriaName);
        for (int index = 0; index < dateValues.length; index++) {
            dateValue = dateValues[index];
            if (dateValue != null) {
                dateValues[index] = ConversionUtil.toStorageDateFromApplicationDate(dateValue);
            }
        }
        return dateValues;
    }

    /**
     * Gets the int.
     * 
     * @param reportCriteriaValues the report criteria values
     * @param criteriaName the criteria name
     * @return the int
     */
    public static int getInt(
            Map<ReportCriteriaToken, String> reportCriteriaValues,
            String criteriaName) {
        int intTokenValue = 0;
        String tokenValue = getString(reportCriteriaValues, criteriaName);
        if (!StringUtil.isNullOrBlank(tokenValue)) {
            try {
                intTokenValue = Integer.parseInt(tokenValue);
            } catch (NumberFormatException e) {
            }
        }
        return intTokenValue;
    }

    /**
     * Gets the ints.
     * 
     * @param reportCriteriaValues the report criteria values
     * @param criteriaName the criteria name
     * @return the ints
     */
    public static int[] getInts(Map<ReportCriteriaToken, String> reportCriteriaValues, String criteriaName) {
        return getInts(reportCriteriaValues, criteriaName, 0);
    }

    /**
     * Gets the ints.
     * 
     * @param reportCriteriaValues the report criteria values
     * @param criteriaName the criteria name
     * @param defaultValue the default value
     * @return the ints
     */
    public static int[] getInts(
            Map<ReportCriteriaToken, String> reportCriteriaValues,
            String criteriaName, int defaultValue) {
        String tokenValueString = null;
        String[] tokenValueStrings = getStrings(reportCriteriaValues, criteriaName);
        int[] tokenValues = new int[2];
        for (int index = 0; index < tokenValueStrings.length; index++) {
            tokenValueString = tokenValueStrings[index];
            if (tokenValueString == null || tokenValueString.trim().equals("")) {
                tokenValues[index] = defaultValue;
            } else {
                tokenValues[index] = Integer.parseInt(tokenValueString.trim());
            }
        }
        return tokenValues;
    }
    
}
