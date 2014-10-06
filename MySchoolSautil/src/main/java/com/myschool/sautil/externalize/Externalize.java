package com.myschool.sautil.externalize;

import java.io.File;

import org.springframework.stereotype.Component;

import com.myschool.common.exception.ApplicationException;
import com.myschool.exim.dto.ExportStatusDto;

/**
 * The Interface Externalize.
 */
@Component
public interface Externalize {

    /**
     * Import data.
     * 
     * @param fromDirectory the from directory
     * @param mappingFile the mapping file
     * @param trackerId the tracker id
     * @throws ApplicationException the application exception
     */
    public void importData(File fromDirectory, File mappingFile, Integer trackerId) throws ApplicationException;

    /**
     * Export data.
     *
     * @param toDirectory the to directory
     * @return the export status dto
     */
    public ExportStatusDto exportData(File toDirectory);

    /**
     * Export data to sql script.
     *
     * @param extDir the ext dir
     * @return true, if successful
     * @throws ApplicationException the application exception
     */
    public boolean exportDataToSqlScript(File extDir) throws ApplicationException;

    /**
     * Import data from sql script.
     *
     * @param importFromDirectory the import from directory
     * @return true, if successful
     * @throws ApplicationException the application exception
     */
    public boolean importDataFromSqlScript(File importFromDirectory) throws ApplicationException;

}
