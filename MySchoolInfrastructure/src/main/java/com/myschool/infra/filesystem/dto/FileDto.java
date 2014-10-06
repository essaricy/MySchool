package com.myschool.infra.filesystem.dto;

import java.io.File;
import java.io.Serializable;

/**
 * The Class FileDto.
 */
public class FileDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The name. */
    private String name;

    /** The file. */
    private File file;

    /** The absence code. */
    private AbsenceCode absenceCode;

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
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("FileDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("absenceCode = ").append(this.absenceCode).append(SEPARATOR)
            .append("file = ").append(this.file).append(SEPARATOR)
            .append("name = ").append(this.name).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
