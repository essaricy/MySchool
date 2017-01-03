package com.myschool.infra.template.reader;

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
import com.myschool.infra.template.constants.TemplateConfigConstants;
import com.myschool.template.dto.Template;
import com.myschool.template.dto.TemplateConfig;
import com.myschool.template.dto.TemplateGroup;
import com.quasar.core.exception.FileSystemException;
import com.quasar.core.util.FileUtil;

/**
 * The Class TemplateConfigReader.
 */
@Component
public class TemplateConfigReader extends DefaultHandler {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(TemplateConfigReader.class);

    /** The template config. */
    private TemplateConfig templateConfig;

    /** The template group. */
    private TemplateGroup templateGroup;

    /** The template. */
    private Template template;

    /**
     * Gets the notification config.
     *
     * @param configFile the config file
     * @return the notification config
     * @throws ConfigurationException the configuration exception
     */
    public TemplateConfig getNotificationConfig(
            File configFile) throws ConfigurationException {
        try {
            LOGGER.info("Loading template configuration.");
            FileUtil.checkFile(configFile, "Missing configuration file", "Invalid configuration file");
            LOGGER.info("Reading configuration.");
            readTemplateConfig(configFile);
        } catch (SAXException saxException) {
            throw new ConfigurationException(saxException.getMessage(), saxException);
        } catch (IOException ioException) {
            throw new ConfigurationException(ioException.getMessage(), ioException);
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(fileSystemException.getMessage(), fileSystemException);
        }
        return templateConfig;
    }

    /**
     * Read template config.
     *
     * @param configFile the config file
     * @throws SAXException the SAX exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void readTemplateConfig(File configFile) throws SAXException, IOException {
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
        if (localName.equalsIgnoreCase(TemplateConfigConstants.TEMPLATE_CONFIG)) {
            templateConfig = new TemplateConfig();
            templateConfig.setBasePath(attributes.getValue(TemplateConfigConstants.BASE_DIR));
        } else if (localName.equalsIgnoreCase(TemplateConfigConstants.TEMPLATE_GROUP)) {
            templateGroup = new TemplateGroup();
            templateGroup.setName(attributes.getValue(TemplateConfigConstants.NAME));
            templateGroup.setUsage(attributes.getValue(TemplateConfigConstants.USAGE));
            templateGroup.setDeliveryMethod(attributes.getValue(TemplateConfigConstants.DELIVERY_METHOD));
            templateGroup.setExampleFileNamePattern(attributes.getValue(TemplateConfigConstants.EXAMPLE_FILE_NAME_PATTERN));
            templateGroup.setTemplateFileNamePattern(attributes.getValue(TemplateConfigConstants.TEMPLATE_FILE_NAME_PATTERN));
        } else if (localName.equalsIgnoreCase(TemplateConfigConstants.TEMPLATE)) {
            template = new Template();
            template.setId(attributes.getValue(TemplateConfigConstants.ID));
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
        if (localName.equalsIgnoreCase(TemplateConfigConstants.TEMPLATE)) {
            List<Template> templates = templateGroup.getTemplates();
            if (templates == null) {
                templates = new ArrayList<Template>();
                templateGroup.setTemplates(templates);
            }
            templates.add(template);
        } else if (localName.equalsIgnoreCase(TemplateConfigConstants.TEMPLATE_GROUP)) {
            List<TemplateGroup> templateGroups = templateConfig.getTemplateGroups();
            if (templateGroups == null) {
                templateGroups = new ArrayList<TemplateGroup>();
                templateConfig.setTemplateGroups(templateGroups);
            }
            templateGroups.add(templateGroup);
        }
    }

}
