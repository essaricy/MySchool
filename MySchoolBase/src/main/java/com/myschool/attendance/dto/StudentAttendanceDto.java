package com.myschool.attendance.dto;

import java.io.Serializable;

import com.myschool.student.dto.StudentDto;

/**
 * The Class StudentAttendanceDto.
 */
public class StudentAttendanceDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The student. */
    private StudentDto student;

    /** The attendance. */
    private AttendanceDto attendance;

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
     * Gets the attendance.
     *
     * @return the attendance
     */
    public AttendanceDto getAttendance() {
        return attendance;
    }

    /**
     * Sets the attendance.
     *
     * @param attendance the new attendance
     */
    public void setAttendance(AttendanceDto attendance) {
        this.attendance = attendance;
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
        retValue.append("StudentAttendanceDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("attendance = ").append(this.attendance).append(SEPARATOR)
            .append("student = ").append(this.student).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
