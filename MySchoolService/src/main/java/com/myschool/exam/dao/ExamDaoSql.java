/*
 * 
 */
package com.myschool.exam.dao;


/**
 * The Class ExamDaoSql.
 */
public class ExamDaoSql {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_CLASS. */
    public static final String SELECT_BY_CLASS;

    /** The Constant SELECT_BY_ID. */
    public static final String SELECT_BY_ID;

    /** The Constant SELECT_BY_CLASS_EXAM. */
    public static final String SELECT_BY_CLASS_EXAM;

    /** The Constant SELECT_BY_EXAM. */
    public static final String SELECT_BY_EXAM;

    /** The Constant SELECT_BY_CURRENT_ACADEMIC. */
    public static final String SELECT_BY_CURRENT_ACADEMIC;

    /** The Constant SELECT_BY_CLASS_CURRENT_ACADEMIC. */
    public static final String SELECT_BY_CLASS_CURRENT_ACADEMIC;

    /** The Constant INSERT. */
    public static final String INSERT;

    /** The Constant UPDATE. */
    public static final String UPDATE;

    /** The Constant DELETE. */
    public static final String DELETE;

    /** The Constant SELECT_LATEST. */
    public static final String SELECT_LATEST;

    static {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append("EXAM_ID, ");
        builder.append("EXAM.CLASS_ID, ");
        builder.append("EXAM_NAME, ");
        builder.append("EXAM_DATE, ");
        builder.append("EXAM_COMPLETED ");
        builder.append("FROM EXAM ");
        SELECT_ALL = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE CLASS_ID=? ");
        builder.append("ORDER BY EXAM_DATE ASC ");
        SELECT_BY_CLASS = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE EXAM.EXAM_ID=? ");
        builder.append("ORDER BY EXAM_DATE ASC ");
        SELECT_BY_ID = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE EXAM.CLASS_ID=? ");
        builder.append("AND EXAM.EXAM_COMPLETED='Y' ");
        builder.append("ORDER BY EXAM_DATE DESC ");
        builder.append("LIMIT 1");
        SELECT_LATEST = builder.toString();
        builder.setLength(0);

        builder.append("INSERT INTO EXAM (");
        builder.append("EXAM_ID, ");
        builder.append("CLASS_ID, ");
        builder.append("EXAM_NAME, ");
        builder.append("EXAM_DATE, ");
        builder.append("EXAM_COMPLETED) ");
        builder.append("VALUES (?, ?, ?, ?, ?) ");
        INSERT = builder.toString();
        builder.setLength(0);

        builder.append("UPDATE EXAM ");
        builder.append("SET EXAM_NAME=?, ");
        builder.append("EXAM_DATE=?, ");
        builder.append("EXAM_COMPLETED=? ");
        builder.append("WHERE EXAM_ID=?");
        UPDATE = builder.toString();
        builder.setLength(0);

        builder.append("DELETE FROM EXAM WHERE EXAM.EXAM_ID=?");
        DELETE = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE EXAM.CLASS_ID=? ");
        builder.append("AND EXAM_NAME=? ");
        builder.append("AND EXAM_DATE=?");
        SELECT_BY_CLASS_EXAM = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE EXAM.CLASS_ID=? ");
        builder.append("AND EXAM_NAME=? ");
        SELECT_BY_EXAM = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE ");
        builder.append("CLASS_ID=? ");
        builder.append("AND EXAM_DATE ");
        builder.append("BETWEEN ( SELECT AY_START_DATE FROM ACADEMICS ");
        builder.append("INNER JOIN ORGANIZATION_PROFILE ");
        builder.append("ON ORGANIZATION_PROFILE.CURRENT_AY_NAME = ACADEMICS.ACADEMIC_YEAR_NAME) ");
        builder.append("AND ( SELECT AY_END_DATE FROM ACADEMICS ");
        builder.append("INNER JOIN ORGANIZATION_PROFILE ");
        builder.append("ON ORGANIZATION_PROFILE.CURRENT_AY_NAME = ACADEMICS.ACADEMIC_YEAR_NAME ) ");
        SELECT_BY_CLASS_CURRENT_ACADEMIC = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE ");
        builder.append("WHERE EXAM_DATE ");
        builder.append("BETWEEN ( SELECT AY_START_DATE FROM ACADEMICS ");
        builder.append("INNER JOIN ORGANIZATION_PROFILE ");
        builder.append("ON ORGANIZATION_PROFILE.CURRENT_AY_NAME = ACADEMICS.ACADEMIC_YEAR_NAME) ");
        builder.append("AND ( SELECT AY_END_DATE FROM ACADEMICS ");
        builder.append("INNER JOIN ORGANIZATION_PROFILE ");
        builder.append("ON ORGANIZATION_PROFILE.CURRENT_AY_NAME = ACADEMICS.ACADEMIC_YEAR_NAME ) ");
        SELECT_BY_CURRENT_ACADEMIC = builder.toString();
        builder.setLength(0);
    }

    /**
     * Instantiates a new exam dao sql.
     */
    private ExamDaoSql() { }

}
