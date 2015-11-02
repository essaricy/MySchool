package com.myschool.apar.dto;

import java.io.Serializable;
import java.util.List;

import com.myschool.employee.dto.EmployeeDto;

/**
 * The Class EmployeePay.
 */
public class EmployeePay implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The payment. */
    private Payment payment;

    /** The employee. */
    private EmployeeDto employee;

    /** The pay components. */
    private List<PayComponent> payComponents;

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
     * Gets the employee.
     * 
     * @return the employee
     */
    public EmployeeDto getEmployee() {
        return employee;
    }

    /**
     * Sets the employee.
     * 
     * @param employee the new employee
     */
    public void setEmployee(EmployeeDto employee) {
        this.employee = employee;
    }

    /**
     * Gets the pay components.
     * 
     * @return the pay components
     */
    public List<PayComponent> getPayComponents() {
        return payComponents;
    }

    /**
     * Sets the pay components.
     * 
     * @param payComponents the new pay components
     */
    public void setPayComponents(List<PayComponent> payComponents) {
        this.payComponents = payComponents;
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
        retValue.append("EmployeePay ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("payment = ").append(this.payment).append(SEPARATOR)
            .append("employee = ").append(this.employee).append(SEPARATOR)
            .append("payComponents = ").append(this.payComponents).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
