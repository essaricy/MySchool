package com.myschool.infra.filesystem.dto;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * The Class DirectoryDto.
 */
public class DirectoryDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The name. */
    private String name;

    /** The path. */
    private String path;

    /** The file. */
    private File file;

    /** The absence code. */
    private AbsenceCode absenceCode;

    /** The directories. */
    private List<DirectoryDto> directories;

    /** The files. */
    private List<FileDto> files;

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the path.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the path.
     *
     * @param path the new path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Gets the file.
     *
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * Sets the file.
     *
     * @param file the new file
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Gets the absence code.
     *
     * @return the absence code
     */
    public AbsenceCode getAbsenceCode() {
        return absenceCode;
    }

    /**
     * Sets the absence code.
     *
     * @param absenceCode the new absence code
     */
    public void setAbsenceCode(AbsenceCode absenceCode) {
        this.absenceCode = absenceCode;
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
     * Gets the files.
     *
     * @return the files
     */
    public List<FileDto> getFiles() {
        return files;
    }

    /**
     * Sets the files.
     *
     * @param files the new files
     */
    public void setFiles(List<FileDto> files) {
        this.files = files;
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
        retValue.append("DirectoryDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("absenceCode = ").append(this.absenceCode).append(SEPARATOR)
            .append("directories = ").append(this.directories).append(SEPARATOR)
            .append("file = ").append(this.file).append(SEPARATOR)
            .append("files = ").append(this.files).append(SEPARATOR)
            .append("name = ").append(this.name).append(SEPARATOR)
            .append("path = ").append(this.path).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
