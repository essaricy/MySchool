package com.myschool.academic.dto;

import java.io.Serializable;

/**
 * The Class HolidayDto.
 */
public class HolidayDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The holiday id. */
    private int holidayId;

    /** The holiday name. */
    private String holidayName;

    /** The start date. */
    private String startDate;

    /** The end date. */
    private String endDate;

    /**
     * Gets the holiday id.
     *
     * @return the holiday id
     */
    public int getHolidayId() {
        return holidayId;
    }

    /**
     * Sets the holiday id.
     *
     * @param holidayId the new holiday id
     */
    public void setHolidayId(int holidayId) {
        this.holidayId = holidayId;
    }

    /**
     * Gets the holiday name.
     *
     * @return the holiday name
     */
    public String getHolidayName() {
        return holidayName;
    }

    /**
     * Sets the holiday name.
     *
     * @param holidayName the new holiday name
     */
    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    /**
     * Gets the start date.
     *
     * @return the start date
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate the new start date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date.
     *
     * @return the end date
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date.
     *
     * @param endDate the new end date
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
        retValue.append("HolidayDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("holidayId = ").append(this.holidayId).append(SEPARATOR)
            .append("holidayName = ").append(this.holidayName).append(SEPARATOR)
            .append("startDate = ").append(this.startDate).append(SEPARATOR)
            .append("endDate = ").append(this.endDate).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
