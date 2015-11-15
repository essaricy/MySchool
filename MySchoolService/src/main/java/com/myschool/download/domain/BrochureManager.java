package com.myschool.download.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.assembler.BrochureDataAssembler;
import com.myschool.application.dto.ResourceDto;
import com.myschool.common.exception.DataException;
import com.myschool.download.dto.BrochureDto;
import com.myschool.infra.filesystem.util.FileUtil;
import com.myschool.infra.media.agent.MediaServerAgent;
import com.myschool.infra.media.exception.ResourceException;

/**
 * The Class BrochureManager.
 */
@Component
public class BrochureManager {

    /** The media server agent. */
    @Autowired
    private MediaServerAgent mediaServerAgent;

    /**
     * Gets the all.
     * 
     * @return the all
     * @throws DataException the data exception
     */
    public List<BrochureDto> getAll() throws DataException {
        List<BrochureDto> brochures = null;
        try {
            List<ResourceDto> resources = mediaServerAgent.getBrochures();
            brochures = BrochureDataAssembler.create(resources);
            if (brochures != null && !brochures.isEmpty()) {
                for (BrochureDto brochure : brochures) {
                    String brochureType = brochure.getBrochureType();
                    brochure.setBrochureType(FileUtil.getExtension(brochureType));
                }
            }
        } catch (ResourceException resourceException) {
            throw new DataException(resourceException.getMessage(), resourceException);
        }
        return brochures;
    }

}
