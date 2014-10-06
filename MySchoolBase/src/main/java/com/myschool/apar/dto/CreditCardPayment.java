package com.myschool.apar.dto;

import java.io.Serializable;

/**
 * The Class CreditCardPayment.
 */
public class CreditCardPayment extends Payment implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The card number. */
    private String cardNumber;

    /**
     * Gets the card number.
     * 
     * @return the card number
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets the card number.
     * 
     * @param cardNumber the new card number
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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
        retValue.append("CreditCardPayment ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("cardNumber = ").append(this.cardNumber).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
