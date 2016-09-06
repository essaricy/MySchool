package com.myschool.data.type;


public class DataTypeName extends DataType {

    public static final int FIRST_NAME = 0;

    public static final int MIDDLE_NAME = 1;

    public static final int LAST_NAME = 2;

    private int placement;

    private String[] customList;

    public DataTypeName(String name, int placement) {
        this(name, placement, null);
    }

    public DataTypeName(String name, String[] customList) {
        this(name, 0, customList);
    }

    public DataTypeName(String name, int placement, String[] customList) {
        super(name);
        this.placement=placement;
        this.customList=customList;
    }

    public int getPlacement() {
        return placement;
    }

    public void setPlacement(int placement) {
        this.placement = placement;
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
        retValue.append("DataTypeName ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("placement = ").append(this.placement).append(SEPARATOR)
            .append("customList = ").append(this.customList).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
