package com.myschool.infra.oxo.dto;

import java.io.Serializable;

/**
 * The Class ObjectXmlFieldDto.
 */
public class ObjectXmlFieldDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The name. */
    private String name;

    /** The type. */
    private String type;

    /** The alias. */
    private String alias;

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the alias.
     *
     * @return the alias
     */
    public String getAlias() {
        return alias;
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
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the alias.
     *
     * @param alias the new alias
     */
    public void setAlias(String alias) {
        this.alias = alias;
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
        retValue.append("ObjectXmlFieldDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("alias = ").append(this.alias).append(SEPARATOR)
            .append("name = ").append(this.name).append(SEPARATOR)
            .append("type = ").append(this.type).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
