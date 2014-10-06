package com.myschool.apar.dto;

import java.io.Serializable;

/**
 * The Class ChequePayment.
 */
public class ChequePayment extends Payment implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The cheque number. */
    private String chequeNumber;

    /** The issuing banker. */
    private String issuingBanker;

    /** The check realization date. */
    private String checkRealizationDate;

    /**
     * Gets the cheque number.
     * 
     * @return the cheque number
     */
    public String getChequeNumber() {
        return chequeNumber;
    }

    /**
     * Sets the cheque number.
     * 
     * @param chequeNumber the new cheque number
     */
    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    /**
     * Gets the issuing banker.
     * 
     * @return the issuing banker
     */
    public String getIssuingBanker() {
        return issuingBanker;
    }

    /**
     * Sets the issuing banker.
     * 
     * @param issuingBanker the new issuing banker
     */
    public void setIssuingBanker(String issuingBanker) {
        this.issuingBanker = issuingBanker;
    }

    /**
     * Gets the check realization date.
     * 
     * @return the check realization date
     */
    public String getCheckRealizationDate() {
        return checkRealizationDate;
    }

    /**
     * Sets the check realization date.
     * 
     * @param checkRealizationDate the new check realization date
     */
    public void setCheckRealizationDate(String checkRealizationDate) {
        this.checkRealizationDate = checkRealizationDate;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value
     * format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("ChequePayment ( ").append(super.toString())
                .append(SEPARATOR).append("chequeNumber = ")
                .append(this.chequeNumber).append(SEPARATOR)
                .append("issuingBanker = ").append(this.issuingBanker)
                .append(SEPARATOR).append("checkRealizationDate = ")
                .append(this.checkRealizationDate).append(SEPARATOR)
                .append(" )\n");
        return retValue.toString();
    }

}