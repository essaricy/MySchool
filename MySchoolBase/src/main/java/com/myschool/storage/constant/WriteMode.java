package com.myschool.storage.constant;

/**
 * The Enum WriteMode.
 */
public enum WriteMode {

    /** The add strict. */
    ADD_STRICT ("ADD"),
    
    /** The add or update. */
    ADD_OR_UPDATE("ADD/UPDATE"),
    
    /** The update strict. */
    UPDATE_STRICT("UPDATE"),
    
    /** The delete. */
    DELETE("DELETE"),
    
    /** The delete strict. */
    DELETE_STRICT("DELETE");

    /** The description. */
    private String description;

    /**
     * Instantiates a new write mode.
     *
     * @param description the description
     */
    private WriteMode(String description) {
        this.description=description;
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
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
