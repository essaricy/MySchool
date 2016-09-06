package com.myschool.application.dto;

import java.io.Serializable;
import java.util.List;

public class ResourcesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ResourceDto> resources;

    public List<ResourceDto> getResources() {
        return resources;
    }

    public void setResources(List<ResourceDto> resources) {
        this.resources = resources;
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
        retValue.append("ResourcesDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("resources = ").append(this.resources).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
