package com.myschool.branch.dto;

import java.io.Serializable;

/**
 * The Class RegionDto.
 */
public class RegionDto implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The region id. */
    private int regionId;
    
    /** The region name. */
    private String regionName;
    
    /**
     * Gets the region id.
     *
     * @return the region id
     */
    public int getRegionId() {
        return regionId;
    }
    
    /**
     * Sets the region id.
     *
     * @param regionId the new region id
     */
    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }
    
    /**
     * Gets the region name.
     *
     * @return the region name
     */
    public String getRegionName() {
        return regionName;
    }
    
    /**
     * Sets the region name.
     *
     * @param regionName the new region name
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
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
        retValue.append("RegionDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("regionId = ").append(this.regionId).append(SEPARATOR)
            .append("regionName = ").append(this.regionName).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
