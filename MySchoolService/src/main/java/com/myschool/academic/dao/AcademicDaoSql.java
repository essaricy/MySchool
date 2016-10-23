package com.myschool.academic.dao;

/**
 * The Class AcademicDaoSql.
 */
public final class AcademicDaoSql {

    /** The SELECT_ALL. */
    private static String SELECT_ALL;

    /** The SELECT_ALL_ORDER_DESC. */
    public static String SELECT_ALL_ORDER_DESC;

    /** The SELECT_BY_ACADEMIC_YEAR_NAME. */
    public static String SELECT_BY_ACADEMIC_YEAR_NAME;

    /** The SELECT_CURRENT_ACADEMIC. */
    public static String SELECT_CURRENT_ACADEMIC;

    /** The SELECT_NEXT_ACADEMIC. */
    public static String SELECT_NEXT_ACADEMIC;

    /** The INSERT. */
    public static String INSERT;

    /** The UPDATE. */
    public static String UPDATE;

    /** The DELETE. */
    public static String DELETE;

    static {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append("ACADEMIC_YEAR_NAME, ");
        builder.append("AY_START_DATE, ");
        builder.append("AY_END_DATE ");
        builder.append("FROM ");
        builder.append("ACADEMICS ");
        SELECT_ALL = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("ORDER BY AY_START_DATE DESC ");
        SELECT_ALL_ORDER_DESC = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE ");
        builder.append("ACADEMIC_YEAR_NAME = ? ");
        SELECT_BY_ACADEMIC_YEAR_NAME = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE ");
        builder.append("ACADEMIC_YEAR_NAME = (SELECT CURRENT_AY_NAME FROM ORGANIZATION_MANIFEST) ");
        SELECT_CURRENT_ACADEMIC = builder.toString();
        builder.setLength(0);

        builder.append(SELECT_ALL);
        builder.append("WHERE ");
        builder.append("AY_START_DATE > ( ");
        builder.append("SELECT AY_END_DATE ");
        builder.append("FROM  ACADEMICS  ");
        builder.append("INNER JOIN ORGANIZATION_MANIFEST ");
        builder.append("ON ORGANIZATION_MANIFEST.CURRENT_AY_NAME = ACADEMICS.ACADEMIC_YEAR_NAME) ");
        builder.append("ORDER BY AY_START_DATE ASC ");
        builder.append("LIMIT 1 ");
        SELECT_NEXT_ACADEMIC = builder.toString();
        builder.setLength(0);

        builder.append("INSERT INTO ACADEMICS (");
        builder.append("ACADEMIC_YEAR_NAME, ");
        builder.append("AY_START_DATE, ");
        builder.append("AY_END_DATE ");
        builder.append(")VALUES (?, ?, ?)");
        INSERT = builder.toString();
        builder.setLength(0);

        builder.append("UPDATE ACADEMICS ");
        builder.append("SET AY_START_DATE=?, ");
        builder.append("AY_END_DATE=? ");
        builder.append("WHERE ACADEMIC_YEAR_NAME=?");
        UPDATE = builder.toString();
        builder.setLength(0);

        builder.append("DELETE FROM ACADEMICS WHERE ACADEMIC_YEAR_NAME=?");
        DELETE = builder.toString();
        builder.setLength(0);

    }

    /**
     * Instantiates a new academic dao sql.
     */
    private AcademicDaoSql() {
    }

}
