package com.myschool.application.assembler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.application.dto.GalleryDetailDto;
import com.myschool.application.dto.ResourceDto;
import com.myschool.common.util.ConversionUtil;

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

    /**
     * Creates the json.
     * 
     * @param galleryDetails the gallery details
     * @return the jSON array
     */
    public static JSONArray createJSON(List<GalleryDetailDto> galleryDetails) {
        JSONArray jsonArray = null;
        if (galleryDetails != null) {
            jsonArray = new JSONArray();
            for (int index = 0; index < galleryDetails.size(); index++) {
                GalleryDetailDto galleryDetail = galleryDetails.get(index);
                if (galleryDetail != null) {
                    jsonArray.put(index, createJSON(galleryDetail));
                }
            }
        }
        return jsonArray;
    }

    /**
     * Creates the json.
     * 
     * @param galleryDetail the gallery detail
     * @return the jSON object
     */
    public static JSONObject createJSON(GalleryDetailDto galleryDetail) {
        JSONObject jsonObject = null;
        if (galleryDetail != null) {
            jsonObject = new JSONObject();
            jsonObject.put("GalleryName", galleryDetail.getGalleryName());
            jsonObject.put("Url", galleryDetail.getUrl());
            jsonObject.put("Passport", galleryDetail.getPassportUrl());
            jsonObject.put("Thumbnail", galleryDetail.getThumbnailUrl());
            jsonObject.put("Pinned", ConversionUtil.toYN(galleryDetail.isPinned()));
            jsonObject.put("GalleryItems", createJSON(galleryDetail.getGalleryItems()));
        }
        return jsonObject;
    }

    /**
     * Creates the.
     * 
     * @param galleryItemNamesValue the gallery item names value
     * @return the list
     * @throws ParseException the parse exception
     */
    public static List<String> create(String galleryItemNamesValue) throws ParseException {
        List<String> galleryItemNames = null;
        JSONArray jsonArray = null;
        if (galleryItemNamesValue != null) {
            jsonArray = new JSONArray(galleryItemNamesValue);
            if (jsonArray != null) {
                galleryItemNames = new ArrayList<String>();
                for (int index = 0; index < jsonArray.length(); index++) {
                    String galleryItemName = (String) jsonArray.get(index);
                    galleryItemNames.add(galleryItemName);
                }
            }
        }
        return galleryItemNames;
    }

    /**
     * Creates the gallery details.
     * 
     * @param resources the resources
     * @param pinnedGallery the pinned gallery
     * @return the list
     */
    public static List<GalleryDetailDto> createGalleryDetails(List<ResourceDto> resources, String pinnedGallery) {
        List<GalleryDetailDto> galleryDetails = null;
        if (resources != null && !resources.isEmpty()) {
            galleryDetails = new ArrayList<GalleryDetailDto>();

            for (ResourceDto resource : resources) {
                GalleryDetailDto galleryDetail = createGalleryDetail(resource, pinnedGallery);
                if (galleryDetail != null) {
                    galleryDetails.add(galleryDetail);
                }
            }
        }
        return galleryDetails;
    }

    /**
     * Creates the gallery detail.
     * 
     * @param resource the resource
     * @param pinnedGallery the pinned gallery
     * @return the gallery detail dto
     */
    public static GalleryDetailDto createGalleryDetail(ResourceDto resource, String pinnedGallery) {
        GalleryDetailDto galleryDetail = null;
        if (resource != null) {
            galleryDetail = new GalleryDetailDto();
            String name = resource.getName();
            galleryDetail.setGalleryName(name);
            galleryDetail.setPinned(name.equals(pinnedGallery));
            galleryDetail.setUrl(resource.getResourceUrl());
            galleryDetail.setPassportUrl(resource.getPassportUrl());
            galleryDetail.setThumbnailUrl(resource.getThumbnailUrl());
        }
        return galleryDetail;
    }

}
