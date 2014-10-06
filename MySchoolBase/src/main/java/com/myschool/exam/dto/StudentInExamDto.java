package com.myschool.exam.dto;

import java.io.Serializable;
import java.util.List;

import com.myschool.student.dto.StudentDto;

/**
 * The Class StudentInExamDto.
 */
public class StudentInExamDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The student. */
    private StudentDto student;

    /** The student exams. */
    private List<StudentExamDto> studentExams;

    /** The total marks. */
    private int totalMarks;

    /** The percentage. */
    private double percentage;

    /** The grade. */
    private String grade;

    /**
     * Gets the student.
     *
     * @return the student
     */
    public StudentDto getStudent() {
        return student;
    }

    /**
     * Sets the student.
     *
     * @param student the new student
     */
    public void setStudent(StudentDto student) {
        this.student = student;
    }

    /**
     * Gets the student exams.
     *
     * @return the student exams
     */
    public List<StudentExamDto> getStudentExams() {
        return studentExams;
    }

    /**
     * Sets the student exams.
     *
     * @param studentExams the new student exams
     */
    public void setStudentExams(List<StudentExamDto> studentExams) {
        this.studentExams = studentExams;
    }

    /**
     * Gets the total marks.
     *
     * @return the total marks
     */
    public int getTotalMarks() {
        return totalMarks;
    }

    /**
     * Sets the total marks.
     *
     * @param totalMarks the new total marks
     */
    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    /**
     * Gets the percentage.
     *
     * @return the percentage
     */
    public double getPercentage() {
        return percentage;
    }

    /**
     * Sets the percentage.
     *
     * @param percentage the new percentage
     */
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    /**
     * Gets the grade.
     *
     * @return the grade
     */
    public String getGrade() {
        return grade;
    }

    /**
     * Sets the grade.
     *
     * @param grade the new grade
     */
    public void setGrade(String grade) {
        this.grade = grade;
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
        retValue.append("StudentInExamDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("percentage = ").append(this.percentage).append(SEPARATOR)
            .append("grade = ").append(this.grade).append(SEPARATOR)
            .append("student = ").append(this.student).append(SEPARATOR)
            .append("studentExams = ").append(this.studentExams).append(SEPARATOR)
            .append("totalMarks = ").append(this.totalMarks).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
