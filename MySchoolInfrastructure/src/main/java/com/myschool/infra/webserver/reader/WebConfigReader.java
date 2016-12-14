package com.myschool.infra.webserver.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.file.util.FileUtil;
import com.myschool.infra.web.dto.WebConfig;
import com.myschool.infra.web.dto.WebResource;
import com.myschool.infra.webserver.constant.WebConfigConstants;

/**
 * The Class WebConfigReader.
 */
@Component
public class WebConfigReader extends DefaultHandler {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(WebConfigReader.class);

    /** The web config. */
    private WebConfig webConfig;

    /**
     * Gets the web config.
     *
     * @param configFile the config file
     * @return the web config
     * @throws ConfigurationException the configuration exception
     */
    public WebConfig getWebConfig(
            File configFile) throws ConfigurationException {
        try {
            LOGGER.info("Loading web configuration.");
            FileUtil.checkFile(configFile, "Missing configuration file", "Invalid configuration file");
            LOGGER.info("Reading configuration.");
            readWebConfig(configFile);
        } catch (SAXException saxException) {
            throw new ConfigurationException(saxException.getMessage(), saxException);
        } catch (IOException ioException) {
            throw new ConfigurationException(ioException.getMessage(), ioException);
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(fileSystemException.getMessage(), fileSystemException);
        }
        return webConfig;
    }

    /**
     * Read web config.
     *
     * @param configFile the config file
     * @throws SAXException the SAX exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void readWebConfig(File configFile) throws SAXException, IOException {
        XMLReader xMLReader = XMLReaderFactory.createXMLReader();
        // Set the ContentHandler...
        xMLReader.setContentHandler(this);
        // Parse the file...
        FileInputStream configXmlStream = new FileInputStream(configFile);
        xMLReader.parse(new InputSource(configXmlStream));
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String namespaceURI, String localName,
            String qName, Attributes attributes) throws SAXException {
        if (localName.equalsIgnoreCase(WebConfigConstants.WEB_CONFIG)) {
            webConfig = new WebConfig();
            webConfig.setUrl(attributes.getValue(WebConfigConstants.URL));
        } else if (localName.equalsIgnoreCase(WebConfigConstants.WEB_RESOURCES)) {
            webConfig.setWebResources(new ArrayList<WebResource>());
        } else if (localName.equalsIgnoreCase(WebConfigConstants.WEB_RESOURCE)) {
            WebResource webResource = new WebResource();
            webResource.setImageUrl(attributes.getValue(WebConfigConstants.IMAGE_URL));
            webResource.setLinkUrl(attributes.getValue(WebConfigConstants.LINK_URL));
            webResource.setName(attributes.getValue(WebConfigConstants.NAME));
            webResource.setPassportUrl(attributes.getValue(WebConfigConstants.PASSPORT_URL));
            webResource.setThumbUrl(attributes.getValue(WebConfigConstants.THUMB_URL));
            List<WebResource> webResources = webConfig.getWebResources();
            webResources.add(webResource);
        }
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
    }

}
