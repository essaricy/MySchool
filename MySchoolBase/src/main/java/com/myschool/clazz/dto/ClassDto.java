package com.myschool.clazz.dto;

import java.io.Serializable;

/**
 * The Class ClassDto.
 */
public class ClassDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The class id. */
    private int classId;

    /** The class name. */
    private String className;

    /** The promotion order. */
    private int promotionOrder;

    /**
     * Gets the class id.
     * 
     * @return the class id
     */
    public int getClassId() {
        return classId;
    }

    /**
     * Sets the class id.
     * 
     * @param classId the new class id
     */
    public void setClassId(int classId) {
        this.classId = classId;
    }

    /**
     * Gets the class name.
     * 
     * @return the class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the class name.
     * 
     * @param className the new class name
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Gets the promotion order.
     *
     * @return the promotion order
     */
    public int getPromotionOrder() {
        return promotionOrder;
    }

    /**
     * Sets the promotion order.
     *
     * @param promotionOrder the new promotion order
     */
    public void setPromotionOrder(int promotionOrder) {
        this.promotionOrder = promotionOrder;
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
        retValue.append("ClassDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("classId = ").append(this.classId).append(SEPARATOR)
            .append("className = ").append(this.className).append(SEPARATOR)
            .append("promotionOrder = ").append(this.promotionOrder).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
