package com.myschool.filesystem.dto;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * The Class DirectoryDto.
 */
public class DirectoryDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    private String id;

    /** The name. */
    private String name;

    /** The path. */
    private String path;

    /** The parent path. */
    private String parentPath;

    /** The file. */
    private File file;

    /** The absence code. */
    private AbsenceCode absenceCode;

    /** The directories. */
    private List<DirectoryDto> directories;

    /** The files. */
    private List<FileDto> files;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

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
     * Gets the parent path.
     *
     * @return the parentPath
     */
    public String getParentPath() {
        return parentPath;
    }

    /**
     * Sets the parent path.
     *
     * @param parentPath the parentPath to set
     */
    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DirectoryDto [id=").append(id).append(", name=")
                .append(name).append(", path=").append(path).append(", file=")
                .append(file).append(", absenceCode=").append(absenceCode)
                .append(", directories=")
                .append(directories != null ? directories : null)
                .append(", files=")
                .append(files != null ? files : null)
                .append("]");
        return builder.toString();
    }

}
