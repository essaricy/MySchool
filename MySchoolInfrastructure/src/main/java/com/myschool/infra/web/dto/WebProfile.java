package com.myschool.infra.web.dto;

import java.io.Serializable;

/**
 * The Class WebProfile.
 */
public class WebProfile implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The logo. */
    private WebContent logo;

    /** The no image. */
    private WebContent noImage;

    /** The contact us. */
    private WebContent contactUs;

    /** The verify email. */
    private WebContent verifyEmail;

    /** The unsubscribe. */
    private WebContent unsubscribe;

    /** The website. */
    private WebContent website;

    /** The change password. */
    private WebContent changePassword;

    /** The facebook. */
    private WebContent facebook;

    /** The twitter. */
    private WebContent twitter;

    /** The googleplus. */
    private WebContent googleplus;

    /**
     * Gets the logo.
     *
     * @return the logo
     */
    public WebContent getLogo() {
        return logo;
    }

    /**
     * Sets the logo.
     *
     * @param logo the logo to set
     */
    public void setLogo(WebContent logo) {
        this.logo = logo;
    }

    /**
     * Gets the no image.
     *
     * @return the noImage
     */
    public WebContent getNoImage() {
        return noImage;
    }

    /**
     * Sets the no image.
     *
     * @param noImage the noImage to set
     */
    public void setNoImage(WebContent noImage) {
        this.noImage = noImage;
    }

    /**
     * Gets the contact us.
     *
     * @return the contactUs
     */
    public WebContent getContactUs() {
        return contactUs;
    }

    /**
     * Sets the contact us.
     *
     * @param contactUs the contactUs to set
     */
    public void setContactUs(WebContent contactUs) {
        this.contactUs = contactUs;
    }

    /**
     * Gets the verify email.
     *
     * @return the verifyEmail
     */
    public WebContent getVerifyEmail() {
        return verifyEmail;
    }

    /**
     * Sets the verify email.
     *
     * @param verifyEmail the verifyEmail to set
     */
    public void setVerifyEmail(WebContent verifyEmail) {
        this.verifyEmail = verifyEmail;
    }

    /**
     * Gets the unsubscribe.
     *
     * @return the unsubscribe
     */
    public WebContent getUnsubscribe() {
        return unsubscribe;
    }

    /**
     * Sets the unsubscribe.
     *
     * @param unsubscribe the unsubscribe to set
     */
    public void setUnsubscribe(WebContent unsubscribe) {
        this.unsubscribe = unsubscribe;
    }

    /**
     * Gets the website.
     *
     * @return the website
     */
    public WebContent getWebsite() {
        return website;
    }

    /**
     * Sets the website.
     *
     * @param website the website to set
     */
    public void setWebsite(WebContent website) {
        this.website = website;
    }

    /**
     * Gets the change password.
     *
     * @return the changePassword
     */
    public WebContent getChangePassword() {
        return changePassword;
    }

    /**
     * Sets the change password.
     *
     * @param changePassword the changePassword to set
     */
    public void setChangePassword(WebContent changePassword) {
        this.changePassword = changePassword;
    }

    /**
     * Gets the facebook.
     *
     * @return the facebook
     */
    public WebContent getFacebook() {
        return facebook;
    }

    /**
     * Sets the facebook.
     *
     * @param facebook the facebook to set
     */
    public void setFacebook(WebContent facebook) {
        this.facebook = facebook;
    }

    /**
     * Gets the twitter.
     *
     * @return the twitter
     */
    public WebContent getTwitter() {
        return twitter;
    }

    /**
     * Sets the twitter.
     *
     * @param twitter the twitter to set
     */
    public void setTwitter(WebContent twitter) {
        this.twitter = twitter;
    }

    /**
     * Gets the googleplus.
     *
     * @return the googleplus
     */
    public WebContent getGoogleplus() {
        return googleplus;
    }

    /**
     * Sets the googleplus.
     *
     * @param googleplus the googleplus to set
     */
    public void setGoogleplus(WebContent googleplus) {
        this.googleplus = googleplus;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("WebProfile [logo=").append(logo).append(", noImage=")
                .append(noImage).append(", contactUs=").append(contactUs)
                .append(", verifyEmail=").append(verifyEmail)
                .append(", unsubscribe=").append(unsubscribe)
                .append(", website=").append(website)
                .append(", changePassword=").append(changePassword)
                .append(", facebook=").append(facebook).append(", twitter=")
                .append(twitter).append(", googleplus=").append(googleplus)
                .append("]");
        return builder.toString();
    }

}
