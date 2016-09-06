package com.myschool.data.type;


public class DataTypeCustomList extends DataType {

    private String token;

    private String[] customList;

    public DataTypeCustomList(String name) {
        this(name, null, null);
    }

    public DataTypeCustomList(String name, String token) {
        this(name, token, null);
    }

    public DataTypeCustomList(String name, String token, String[] customList) {
        super(name);
        this.token=token;
        this.customList=customList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String[] getCustomList() {
        return customList;
    }

    public void setCustomList(String[] customList) {
        this.customList = customList;
    }

    /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("DataTypeCustomList ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("token = ").append(this.token).append(SEPARATOR)
            .append("customList = ").append(this.customList).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
