package com.myschool.common.dto;

import java.io.Serializable;

/**
 * The Class Relationship.
 */
public class Relationship implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The code. */
    private String code;

    /** The name. */
    private String name;

    /**
     * Gets the code.
     * 
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code.
     * 
     * @param code the new code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
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
        retValue.append("Relationship ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("code = ").append(this.code).append(SEPARATOR)
            .append("name = ").append(this.name).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
