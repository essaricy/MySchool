package com.myschool.data;

import java.util.HashMap;
import java.util.Map;

public class DataFactory {

    private Map<String, String[]> customListDataMap;

    private static DataFactory INSTANCE;

    private DataFactory() {
        customListDataMap = new HashMap<String, String[]>();
    }

    public static DataFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataFactory();
        }
        return INSTANCE;
    }

    public void addCustomData(String key, String[] customData) {
        customListDataMap.put(key, customData);
    }

    public Map<String, String[]> getCustomListData() {
        return customListDataMap;
    }

    public void setCustomListData(Map<String, String[]> customListData) {
        this.customListDataMap = customListData;
    }

}
