package com.myschool.common.util;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * The Class XmlUtil.
 */
public class XmlUtil {

    /**
     * Validate configuration.
     * 
     * @param configFileStream the config file stream
     * @param configSchemaStream the config schema stream
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SAXException the sAX exception
     */
    public static void validateConfiguration(InputStream configFileStream,
            InputStream configSchemaStream) throws IOException, SAXException {
        Source xmlFile = new StreamSource(configFileStream);
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new StreamSource(
                configSchemaStream));
        Validator validator = schema.newValidator();
        validator.validate(xmlFile);
    }

    /**
     * Gets the int attribute.
     *
     * @param element the element
     * @param attributeName the attribute name
     * @return the int attribute
     */
    public static int getIntAttribute(Element element, String attributeName) {
        int intValue = 0;
        String stringAttribute = getStringAttribute(element, attributeName);
        if (stringAttribute != null && !stringAttribute.equals("")) {
            intValue = Integer.parseInt(stringAttribute);
        }
        return intValue;
    }

    /**
     * Gets the boolean attribute.
     *
     * @param element the element
     * @param attributeName the attribute name
     * @return the boolean attribute
     */
    public static boolean getBooleanAttribute(Element element, String attributeName) {
        boolean booleanValue = false;
        String stringAttribute = getStringAttribute(element, attributeName);
        if (stringAttribute != null
                && (stringAttribute.equalsIgnoreCase("Y")
                        || stringAttribute.equalsIgnoreCase("TRUE"))) {
            booleanValue = true;
        }
        return booleanValue;
    }

    /**
     * Gets the string attribute.
     *
     * @param element the element
     * @param attributeName the attribute name
     * @return the string attribute
     */
    public static String getStringAttribute(Element element, String attributeName) {
        return element.getAttribute(attributeName);
    }

    /**
     * Gets the attribute.
     * 
     * @param attributes the attributes
     * @param attributeName the attribute name
     * @return the attribute
     */
    public static String getAttribute(Attributes attributes,
            String attributeName) {
        if (attributeName != null && attributes != null) {
            String value = attributes.getValue(attributeName);
            return (value == null) ? null : value.trim();
        }
        return null;
    }

}
