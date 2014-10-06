package com.myschool.infra.middleware.readers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.util.XmlUtil;
import com.myschool.infra.middleware.constants.QueueCategory;
import com.myschool.infra.middleware.dto.MessageQueue;
import com.myschool.infra.middleware.dto.MqConfiguration;

/**
 * The Class MessageQueueConfigReader.
 */
@Component
public class MessageQueueConfigReader extends DefaultHandler {

    /** The Constant ELEMENT_MQ_CONFIGURATION. */
    private static final String ELEMENT_MQ_CONFIGURATION = "mq-configuration";

    /** The Constant ELEMENT_MESSAGE_QUEUES. */
    private static final String ELEMENT_MESSAGE_QUEUES = "message-queues";

    /** The Constant ELEMENT_MESSAGE_QUEUE. */
    private static final String ELEMENT_MESSAGE_QUEUE = "message-queue";

    /** The Constant ATTRIBUTE_CATEGORY. */
    private static final String ATTRIBUTE_CATEGORY = "category";

    /** The Constant ATTRIBUTE_DESCRIPTION. */
    private static final String ATTRIBUTE_DESCRIPTION = "description";

    /** The Constant ATTRIBUTE_OUTPUT. */
    private static final String ATTRIBUTE_OUTPUT = "output";

    /** The Constant ATTRIBUTE_FAIL. */
    private static final String ATTRIBUTE_FAIL = "fail";

    /** The Constant ATTRIBUTE_ERROR. */
    private static final String ATTRIBUTE_ERROR = "error";

    /** The mq configuration. */
    private MqConfiguration mqConfiguration;

    /** The message queues. */
    private List<MessageQueue> messageQueues;

    /**
     * Instantiates a new queue config reader.
     *
     * @param queueConfigXml the queue config xml
     * @param queueConfigSchema the queue config schema
     * @throws ConfigurationException the configuration exception
     */
    public void readConfiguraion(File queueConfigXml, File queueConfigSchema)
            throws ConfigurationException {
        InputStream configXmlStream = null;
        InputStream configXsdStream = null;
        try {
            configXmlStream = new FileInputStream(queueConfigXml);
            //configXsdStream = new FileInputStream(queueConfigSchema);
            // TODO validate against the schema using XmlUtil
            ////validateConfiguration(configXmlStream, configXsdStream);
            //configXmlStream = new FileInputStream(queueConfigXml);
            readConfiguration(configXmlStream);
        } catch (SAXException saxException) {
            throw new ConfigurationException(saxException.getMessage(),
                    saxException);
        } catch (IOException ioException) {
            throw new ConfigurationException(ioException.getMessage(),
                    ioException);
        } catch (NumberFormatException numberFormatException) {
            throw new ConfigurationException(
                    numberFormatException.getMessage(), numberFormatException);
        } finally {
            try {
                if (configXmlStream != null) {
                    configXmlStream.close();
                }
                if (configXsdStream != null) {
                    configXsdStream.close();
                }
            } catch (IOException ioException) {
                throw new ConfigurationException(ioException.getMessage(),
                        ioException);
            }
        }
    }

    /**
     * Read configuration.
     * 
     * @param inputStream the input stream
     * @throws SAXException the sAX exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void readConfiguration(InputStream inputStream) throws SAXException,
            IOException {
        // Create SAX 2 parser...
        XMLReader xMLReader = XMLReaderFactory.createXMLReader();
        // Set the ContentHandler...
        // XMLParser xMLParser = new XMLParser();
        xMLReader.setContentHandler(this);
        // Parse the file...
        xMLReader.parse(new InputSource(inputStream));
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String namespaceURI, String localName,
            String qName, Attributes attributes) throws SAXException {
        if (localName.equalsIgnoreCase(ELEMENT_MQ_CONFIGURATION)) {
            mqConfiguration = new MqConfiguration();
        } else if (localName.equalsIgnoreCase(ELEMENT_MESSAGE_QUEUES)) {
            messageQueues = new ArrayList<MessageQueue>();
        } else if (localName.equalsIgnoreCase(ELEMENT_MESSAGE_QUEUE)) {
            MessageQueue messageQueue = new MessageQueue();
            messageQueue.setDescription(XmlUtil.getAttribute(attributes, ATTRIBUTE_DESCRIPTION));
            messageQueue.setError(XmlUtil.getAttribute(attributes, ATTRIBUTE_ERROR));
            messageQueue.setFail(XmlUtil.getAttribute(attributes, ATTRIBUTE_FAIL));
            messageQueue.setQueueCategory(QueueCategory.get(XmlUtil.getAttribute(attributes, ATTRIBUTE_CATEGORY)));
            messageQueue.setOutput(XmlUtil.getAttribute(attributes, ATTRIBUTE_OUTPUT));
            messageQueues.add(messageQueue);
        }
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if (localName.equalsIgnoreCase(ELEMENT_MQ_CONFIGURATION)) {
            mqConfiguration.setMessageQueues(messageQueues);
        }
    }

    /**
     * Gets the configuration.
     * 
     * @param mqConfigFile the mq config file
     * @return the configuration
     * @throws ConfigurationException the configuration exception
     */
    public MqConfiguration getConfiguration(File mqConfigFile) throws ConfigurationException {
        readConfiguraion(mqConfigFile, null);
        return mqConfiguration;
    }

}