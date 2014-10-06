package com.myschool.apar.dto;

import java.io.Serializable;

/**
 * The Class Payment.
 */
public class Payment implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The amount paid. */
    protected double amountPaid;

    /** The payment date. */
    protected String paymentDate;

    /**
     * Gets the amount paid.
     * 
     * @return the amount paid
     */
    public double getAmountPaid() {
        return amountPaid;
    }

    /**
     * Sets the amount paid.
     * 
     * @param amountPaid the new amount paid
     */
    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    /**
     * Gets the payment date.
     * 
     * @return the payment date
     */
    public String getPaymentDate() {
        return paymentDate;
    }

    /**
     * Sets the payment date.
     * 
     * @param paymentDate the new payment date
     */
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
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
        retValue.append("Payment ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("amountPaid = ").append(this.amountPaid).append(SEPARATOR)
            .append("paymentDate = ").append(this.paymentDate).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
