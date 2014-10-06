package com.myschool.download.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DataException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.download.dto.BrochureDto;
import com.myschool.infra.filesystem.agent.BrochuresFileSystem;

/**
 * The Class BrochureManager.
 */
@Component
public class BrochureManager {

    /** The brochures file system. */
    @Autowired
    private BrochuresFileSystem brochuresFileSystem;

    /**
     * Gets the brochures.
     * 
     * @return the brochures
     * @throws DataException the data exception
     */
    public List<BrochureDto> getBrochures() throws DataException {
        List<BrochureDto> brochures = null;
        try {
            brochures = brochuresFileSystem.getBrochures();
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        }
        return brochures;
    }

    /**
     * Gets the brochure.
     * 
     * @param brochureName the brochure name
     * @return the brochure
     * @throws DataException the data exception
     */
    public BrochureDto getBrochure(String brochureName) throws DataException {
        BrochureDto brochure = null;
        try {
            if (brochureName != null) {
                brochure = brochuresFileSystem.getBrochure(brochureName);
            }
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        }
        return brochure;
    }
    
}
