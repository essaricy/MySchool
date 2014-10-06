package com.myschool.report.dto;

import java.io.Serializable;

/**
 * The Class DatasetDto.
 */
public class DatasetDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The plot value. */
    private double plotValue;

    /** The x value. */
    private String xValue;

    /** The y value. */
    private String yValue;

    /**
     * Gets the plot value.
     *
     * @return the plot value
     */
    public double getPlotValue() {
        return plotValue;
    }

    /**
     * Sets the plot value.
     *
     * @param plotValue the new plot value
     */
    public void setPlotValue(double plotValue) {
        this.plotValue = plotValue;
    }

    /**
     * Gets the x value.
     *
     * @return the x value
     */
    public String getXValue() {
        return xValue;
    }

    /**
     * Sets the x value.
     *
     * @param value the new x value
     */
    public void setXValue(String value) {
        xValue = value;
    }

    /**
     * Gets the y value.
     *
     * @return the y value
     */
    public String getYValue() {
        return yValue;
    }

    /**
     * Sets the y value.
     *
     * @param value the new y value
     */
    public void setYValue(String value) {
        yValue = value;
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
        retValue.append("DatasetDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("plotValue = ").append(this.plotValue).append(SEPARATOR)
            .append("xValue = ").append(this.xValue).append(SEPARATOR)
            .append("yValue = ").append(this.yValue).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
