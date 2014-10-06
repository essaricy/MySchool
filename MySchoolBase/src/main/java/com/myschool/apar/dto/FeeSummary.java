package com.myschool.apar.dto;

import java.io.Serializable;
import java.util.List;

/**
 * The Class FeeSummary.
 */
public class FeeSummary implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The total fee to pay. */
    private double totalFeeToPay;

    /** The total fee paid. */
    private double totalFeePaid;

    /** The total remaining fee to pay. */
    private double totalRemainingFeeToPay;

    /** The fee transactions. */
    private List<FeeTransaction> feeTransactions;

    /**
     * Gets the total fee to pay.
     * 
     * @return the total fee to pay
     */
    public double getTotalFeeToPay() {
        return totalFeeToPay;
    }

    /**
     * Sets the total fee to pay.
     * 
     * @param totalFeeToPay the new total fee to pay
     */
    public void setTotalFeeToPay(double totalFeeToPay) {
        this.totalFeeToPay = totalFeeToPay;
    }

    /**
     * Gets the total fee paid.
     * 
     * @return the total fee paid
     */
    public double getTotalFeePaid() {
        return totalFeePaid;
    }

    /**
     * Sets the total fee paid.
     * 
     * @param totalFeePaid the new total fee paid
     */
    public void setTotalFeePaid(double totalFeePaid) {
        this.totalFeePaid = totalFeePaid;
    }

    /**
     * Gets the total remaining fee to pay.
     * 
     * @return the total remaining fee to pay
     */
    public double getTotalRemainingFeeToPay() {
        return totalRemainingFeeToPay;
    }

    /**
     * Sets the total remaining fee to pay.
     * 
     * @param totalRemainingFeeToPay the new total remaining fee to pay
     */
    public void setTotalRemainingFeeToPay(double totalRemainingFeeToPay) {
        this.totalRemainingFeeToPay = totalRemainingFeeToPay;
    }

    /**
     * Gets the fee transactions.
     * 
     * @return the fee transactions
     */
    public List<FeeTransaction> getFeeTransactions() {
        return feeTransactions;
    }

    /**
     * Sets the fee transactions.
     * 
     * @param feeTransactions the new fee transactions
     */
    public void setFeeTransactions(List<FeeTransaction> feeTransactions) {
        this.feeTransactions = feeTransactions;
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
        retValue.append("FeeSummary ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("totalFeeToPay = ").append(this.totalFeeToPay).append(SEPARATOR)
            .append("totalFeePaid = ").append(this.totalFeePaid).append(SEPARATOR)
            .append("totalRemainingFeeToPay = ").append(this.totalRemainingFeeToPay).append(SEPARATOR)
            .append("feeTransactions = ").append(this.feeTransactions).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}