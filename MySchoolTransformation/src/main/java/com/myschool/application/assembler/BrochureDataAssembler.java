package com.myschool.application.assembler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.myschool.application.dto.ResourceDto;
import com.myschool.common.util.StringUtil;
import com.myschool.download.dto.BrochureDto;

/**
 * The Class BrochureDataAssembler.
 */
public class BrochureDataAssembler {

    /**
     * Creates the.
     * 
     * @param resources the resources
     * @return the list
     */
    public static List<BrochureDto> create(List<ResourceDto> resources) {
        List<BrochureDto> brochures = null;
        if (resources != null && !resources.isEmpty()) {
            brochures = new ArrayList<BrochureDto>();
            for (ResourceDto resource : resources) {
                BrochureDto brochure = create(resource);
                if (brochure != null) {
                    brochures.add(brochure);
                }
            }
        }
        return brochures;
    }

    /**
     * Creates the brochure.
     * 
     * @param brochureResource the brochure resource
     * @return the brochure dto
     */
    public static BrochureDto create(ResourceDto brochureResource) {
        BrochureDto brochure = null;
        if (brochureResource != null) {
            brochure = new BrochureDto();
            String name = brochureResource.getName();
            String shortDescription = brochureResource.getShortDescription();
            if (StringUtil.isNullOrBlank(shortDescription)) {
                brochure.setBrochureName(name);
            } else {
                brochure.setBrochureName(shortDescription);
            }
            brochure.setBrochureType(name);
            brochure.setUrl(brochureResource.getResourceUrl());
        }
        return brochure;
    }

    /**
     * Creates the json array.
     * 
     * @param brochures the brochures
     * @return the jSON array
     */
    public static JSONArray createJSONArray(List<BrochureDto> brochures) {
        JSONArray array = new JSONArray();
        if (brochures != null) {
            for(BrochureDto brochure : brochures){
                JSONArray row = new JSONArray();
                row.put(brochure.getUrl());
                row.put(brochure.getBrochureName());
                row.put(brochure.getBrochureType());
                array.put(row);
            }
        }
        return array;
    }

}
