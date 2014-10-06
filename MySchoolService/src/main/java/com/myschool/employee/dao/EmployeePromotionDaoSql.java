package com.myschool.employee.dao;


/**
 * The Class EmployeePromotionDaoSql.
 */
public class EmployeePromotionDaoSql {

    /** The SELECT_ALL. */
    public static String SELECT_ALL;

    /** The SELECT_BY_ID. */
    public static String SELECT_BY_ID;

    /** The SELECT_BY_EMPLOYEE_ID. */
    public static String SELECT_BY_EMPLOYEE_ID;

    /** The SELECT_BY_EMPLOYEE_NUMBER. */
    public static String SELECT_BY_EMPLOYEE_NUMBER;

    /** The SELECT_BY_PROMOTION. */
    public static String SELECT_BY_PROMOTION;

    /** The INSERT. */
    public static String INSERT;

    /** The UPDATE. */
    public static String UPDATE;

    /** The DELETE. */
    public static String DELETE;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("EMPLOYEE_PROMOTION.PROMOTION_ID, ");
        buffer.append("EMPLOYEE_PROMOTION.EMPLOYEE_ID, ");
        buffer.append("PRIOR_DESIGNATION.DESIGNATION_ID AS PRIOR_DESIGNATION_DESIGNATION_ID, ");
        buffer.append("PRIOR_DESIGNATION.DESIGNATION AS PRIOR_DESIGNATION_DESIGNATION, ");
        buffer.append("CURRENT_DESIGNATION.DESIGNATION_ID AS CURRENT_DESIGNATION_DESIGNATION_ID, ");
        buffer.append("CURRENT_DESIGNATION.DESIGNATION AS CURRENT_DESIGNATION_DESIGNATION, ");
        buffer.append("EMPLOYEE_PROMOTION.EFFECTIVE_FROM ");
        buffer.append("FROM ");
        buffer.append("EMPLOYEE_PROMOTION ");
        buffer.append("INNER JOIN REF_DESIGNATION PRIOR_DESIGNATION ");
        buffer.append("ON PRIOR_DESIGNATION.DESIGNATION_ID = EMPLOYEE_PROMOTION.PRIOR_DESIGNATION ");
        buffer.append("INNER JOIN REF_DESIGNATION CURRENT_DESIGNATION ");
        buffer.append("ON CURRENT_DESIGNATION.DESIGNATION_ID = EMPLOYEE_PROMOTION.CURRENT_DESIGNATION ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE PROMOTION_ID=? ");
        SELECT_BY_ID = buffer.toString();

        buffer.setLength(0);
        buffer.append(SELECT_ALL);
        buffer.append("WHERE EMPLOYEE_ID=? ");
        SELECT_BY_EMPLOYEE_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL); 
        buffer.append("WHERE ");
        buffer.append("EMPLOYEE_PROMOTION.EMPLOYEE_ID=");
        buffer.append("(SELECT EMPLOYEE_ID FROM EMPLOYEE WHERE EMPLOYEE_NUMBER=?)");
        SELECT_BY_EMPLOYEE_NUMBER = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE EMPLOYEE_ID=? ");
        buffer.append("AND PRIOR_DESIGNATION.DESIGNATION_ID=? ");
        SELECT_BY_PROMOTION = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO ");
        buffer.append("EMPLOYEE_PROMOTION( ");
        buffer.append("PROMOTION_ID, ");
        buffer.append("EMPLOYEE_ID, ");
        buffer.append("PRIOR_DESIGNATION, ");
        buffer.append("CURRENT_DESIGNATION, ");
        buffer.append("EFFECTIVE_FROM ");
        buffer.append(") VALUES (?, ?, ?, ?, ?) ");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE EMPLOYEE_PROMOTION ");
        buffer.append("SET PRIOR_DESIGNATION=?, ");
        buffer.append("CURRENT_DESIGNATION=?, ");
        buffer.append("EFFECTIVE_FROM=? ");
        buffer.append("WHERE PROMOTION_ID=? ");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM EMPLOYEE_PROMOTION ");
        buffer.append("WHERE PROMOTION_ID=? ");
        DELETE = buffer.toString();
        buffer.setLength(0);
    }

}
