package com.myschool.infra.remote.dto;

import java.io.Serializable;

public class MessageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String instruct;

    private Object content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstruct() {
        return instruct;
    }

    public void setInstruct(String instruct) {
        this.instruct = instruct;
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
        retValue.append("MessageDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("id = ").append(this.id).append(SEPARATOR)
            .append("instruct = ").append(this.instruct).append(SEPARATOR)
            .append("content = ").append(this.content).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
