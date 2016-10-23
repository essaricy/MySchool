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

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.myschool.common.dto.Rule;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.util.XmlUtil;
import com.myschool.infra.filesystem.constants.RulesConfigConstants;

/**
 * The Class RulesConfigReader.
 */
@Component
public class RulesConfigReader extends DefaultHandler {

    /**
     * Read rules.
     *
     * @param ruleFile the rule file
     * @return the list
     * @throws ConfigurationException the configuration exception
     */
    public List<Rule> readRules(File ruleFile) throws ConfigurationException {
        //get the factory
        List<Rule> rulesList = null;
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(ruleFile);

            if (inputStream != null) {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                //Using factory get an instance of document builder
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                //parse using builder to get DOM representation of the XML file
                Document document = documentBuilder.parse(inputStream);
                rulesList = readImportRules(document);
            }
        } catch(ParserConfigurationException parserConfigurationException) {
            throw new ConfigurationException(parserConfigurationException.getMessage(), parserConfigurationException);
        } catch(IOException ioException) {
            throw new ConfigurationException(ioException.getMessage(), ioException);
        } catch (SAXException saxException) {
            throw new ConfigurationException(saxException.getMessage(), saxException);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return rulesList;
    }

    /**
     * Read import rules.
     *
     * @param document the document
     * @return the list
     */
    private List<Rule> readImportRules(Document document){

        List<Rule> rules = null;
        //get the root element
        Element elelment = document.getDocumentElement();
        Element fieldSequenceElement = null;
        Element fieldElelment = null;
        NodeList fieldSequenceList = null;
        NodeList filedsList = null;
        Rule rule = null;

        //get a node list of elements
        fieldSequenceList = elelment.getElementsByTagName(RulesConfigConstants.ELEMENT_FIELD_SEQUENCE);
        if(fieldSequenceList != null && fieldSequenceList.getLength() > 0) {
            fieldSequenceElement = (Element)fieldSequenceList.item(0);
            if (fieldSequenceElement != null) {

                filedsList = fieldSequenceElement.getElementsByTagName(RulesConfigConstants.ELEMENT_FIELD);
                rules = new ArrayList<Rule>();

                for(int nodeIndex = 0 ; nodeIndex < filedsList.getLength();nodeIndex++) {
                    //get the employee element
                    fieldElelment = (Element) filedsList.item(nodeIndex);
                    //get the Employee object
                    rule = getRule(fieldElelment);
                    rule.setFieldPosition(nodeIndex);
                    //add it to list
                    rules.add(rule);
                }
            }
        }
        return rules;
    }

    /**
     * Gets the rule.
     *
     * @param element the element
     * @return the rule
     */
    private Rule getRule(Element element) {
        Rule rule = null;
        if (element != null) {
            rule = new Rule();
            rule.setFieldName(XmlUtil.getStringAttribute(element, RulesConfigConstants.ATTRIBUTE_NAME));
            rule.setMandatory(XmlUtil.getBooleanAttribute(element, RulesConfigConstants.ATTRIBUTE_MANDATORY));
            rule.setDataType(XmlUtil.getStringAttribute(element, RulesConfigConstants.ATTRIBUTE_DATATYPE));
            rule.setMaxLength(XmlUtil.getIntAttribute(element, RulesConfigConstants.ATTRIBUTE_MAXLENGTH));
            rule.setFormat(XmlUtil.getStringAttribute(element, RulesConfigConstants.ATTRIBUTE_FORMAT));
        }
        return rule;
    }

}
