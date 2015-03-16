package com.myschool.application.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONObject;

import com.myschool.common.constants.DocumentApplicability;
import com.myschool.common.dto.DocumentDto;
import com.myschool.common.dto.DocumentSearchCriteria;
import com.myschool.report.assembler.ReportDataAssembler;
import com.myschool.report.constants.ReportCriteriaTokenConstants;
import com.myschool.report.dto.ReportCriteriaToken;
import com.myschool.user.constants.UserType;

/**
 * The Class DocumentDataAssembler.
 */
public class DocumentDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the document dto
     * @throws SQLException the sQL exception
     */
    public static DocumentDto create(ResultSet resultSet) throws SQLException {
        return create(resultSet, false);
    }

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @param aliased the aliased
     * @return the document dto
     * @throws SQLException the sQL exception
     */
    public static DocumentDto create(ResultSet resultSet, boolean aliased)
            throws SQLException {
        DocumentDto document = new DocumentDto();
        if (aliased) {
            document.setApplicabilityForEmployee(
                    DocumentApplicability.getByCode(
                            resultSet.getString("DOCUMENT_EMPLOYEE_APPLICABLE")));
            document.setApplicabilityForStudent(
                    DocumentApplicability.getByCode(
                            resultSet.getString("DOCUMENT_STUDENT_APPLICABLE")));
            document.setDescription(resultSet.getString("DOCUMENT_DESCRIPTION"));
            document.setDocumentId(resultSet.getInt("DOCUMENT_DOCUMENT_ID"));
            document.setName(resultSet.getString("DOCUMENT_NAME"));
        } else {
            document.setApplicabilityForEmployee(
                    DocumentApplicability.getByCode(resultSet.getString("EMPLOYEE_APPLICABLE")));
            document.setApplicabilityForStudent(
                    DocumentApplicability.getByCode(resultSet.getString("STUDENT_APPLICABLE")));
            document.setDescription(resultSet.getString("DESCRIPTION"));
            document.setDocumentId(resultSet.getInt("DOCUMENT_ID"));
            document.setName(resultSet.getString("DOCUMENT_NAME"));
        }
        return document;
    }

    /**
     * Creates the.
     * 
     * @param document the document
     * @return the jSON object
     */
    public static JSONObject create(DocumentDto document) {
        JSONObject jsonObject = null;
        if (document != null) {
            jsonObject = new JSONObject();
            jsonObject.put("DocumentId", document.getDocumentId());
            jsonObject.put("Name", document.getName());
            jsonObject.put("Description", document.getDescription());
            jsonObject.put("ApplicabilityForEmployee", document.getApplicabilityForEmployee().toString());
            jsonObject.put("ApplicabilityForStudent", document.getApplicabilityForStudent().toString());
        }
        return jsonObject;
    }

    /**
     * Creates the.
     * 
     * @param reportCriteriaValues the report criteria values
     * @return the document search criteria
     */
    public static DocumentSearchCriteria create(
            Map<ReportCriteriaToken, String> reportCriteriaValues) {
        DocumentSearchCriteria documentSearchCriteria = null;
        if (reportCriteriaValues != null && !reportCriteriaValues.isEmpty()) {
            documentSearchCriteria = new DocumentSearchCriteria();
            documentSearchCriteria.setApplicableFor(
                    UserType.get(ReportDataAssembler.getString(
                            reportCriteriaValues, ReportCriteriaTokenConstants.APPLICABLE_FOR)));
            documentSearchCriteria.setDocumentApplicability(
                    DocumentApplicability.getByCode(
                            ReportDataAssembler.getString(
                                    reportCriteriaValues, ReportCriteriaTokenConstants.DOCUMENT_APPLICABILITY)));
        }
        return documentSearchCriteria;
    }

}
