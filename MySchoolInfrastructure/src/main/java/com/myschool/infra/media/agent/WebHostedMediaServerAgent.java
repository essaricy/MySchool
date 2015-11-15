package com.myschool.infra.media.agent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.myschool.application.dto.ResourceDto;
import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.util.CollectionUtil;
import com.myschool.infra.media.constants.ResourceMappingConstants;
import com.myschool.infra.media.dto.ResourceConfigDto;
import com.myschool.infra.media.reader.WebHostedMediaServerMappingReader;

/**
 * The Class WebHostedMediaServerAgent.
 */
@Component
public class WebHostedMediaServerAgent extends MediaServerAgent {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(WebHostedMediaServerAgent.class);

    /** The Constant DESC_FILE_SUFFIX. */
    private static final String DESC_FILE_SUFFIX="-desc.xml";

    /** The web hosted media server mapping reader. */
    @Autowired
    private WebHostedMediaServerMappingReader webHostedMediaServerMappingReader;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {
        try {
            this.mediaServerConfig = webHostedMediaServerMappingReader.readResources(configFile);
            LOGGER.info("mediaServerConfig=" + mediaServerConfig);
        } catch (SAXException saxException) {
            throw new ConfigurationException(saxException.getMessage(), saxException);
        } catch (IOException ioException) {
            throw new ConfigurationException(ioException.getMessage(), ioException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.Agent#validate()
     */
    @Override
    public void validate() throws AgentException {
        // nothing to validate
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.resource.agent.MediaServerAgent#getResource(java.lang.String)
     */
    @Override
    public ResourceDto getResource(String resourceId) {
        ResourceDto resource = null;
        Map<String, ResourceConfigDto> resourcesMap = mediaServerConfig.getResourcesMap();
        ResourceConfigDto resourceConfig = resourcesMap.get(resourceId);
        if (resourceConfig == null) {
            return null;
        }
        String name = resourceConfig.getName();
        String resourceUrl = resourceConfig.getUrl();
        String passportUrlPattern = mediaServerConfig.getPassportUrlPattern();
        String thumbnailUrlPattern = mediaServerConfig.getThumbnailUrlPattern();
        resource = new ResourceDto();
        if (name == null) {
            String parentUrl = mediaServerConfig.getBaseUrl();
            resource.setName(resourceConfig.getId());
            resource.setParentUrl(parentUrl);
            resource.setResourceUrl(resourceUrl);
            resource.setPassportUrl(passportUrlPattern.replaceAll(PARENT_URL, resourceUrl));
            resource.setThumbnailUrl(thumbnailUrlPattern.replaceAll(PARENT_URL, resourceUrl));
        } else {
            // Its a static resource
            resource.setName(name);
            resource.setParentUrl(resourceUrl);
            resource.setResourceUrl(resourceUrl + "/" + name);
            resource.setPassportUrl(passportUrlPattern.replaceAll(PARENT_URL, resourceUrl).replaceAll(RESOURCE_NAME, name));
            resource.setThumbnailUrl(thumbnailUrlPattern.replaceAll(PARENT_URL, resourceUrl).replaceAll(RESOURCE_NAME, name));
        }
        return resource;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.resource.agent.MediaServerAgent#getResourceItems(java.lang.String)
     */
    @Override
    protected List<ResourceDto> getResourceItems(String resourceId) {
        Map<String, ResourceConfigDto> resourcesMap = mediaServerConfig.getResourcesMap();
        ResourceConfigDto resourceConfig = resourcesMap.get(resourceId);

        if (resourceConfig == null) {
            return null;
        }
        String name = resourceConfig.getName();
        // Cannot list resource items for a static resource
        if (name != null) {
            return null;
        }
        String resourceUrl = resourceConfig.getUrl();
        List<String> excludes = resourceConfig.getExcludes();

        String response = httpGet(resourceUrl);
        if (response == null) {
            return null;
        }
        List<ResourceDto> resourceItems = getResourceItems(resourceUrl, excludes);
        return resourceItems;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.resource.agent.MediaServerAgent#getResourceItems(java.lang.String, java.lang.String)
     */
    @Override
    protected List<ResourceDto> getResourceItems(String resourceId, String childResourceName) {
        Map<String, ResourceConfigDto> resourcesMap = mediaServerConfig.getResourcesMap();
        ResourceConfigDto resourceConfig = resourcesMap.get(resourceId);

        if (resourceConfig == null) {
            return null;
        }
        String name = resourceConfig.getName();
        // Cannot list resource items for a static resource
        if (name != null) {
            return null;
        }
        String resourceUrl = resourceConfig.getUrl() + "/" + childResourceName.replaceAll(" ", "%20");
        List<String> excludes = resourceConfig.getExcludes();

        List<ResourceDto> resourceItems = getResourceItems(resourceUrl, excludes);
        return resourceItems;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.resource.agent.MediaServerAgent#getResourceItems(java.lang.String, java.util.List)
     */
    @Override
    protected List<ResourceDto> getResourceItems(String resourceUrl, List<String> excludes) {
        String response = httpGet(resourceUrl);
        if (response == null) {
            return null;
        }
        String passportUrlPattern = mediaServerConfig.getPassportUrlPattern();
        String thumbnailUrlPattern = mediaServerConfig.getThumbnailUrlPattern();
        // Get resource Items
        List<ResourceDto> resourceItems = new ArrayList<ResourceDto>();
        String descFile = null;
        ResourceDto resourceItem = null;
        Document document = Jsoup.parse(response);
        Elements links = document.select("a");
        if (links != null && !links.isEmpty()) {
            for (Element link : links) {
                String text = link.text();
                if (!CollectionUtil.containsIgnoreCase(text, excludes)) {
                    if (text.indexOf(DESC_FILE_SUFFIX) != -1) {
                        descFile = text;
                        continue;
                    }
                    String resourceName = text.trim().replaceAll(">", "").replaceAll("<", "");
                    resourceItem = new ResourceDto();
                    resourceItem.setName(resourceName);
                    resourceItem.setParentUrl(resourceUrl);
                    resourceItem.setResourceUrl(resourceUrl + "/" + resourceName);
                    resourceItem.setPassportUrl(passportUrlPattern.replaceAll(PARENT_URL, resourceUrl).replaceAll(RESOURCE_NAME, resourceName));
                    resourceItem.setThumbnailUrl(thumbnailUrlPattern.replaceAll(PARENT_URL, resourceUrl).replaceAll(RESOURCE_NAME, resourceName));
                    resourceItems.add(resourceItem);
                }
            }
        }
        if (descFile != null) {
            fillDesc(resourceItems, resourceUrl + "/" + descFile);
        }
        return resourceItems;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.resource.agent.MediaServerAgent#fillDesc(java.util.List, java.lang.String)
     */
    @Override
    protected void fillDesc(List<ResourceDto> resources, String url) {
        if (resources != null && !resources.isEmpty()) {
            String response = httpGet(url);
            Document document = Jsoup.parse(response);
            Elements resourcesDesc = document.select(ResourceMappingConstants.RESOURCE);
            if (resourcesDesc != null && !resourcesDesc.isEmpty()) {
                for (Element resourceDesc : resourcesDesc) {
                    String name = resourceDesc.attr(ResourceMappingConstants.NAME);
                    if (name != null) {
                        for (ResourceDto childResource : resources) {
                            String resourceName = childResource.getName();
                            if (name.equals(resourceName)) {
                                childResource.setShortDescription(resourceDesc.attr(ResourceMappingConstants.SHORT_DESCRIPTION));
                                childResource.setLongDescription(resourceDesc.attr(ResourceMappingConstants.LONG_DESCRIPTION));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Http get.
     * 
     * @param url the url
     * @return the string
     */
    private String httpGet(String url) {
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.connect();

            // 4xx: client error, 5xx: server error. See:
            boolean isError = httpURLConnection.getResponseCode() >= 400;
            // The normal input stream doesn't work in error-cases.
            inputStream = isError ? httpURLConnection.getErrorStream() : httpURLConnection.getInputStream();

            String contentEncoding = httpURLConnection.getContentEncoding() != null ? httpURLConnection
                    .getContentEncoding() : "UTF-8";
            return IOUtils.toString(inputStream, contentEncoding); // Apache Commons Lang
        } catch (MalformedURLException malformedURLException) {
            LOGGER.warn("Error when retrieving content from Media server " + malformedURLException.getMessage());
        } catch (IOException ioException) {
            LOGGER.warn("Error when retrieving content from Media server " + ioException.getMessage());
        } finally {
            IOUtils.closeQuietly(inputStream);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }

}
