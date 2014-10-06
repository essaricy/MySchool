package com.myschool.infra.application.dto;

import java.io.Serializable;

/**
 * The Class ConfigurationParameterDto.
 */
public class ConfigurationParameterDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    private String id;

    /** The value. */
    private String value;

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    public void setValue(String value) {
        this.value = value;
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
        retValue.append("ConfigurationParameterDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("id = ").append(this.id).append(SEPARATOR)
            .append("value = ").append(this.value).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
