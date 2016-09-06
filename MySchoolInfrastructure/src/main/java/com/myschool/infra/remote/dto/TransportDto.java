package com.myschool.infra.remote.dto;

import java.io.Serializable;
import java.util.List;

public class TransportDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String trackerId;

    private List<MessageDto> messages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(String trackerId) {
        this.trackerId = trackerId;
    }

    public List<MessageDto> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDto> messages) {
        this.messages = messages;
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
        retValue.append("TransportDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("id = ").append(this.id).append(SEPARATOR)
            .append("trackerId = ").append(this.trackerId).append(SEPARATOR)
            .append("messages = ").append(this.messages).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}