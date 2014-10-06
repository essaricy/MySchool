package com.myschool.infra.filesystem.dto;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * The Class FileSystemDto.
 */
public class FileSystemDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The base directory. */
    private File baseDirectory;

    /** The directories. */
    private List<DirectoryDto> directories;

    /**
     * Gets the base directory.
     *
     * @return the base directory
     */
    public File getBaseDirectory() {
        return baseDirectory;
    }

    /**
     * Sets the base directory.
     *
     * @param baseDirectory the new base directory
     */
    public void setBaseDirectory(File baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    /**
     * Gets the directories.
     *
     * @return the directories
     */
    public List<DirectoryDto> getDirectories() {
        return directories;
    }

    /**
     * Sets the directories.
     *
     * @param directories the new directories
     */
    public void setDirectories(List<DirectoryDto> directories) {
        this.directories = directories;
    }

    /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("FileSystemDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("baseDirectory = ").append(this.baseDirectory).append(SEPARATOR)
            .append("directories = ").append(this.directories).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
