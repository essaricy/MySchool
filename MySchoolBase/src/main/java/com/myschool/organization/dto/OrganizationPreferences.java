package com.myschool.organization.dto;

import java.io.Serializable;

/**
 * The Class OrganizationPreferences.
 */
public class OrganizationPreferences implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    private int id; 

    /** The email active. */
    private boolean emailActive;

    /** The sms active. */
    private boolean smsActive;

    /** The email students. */
    private boolean emailStudents;

    /** The sms students. */
    private boolean smsStudents;

    /** The email employees. */
    private boolean emailEmployees;

    /** The sms employees. */
    private boolean smsEmployees;

    /** The use menu icons. */
    private boolean useMenuIcons;

    /** The use employee self submit. */
    private boolean useEmployeeSelfSubmit;

    /** The use student self submit. */
    private boolean useStudentSelfSubmit;

    /** The default theme. */
    private String defaultTheme;

    /** The default gallery. */
    private String defaultGallery;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Checks if is email active.
     *
     * @return the emailActive
     */
    public boolean isEmailActive() {
        return emailActive;
    }

    /**
     * Sets the email active.
     *
     * @param emailActive the emailActive to set
     */
    public void setEmailActive(boolean emailActive) {
        this.emailActive = emailActive;
    }

    /**
     * Checks if is sms active.
     *
     * @return the smsActive
     */
    public boolean isSmsActive() {
        return smsActive;
    }

    /**
     * Sets the sms active.
     *
     * @param smsActive the smsActive to set
     */
    public void setSmsActive(boolean smsActive) {
        this.smsActive = smsActive;
    }

    /**
     * Checks if is email students.
     *
     * @return the emailStudents
     */
    public boolean isEmailStudents() {
        return emailStudents;
    }

    /**
     * Sets the email students.
     *
     * @param emailStudents the emailStudents to set
     */
    public void setEmailStudents(boolean emailStudents) {
        this.emailStudents = emailStudents;
    }

    /**
     * Checks if is sms students.
     *
     * @return the smsStudents
     */
    public boolean isSmsStudents() {
        return smsStudents;
    }

    /**
     * Sets the sms students.
     *
     * @param smsStudents the smsStudents to set
     */
    public void setSmsStudents(boolean smsStudents) {
        this.smsStudents = smsStudents;
    }

    /**
     * Checks if is email employees.
     *
     * @return the emailEmployees
     */
    public boolean isEmailEmployees() {
        return emailEmployees;
    }

    /**
     * Sets the email employees.
     *
     * @param emailEmployees the emailEmployees to set
     */
    public void setEmailEmployees(boolean emailEmployees) {
        this.emailEmployees = emailEmployees;
    }

    /**
     * Checks if is sms employees.
     *
     * @return the smsEmployees
     */
    public boolean isSmsEmployees() {
        return smsEmployees;
    }

    /**
     * Sets the sms employees.
     *
     * @param smsEmployees the smsEmployees to set
     */
    public void setSmsEmployees(boolean smsEmployees) {
        this.smsEmployees = smsEmployees;
    }

    /**
     * Checks if is use menu icons.
     *
     * @return the useMenuIcons
     */
    public boolean isUseMenuIcons() {
        return useMenuIcons;
    }

    /**
     * Sets the use menu icons.
     *
     * @param useMenuIcons the useMenuIcons to set
     */
    public void setUseMenuIcons(boolean useMenuIcons) {
        this.useMenuIcons = useMenuIcons;
    }

    /**
     * Gets the default theme.
     *
     * @return the defaultTheme
     */
    public String getDefaultTheme() {
        return defaultTheme;
    }

    /**
     * Sets the default theme.
     *
     * @param defaultTheme the defaultTheme to set
     */
    public void setDefaultTheme(String defaultTheme) {
        this.defaultTheme = defaultTheme;
    }

    /**
     * Checks if is use employee self submit.
     *
     * @return the useEmployeeSelfSubmit
     */
    public boolean isUseEmployeeSelfSubmit() {
        return useEmployeeSelfSubmit;
    }

    /**
     * Sets the use employee self submit.
     *
     * @param useEmployeeSelfSubmit the useEmployeeSelfSubmit to set
     */
    public void setUseEmployeeSelfSubmit(boolean useEmployeeSelfSubmit) {
        this.useEmployeeSelfSubmit = useEmployeeSelfSubmit;
    }

    /**
     * Checks if is use student self submit.
     *
     * @return the useStudentSelfSubmit
     */
    public boolean isUseStudentSelfSubmit() {
        return useStudentSelfSubmit;
    }

    /**
     * Sets the use student self submit.
     *
     * @param useStudentSelfSubmit the useStudentSelfSubmit to set
     */
    public void setUseStudentSelfSubmit(boolean useStudentSelfSubmit) {
        this.useStudentSelfSubmit = useStudentSelfSubmit;
    }

    /**
     * Gets the default gallery.
     *
     * @return the defaultGallery
     */
    public String getDefaultGallery() {
        return defaultGallery;
    }

    /**
     * Sets the default gallery.
     *
     * @param defaultGallery the defaultGallery to set
     */
    public void setDefaultGallery(String defaultGallery) {
        this.defaultGallery = defaultGallery;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OrganizationPreferences [id=").append(id)
                .append(", emailActive=").append(emailActive)
                .append(", smsActive=").append(smsActive)
                .append(", emailStudents=").append(emailStudents)
                .append(", smsStudents=").append(smsStudents)
                .append(", emailEmployees=").append(emailEmployees)
                .append(", smsEmployees=").append(smsEmployees)
                .append(", useMenuIcons=").append(useMenuIcons)
                .append(", useEmployeeSelfSubmit=")
                .append(useEmployeeSelfSubmit).append(", useStudentSelfSubmit=")
                .append(useStudentSelfSubmit).append(", defaultTheme=")
                .append(defaultTheme).append(", defaultGallery=")
                .append(defaultGallery).append("]");
        return builder.toString();
    }

}
