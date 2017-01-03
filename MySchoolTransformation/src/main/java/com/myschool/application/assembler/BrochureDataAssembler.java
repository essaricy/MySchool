package com.myschool.application.assembler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.myschool.download.dto.BrochureDto;
import com.myschool.storage.dto.StorageItem;
import com.quasar.core.util.FileUtil;

/**
 * The Class BrochureDataAssembler.
 */
public class BrochureDataAssembler {

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

    /**
     * Creates the.
     *
     * @param storageItems the storage items
     * @return the list
     */
    public static List<BrochureDto> create(List<StorageItem> storageItems) {
        List<BrochureDto> brochures = null;
        if (storageItems != null && !storageItems.isEmpty()) {
            brochures = new ArrayList<BrochureDto>();
            for (StorageItem storageItem : storageItems) {
                BrochureDto brochure = create(storageItem);
                if (brochure != null) {
                    brochures.add(brochure);
                }
            }
        }
        return brochures;
    }

    /**
     * Creates the.
     *
     * @param storageItem the storage item
     * @return the brochure dto
     */
    public static BrochureDto create(StorageItem storageItem) {
        BrochureDto brochure = null;
        if (storageItem != null) {
            brochure = new BrochureDto();
            String name = storageItem.getName();
            brochure.setBrochureName(FileUtil.getFileName(name));
            brochure.setBrochureType(FileUtil.getExtension(name));
            brochure.setUrl(storageItem.getDirectLink());
        }
        return brochure;
    }

}
