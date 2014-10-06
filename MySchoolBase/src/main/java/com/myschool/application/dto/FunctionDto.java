package com.myschool.application.dto;

import java.io.Serializable;

/**
 * The Class FunctionDto.
 */
public class FunctionDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The function id. */
    private int functionId;

    /** The function name. */
    private String functionName;

    /** The module. */
    private ModuleDto module;

    /** The icon url. */
    private String iconUrl;

    /** The access url. */
    private String accessUrl;

    /**
     * Gets the function id.
     * 
     * @return the function id
     */
    public int getFunctionId() {
        return functionId;
    }

    /**
     * Sets the function id.
     * 
     * @param functionId the new function id
     */
    public void setFunctionId(int functionId) {
        this.functionId = functionId;
    }

    /**
     * Gets the function name.
     * 
     * @return the function name
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * Sets the function name.
     * 
     * @param functionName the new function name
     */
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    /**
     * Gets the module.
     * 
     * @return the module
     */
    public ModuleDto getModule() {
        return module;
    }

    /**
     * Sets the module.
     * 
     * @param module the new module
     */
    public void setModule(ModuleDto module) {
        this.module = module;
    }

    /**
     * Gets the icon url.
     * 
     * @return the icon url
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * Sets the icon url.
     * 
     * @param iconUrl the new icon url
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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
        retValue.append("FunctionDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("accessUrl = ").append(this.accessUrl).append(SEPARATOR)
            .append("functionId = ").append(this.functionId).append(SEPARATOR)
            .append("functionName = ").append(this.functionName).append(SEPARATOR)
            .append("module = ").append(this.module).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
