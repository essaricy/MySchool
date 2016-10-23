package com.myschool.infra.storage.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.filesystem.dto.AbsenceCode;
import com.myschool.filesystem.dto.DirectoryDto;
import com.myschool.infra.filesystem.constants.FileSystemConfigConstants;
import com.myschool.infra.filesystem.util.FileUtil;
import com.myschool.storage.dto.StorageConfig;
import com.myschool.storage.dto.StorageProviderDto;

/**
 * The Class StorageConfigReader.
 */
@Component
public class StorageConfigReader extends DefaultHandler {

    /**
     * Gets the storage config.
     *
     * @param configFile the config file
     * @return the storage config
     * @throws ConfigurationException the configuration exception
     */
    public StorageConfig getStorageConfig(File configFile) throws ConfigurationException {
        try {
            String message = configFile.getAbsolutePath() + " is missing/inaccessible";
            FileUtil.checkFile(configFile.getAbsolutePath(), message, message);
            return readStorageConfig(configFile);
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(fileSystemException.getMessage(), fileSystemException);
        }
    }

    /**
     * Read storage config.
     *
     * @param configFile the config file
     * @return the storage config
     * @throws ConfigurationException the configuration exception
     */
    private StorageConfig readStorageConfig(File configFile) throws ConfigurationException {
        StorageConfig storageConfig = null;
        InputStream inputStream = null;
        DirectoryDto directoryDto = null;

        try {
            // TODO validate fileSystemConfigFile against schema
            inputStream = new FileInputStream(configFile);
            if (inputStream != null) {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                //Using factory get an instance of document builder
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                //parse using builder to get DOM representation of the XML file
                Document document = documentBuilder.parse(inputStream);

                NodeList storageConfigNodeList = document.getElementsByTagName("storage-config");
                Node storageConfigNode = storageConfigNodeList.item(0);
                if (storageConfigNode != null && storageConfigNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element storageConfigElement = (Element) storageConfigNode;
                    storageConfig = new StorageConfig();

                    // Read storage information
                    NodeList storageNodeList = storageConfigElement.getElementsByTagName("provider");
                    Node storageNode = storageNodeList.item(0);
                    if (storageNode != null && storageNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element storageElement = (Element) storageNode;

                        StorageProviderDto storageProvider = new StorageProviderDto();
                        storageConfig.setStorageProvider(storageProvider);

                        storageProvider.setName(storageElement.getAttribute("name"));
                        storageProvider.setAppname(storageElement.getAttribute("app-name"));
                        storageProvider.setDirectLink(storageElement.getAttribute("direct-link"));
                        storageProvider.setPassportLink(storageElement.getAttribute("passport-link"));
                        storageProvider.setThumbnailLink(storageElement.getAttribute("thumbnail-link"));

                        NodeList paramNodeList = storageElement.getElementsByTagName("param");
                        if (paramNodeList != null && paramNodeList.getLength() != 0) {
                            Map<String, String> params = new HashMap<String, String>();
                            for (int index = 0; index < paramNodeList.getLength(); index++) {
                                Node paramNode = paramNodeList.item(index);
                                Element paramElement = (Element) paramNode;

                                String key = paramElement.getAttribute("key");
                                String value = paramElement.getAttribute("value");
                                params.put(key, value);
                            }
                            storageProvider.setParams(params);
                        }
                    }

                    // Read h information
                    NodeList hierarchyNodeList = storageConfigElement.getElementsByTagName("hierarchy");
                    Node hierarchyNode = hierarchyNodeList.item(0);
                    if (hierarchyNode != null && hierarchyNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element hierarchyElement = (Element) hierarchyNode;

                        NodeList directoryNodeList = hierarchyElement.getElementsByTagName(FileSystemConfigConstants.ELEMENT_DIRECTORY);
                        int numberOfDirectories = directoryNodeList.getLength();
                        if (numberOfDirectories > 0) {
                            List<DirectoryDto> directories = new ArrayList<DirectoryDto>();
                            for (int index=0; index < numberOfDirectories;index++) {
                                Node directoryNode = directoryNodeList.item(index);
                                if (directoryNode.getParentNode() == hierarchyElement) {
                                    directoryDto = getDirectory(directoryNode, null);
                                    directories.add(directoryDto);
                                }
                            }
                            storageConfig.setDirectories(directories);
                        }
                    }
                }
            }
        } catch (ParserConfigurationException parserConfigurationException) {
            throw new ConfigurationException(parserConfigurationException.getMessage(), parserConfigurationException);
        } catch (SAXException saxException) {
            throw new ConfigurationException(saxException.getMessage(), saxException);
        } catch (IOException ioException) {
            throw new ConfigurationException(ioException.getMessage(), ioException);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return storageConfig;
    }

    /**
     * Gets the directory.
     *
     * @param directoryNode the directory node
     * @param pathFromParent the path from parent
     * @return the directory
     */
    private DirectoryDto getDirectory(Node directoryNode, String pathFromParent) {
        DirectoryDto directoryDto = null;
        if (directoryNode.getNodeType() == Node.ELEMENT_NODE) {
            Element directoryElement = (Element) directoryNode;
            directoryDto = new DirectoryDto();
            directoryDto.setAbsenceCode(AbsenceCode.get(directoryElement.getAttribute(FileSystemConfigConstants.ATTRIBUTE_ABSENCE)));
            String fileName = directoryElement.getAttribute(FileSystemConfigConstants.ATTRIBUTE_NAME);
            directoryDto.setName(fileName);
            directoryDto.setId(directoryElement.getAttribute(FileSystemConfigConstants.ATTRIBUTE_ID));

            if (pathFromParent == null) {
                directoryDto.setPath(fileName);
            } else {
                directoryDto.setParentPath(pathFromParent);
                directoryDto.setPath(pathFromParent + "/" + fileName);
            }

            NodeList childDirectories = directoryElement.getElementsByTagName(FileSystemConfigConstants.ELEMENT_DIRECTORY);
            int numberOfChildDirectories = childDirectories.getLength();
            if (numberOfChildDirectories > 0) {
                List<DirectoryDto> directories = directoryDto.getDirectories();
                if (directories == null) {
                    directories = new ArrayList<DirectoryDto>();
                }
                for (int index=0; index<numberOfChildDirectories; index++) {
                    Node childDirectory = childDirectories.item(index);
                    if (childDirectory.getParentNode() == directoryElement) {
                        directories.add(getDirectory(childDirectories.item(index), directoryDto.getPath()));
                    }
                }
                directoryDto.setDirectories(directories);
            }
        }
        return directoryDto;
    }

}
