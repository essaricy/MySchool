package com.myschool.academic.dto;

import java.io.Serializable;
import java.util.List;

import com.myschool.exam.dto.ExamGradeDto;

/**
 * The Class AcademicYearClosureDto.
 */
public class AcademicYearClosureDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The current academic. */
    private AcademicDto currentAcademic;

    /** The next academic. */
    private AcademicDto nextAcademic;

    /** The exam grades. */
    private List<ExamGradeDto> examGrades;

    /**
     * Gets the current academic.
     * 
     * @return the current academic
     */
    public AcademicDto getCurrentAcademic() {
        return currentAcademic;
    }

    /**
     * Sets the current academic.
     * 
     * @param currentAcademic the new current academic
     */
    public void setCurrentAcademic(AcademicDto currentAcademic) {
        this.currentAcademic = currentAcademic;
    }

    /**
     * Gets the next academic.
     * 
     * @return the next academic
     */
    public AcademicDto getNextAcademic() {
        return nextAcademic;
    }

    /**
     * Sets the next academic.
     * 
     * @param nextAcademic the new next academic
     */
    public void setNextAcademic(AcademicDto nextAcademic) {
        this.nextAcademic = nextAcademic;
    }

    /**
     * Gets the exam grades.
     * 
     * @return the exam grades
     */
    public List<ExamGradeDto> getExamGrades() {
        return examGrades;
    }

    /**
     * Sets the exam grades.
     * 
     * @param examGrades the new exam grades
     */
    public void setExamGrades(List<ExamGradeDto> examGrades) {
        this.examGrades = examGrades;
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
        retValue.append("AcademicYearClosureDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("currentAcademic = ").append(this.currentAcademic).append(SEPARATOR)
            .append("nextAcademic = ").append(this.nextAcademic).append(SEPARATOR)
            .append("examGrades = ").append(this.examGrades).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
