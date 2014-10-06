package com.myschool.exam.dto;

import java.io.Serializable;
import java.util.List;

import com.myschool.clazz.dto.RegisteredClassDto;

/**
 * The Class ExamDto.
 */
public class ExamDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The exam id. */
    private int examId;

    /** The exam name. */
    private String examName;

    /** The exam date. */
    private String examDate;

    /** The exam completed. */
    private boolean examCompleted;

    /** The registered class. */
    private RegisteredClassDto registeredClass;

    /** The subject exams. */
    private List<SubjectExamDto> subjectExams;

    /**
     * Gets the exam id.
     *
     * @return the exam id
     */
    public int getExamId() {
        return examId;
    }

    /**
     * Sets the exam id.
     *
     * @param examId the new exam id
     */
    public void setExamId(int examId) {
        this.examId = examId;
    }

    /**
     * Gets the exam name.
     *
     * @return the exam name
     */
    public String getExamName() {
        return examName;
    }

    /**
     * Sets the exam name.
     *
     * @param examName the new exam name
     */
    public void setExamName(String examName) {
        this.examName = examName;
    }

    /**
     * Gets the exam date.
     *
     * @return the exam date
     */
    public String getExamDate() {
        return examDate;
    }

    /**
     * Sets the exam date.
     *
     * @param examDate the new exam date
     */
    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    /**
     * Checks if is exam completed.
     *
     * @return true, if is exam completed
     */
    public boolean isExamCompleted() {
        return examCompleted;
    }

    /**
     * Sets the exam completed.
     *
     * @param examCompleted the new exam completed
     */
    public void setExamCompleted(boolean examCompleted) {
        this.examCompleted = examCompleted;
    }

    /**
     * Gets the subject exams.
     *
     * @return the subject exams
     */
    public List<SubjectExamDto> getSubjectExams() {
        return subjectExams;
    }

    /**
     * Sets the subject exams.
     *
     * @param subjectExams the new subject exams
     */
    public void setSubjectExams(List<SubjectExamDto> subjectExams) {
        this.subjectExams = subjectExams;
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
        retValue.append("ExamDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("examCompleted = ").append(this.examCompleted).append(SEPARATOR)
            .append("examDate = ").append(this.examDate).append(SEPARATOR)
            .append("examId = ").append(this.examId).append(SEPARATOR)
            .append("examName = ").append(this.examName).append(SEPARATOR)
            .append("registeredClass = ").append(this.registeredClass).append(SEPARATOR)
            .append("subjectExams = ").append(this.subjectExams).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
