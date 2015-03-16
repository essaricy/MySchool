package com.myschool.academic.dao;

import com.myschool.common.util.DatabaseUtil;

/**
 * The Class AyeProcessDaoSql.
 */
public class AyeProcessDaoSql {

    /**
     * Builds the insert sql.
     *
     * @param academicYearName the academic year name
     * @return the string
     */
    public static String buildInsertSql(String academicYearName) {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ORGANIZATION_PROFILE ");
        builder.append("SET CURRENT_AY_NAME = ").append(DatabaseUtil.getNullableStringValue(academicYearName));
        return builder.toString();
    }

}
