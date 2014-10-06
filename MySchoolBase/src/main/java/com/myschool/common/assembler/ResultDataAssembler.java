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
            for (ResultDto resultDto : resultList) {
                jsonArray.put(create(resultDto));
            }
        }
        return jsonArray;
    }

    /**
     * Creates the.
     * 
     * @param resultDto the result dto
     * @return the jSON object
     * @throws JSONException the jSON exception
     */
    private static JSONObject create(ResultDto resultDto) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if (resultDto != null) {
            jsonObject.put("Successful", resultDto.isSuccessful());
            jsonObject.put("StatusMessage", resultDto.getStatusMessage());
            jsonObject.put("ReferenceNumber", resultDto.getReferenceNumber());
        }
        return jsonObject;
    }

}
