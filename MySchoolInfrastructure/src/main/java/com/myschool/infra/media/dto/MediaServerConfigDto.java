package com.myschool.infra.media.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * The Class MediaServerConfigDto.
 */
public class MediaServerConfigDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The base url. */
    private String baseUrl;

    /** The passport url pattern. */
    private String passportUrlPattern;

    /** The thumbnail url pattern. */
    private String thumbnailUrlPattern;

    /** The resources map. */
    private Map<String, ResourceConfigDto> resourcesMap;

    /**
     * Gets the base url.
     * 
     * @return the base url
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Sets the base url.
     * 
     * @param baseUrl the new base url
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Gets the passport url pattern.
     * 
     * @return the passport url pattern
     */
    public String getPassportUrlPattern() {
        return passportUrlPattern;
    }

    /**
     * Sets the passport url pattern.
     * 
     * @param passportUrlPattern the new passport url pattern
     */
    public void setPassportUrlPattern(String passportUrlPattern) {
        this.passportUrlPattern = passportUrlPattern;
    }

    /**
     * Gets the thumbnail url pattern.
     * 
     * @return the thumbnail url pattern
     */
    public String getThumbnailUrlPattern() {
        return thumbnailUrlPattern;
    }

    /**
     * Sets the thumbnail url pattern.
     * 
     * @param thumbnailUrlPattern the new thumbnail url pattern
     */
    public void setThumbnailUrlPattern(String thumbnailUrlPattern) {
        this.thumbnailUrlPattern = thumbnailUrlPattern;
    }

    /**
     * Gets the resources map.
     * 
     * @return the resources map
     */
    public Map<String, ResourceConfigDto> getResourcesMap() {
        return resourcesMap;
    }

    /**
     * Sets the resources map.
     * 
     * @param resourcesMap the resources map
     */
    public void setResourcesMap(Map<String, ResourceConfigDto> resourcesMap) {
        this.resourcesMap = resourcesMap;
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
        retValue.append("MediaServerConfigDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("baseUrl = ").append(this.baseUrl).append(SEPARATOR)
            .append("passportUrlPattern = ").append(this.passportUrlPattern).append(SEPARATOR)
            .append("thumbnailUrlPattern = ").append(this.thumbnailUrlPattern).append(SEPARATOR)
            .append("resourcesMap = ").append(this.resourcesMap).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
