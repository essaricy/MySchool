package com.myschool.infra.ojo.agent;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.infra.oxo.dto.ObjectXmlFieldDto;
import com.myschool.infra.oxo.dto.ObjectXmlMappingDto;
import com.myschool.infra.oxo.reader.OxoMappingReader;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

/**
 * The Class XStreamJsonAgent.
 */
@Component
public class XStreamJsonAgent extends OjoAgent {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(XStreamJsonAgent.class);

    /** The Constant X_STREAM. */
    private static final XStream X_STREAM_JSON_READER = new XStream(new JettisonMappedXmlDriver());

    /** The Constant X_STREAM_JSON_WRITER. */
    private static final XStream X_STREAM_JSON_WRITER = new XStream(new JsonHierarchicalStreamDriver());

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

        // Use OXO Configuration mapping file as it is same for OJO Agent
        List<ObjectXmlMappingDto> mappings = objectXmlMappingReader.readMappings(configFile);

        if (mappings != null && !mappings.isEmpty()) {
            for (ObjectXmlMappingDto objectXmlMapping : mappings) {
                try {
                    name = objectXmlMapping.getName();
                    type = objectXmlMapping.getType();
                    objectClass = Class.forName(type);
                    objectXmlFields = objectXmlMapping.getObjectXmlFields();

                    X_STREAM_JSON_READER.alias(name, objectClass);
                    X_STREAM_JSON_WRITER.alias(name, objectClass);
                    LOGGER.debug("Alias '" + name + "' will be used for type " + type);

                    if (objectXmlFields != null && !objectXmlFields.isEmpty()) {
                        for (ObjectXmlFieldDto objectXmlField : objectXmlFields) {
                            fieldAlias = objectXmlField.getAlias();
                            fieldName = objectXmlField.getName();
                            String fieldType = objectXmlField.getType();

                            // Erase sensitive information
                            if (fieldType.equals(File.class.getName())) {
                                X_STREAM_JSON_READER.omitField(objectClass, fieldName);
                                X_STREAM_JSON_WRITER.omitField(objectClass, fieldName);
                            } else {
                                // Use attributes instead of elements.
                                X_STREAM_JSON_READER.useAttributeFor(objectClass, fieldName);
                                X_STREAM_JSON_READER.aliasField(fieldAlias, objectClass, fieldName);
                                X_STREAM_JSON_WRITER.useAttributeFor(objectClass, fieldName);
                                X_STREAM_JSON_WRITER.aliasField(fieldAlias, objectClass, fieldName);
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
     * @see com.myschool.infra.ojo.agent.OjoAgent#toJson(java.lang.Object)
     */
    @Override
    public String toJson(Object object) {
        if (object instanceof Serializable) {
            return X_STREAM_JSON_WRITER.toXML(object);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.oxo.agent.OxoAgent#toObject(java.lang.String)
     */
    @Override
    public Object toObject(String xmlContent) {
        return X_STREAM_JSON_READER.fromXML(xmlContent);
    }

}
