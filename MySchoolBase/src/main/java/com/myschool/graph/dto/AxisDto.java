package com.myschool.graph.dto;

import java.io.Serializable;
import java.util.List;

/**
 * The Class AxisDto.
 */
public class AxisDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The label. */
    private String label;

    /** The markers. */
    private List<String> markers;

    /**
     * Gets the label.
     * 
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label.
     * 
     * @param label the new label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets the markers.
     * 
     * @return the markers
     */
    public List<String> getMarkers() {
        return markers;
    }

    /**
     * Sets the markers.
     * 
     * @param markers the new markers
     */
    public void setMarkers(List<String> markers) {
        this.markers = markers;
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
        retValue.append("AxisDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("label = ").append(this.label).append(SEPARATOR)
            .append("markers = ").append(this.markers).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
