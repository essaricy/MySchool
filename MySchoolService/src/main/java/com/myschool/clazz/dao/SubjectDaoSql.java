package com.myschool.clazz.dao;

import com.quasar.core.util.DatabaseUtil;


/**
 * The Class SubjectDaoSql.
 */
public class SubjectDaoSql {

    /**
     * Gets the insert subject query.
     *
     * @return the insert subject query
     */
    public static String getInsertSubjectSql() {
        return "INSERT INTO REF_SUBJECT (SUBJECT_ID, SUBJECT_NAME) VALUES ( ?, ?)";
    }

    /**
     * Gets the subjects query.
     *
     * @param uniqueRecord the unique record
     * @return the subjects query
     */
    public static String getSubjectsQuery(boolean uniqueRecord) {
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("SELECT SUBJECT_ID, SUBJECT_NAME FROM REF_SUBJECT ");
    	if (uniqueRecord) {
    	    buffer.append(" WHERE SUBJECT_ID = ?");
    	}
    	return buffer.toString();
    	
    }

    /**
     * Gets the update subject query.
     *
     * @return the update subject query
     */
    public static String getUpdateSubjectQuery() {
        return "UPDATE REF_SUBJECT SET SUBJECT_NAME = ? WHERE SUBJECT_ID = ? ";
    }

    /**
     * Gets the delete subject query.
     *
     * @return the delete subject query
     */
    public static String getDeleteSubjectQuery() {
        return "DELETE FROM REF_SUBJECT WHERE SUBJECT_ID = ?";
    }

    /**
     * Gets the operating subjects by class query.
     *
     * @return the operating subjects by class query
     */
    public static String getOperatingSubjectsByClassQuery() {
        return "SELECT "
        + "SUBJECT.SUBJECT_ID AS SUBJECT_ID, "
        + "REF_SUBJECT.SUBJECT_NAME AS SUBJECT_NAME "
        + "FROM SUBJECT "
        + "INNER JOIN REF_SUBJECT "
        + "ON REF_SUBJECT.SUBJECT_ID = SUBJECT.REF_SUBJECT_ID "
        + "WHERE CLASS_ID = ? ";
    }

    /**
     * Gets the select subject query.
     *
     * @param subjectName the subject name
     * @return the select subject query
     */
    public static String getSelectSubjectSql(String subjectName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT SUBJECT_ID, SUBJECT_NAME FROM REF_SUBJECT ");
        buffer.append(" WHERE SUBJECT_NAME = ").append(DatabaseUtil.getNullableStringValue(subjectName));
        return buffer.toString();
    }

}
