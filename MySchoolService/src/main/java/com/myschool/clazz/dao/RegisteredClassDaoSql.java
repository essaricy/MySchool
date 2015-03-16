package com.myschool.clazz.dao;

import java.util.HashMap;
import java.util.Map;

import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.DivisionDto;
import com.myschool.clazz.dto.ClassDto;
import com.myschool.clazz.dto.MediumDto;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.clazz.dto.SectionDto;
import com.myschool.common.util.DatabaseUtil;
import com.myschool.school.dto.SchoolDto;


/**
 * The Class RegisteredClassDaoSql.
 */
public class RegisteredClassDaoSql {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_ID. */
    public static final String SELECT_BY_ID;

    /** The Constant SELECT_BY_SCHOOL_ID. */
    public static final String SELECT_BY_SCHOOL_ID;

    /** The Constant SELECT_BY_ADMISSION_NUMBER. */
    public static final String SELECT_BY_ADMISSION_NUMBER;

    /** The Constant INSERT. */
    public static final String INSERT;

    /** The Constant UPDATE. */
    public static final String UPDATE;

    /** The Constant DELETE. */
    public static final String DELETE;

    static {
        StringBuffer buffer = new StringBuffer();
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
        buffer.append("BRANCH.MAP_URL AS BRANCH_MAP_URL,");
        buffer.append("REF_REGION.REGION_ID AS REF_REGION_REGION_ID, ");
        buffer.append("REF_REGION.REGION_NAME AS REF_REGION_REGION_NAME, ");
        buffer.append("REF_STATE.STATE_ID AS REF_STATE_STATE_ID, ");
        buffer.append("REF_STATE.STATE_NAME AS REF_STATE_STATE_NAME, ");
        buffer.append("REF_DIVISION.DIVISION_ID AS REF_DIVISION_DIVISION_ID, ");
        buffer.append("REF_DIVISION.DIVISION_CODE AS REF_DIVISION_DIVISION_CODE, ");
        buffer.append("REF_DIVISION.DESCRIPTION AS REF_DIVISION_DESCRIPTION ");
        buffer.append("FROM CLASS ");
        buffer.append("INNER JOIN REF_CLASS ON REF_CLASS.CLASS_ID = CLASS.REF_CLASS_ID ");
        buffer.append("INNER JOIN REF_MEDIUM ON REF_MEDIUM.MEDIUM_ID = CLASS.REF_MEDIUM_ID ");
        buffer.append("INNER JOIN REF_SECTION ON REF_SECTION.SECTION_ID = CLASS.REF_SECTION_ID ");
        buffer.append("INNER JOIN SCHOOL ON CLASS.SCHOOL_ID = SCHOOL.SCHOOL_ID ");
        buffer.append("INNER JOIN REF_DIVISION ON SCHOOL.REF_DIVISION_ID = REF_DIVISION.DIVISION_ID ");
        buffer.append("INNER JOIN BRANCH ON SCHOOL.BRANCH_ID = BRANCH.BRANCH_ID ");
        buffer.append("INNER JOIN REF_REGION ON REF_REGION.REGION_ID = BRANCH.REF_REGION_ID ");
        buffer.append("INNER JOIN REF_STATE ON REF_STATE.STATE_ID = REF_REGION.REF_STATE_ID ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE CLASS.CLASS_ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE CLASS.SCHOOL_ID=? ");
        buffer.append("ORDER BY REF_CLASS.CLASS_NAME, REF_MEDIUM.DESCRIPTION, REF_SECTION.SECTION_NAME");
        SELECT_BY_SCHOOL_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE CLASS.CLASS_ID = (SELECT CLASS_ID FROM STUDENT WHERE ADMISSION_NUMBER=?)");
        SELECT_BY_ADMISSION_NUMBER = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO CLASS (");
        buffer.append("CLASS_ID, SCHOOL_ID, REF_CLASS_ID, REF_MEDIUM_ID, REF_SECTION_ID) ");
        buffer.append("VALUES (?, ?, ?, ?, ?)");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE CLASS ");
        buffer.append("SET REF_CLASS_ID=?, ");
        buffer.append("REF_MEDIUM_ID=?, ");
        buffer.append("REF_SECTION_ID=? ");
        buffer.append("WHERE CLASS_ID=? ");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM CLASS WHERE CLASS_ID=?");
        DELETE = buffer.toString();
        buffer.setLength(0);
    }

    /**
     * Gets the search registered classes query.
     * 
     * @param registeredClass the registered class
     * @return the select registered class sql
     */
    public static String getSearchRegisteredClassesQuery(
            RegisteredClassDto registeredClass) {
        SchoolDto school = registeredClass.getSchool();
        ClassDto classDto = registeredClass.getClassDto();
        MediumDto medium = registeredClass.getMedium();
        SectionDto section = registeredClass.getSection();
        BranchDto branch = school.getBranch();
        DivisionDto division = school.getDivision();

        StringBuilder builder = new StringBuilder();
        builder.append(SELECT_ALL);
        builder.append("INNER JOIN SCHOOL ON SCHOOL.SCHOOL_ID = CLASS.SCHOOL_ID ");
        builder.append("INNER JOIN BRANCH ON BRANCH.BRANCH_ID = SCHOOL.BRANCH_ID ");
        builder.append("INNER JOIN REF_DIVISION ON REF_DIVISION.DIVISION_ID = SCHOOL.REF_DIVISION_ID ");

        Map<String, String> whereClauseMap = new HashMap<String, String>();
        whereClauseMap.put("BRANCH.BRANCH_CODE='?'", branch.getBranchCode());
        whereClauseMap.put("REF_DIVISION.DIVISION_CODE='?'", division.getDivisionCode());
        whereClauseMap.put("SCHOOL.SCHOOL_NAME='?'", school.getSchoolName());
        whereClauseMap.put("REF_CLASS.CLASS_NAME='?'", classDto.getClassName());
        whereClauseMap.put("REF_MEDIUM.DESCRIPTION='?'", medium.getDescription());
        whereClauseMap.put("REF_SECTION.SECTION_NAME='?'", section.getSectionName());
        builder.append(DatabaseUtil.getWhereClause(whereClauseMap));
        return builder.toString();
    }

    /**
     * Instantiates a new registered class dao sql.
     */
    private RegisteredClassDaoSql() {}

}
