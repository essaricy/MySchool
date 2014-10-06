package com.myschool.exam.dto;

import java.io.Serializable;

import com.myschool.student.dto.StudentDto;

/**
 * The Class StudentExamDto.
 */
public class StudentExamDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The student exam id. */
    private int studentExamId;

    /** The obtained marks. */
    private int obtainedMarks;

    /** The subject exam. */
    private SubjectExamDto subjectExam;

    /** The student. */
    private StudentDto student;

    /** The subject exam id. */
    private int subjectExamId;

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
     * Gets the subject exam.
     *
     * @return the subject exam
     */
    public SubjectExamDto getSubjectExam() {
        return subjectExam;
    }

    /**
     * Sets the subject exam.
     *
     * @param subjectExam the new subject exam
     */
    public void setSubjectExam(SubjectExamDto subjectExam) {
        this.subjectExam = subjectExam;
    }

    /**
     * Gets the student exam id.
     *
     * @return the student exam id
     */
    public int getStudentExamId() {
        return studentExamId;
    }

    /**
     * Sets the student exam id.
     *
     * @param studentExamId the new student exam id
     */
    public void setStudentExamId(int studentExamId) {
        this.studentExamId = studentExamId;
    }

    /**
     * Gets the obtained marks.
     *
     * @return the obtained marks
     */
    public int getObtainedMarks() {
        return obtainedMarks;
    }

    /**
     * Sets the obtained marks.
     *
     * @param obtainedMarks the new obtained marks
     */
    public void setObtainedMarks(int obtainedMarks) {
        this.obtainedMarks = obtainedMarks;
    }

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
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("StudentExamDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("studentExamId = ").append(this.studentExamId).append(SEPARATOR)
            .append("obtainedMarks = ").append(this.obtainedMarks).append(SEPARATOR)
            .append("subjectExam = ").append(this.subjectExam).append(SEPARATOR)
            .append("student = ").append(this.student).append(SEPARATOR)
            .append("subjectExamId = ").append(this.subjectExamId).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
