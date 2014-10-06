package com.myschool.infra.oxo.agent;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.apache.batik.xml.XMLException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.infra.oxo.dto.ObjectXmlFieldDto;
import com.myschool.infra.oxo.dto.ObjectXmlMappingDto;
import com.myschool.infra.oxo.reader.OxoMappingReader;
import com.thoughtworks.xstream.XStream;

/**
 * The Class XStreamAgent.
 */
@Component
public class XStreamAgent extends OxoAgent {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(XStreamAgent.class);

    /** The Constant X_STREAM. */
    private static final XStream X_STREAM = new XStream();

    /** The object xml mapping reader. */
    @Autowired
    private OxoMappingReader objectXmlMappingReader;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {
        String name = null;
        String type = null;
        String fieldAlias = null;
        String fieldName = null;

        Class<?> objectClass = null;
        List<ObjectXmlFieldDto> objectXmlFields = null;

        List<ObjectXmlMappingDto> mappings = objectXmlMappingReader.readMappings(configFile);

        if (mappings != null && !mappings.isEmpty()) {
            for (ObjectXmlMappingDto objectXmlMapping : mappings) {
                try {
                    name = objectXmlMapping.getName();
                    type = objectXmlMapping.getType();
                    objectClass = Class.forName(type);
                    objectXmlFields = objectXmlMapping.getObjectXmlFields();

                    X_STREAM.alias(name, objectClass);
                    //LOGGER.debug("Alias '" + name + "' will be used for type " + type);

                    if (objectXmlFields != null && !objectXmlFields.isEmpty()) {
                        for (ObjectXmlFieldDto objectXmlField : objectXmlFields) {
                            fieldAlias = objectXmlField.getAlias();
                            fieldName = objectXmlField.getName();
                            String fieldType = objectXmlField.getType();

                            // Erase sensitive information
                            if (fieldType.equals(File.class.getName())) {
                                X_STREAM.omitField(objectClass, fieldName);
                            } else {
                                // Use attributes instead of elements.
                                X_STREAM.useAttributeFor(objectClass, fieldName);
                                X_STREAM.aliasField(fieldAlias, objectClass, fieldName);
                                //LOGGER.debug("Alias '" + fieldAlias + "' will be used for field " + fieldName + " in " + objectClass);
                            }
                        }
                    }
                } catch (ClassNotFoundException classNotFoundException) {
                    LOGGER.error("Error creating alias for " + name);
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.Agent#validate()
     */
    @Override
    public void validate() throws AgentException {
        // No implementation planned
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.xml.agent.XmlAgent#toXml(java.lang.Object)
     */
    @Override
    public String toXml(Object object) {
        if (!(object instanceof Serializable)) {
            throw new XMLException("Object must be serializable to convert to XML.");
        }
        return X_STREAM.toXML(object);
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.oxo.agent.OxoAgent#toObject(java.lang.String)
     */
    @Override
    public Object toObject(String xmlContent) {
        return X_STREAM.fromXML(xmlContent);
    }

}
