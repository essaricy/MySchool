package com.myschool.organization.dto;

import java.io.Serializable;

/**
 * The Class OrganizationManifest.
 */
public class OrganizationManifest implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    private int id;

    /** The academic year name. */
    private String academicYearName;

    /** The map url. */
    private String mapURL;

    /** The aye in progress. */
    private boolean ayeInProgress;

    /** The captcha key. */
    private String captchaKey;

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
     * Gets the academic year name.
     *
     * @return the academicYearName
     */
    public String getAcademicYearName() {
        return academicYearName;
    }

    /**
     * Sets the academic year name.
     *
     * @param academicYearName the academicYearName to set
     */
    public void setAcademicYearName(String academicYearName) {
        this.academicYearName = academicYearName;
    }

    /**
     * Gets the map url.
     *
     * @return the mapURL
     */
    public String getMapURL() {
        return mapURL;
    }

    /**
     * Sets the map url.
     *
     * @param mapURL the mapURL to set
     */
    public void setMapURL(String mapURL) {
        this.mapURL = mapURL;
    }

    /**
     * Checks if is aye in progress.
     *
     * @return the ayeInProgress
     */
    public boolean isAyeInProgress() {
        return ayeInProgress;
    }

    /**
     * Sets the aye in progress.
     *
     * @param ayeInProgress the ayeInProgress to set
     */
    public void setAyeInProgress(boolean ayeInProgress) {
        this.ayeInProgress = ayeInProgress;
    }

    /**
     * Gets the captcha key.
     *
     * @return the captchaKey
     */
    public String getCaptchaKey() {
        return captchaKey;
    }

    /**
     * Sets the captcha key.
     *
     * @param captchaKey the captchaKey to set
     */
    public void setCaptchaKey(String captchaKey) {
        this.captchaKey = captchaKey;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OrganizationManifest [id=").append(id)
                .append(", academicYearName=").append(academicYearName)
                .append(", mapURL=").append(mapURL).append(", ayeInProgress=")
                .append(ayeInProgress).append(", captchaKey=")
                .append(captchaKey).append("]");
        return builder.toString();
    }

}
