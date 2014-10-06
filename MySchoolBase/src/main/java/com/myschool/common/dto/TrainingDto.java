package com.myschool.common.dto;

import java.io.Serializable;

/**
 * The Class TrainingDto.
 */
public class TrainingDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The training name. */
    private String trainingName;

    /** The mandatory. */
    private boolean mandatory;

    /** The completed. */
    private boolean completed;

    /**
     * Gets the training name.
     *
     * @return the training name
     */
    public String getTrainingName() {
        return trainingName;
    }

    /**
     * Sets the training name.
     *
     * @param trainingName the new training name
     */
    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    /**
     * Checks if is mandatory.
     *
     * @return true, if is mandatory
     */
    public boolean isMandatory() {
        return mandatory;
    }

    /**
     * Sets the mandatory.
     *
     * @param mandatory the new mandatory
     */
    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    /**
     * Checks if is completed.
     *
     * @return true, if is completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets the completed.
     *
     * @param completed the new completed
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
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
        retValue.append("TrainingDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("completed = ").append(this.completed).append(SEPARATOR)
            .append("mandatory = ").append(this.mandatory).append(SEPARATOR)
            .append("trainingName = ").append(this.trainingName).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
