package com.myschool.common.assembler;

import java.util.List;

import org.json.JSONArray;

/**
 * The Class CollectionDataAssembler.
 */
public class CollectionDataAssembler {

    /**
     * Creates the.
     * 
     * @param values the values
     * @return the jSON array
     */
    public static JSONArray createJSONArray(List<? extends Object> values) {
        JSONArray jsonArray = null;
        if (values != null) {
            jsonArray = new JSONArray();
            for (Object value : values) {
                jsonArray.put(value);
            }
        }
        return jsonArray;
    }

    /**
     * Creates the json array of array.
     * 
     * @param lineSeriesData the line series data
     * @return the jSON array
     */
    public static JSONArray createJSONArrayOfArray(List<? extends List<? extends Object>> lineSeriesData) {
        JSONArray lineSeriesJSONArray = null;
        if (lineSeriesData != null) {
            lineSeriesJSONArray = new JSONArray();
            for (List<? extends Object> lineSeries : lineSeriesData) {
                lineSeriesJSONArray.put(createJSONArray(lineSeries));
            }
        }
        return lineSeriesJSONArray;
    }

}
