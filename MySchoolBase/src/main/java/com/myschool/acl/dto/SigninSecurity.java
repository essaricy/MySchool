package com.myschool.acl.dto;

import com.myschool.acl.constant.SigninSecurityLevel;

/**
 * The Class SigninSecurity.
 */
public class SigninSecurity {

    /** The number of failed attempts. */
    private int numberOfFailedAttempts;

    /** The last failed attempt. */
    private long lastFailedAttempt;

    /** The current security level. */
    private SigninSecurityLevel currentSecurityLevel;

    /**
     * Gets the number of failed attempts.
     *
     * @return the numberOfFailedAttempts
     */
    public int getNumberOfFailedAttempts() {
        return numberOfFailedAttempts;
    }

    /**
     * Sets the number of failed attempts.
     *
     * @param numberOfFailedAttempts the numberOfFailedAttempts to set
     */
    public void setNumberOfFailedAttempts(int numberOfFailedAttempts) {
        this.numberOfFailedAttempts = numberOfFailedAttempts;
    }

    /**
     * Gets the last failed attempt.
     *
     * @return the lastFailedAttempt
     */
    public long getLastFailedAttempt() {
        return lastFailedAttempt;
    }

    /**
     * Sets the last failed attempt.
     *
     * @param lastFailedAttempt the lastFailedAttempt to set
     */
    public void setLastFailedAttempt(long lastFailedAttempt) {
        this.lastFailedAttempt = lastFailedAttempt;
    }

    /**
     * Gets the current security level.
     *
     * @return the currentSecurityLevel
     */
    public SigninSecurityLevel getCurrentSecurityLevel() {
        return currentSecurityLevel;
    }

    /**
     * Sets the current security level.
     *
     * @param currentSecurityLevel the currentSecurityLevel to set
     */
    public void setCurrentSecurityLevel(SigninSecurityLevel currentSecurityLevel) {
        this.currentSecurityLevel = currentSecurityLevel;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SigninSecurity [numberOfFailedAttempts=")
                .append(numberOfFailedAttempts).append(", lastFailedAttempt=")
                .append(lastFailedAttempt).append(", currentSecurityLevel=")
                .append(currentSecurityLevel).append("]");
        return builder.toString();
    }

}
