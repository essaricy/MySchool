package com.myschool.exim.service;

import java.io.File;

import com.myschool.common.exception.ServiceException;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.ExportStatusDto;
import com.myschool.exim.dto.ImportStatusDto;
import com.myschool.exim.dto.UploadFileTrackerDto;

/**
 * The Interface Eximable.
 */
public interface Eximable {

    /**
     * Import data.
     * 
     * @param eximPolicy the exim policy
     * @param importFile the import file
     * @param uploadFileTracker the upload file tracker
     * @return the import status dto
     * @throws ServiceException the service exception
     */
    ImportStatusDto importData(EximPolicy eximPolicy, File importFile,
            UploadFileTrackerDto uploadFileTracker) throws ServiceException;

    /**
     * Export data.
     *
     * @param students the students
     * @param dataToExport the data to export
     * @return the export status dto
     * @throws ServiceException the service exception
     */
    ExportStatusDto exportData(EximPolicy students, Object dataToExport) throws ServiceException;

}
