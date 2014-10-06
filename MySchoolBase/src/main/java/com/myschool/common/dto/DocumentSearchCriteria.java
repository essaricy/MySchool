package com.myschool.common.dto;

import java.io.Serializable;

import com.myschool.common.constants.DocumentApplicability;
import com.myschool.user.constants.UserType;

/**
 * The Class DocumentSearchCriteria.
 */
public class DocumentSearchCriteria implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The applicable for. */
    private UserType applicableFor;

    /** The applicability code. */
    private DocumentApplicability documentApplicability;

    /**
     * Gets the applicable for.
     * 
     * @return the applicable for
     */
    public UserType getApplicableFor() {
        return applicableFor;
    }

    /**
     * Sets the applicable for.
     * 
     * @param applicableFor the new applicable for
     */
    public void setApplicableFor(UserType applicableFor) {
        this.applicableFor = applicableFor;
    }

    /**
     * Gets the document applicability.
     * 
     * @return the document applicability
     */
    public DocumentApplicability getDocumentApplicability() {
        return documentApplicability;
    }

    /**
     * Sets the document applicability.
     * 
     * @param documentApplicability the new document applicability
     */
    public void setDocumentApplicability(DocumentApplicability documentApplicability) {
        this.documentApplicability = documentApplicability;
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
        retValue.append("DocumentSearchCriteria ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("applicableFor = ").append(this.applicableFor).append(SEPARATOR)
            .append("documentApplicability = ").append(this.documentApplicability).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
