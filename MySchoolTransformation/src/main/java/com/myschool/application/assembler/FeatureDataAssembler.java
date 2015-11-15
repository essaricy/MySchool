package com.myschool.application.assembler;

import java.util.ArrayList;
import java.util.List;

import com.myschool.application.dto.FeatureDto;
import com.myschool.application.dto.ResourceDto;

/**
 * The Class FeatureDataAssembler.
 */
public class FeatureDataAssembler {

    /**
     * Creates the.
     * 
     * @param resources the resources
     * @return the list
     */
    public static List<FeatureDto> create(List<ResourceDto> resources) {
        List<FeatureDto> features = null;
        if (resources != null && !resources.isEmpty()) {
            features = new ArrayList<FeatureDto>();
            for (ResourceDto featureResource : resources) {
                FeatureDto feature = create(featureResource);
                if (feature != null) {
                    features.add(feature);
                }
            }
        }
        return features;
    }

    /**
     * Creates the.
     * 
     * @param resource the resource
     * @return the feature dto
     */
    public static FeatureDto create(ResourceDto resource) {
        FeatureDto feature = null;
        if (resource != null) {
            feature = new FeatureDto();
            feature.setImagePath(resource.getResourceUrl());
            feature.setName(resource.getShortDescription());
            feature.setDescription(resource.getLongDescription());
            feature.setPassportUrl(resource.getPassportUrl());
            feature.setThumbnailUrl(resource.getThumbnailUrl());
        }
        return feature;
    }

}
