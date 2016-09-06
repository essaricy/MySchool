package com.myschool.common.assembler;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.application.assembler.GalleryDataAssembler;
import com.myschool.application.dto.GalleryDetailDto;
import com.myschool.common.dto.ResultDto;

/**
 * The Class ResultDataAssembler.
 */
public class ResultDataAssembler {

    /**
     * Creates the.
     *
     * @param resultList the result list
     * @return the jSON array
     */
    public static JSONArray create(List<ResultDto> resultList) {
        JSONArray jsonArray = null;
        if (resultList != null && !resultList.isEmpty()) {
            jsonArray = new JSONArray();
            for (ResultDto result : resultList) {
                jsonArray.put(create(result));
            }
        }
        return jsonArray;
    }

    /**
     * Creates the.
     *
     * @param result the result dto
     * @return the jSON object
     */
    private static JSONObject create(ResultDto result) {
        JSONObject jsonObject = new JSONObject();
        if (result != null) {
            jsonObject.put("Successful", result.isSuccessful());
            jsonObject.put("StatusMessage", result.getStatusMessage());
            jsonObject.put("ReferenceNumber", result.getReferenceNumber());
        }
        return jsonObject;
    }

    /**
     * Creates the json.
     *
     * @param result the result
     * @return the JSON object
     */
    public static JSONObject createJSON(ResultDto result) {
        JSONObject jsonObject = null;
        if (result != null) {
            jsonObject = new JSONObject();
            jsonObject.put("Successful", result.isSuccessful());
            jsonObject.put("StatusMessage", result.getStatusMessage());
            jsonObject.put("ReferenceNumber", result.getReferenceNumber());
            Object reference = result.getReference();
            if (reference instanceof GalleryDetailDto) {
                jsonObject.put("Reference", GalleryDataAssembler.createJSON((GalleryDetailDto)reference));
            }
        }
        return jsonObject;
    }

}
