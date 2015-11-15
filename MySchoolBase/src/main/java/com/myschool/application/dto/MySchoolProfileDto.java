package com.myschool.application.dto;

import java.io.Serializable;

/**
 * The Class MySchoolProfileDto.
 */
public class MySchoolProfileDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

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

    /** The aye in progress. */
    private boolean ayeInProgress;

    /** The use menu icons. */
    private boolean useMenuIcons;

    /** The use employee self submit. */
    private boolean useEmployeeSelfSubmit;

    /** The use student self submit. */
    private boolean useStudentSelfSubmit;

    /** The pinned gallery. */
    private String pinnedGallery;

    /**
     * Checks if is email active.
     * 
     * @return true, if is email active
     */
    public boolean isEmailActive() {
        return emailActive;
    }

    /**
     * Sets the email active.
     * 
     * @param emailActive the new email active
     */
    public void setEmailActive(boolean emailActive) {
        this.emailActive = emailActive;
    }

    /**
     * Checks if is sms active.
     * 
     * @return true, if is sms active
     */
    public boolean isSmsActive() {
        return smsActive;
    }

    /**
     * Sets the sms active.
     * 
     * @param smsActive the new sms active
     */
    public void setSmsActive(boolean smsActive) {
        this.smsActive = smsActive;
    }

    /**
     * Checks if is email students.
     * 
     * @return true, if is email students
     */
    public boolean isEmailStudents() {
        return emailStudents;
    }

    /**
     * Sets the email students.
     * 
     * @param emailStudents the new email students
     */
    public void setEmailStudents(boolean emailStudents) {
        this.emailStudents = emailStudents;
    }

    /**
     * Checks if is sms students.
     * 
     * @return true, if is sms students
     */
    public boolean isSmsStudents() {
        return smsStudents;
    }

    /**
     * Sets the sms students.
     * 
     * @param smsStudents the new sms students
     */
    public void setSmsStudents(boolean smsStudents) {
        this.smsStudents = smsStudents;
    }

    /**
     * Checks if is email employees.
     * 
     * @return true, if is email employees
     */
    public boolean isEmailEmployees() {
        return emailEmployees;
    }

    /**
     * Sets the email employees.
     * 
     * @param emailEmployees the new email employees
     */
    public void setEmailEmployees(boolean emailEmployees) {
        this.emailEmployees = emailEmployees;
    }

    /**
     * Checks if is sms employees.
     * 
     * @return true, if is sms employees
     */
    public boolean isSmsEmployees() {
        return smsEmployees;
    }

    /**
     * Sets the sms employees.
     * 
     * @param smsEmployees the new sms employees
     */
    public void setSmsEmployees(boolean smsEmployees) {
        this.smsEmployees = smsEmployees;
    }

    /**
     * Checks if is aye in progress.
     * 
     * @return the aye in progress
     */
    public boolean isAyeInProgress() {
        return ayeInProgress;
    }

    /**
     * Sets the aye in progress.
     * 
     * @param ayeInProgress the new aye in progress
     */
    public void setAyeInProgress(boolean ayeInProgress) {
        this.ayeInProgress = ayeInProgress;
    }

    /**
     * Checks if is use menu icons.
     * 
     * @return true, if is use menu icons
     */
    public boolean isUseMenuIcons() {
        return useMenuIcons;
    }

    /**
     * Sets the use menu icons.
     * 
     * @param useMenuIcons the new use menu icons
     */
    public void setUseMenuIcons(boolean useMenuIcons) {
        this.useMenuIcons = useMenuIcons;
    }

    /**
     * Checks if is use employee self submit.
     *
     * @return true, if is use employee self submit
     */
    public boolean isUseEmployeeSelfSubmit() {
		return useEmployeeSelfSubmit;
	}

	/**
	 * Sets the use employee self submit.
	 *
	 * @param useEmployeeSelfSubmit the new use employee self submit
	 */
	public void setUseEmployeeSelfSubmit(boolean useEmployeeSelfSubmit) {
		this.useEmployeeSelfSubmit = useEmployeeSelfSubmit;
	}

	/**
	 * Checks if is use student self submit.
	 *
	 * @return true, if is use student self submit
	 */
	public boolean isUseStudentSelfSubmit() {
		return useStudentSelfSubmit;
	}

	/**
	 * Sets the use student self submit.
	 *
	 * @param useStudentSelfSubmit the new use student self submit
	 */
	public void setUseStudentSelfSubmit(boolean useStudentSelfSubmit) {
		this.useStudentSelfSubmit = useStudentSelfSubmit;
	}

	/**
     * Gets the pinned gallery.
     * 
     * @return the pinned gallery
     */
	public String getPinnedGallery() {
        return pinnedGallery;
    }

    /**
     * Sets the pinned gallery.
     * 
     * @param pinnedGallery the new pinned gallery
     */
    public void setPinnedGallery(String pinnedGallery) {
        this.pinnedGallery = pinnedGallery;
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
        retValue.append("MySchoolProfileDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("emailActive = ").append(this.emailActive).append(SEPARATOR)
            .append("smsActive = ").append(this.smsActive).append(SEPARATOR)
            .append("emailStudents = ").append(this.emailStudents).append(SEPARATOR)
            .append("smsStudents = ").append(this.smsStudents).append(SEPARATOR)
            .append("emailEmployees = ").append(this.emailEmployees).append(SEPARATOR)
            .append("smsEmployees = ").append(this.smsEmployees).append(SEPARATOR)
            .append("ayeInProgress = ").append(this.ayeInProgress).append(SEPARATOR)
            .append("useMenuIcons = ").append(this.useMenuIcons).append(SEPARATOR)
            .append("useEmployeeSelfSubmit = ").append(this.useEmployeeSelfSubmit).append(SEPARATOR)
            .append("useStudentSelfSubmit = ").append(this.useStudentSelfSubmit).append(SEPARATOR)
            .append("pinnedGallery = ").append(this.pinnedGallery).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
