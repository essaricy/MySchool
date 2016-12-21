package com.myschool.email;

public class WebProfile {

    private WebContent logo;

    private WebContent contactUs;

    private WebContent unsubscribe;

    private WebContent website;

    private WebContent changePassword;

    private WebContent facebook;

    private WebContent twitter;

    private WebContent googleplus;

    /**
     * @return the logo
     */
    public WebContent getLogo() {
        return logo;
    }

    /**
     * @param logo the logo to set
     */
    public void setLogo(WebContent logo) {
        this.logo = logo;
    }

    /**
     * @return the contactUs
     */
    public WebContent getContactUs() {
        return contactUs;
    }

    /**
     * @param contactUs the contactUs to set
     */
    public void setContactUs(WebContent contactUs) {
        this.contactUs = contactUs;
    }

    /**
     * @return the unsubscribe
     */
    public WebContent getUnsubscribe() {
        return unsubscribe;
    }

    /**
     * @param unsubscribe the unsubscribe to set
     */
    public void setUnsubscribe(WebContent unsubscribe) {
        this.unsubscribe = unsubscribe;
    }

    /**
     * @return the website
     */
    public WebContent getWebsite() {
        return website;
    }

    /**
     * @param website the website to set
     */
    public void setWebsite(WebContent website) {
        this.website = website;
    }

    /**
     * @return the changePassword
     */
    public WebContent getChangePassword() {
        return changePassword;
    }

    /**
     * @param changePassword the changePassword to set
     */
    public void setChangePassword(WebContent changePassword) {
        this.changePassword = changePassword;
    }

    /**
     * @return the facebook
     */
    public WebContent getFacebook() {
        return facebook;
    }

    /**
     * @param facebook the facebook to set
     */
    public void setFacebook(WebContent facebook) {
        this.facebook = facebook;
    }

    /**
     * @return the twitter
     */
    public WebContent getTwitter() {
        return twitter;
    }

    /**
     * @param twitter the twitter to set
     */
    public void setTwitter(WebContent twitter) {
        this.twitter = twitter;
    }

    /**
     * @return the googleplus
     */
    public WebContent getGoogleplus() {
        return googleplus;
    }

    /**
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
        builder.append("WebProfile [logo=").append(logo).append(", contactUs=")
                .append(contactUs).append(", unsubscribe=").append(unsubscribe)
                .append(", website=").append(website)
                .append(", changePassword=").append(changePassword)
                .append(", facebook=").append(facebook).append(", twitter=")
                .append(twitter).append(", googleplus=").append(googleplus)
                .append("]");
        return builder.toString();
    }

}
