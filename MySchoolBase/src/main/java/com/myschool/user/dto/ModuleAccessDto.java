package com.myschool.user.dto;

import java.io.Serializable;
import java.util.List;

/**
 * The Class ModuleAccessDto.
 */
public class ModuleAccessDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The module id. */
    private int moduleId;

    /** The module name. */
    private String moduleName;

    /** The function access. */
    private List<FunctionAccessDto> functionAccess;

    /** The module accessible. */
    private boolean moduleAccessible;

    /** The all view. */
    private boolean allView;

    /** The all create. */
    private boolean allCreate;

    /** The all update. */
    private boolean allUpdate;

    /** The all delete. */
    private boolean allDelete;

    /** The admin access. */
    private boolean adminAccess;

    /** The employee access. */
    private boolean employeeAccess;

    /** The student access. */
    private boolean studentAccess;

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
     * Gets the function access.
     * 
     * @return the function access
     */
    public List<FunctionAccessDto> getFunctionAccess() {
        return functionAccess;
    }

    /**
     * Sets the function access.
     * 
     * @param functionAccess the new function access
     */
    public void setFunctionAccess(List<FunctionAccessDto> functionAccess) {
        this.functionAccess = functionAccess;
    }

    /**
     * Checks if is module accessible.
     *
     * @return true, if is module accessible
     */
    public boolean isModuleAccessible() {
        return moduleAccessible;
    }

    /**
     * Sets the module accessible.
     *
     * @param moduleAccessible the new module accessible
     */
    public void setModuleAccessible(boolean moduleAccessible) {
        this.moduleAccessible = moduleAccessible;
    }

    /**
     * Checks if is all view.
     *
     * @return true, if is all view
     */
    public boolean isAllView() {
        return allView;
    }

    /**
     * Sets the all view.
     *
     * @param allView the new all view
     */
    public void setAllView(boolean allView) {
        this.allView = allView;
    }

    /**
     * Checks if is all create.
     *
     * @return true, if is all create
     */
    public boolean isAllCreate() {
        return allCreate;
    }

    /**
     * Sets the all create.
     *
     * @param allCreate the new all create
     */
    public void setAllCreate(boolean allCreate) {
        this.allCreate = allCreate;
    }

    /**
     * Checks if is all update.
     *
     * @return true, if is all update
     */
    public boolean isAllUpdate() {
        return allUpdate;
    }

    /**
     * Sets the all update.
     *
     * @param allUpdate the new all update
     */
    public void setAllUpdate(boolean allUpdate) {
        this.allUpdate = allUpdate;
    }

    /**
     * Checks if is all delete.
     *
     * @return true, if is all delete
     */
    public boolean isAllDelete() {
        return allDelete;
    }

    /**
     * Sets the all delete.
     *
     * @param allDelete the new all delete
     */
    public void setAllDelete(boolean allDelete) {
        this.allDelete = allDelete;
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
        retValue.append("ModuleAccessDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("accessUrl = ").append(this.accessUrl).append(SEPARATOR)
            .append("adminAccess = ").append(this.adminAccess).append(SEPARATOR)
            .append("allCreate = ").append(this.allCreate).append(SEPARATOR)
            .append("allDelete = ").append(this.allDelete).append(SEPARATOR)
            .append("allUpdate = ").append(this.allUpdate).append(SEPARATOR)
            .append("allView = ").append(this.allView).append(SEPARATOR)
            .append("employeeAccess = ").append(this.employeeAccess).append(SEPARATOR)
            .append("functionAccess = ").append(this.functionAccess).append(SEPARATOR)
            .append("moduleAccessible = ").append(this.moduleAccessible).append(SEPARATOR)
            .append("moduleId = ").append(this.moduleId).append(SEPARATOR)
            .append("moduleName = ").append(this.moduleName).append(SEPARATOR)
            .append("studentAccess = ").append(this.studentAccess).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
