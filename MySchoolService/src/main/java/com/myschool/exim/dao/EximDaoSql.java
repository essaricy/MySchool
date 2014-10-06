package com.myschool.exim.dao;

/**
 * The Class EximDaoSql.
 */
public class EximDaoSql {

    /**
     * Select exims sql.
     *
     * @return the string
     */
    public static String selectEximsSql() {
        return selectEximsSql(true, true);
    }

    /**
     * Select exims sql.
     *
     * @param imports the imports
     * @param exports the exports
     * @return the string
     */
    public static String selectEximsSql(boolean imports, boolean exports) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append("EXIM_KEY, ");
        builder.append("DESCRIPTION, ");
        builder.append("CAN_IMPORT, ");
        builder.append("CAN_EXPORT ");
        builder.append("FROM EXIM ");
        if (imports) {
            builder.append("WHERE CAN_IMPORT = 'Y' ");
            if (exports) {
                builder.append("AND CAN_EXPORT = 'Y' ");
            }
        } else {
            if (exports) {
                builder.append("WHERE CAN_EXPORT = 'Y' ");
            }
        }
        builder.append("ORDER BY DESCRIPTION ");
        return builder.toString();
    }

}
