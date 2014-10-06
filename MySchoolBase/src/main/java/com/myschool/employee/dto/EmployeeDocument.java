package com.myschool.employee.dto;

import java.io.Serializable;

import com.myschool.common.dto.DocumentDto;

/**
 * The Class EmployeeDocument.
 */
public class EmployeeDocument implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The employee document id. */
    private int employeeDocumentId;

    /** The document. */
    private DocumentDto document;

    /** The document number. */
    private String documentNumber;

    /** The document expiry date. */
    private String documentExpiryDate;

    /** The document issued by. */
    private String documentIssuedBy;

    /** The employee. */
    private EmployeeDto employee;

    /**
     * Gets the employee document id.
     * 
     * @return the employee document id
     */
    public int getEmployeeDocumentId() {
        return employeeDocumentId;
    }

    /**
     * Sets the employee document id.
     * 
     * @param employeeDocumentId the new employee document id
     */
    public void setEmployeeDocumentId(int employeeDocumentId) {
        this.employeeDocumentId = employeeDocumentId;
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
     * Gets the employee.
     * 
     * @return the employee
     */
    public EmployeeDto getEmployee() {
        return employee;
    }

    /**
     * Sets the employee.
     * 
     * @param employee the new employee
     */
    public void setEmployee(EmployeeDto employee) {
        this.employee = employee;
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
        retValue.append("EmployeeDocument ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("employeeDocumentId = ").append(this.employeeDocumentId).append(SEPARATOR)
            .append("document = ").append(this.document).append(SEPARATOR)
            .append("documentNumber = ").append(this.documentNumber).append(SEPARATOR)
            .append("documentExpiryDate = ").append(this.documentExpiryDate).append(SEPARATOR)
            .append("documentIssuedBy = ").append(this.documentIssuedBy).append(SEPARATOR)
            .append("employee = ").append(this.employee).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
