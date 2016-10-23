package com.myschool.exam.dao;


/**
 * The Class StudentExamDaoSql.
 */
public class StudentExamDaoSql {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_ID. */
    public static final String SELECT_BY_STUDENT_SUBJECT;

    /** The Constant SELECT_BY_EXAM. */
    public static final String SELECT_BY_EXAM;

    /** The Constant SELECT_BY_CLASS. */
    public static final String SELECT_BY_CLASS_EXAM;

    /** The Constant SELECT_BY_STUDENT_CLASS_EXAM. */
    public static final String SELECT_BY_STUDENT_CLASS_EXAM;

    /** The Constant INSERT. */
    public static final String INSERT;

    /** The Constant UPDATE. */
    public static final String UPDATE;

    /** The Constant SELECT_STUDENT_IN_EXAM. */
    public static final String SELECT_STUDENT_IN_EXAM;

    /** The Constant SELECT_CURRENT_ACADEMIC_STUDENT_SUBJECT_MARKS. */
    public static final String SELECT_CURRENT_ACADEMIC_STUDENT_SUBJECT_MARKS;

    static {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append("STUDENT.STUDENT_ID, ");
        builder.append("STUDENT.ADMISSION_NUMBER, ");
        builder.append("STUDENT.FIRST_NAME, ");
        builder.append("STUDENT.MIDDLE_NAME, ");
        builder.append("STUDENT.LAST_NAME, ");
        builder.append("STUDENT.MOBILE_NUMBER, ");
        builder.append("STUDENT_EXAM.STUDENT_EXAM_ID, ");
        builder.append("STUDENT_EXAM.OBTAINED_MARKS, ");
        builder.append("SUBJECT_EXAM.SUBJECT_EXAM_ID, ");
        builder.append("SUBJECT_EXAM.SUBJECT_ID, ");
        builder.append("SUBJECT_EXAM.MAXIMUM_MARKS, ");
        builder.append("EXAM.EXAM_ID, ");
        builder.append("EXAM.EXAM_NAME ");
        builder.append("FROM ");
        builder.append("STUDENT ");
        builder.append("INNER JOIN CLASS ");
        builder.append("ON CLASS.CLASS_ID = STUDENT.CLASS_ID ");
        builder.append("INNER JOIN EXAM ");
        builder.append("ON EXAM.CLASS_ID = CLASS.CLASS_ID ");
        builder.append("INNER JOIN SUBJECT_EXAM ");
        builder.append("ON SUBJECT_EXAM.EXAM_ID = EXAM.EXAM_ID ");
        builder.append("LEFT OUTER JOIN STUDENT_EXAM ");
        builder.append("ON STUDENT_EXAM.SUBJECT_EXAM_ID = SUBJECT_EXAM.SUBJECT_EXAM_ID ");
        builder.append("AND STUDENT_EXAM.STUDENT_ID = STUDENT.STUDENT_ID ");
        SELECT_ALL = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE EXAM.EXAM_ID=?");
        SELECT_BY_EXAM = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE EXAM.EXAM_ID=? ");
        builder.append("AND CLASS.CLASS_ID=?");
        SELECT_BY_CLASS_EXAM = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE STUDENT.STUDENT_ID=? ");
        builder.append("AND CLASS.CLASS_ID=? ");
        builder.append("AND EXAM.EXAM_ID=?");
        SELECT_BY_STUDENT_CLASS_EXAM = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE CLASS.CLASS_ID=? ");
        builder.append("AND EXAM CLASS.CLASS_ID=?");
        SELECT_BY_STUDENT_SUBJECT = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE STUDENT.ADMISSION_NUMBER=? ");
        builder.append("AND SUBJECT_EXAM.SUBJECT_ID=? ");
        builder.append("AND EXAM.EXAM_COMPLETED='Y' ");
        builder.append("AND EXAM_DATE BETWEEN ");
        builder.append("  (SELECT AY_START_DATE FROM ACADEMICS ");
        builder.append("  INNER JOIN ORGANIZATION_MANIFEST ");
        builder.append("  ON ORGANIZATION_MANIFEST.CURRENT_AY_NAME = ACADEMICS.ACADEMIC_YEAR_NAME)");
        builder.append(" AND ");
        builder.append("  (SELECT AY_END_DATE FROM ACADEMICS ");
        builder.append("  INNER JOIN ORGANIZATION_MANIFEST ");
        builder.append("  ON ORGANIZATION_MANIFEST.CURRENT_AY_NAME = ACADEMICS.ACADEMIC_YEAR_NAME) ");
        builder.append("ORDER BY EXAM.EXAM_DATE, EXAM.EXAM_NAME");
        SELECT_CURRENT_ACADEMIC_STUDENT_SUBJECT_MARKS = builder.toString();
        builder.setLength(0);

        builder.append("INSERT INTO STUDENT_EXAM ( ");
        builder.append("STUDENT_EXAM_ID, ");
        builder.append("STUDENT_ID, ");
        builder.append("SUBJECT_EXAM_ID, ");
        builder.append("OBTAINED_MARKS)");
        builder.append("VALUES (?, ?, ?, ?) ");
        INSERT = builder.toString();
        builder.setLength(0);

        builder.append("UPDATE STUDENT_EXAM ");
        builder.append("SET OBTAINED_MARKS=? ");
        builder.append("WHERE STUDENT_EXAM_ID=?");
        UPDATE = builder.toString();
        builder.setLength(0);

        builder.append("SELECT "); 
        builder.append("SUBJECT_EXAM.SUBJECT_EXAM_ID, ");
        builder.append("SUBJECT_EXAM.MAXIMUM_MARKS, ");
        builder.append("SUBJECT.SUBJECT_ID AS OPERATING_SUBJECT_ID, ");
        builder.append("REF_SUBJECT.SUBJECT_ID, ");
        builder.append("REF_SUBJECT.SUBJECT_NAME, ");
        builder.append("OBTAINED_MARKS ");
        builder.append("FROM STUDENT_EXAM ");
        builder.append("INNER JOIN SUBJECT_EXAM ");
        builder.append("ON SUBJECT_EXAM.SUBJECT_EXAM_ID = STUDENT_EXAM.SUBJECT_EXAM_ID ");
        builder.append("INNER JOIN SUBJECT ");
        builder.append("ON SUBJECT.SUBJECT_ID = SUBJECT_EXAM.SUBJECT_ID ");
        builder.append("INNER JOIN REF_SUBJECT ");
        builder.append("ON REF_SUBJECT.SUBJECT_ID = SUBJECT.REF_SUBJECT_ID ");
        builder.append("INNER JOIN STUDENT ");
        builder.append("ON STUDENT.STUDENT_ID = STUDENT_EXAM.STUDENT_ID ");
        builder.append("WHERE STUDENT.ADMISSION_NUMBER=? ");
        builder.append("AND SUBJECT_EXAM.EXAM_ID=?");
        SELECT_STUDENT_IN_EXAM = builder.toString();
        builder.setLength(0);
    }

    /**
     * Instantiates a new student exam dao sql.
     */
    private StudentExamDaoSql() { }

}
