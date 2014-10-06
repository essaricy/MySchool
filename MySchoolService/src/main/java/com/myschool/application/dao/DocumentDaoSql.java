package com.myschool.application.dao;

import com.myschool.common.constants.DocumentApplicability;
import com.myschool.common.dto.DocumentSearchCriteria;
import com.myschool.user.constants.UserType;

/**
 * The Class DocumentDaoSql.
 */
public class DocumentDaoSql {

    /** The SELECT_ALL. */
    public static String SELECT_ALL;

    /** The SELECT_BY_ID. */
    public static String SELECT_BY_ID;

    /** The SELECT_BY_NAME. */
    public static String SELECT_BY_NAME;

    /** The INSERT. */
    public static String INSERT;

    /** The UPDATE. */
    public static String UPDATE;

    /** The DELETE. */
    public static String DELETE;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ");
        buffer.append("DOCUMENT_ID, ");
        buffer.append("DOCUMENT_NAME, ");
        buffer.append("DESCRIPTION, ");
        buffer.append("STUDENT_APPLICABLE, ");
        buffer.append("EMPLOYEE_APPLICABLE ");
        buffer.append("FROM ");
        buffer.append("DOCUMENT ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE DOCUMENT_ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE DOCUMENT_NAME=?");
        SELECT_BY_NAME = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO ");
        buffer.append("DOCUMENT( ");
        buffer.append("DOCUMENT_ID, ");
        buffer.append("DOCUMENT_NAME, ");
        buffer.append("DESCRIPTION, ");
        buffer.append("STUDENT_APPLICABLE, ");
        buffer.append("EMPLOYEE_APPLICABLE ");
        buffer.append(") VALUES (?, ?, ?, ?, ?) ");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE ");
        buffer.append("DOCUMENT ");
        buffer.append("SET DOCUMENT_NAME=?, ");
        buffer.append("DESCRIPTION=?, ");
        buffer.append("STUDENT_APPLICABLE=?, ");
        buffer.append("EMPLOYEE_APPLICABLE=? ");
        buffer.append("WHERE DOCUMENT_ID=?");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE ");
        buffer.append("FROM DOCUMENT ");
        buffer.append("WHERE DOCUMENT_ID=?");
        DELETE = buffer.toString();
        buffer.setLength(0);

    }

    /**
     * Instantiates a new document dao sql.
     */
    private DocumentDaoSql() {
    }

    /**
     * Builds the select query.
     * 
     * @param userType the user type
     * @return the string
     */
    public static String buildSelectQuery(UserType userType) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(SELECT_ALL);
        if (userType != null) {
            if (userType == UserType.EMPLOYEE) {
                buffer.append("WHERE EMPLOYEE_APPLICABLE IN (");
                buffer.append("'").append(DocumentApplicability.MANDATORY.getApplicabilityCode()).append("'");
                buffer.append(",'").append(DocumentApplicability.OPTIONAL.getApplicabilityCode()).append("')");
            } else if (userType == UserType.STUDENT) {
                buffer.append("WHERE STUDENT_APPLICABLE IN (");
                buffer.append("'").append(DocumentApplicability.MANDATORY.getApplicabilityCode()).append("'");
                buffer.append(",'").append(DocumentApplicability.OPTIONAL.getApplicabilityCode()).append("')");
            }
        }
        buffer.append("ORDER BY DOCUMENT_NAME");
        return buffer.toString();
    }

    /**
     * Builds the select query.
     * 
     * @param documentSearchCriteria the document search criteria
     * @return the string
     */
    public static String buildSelectQuery(
            DocumentSearchCriteria documentSearchCriteria) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(SELECT_ALL);
        if (documentSearchCriteria != null) {
            UserType userType = documentSearchCriteria.getApplicableFor();
            DocumentApplicability documentApplicability = documentSearchCriteria.getDocumentApplicability();
            if (userType == UserType.EMPLOYEE) {
                if (documentApplicability == null) {
                    buffer.append("WHERE EMPLOYEE_APPLICABLE IN ('");
                    buffer.append(DocumentApplicability.MANDATORY.getApplicabilityCode()).append("', '");
                    buffer.append(DocumentApplicability.OPTIONAL.getApplicabilityCode()).append("') ");
                } else {
                    buffer.append("WHERE EMPLOYEE_APPLICABLE='").append(documentApplicability.getApplicabilityCode()).append("' ");
                }
            } else if (userType == UserType.STUDENT) {
                if (documentApplicability == null) {
                    buffer.append("WHERE STUDENT_APPLICABLE IN ('");
                    buffer.append(DocumentApplicability.MANDATORY.getApplicabilityCode()).append("', '");
                    buffer.append(DocumentApplicability.OPTIONAL.getApplicabilityCode()).append("') ");
                } else {
                    buffer.append("WHERE STUDENT_APPLICABLE='").append(documentApplicability.getApplicabilityCode()).append("' ");
                }
            }
        }
        buffer.append("ORDER BY DOCUMENT_NAME");
        return buffer.toString();
    }

}
