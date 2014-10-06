package com.myschool.clazz.dto;

import java.io.Serializable;


/**
 * The Class SubjectClassDto.
 */
public class SubjectClassDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The subject class id. */
    private int subjectClassId;

    /** The subject. */
    private SubjectDto subject;

    /** The registered class. */
    private RegisteredClassDto registeredClass;

    /**
     * Gets the subject class id.
     *
     * @return the subject class id
     */
    public int getSubjectClassId() {
        return subjectClassId;
    }

    /**
     * Sets the subject class id.
     *
     * @param subjectClassId the new subject class id
     */
    public void setSubjectClassId(int subjectClassId) {
        this.subjectClassId = subjectClassId;
    }

    /**
     * Gets the subject.
     *
     * @return the subject
     */
    public SubjectDto getSubject() {
        return subject;
    }

    /**
     * Sets the subject.
     *
     * @param subject the new subject
     */
    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }

    /**
     * Gets the registered class.
     *
     * @return the registered class
     */
    public RegisteredClassDto getRegisteredClass() {
        return registeredClass;
    }

    /**
     * Sets the registered class.
     *
     * @param registeredClass the new registered class
     */
    public void setRegisteredClass(RegisteredClassDto registeredClass) {
        this.registeredClass = registeredClass;
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
        retValue.append("SubjectClassDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("registeredClass = ").append(this.registeredClass).append(SEPARATOR)
            .append("subject = ").append(this.subject).append(SEPARATOR)
            .append("subjectClassId = ").append(this.subjectClassId).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
