package com.myschool.infra.notification;

import com.myschool.common.dto.Person;

public class Message {

    private String type;

    private String id;

    private long timestamp;

    private Person sendTo;

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the sendTo
     */
    public Person getSendTo() {
        return sendTo;
    }

    /**
     * @param sendTo the sendTo to set
     */
    public void setSendTo(Person sendTo) {
        this.sendTo = sendTo;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Message [type=").append(type).append(", id=").append(id)
                .append(", timestamp=").append(timestamp).append(", sendTo=")
                .append(sendTo).append("]");
        return builder.toString();
    }

}
