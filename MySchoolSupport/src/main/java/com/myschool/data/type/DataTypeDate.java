package com.myschool.data.type;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTypeDate extends DataType {

    private Date after;

    private Date before;

    private SimpleDateFormat format;

    public DataTypeDate(String name) {
        super(name);
    }

    public DataTypeDate(String name, Date after, Date before, SimpleDateFormat format) {
        super(name);
        this.after = after;
        this.before = before;
        this.format = format;
    }

    public Date getAfter() {
        return after;
    }

    public void setAfter(Date after) {
        this.after = after;
    }

    public Date getBefore() {
        return before;
    }

    public void setBefore(Date before) {
        this.before = before;
    }

    public SimpleDateFormat getFormat() {
        return format;
    }

    public void setFormat(SimpleDateFormat format) {
        this.format = format;
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
        retValue.append("DataTypeDate ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("after = ").append(this.after).append(SEPARATOR)
            .append("before = ").append(this.before).append(SEPARATOR)
            .append("format = ").append(this.format).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
