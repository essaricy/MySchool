package com.myschool.infra.webserver.agent;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.infra.agent.AbstractAgent;
import com.myschool.infra.template.agent.TemplateAgent;
import com.myschool.infra.web.dto.WebConfig;
import com.myschool.infra.web.dto.WebContent;
import com.myschool.infra.web.dto.WebProfile;
import com.myschool.infra.web.dto.WebResource;
import com.myschool.infra.webserver.constant.WebConfigConstants;
import com.myschool.infra.webserver.reader.WebConfigReader;

/**
 * The Class WebServerAgent.
 */
public abstract class WebServerAgent extends AbstractAgent {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(TemplateAgent.class);

    /** The web config reader. */
    @Autowired
    private WebConfigReader webConfigReader;

    /** The web config. */
    private WebConfig webConfig;

    /**
     * Load configuration.
     *
     * @param configFile the config file
     * @throws ConfigurationException the configuration exception
     */
    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {
        LOGGER.info("loading web configuration");
        webConfig = webConfigReader.getWebConfig(configFile);
        LOGGER.info("loaded web configuration");
    }

    /**
     * Validate.
     *
     * @throws AgentException the agent exception
     */
    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#validate()
     */
    @Override
    public void validate() throws AgentException {
    }

    /**
     * Gets the data source.
     *
     * @param dataSourceProperties the data source properties
     * @return the data source
     * @throws AgentException the agent exception
     */
    public abstract DataSource getDataSource(Properties dataSourceProperties) throws AgentException;

    /**
     * Checks if is web server.
     *
     * @return true, if is web server
     * @throws AgentException the agent exception
     */
    public abstract boolean isWebServer() throws AgentException;

    /**
     * Gets the web profile.
     *
     * @return the web profile
     */
    public WebProfile getWebProfile() {
        //System.out.println("webConfig 2 = " + webConfig);
        String webUrl = webConfig.getUrl();

        WebProfile webProfile = new WebProfile();

        WebContent website = new WebContent();
        website.setContentLink(webUrl);
        webProfile.setWebsite(website);
        webProfile.setLogo(getWebContent(WebConfigConstants.LOGO));
        webProfile.setNoImage(getWebContent(WebConfigConstants.NO_IMAGE));

        webProfile.setContactUs(getWebContent(WebConfigConstants.CONTACT_US));
        webProfile.setChangePassword(getWebContent(WebConfigConstants.CHANGE_PASSWORD));
        webProfile.setUnsubscribe(getWebContent(WebConfigConstants.UNSUBSCRIBE));

        webProfile.setFacebook(getWebContent(WebConfigConstants.SOCIAL_FACEBOOK));
        webProfile.setTwitter(getWebContent(WebConfigConstants.SOCIAL_TWITTER));
        webProfile.setGoogleplus(getWebContent(WebConfigConstants.SOCIAL_GOOGLEPLUS));
        return webProfile;
    }

    /**
     * Gets the web content.
     *
     * @param contentName the content name
     * @return the web content
     */
    private WebContent getWebContent(String contentName) {
        if (contentName != null) {
            String url = webConfig.getUrl();
            List<WebResource> webResources = webConfig.getWebResources();
            if (webResources != null && !webResources.isEmpty()) {
                for (WebResource webResource : webResources) {
                    String name = webResource.getName();
                    if (contentName.equals(name)) {
                        WebContent webContent = new WebContent();
                        webContent.setContentLink(url + "/" + webResource.getLinkUrl());
                        webContent.setOriginalImageLink(webResource.getImageUrl());
                        webContent.setPassportImageLink(webResource.getPassportUrl());
                        webContent.setThumbnailImageLink(webResource.getThumbUrl());
                        return webContent;
                    }
                }
            }
        }
        return null;
    }

}
