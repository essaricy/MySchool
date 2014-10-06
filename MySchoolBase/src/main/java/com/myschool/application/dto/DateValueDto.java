package com.myschool.application.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class DateValueDto.
 */
public class DateValueDto implements Serializable, Comparable<DateValueDto> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The date. */
    private Date date;

    /** The value. */
    private double value;

    /**
     * Gets the date.
     * 
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date.
     * 
     * @param date the new date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    public void setValue(double value) {
        this.value = value;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DateValueDto other = (DateValueDto) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(DateValueDto anotherDate) {
        if (anotherDate != null && anotherDate.getDate() != null && getDate() != null) {
            return anotherDate.getDate().compareTo(getDate());
        }
        return 0;
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
        retValue.append("DateValueDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("date = ").append(this.date).append(SEPARATOR)
            .append("value = ").append(this.value).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
