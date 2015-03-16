package com.myschool.attendance.dao;

import com.myschool.branch.dao.BranchDaoSql;
import com.myschool.branch.dao.RegionDaoSql;
import com.myschool.branch.dao.StateDaoSql;
import com.myschool.clazz.dao.RegisteredClassDaoSql;
import com.myschool.school.dao.SchoolDaoSql;

/**
 * The Class AttendanceProfileDaoSql.
 */
public class AttendanceProfileDaoSql {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_ID. */
    public static final String SELECT_BY_ID;

    public static final String SELECT_BY_PROFILE_NAME;

    /** The Constant SELECT_ALL_STATES_BY_PROFILE. */
    public static final String SELECT_ALL_STATES_BY_PROFILE;

    /** The Constant SELECT_ALL_REGIONS_BY_PROFILE. */
    public static final String SELECT_ALL_REGIONS_BY_PROFILE;

    /** The Constant SELECT_ALL_BRANCHES_BY_PROFILE. */
    public static final String SELECT_ALL_BRANCHES_BY_PROFILE;

    /** The Constant SELECT_ALL_SCHOOLS_BY_PROFILE. */
    public static final String SELECT_ALL_SCHOOLS_BY_PROFILE;

    /** The Constant SELECT_ALL_CLASSES_BY_PROFILE. */
    public static final String SELECT_ALL_CLASSES_BY_PROFILE;

    public static final String SELECT_ALL_EXCLUDE_THIS;

    /** The Constant INSERT. */
    public static final String INSERT;

    /** The Constant UPDATE. */
    public static final String UPDATE;

    /** The Constant DELETE. */
    public static final String DELETE;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("PROFILE_ID, ");
        buffer.append("PROFILE_NAME, ");
        buffer.append("ACADEMICS.ACADEMIC_YEAR_NAME AS ACADEMICS_ACADEMIC_YEAR_NAME, ");
        buffer.append("ACADEMICS.AY_START_DATE AS ACADEMICS_AY_START_DATE, ");
        buffer.append("ACADEMICS.AY_END_DATE AS ACADEMICS_AY_END_DATE ");
        buffer.append("FROM ");
        buffer.append("ATTENDANCE_PROFILE ");
        buffer.append("INNER JOIN ACADEMICS ");
        buffer.append("ON EFFECTIVE_ACADEMIC=ACADEMICS.ACADEMIC_YEAR_NAME ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE PROFILE_ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE PROFILE_NAME=?");
        SELECT_BY_PROFILE_NAME = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE PROFILE_ID<>? ");
        buffer.append("AND ACADEMICS.ACADEMIC_YEAR_NAME=? ");
        SELECT_ALL_EXCLUDE_THIS = buffer.toString();
        buffer.setLength(0);

        buffer.append(StateDaoSql.SELECT_ALL);
        buffer.append("INNER JOIN ATTENDANCE_PROFILE_STATE ");
        buffer.append("ON ATTENDANCE_PROFILE_STATE.STATE_ID=REF_STATE.STATE_ID ");
        buffer.append("WHERE ATTENDANCE_PROFILE_STATE.ATTENDANCE_PROFILE_ID=?");
        SELECT_ALL_STATES_BY_PROFILE = buffer.toString();
        buffer.setLength(0);

        buffer.append(RegionDaoSql.SELECT_ALL);
        buffer.append("INNER JOIN ATTENDANCE_PROFILE_REGION ");
        buffer.append("ON ATTENDANCE_PROFILE_REGION.REGION_ID=REF_REGION.REGION_ID ");
        buffer.append("WHERE ATTENDANCE_PROFILE_REGION.ATTENDANCE_PROFILE_ID=?");
        SELECT_ALL_REGIONS_BY_PROFILE = buffer.toString();
        buffer.setLength(0);

        buffer.append(BranchDaoSql.SELECT_ALL);
        buffer.append("INNER JOIN ATTENDANCE_PROFILE_BRANCH ");
        buffer.append("ON ATTENDANCE_PROFILE_BRANCH.BRANCH_ID=BRANCH.BRANCH_ID ");
        buffer.append("WHERE ATTENDANCE_PROFILE_BRANCH.ATTENDANCE_PROFILE_ID=?");
        SELECT_ALL_BRANCHES_BY_PROFILE = buffer.toString();
        buffer.setLength(0);

        buffer.append(SchoolDaoSql.SELECT_ALL);
        buffer.append("INNER JOIN ATTENDANCE_PROFILE_SCHOOL ");
        buffer.append("ON ATTENDANCE_PROFILE_SCHOOL.SCHOOL_ID=SCHOOL.SCHOOL_ID ");
        buffer.append("WHERE ATTENDANCE_PROFILE_SCHOOL.ATTENDANCE_PROFILE_ID=?");
        SELECT_ALL_SCHOOLS_BY_PROFILE = buffer.toString();
        buffer.setLength(0);

        buffer.append(RegisteredClassDaoSql.SELECT_ALL);
        buffer.append("INNER JOIN ATTENDANCE_PROFILE_CLASS ");
        buffer.append("ON ATTENDANCE_PROFILE_CLASS.CLASS_ID=CLASS.CLASS_ID ");
        buffer.append("WHERE ATTENDANCE_PROFILE_CLASS.ATTENDANCE_PROFILE_ID=?");
        SELECT_ALL_CLASSES_BY_PROFILE = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO ATTENDANCE_PROFILE (");
        buffer.append("PROFILE_ID, PROFILE_NAME, EFFECTIVE_ACADEMIC");
        buffer.append(")VALUES(?, ?, ?)");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE ATTENDANCE_PROFILE ");
        buffer.append("SET PROFILE_NAME=?,");
        buffer.append("EFFECTIVE_ACADEMIC=? ");
        buffer.append("WHERE PROFILE_ID=?");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM ATTENDANCE_PROFILE WHERE PROFILE_ID=?");
        DELETE = buffer.toString();
        buffer.setLength(0);

    }

    /**
     * Instantiates a new attendance profile sql.
     */
    private AttendanceProfileDaoSql() { }

}
