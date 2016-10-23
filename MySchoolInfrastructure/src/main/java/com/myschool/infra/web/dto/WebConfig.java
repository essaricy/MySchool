package com.myschool.infra.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * The Class WebConfig.
 */
public class WebConfig implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The url. */
    private String url;

    /** The web resources. */
    private List<WebResource> webResources;

    /**
     * Gets the url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url.
     *
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the web resources.
     *
     * @return the webResources
     */
    public List<WebResource> getWebResources() {
        return webResources;
    }

    /**
     * Sets the web resources.
     *
     * @param webResources the webResources to set
     */
    public void setWebResources(List<WebResource> webResources) {
        this.webResources = webResources;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final int maxLen = 10;
        StringBuilder builder = new StringBuilder();
        builder.append("WebConfig [url=").append(url).append(", webResources=")
                .append(webResources != null ? webResources.subList(0,
                        Math.min(webResources.size(), maxLen)) : null)
                .append("]");
        return builder.toString();
    }

}
