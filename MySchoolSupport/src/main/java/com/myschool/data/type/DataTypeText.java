package com.myschool.data.type;


public class DataTypeText extends DataType {

    private String[] customList;

    public DataTypeText(String name) {
        super(name);
    }

    public DataTypeText(String name, String[] customList) {
        super(name);
        this.customList=customList;
    }

    public String[] getCustomList() {
        return customList;
    }

    public void setCustomList(String[] customList) {
        this.customList = customList;
    }

}
