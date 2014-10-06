package com.myschool.student.dto;

import java.io.Serializable;

import com.myschool.common.dto.DocumentDto;

/**
 * The Class StudentDocument.
 */
public class StudentDocument implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The student document id. */
    private int studentDocumentId;

    /** The document. */
    private DocumentDto document;

    /** The document number. */
    private String documentNumber;

    /** The document expiry date. */
    private String documentExpiryDate;

    /** The document issued by. */
    private String documentIssuedBy;

    /** The student. */
    private StudentDto student;

    /**
     * Gets the student document id.
     * 
     * @return the student document id
     */
    public int getStudentDocumentId() {
        return studentDocumentId;
    }

    /**
     * Sets the student document id.
     * 
     * @param studentDocumentId the new student document id
     */
    public void setStudentDocumentId(int studentDocumentId) {
        this.studentDocumentId = studentDocumentId;
    }

    /**
     * Gets the document.
     * 
     * @return the document
     */
    public DocumentDto getDocument() {
        return document;
    }

    /**
     * Sets the document.
     * 
     * @param document the new document
     */
    public void setDocument(DocumentDto document) {
        this.document = document;
    }

    /**
     * Gets the document number.
     * 
     * @return the document number
     */
    public String getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Sets the document number.
     * 
     * @param documentNumber the new document number
     */
    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    /**
     * Gets the document expiry date.
     * 
     * @return the document expiry date
     */
    public String getDocumentExpiryDate() {
        return documentExpiryDate;
    }

    /**
     * Sets the document expiry date.
     * 
     * @param documentExpiryDate the new document expiry date
     */
    public void setDocumentExpiryDate(String documentExpiryDate) {
        this.documentExpiryDate = documentExpiryDate;
    }

    /**
     * Gets the document issued by.
     * 
     * @return the document issued by
     */
    public String getDocumentIssuedBy() {
        return documentIssuedBy;
    }

    /**
     * Sets the document issued by.
     * 
     * @param documentIssuedBy the new document issued by
     */
    public void setDocumentIssuedBy(String documentIssuedBy) {
        this.documentIssuedBy = documentIssuedBy;
    }

    /**
     * Gets the student.
     * 
     * @return the student
     */
    public StudentDto getStudent() {
        return student;
    }

    /**
     * Sets the student.
     * 
     * @param student the new student
     */
    public void setStudent(StudentDto student) {
        this.student = student;
    }

    /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("StudentDocument ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("studentDocumentId = ").append(this.studentDocumentId).append(SEPARATOR)
            .append("document = ").append(this.document).append(SEPARATOR)
            .append("documentNumber = ").append(this.documentNumber).append(SEPARATOR)
            .append("documentExpiryDate = ").append(this.documentExpiryDate).append(SEPARATOR)
            .append("documentIssuedBy = ").append(this.documentIssuedBy).append(SEPARATOR)
            .append("student = ").append(this.student).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
