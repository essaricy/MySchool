package com.myschool.apar.dto;

import java.io.Serializable;

import com.myschool.apar.constants.Ledger;

/**
 * The Class PayComponent.
 */
public class PayComponent implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The ledger. */
    private Ledger ledger;

    /** The component name. */
    private String componentName;

    /** The amount. */
    private double amount;

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
     * Gets the component name.
     * 
     * @return the component name
     */
    public String getComponentName() {
        return componentName;
    }

    /**
     * Sets the component name.
     * 
     * @param componentName the new component name
     */
    public void setComponentName(String componentName) {
        this.componentName = componentName;
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
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("PayComponent ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("ledger = ").append(this.ledger).append(SEPARATOR)
            .append("componentName = ").append(this.componentName).append(SEPARATOR)
            .append("amount = ").append(this.amount).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
