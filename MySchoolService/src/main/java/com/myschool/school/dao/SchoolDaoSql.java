package com.myschool.school.dao;

import java.util.HashMap;
import java.util.Map;

import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.DivisionDto;
import com.myschool.common.util.DatabaseUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.school.dto.SchoolDto;

/**
 * The Class SchoolDaoSql.
 */
public class SchoolDaoSql {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_ID. */
    public static final String SELECT_BY_ID;

    /** The Constant SELECT_BY_BRANCH_ID. */
    public static final String SELECT_BY_BRANCH_ID;

    /** The Constant INSERT. */
    public static final String INSERT;

    /** The Constant UPDATE. */
    public static final String UPDATE;

    /** The Constant DELETE. */
    public static final String DELETE;

    static {
        StringBuffer buffer = new StringBuffer();

        buffer.append("SELECT ");
        buffer.append("SCHOOL.SCHOOL_ID, ");
        buffer.append("SCHOOL.BRANCH_ID, ");
        buffer.append("SCHOOL.REF_DIVISION_ID, ");
        buffer.append("SCHOOL.ADDRESS, ");
        buffer.append("SCHOOL.PRIMARY_PHONE_NUMBER, ");
        buffer.append("SCHOOL.SECONDARY_PHONE_NUMBER, ");
        buffer.append("SCHOOL.MOBILE_NUMBER, ");
        buffer.append("SCHOOL.FAX_NUMBER, ");
        buffer.append("SCHOOL.EMAIL_ID, ");
        buffer.append("SCHOOL.SCHOOL_NAME, ");
        buffer.append("SCHOOL.MAP_URL, ");
        buffer.append("BRANCH.BRANCH_ID AS BRANCH_BRANCH_ID, ");
        buffer.append("BRANCH.BRANCH_CODE AS BRANCH_BRANCH_CODE, ");
        buffer.append("BRANCH.DESCRIPTION AS BRANCH_DESCRIPTION, ");
        buffer.append("BRANCH.ADDRESS AS BRANCH_ADDRESS, ");
        buffer.append("BRANCH.PHONE_NUMBER AS BRANCH_PHONE_NUMBER, ");
        buffer.append("BRANCH.EMAIL_ID AS BRANCH_EMAIL_ID, ");
        buffer.append("BRANCH.MAP_URL AS BRANCH_MAP_URL,");
        buffer.append("REF_REGION.REGION_ID AS REF_REGION_REGION_ID, ");
        buffer.append("REF_REGION.REGION_NAME AS REF_REGION_REGION_NAME, ");
        buffer.append("REF_STATE.STATE_ID AS REF_STATE_STATE_ID, ");
        buffer.append("REF_STATE.STATE_NAME AS REF_STATE_STATE_NAME, ");
        buffer.append("REF_DIVISION.DIVISION_ID AS REF_DIVISION_DIVISION_ID, ");
        buffer.append("REF_DIVISION.DIVISION_CODE AS REF_DIVISION_DIVISION_CODE, ");
        buffer.append("REF_DIVISION.DESCRIPTION AS REF_DIVISION_DESCRIPTION ");
        buffer.append("FROM SCHOOL ");
        buffer.append("INNER JOIN REF_DIVISION ON SCHOOL.REF_DIVISION_ID = REF_DIVISION.DIVISION_ID ");
        buffer.append("INNER JOIN BRANCH ON SCHOOL.BRANCH_ID = BRANCH.BRANCH_ID ");
        buffer.append("INNER JOIN REF_REGION ON REF_REGION.REGION_ID = BRANCH.REF_REGION_ID ");
        buffer.append("INNER JOIN REF_STATE ON REF_STATE.STATE_ID = REF_REGION.REF_STATE_ID ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE SCHOOL.SCHOOL_ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE SCHOOL.BRANCH_ID=?");
        SELECT_BY_BRANCH_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(" INSERT INTO SCHOOL( ");
        buffer.append(" SCHOOL_ID, ");
        buffer.append("SCHOOL_NAME, ");
        buffer.append("ADDRESS, ");
        buffer.append("PRIMARY_PHONE_NUMBER, ");
        buffer.append("SECONDARY_PHONE_NUMBER, ");
        buffer.append("MOBILE_NUMBER, ");
        buffer.append("FAX_NUMBER, ");
        buffer.append("EMAIL_ID, ");
        buffer.append("BRANCH_ID, ");
        buffer.append("REF_DIVISION_ID, MAP_URL ");
        buffer.append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL)");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE SCHOOL ");
        buffer.append("SET SCHOOL_NAME=?, ");
        buffer.append("ADDRESS=?, ");
        buffer.append("PRIMARY_PHONE_NUMBER=?, ");
        buffer.append("SECONDARY_PHONE_NUMBER=?, ");
        buffer.append("MOBILE_NUMBER=?, ");
        buffer.append("FAX_NUMBER=?, ");
        buffer.append("EMAIL_ID=?, ");
        buffer.append("BRANCH_ID=?, ");
        buffer.append("REF_DIVISION_ID=? ");
        buffer.append("WHERE SCHOOL_ID=?");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM SCHOOL WHERE SCHOOL_ID=?");
        DELETE = buffer.toString();
        buffer.setLength(0);

    }

    /**
     * Instantiates a new school dao sql.
     */
    private SchoolDaoSql() { }

    /**
     * Gets the select school sql.
     *
     * @param school the school
     * @return the select school sql
     */
    public static String getSelectSchoolSql(SchoolDto school) {
        StringBuilder builder = new StringBuilder();
        builder.append(SELECT_ALL);
        if (school != null) {
            Map<String, String> whereClauseMap = new HashMap<String, String>();
            BranchDto branch = school.getBranch();
            DivisionDto division = school.getDivision();
            if (branch != null) {
                int branchId = branch.getBranchId();
                if (branchId!=0) {
                    whereClauseMap.put("BRANCH.BRANCH_ID=?", String.valueOf(branchId));
                    
                }
                String branchCode = branch.getBranchCode();
                if (!StringUtil.isNullOrBlank(branchCode)) {
                    whereClauseMap.put("BRANCH.BRANCH_CODE='?'", branchCode);
                }
            }
            if (division != null) {
                int divisionId = division.getDivisionId();
                if (divisionId!=0) {
                    whereClauseMap.put("REF_DIVISION.DIVISION_ID=?", String.valueOf(divisionId));
                    
                }
                String divisionCode = division.getDivisionCode();
                if (!StringUtil.isNullOrBlank(divisionCode)) {
                    whereClauseMap.put("REF_DIVISION.DIVISION_CODE='?'", divisionCode);
                }
            }
            String schoolName = school.getSchoolName();
            if (!StringUtil.isNullOrBlank(schoolName)) {
                whereClauseMap.put("SCHOOL.SCHOOL_NAME='?'", schoolName);
            }
            builder.append(DatabaseUtil.getWhereClause(whereClauseMap));
        }
        return builder.toString();
    }

}
