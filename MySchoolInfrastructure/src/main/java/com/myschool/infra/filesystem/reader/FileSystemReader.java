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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.file.util.FileUtil;
import com.myschool.filesystem.dto.AbsenceCode;
import com.myschool.filesystem.dto.DirectoryDto;
import com.myschool.filesystem.dto.FileDto;
import com.myschool.filesystem.dto.FileSystemDto;
import com.myschool.infra.filesystem.constants.FileSystemConfigConstants;

/**
 * The Class FileSystemReader.
 */
@Component
public class FileSystemReader extends DefaultHandler {

    /**
     * Gets the file system config.
     *
     * @param fileSystemConfigFile the file system config file
     * @return the file system config
     * @throws ConfigurationException the configuration exception
     */
    public FileSystemDto getFileSystemConfig(File fileSystemConfigFile) throws ConfigurationException {
        try {
            String message = fileSystemConfigFile.getAbsolutePath() + " is missing/inaccessible";
            FileUtil.checkFile(fileSystemConfigFile.getAbsolutePath(), message, message);
            return readFileSystemConfig(fileSystemConfigFile);
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(fileSystemException.getMessage(), fileSystemException);
        }
    }

    /**
     * Read file system config.
     *
     * @param fileSystemConfigFile the file system config file
     * @return the file system dto
     * @throws ConfigurationException the configuration exception
     */
    private FileSystemDto readFileSystemConfig(File fileSystemConfigFile)
            throws ConfigurationException {
        InputStream inputStream = null;
        Node fileSystemNode = null;
        NodeList fileSystemNodeList = null;
        Element fileSystemElement = null;
        FileSystemDto fileSystem = null;
        DirectoryDto directoryDto = null;

        try {
            // TODO validate fileSystemConfigFile against schema
            inputStream = new FileInputStream(fileSystemConfigFile);
            if (inputStream != null) {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                //Using factory get an instance of document builder
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                //parse using builder to get DOM representation of the XML file
                Document document = documentBuilder.parse(inputStream);

                fileSystemNodeList = document.getElementsByTagName(FileSystemConfigConstants.ELEMENT_FILE_SYSTEM);
                fileSystemNode = fileSystemNodeList.item(0);
                if (fileSystemNode != null && fileSystemNode.getNodeType() == Node.ELEMENT_NODE) {
                    fileSystemElement = (Element) fileSystemNode;
                    fileSystem = new FileSystemDto();
                    File baseDir = new File(fileSystemElement.getAttribute(FileSystemConfigConstants.ATTRIBUTE_BASE_DIR));
                    fileSystem.setBaseDirectory(baseDir);
                    NodeList directoryNodeList = fileSystemElement.getElementsByTagName(FileSystemConfigConstants.ELEMENT_DIRECTORY);
                    int numberOfDirectories = directoryNodeList.getLength();
                    if (numberOfDirectories > 0) {
                        List<DirectoryDto> directories = new ArrayList<DirectoryDto>();
                        fileSystem.setDirectories(directories);
                        for (int index=0; index < numberOfDirectories;index++) {
                            Node directoryNode = directoryNodeList.item(index);
                            if (directoryNode.getParentNode() == fileSystemElement) {
                                directoryDto = getDirectory(directoryNode, baseDir, null);
                                directories.add(directoryDto);
                            }
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
        return fileSystem;
    }

    /**
     * Gets the directory.
     *
     * @param directoryNode the directory node
     * @param parentDirectory the parent directory
     * @param pathFromParent 
     * @return the directory
     */
    private DirectoryDto getDirectory(Node directoryNode, File parentDirectory, String pathFromParent) {
        DirectoryDto directoryDto = null;
        if (directoryNode.getNodeType() == Node.ELEMENT_NODE) {
            Element directoryElement = (Element) directoryNode;
            directoryDto = new DirectoryDto();
            directoryDto.setAbsenceCode(AbsenceCode.get(directoryElement.getAttribute(FileSystemConfigConstants.ATTRIBUTE_ABSENCE)));
            String fileName = directoryElement.getAttribute(FileSystemConfigConstants.ATTRIBUTE_NAME);
            File directory = new File(parentDirectory, fileName);
            directoryDto.setName(fileName);
            directoryDto.setFile(directory);
            directoryDto.setId(directoryElement.getAttribute(FileSystemConfigConstants.ATTRIBUTE_ID));

            if (pathFromParent == null) {
                directoryDto.setPath(fileName);
            } else {
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
                        directories.add(getDirectory(childDirectories.item(index), directory, directoryDto.getPath()));
                    }
                }
                directoryDto.setDirectories(directories);
            }

            NodeList childFiles = directoryElement.getElementsByTagName(FileSystemConfigConstants.ELEMENT_FILE);
            int numberOfChildFiles = childFiles.getLength();
            if (numberOfChildFiles > 0) {
                List<FileDto> files = directoryDto.getFiles();
                if (files == null) {
                    files = new ArrayList<FileDto>();
                }
                for (int index=0; index<numberOfChildFiles; index++) {
                    Node childFile = childFiles.item(index);
                    if (childFile.getParentNode() == directoryElement) {
                        files.add(getFile(childFiles.item(index), directory));
                    }
                }
                directoryDto.setFiles(files);
            }
        }
        return directoryDto;
    }

    /**
     * Gets the file.
     *
     * @param fileNode the file node
     * @param parentDirectory the parent directory
     * @return the file
     */
    private FileDto getFile(Node fileNode, File parentDirectory) {
        Element fileElement = null;
        FileDto fileDto = null;
        if (fileNode.getNodeType() == Node.ELEMENT_NODE) {
            fileElement = (Element) fileNode;
            fileDto = new FileDto();
            fileDto.setAbsenceCode(AbsenceCode.get(fileElement.getAttribute(FileSystemConfigConstants.ATTRIBUTE_ABSENCE)));
            String name = fileElement.getAttribute(FileSystemConfigConstants.ATTRIBUTE_NAME);
            fileDto.setName(name);
            fileDto.setFile(new File(parentDirectory, name));
        }
        return fileDto;
    }

}
