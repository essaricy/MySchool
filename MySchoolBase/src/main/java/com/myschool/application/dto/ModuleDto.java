package com.myschool.application.dto;

import java.io.Serializable;

/**
 * The Class ModuleDto.
 */
public class ModuleDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The module id. */
    private int moduleId;

    /** The admin access. */
    private boolean adminAccess;

    /** The employee access. */
    private boolean employeeAccess;

    /** The student access. */
    private boolean studentAccess;

    /** The module name. */
    private String moduleName;

    /** The access url. */
    private String accessUrl;

    /**
     * Gets the module id.
     * 
     * @return the module id
     */
    public int getModuleId() {
        return moduleId;
    }

    /**
     * Sets the module id.
     * 
     * @param moduleId the new module id
     */
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * Checks if is admin access.
     *
     * @return true, if is admin access
     */
    public boolean isAdminAccess() {
        return adminAccess;
    }

    /**
     * Sets the admin access.
     *
     * @param adminAccess the new admin access
     */
    public void setAdminAccess(boolean adminAccess) {
        this.adminAccess = adminAccess;
    }

    /**
     * Checks if is employee access.
     *
     * @return true, if is employee access
     */
    public boolean isEmployeeAccess() {
        return employeeAccess;
    }

    /**
     * Sets the employee access.
     *
     * @param employeeAccess the new employee access
     */
    public void setEmployeeAccess(boolean employeeAccess) {
        this.employeeAccess = employeeAccess;
    }

    /**
     * Checks if is student access.
     *
     * @return true, if is student access
     */
    public boolean isStudentAccess() {
        return studentAccess;
    }

    /**
     * Sets the student access.
     *
     * @param studentAccess the new student access
     */
    public void setStudentAccess(boolean studentAccess) {
        this.studentAccess = studentAccess;
    }

    /**
     * Gets the module name.
     * 
     * @return the module name
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * Sets the module name.
     * 
     * @param moduleName the new module name
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * Gets the access url.
     *
     * @return the access url
     */
    public String getAccessUrl() {
        return accessUrl;
    }

    /**
     * Sets the access url.
     *
     * @param accessUrl the new access url
     */
    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
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
        retValue.append("ModuleDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("accessUrl = ").append(this.accessUrl).append(SEPARATOR)
            .append("adminAccess = ").append(this.adminAccess).append(SEPARATOR)
            .append("employeeAccess = ").append(this.employeeAccess).append(SEPARATOR)
            .append("moduleId = ").append(this.moduleId).append(SEPARATOR)
            .append("moduleName = ").append(this.moduleName).append(SEPARATOR)
            .append("studentAccess = ").append(this.studentAccess).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
