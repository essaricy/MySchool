package com.myschool.infra.media.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.myschool.common.util.StringUtil;
import com.myschool.infra.media.constants.ResourceMappingConstants;
import com.myschool.infra.media.dto.MediaServerConfigDto;
import com.myschool.infra.media.dto.ResourceConfigDto;

/**
 * The Class WebHostedResourceMappingReader.
 */
@Component
public class WebHostedMediaServerMappingReader extends DefaultHandler {

    /** The Constant BASE_URL. */
    private static final String BASE_URL = "@BASE_URL@";

    /** The media server config. */
    private MediaServerConfigDto mediaServerConfig;

    /** The resource config. */
    private ResourceConfigDto resourceConfig;

    /**
     * Read resources.
     * 
     * @param resourcesFile the resources file
     * @return the resource server config dto
     * @throws SAXException the sAX exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public MediaServerConfigDto readResources(File resourcesFile) throws SAXException, IOException {
        XMLReader xMLReader = XMLReaderFactory.createXMLReader();
        // Set the ContentHandler...
        xMLReader.setContentHandler(this);
        // Parse the file...
        FileInputStream configXmlStream = new FileInputStream(resourcesFile);
        xMLReader.parse(new InputSource(configXmlStream));
        return mediaServerConfig;
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startDocument()
     */
    @Override
    public void startDocument() throws SAXException {
        mediaServerConfig = new MediaServerConfigDto();
        mediaServerConfig.setResourcesMap(new HashMap<String, ResourceConfigDto>());
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        if (localName.equals(ResourceMappingConstants.MEDIA_SERVER)) {
            String baseUrl = attributes.getValue(ResourceMappingConstants.BASE_URL);
            String passportUrlPattern = attributes.getValue(ResourceMappingConstants.PASSPORT);
            String thumbnailUrlPattern = attributes.getValue(ResourceMappingConstants.THUMBNAIL);

            if (StringUtil.isNullOrBlank(baseUrl)
                    || StringUtil.isNullOrBlank(passportUrlPattern)
                    || StringUtil.isNullOrBlank(thumbnailUrlPattern)) {
                throw new SAXException("Missing one of these attributes. "
                        + ResourceMappingConstants.BASE_URL + ", "
                        + ResourceMappingConstants.PASSPORT + " or "
                        + ResourceMappingConstants.THUMBNAIL);
            }
            mediaServerConfig.setBaseUrl(baseUrl);
            mediaServerConfig.setPassportUrlPattern(passportUrlPattern);
            mediaServerConfig.setThumbnailUrlPattern(thumbnailUrlPattern);
        } else if (localName.equals(ResourceMappingConstants.RESOURCE)) {
            resourceConfig = new ResourceConfigDto();
            String id = attributes.getValue(ResourceMappingConstants.ID);
            String url = parseResourceUrl(attributes.getValue(ResourceMappingConstants.URL));

            if (StringUtil.isNullOrBlank(url)) {
                throw new SAXException("A resource must have URL. Missing for resource with id " + id);
            }
            resourceConfig.setId(id);
            resourceConfig.setUrl(url);
            Map<String, ResourceConfigDto> resourcesMap = mediaServerConfig.getResourcesMap();
            resourcesMap.put(id, resourceConfig);
        } else if (localName.equals(ResourceMappingConstants.PATTERN)) {
            String excludesValue = attributes.getValue(ResourceMappingConstants.EXCLUDES);
            if (!StringUtil.isNullOrBlank(excludesValue)) {
                List<String> excludes = new ArrayList<String>();
                if (excludesValue.indexOf(",") != -1) {
                    String[] split = excludesValue.split(",");
                    for (String excludeValue : split) {
                        excludes.add(excludeValue);
                    }
                } else {
                    excludes.add(excludesValue);
                }
                resourceConfig.setExcludes(excludes);
            }
        } else if (localName.equals(ResourceMappingConstants.STATIC_RESOURCE)) {
            if (resourceConfig == null) {
                throw new SAXException(ResourceMappingConstants.STATIC_RESOURCE + " must be within " + ResourceMappingConstants.RESOURCE);
            }
            ResourceConfigDto staticResourceConfig = new ResourceConfigDto();
            String id = attributes.getValue(ResourceMappingConstants.ID);
            String name = attributes.getValue(ResourceMappingConstants.NAME);

            if (StringUtil.isNullOrBlank(id) || StringUtil.isNullOrBlank(name)) {
                throw new SAXException(ResourceMappingConstants.STATIC_RESOURCE + " is missing 'id' or 'name' within " + ResourceMappingConstants.RESOURCE);
            }
            staticResourceConfig.setId(id);
            staticResourceConfig.setUrl(parseResourceUrl(resourceConfig.getUrl()));
            staticResourceConfig.setName(name);

            Map<String, ResourceConfigDto> resourcesMap = mediaServerConfig.getResourcesMap();
            resourcesMap.put(id, staticResourceConfig);
        }
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (localName.equals(ResourceMappingConstants.STATIC_RESOURCE)) {
            Map<String, ResourceConfigDto> resourcesMap = mediaServerConfig.getResourcesMap();
            resourcesMap.remove(null);
        }
    }

    /**
     * Parses the resource url.
     * 
     * @param url the url
     * @return the string
     */
    private String parseResourceUrl(String url) {
        /*
        @MYSCHOOL.NAME@ - replaces with myschool name, replaced only during build
        @BASE_URL@ - Represents base url, defined in base-url
        @PARENT_URL@ - Represents parent url
        @RESOURCE_NAME@ - Resource Name
        */
        if (url != null && url.trim().length() != 0) {
            url = url.replaceAll(BASE_URL, mediaServerConfig.getBaseUrl());
        }
        return url;
    }

}
