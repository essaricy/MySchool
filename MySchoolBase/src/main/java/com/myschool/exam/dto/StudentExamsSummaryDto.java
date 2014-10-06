package com.myschool.exam.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.myschool.student.dto.StudentDto;

/**
 * The Class StudentExamsSummaryDto.
 */
public class StudentExamsSummaryDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The student. */
    private StudentDto student;

    /** The exams. */
    private List<ExamDto> exams;

    /** The subject exams. */
    private List<SubjectExamDto> subjectExams;

    /** The student subject exam marks. */
    private Map<SubjectExamDto, List<StudentExamDto>> studentSubjectExamMarks;

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
     * Gets the exams.
     * 
     * @return the exams
     */
    public List<ExamDto> getExams() {
        return exams;
    }

    /**
     * Sets the exams.
     * 
     * @param exams the new exams
     */
    public void setExams(List<ExamDto> exams) {
        this.exams = exams;
    }

    /**
     * Gets the student subject exam marks.
     * 
     * @return the student subject exam marks
     */
    public Map<SubjectExamDto, List<StudentExamDto>> getStudentSubjectExamMarks() {
        return studentSubjectExamMarks;
    }

    /**
     * Sets the student subject exam marks.
     * 
     * @param studentSubjectExamMarks the student subject exam marks
     */
    public void setStudentSubjectExamMarks(
            Map<SubjectExamDto, List<StudentExamDto>> studentSubjectExamMarks) {
        this.studentSubjectExamMarks = studentSubjectExamMarks;
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
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("StudentExamsSummaryDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("student = ").append(this.student).append(SEPARATOR)
            .append("exams = ").append(this.exams).append(SEPARATOR)
            .append("subjectExams = ").append(this.subjectExams).append(SEPARATOR)
            .append("studentSubjectExamMarks = ").append(this.studentSubjectExamMarks).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
