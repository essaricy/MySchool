package com.myschool.infra.filesystem.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.ResourceUtil;
import com.myschool.infra.filesystem.constants.NotificationConfigConstants;
import com.myschool.infra.filesystem.dto.DirectoryDto;
import com.myschool.notification.constants.NotificationEndPoint;
import com.myschool.notification.constants.NotificationMode;
import com.myschool.notification.constants.NotificationType;
import com.myschool.notification.dto.NotificationTemplateDto;

/**
 * The Class NotificationConfigReader.
 */
@Component
public class NotificationConfigReader extends DefaultHandler {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(NotificationConfigReader.class);

    /**
     * Gets the notification templates.
     *
     * @param configFile the config file
     * @param mainNotificationDirectory the main notification directory
     * @param testNotificationDirectory the test notification directory
     * @return the notification templates
     * @throws FileSystemException the file system exception
     */
    public List<NotificationTemplateDto> getNotificationTemplates(
            File configFile, DirectoryDto mainNotificationDirectory,
            DirectoryDto testNotificationDirectory)
            throws FileSystemException {

        InputStream inputStream = null;
        List<NotificationTemplateDto> notificationTemplates = new ArrayList<NotificationTemplateDto>();
        try {
            inputStream = new FileInputStream(configFile);
            if (inputStream != null) {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                //Using factory get an instance of document builder
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                //parse using builder to get DOM representation of the XML file
                Document document = documentBuilder.parse(inputStream);
                File mainNotificationFile = mainNotificationDirectory.getFile();
                File testNotificationFile = testNotificationDirectory.getFile();
                notificationTemplates = readNotificationTemplates(document, mainNotificationFile, testNotificationFile);
            }
        } catch (ParserConfigurationException parserConfigurationException) {
            throw new FileSystemException(parserConfigurationException.getMessage(), parserConfigurationException);
        } catch (SAXException saxException) {
            throw new FileSystemException(saxException.getMessage(), saxException);
        } catch (IOException ioException) {
            throw new FileSystemException(ioException.getMessage(), ioException);
        } finally {
            ResourceUtil.releaseResource(inputStream);
        }
        return notificationTemplates;
    }

    /**
     * Read notification templates.
     *
     * @param document the document
     * @param notificationDir the notification dir
     * @param testNotificationDir the test notification dir
     * @return the list
     */
    public List<NotificationTemplateDto> readNotificationTemplates(
            Document document, File notificationDir, File testNotificationDir) {

        File notificationModeDir = null;
        File notificationEndPointDir = null;
        File testNotificationEndPointDir = null;

        //get the root element
        Element elelment = document.getDocumentElement();
        Element notificationNode = null;
        Element templateElelment = null;
        NodeList notificationNodeList = null;
        NodeList templateNodeList = null;

        NotificationMode mode = null;
        NotificationEndPoint endPoint = null;
        NotificationType notificationType = null;
        NotificationTemplateDto notificationTemplate = null;
        List<NotificationTemplateDto> notificationTemplates = null;

        //get a node list of elements
        notificationNodeList = elelment.getElementsByTagName(NotificationConfigConstants.ELEMENT_NOTIFICATION);
        if(notificationNodeList != null && notificationNodeList.getLength() > 0) {
            notificationTemplates = new ArrayList<NotificationTemplateDto>();

            for(int notificationNodeIndex = 0 ; notificationNodeIndex < notificationNodeList.getLength(); notificationNodeIndex++) {
                notificationNode = (Element)notificationNodeList.item(notificationNodeIndex);
                if (notificationNode != null) {
                    mode = NotificationMode.get(notificationNode.getAttribute(NotificationConfigConstants.ATTRIBUTE_MODE));
                    endPoint = NotificationEndPoint.get(notificationNode.getAttribute(NotificationConfigConstants.ATTRIBUTE_END_POINT));
                    notificationModeDir = new File(notificationDir, mode.toString().toLowerCase());
                    if (!notificationModeDir.exists()) {
                        LOGGER.warn("Notification mode directory does not exists.");
                        continue;
                    }
                    notificationEndPointDir = new File(notificationModeDir, endPoint.toString().toLowerCase());
                    if (!notificationEndPointDir.exists()) {
                        LOGGER.warn("Notification end point directory does not exists.");
                        continue;
                    }
                    testNotificationEndPointDir = new File(testNotificationDir, endPoint.toString().toLowerCase());
                    if (!testNotificationEndPointDir.exists()) {
                        LOGGER.warn("Notification end point test directory does not exists.");
                        continue;
                    }
                    templateNodeList = notificationNode.getElementsByTagName(NotificationConfigConstants.ELEMENT_TEMPLATE);
                    for(int templateNodeIndex = 0; templateNodeIndex < templateNodeList.getLength(); templateNodeIndex++) {
                        //get the employee element
                        templateElelment = (Element)templateNodeList.item(templateNodeIndex);
                        String notificationTypeName = templateElelment.getAttribute(NotificationConfigConstants.ATTRIBUTE_NAME);
                        notificationType = NotificationType.get(notificationTypeName);
                        String templateFileValue = templateElelment.getAttribute(NotificationConfigConstants.ATTRIBUTE_TEMPLATE_FILE);
                        String testFileValue = templateElelment.getAttribute(NotificationConfigConstants.ATTRIBUTE_TEST_FILE);

                        File templateFile = new File(notificationEndPointDir, templateFileValue);
                        if (templateFile.exists()) {
                            notificationTemplate = new NotificationTemplateDto();
                            notificationTemplate.setNotificationEndPoint(endPoint);
                            notificationTemplate.setNotificationMode(mode);
                            notificationTemplate.setNotificationType(notificationType);
                            notificationTemplate.setTemplateFile(templateFile);

                            File testFile = new File(testNotificationEndPointDir, testFileValue);
                            if (testFile.exists()) {
                                notificationTemplate.setTestFile(testFile);
                            } else {
                                LOGGER.warn("Test file (" + testFileValue + ") does not exist at " + testNotificationEndPointDir);
                            }
                            //add it to list
                            notificationTemplates.add(notificationTemplate);
                        } else {
                            LOGGER.warn("Template file (" + templateFileValue + ") does not exist at " + notificationEndPointDir);
                        }
                    }
                }
            }
        }
        return notificationTemplates;
    }

}
