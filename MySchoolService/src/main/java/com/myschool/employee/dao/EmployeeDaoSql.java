package com.myschool.employee.dao;

import java.util.HashMap;
import java.util.Map;

import com.myschool.common.util.StringUtil;
import com.myschool.employee.dto.EmployeeSearchCriteriaDto;
import com.myschool.infra.database.util.DatabaseUtil;

/**
 * The Class EmployeeDaoSql.
 */
public class EmployeeDaoSql {

    /** The SELECT_ALL. */
    public static String SELECT_ALL;

    /** The SELECT_BY_EMPLOYEE_NUMBER. */
    public static String SELECT_BY_EMPLOYEE_NUMBER;

    /** The SELECT_BY_EMPLOYEE_ID. */
    public static String SELECT_BY_EMPLOYEE_ID;

    /** The SELECT_LAST_EMPLOYEE. */
    public static String SELECT_LAST_EMPLOYEE;

    /** The INSERT. */
    public static String INSERT;

    /** The UPDATE. */
    public static String UPDATE;

    /** The DELETE_BY_NUMBER. */
    public static String DELETE_BY_NUMBER;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("EMPLOYEE.EMPLOYEE_ID, ");
        buffer.append("EMPLOYEE.EMPLOYEE_NUMBER, ");
        buffer.append("EMPLOYEE.FIRST_NAME, ");
        buffer.append("EMPLOYEE.MIDDLE_NAME, ");
        buffer.append("EMPLOYEE.LAST_NAME, ");
        buffer.append("EMPLOYEE.GENDER, ");
        buffer.append("EMPLOYEE.DATE_OF_BIRTH, ");
        buffer.append("EMPLOYEE.BLOOD_GROUP, ");
        buffer.append("EMPLOYEE.NATIONALITY, ");
        buffer.append("EMPLOYEE.MARITAL_STATUS, ");
        buffer.append("EMPLOYEE.WEDDING_DAY, ");
        buffer.append("EMPLOYEE.EMPLOYMENT_START_DATE, ");
        buffer.append("EMPLOYEE.EMPLOYMENT_END_DATE, ");
        buffer.append("EMPLOYEE.REPORTING_TO, ");
        buffer.append("EMPLOYEE.REMARKS, ");
        buffer.append("EMPLOYEE.VERIFIED, ");
        // Reporting branch
        buffer.append("BRANCH.ADDRESS AS BRANCH_ADDRESS, ");
        buffer.append("BRANCH.BRANCH_CODE AS BRANCH_BRANCH_CODE, ");
        buffer.append("BRANCH.BRANCH_ID AS BRANCH_BRANCH_ID, ");
        buffer.append("BRANCH.DESCRIPTION AS BRANCH_DESCRIPTION, ");
        buffer.append("BRANCH.EMAIL_ID AS BRANCH_EMAIL_ID, ");
        // Region
        buffer.append("REF_REGION.REGION_ID AS REF_REGION_REGION_ID, ");
        buffer.append("REF_REGION.REGION_NAME AS REF_REGION_REGION_NAME, ");
        // State
        buffer.append("REF_STATE.STATE_ID AS REF_STATE_STATE_ID, ");
        buffer.append("REF_STATE.STATE_NAME AS REF_STATE_STATE_NAME, ");
        // Designation
        buffer.append("REF_DESIGNATION.DESIGNATION_ID AS REF_DESIGNATION_DESIGNATION_ID, ");
        buffer.append("REF_DESIGNATION.DESIGNATION AS REF_DESIGNATION_DESIGNATION, ");
        // Employment status
        buffer.append("EMPLOYMENT_STATUS.STATUS_ID AS EMPLOYMENT_STATUS_STATUS_ID, ");
        buffer.append("EMPLOYMENT_STATUS.DESCRIPTION AS EMPLOYMENT_STATUS_DESCRIPTION ");

