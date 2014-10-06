package com.myschool.exim.dto;

import java.io.File;
import java.io.Serializable;

import com.myschool.common.dto.StatusDto;

/**
 * The Class ExportStatusDto.
 */
public class ExportStatusDto extends StatusDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The exported file. */
    private File exportedFile;

    /**
     * Gets the exported file.
     *
     * @return the exported file
     */
    public File getExportedFile() {
        return exportedFile;
    }

    /**
     * Sets the exported file.
     *
     * @param exportedFile the new exported file
     */
    public void setExportedFile(File exportedFile) {
        this.exportedFile = exportedFile;
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
        retValue.append("ExportStatusDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("exportedFile = ").append(this.exportedFile).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
