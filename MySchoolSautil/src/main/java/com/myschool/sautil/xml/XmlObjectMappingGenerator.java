package com.myschool.sautil.xml;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.util.StringUtil;
import com.myschool.infra.oxo.agent.OxoAgent;
import com.myschool.infra.oxo.constants.OxoMappingConstants;
import com.myschool.sautil.base.StandAloneUtility;

/**
 * The Class XmlObjectMappingGenerator.
 */
@Component
public class XmlObjectMappingGenerator extends StandAloneUtility {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(XmlObjectMappingGenerator.class);

    /** The Constant JAVA_IO_SERIALIZABLE. */
    private static final String JAVA_IO_SERIALIZABLE = "java.io.Serializable";
    
    /** The Constant JAVA_LANG_STRING. */
    private static final String JAVA_LANG_STRING = "java.lang.String";
    
    /** The Constant SERIAL_VERSION_UID. */
    private static final String SERIAL_VERSION_UID = "serialVersionUID";

    /** The Constant PACKAGE. */
    public static final String PACKAGE = "--package";

    /** The oxo agent. */
    @Autowired
    private OxoAgent oxoAgent;

    /* (non-Javadoc)
     * @see com.myschool.sautil.base.StandAloneUtility#validateParameters()
     */
    public void validateParameters() throws ConfigurationException {
        /*String codeBasePackage = executionProperties.getProperty(PACKAGE);
        if (StringUtil.isNullOrBlank(codeBasePackage)) {
            throw new ConfigurationException("You must specify the option " + PACKAGE + " to generate mappings.");
        }
        if (codeBasePackage.indexOf(".") != -1) {
            throw new ConfigurationException("Please specify \"/\" as package seperator instead of \".\" for the option " + PACKAGE);
        }*/
    	executionProperties.setProperty(PACKAGE, "com/myschool");
        String outputDirectoryName = executionProperties.getProperty(OPTION_EXT_DIR);
        if (StringUtil.isNullOrBlank(outputDirectoryName)) {
            throw new ConfigurationException("You must specify the option " + OPTION_EXT_DIR + ". The generated file will be placed in this directory.");
        }
        File outputDirectory = new File(outputDirectoryName);
        if (!outputDirectory.exists() || !outputDirectory.isDirectory() || !outputDirectory.canWrite()) {
            throw new ConfigurationException("The output directory '" + outputDirectory.getAbsolutePath() + "' does not exist, not a directory or not accessible.");
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.sautil.base.StandAloneUtility#startProcess()
     */
    @Override
    public void startProcess() throws Exception {
        LOGGER.info("Starting generating OObject XML Mapping document.");
        String fieldName = null;
        String aliasName = null;

        Class fieldClass = null;
        Field[] declaredFields = null;

        Element fieldElement = null;
        Element objectXmlMappingElement = null;

        List<Class> serializables = getSerializableClasses();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder newDocumentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = newDocumentBuilder.newDocument();
        Element root = document.createElement(OxoMappingConstants.OBJECT_XML_MAPPINGS);
        document.appendChild(root);

        if (serializables != null && !serializables.isEmpty()) {
            LOGGER.info("There are " + serializables.size() + " serializable objects found.");

            for (Class serializableClass : serializables) {
                LOGGER.info("Generating mapping for " + serializableClass);
                String className = serializableClass.getSimpleName();
                String canonicalName = serializableClass.getCanonicalName();

                if (className.endsWith("Dto")) {
                    className = className.substring(0, className.indexOf("Dto"));
                }
                objectXmlMappingElement = document.createElement(OxoMappingConstants.OBJECT_XML_MAPPING);
                objectXmlMappingElement.setAttribute(OxoMappingConstants.OBJECT_XML_MAPPING_NAME, className);
                objectXmlMappingElement.setAttribute(OxoMappingConstants.OBJECT_XML_MAPPING_TYPE, canonicalName);
                root.appendChild(objectXmlMappingElement);

                declaredFields = serializableClass.getDeclaredFields();
                if (declaredFields != null) {
                    for (Field field : declaredFields) {
                        fieldName = field.getName();
                        if (fieldName.equals(SERIAL_VERSION_UID)) {
                            continue;
                        }
                        aliasName = ("" + fieldName.charAt(0)).toUpperCase() + fieldName.substring(1, fieldName.length());
                        // Truncate Dto suffix
                        if (aliasName.endsWith("Dto")) {
                            aliasName = aliasName.substring(0, aliasName.indexOf("Dto"));
                        }
                        fieldElement = document.createElement(OxoMappingConstants.OBJECT_XML_MAPPING_FIELD);
                        objectXmlMappingElement.appendChild(fieldElement);
                        fieldClass = field.getType();
                        if (!fieldClass.isPrimitive() && !fieldClass.getName().equals(JAVA_LANG_STRING)) {
                            String fieldTypeName = fieldClass.getName();
                            fieldElement.setAttribute(OxoMappingConstants.OBJECT_XML_MAPPING_TYPE, fieldTypeName);
                        }
                        // Each simple type member should be an attribute
                        // and complex type member should be an element with type as class name
                        fieldElement.setAttribute(OxoMappingConstants.OBJECT_XML_MAPPING_NAME, fieldName);
                        fieldElement.setAttribute(OxoMappingConstants.OBJECT_XML_MAPPING_ALIAS, aliasName);
                    }
                }
                LOGGER.info("Generated mapping for " + serializableClass);
            }
        }
        createObjectXmlMappingFile(document);
        LOGGER.info("Completed generating OObject XML Mapping document.");
    }

    /* (non-Javadoc)
     * @see com.myschool.sautil.base.StandAloneUtility#getUsageText()
     */
    @Override
    public String getUsageText() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Usage: ").append(this.getClass().getName()).append(" [OPTIONS] \n");
        buffer.append("Valid Options are\n");
        buffer.append(String.format(PADDING_WIDTH, OPTION_HELP)).append("For Help\n");
        buffer.append(String.format(PADDING_WIDTH, PACKAGE)).append("Code base to consider to generate object and xml mappings.\n");
        buffer.append(String.format(PADDING_WIDTH, OPTION_EXT_DIR)).append("Output directory to place the generated mapping file.\n");
        return buffer.toString();
    }

    /**
     * Creates the object xml mapping file.
     *
     * @param document the document
     * @throws TransformerException the transformer exception
     */
    private void createObjectXmlMappingFile(Document document) throws TransformerException {
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource source = new DOMSource(document);
        File file = new File(executionProperties.getProperty(OPTION_EXT_DIR), oxoAgent.getOxoMappingFileName());
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);
        LOGGER.info("Generated mapping file = " + file);
    }

    /**
     * Gets the serializable classes.
     *
     * @return the serializable classes
     * @throws ClassNotFoundException the class not found exception
     * @throws InstantiationException the instantiation exception
     * @throws IllegalAccessException the illegal access exception
     */
    private List<Class> getSerializableClasses() throws ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        List<Class> serializables = new ArrayList<Class>();
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);

        provider.addIncludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
                    throws IOException {
                ClassMetadata classMetadata = metadataReader.getClassMetadata();
                String[] interfaceNames = classMetadata.getInterfaceNames();
                if (interfaceNames != null) {
                    for (int index = 0; index < interfaceNames.length; index++) {
                        String interfaceName = interfaceNames[index];
                        if (interfaceName != null && interfaceName.equalsIgnoreCase(JAVA_IO_SERIALIZABLE)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        // scan in org.example.package
        Set<BeanDefinition> components = provider.findCandidateComponents(executionProperties.getProperty(PACKAGE));
        for (BeanDefinition component : components) {
            Class clazz = Class.forName(component.getBeanClassName());
            if (clazz.newInstance() instanceof Serializable) {
                serializables.add(clazz);
            }
        }
        return serializables;
    }

}