        buffer.append("FROM EMPLOYEE ");
        buffer.append("INNER JOIN BRANCH ON BRANCH.BRANCH_ID = EMPLOYEE.EMPLOYED_AT ");
        buffer.append("INNER JOIN REF_REGION ON REF_REGION.REGION_ID = BRANCH.REF_REGION_ID ");
        buffer.append("INNER JOIN REF_STATE ON REF_STATE.STATE_ID = REF_REGION.REF_STATE_ID ");
        buffer.append("INNER JOIN REF_DESIGNATION ON REF_DESIGNATION.DESIGNATION_ID = EMPLOYEE.DESIGNATION_ID ");
        buffer.append("INNER JOIN EMPLOYMENT_STATUS ON EMPLOYMENT_STATUS.STATUS_ID = EMPLOYEE.EMPLOYMENT_STATUS_ID ");

        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE EMPLOYEE.EMPLOYEE_NUMBER=?");
        SELECT_BY_EMPLOYEE_NUMBER = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE EMPLOYEE.EMPLOYEE_ID=?");
        SELECT_BY_EMPLOYEE_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO EMPLOYEE ( ");
        buffer.append("EMPLOYEE_ID, ");
        buffer.append("EMPLOYEE_NUMBER, "); 
        buffer.append("FIRST_NAME, ");
        buffer.append("MIDDLE_NAME, "); 
        buffer.append("LAST_NAME, ");
        buffer.append("GENDER, ");
        buffer.append("DATE_OF_BIRTH, ");
        buffer.append("BLOOD_GROUP, ");
        buffer.append("NATIONALITY, ");
        buffer.append("MARITAL_STATUS, ");
        buffer.append("WEDDING_DAY, ");
        buffer.append("EMPLOYED_AT, ");
        buffer.append("DESIGNATION_ID, ");
        buffer.append("EMPLOYMENT_STATUS_ID, ");
        buffer.append("EMPLOYMENT_START_DATE, ");
        buffer.append("EMPLOYMENT_END_DATE, ");
        buffer.append("REPORTING_TO, ");
        buffer.append("REMARKS,");
        buffer.append("VERIFIED");
        buffer.append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE EMPLOYEE ");
        buffer.append("SET FIRST_NAME=?, ");
        buffer.append("MIDDLE_NAME=?, ");
        buffer.append("LAST_NAME=?, ");
        buffer.append("GENDER=?, ");
        buffer.append("DATE_OF_BIRTH=?, ");
        buffer.append("BLOOD_GROUP=?, ");
        buffer.append("NATIONALITY=?, ");
        buffer.append("MARITAL_STATUS=?, ");
        buffer.append("WEDDING_DAY=?, ");
        buffer.append("EMPLOYED_AT=?, ");
        buffer.append("DESIGNATION_ID=?, ");
        buffer.append("EMPLOYMENT_STATUS_ID=?, ");
        buffer.append("EMPLOYMENT_START_DATE=?, ");
        buffer.append("EMPLOYMENT_END_DATE=?, ");
        buffer.append("REPORTING_TO=?, ");
        buffer.append("REMARKS=?, ");
        buffer.append("VERIFIED=?");
        buffer.append("WHERE EMPLOYEE_ID=?");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("SELECT EMPLOYEE_NUMBER ");
        buffer.append("FROM EMPLOYEE ");
        buffer.append("WHERE EMPLOYEE_ID = (SELECT MAX(EMPLOYEE_ID) FROM EMPLOYEE)");
        SELECT_LAST_EMPLOYEE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM EMPLOYEE WHERE EMPLOYEE_NUMBER=?");
        DELETE_BY_NUMBER = buffer.toString();
        buffer.setLength(0);

    }


