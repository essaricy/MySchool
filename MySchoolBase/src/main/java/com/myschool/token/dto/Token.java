package com.myschool.token.dto;

import java.io.Serializable;

/**
 * The Class Token.
 */
public class Token implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The token id. */
    private String tokenId;

    /** The consumed by. */
    private String consumedBy;

    /** The necessity. */
    private String necessity;

    /** The generated on. */
    private String generatedOn;

    /** The disposed on. */
    private String disposedOn;

    /** The validity. */
    private int validity;

    /**
     * Gets the token id.
     *
     * @return the tokenId
     */
    public String getTokenId() {
        return tokenId;
    }

    /**
     * Sets the token id.
     *
     * @param tokenId the tokenId to set
     */
    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    /**
     * Gets the consumed by.
     *
     * @return the consumedBy
     */
    public String getConsumedBy() {
        return consumedBy;
    }

    /**
     * Sets the consumed by.
     *
     * @param consumedBy the consumedBy to set
     */
    public void setConsumedBy(String consumedBy) {
        this.consumedBy = consumedBy;
    }

    /**
     * Gets the necessity.
     *
     * @return the necessity
     */
    public String getNecessity() {
        return necessity;
    }

    /**
     * Sets the necessity.
     *
     * @param necessity the necessity to set
     */
    public void setNecessity(String necessity) {
        this.necessity = necessity;
    }

    /**
     * Gets the generated on.
     *
     * @return the generatedOn
     */
    public String getGeneratedOn() {
        return generatedOn;
    }

    /**
     * Sets the generated on.
     *
     * @param generatedOn the generatedOn to set
     */
    public void setGeneratedOn(String generatedOn) {
        this.generatedOn = generatedOn;
    }

    /**
     * Gets the disposed on.
     *
     * @return the disposedOn
     */
    public String getDisposedOn() {
        return disposedOn;
    }

    /**
     * Sets the disposed on.
     *
     * @param disposedOn the disposedOn to set
     */
    public void setDisposedOn(String disposedOn) {
        this.disposedOn = disposedOn;
    }

    /**
     * Gets the validity.
     *
     * @return the validity
     */
    public int getValidity() {
        return validity;
    }

    /**
     * Sets the validity.
     *
     * @param validity the validity to set
     */
    public void setValidity(int validity) {
        this.validity = validity;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Token [tokenId=").append(tokenId)
                .append(", consumedBy=").append(consumedBy)
                .append(", necessity=").append(necessity)
                .append(", generatedOn=").append(generatedOn)
                .append(", disposedOn=").append(disposedOn)
                .append(", validity=").append(validity).append("]");
        return builder.toString();
    }

}
