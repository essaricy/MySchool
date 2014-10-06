package com.myschool.infra.oxo.dto;

import java.io.Serializable;
import java.util.List;

/**
 * The Class ObjectXmlMappingDto.
 */
public class ObjectXmlMappingDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The name. */
    private String name;

    /** The type. */
    private String type;

    /** The object xml fields. */
    private List<ObjectXmlFieldDto> objectXmlFields;

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
     * Gets the object xml fields.
     *
     * @return the object xml fields
     */
    public List<ObjectXmlFieldDto> getObjectXmlFields() {
        return objectXmlFields;
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
     * Sets the object xml fields.
     *
     * @param objectXmlFields the new object xml fields
     */
    public void setObjectXmlFields(List<ObjectXmlFieldDto> objectXmlFields) {
        this.objectXmlFields = objectXmlFields;
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
        retValue.append("ObjectXmlMappingDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("name = ").append(this.name).append(SEPARATOR)
            .append("objectXmlFields = ").append(this.objectXmlFields).append(SEPARATOR)
            .append("type = ").append(this.type).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
