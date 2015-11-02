package com.myschool.attendance.dao;


/**
 * The Class AttendanceProfileAssignmentsDaoSql.
 */
public class AttendanceProfileAssignmentsDaoSql {

    /** The Constant SELECT_PROFILE_ASSIGNED_SCHOOLS. */
    public static final String SELECT_PROFILE_ASSIGNED_SCHOOLS;

    /** The Constant SELECT_PROFILE_ASSIGNED_CLASSES. */
    public static final String SELECT_PROFILE_ASSIGNED_CLASSES;

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
        buffer.append("BRANCH.MAP_URL AS BRANCH_MAP_URL, ");
        buffer.append("REF_REGION.REGION_ID AS REF_REGION_REGION_ID, ");
        buffer.append("REF_REGION.REGION_NAME AS REF_REGION_REGION_NAME, ");
        buffer.append("REF_STATE.STATE_ID AS REF_STATE_STATE_ID, ");
        buffer.append("REF_STATE.STATE_NAME AS REF_STATE_STATE_NAME, ");
        buffer.append("REF_DIVISION.DIVISION_ID AS REF_DIVISION_DIVISION_ID, ");
        buffer.append("REF_DIVISION.DIVISION_CODE AS REF_DIVISION_DIVISION_CODE, ");
        buffer.append("REF_DIVISION.DESCRIPTION AS REF_DIVISION_DESCRIPTION ");
        buffer.append("FROM ATTENDANCE_PROFILE_SCHOOL ");
        buffer.append("INNER JOIN SCHOOL ON SCHOOL.SCHOOL_ID=ATTENDANCE_PROFILE_SCHOOL.SCHOOL_ID ");
        buffer.append("INNER JOIN REF_DIVISION ON SCHOOL.REF_DIVISION_ID = REF_DIVISION.DIVISION_ID ");
        buffer.append("INNER JOIN BRANCH ON SCHOOL.BRANCH_ID = BRANCH.BRANCH_ID ");
        buffer.append("INNER JOIN REF_REGION ON REF_REGION.REGION_ID = BRANCH.REF_REGION_ID ");
        buffer.append("INNER JOIN REF_STATE ON REF_STATE.STATE_ID = REF_REGION.REF_STATE_ID ");
        buffer.append("WHERE ATTENDANCE_PROFILE_ID=?");
        SELECT_PROFILE_ASSIGNED_SCHOOLS = buffer.toString();
        buffer.setLength(0);

        buffer.append("SELECT "); 
        buffer.append("CLASS.CLASS_ID AS CLASS_ID, ");
        buffer.append("CLASS_NAME AS CLASS_NAME, ");
        buffer.append("PROMOTION_ORDER AS PROMOTION_ORDER, ");
        buffer.append("REF_CLASS.CLASS_ID AS REF_CLASS_CLASS_ID, ");
        buffer.append("REF_CLASS.CLASS_NAME AS REF_CLASS_CLASS_NAME, ");
        buffer.append("REF_MEDIUM.MEDIUM_ID AS REF_MEDIUM_MEDIUM_ID, ");
        buffer.append("REF_MEDIUM.DESCRIPTION AS REF_MEDIUM_DESCRIPTION, ");
        buffer.append("REF_SECTION.SECTION_ID AS REF_SECTION_SECTION_ID, ");
        buffer.append("REF_SECTION.SECTION_NAME AS REF_SECTION_SECTION_NAME, ");
        buffer.append("SCHOOL.SCHOOL_ID AS SCHOOL_SCHOOL_ID, ");
        buffer.append("SCHOOL.SCHOOL_NAME AS SCHOOL_SCHOOL_NAME, ");
        buffer.append("SCHOOL.ADDRESS AS SCHOOL_ADDRESS, ");
        buffer.append("SCHOOL.PRIMARY_PHONE_NUMBER AS SCHOOL_PRIMARY_PHONE_NUMBER, ");
        buffer.append("SCHOOL.SECONDARY_PHONE_NUMBER AS SCHOOL_SECONDARY_PHONE_NUMBER, ");
        buffer.append("SCHOOL.MOBILE_NUMBER AS SCHOOL_MOBILE_NUMBER, ");
        buffer.append("SCHOOL.FAX_NUMBER AS SCHOOL_FAX_NUMBER, ");
        buffer.append("SCHOOL.EMAIL_ID AS SCHOOL_EMAIL_ID, ");
        buffer.append("SCHOOL.MAP_URL AS SCHOOL_MAP_URL, ");
        buffer.append("BRANCH.BRANCH_ID AS BRANCH_BRANCH_ID, ");
        buffer.append("BRANCH.BRANCH_CODE AS BRANCH_BRANCH_CODE, ");
        buffer.append("BRANCH.DESCRIPTION AS BRANCH_DESCRIPTION, ");
        buffer.append("BRANCH.ADDRESS AS BRANCH_ADDRESS, ");
        buffer.append("BRANCH.PHONE_NUMBER AS BRANCH_PHONE_NUMBER, ");
        buffer.append("BRANCH.EMAIL_ID AS BRANCH_EMAIL_ID, ");
        buffer.append("BRANCH.MAP_URL AS BRANCH_MAP_URL, ");
        buffer.append("REF_REGION.REGION_ID AS REF_REGION_REGION_ID, ");
        buffer.append("REF_REGION.REGION_NAME AS REF_REGION_REGION_NAME, ");
        buffer.append("REF_STATE.STATE_ID AS REF_STATE_STATE_ID, ");
        buffer.append("REF_STATE.STATE_NAME AS REF_STATE_STATE_NAME, ");
        buffer.append("REF_DIVISION.DIVISION_ID AS REF_DIVISION_DIVISION_ID, ");
        buffer.append("REF_DIVISION.DIVISION_CODE AS REF_DIVISION_DIVISION_CODE, ");
        buffer.append("REF_DIVISION.DESCRIPTION AS REF_DIVISION_DESCRIPTION ");
        buffer.append("FROM ATTENDANCE_PROFILE_CLASS ");
        buffer.append("INNER JOIN CLASS ON CLASS.CLASS_ID=ATTENDANCE_PROFILE_CLASS.CLASS_ID ");
        buffer.append("INNER JOIN REF_CLASS ON REF_CLASS.CLASS_ID = CLASS.REF_CLASS_ID ");
        buffer.append("INNER JOIN REF_MEDIUM ON REF_MEDIUM.MEDIUM_ID = CLASS.REF_MEDIUM_ID ");
        buffer.append("INNER JOIN REF_SECTION ON REF_SECTION.SECTION_ID = CLASS.REF_SECTION_ID ");
        buffer.append("INNER JOIN SCHOOL ON CLASS.SCHOOL_ID = SCHOOL.SCHOOL_ID ");
        buffer.append("INNER JOIN REF_DIVISION ON SCHOOL.REF_DIVISION_ID = REF_DIVISION.DIVISION_ID ");
        buffer.append("INNER JOIN BRANCH ON SCHOOL.BRANCH_ID = BRANCH.BRANCH_ID ");
        buffer.append("INNER JOIN REF_REGION ON REF_REGION.REGION_ID = BRANCH.REF_REGION_ID ");
        buffer.append("INNER JOIN REF_STATE ON REF_STATE.STATE_ID = REF_REGION.REF_STATE_ID ");
        buffer.append("WHERE ATTENDANCE_PROFILE_ID=?");
        SELECT_PROFILE_ASSIGNED_CLASSES = buffer.toString();
        buffer.setLength(0);
    }

}
