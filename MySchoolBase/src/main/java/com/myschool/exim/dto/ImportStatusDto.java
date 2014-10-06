package com.myschool.exim.dto;

import java.io.Serializable;
import java.util.List;

import com.myschool.common.dto.StatusDto;

/**
 * The Class ImportStatusDto.
 */
public class ImportStatusDto extends StatusDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The successful imports. */
    private int successfulImports;

    /** The failed imports. */
    private int failedImports;

    /** The import record status list. */
    private List<ImportRecordStatusDto> importRecordStatusList;

    /**
     * Gets the successful imports.
     *
     * @return the successful imports
     */
    public int getSuccessfulImports() {
        return successfulImports;
    }

    /**
     * Sets the successful imports.
     *
     * @param successfulImports the new successful imports
     */
    public void setSuccessfulImports(int successfulImports) {
        this.successfulImports = successfulImports;
    }

    /**
     * Gets the failed imports.
     *
     * @return the failed imports
     */
    public int getFailedImports() {
        return failedImports;
    }

    /**
     * Sets the failed imports.
     *
     * @param failedImports the new failed imports
     */
    public void setFailedImports(int failedImports) {
        this.failedImports = failedImports;
    }

    /**
     * Gets the import record status list.
     *
     * @return the import record status list
     */
    public List<ImportRecordStatusDto> getImportRecordStatusList() {
        return importRecordStatusList;
    }

    /**
     * Sets the import record status list.
     *
     * @param importRecordStatusList the new import record status list
     */
    public void setImportRecordStatusList(
            List<ImportRecordStatusDto> importRecordStatusList) {
        this.importRecordStatusList = importRecordStatusList;
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
        retValue.append("ImportStatusDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("failedImports = ").append(this.failedImports).append(SEPARATOR)
            .append("importRecordStatusList = ").append(this.importRecordStatusList).append(SEPARATOR)
            .append("successfulImports = ").append(this.successfulImports).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
