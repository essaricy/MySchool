package com.myschool.graph.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * The Class LineChartDto.
 */
public class LineChartDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The X axis. */
    private AxisDto XAxis;

    /** The Y axis. */
    private AxisDto YAxis;

    /** The series names. */
    private List<String> seriesNames;

    /** The line series. */
    private List<List<BigDecimal>> lineSeries;

    /**
     * Gets the x axis.
     * 
     * @return the x axis
     */
    public AxisDto getXAxis() {
        return XAxis;
    }

    /**
     * Sets the x axis.
     * 
     * @param xAxis the new x axis
     */
    public void setXAxis(AxisDto xAxis) {
        XAxis = xAxis;
    }

    /**
     * Gets the y axis.
     * 
     * @return the y axis
     */
    public AxisDto getYAxis() {
        return YAxis;
    }

    /**
     * Sets the y axis.
     * 
     * @param yAxis the new y axis
     */
    public void setYAxis(AxisDto yAxis) {
        YAxis = yAxis;
    }

    /**
     * Gets the series names.
     * 
     * @return the series names
     */
    public List<String> getSeriesNames() {
        return seriesNames;
    }

    /**
     * Sets the series names.
     * 
     * @param seriesNames the new series names
     */
    public void setSeriesNames(List<String> seriesNames) {
        this.seriesNames = seriesNames;
    }

    /**
     * Gets the line series.
     * 
     * @return the line series
     */
    public List<List<BigDecimal>> getLineSeries() {
        return lineSeries;
    }

    /**
     * Sets the line series.
     * 
     * @param lineSeries the new line series
     */
    public void setLineSeries(List<List<BigDecimal>> lineSeries) {
        this.lineSeries = lineSeries;
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
        retValue.append("LineChartDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("XAxis = ").append(this.XAxis).append(SEPARATOR)
            .append("YAxis = ").append(this.YAxis).append(SEPARATOR)
            .append("seriesNames = ").append(this.seriesNames).append(SEPARATOR)
            .append("lineSeries = ").append(this.lineSeries).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
