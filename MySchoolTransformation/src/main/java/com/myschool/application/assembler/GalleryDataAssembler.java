package com.myschool.application.assembler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.application.dto.GalleryDetailDto;
import com.myschool.storage.dto.StorageItem;
import com.quasar.core.util.ConversionUtil;

/**
 * The Class GalleryDataAssembler.
 */
public class GalleryDataAssembler {

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
     * @param storageItems the storage items
     * @param pinnedGallery the pinned gallery
     * @return the list
     */
    public static List<GalleryDetailDto> createGalleryDetails(List<StorageItem> storageItems, String pinnedGallery) {
        List<GalleryDetailDto> galleryDetails = null;
        if (storageItems != null && !storageItems.isEmpty()) {
            galleryDetails = new ArrayList<GalleryDetailDto>();

            for (StorageItem storageItem : storageItems) {
                GalleryDetailDto galleryDetail = createGalleryDetail(storageItem, pinnedGallery);
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
     * @param storageItem the storage item
     * @param pinnedGallery the pinned gallery
     * @return the gallery detail dto
     */
    public static GalleryDetailDto createGalleryDetail(StorageItem storageItem, String pinnedGallery) {
        GalleryDetailDto galleryDetail = null;
        if (storageItem != null) {
            galleryDetail = new GalleryDetailDto();
            String name = storageItem.getName();
            galleryDetail.setGalleryName(name);
            galleryDetail.setPinned(name.equals(pinnedGallery));
            galleryDetail.setUrl(storageItem.getDirectLink());
            galleryDetail.setPassportUrl(storageItem.getPassportLink());
            galleryDetail.setThumbnailUrl(storageItem.getThumbnailLink());
        }
        return galleryDetail;
    }

}
