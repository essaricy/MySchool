package com.myschool.user.dto;

import java.io.Serializable;

/**
 * The Class UserStatistics.
 */
public class UserStatistics implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The number of visits. */
    private int numberOfVisits;

    /** The last visit on. */
    private String lastVisitOn;

    /**
     * Gets the number of visits.
     *
     * @return the number of visits
     */
    public int getNumberOfVisits() {
        return numberOfVisits;
    }

    /**
     * Sets the number of visits.
     *
     * @param numberOfVisits the new number of visits
     */
    public void setNumberOfVisits(int numberOfVisits) {
        this.numberOfVisits = numberOfVisits;
    }

    /**
     * Gets the last visit on.
     *
     * @return the last visit on
     */
    public String getLastVisitOn() {
        return lastVisitOn;
    }

    /**
     * Sets the last visit on.
     *
     * @param lastVisitOn the new last visit on
     */
    public void setLastVisitOn(String lastVisitOn) {
        this.lastVisitOn = lastVisitOn;
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
        retValue.append("UserStatistics ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("lastVisitOn = ").append(this.lastVisitOn).append(SEPARATOR)
            .append("numberOfVisits = ").append(this.numberOfVisits).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
