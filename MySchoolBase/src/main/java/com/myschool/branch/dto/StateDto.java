package com.myschool.branch.dto;

import java.io.Serializable;

/**
 * The Class StateDto.
 */
public class StateDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The state id. */
    private int stateId;

    /** The state name. */
    private String stateName;
    
    /**
     * Gets the state id.
     *
     * @return the state id
     */
    public int getStateId() {
        return stateId;
    }
    
    /**
     * Sets the state id.
     *
     * @param stateId the new state id
     */
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }
    
    /**
     * Gets the state name.
     *
     * @return the state name
     */
    public String getStateName() {
        return stateName;
    }
    
    /**
     * Sets the state name.
     *
     * @param stateName the new state name
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
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
        retValue.append("StateDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("stateId = ").append(this.stateId).append(SEPARATOR)
            .append("stateName = ").append(this.stateName).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
