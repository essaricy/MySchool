package com.myschool.student.dao;

import java.util.HashMap;
import java.util.Map;

import com.myschool.common.constants.RecordStatus;
import com.myschool.common.util.DatabaseUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.student.dto.StudentSearchCriteriaDto;

/**
 * The Class StudentDaoSql.
 */
public class StudentDaoSql {

    /** The SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_ID. */
    public static final String SELECT_BY_ID;

    /** The SELET_BY_ADMISSION_NUMBER. */
    public static final String SELET_BY_ADMISSION_NUMBER;

    /** The Constant INSERT. */
    public static final String INSERT;

    /** The Constant UPDATE. */
    public static final String UPDATE;

    /** The Constant DELETE. */
    public static final String DELETE;

    /** The Constant TERMINATE_STUDENT. */
    public static final String TERMINATE_STUDENT;

    /** The SELECT_ALL_ADMISSION_NUMBERS. */
    private static String SELECT_ALL_ADMISSION_NUMBERS;

    /** The Constant SELECT_LAST_ADMISSION_NUMBER. */
    public static final String SELECT_LAST_ADMISSION_NUMBER;

    /** The SELECT_NEXT_UNVERIFIED_ADMISSION_NUMBER. */
    public static String SELECT_NEXT_UNVERIFIED_ADMISSION_NUMBER;

    /** The SELECT_NEXT_VERIFIED_ADMISSION_NUMBER. */
    public static String SELECT_NEXT_VERIFIED_ADMISSION_NUMBER;

    /** The SELECT_PREVIOUS_UNVERIFIED_ADMISSION_NUMBER. */
    public static String SELECT_PREVIOUS_UNVERIFIED_ADMISSION_NUMBER;

    /** The SELECT_PREVIOUS_VERIFIED_ADMISSION_NUMBER. */
    public static String SELECT_PREVIOUS_VERIFIED_ADMISSION_NUMBER;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("STUDENT.STUDENT_ID, ");
        buffer.append("STUDENT.ADMISSION_NUMBER, ");
        buffer.append("STUDENT.DATE_OF_JOINING AS DATE_OF_JOINING, ");
        buffer.append("STUDENT.FIRST_NAME, ");
        buffer.append("STUDENT.MIDDLE_NAME, ");
        buffer.append("STUDENT.LAST_NAME, ");
        buffer.append("STUDENT.GENDER, ");
        buffer.append("STUDENT.DATE_OF_BIRTH AS DATE_OF_BIRTH, ");
        buffer.append("STUDENT.RELIGION AS RELIGION, ");
        buffer.append("STUDENT.CASTE AS CASTE, ");
        buffer.append("STUDENT.NATIONALITY AS NATIONALITY, ");
        buffer.append("STUDENT.MOTHER_TONGUE AS MOTHER_TONGUE, ");
        buffer.append("STUDENT.PERMANENT_ADDRESS AS PERMANENT_ADDRESS, ");
        buffer.append("STUDENT.CORRESPONDENCE_ADDRESS AS CORRESPONDENCE_ADDRESS, ");
        buffer.append("STUDENT.MOBILE_NUMBER AS MOBILE_NUMBER, ");
        buffer.append("STUDENT.IDENTIFICATION_MARKS AS IDENTIFICATION_MARKS, ");
        buffer.append("STUDENT.BLOOD_GROUP AS BLOOD_GROUP, ");
        buffer.append("STUDENT.REMARKS AS REMARKS, ");
        buffer.append("STUDENT.VERIFIED AS VERIFIED, ");

        buffer.append("CLASS.CLASS_ID AS CLASS_CLASS_ID, ");

        // admission status
        buffer.append("ADMISSION_STATUS.STATUS_ID AS ADMISSION_STATUS_STATUS_ID, ");
        buffer.append("ADMISSION_STATUS.DESCRIPTION AS ADMISSION_STATUS_DESCRIPTION, ");

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
        buffer.append("BRANCH.EMAIL_ID AS BRANCH_EMAIL_ID, ");

        buffer.append("REF_DIVISION.DIVISION_ID AS REF_DIVISION_DIVISION_ID, ");
        buffer.append("REF_DIVISION.DIVISION_CODE AS REF_DIVISION_DIVISION_CODE, ");
        buffer.append("REF_DIVISION.DESCRIPTION AS REF_DIVISION_DESCRIPTION, ");

