package com.myschool.employee.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.application.assembler.DocumentDataAssembler;
import com.myschool.common.dto.DocumentDto;
import com.myschool.common.util.ConversionUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.employee.dto.EmployeeDocument;

/**
 * The Class EmployeeDocumentDataAssembler.
 */
public class EmployeeDocumentDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the employee document
     * @throws SQLException the sQL exception
     */
    public static EmployeeDocument create(ResultSet resultSet) throws SQLException {
        EmployeeDocument employeeDocument = new EmployeeDocument();
        employeeDocument.setEmployeeDocumentId(resultSet.getInt("EMPLOYEE_DOCUMENT_ID"));
        employeeDocument.setDocument(DocumentDataAssembler.create(resultSet, true));
        employeeDocument.setDocumentExpiryDate(ConversionUtil.toApplicationDateFromStorageDate(
                resultSet.getString("DOCUMENT_EXPIRY_DATE")));
        employeeDocument.setDocumentIssuedBy(resultSet.getString("ISSUED_BY"));
        employeeDocument.setDocumentNumber(resultSet.getString("DOCUMENT_NUMBER"));
        return employeeDocument;
    }

    /**
     * Creates the.
     * 
     * @param employeeDocumentData the employee document data
     * @return the employee document
     */
    public static EmployeeDocument create(JSONObject employeeDocumentData) {
        EmployeeDocument employeeDocument = null;
        if (employeeDocumentData != null) {
            employeeDocument = new EmployeeDocument();

            String employeeDocumentId = employeeDocumentData.getString("EmployeeDocumentId");
            if (!StringUtil.isNullOrBlank(employeeDocumentId)) {
                employeeDocument.setEmployeeDocumentId(Integer.parseInt(employeeDocumentId));
            }
            DocumentDto document = new DocumentDto();
            document.setDocumentId(Integer.parseInt(employeeDocumentData.getString("DocumentId")));
            employeeDocument.setDocument(document);
            employeeDocument.setDocumentNumber(employeeDocumentData.getString("DocumentNumber"));
            employeeDocument.setDocumentExpiryDate(employeeDocumentData.getString("DocumentExpiryDate"));
            employeeDocument.setDocumentIssuedBy(employeeDocumentData.getString("DocumentIssuedBy"));
        }
        return employeeDocument;
    }

    /**
     * Creates the.
     * 
     * @param jsonObjectArray the json object array
     * @return the employee documents
     */
    public static List<EmployeeDocument> create(JSONArray jsonObjectArray) {
        List<EmployeeDocument> employeeDocuments = null;
        if (jsonObjectArray != null && jsonObjectArray.length() != 0) {
            employeeDocuments = new ArrayList<EmployeeDocument>();
            for (int index = 0; index < jsonObjectArray.length(); index++) {
                JSONObject jsonObject = (JSONObject) jsonObjectArray.get(index);
                if (jsonObject != null) {
                    employeeDocuments.add(create(jsonObject));
                }
            }
        }
        return employeeDocuments;
    }

    /**
     * Creates the.
     * 
     * @param employeeDocuments the employee documents
     * @return the jSON array
     */
    public static JSONArray create(List<EmployeeDocument> employeeDocuments) {
        JSONArray jsonArray = null;
        if (employeeDocuments != null && !employeeDocuments.isEmpty()) {
            jsonArray = new JSONArray();
            for (EmployeeDocument employeeDocument : employeeDocuments) {
                jsonArray.put(create(employeeDocument));
            }
        }
        return jsonArray;
    }

    /**
     * Creates the.
     * 
     * @param employeeDocument the employee document
     * @return the jSON array
     */
    private static JSONArray create(EmployeeDocument employeeDocument) {
        JSONArray jsonArray = null;
        if (employeeDocument != null) {
            jsonArray = new JSONArray();
            DocumentDto document = employeeDocument.getDocument();
            jsonArray.put(employeeDocument.getEmployeeDocumentId());
            jsonArray.put(document.getDocumentId());
            jsonArray.put(document.getName());
            jsonArray.put(employeeDocument.getDocumentNumber());
            jsonArray.put(StringUtil.getJsonValue(employeeDocument.getDocumentExpiryDate()));
            jsonArray.put(StringUtil.getJsonValue(employeeDocument.getDocumentIssuedBy()));
        }
        return jsonArray;
    }

}
