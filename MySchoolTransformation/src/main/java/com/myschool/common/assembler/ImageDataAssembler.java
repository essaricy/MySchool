package com.myschool.common.assembler;

import org.json.JSONObject;

import com.myschool.application.dto.ImageAccessDto;
import com.myschool.storage.dto.StorageItem;

/**
 * The Class ImageDataAssembler.
 */
public class ImageDataAssembler {

    /**
     * Creates the.
     *
     * @param storageItem the storage item
     * @return the image access dto
     */
    public static ImageAccessDto create(StorageItem storageItem) {
        ImageAccessDto imageAccessDto = null;
        if (storageItem != null) {
            imageAccessDto = new ImageAccessDto();
            imageAccessDto.setId(storageItem.getId());
            imageAccessDto.setName(storageItem.getName());
            imageAccessDto.setDirectLink(storageItem.getDirectLink());
            imageAccessDto.setPassportLink(storageItem.getPassportLink());
            imageAccessDto.setThumbnailLink(storageItem.getThumbnailLink());
        }
        return imageAccessDto;
    }

    /**
     * Creates the.
     *
     * @param imageAccess the image access
     * @return the JSON object
     */
    public static JSONObject create(ImageAccessDto imageAccess) {
        JSONObject jsonObject = null;
        if (imageAccess != null) {
            jsonObject = new JSONObject();
            jsonObject.put("Id", imageAccess.getId());
            jsonObject.put("Name", imageAccess.getName());
            jsonObject.put("DirectLink", imageAccess.getDirectLink());
            jsonObject.put("PassportLink", imageAccess.getPassportLink());
            jsonObject.put("ThumbnailLink", imageAccess.getThumbnailLink());
        }
        return jsonObject;
    }

}
