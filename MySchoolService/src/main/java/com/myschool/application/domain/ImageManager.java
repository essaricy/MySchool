package com.myschool.application.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.assembler.FeatureDataAssembler;
import com.myschool.application.dto.FeatureDto;
import com.myschool.application.dto.ResourceDto;
import com.myschool.common.exception.DataException;
import com.myschool.infra.media.agent.MediaServerAgent;
import com.myschool.infra.media.exception.ResourceException;

/**
 * The Class ImageManager.
 */
@Component
public class ImageManager {

    /** The media server agent. */
    @Autowired
    private MediaServerAgent mediaServerAgent;

    /**
     * Gets the features.
     * 
     * @return the features
     * @throws DataException the data exception
     */
    public List<FeatureDto> getFeatures() throws DataException {
        List<FeatureDto> features = null;
        try {
            List<ResourceDto> resources = mediaServerAgent.getFeatures();
            features = FeatureDataAssembler.create(resources);
        } catch (ResourceException resourceException) {
            throw new DataException(resourceException.getMessage(), resourceException);
        }
        return features;
    }

}
