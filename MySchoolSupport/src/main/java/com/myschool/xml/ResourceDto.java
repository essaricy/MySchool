package com.myschool.xml;

/**
 * The Class ResourceDto.
 */
public class ResourceDto {

    /** The name. */
    private String name;

    /** The parent url. */
    private String parentUrl;

    /** The resource url. */
    private String resourceUrl;

    /** The passport url. */
    private String passportUrl;

    /** The thumbnail url. */
    private String thumbnailUrl;

    /** The short description. */
    private String shortDescription;

    /** The long description. */
    private String longDescription;

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the parent url.
     * 
     * @return the parent url
     */
    public String getParentUrl() {
        return parentUrl;
    }

    /**
     * Sets the parent url.
     * 
     * @param parentUrl the new parent url
     */
    public void setParentUrl(String parentUrl) {
        this.parentUrl = parentUrl;
    }

    /**
     * Gets the resource url.
     * 
     * @return the resource url
     */
    public String getResourceUrl() {
        return resourceUrl;
    }

    /**
     * Sets the resource url.
     * 
     * @param resourceUrl the new resource url
     */
    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    /**
     * Gets the passport url.
     * 
     * @return the passport url
     */
    public String getPassportUrl() {
        return passportUrl;
    }

    /**
     * Sets the passport url.
     * 
     * @param passportUrl the new passport url
     */
    public void setPassportUrl(String passportUrl) {
        this.passportUrl = passportUrl;
    }

    /**
     * Gets the thumbnail url.
     * 
     * @return the thumbnail url
     */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /**
     * Sets the thumbnail url.
     * 
     * @param thumbnailUrl the new thumbnail url
     */
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    /**
     * Gets the short description.
     * 
     * @return the short description
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the short description.
     * 
     * @param shortDescription the new short description
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * Gets the long description.
     * 
     * @return the long description
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * Sets the long description.
     * 
     * @param longDescription the new long description
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
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
        retValue.append("ResourceDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("name = ").append(this.name).append(SEPARATOR)
            .append("parentUrl = ").append(this.parentUrl).append(SEPARATOR)
            .append("resourceUrl = ").append(this.resourceUrl).append(SEPARATOR)
            .append("passportUrl = ").append(this.passportUrl).append(SEPARATOR)
            .append("thumbnailUrl = ").append(this.thumbnailUrl).append(SEPARATOR)
            .append("shortDescription = ").append(this.shortDescription).append(SEPARATOR)
            .append("longDescription = ").append(this.longDescription).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
