package com.myschool.attendance.dto;

import java.io.Serializable;

import com.myschool.attendance.constants.LeaveStatus;

/**
 * The Class LeaveDto.
 */
public class LeaveDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The type. */
    private String type;
    
    /** The start date. */
    private String startDate;

    /** The end date. */
    private String endDate;

    /** The number of days. */
    private double numberOfDays;

    /** The comments. */
    private String comments;

    /** The leave status. */
    private LeaveStatus leaveStatus;

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public String getType() {
        return type;
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
     * Gets the number of days.
     * 
     * @return the number of days
     */
    public double getNumberOfDays() {
        return numberOfDays;
    }

    /**
     * Sets the number of days.
     * 
     * @param numberOfDays the new number of days
     */
    public void setNumberOfDays(double numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    /**
     * Gets the comments.
     * 
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the comments.
     * 
     * @param comments the new comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Gets the leave status.
     * 
     * @return the leave status
     */
    public LeaveStatus getLeaveStatus() {
        return leaveStatus;
    }

    /**
     * Sets the leave status.
     * 
     * @param leaveStatus the new leave status
     */
    public void setLeaveStatus(LeaveStatus leaveStatus) {
        this.leaveStatus = leaveStatus;
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
        retValue.append("LeaveDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("type = ").append(this.type).append(SEPARATOR)
            .append("startDate = ").append(this.startDate).append(SEPARATOR)
            .append("endDate = ").append(this.endDate).append(SEPARATOR)
            .append("numberOfDays = ").append(this.numberOfDays).append(SEPARATOR)
            .append("comments = ").append(this.comments).append(SEPARATOR)
            .append("leaveStatus = ").append(this.leaveStatus).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
