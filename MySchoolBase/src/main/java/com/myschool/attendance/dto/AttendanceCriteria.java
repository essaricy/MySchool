package com.myschool.attendance.dto;

/**
 * The Class AttendanceCriteria.
 */
public class AttendanceCriteria {

    /** The class id. */
    private int classId;

    /** The date. */
    private int date;

    /** The month. */
    private int month;

    /** The year. */
    private int year;

    /**
     * Gets the class id.
     *
     * @return the class id
     */
    public int getClassId() {
        return classId;
    }

    /**
     * Sets the class id.
     *
     * @param classId the new class id
     */
    public void setClassId(int classId) {
        this.classId = classId;
    }

    /**
     * Gets the date.
     *
     * @return the date
     */
    public int getDate() {
        return date;
    }

    /**
     * Sets the date.
     *
     * @param date the new date
     */
    public void setDate(int date) {
        this.date = date;
    }

    /**
     * Gets the month.
     *
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Sets the month.
     *
     * @param month the new month
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Gets the year.
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year.
     *
     * @param year the new year
     */
    public void setYear(int year) {
        this.year = year;
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
        retValue.append("AttendanceCriteria ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("classId = ").append(this.classId).append(SEPARATOR)
            .append("date = ").append(this.date).append(SEPARATOR)
            .append("month = ").append(this.month).append(SEPARATOR)
            .append("year = ").append(this.year).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
