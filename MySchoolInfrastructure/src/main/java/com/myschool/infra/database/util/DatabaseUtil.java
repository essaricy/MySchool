package com.myschool.infra.database.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.myschool.common.util.StringUtil;

/**
 * The Class DatabaseUtil.
 */
public class DatabaseUtil {

    /**
     * Gets the nullable string value.
     *
     * @param value the value
     * @return the nullable string value
     */
    public static String getNullableStringValue(String value) {
        StringBuffer queryPart = new StringBuffer();
        if (value == null || StringUtil.isEmpty(value.trim())) {
            queryPart .append("NULL");
        } else {
            queryPart.append("'").append(value).append("'");
        }
        return queryPart.toString();
    }

    /**
     * Builds the where in clause.
     *
     * @param objects the objects
     * @return the string
     */
    public static String buildWhereInClause(List<Object> objects) {
        boolean atLeastOneAdded = false;
        StringBuffer stringBuffer = new StringBuffer();

        if (objects != null && !objects.isEmpty()) {
            for (int index = 0; index < objects.size(); index++) {
                Object object = objects.get(index);
                if (object != null) {
                    String objectToString = object.toString().trim();
                    if (!StringUtil.isNullOrBlank(objectToString)) {
                        if (atLeastOneAdded) {
                            stringBuffer.append(", ");
                        }
                        stringBuffer.append("'").append(objectToString).append("'");
                        atLeastOneAdded = true;
                    }
                }
            }
        }
        return stringBuffer.toString();
    }

    /**
     * Gets the where clause.
     * 
     * @param whereClauseMap the where clause map
     * @return the where clause
     */
    public static StringBuffer getWhereClause(Map<String, String> whereClauseMap) {
        String key = null;
        String value = null;
        StringBuffer whereClause = new StringBuffer();
        boolean whereClauseAdded = false;
        Entry<String, String> entry = null;

        if (whereClauseMap != null && !whereClauseMap.isEmpty()) {
            Set<Entry<String, String>> entrySet = whereClauseMap.entrySet();
            for (Iterator iterator = entrySet.iterator(); iterator.hasNext();) {
                entry = (Entry<String, String>) iterator.next();
                key = entry.getKey();
                value = entry.getValue();
                if (!StringUtil.isNullOrBlank(value)) {
                    if (whereClauseAdded) {
                        whereClause.append("AND ");
                    } else {
                        whereClause.append(" WHERE ");
                    }
                    whereClause.append(key.replaceAll("\\?", value)).append(" ");
                    whereClauseAdded = true;
                }
            }
        }
        return whereClause;
    }

}
