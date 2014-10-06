package com.myschool.attendance.dao;

/**
 * The Class AttendanceAssignmentsDaoSql.
 */
public class AttendanceAssignmentsDaoSql {

    /** The Constant INSERT_ATTENDANCE_REGION. */
    public static final String INSERT_ATTENDANCE_REGION;

    /** The Constant INSERT_ATTENDANCE_BRANCH. */
    public static final String INSERT_ATTENDANCE_BRANCH;

    /** The Constant INSERT_ATTENDANCE_SCHOOL. */
    public static final String INSERT_ATTENDANCE_SCHOOL;

    /** The Constant INSERT_ATTENDANCE_REGISTERED_CLASS. */
    public static final String INSERT_ATTENDANCE_REGISTERED_CLASS;

    /** The Constant DELETE_ATTENDANCE_REGION. */
    public static final String DELETE_ATTENDANCE_REGION;

    /** The Constant DELETE_ATTENDANCE_BRANCH. */
    public static final String DELETE_ATTENDANCE_BRANCH;

    /** The Constant DELETE_ATTENDANCE_SCHOOL. */
    public static final String DELETE_ATTENDANCE_SCHOOL;

    /** The Constant DELETE_ATTENDANCE_REGISTERED_CLASS. */
    public static final String DELETE_ATTENDANCE_REGISTERED_CLASS;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("INSERT INTO ATTENDANCE_PROFILE_REGION(");
        buffer.append("ATTENDANCE_PROFILE_ID, REGION_ID");
        buffer.append(") VALUES (?, ?)");
        INSERT_ATTENDANCE_REGION = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO ATTENDANCE_PROFILE_BRANCH(");
        buffer.append("ATTENDANCE_PROFILE_ID, BRANCH_ID");
        buffer.append(") VALUES (?, ?)");
        INSERT_ATTENDANCE_BRANCH = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO ATTENDANCE_PROFILE_SCHOOL(");
        buffer.append("ATTENDANCE_PROFILE_ID, SCHOOL_ID");
        buffer.append(") VALUES (?, ?)");
        INSERT_ATTENDANCE_SCHOOL = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO ATTENDANCE_PROFILE_CLASS(");
        buffer.append("ATTENDANCE_PROFILE_ID, CLASS_ID");
        buffer.append(") VALUES (?, ?)");
        INSERT_ATTENDANCE_REGISTERED_CLASS = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM ATTENDANCE_PROFILE_REGION ");
        buffer.append("WHERE ATTENDANCE_PROFILE_ID=?");
        DELETE_ATTENDANCE_REGION = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM ATTENDANCE_PROFILE_BRANCH ");
        buffer.append("WHERE ATTENDANCE_PROFILE_ID=?");
        DELETE_ATTENDANCE_BRANCH = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM ATTENDANCE_PROFILE_SCHOOL ");
        buffer.append("WHERE ATTENDANCE_PROFILE_ID=?");
        DELETE_ATTENDANCE_SCHOOL = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM ATTENDANCE_PROFILE_CLASS ");
        buffer.append("WHERE ATTENDANCE_PROFILE_ID=?");
        DELETE_ATTENDANCE_REGISTERED_CLASS = buffer.toString();
        buffer.setLength(0);

    }

    /**
     * Instantiates a new attendance assignments dao sql.
     */
    private AttendanceAssignmentsDaoSql() {
    }

}
