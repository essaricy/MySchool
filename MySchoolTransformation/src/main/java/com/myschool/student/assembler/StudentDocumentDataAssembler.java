package com.myschool.student.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.application.assembler.DocumentDataAssembler;
import com.myschool.common.dto.DocumentDto;
import com.myschool.common.util.ConversionUtil;
import com.myschool.common.util.JsonUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.student.dto.StudentDocument;

/**
 * The Class StudentDocumentDataAssembler.
 */
public class StudentDocumentDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the student document
     * @throws SQLException the sQL exception
     */
    public static StudentDocument create(ResultSet resultSet) throws SQLException {
        StudentDocument studentDocument = new StudentDocument();
        studentDocument.setStudentDocumentId(resultSet.getInt("STUDENT_DOCUMENT_ID"));
        studentDocument.setDocument(DocumentDataAssembler.create(resultSet, true));
        studentDocument.setDocumentExpiryDate(ConversionUtil.toApplicationDateFromStorageDate(
                resultSet.getString("DOCUMENT_EXPIRY_DATE")));
        studentDocument.setDocumentIssuedBy(resultSet.getString("ISSUED_BY"));
        studentDocument.setDocumentNumber(resultSet.getString("DOCUMENT_NUMBER"));
        return studentDocument;
    }

    /**
     * Creates the.
     * 
     * @param studentDocumentData the student document data
     * @return the student document
     */
    public static StudentDocument create(JSONObject studentDocumentData) {
        StudentDocument studentDocument = null;
        if (studentDocumentData != null) {
            studentDocument = new StudentDocument();
            studentDocument.setStudentDocumentId(JsonUtil.getInt(studentDocumentData, "StudentDocumentId"));
            DocumentDto document = new DocumentDto();
            document.setDocumentId(JsonUtil.getInt(studentDocumentData, "DocumentId"));
            studentDocument.setDocument(document);
            studentDocument.setDocumentNumber(JsonUtil.getString(studentDocumentData, "DocumentNumber"));
            studentDocument.setDocumentExpiryDate(JsonUtil.getString(studentDocumentData, "DocumentExpiryDate"));
            studentDocument.setDocumentIssuedBy(JsonUtil.getString(studentDocumentData, "DocumentIssuedBy"));
        }
        return studentDocument;
    }

    /**
     * Creates the.
     * 
     * @param jsonObjectArray the json object array
     * @return the list
     */
    public static List<StudentDocument> create(JSONArray jsonObjectArray) {
        List<StudentDocument> studentDocuments = null;
        if (jsonObjectArray != null && jsonObjectArray.length() != 0) {
            studentDocuments = new ArrayList<StudentDocument>();
            for (int index = 0; index < jsonObjectArray.length(); index++) {
                JSONObject jsonObject = (JSONObject) jsonObjectArray.get(index);
                if (jsonObject != null) {
                    studentDocuments.add(create(jsonObject));
                }
            }
        }
        return studentDocuments;
    }

    /**
     * Creates the.
     * 
     * @param studentDocuments the student documents
     * @return the jSON array
     */
    public static JSONArray create(List<StudentDocument> studentDocuments) {
        JSONArray jsonArray = null;
        if (studentDocuments != null && !studentDocuments.isEmpty()) {
            jsonArray = new JSONArray();
            for (StudentDocument studentDocument : studentDocuments) {
                jsonArray.put(create(studentDocument));
            }
        }
        return jsonArray;
    }

    /**
     * Creates the.
     * 
     * @param studentDocument the student document
     * @return the jSON array
     */
    private static JSONArray create(StudentDocument studentDocument) {
        JSONArray jsonArray = null;
        if (studentDocument != null) {
            jsonArray = new JSONArray();
            DocumentDto document = studentDocument.getDocument();
            jsonArray.put(studentDocument.getStudentDocumentId());
            jsonArray.put(document.getDocumentId());
            jsonArray.put(document.getName());
            jsonArray.put(studentDocument.getDocumentNumber());
            jsonArray.put(StringUtil.getJsonValue(studentDocument.getDocumentExpiryDate()));
            jsonArray.put(StringUtil.getJsonValue(studentDocument.getDocumentIssuedBy()));
        }
        return jsonArray;
    }

}
