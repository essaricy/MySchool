package com.myschool.clazz.dto;

import java.io.Serializable;

/**
 * The Class SubjectDto.
 */
public class SubjectDto implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The subject id. */
    private int subjectId;
    
    /** The subject name. */
    private String subjectName;
    
    /**
     * Gets the subject id.
     *
     * @return the subject id
     */
    public int getSubjectId() {
        return subjectId;
    }
    
    /**
     * Sets the subject id.
     *
     * @param subjectId the new subject id
     */
    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
    
    /**
     * Gets the subject name.
     *
     * @return the subject name
     */
    public String getSubjectName() {
        return subjectName;
    }
    
    /**
     * Sets the subject name.
     *
     * @param subjectName the new subject name
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + subjectId;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SubjectDto other = (SubjectDto) obj;
        if (subjectId != other.subjectId) {
            return false;
        }
        return true;
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
        retValue.append("SubjectDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("subjectId = ").append(this.subjectId).append(SEPARATOR)
            .append("subjectName = ").append(this.subjectName).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
