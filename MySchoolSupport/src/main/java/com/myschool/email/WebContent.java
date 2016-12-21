package com.myschool.email;

public class WebContent {

    private String contentLink;

    private String originalImageLink;

    private String passportImageLink;

    private String thumbnailImageLink;

    /**
     * @return the contentLink
     */
    public String getContentLink() {
        return contentLink;
    }

    /**
     * @param contentLink the contentLink to set
     */
    public void setContentLink(String contentLink) {
        this.contentLink = contentLink;
    }

    /**
     * @return the originalImageLink
     */
    public String getOriginalImageLink() {
        return originalImageLink;
    }

    /**
     * @param originalImageLink the originalImageLink to set
     */
    public void setOriginalImageLink(String originalImageLink) {
        this.originalImageLink = originalImageLink;
    }

    /**
     * @return the passportImageLink
     */
    public String getPassportImageLink() {
        return passportImageLink;
    }

    /**
     * @param passportImageLink the passportImageLink to set
     */
    public void setPassportImageLink(String passportImageLink) {
        this.passportImageLink = passportImageLink;
    }

    /**
     * @return the thumbnailImageLink
     */
    public String getThumbnailImageLink() {
        return thumbnailImageLink;
    }

    /**
     * @param thumbnailImageLink the thumbnailImageLink to set
     */
    public void setThumbnailImageLink(String thumbnailImageLink) {
        this.thumbnailImageLink = thumbnailImageLink;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("WebContent [contentLink=").append(contentLink)
                .append(", originalImageLink=").append(originalImageLink)
                .append(", passportImageLink=").append(passportImageLink)
                .append(", thumbnailImageLink=").append(thumbnailImageLink)
                .append("]");
        return builder.toString();
    }

}
