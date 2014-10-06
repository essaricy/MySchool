package com.myschool.student.dto;

import java.io.Serializable;

import com.myschool.exam.dto.ExamDto;
import com.myschool.exam.dto.StudentInExamDto;

/**
 * The Class StudentPerformaceDto.
 */
public class StudentPerformaceDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The exam. */
    private ExamDto exam;

    /** The student exams. */
    private StudentInExamDto studentInExam;

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
     * Gets the student in exam.
     *
     * @return the student in exam
     */
    public StudentInExamDto getStudentInExam() {
        return studentInExam;
    }

    /**
     * Sets the student in exam.
     *
     * @param studentInExam the new student in exam
     */
    public void setStudentInExam(StudentInExamDto studentInExam) {
        this.studentInExam = studentInExam;
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
        retValue.append("StudentPerformaceDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("exam = ").append(this.exam).append(SEPARATOR)
            .append("studentInExam = ").append(this.studentInExam).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

    
}