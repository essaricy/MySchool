package com.myschool.infra.application.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.myschool.application.dto.FeatureDto;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.PropertiesUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.infra.application.constants.AppConfigConstants;
import com.myschool.infra.application.dto.AgentDto;
import com.myschool.infra.application.dto.AppConfigDto;
import com.myschool.infra.application.dto.MySchoolDto;
import com.myschool.infra.filesystem.util.FileUtil;

/**
 * The Class AppConfigReader.
 */
@Component
public class AppConfigReader extends DefaultHandler {

    /** The app config. */
    private AppConfigDto appConfig;

    /** The myschool. */
    private MySchoolDto myschool;

    /** The agent dtos. */
    private List<AgentDto> agentDtos;

    /** The features. */
    private List<FeatureDto> features;

    /** The file system properties. */
    private Properties fileSystemProperties;

    /**
     * Gets the app config.
     *
     * @param appConfigPath the app config path
     * @param fileSystemProperties the file system properties
     * @return the app config
     * @throws ConfigurationException the configuration exception
     */
    public AppConfigDto getAppConfig(String appConfigPath, Properties fileSystemProperties) throws ConfigurationException {
        String message = "Application configuration file is not found.";
        try {
            if (StringUtil.isNullOrBlank(appConfigPath)) {
                throw new ConfigurationException(message);
            }
            this.fileSystemProperties = fileSystemProperties;
            File appConfigFile = FileUtil.checkFile(appConfigPath, message, message);

            readAppConfig(appConfigFile);
        } catch (SAXException saxException) {
            throw new ConfigurationException(saxException.getMessage(), saxException);
        } catch (IOException ioException) {
            throw new ConfigurationException(ioException.getMessage(), ioException);
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(fileSystemException.getMessage(), fileSystemException);
        }
        return appConfig;
    }

    /**
     * Read app config.
     *
     * @param appConfigFile the app config file
     * @throws SAXException the sAX exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void readAppConfig(File appConfigFile) throws SAXException, IOException {
        // TODO validate app-config.xml file against schema
        XMLReader xMLReader = XMLReaderFactory.createXMLReader();
        // Set the ContentHandler...
        xMLReader.setContentHandler(this);
        // Parse the file...
        FileInputStream configXmlStream = new FileInputStream(appConfigFile);
        xMLReader.parse(new InputSource(configXmlStream));
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String namespaceURI, String localName,
            String qName, Attributes attributes) throws SAXException {

        if (localName.equalsIgnoreCase(AppConfigConstants.ELEMENT_APP_CONFIG)) {
            appConfig = new AppConfigDto();
        } else if (localName.equalsIgnoreCase(AppConfigConstants.ELEMENT_AGENTS)) {
            agentDtos = new ArrayList<AgentDto>();
        } else if (localName.equalsIgnoreCase(AppConfigConstants.ELEMENT_AGENT)) {
            AgentDto agentConfig = new AgentDto();
            agentConfig.setAgentId(attributes.getValue(AppConfigConstants.ATTRIBUTE_ID));
            agentConfig.setClassName(attributes.getValue(AppConfigConstants.ATTRIBUTE_CLASS));
            String configFilePropertyKey = attributes.getValue(AppConfigConstants.ATTRIBUTE_CONFIG);
            if (!StringUtil.isNullOrBlank(configFilePropertyKey)) {
                String resolvedConfigFilePath = PropertiesUtil.resolveProperty(fileSystemProperties, configFilePropertyKey);
                agentConfig.setConfigFile(new File(resolvedConfigFilePath));
            }
            agentDtos.add(agentConfig);
        } else if (localName.equalsIgnoreCase(AppConfigConstants.ELEMENT_FEATURES)) {
            features = new ArrayList<FeatureDto>();
        } else if (localName.equalsIgnoreCase(AppConfigConstants.ELEMENT_FEATURE)) {
            FeatureDto featureDto = new FeatureDto();
            featureDto.setName(attributes.getValue(AppConfigConstants.ATTRIBUTE_ID));
            featureDto.setDescription(attributes.getValue(AppConfigConstants.ATTRIBUTE_DESCRIPTION));
            featureDto.setImagePath(attributes.getValue(AppConfigConstants.ATTRIBUTE_VALUE));
            features.add(featureDto);
        } else if (localName.equalsIgnoreCase(AppConfigConstants.ELEMENT_MYSCHOOL)) {
            myschool = new MySchoolDto();
            myschool.setBaseDir(new File(attributes.getValue(AppConfigConstants.ATTRIBUTE_BASE_DIR)));
            myschool.setMySchoolName(attributes.getValue(AppConfigConstants.ATTRIBUTE_ID));
            myschool.setWebUrl(attributes.getValue(AppConfigConstants.ATTRIBUTE_WEB_URL));
        } else if (localName.equalsIgnoreCase(AppConfigConstants.ELEMENT_FEATURE)) {
            FeatureDto featureDto = new FeatureDto();
            featureDto.setName(attributes.getValue(AppConfigConstants.ATTRIBUTE_ID));
            featureDto.setDescription(attributes.getValue(AppConfigConstants.ATTRIBUTE_DESCRIPTION));
            featureDto.setImagePath(attributes.getValue(AppConfigConstants.ATTRIBUTE_VALUE));
            features.add(featureDto);
        }
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (localName.equalsIgnoreCase(AppConfigConstants.ELEMENT_APP_CONFIG)) {
            appConfig.setAgentDtos(agentDtos);
            appConfig.setFeatures(features);
            appConfig.setMyschool(myschool);
        }
    }

}
