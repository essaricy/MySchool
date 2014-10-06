package com.myschool.user.dto;

import java.io.Serializable;

/**
 * The Class FunctionAccessDto.
 */
public class FunctionAccessDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The function id. */
    private int functionId;

    /** The function name. */
    private String functionName;

    /** The view. */
    private boolean view;

    /** The create. */
    private boolean create;

    /** The update. */
    private boolean update;

    /** The delete. */
    private boolean delete;

    /** The access url. */
    private String accessUrl;

    /** The icon url. */
    private String iconUrl;

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
     * Checks if is view.
     * 
     * @return true, if is view
     */
    public boolean isView() {
        return view;
    }

    /**
     * Sets the view.
     * 
     * @param view the new view
     */
    public void setView(boolean view) {
        this.view = view;
    }

    /**
     * Checks if is creates the.
     * 
     * @return true, if is creates the
     */
    public boolean isCreate() {
        return create;
    }

    /**
     * Sets the creates the.
     * 
     * @param create the new creates the
     */
    public void setCreate(boolean create) {
        this.create = create;
    }

    /**
     * Checks if is update.
     * 
     * @return true, if is update
     */
    public boolean isUpdate() {
        return update;
    }

    /**
     * Sets the update.
     * 
     * @param update the new update
     */
    public void setUpdate(boolean update) {
        this.update = update;
    }

    /**
     * Checks if is delete.
     * 
     * @return true, if is delete
     */
    public boolean isDelete() {
        return delete;
    }

    /**
     * Sets the delete.
     * 
     * @param delete the new delete
     */
    public void setDelete(boolean delete) {
        this.delete = delete;
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
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("FunctionAccessDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("functionId = ").append(this.functionId).append(SEPARATOR)
            .append("functionName = ").append(this.functionName).append(SEPARATOR)
            .append("view = ").append(this.view).append(SEPARATOR)
            .append("create = ").append(this.create).append(SEPARATOR)
            .append("update = ").append(this.update).append(SEPARATOR)
            .append("delete = ").append(this.delete).append(SEPARATOR)
            .append("accessUrl = ").append(this.accessUrl).append(SEPARATOR)
            .append("iconUrl = ").append(this.iconUrl).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
