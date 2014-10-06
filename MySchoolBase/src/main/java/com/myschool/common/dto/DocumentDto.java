package com.myschool.common.dto;

import java.io.Serializable;

import com.myschool.common.constants.DocumentApplicability;

/**
 * The Class DocumentDto.
 */
public class DocumentDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The document id. */
    private int documentId;

    /** The name. */
    private String name;

    /** The description. */
    private String description;

    /** The applicability for employee. */
    private DocumentApplicability applicabilityForEmployee;

    /** The applicability for student. */
    private DocumentApplicability applicabilityForStudent;

    /**
     * Gets the document id.
     * 
     * @return the document id
     */
    public int getDocumentId() {
        return documentId;
    }

    /**
     * Sets the document id.
     * 
     * @param documentId the new document id
     */
    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * 
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the applicability for employee.
     * 
     * @return the applicability for employee
     */
    public DocumentApplicability getApplicabilityForEmployee() {
        return applicabilityForEmployee;
    }

    /**
     * Sets the applicability for employee.
     * 
     * @param applicabilityForEmployee the new applicability for employee
     */
    public void setApplicabilityForEmployee(
            DocumentApplicability applicabilityForEmployee) {
        this.applicabilityForEmployee = applicabilityForEmployee;
    }

    /**
     * Gets the applicability for student.
     * 
     * @return the applicability for student
     */
    public DocumentApplicability getApplicabilityForStudent() {
        return applicabilityForStudent;
    }

    /**
     * Sets the applicability for student.
     * 
     * @param applicabilityForStudent the new applicability for student
     */
    public void setApplicabilityForStudent(
            DocumentApplicability applicabilityForStudent) {
        this.applicabilityForStudent = applicabilityForStudent;
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
        retValue.append("DocumentDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("documentId = ").append(this.documentId).append(SEPARATOR)
            .append("name = ").append(this.name).append(SEPARATOR)
            .append("description = ").append(this.description).append(SEPARATOR)
            .append("applicabilityForEmployee = ").append(this.applicabilityForEmployee).append(SEPARATOR)
            .append("applicabilityForStudent = ").append(this.applicabilityForStudent).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