        buffer.append("REF_REGION.REGION_ID AS REF_REGION_REGION_ID, ");
        buffer.append("REF_REGION.REGION_NAME AS REF_REGION_REGION_NAME, ");

        buffer.append("REF_STATE.STATE_ID AS REF_STATE_STATE_ID, ");
        buffer.append("REF_STATE.STATE_NAME AS REF_STATE_STATE_NAME ");

        buffer.append("FROM STUDENT ");
        buffer.append("INNER JOIN ADMISSION_STATUS ON ADMISSION_STATUS.STATUS_ID = STUDENT.ADMISSION_STATUS_ID ");
        buffer.append("INNER JOIN CLASS ON CLASS.CLASS_ID = STUDENT.CLASS_ID ");
        buffer.append("INNER JOIN REF_CLASS ON REF_CLASS.CLASS_ID = CLASS.REF_CLASS_ID ");
        buffer.append("INNER JOIN REF_MEDIUM ON REF_MEDIUM.MEDIUM_ID = CLASS.REF_MEDIUM_ID ");
        buffer.append("INNER JOIN REF_SECTION ON REF_SECTION.SECTION_ID = CLASS.REF_SECTION_ID ");
        buffer.append("INNER JOIN SCHOOL ON SCHOOL.SCHOOL_ID = CLASS.SCHOOL_ID ");
        buffer.append("INNER JOIN BRANCH ON BRANCH.BRANCH_ID = SCHOOL.BRANCH_ID ");
        buffer.append("INNER JOIN REF_DIVISION ON REF_DIVISION.DIVISION_ID = SCHOOL.REF_DIVISION_ID ");
        buffer.append("INNER JOIN REF_REGION ON BRANCH.REF_REGION_ID = REF_REGION.REGION_ID ");
        buffer.append("INNER JOIN REF_STATE ON REF_REGION.REF_STATE_ID = REF_STATE.STATE_ID ");

        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE ADMISSION_NUMBER=?");
        SELET_BY_ADMISSION_NUMBER = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE STUDENT_ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO STUDENT( ");
        buffer.append("STUDENT_ID, ADMISSION_NUMBER, CLASS_ID, ADMISSION_STATUS_ID, DATE_OF_JOINING, ");
        buffer.append("FIRST_NAME, MIDDLE_NAME, LAST_NAME, GENDER, ");
        buffer.append("DATE_OF_BIRTH, RELIGION, CASTE, NATIONALITY, MOTHER_TONGUE, ");
        buffer.append("PERMANENT_ADDRESS, CORRESPONDENCE_ADDRESS, MOBILE_NUMBER, IDENTIFICATION_MARKS, BLOOD_GROUP, ");
        buffer.append("REMARKS, VERIFIED) ");
        buffer.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE STUDENT ");
        buffer.append("SET CLASS_ID=?, ");
        buffer.append("ADMISSION_STATUS_ID=?, ");
        buffer.append("DATE_OF_JOINING=?, ");
        buffer.append("FIRST_NAME=?, ");
        buffer.append("MIDDLE_NAME=?, ");
        buffer.append("LAST_NAME=?, ");
        buffer.append("GENDER=?, ");
        buffer.append("DATE_OF_BIRTH=?, ");
        buffer.append("RELIGION=?, ");
        buffer.append("CASTE=?, ");
        buffer.append("NATIONALITY=?, ");
        buffer.append("MOTHER_TONGUE=?, ");
        buffer.append("PERMANENT_ADDRESS=?, ");
        buffer.append("CORRESPONDENCE_ADDRESS=?, ");
        buffer.append("MOBILE_NUMBER=?, ");
        buffer.append("IDENTIFICATION_MARKS=?, ");
        buffer.append("BLOOD_GROUP=?, ");
        buffer.append("REMARKS=?, ");
        buffer.append("VERIFIED=? ");
        buffer.append("WHERE STUDENT_ID=?");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM STUDENT WHERE ADMISSION_NUMBER=?");
        DELETE = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE STUDENT ");
        buffer.append("SET ADMISSION_STATUS_ID=? ");
        buffer.append("AND LAST_ADMISSION_NUMBER= (SELECT CURRENT_AY_NAME FROM ORGANIZATION_PROFILE) ");
        buffer.append("WHERE ADMISSION_NUMBER=?");
        TERMINATE_STUDENT = buffer.toString();
        buffer.setLength(0);

        buffer.append("SELECT ");
        buffer.append("ADMISSION_NUMBER ");
        buffer.append("FROM STUDENT ");
        SELECT_ALL_ADMISSION_NUMBERS = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL_ADMISSION_NUMBERS);
        buffer.append("WHERE STUDENT_ID = (SELECT MAX(STUDENT_ID) FROM STUDENT)");
        SELECT_LAST_ADMISSION_NUMBER = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL_ADMISSION_NUMBERS);
        buffer.append("WHERE ");
        buffer.append("STUDENT_ID > (SELECT STUDENT_ID FROM STUDENT WHERE ADMISSION_NUMBER=?) ");
        buffer.append("AND VERIFIED='N' ");
        buffer.append("ORDER BY STUDENT_ID ");
        buffer.append("LIMIT 1");
        SELECT_NEXT_UNVERIFIED_ADMISSION_NUMBER = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL_ADMISSION_NUMBERS);
        buffer.append("WHERE ");
        buffer.append("STUDENT_ID > (SELECT STUDENT_ID FROM STUDENT WHERE ADMISSION_NUMBER=?) ");
        buffer.append("AND VERIFIED='Y' ");
        buffer.append("ORDER BY STUDENT_ID ");
        buffer.append("LIMIT 1");
        SELECT_NEXT_VERIFIED_ADMISSION_NUMBER = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL_ADMISSION_NUMBERS);
        buffer.append("WHERE ");
        buffer.append("STUDENT_ID < (SELECT STUDENT_ID FROM STUDENT WHERE ADMISSION_NUMBER=?) ");
        buffer.append("AND VERIFIED='N' ");
        buffer.append("ORDER BY STUDENT_ID DESC ");
        buffer.append("LIMIT 1");
        SELECT_PREVIOUS_UNVERIFIED_ADMISSION_NUMBER = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL_ADMISSION_NUMBERS);
        buffer.append("WHERE ");
        buffer.append("STUDENT_ID < (SELECT STUDENT_ID FROM STUDENT WHERE ADMISSION_NUMBER=?) ");
        buffer.append("AND VERIFIED='Y' ");
        buffer.append("ORDER BY STUDENT_ID DESC ");
        buffer.append("LIMIT 1");
        SELECT_PREVIOUS_VERIFIED_ADMISSION_NUMBER = buffer.toString();
        buffer.setLength(0);
    }

    /**
     * Gets the students.
     *
     * @param classId the class id
     * @return the students
     */
    public static String getCurrentAyStudentsSql(int classId) {
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append("SELECT ");
        queryBuffer.append("STUDENT.STUDENT_ID, ");
        queryBuffer.append("STUDENT.CLASS_ID, ");
        queryBuffer.append("STUDENT.ADMISSION_NUMBER, ");
        queryBuffer.append("STUDENT.DATE_OF_JOINING AS DATE_OF_JOINING, ");
        queryBuffer.append("STUDENT.FIRST_NAME, ");
        queryBuffer.append("STUDENT.MIDDLE_NAME, ");
        queryBuffer.append("STUDENT.LAST_NAME, ");
        queryBuffer.append("STUDENT.GENDER, ");
        queryBuffer.append("STUDENT.DATE_OF_BIRTH AS DATE_OF_BIRTH, ");
        queryBuffer.append("STUDENT.RELIGION AS RELIGION, ");
        queryBuffer.append("STUDENT.CASTE AS CASTE, ");
        queryBuffer.append("STUDENT.NATIONALITY AS NATIONALITY, ");
        queryBuffer.append("STUDENT.MOTHER_TONGUE AS MOTHER_TONGUE, ");
        queryBuffer.append("STUDENT.PERMANENT_ADDRESS AS PERMANENT_ADDRESS, ");
        queryBuffer.append("STUDENT.CORRESPONDENCE_ADDRESS AS CORRESPONDENCE_ADDRESS, ");
        queryBuffer.append("STUDENT.MOBILE_NUMBER AS MOBILE_NUMBER, ");
        queryBuffer.append("STUDENT.IDENTIFICATION_MARKS AS IDENTIFICATION_MARKS, ");
        queryBuffer.append("STUDENT.BLOOD_GROUP AS BLOOD_GROUP, ");
        queryBuffer.append("STUDENT.REMARKS AS REMARKS, ");
        // admission status
        queryBuffer.append("ADMISSION_STATUS.STATUS_ID AS ADMISSION_STATUS_STATUS_ID, ");
        queryBuffer.append("ADMISSION_STATUS.DESCRIPTION AS ADMISSION_STATUS_DESCRIPTION, ");

        queryBuffer.append("CLASS.CLASS_ID AS CLASS_CLASS_ID, ");
        queryBuffer.append("BRANCH.BRANCH_ID AS BRANCH_BRANCH_ID, ");
        queryBuffer.append("BRANCH.BRANCH_CODE AS BRANCH_BRANCH_CODE, ");
        queryBuffer.append("BRANCH.DESCRIPTION AS BRANCH_DESCRIPTION, ");
        queryBuffer.append("BRANCH.ADDRESS AS BRANCH_ADDRESS, ");
        queryBuffer.append("BRANCH.EMAIL_ID AS BRANCH_EMAIL_ID, ");

        queryBuffer.append("REF_CLASS.CLASS_ID AS REF_CLASS_CLASS_ID, ");
        queryBuffer.append("REF_CLASS.CLASS_NAME AS REF_CLASS_CLASS_NAME, ");

        queryBuffer.append("REF_DIVISION.DIVISION_ID AS REF_DIVISION_DIVISION_ID, ");
        queryBuffer.append("REF_DIVISION.DIVISION_CODE AS REF_DIVISION_DIVISION_CODE, ");
        queryBuffer.append("REF_DIVISION.DESCRIPTION AS REF_DIVISION_DESCRIPTION, ");

        queryBuffer.append("REF_MEDIUM.MEDIUM_ID AS REF_MEDIUM_MEDIUM_ID, ");
        queryBuffer.append("REF_MEDIUM.DESCRIPTION AS REF_MEDIUM_DESCRIPTION, ");

        queryBuffer.append("REF_SECTION.SECTION_ID AS REF_SECTION_SECTION_ID, ");
        queryBuffer.append("REF_SECTION.SECTION_NAME AS REF_SECTION_SECTION_NAME ");

        queryBuffer.append("FROM STUDENT ");
        queryBuffer.append("INNER JOIN ADMISSION_STATUS ");
        queryBuffer.append("ON ADMISSION_STATUS.STATUS_ID = STUDENT.ADMISSION_STATUS_ID ");
        queryBuffer.append("INNER JOIN CLASS ");
        queryBuffer.append("ON CLASS.CLASS_ID = STUDENT.CLASS_ID ");
        queryBuffer.append("INNER JOIN SCHOOL ");
        queryBuffer.append("ON SCHOOL.SCHOOL_ID = CLASS.SCHOOL_ID ");
        queryBuffer.append("INNER JOIN BRANCH ");
        queryBuffer.append("ON BRANCH.BRANCH_ID = SCHOOL.BRANCH_ID ");
        queryBuffer.append("INNER JOIN REF_DIVISION ");
        queryBuffer.append("ON REF_DIVISION.DIVISION_ID = SCHOOL.REF_DIVISION_ID ");
        queryBuffer.append("INNER JOIN REF_CLASS ");
        queryBuffer.append("ON REF_CLASS.CLASS_ID = CLASS.REF_CLASS_ID ");
        queryBuffer.append("INNER JOIN REF_MEDIUM ");
        queryBuffer.append("ON REF_MEDIUM.MEDIUM_ID = CLASS.REF_MEDIUM_ID ");
        queryBuffer.append("INNER JOIN REF_SECTION ");
        queryBuffer.append("ON REF_SECTION.SECTION_ID = CLASS.REF_SECTION_ID ");

        // TODO get them based on student date of joining.
        //queryBuffer.append("WHERE ACADEMIC_YEAR = (SELECT CURRENT_AY_NAME FROM ORGANIZATION_PROFILE) ");
        queryBuffer.append("AND STUDENT.CLASS_ID  = ").append(classId);
        return queryBuffer.toString();
    }

    /**
     * Gets the promote student query.
     *
     * @param studentId the student id
     * @param nextClassId the next class id
     * @param nextAcademicYear the next academic year
     * @return the promote student query
     */
    public static String getPromoteStudentQuery(int studentId, int nextClassId, String nextAcademicYear) {
        return nextAcademicYear;
        // TODO update to student academics
        /*StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append("UPDATE STUDENT ");
        queryBuffer.append("SET CLASS_ID = ").append(nextClassId).append(", ");
        queryBuffer.append(" ACADEMIC_YEAR = ").append(DatabaseUtil.getNullableStringValue(nextAcademicYear));
        queryBuffer.append(" WHERE STUDENT_ID = ").append(studentId);
        return queryBuffer.toString();*/
    }

    /**
     * Gets the student search query.
     *
     * @param studentSearchCriteria the student search criteria dto
     * @return the student search query
     */
    public static String getStudentSearchQuery(
            StudentSearchCriteriaDto studentSearchCriteria) {
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append(SELECT_ALL);
        if (studentSearchCriteria != null) {
            Map<String, String> whereClauseMap = new HashMap<String, String>();
            RecordStatus recordStatus = studentSearchCriteria.getRecordStatus();
            if (recordStatus == RecordStatus.VERIFIED) {
                whereClauseMap.put("STUDENT.VERIFIED='?'", "Y");
            } else {
                whereClauseMap.put("STUDENT.VERIFIED='?'", "N");
            }
            
            whereClauseMap.put("STUDENT.ADMISSION_NUMBER LIKE '%?%'", studentSearchCriteria.getAdmissionNumber());
            whereClauseMap.put("(STUDENT.FIRST_NAME LIKE '%?%' OR STUDENT.MIDDLE_NAME LIKE '%?%' OR STUDENT.LAST_NAME LIKE '%?%')",
                    studentSearchCriteria.getStudentName());
            int branchId = studentSearchCriteria.getBranchId();
            if (branchId != 0) {
                whereClauseMap.put("BRANCH.BRANCH_ID=?", String.valueOf(branchId));
            }
            int divisionId = studentSearchCriteria.getDivisionId();
            if (divisionId != 0) {
                whereClauseMap.put("REF_DIVISION.DIVISION_ID=?", String.valueOf(divisionId));
            }
            int schoolId = studentSearchCriteria.getSchoolId();
            if (schoolId != 0) {
                whereClauseMap.put("SCHOOL.SCHOOL_ID=?", String.valueOf(schoolId));
            }
            int classId = studentSearchCriteria.getClassId();
            if (classId != 0) {
                whereClauseMap.put("REF_CLASS.CLASS_ID=?", String.valueOf(classId));
            }
            int mediumId = studentSearchCriteria.getMediumId();
            if (mediumId != 0) {
                whereClauseMap.put("REF_MEDIUM.MEDIUM_ID=?", String.valueOf(mediumId));
            }
            int sectionId = studentSearchCriteria.getSectionId();
            if (sectionId != 0) {
                whereClauseMap.put("REF_SECTION.SECTION_ID=?", String.valueOf(sectionId));
            }
            whereClauseMap.put("STUDENT.GENDER='?'", studentSearchCriteria.getGender());
            whereClauseMap.put("STUDENT.RELIGION='?'", studentSearchCriteria.getReligion());
            whereClauseMap.put("STUDENT.BLOOD_GROUP='?'", studentSearchCriteria.getBloodGroup());

            // Filter by date of birth
            String dateOfBirthMin = studentSearchCriteria.getDateOfBirthMin();
            String dateOfBirthMax = studentSearchCriteria.getDateOfBirthMax();
            boolean filterByDateOfBirth = (!StringUtil.isNullOrBlank(dateOfBirthMin)
                    && !StringUtil.isNullOrBlank(dateOfBirthMax));
            if (filterByDateOfBirth) {
                whereClauseMap.put("STUDENT.DATE_OF_BIRTH>='?'", dateOfBirthMin);
                whereClauseMap.put("STUDENT.DATE_OF_BIRTH<='?'", dateOfBirthMax);
            }

            // Filter by date of employment.
            String dateOfJoiningMin = studentSearchCriteria.getDateOfJoiningMin();
            String dateOfJoiningMax = studentSearchCriteria.getDateOfJoiningMax();
            boolean filterByDateOfJoining = (!StringUtil.isNullOrBlank(dateOfJoiningMin)
                    && !StringUtil.isNullOrBlank(dateOfJoiningMax));
            if (filterByDateOfJoining) {
                whereClauseMap.put("STUDENT.DATE_OF_JOINING>='?'", dateOfJoiningMin);
                whereClauseMap.put("STUDENT.DATE_OF_JOINING<='?'", dateOfJoiningMax);
            }
            queryBuffer.append(DatabaseUtil.getWhereClause(whereClauseMap));
        }
        queryBuffer.append(" ORDER BY STUDENT.STUDENT_ID ");
        return queryBuffer.toString();
    }

}
