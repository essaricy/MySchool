package com.myschool.infra.notification.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
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
import com.myschool.infra.filesystem.util.FileUtil;
import com.myschool.infra.notification.constants.NotificationConfigConstants;
import com.myschool.notification.constants.NotificationMedium;
import com.myschool.notification.dto.NotificationConfig;
import com.myschool.notification.dto.NotificationTemplate;

/**
 * The Class NotificationConfigReader.
 */
@Component
public class NotificationConfigReader extends DefaultHandler {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(NotificationConfigReader.class);

    //private static final String FILE_HIERARCHY = "{0}/{1}/{2}/{3}";
    private static final String FILE_HIERARCHY = "{0}/{1}/{2}";

    /** The notification config. */
    private NotificationConfig notificationConfig;

    /** The notification template. */
    private NotificationTemplate notificationTemplate;

    /** The medium. */
    private String medium;

    /** The category. */
    private String category;

    /**
     * Gets the notification config.
     *
     * @param configFile the config file
     * @return the notification config
     * @throws ConfigurationException the configuration exception
     */
    public NotificationConfig getNotificationConfig(
            File configFile) throws ConfigurationException {
        try {
            LOGGER.info("Loading notification templates.");
            FileUtil.checkFile(configFile, "Missing notifications configuration file", "Invalid notifications configuration file");
            LOGGER.info("Reading configuration.");
            readNotificationTemplates(configFile);
        } catch (SAXException saxException) {
            throw new ConfigurationException(saxException.getMessage(), saxException);
        } catch (IOException ioException) {
            throw new ConfigurationException(ioException.getMessage(), ioException);
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(fileSystemException.getMessage(), fileSystemException);
        }
        return notificationConfig;
    }

    /**
     * Read notification templates.
     *
     * @param configFile the config file
     * @throws SAXException the SAX exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void readNotificationTemplates(File configFile) throws SAXException, IOException {
        XMLReader xMLReader = XMLReaderFactory.createXMLReader();
        // Set the ContentHandler...
        xMLReader.setContentHandler(this);
        // Parse the file...
        FileInputStream configXmlStream = new FileInputStream(configFile);
        xMLReader.parse(new InputSource(configXmlStream));
    }

    /**
     * Start element.
     *
     * @param namespaceURI the namespace uri
     * @param localName the local name
     * @param qName the q name
     * @param attributes the attributes
     * @throws SAXException the SAX exception
     */
    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
     * java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String namespaceURI, String localName,
            String qName, Attributes attributes) throws SAXException {
        if (localName.equalsIgnoreCase(NotificationConfigConstants.NOTIFICATION_CONFIG)) {
            notificationConfig = new NotificationConfig();
            notificationConfig.setBaseDir(attributes.getValue(NotificationConfigConstants.BASE_DIR));
            notificationConfig.setSamplesBase(attributes.getValue(NotificationConfigConstants.SAMPLES_DIR));
            notificationConfig.setTemplatesBase(attributes.getValue(NotificationConfigConstants.TEMPLATES_DIR));
        } else if (localName.equalsIgnoreCase(NotificationConfigConstants.NOTIFICATIONS)) {
            notificationConfig.setNotificationTemplates(new ArrayList<NotificationTemplate>());
        } else if (localName.equalsIgnoreCase(NotificationConfigConstants.NOTIFICATION)) {
            medium = attributes.getValue(NotificationConfigConstants.MEDIUM);
            category = attributes.getValue(NotificationConfigConstants.CATEGORY);
        } else if (localName.equalsIgnoreCase(NotificationConfigConstants.TEMPLATE)) {
            notificationTemplate = new NotificationTemplate();
            notificationTemplate.setCategory(category);
            notificationTemplate.setMedium(NotificationMedium.get(medium));
            notificationTemplate.setName(attributes.getValue(NotificationConfigConstants.NAME));

            notificationTemplate.setSample(MessageFormat.format(
                    FILE_HIERARCHY, medium.toLowerCase(), category.toLowerCase(),
                    attributes.getValue(NotificationConfigConstants.SAMPLE)));
            notificationTemplate.setTemplate(MessageFormat.format(
                    FILE_HIERARCHY, medium.toLowerCase(), category.toLowerCase(),
                    attributes.getValue(NotificationConfigConstants.FILE)));
        }
    }

    /**
     * End element.
     *
     * @param uri the uri
     * @param localName the local name
     * @param qName the q name
     * @throws SAXException the SAX exception
     */
    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (localName.equalsIgnoreCase(NotificationConfigConstants.TEMPLATE)) {
            List<NotificationTemplate> notificationTemplates = notificationConfig.getNotificationTemplates();
            notificationTemplates.add(notificationTemplate);
        }
    }


}
