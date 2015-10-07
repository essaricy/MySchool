package com.myschool.application.assembler;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.application.dto.GalleryDetailDto;
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
	 * Creates the gallery details.
	 *
	 * @param galleryFiles the gallery files
	 * @return the list
	 */
	public static List<GalleryDetailDto> createGalleryDetails(List<File> galleryFiles) {
		List<GalleryDetailDto> galleryDetailDtos = null;
		if (galleryFiles != null && !galleryFiles.isEmpty()) {
    		galleryDetailDtos = new ArrayList<GalleryDetailDto>();
    		for (File galleryFile : galleryFiles) {
    			galleryDetailDtos.add(GalleryDataAssembler.createGalleryDetail(galleryFile));
			}
    	}
		return galleryDetailDtos;
	}

	/**
	 * Creates the gallery detail.
	 *
	 * @param galleryFile the gallery file
	 * @return the gallery detail dto
	 */
	public static GalleryDetailDto createGalleryDetail(File galleryFile) {
		GalleryDetailDto galleryDetailDto = null;
		if (galleryFile != null) {
			galleryDetailDto = new GalleryDetailDto();
			galleryDetailDto.setGalleryName(galleryFile.getName());
			galleryDetailDto.setLastModified(ConversionUtil.toApplicationDate(galleryFile.lastModified()));
			galleryDetailDto.setSize(String.valueOf(galleryFile.length()));
			galleryDetailDto.setFile(galleryFile);
		}
		return galleryDetailDto;
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
			jsonObject.put("LastModified", galleryDetail.getLastModified());
			jsonObject.put("Size", galleryDetail.getSize());
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

}
