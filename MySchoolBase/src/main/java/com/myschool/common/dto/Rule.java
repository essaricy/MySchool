package com.myschool.common.dto;

/**
 * The Class Rule.
 */
public class Rule {
    
    /** The field position. */
    private int fieldPosition;
    
    /** The field name. */
    private String fieldName;
    
    /** The mandatory. */
    private boolean mandatory;
    
    /** The max length. */
    private int maxLength;
    
    /** The data type. */
    private String dataType;
    
    /** The format. */
    private String format;
    
    /**
     * Gets the field position.
     *
     * @return the fieldPosition
     */
    public int getFieldPosition() {
        return fieldPosition;
    }
    
    /**
     * Sets the field position.
     *
     * @param fieldPosition the fieldPosition to set
     */
    public void setFieldPosition(int fieldPosition) {
        this.fieldPosition = fieldPosition;
    }
    
    /**
     * Gets the field name.
     *
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;
    }
    
    /**
     * Sets the field name.
     *
     * @param fieldName the fieldName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    
    /**
     * Checks if is mandatory.
     *
     * @return the mandatory
     */
    public boolean isMandatory() {
        return mandatory;
    }
    
    /**
     * Sets the mandatory.
     *
     * @param mandatory the mandatory to set
     */
    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }
    
    /**
     * Gets the max length.
     *
     * @return the maxLength
     */
    public int getMaxLength() {
        return maxLength;
    }
    
    /**
     * Sets the max length.
     *
     * @param maxLength the maxLength to set
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
    
    /**
     * Gets the data type.
     *
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }
    
    /**
     * Sets the data type.
     *
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    
    /**
     * Gets the format.
     *
     * @return the format
     */
    public String getFormat() {
        return format;
    }
    
    /**
     * Sets the format.
     *
     * @param format the format to set
     */
    public void setFormat(String format) {
        this.format = format;
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
        
        final StringBuffer returnValue = new StringBuffer();
        
        returnValue.append("Rule ( ")
            .append("dataType = ").append(this.dataType).append(SEPARATOR)
            .append("fieldName = ").append(this.fieldName).append(SEPARATOR)
            .append("fieldPosition = ").append(this.fieldPosition).append(SEPARATOR)
            .append("format = ").append(this.format).append(SEPARATOR)
            .append("mandatory = ").append(this.mandatory).append(SEPARATOR)
            .append("maxLength = ").append(this.maxLength).append(SEPARATOR)
            .append(" )\n");
        return returnValue.toString();
    }

}
