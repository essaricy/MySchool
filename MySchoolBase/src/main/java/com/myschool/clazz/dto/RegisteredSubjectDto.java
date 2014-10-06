package com.myschool.clazz.dto;

import java.io.Serializable;


/**
 * The Class RegisteredSubjectDto.
 */
public class RegisteredSubjectDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The subject id. */
    private int subjectId;

    /** The subject. */
    private SubjectDto subject;

    /** The registered class. */
    private RegisteredClassDto registeredClass;

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
        retValue.append("RegisteredSubjectDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("registeredClass = ").append(this.registeredClass).append(SEPARATOR)
            .append("subject = ").append(this.subject).append(SEPARATOR)
            .append("subjectId = ").append(this.subjectId).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
