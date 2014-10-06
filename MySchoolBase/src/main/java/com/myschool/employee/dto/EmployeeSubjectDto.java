/*
 * 
 */
package com.myschool.employee.dto;

import java.io.Serializable;

import com.myschool.clazz.dto.RegisteredSubjectDto;

/**
 * The Class EmployeeSubjectDto.
 */
public class EmployeeSubjectDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The employee subject id. */
    private int employeeSubjectId;

    /** The employee. */
    private EmployeeDto employee;

    /** The registered subject. */
    private RegisteredSubjectDto registeredSubject;

    /**
     * Gets the employee subject id.
     * 
     * @return the employee subject id
     */
    public int getEmployeeSubjectId() {
        return employeeSubjectId;
    }

    /**
     * Sets the employee subject id.
     * 
     * @param employeeSubjectId the new employee subject id
     */
    public void setEmployeeSubjectId(int employeeSubjectId) {
        this.employeeSubjectId = employeeSubjectId;
    }

    /**
     * Gets the employee.
     *
     * @return the employee
     */
    public EmployeeDto getEmployee() {
        return employee;
    }

    /**
     * Sets the employee.
     *
     * @param employee the new employee
     */
    public void setEmployee(EmployeeDto employee) {
        this.employee = employee;
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
        retValue.append("EmployeeSubjectDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("employeeSubjectId = ").append(this.employeeSubjectId).append(SEPARATOR)
            .append("employee = ").append(this.employee).append(SEPARATOR)
            .append("registeredSubject = ").append(this.registeredSubject).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
