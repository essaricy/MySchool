package com.myschool.clazz.dao;

import com.myschool.infra.database.util.DatabaseUtil;

public class SectionDaoSql {

    public static String buildInsertSql() {
        return "INSERT INTO REF_SECTION (SECTION_ID, SECTION_NAME) VALUES ( ?, ?) ";
    }

    public static String buildDeleteSql() {
        return "DELETE FROM REF_SECTION WHERE SECTION_ID = ? ";
    }

    public static String buildSelectSql(boolean uniqueRecord) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT SECTION_ID, SECTION_NAME ");
        buffer.append("FROM REF_SECTION ");
        if (uniqueRecord) {
            buffer.append("WHERE SECTION_ID = ? ");
        }
        return buffer.toString();
    }

    public static String buildUpdateSql() {
        return "UPDATE REF_SECTION SET SECTION_NAME =? WHERE SECTION_ID = ? ";
    }

    /**
     * Gets the select section sql.
     *
     * @param sectionName the section name
     * @return the select section sql
     */
    public static String getSelectSectionSql(String sectionName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT SECTION_ID, SECTION_NAME ");
        buffer.append("FROM REF_SECTION ");
        buffer.append("WHERE SECTION_NAME = ").append(DatabaseUtil.getNullableStringValue(sectionName));
        return buffer.toString();
    }

}
