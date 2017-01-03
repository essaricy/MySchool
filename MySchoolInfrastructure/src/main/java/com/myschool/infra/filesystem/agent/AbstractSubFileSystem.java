package com.myschool.infra.filesystem.agent;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.myschool.filesystem.dto.DirectoryDto;
import com.myschool.filesystem.dto.FileDto;
import com.quasar.core.exception.FileSystemException;
import com.quasar.core.util.FileUtil;

/**
 * The Class AbstractSubFileSystem.
 */
@Component
public abstract class AbstractSubFileSystem implements FileSystem {

    protected static final String GALLERY = "gallery";

    /** The Constant EMPLOYEE. */
    protected static final String EMPLOYEE = "employee";

    /** The Constant REPORT. */
    protected static final String REPORT = "report";

    /** The Constant STUDENT. */
    protected static final String STUDENT = "student";

    /** The Constant IMPORT. */
    protected static final String IMPORT = "import";

    /** The Constant NOTIFICATION. */
    protected static final String NOTIFICATION = "notification";

    /** The Constant MAIN. */
    protected static final String MAIN = "main";

    /** The Constant TEST. */
    protected static final String TEST = "test";

    /** The Constant EMAIL. */
    protected static final String EMAIL = "email";

    /** The Constant SMS. */
    protected static final String SMS = "sms";

    /** The Constant NOTICE_BOARD. */
    protected static final String NOTICE_BOARD = "notice_board";

    /** The Constant UPLOAD. */
    protected static final String UPLOAD = "upload";

    /** The Constant ORGANIZATION. */
    protected static final String ORGANIZATION = "organization";

    /** The directory. */
    protected DirectoryDto directory;

    /** The configuration files. */
    protected List<FileDto> configurationFiles;

    /* (non-Javadoc)
     * @see com.myschool.infra.filesystem.agent.FileSystem#init(com.myschool.infra.filesystem.dto.DirectoryDto)
     */
    @Override
    public void init(DirectoryDto directory) throws FileSystemException {
        this.directory = directory;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.filesystem.agent.FileSystem#destroy()
     */
    @Override
    @PreDestroy
    public void destroy() throws FileSystemException {
    }

    /**
     * Set configuration files.
     *
     * @param configurationFiles the new configuration files
     */
    public void setConfigurationFiles(List<FileDto> configurationFiles) {
        this.configurationFiles = configurationFiles;
    }

    /**
     * Gets the directory.
     *
     * @return the directory
     * @throws FileSystemException the file system exception
     */
    protected File getDirectory() throws FileSystemException {
        if (directory == null) {
            throw new FileSystemException("No corresponding directory found");
        }
        File file = directory.getFile();
        FileUtil.checkDirectory(file, "No such directory", "No such directory");
        return file;
    }

    /**
     * Gets the files.
     *
     * @return the files
     * @throws FileSystemException the file system exception
     */
    protected File[] getFiles() throws FileSystemException {
        return getDirectory().listFiles();
    }

    /**
     * Gets the files.
     *
     * @param parentDirectory the parent directory
     * @return the files
     * @throws FileSystemException the file system exception
     */
    protected File[] getFiles(File parentDirectory) throws FileSystemException {
        return parentDirectory.listFiles();
    }

    /**
     * Gets the files.
     * 
     * @param parentDirectory the parent directory
     * @param fileFilter the file filter
     * @return the files
     * @throws FileSystemException the file system exception
     */
    protected File[] getFiles(File parentDirectory, FileFilter fileFilter) throws FileSystemException {
        return parentDirectory.listFiles(fileFilter);
    }

    /**
     * Gets the file.
     *
     * @param fileName the file name
     * @return the file
     * @throws FileSystemException the file system exception
     */
    protected File getFile(String fileName) throws FileSystemException {
        return new File(getDirectory(), fileName);
    }

    /**
     * Gets the file.
     *
     * @param parentDirectory the parent directory
     * @param fileName the file name
     * @return the file
     */
    protected File getFile(File parentDirectory, String fileName) {
        return new File(parentDirectory, fileName);
    }

    /**
     * Gets the child directory file.
     *
     * @param childDirectoryName the child directory name
     * @return the child directory file
     */
    protected File getChildDirectoryFile(String childDirectoryName) {
        if (childDirectoryName != null) {
            List<DirectoryDto> directories = directory.getDirectories();
            if (directories != null && !directories.isEmpty()) {
                for (DirectoryDto directoryDto : directories) {
                    if (childDirectoryName.equalsIgnoreCase(directoryDto.getName())) {
                        return directoryDto.getFile();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets the child directory.
     *
     * @param childDirectoryName the child directory name
     * @return the child directory
     */
    protected DirectoryDto getChildDirectory(String childDirectoryName) {
        return getChildDirectory(directory, childDirectoryName);
    }

    /**
     * Gets the child directory.
     *
     * @param parentDirectory the parent directory
     * @param childDirectoryName the child directory name
     * @return the child directory
     */
    protected DirectoryDto getChildDirectory(DirectoryDto parentDirectory, String childDirectoryName) {
        if (parentDirectory != null && childDirectoryName != null) {
            List<DirectoryDto> directories = parentDirectory.getDirectories();
            if (directories != null && !directories.isEmpty()) {
                for (DirectoryDto directoryDto : directories) {
                    if (childDirectoryName.equalsIgnoreCase(directoryDto.getName())) {
                        return directoryDto;
                    }
                }
            }
        }
        return null;
    }

}
