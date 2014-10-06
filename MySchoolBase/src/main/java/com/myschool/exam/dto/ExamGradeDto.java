package com.myschool.exam.dto;

import java.io.Serializable;

/**
 * The Class ExamGradeDto.
 */
public class ExamGradeDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The exam grade id. */
    private int examGradeId;

    /** The grade name. */
    private String gradeName;

    /** The qualifying percentage. */
    private int qualifyingPercentage;

    /**
     * Gets the exam grade id.
     *
     * @return the exam grade id
     */
    public int getExamGradeId() {
        return examGradeId;
    }

    /**
     * Sets the exam grade id.
     *
     * @param examGradeId the new exam grade id
     */
    public void setExamGradeId(int examGradeId) {
        this.examGradeId = examGradeId;
    }

    /**
     * Gets the grade name.
     *
     * @return the grade name
     */
    public String getGradeName() {
        return gradeName;
    }

    /**
     * Sets the grade name.
     *
     * @param gradeName the new grade name
     */
    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    /**
     * Gets the qualifying percentage.
     *
     * @return the qualifying percentage
     */
    public int getQualifyingPercentage() {
        return qualifyingPercentage;
    }

    /**
     * Sets the qualifying percentage.
     *
     * @param qualifyingPercentage the new qualifying percentage
     */
    public void setQualifyingPercentage(int qualifyingPercentage) {
        this.qualifyingPercentage = qualifyingPercentage;
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
        retValue.append("ExamGradeDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("examGradeId = ").append(this.examGradeId).append(SEPARATOR)
            .append("gradeName = ").append(this.gradeName).append(SEPARATOR)
            .append("qualifyingPercentage = ").append(this.qualifyingPercentage).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
