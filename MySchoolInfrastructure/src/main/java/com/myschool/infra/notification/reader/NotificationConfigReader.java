package com.myschool.infra.notification.reader;

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
import com.myschool.common.util.DateUtil;
import com.myschool.file.util.FileUtil;
import com.myschool.infra.notification.constants.NotificationConfigConstants;
import com.myschool.notification.dto.Notification;
import com.myschool.notification.dto.NotificationConfig;

/**
 * The Class NotificationConfigReader.
 */
@Component
public class NotificationConfigReader extends DefaultHandler {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(NotificationConfigReader.class);

    /** The notification config. */
    private NotificationConfig notificationConfig;

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
            LOGGER.info("Loading notifications.");
            FileUtil.checkFile(configFile, "Missing notifications configuration file", "Invalid notifications configuration file");
            LOGGER.info("Reading configuration.");
            readNotifications(configFile);
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
     *
     * @param configFile the config file
     * @throws SAXException the SAX exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void readNotifications(File configFile) throws SAXException, IOException {
        XMLReader xMLReader = XMLReaderFactory.createXMLReader();
        // Set the ContentHandler...
        xMLReader.setContentHandler(this);
        // Parse the file...
        FileInputStream configXmlStream = new FileInputStream(configFile);
        xMLReader.parse(new InputSource(configXmlStream));
    }

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
        } else if (localName.equalsIgnoreCase(NotificationConfigConstants.NOTIFICATIONS)) {
            notificationConfig.setNotifications(new ArrayList<Notification>());
        } else if (localName.equalsIgnoreCase(NotificationConfigConstants.NOTIFICATION)) {
            Notification notification = new Notification();
            notification.setId(attributes.getValue(NotificationConfigConstants.ID));
            notification.setTitle(attributes.getValue(NotificationConfigConstants.TITLE));
            notification.setValidity(DateUtil.getMillisFromOffset(attributes.getValue(NotificationConfigConstants.VALIDITY)));

            List<Notification> notifications = notificationConfig.getNotifications();
            notifications.add(notification);
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
