package com.myschool.infra.filesystem.agent;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.infra.filesystem.dto.AbsenceCode;
import com.myschool.infra.filesystem.dto.DirectoryDto;
import com.myschool.infra.filesystem.dto.FileDto;
import com.myschool.infra.filesystem.dto.FileSystemDto;
import com.myschool.infra.filesystem.reader.FileSystemReader;
import com.myschool.infra.filesystem.util.FileUtil;

/**
 * The Class LocalFileSystemAgent.
 */
@Component
public class LocalFileSystemAgent extends FileSystemAgent {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(LocalFileSystemAgent.class);

    /** The file system reader. */
    @Autowired
    private FileSystemReader fileSystemReader;

    /** The rules file system. */
    @Autowired
    private RulesFileSystem rulesFileSystem;

    /** The temp file system. */
    @Autowired
    private TempFileSystem tempFileSystem;

    /** The templates file system. */
    @Autowired
    private TemplatesFileSystem templatesFileSystem;

    /** The file system. */
    private FileSystemDto fileSystem;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {
        // Load  FileSystem.xml file
        fileSystem = fileSystemReader.getFileSystemConfig(configFile);
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.Agent#validate()
     */
    @Override
    public void validate() throws AgentException {
        try {
            File baseDirectory = fileSystem.getBaseDirectory();
            String baseDirectoryPath = baseDirectory.getAbsolutePath();
            String baseDirMissingMessage = MessageFormat.format(DIRECTORY_MISSING_MESSAGE, baseDirectoryPath);
            FileUtil.checkFile(baseDirectoryPath, baseDirMissingMessage, baseDirMissingMessage);
            List<DirectoryDto> directories = fileSystem.getDirectories();
            if (directories != null && !directories.isEmpty()) {
                for (DirectoryDto directory : directories) {
                    validateDirectory(directory);
                }
            }
        } catch (FileSystemException fileSystemException) {
            throw new AgentException(fileSystemException.getMessage(), fileSystemException);
        }
    }

    /**
     * Validate directory.
     *
     * @param directory the directory
     * @throws FileSystemException the file system exception
     */
    private void validateDirectory(DirectoryDto directory) throws FileSystemException {
        String path = directory.getPath();
        File directoryFile = directory.getFile();
        AbsenceCode absenceCode = directory.getAbsenceCode();
        if (absenceCode == AbsenceCode.CREATE) {
            directoryFile.mkdir();
            LOGGER.info("[CREATED] " + path);
        } else if (absenceCode == AbsenceCode.ERROR) {
            String dirPath = directoryFile.getAbsolutePath();
            String dirMissingMessage = MessageFormat.format(DIRECTORY_MISSING_MESSAGE, dirPath);;
            FileUtil.checkDirectory(dirPath, dirMissingMessage, dirMissingMessage);
            LOGGER.info("[VALIDATED] " + path);
        }
        List<DirectoryDto> directories = directory.getDirectories();
        if (directories != null && !directories.isEmpty()) {
            for (DirectoryDto childDirectory : directories) {
                validateDirectory(childDirectory);
            }
        }

        List<FileDto> files = directory.getFiles();
        if (files != null && !files.isEmpty()) {
            for (FileDto file : files) {
                validateFile(file);
            }
        }

        // Determine the sub file system and pass the excerpt to initiate the sub file system
        if (path.equalsIgnoreCase("rules")) {
            rulesFileSystem.init(directory); 
        } else if (path.equalsIgnoreCase("temp")) {
            tempFileSystem.init(directory); 
        } else if (path.equalsIgnoreCase("templates")) {
            templatesFileSystem.init(directory); 
        } else if (path.equalsIgnoreCase("config/rules")) {
            rulesFileSystem.setConfigurationFiles(directory.getFiles()); 
        } else if (path.equalsIgnoreCase("config/templates")) {
            templatesFileSystem.setConfigurationFiles(directory.getFiles()); 
        }
        
    }

    /**
     * Validate file.
     *
     * @param file the file
     * @throws FileSystemException the file system exception
     */
    private void validateFile(FileDto file) throws FileSystemException {
        try {
            File absoluteFile = file.getFile();
            AbsenceCode absenceCode = file.getAbsenceCode();
            if (absenceCode == AbsenceCode.CREATE) {
                absoluteFile.createNewFile();
            } else if (absenceCode == AbsenceCode.ERROR) {
                String dirPath = absoluteFile.getAbsolutePath();
                String dirMissingMessage = MessageFormat.format(FILE_MISSING_MESSAGE, dirPath);;
                FileUtil.checkFile(dirPath, dirMissingMessage, dirMissingMessage);
            }
        } catch (IOException ioException) {
            throw new FileSystemException(ioException.getMessage(), ioException);
        }
    }

}
