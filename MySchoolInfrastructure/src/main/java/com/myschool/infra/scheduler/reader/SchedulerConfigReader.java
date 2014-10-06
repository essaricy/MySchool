package com.myschool.infra.scheduler.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import com.myschool.infra.scheduler.dto.SchedulerConfigDto;

/**
 * The Class SchedulerConfigReader.
 */
@Component
public class SchedulerConfigReader extends DefaultHandler {

    /** The scheduler configs. */
    private List<SchedulerConfigDto> schedulerConfigs;

    /**
     * Gets the scheduler config.
     *
     * @param configFile the config file
     * @return the scheduler config
     * @throws ConfigurationException the configuration exception
     */
    public List<SchedulerConfigDto> getSchedulerConfig(File configFile)
            throws ConfigurationException {
        try {
            // TODO validate against schema
            XMLReader xMLReader = XMLReaderFactory.createXMLReader();
            // Set the ContentHandler...
            xMLReader.setContentHandler(this);
            // Parse the file...
            FileInputStream configXmlStream = new FileInputStream(configFile);
            xMLReader.parse(new InputSource(configXmlStream));
        } catch (SAXException saxException) {
            throw new ConfigurationException(saxException.getMessage(),
                    saxException);
        } catch (IOException ioException) {
            throw new ConfigurationException(ioException.getMessage(),
                    ioException);
        }
        return schedulerConfigs;
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String namespaceURI, String localName,
            String qName, Attributes attributes) throws SAXException {
        if (localName.equalsIgnoreCase(SchedulerConfigConstants.ELEMENT_SCHEDULERS_CONFIG)) {
            schedulerConfigs = new ArrayList<SchedulerConfigDto>();
        } else if (localName.equalsIgnoreCase(SchedulerConfigConstants.ELEMENT_SCHEDULERS_CONFIG)) {
            SchedulerConfigDto schedulerConfigDto = new SchedulerConfigDto();
            schedulerConfigDto.setClassName(attributes.getValue(SchedulerConfigConstants.ATTRIBUTE_CLASS));
            schedulerConfigDto.setDate(attributes.getValue(SchedulerConfigConstants.ATTRIBUTE_DATE));
            schedulerConfigDto.setDays(attributes.getValue(SchedulerConfigConstants.ATTRIBUTE_DAYS));
            schedulerConfigDto.setHour(attributes.getValue(SchedulerConfigConstants.ATTRIBUTE_HOUR));
            schedulerConfigDto.setMinute(attributes.getValue(SchedulerConfigConstants.ATTRIBUTE_MINUTE));
            schedulerConfigDto.setMonth(attributes.getValue(SchedulerConfigConstants.ATTRIBUTE_MONTH));
            schedulerConfigDto.setName(attributes.getValue(SchedulerConfigConstants.ATTRIBUTE_NAME));
            schedulerConfigDto.setSecond(attributes.getValue(SchedulerConfigConstants.ATTRIBUTE_SECOND));
            schedulerConfigDto.setYear(attributes.getValue(SchedulerConfigConstants.ATTRIBUTE_YEAR));
            schedulerConfigs.add(schedulerConfigDto);
        }
    }

}
