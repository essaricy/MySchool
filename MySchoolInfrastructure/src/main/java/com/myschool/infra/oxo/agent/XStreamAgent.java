package com.myschool.infra.oxo.agent;

import java.io.File;
import java.io.Serializable;
import java.io.Writer;
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
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

/**
 * The Class XStreamAgent.
 */
@Component
public class XStreamAgent extends OxoAgent {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(XStreamAgent.class);

    /** The Constant X_STREAM. */
    private static XStream X_STREAM = new XStream();

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

        X_STREAM = new XStream(new DomDriver() {
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    protected void writeText(QuickWriter writer, String text) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    }
                };
            }
        });

        List<ObjectXmlMappingDto> mappings = objectXmlMappingReader.readMappings(configFile);

        if (mappings != null && !mappings.isEmpty()) {
            for (ObjectXmlMappingDto objectXmlMapping : mappings) {
                try {
                    name = objectXmlMapping.getName();
                    type = objectXmlMapping.getType();
                    objectClass = Class.forName(type);
                    objectXmlFields = objectXmlMapping.getObjectXmlFields();

                    X_STREAM.alias(name, objectClass);
                    //String implicitCollectionName = name + "List";
                    //System.out.println("Adding implicit collection " + implicitCollectionName + " to " + objectClass);
                    //X_STREAM.addImplicitCollection(objectClass, implicitCollectionName);
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
     * @see com.myschool.infra.oxo.agent.OxoAgent#toXml(java.lang.Object)
     */
    @Override
    public String toXml(Object object) {
        if (object instanceof Serializable) {
            return X_STREAM.toXML(object);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.oxo.agent.OxoAgent#toObject(java.lang.String)
     */
    @Override
    public Object toObject(String xmlContent) {
        return X_STREAM.fromXML(xmlContent);
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.oxo.agent.OxoAgent#toXml(java.util.List, java.lang.Class)
     */
    @Override
    public synchronized <T> String toXml(List<T> list, Class<T> type) {
        String xml = null;
        if (list != null) {
            String className = type.getSimpleName();
            if (className.endsWith("Dto")) {
                className = className.substring(0, className.indexOf("Dto"));
            }
            xml = X_STREAM.toXML(list);
            if (xml != null) {
                xml = xml.replaceAll("<list>", "<" + className + "s>")
                        .replaceAll("</list>", "</" + className + "s>")
                        .replaceAll("<list/>", "<" + className + "s/>")
                        .replaceAll("<list />", "<" + className + "s />");
            }
        }
        return xml;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.oxo.agent.OxoAgent#toXml(com.myschool.infra.application.dto.CommandDto, java.lang.Class)
     
    @Override
    public <T> String toXml(CommandDto command, Class<T> type) {
        String xml = toXml(command);
        if (xml != null) {
            String className = type.getSimpleName();
            if (className.endsWith("Dto")) {
                className = className.substring(0, className.indexOf("Dto"));
            }
            xml = xml.replaceAll("<Content>", "<" + className + ">")
                    .replaceAll("</Content>", "</" + className + ">")
                    .replaceAll("<Content/>", "<" + className + "/>")
                    .replaceAll("<Content />", "<" + className + " />")
                    .replaceAll("<Content", "<" + className);
        }
        return null;
    }*/

}
