package com.myschool.infra.oxo.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.ResourceUtil;
import com.myschool.infra.oxo.constants.OxoMappingConstants;
import com.myschool.infra.oxo.dto.ObjectXmlFieldDto;
import com.myschool.infra.oxo.dto.ObjectXmlMappingDto;

/**
 * The Class OxoMappingReader.
 */
@Component
public class OxoMappingReader {

    /**
     * Read mappings.
     *
     * @return the list
     * @throws ConfigurationException 
     */
    public List<ObjectXmlMappingDto> readMappings(File mappingFile) throws ConfigurationException {

        InputStream inputStream = null;

        Node node = null;
        Node fieldNode = null;
        NodeList fieldNodes = null;
        Element element = null;
        Element fieldElement = null;

        ObjectXmlFieldDto objectXmlField = null;
        ObjectXmlMappingDto objectXmlMappingDto = null;
        List<ObjectXmlFieldDto> objectXmlFields = null;
        List<ObjectXmlMappingDto> objectXmlMappings = null;

        try {
            inputStream = new FileInputStream(mappingFile);

            if (inputStream != null) {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                //Using factory get an instance of document builder
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                //parse using builder to get DOM representation of the XML file
                Document document = documentBuilder.parse(inputStream);
                NodeList nodeList = document.getElementsByTagName(OxoMappingConstants.OBJECT_XML_MAPPING);
                objectXmlMappings = new ArrayList<ObjectXmlMappingDto>();
                for (int index = 0; index < nodeList.getLength(); index++) {
                    node = nodeList.item(index);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        element = (Element) node;
                        objectXmlMappingDto = new ObjectXmlMappingDto();
                        objectXmlMappingDto.setName(element.getAttribute(OxoMappingConstants.OBJECT_XML_MAPPING_NAME));
                        objectXmlMappingDto.setType(element.getAttribute(OxoMappingConstants.OBJECT_XML_MAPPING_TYPE));

                        fieldNodes = element.getElementsByTagName(OxoMappingConstants.OBJECT_XML_MAPPING_FIELD);
                        
                        objectXmlFields = new ArrayList<ObjectXmlFieldDto>();
                        for (int jindex = 0; jindex < fieldNodes.getLength(); jindex++) {
                            fieldNode = fieldNodes.item(jindex);
                            if (fieldNode.getNodeType() == Node.ELEMENT_NODE) {
                                fieldElement = (Element) fieldNode;
                                objectXmlField = new ObjectXmlFieldDto();
                                objectXmlField.setAlias(fieldElement.getAttribute(OxoMappingConstants.OBJECT_XML_MAPPING_ALIAS));
                                objectXmlField.setName(fieldElement.getAttribute(OxoMappingConstants.OBJECT_XML_MAPPING_NAME));
                                objectXmlField.setType(fieldElement.getAttribute(OxoMappingConstants.OBJECT_XML_MAPPING_TYPE));
                                objectXmlFields.add(objectXmlField);
                            }
                        }
                        objectXmlMappingDto.setObjectXmlFields(objectXmlFields);
                        objectXmlMappings.add(objectXmlMappingDto);
                    }
                }
            }
        } catch(ParserConfigurationException parserConfigurationException) {
            throw new ConfigurationException(parserConfigurationException.getMessage(), parserConfigurationException);
        } catch(IOException ioException) {
            throw new ConfigurationException(ioException.getMessage(), ioException);
        } catch (SAXException saxException) {
            throw new ConfigurationException(saxException.getMessage(), saxException);
        } finally {
            try {
                ResourceUtil.releaseResource(inputStream);
            } catch (FileSystemException fileSystemException) {
                throw new ConfigurationException(fileSystemException.getMessage(), fileSystemException);
            }
        }
        return objectXmlMappings;
    }
}