    /**
     * Gets the employee search query.
     *
     * @param employeeSearchCriteria the employee search criteria dto
     * @return the employee search query
     */
    public static String getEmployeeSearchQuery(
            EmployeeSearchCriteriaDto employeeSearchCriteria) {
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append(SELECT_ALL);

        if (employeeSearchCriteria != null) {
            boolean filterByExperience = false;
            int experienceInMonthsMin = employeeSearchCriteria.getExperienceInMonthsMin();
            int experienceInMonthsMax = employeeSearchCriteria.getExperienceInMonthsMax();
            filterByExperience = (experienceInMonthsMin >= 0 && experienceInMonthsMax >= 0);
            // Join EMPLOYEE_EXPERIENCE if needs to be filtered by experience
            if (filterByExperience) {
                queryBuffer.append("LEFT OUTER JOIN ( ");
                queryBuffer.append("SELECT EMPLOYEE_ID, SUM(EXPERIENCE_IN_MONTHS) AS TOTAL_EXPERIENCE_IN_MONTHS ");
                queryBuffer.append("FROM EMPLOYEE_EXPERIENCE ");
                queryBuffer.append("GROUP BY EMPLOYEE_ID ");
                queryBuffer.append(") AS EMPLOYEE_EXPERIENCE_TEMP ");
                queryBuffer.append("ON EMPLOYEE_EXPERIENCE_TEMP.EMPLOYEE_ID = EMPLOYEE.EMPLOYEE_ID ");
            }

            // Actual where clause starts here.
            Map<String, String> whereClauseMap = new HashMap<String, String>();
            whereClauseMap.put("EMPLOYEE.VERIFIED='?'", employeeSearchCriteria.getVerifiedStatus());
            whereClauseMap.put("EMPLOYEE.EMPLOYEE_NUMBER LIKE '%?%'", employeeSearchCriteria.getEmployeeNumber());
            whereClauseMap.put("(EMPLOYEE.FIRST_NAME LIKE '%?%' OR EMPLOYEE.MIDDLE_NAME LIKE '%?%' OR EMPLOYEE.LAST_NAME LIKE '%?%')", employeeSearchCriteria.getEmployeeName());
            int designationId = employeeSearchCriteria.getDesignationId();
            if (designationId != 0) {
                whereClauseMap.put("EMPLOYEE.DESIGNATION_ID=?", String.valueOf(designationId));
            }
            int employmentStatusId = employeeSearchCriteria.getEmploymentStatusId();
            if (employmentStatusId != 0) {
                whereClauseMap.put("EMPLOYEE.EMPLOYMENT_STATUS_ID=?", String.valueOf(employmentStatusId));
            }
            whereClauseMap.put("EMPLOYEE.GENDER='?'", employeeSearchCriteria.getGender());
            whereClauseMap.put("EMPLOYEE.BLOOD_GROUP='?'", employeeSearchCriteria.getBloodGroup());

            String reportingToEmployeeNumber = employeeSearchCriteria.getReportingToEmployeeNumber();
            if (!StringUtil.isNullOrBlank(reportingToEmployeeNumber)) {
                whereClauseMap.put("EMPLOYEE.REPORTING_TO IN (SELECT EMPLOYEE_ID FROM EMPLOYEE WHERE EMPLOYEE_NUMBER='?')", reportingToEmployeeNumber);
            }
            String employedAtBranchCode = employeeSearchCriteria.getEmployedAtBranchCode();
            if (!StringUtil.isNullOrBlank(employedAtBranchCode)) {
                whereClauseMap.put("EMPLOYEE.EMPLOYED_AT IN (SELECT BRANCH_ID FROM BRANCH WHERE BRANCH_CODE='?')", employedAtBranchCode);
            }

            // Filter by experience
            if (filterByExperience) {
                whereClauseMap.put("EMPLOYEE_EXPERIENCE_TEMP.TOTAL_EXPERIENCE_IN_MONTHS IS NOT NULL", "");
                whereClauseMap.put("EMPLOYEE_EXPERIENCE_TEMP.TOTAL_EXPERIENCE_IN_MONTHS>=?", String.valueOf(experienceInMonthsMin));
                whereClauseMap.put("EMPLOYEE_EXPERIENCE_TEMP.TOTAL_EXPERIENCE_IN_MONTHS<=?", String.valueOf(experienceInMonthsMax));
            }

            // Filter by date of birth
            String dateOfBirthMin = employeeSearchCriteria.getDateOfBirthMin();
            String dateOfBirthMax = employeeSearchCriteria.getDateOfBirthMax();
            boolean filterByDateOfBirth = (!StringUtil.isNullOrBlank(dateOfBirthMin)
                    && !StringUtil.isNullOrBlank(dateOfBirthMax));
            if (filterByDateOfBirth) {
                whereClauseMap.put("DATE_OF_BIRTH>='?'", String.valueOf(dateOfBirthMin));
                whereClauseMap.put("DATE_OF_BIRTH<='?'", String.valueOf(dateOfBirthMax));
            }

            // Filter by date of employment.
            String employmentStartDateMin = employeeSearchCriteria.getEmploymentStartDateMin();
            String employmentStartDateMax = employeeSearchCriteria.getEmploymentStartDateMax();
            boolean filterByEmploymentStartDate = (!StringUtil.isNullOrBlank(employmentStartDateMin)
                    && !StringUtil.isNullOrBlank(employmentStartDateMax));
            if (filterByEmploymentStartDate) {
                whereClauseMap.put("EMPLOYMENT_START_DATE>='?'", String.valueOf(employmentStartDateMin));
                whereClauseMap.put("EMPLOYMENT_START_DATE<='?'", String.valueOf(employmentStartDateMax));
            }
            queryBuffer.append(DatabaseUtil.getWhereClause(whereClauseMap));
        }
        queryBuffer.append(" ORDER BY EMPLOYEE.EMPLOYEE_ID ");
        return queryBuffer.toString();
    }

}
