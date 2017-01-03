package com.myschool.clazz.dao;

import com.quasar.core.util.DatabaseUtil;


/**
 * The Class MediumDaoSql.
 */
public class MediumDaoSql {


    /**
     * Builds the insert medium sql.
     *
     * @return the string
     */
    public static String buildInsertMediumSql() {
        return "INSERT INTO REF_MEDIUM (MEDIUM_ID, DESCRIPTION) VALUES ( ?, ?)";
    }

    /**
     * Builds the select mediums sql.
     *
     * @param uniqueRecord the unique record
     * @return the string
     */
    public static String buildSelectMediumsSql(boolean uniqueRecord) {
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("SELECT MEDIUM_ID, DESCRIPTION FROM REF_MEDIUM ");
    	if (uniqueRecord) {
    	    buffer.append(" WHERE MEDIUM_ID = ?");
    	}
    	return buffer.toString();
    	
    }

   
    /**
     * Builds the update medium sql.
     *
     * @return the string
     */
    public static String buildUpdateMediumSql() {
        return "UPDATE REF_MEDIUM SET DESCRIPTION = ? WHERE MEDIUM_ID = ? ";
    }

    /**
     * Builds the delete medium sql.
     *
     * @return the string
     */
    public static String buildDeleteMediumSql() {
        return "DELETE FROM REF_MEDIUM WHERE MEDIUM_ID = ?";
    }

    /**
     * Builds the select mediums sql.
     *
     * @param mediumName the medium name
     * @return the string
     */
    public static String buildSelectMediumsSql(String mediumName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT MEDIUM_ID, DESCRIPTION FROM REF_MEDIUM ");
        buffer.append(" WHERE DESCRIPTION = ").append(DatabaseUtil.getNullableStringValue(mediumName));
        return buffer.toString();
    }

}
