package com.myschool.application.assembler;

import java.util.List;

import org.json.JSONArray;

/**
 * The Class GalleryDataAssembler.
 */
public class GalleryDataAssembler {

    /**
     * Creates the.
     * 
     * @param galleryNames the gallery names
     * @return the jSON array
     */
    public static JSONArray create(List<String> galleryNames) {
        JSONArray jsonGalleryItems = null;
        if (galleryNames != null && !galleryNames.isEmpty()) {
            jsonGalleryItems = new JSONArray();
            for (String galleryName : galleryNames) {
                jsonGalleryItems.put(galleryName);
            }
        }
        return jsonGalleryItems;
    }

}
