package com.myschool.apar.dto;

import java.io.Serializable;

import com.myschool.apar.constants.Ledger;

/**
 * The Class FeeTransaction.
 */
public class FeeTransaction implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The particulars. */
    private String particulars;
    
    /** The ledger. */
    private Ledger ledger;
    
    /** The due date. */
    private String dueDate;
    
    /** The transaction date. */
    private String transactionDate;
    
    /** The amount. */
    private double amount;
    
    /** The payment. */
    private Payment payment;

    /**
     * Gets the particulars.
     * 
     * @return the particulars
     */
    public String getParticulars() {
        return particulars;
    }

    /**
     * Sets the particulars.
     * 
     * @param particulars the new particulars
     */
    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    /**
     * Gets the ledger.
     * 
     * @return the ledger
     */
    public Ledger getLedger() {
        return ledger;
    }

    /**
     * Sets the ledger.
     * 
     * @param ledger the new ledger
     */
    public void setLedger(Ledger ledger) {
        this.ledger = ledger;
    }

    /**
     * Gets the due date.
     * 
     * @return the due date
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date.
     * 
     * @param dueDate the new due date
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the transaction date.
     * 
     * @return the transaction date
     */
    public String getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the transaction date.
     * 
     * @param transactionDate the new transaction date
     */
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * Gets the amount.
     * 
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount.
     * 
     * @param amount the new amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the payment.
     * 
     * @return the payment
     */
    public Payment getPayment() {
        return payment;
    }

    /**
     * Sets the payment.
     * 
     * @param payment the new payment
     */
    public void setPayment(Payment payment) {
        this.payment = payment;
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
        retValue.append("FeeTransaction ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("particulars = ").append(this.particulars).append(SEPARATOR)
            .append("ledger = ").append(this.ledger).append(SEPARATOR)
            .append("dueDate = ").append(this.dueDate).append(SEPARATOR)
            .append("transactionDate = ").append(this.transactionDate).append(SEPARATOR)
            .append("amount = ").append(this.amount).append(SEPARATOR)
            .append("payment = ").append(this.payment).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
