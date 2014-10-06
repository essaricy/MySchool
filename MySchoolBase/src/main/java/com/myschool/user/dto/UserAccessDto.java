package com.myschool.user.dto;

import java.io.Serializable;

import com.myschool.application.dto.FunctionDto;

/**
 * The Class UserAccessDto.
 */
public class UserAccessDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The function. */
    private FunctionDto function;

    /** The view. */
    private boolean view;

    /** The create. */
    private boolean create;

    /** The update. */
    private boolean update;

    /** The delete. */
    private boolean delete;

    /**
     * Gets the function.
     *
     * @return the function
     */
    public FunctionDto getFunction() {
        return function;
    }

    /**
     * Sets the function.
     *
     * @param function the new function
     */
    public void setFunction(FunctionDto function) {
        this.function = function;
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
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("UserAccessDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("create = ").append(this.create).append(SEPARATOR)
            .append("delete = ").append(this.delete).append(SEPARATOR)
            .append("function = ").append(this.function).append(SEPARATOR)
            .append("update = ").append(this.update).append(SEPARATOR)
            .append("view = ").append(this.view).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
