package com.myschool.attendance.dto;

import java.io.Serializable;
import java.util.List;

/**
 * The Class Year.
 */
public class Year implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The number. */
    private int number;

    /** The months. */
    private List<Month> months;

    /**
     * Gets the number.
     *
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the number.
     *
     * @param number the new number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Gets the months.
     *
     * @return the months
     */
    public List<Month> getMonths() {
        return months;
    }

    /**
     * Sets the months.
     *
     * @param months the new months
     */
    public void setMonths(List<Month> months) {
        this.months = months;
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
        retValue.append("Year ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("months = ").append(this.months).append(SEPARATOR)
            .append("number = ").append(this.number).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
