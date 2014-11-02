package com.myschool.common.assembler;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

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
     * @throws JSONException the jSON exception
     */
    public static JSONArray create(List<ResultDto> resultList) throws JSONException {
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
     * @throws JSONException the jSON exception
     */
    private static JSONObject create(ResultDto result) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if (result != null) {
            jsonObject.put("Successful", result.isSuccessful());
            jsonObject.put("StatusMessage", result.getStatusMessage());
            jsonObject.put("ReferenceNumber", result.getReferenceNumber());
        }
        return jsonObject;
    }

}
