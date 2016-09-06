package com.myschool.infra.remote.dto;

import java.io.Serializable;

public class CommandDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;

    private Object content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
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
        retValue.append("CommandDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("type = ").append(this.type).append(SEPARATOR)
            .append("content = ").append(this.content).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
