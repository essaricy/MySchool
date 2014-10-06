package com.myschool.exam.dao;

import com.myschool.infra.database.util.DatabaseUtil;

/**
 * The Class ExamGradeDaoSql.
 */
public class ExamGradeDaoSql {

    /**
     * Gets the exam grade sql.
     *
     * @param uniqueRecord the unique record
     * @return the exam grade sql
     */
    public static String getExamGradeSql(boolean uniqueRecord) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT EXAM_GRADE_ID, GRADE_NAME, PERCENTAGE ");
        builder.append("FROM REF_EXAM_GRADE ");
        if (uniqueRecord) {
            builder.append("WHERE ");
            builder.append("EXAM_GRADE_ID = ? ");
        }
        return builder.toString();
    }

    /**
     * Gets the insert exam grade sql.
     *
     * @return the insert exam grade sql
     */
    public static String getInsertExamGradeSql() {
        return "INSERT INTO REF_EXAM_GRADE(EXAM_GRADE_ID, GRADE_NAME, PERCENTAGE) VALUES (?, ?, ?)";
    }

    /**
     * Gets the select exam grade sql.
     *
     * @param examGradeName the exam grade name
     * @return the select exam grade sql
     */
    public static String getSelectExamGradeSql(String examGradeName) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT EXAM_GRADE_ID, GRADE_NAME, PERCENTAGE FROM REF_EXAM_GRADE ");
        builder.append(" WHERE GRADE_NAME = ").append(DatabaseUtil.getNullableStringValue(examGradeName));
        return builder.toString();
    }

    /**
     * Builds the delete exam grade sql.
     *
     * @return the string
     */
    public static String buildDeleteExamGradeSql() {
        StringBuilder builder = new StringBuilder();
        builder.append("DELETE FROM REF_EXAM_GRADE ");
        builder.append("WHERE EXAM_GRADE_ID = ? ");
        return builder.toString();
    }

    /**
     * Builds the exam grade update sql.
     *
     * @return the string
     */
    public static String buildExamGradeUpdateSql() {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE REF_EXAM_GRADE ");
        builder.append("SET GRADE_NAME = ?, ");
        builder.append("PERCENTAGE = ? ");
        builder.append("WHERE ");
        builder.append("EXAM_GRADE_ID = ? ");
        return builder.toString();
    }

}
