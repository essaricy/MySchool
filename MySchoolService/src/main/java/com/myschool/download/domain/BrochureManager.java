package com.myschool.download.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.assembler.BrochureDataAssembler;
import com.myschool.common.exception.DataException;
import com.myschool.download.dto.BrochureDto;
import com.myschool.infra.storage.StorageAccessAgent;
import com.myschool.infra.storage.exception.StorageAccessException;
import com.myschool.storage.dto.StorageItem;

/**
 * The Class BrochureManager.
 */
@Component
public class BrochureManager {

    /** The storage access agent. */
    @Autowired
    private StorageAccessAgent storageAccessAgent;

    /**
     * Gets the all.
     * 
     * @return the all
     * @throws DataException the data exception
     */
    public List<BrochureDto> getAll() throws DataException {
        List<BrochureDto> brochures = null;
        try {
            List<StorageItem> brochureItems = storageAccessAgent.BROCHURE_STORAGE.getAll();
            brochures = BrochureDataAssembler.create(brochureItems);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
        }
        return brochures;
    }

}
