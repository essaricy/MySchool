package com.myschool.infra.middleware.dto;

import java.io.Serializable;

import com.myschool.infra.middleware.constants.QueueCategory;

/**
 * The Class MessageQueue.
 */
public class MessageQueue implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The queue category. */
    private QueueCategory queueCategory;

    /** The description. */
    private String description;

    /** The output. */
    private String output;

    /** The fail. */
    private String fail;

    /** The error. */
    private String error;

    /**
     * Gets the queue category.
     * 
     * @return the queue category
     */
    public QueueCategory getQueueCategory() {
        return queueCategory;
    }

    /**
     * Sets the queue category.
     * 
     * @param queueCategory the new queue category
     */
    public void setQueueCategory(QueueCategory queueCategory) {
        this.queueCategory = queueCategory;
    }

    /**
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * 
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the output.
     * 
     * @return the output
     */
    public String getOutput() {
        return output;
    }

    /**
     * Sets the output.
     * 
     * @param output the new output
     */
    public void setOutput(String output) {
        this.output = output;
    }

    /**
     * Gets the fail.
     * 
     * @return the fail
     */
    public String getFail() {
        return fail;
    }

    /**
     * Sets the fail.
     * 
     * @param fail the new fail
     */
    public void setFail(String fail) {
        this.fail = fail;
    }

    /**
     * Gets the error.
     * 
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the error.
     * 
     * @param error the new error
     */
    public void setError(String error) {
        this.error = error;
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
        retValue.append("MessageQueue ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("queueCategory = ").append(this.queueCategory).append(SEPARATOR)
            .append("description = ").append(this.description).append(SEPARATOR)
            .append("output = ").append(this.output).append(SEPARATOR)
            .append("fail = ").append(this.fail).append(SEPARATOR)
            .append("error = ").append(this.error).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
