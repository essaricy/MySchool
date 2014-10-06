package com.myschool.exam.dto;

import java.io.Serializable;

import com.myschool.clazz.dto.RegisteredSubjectDto;

/**
 * The Class SubjectExamDto.
 */
public class SubjectExamDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The subject exam id. */
    private int subjectExamId;

    /** The exam. */
    private ExamDto exam;

    /** The registered subject. */
    private RegisteredSubjectDto registeredSubject;

    /** The maximum marks. */
    private int maximumMarks;

    /**
     * Gets the subject exam id.
     *
     * @return the subject exam id
     */
    public int getSubjectExamId() {
        return subjectExamId;
    }

    /**
     * Sets the subject exam id.
     *
     * @param subjectExamId the new subject exam id
     */
    public void setSubjectExamId(int subjectExamId) {
        this.subjectExamId = subjectExamId;
    }

    /**
     * Gets the exam.
     *
     * @return the exam
     */
    public ExamDto getExam() {
        return exam;
    }

    /**
     * Sets the exam.
     *
     * @param exam the new exam
     */
    public void setExam(ExamDto exam) {
        this.exam = exam;
    }

    /**
     * Gets the maximum marks.
     *
     * @return the maximum marks
     */
    public int getMaximumMarks() {
        return maximumMarks;
    }

    /**
     * Sets the maximum marks.
     *
     * @param maximumMarks the new maximum marks
     */
    public void setMaximumMarks(int maximumMarks) {
        this.maximumMarks = maximumMarks;
    }

    /**
     * Gets the registered subject.
     *
     * @return the registered subject
     */
    public RegisteredSubjectDto getRegisteredSubject() {
        return registeredSubject;
    }

    /**
     * Sets the registered subject.
     *
     * @param registeredSubject the new registered subject
     */
    public void setRegisteredSubject(RegisteredSubjectDto registeredSubject) {
        this.registeredSubject = registeredSubject;
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
        retValue.append("SubjectExamDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("exam = ").append(this.exam).append(SEPARATOR)
            .append("maximumMarks = ").append(this.maximumMarks).append(SEPARATOR)
            .append("registeredSubject = ").append(this.registeredSubject).append(SEPARATOR)
            .append("subjectExamId = ").append(this.subjectExamId).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
