package com.myschool.template.dto;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * The Class TemplateConfig.
 */
public class TemplateConfig implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The base path. */
    private String basePath;

    /** The base dir. */
    private File baseDir;

    /** The template groups. */
    private List<TemplateGroup> templateGroups;

    /**
     * Gets the base path.
     *
     * @return the basePath
     */
    public String getBasePath() {
        return basePath;
    }

    /**
     * Sets the base path.
     *
     * @param basePath the basePath to set
     */
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    /**
     * Gets the base dir.
     *
     * @return the baseDir
     */
    public File getBaseDir() {
        return baseDir;
    }

    /**
     * Sets the base dir.
     *
     * @param baseDir the baseDir to set
     */
    public void setBaseDir(File baseDir) {
        this.baseDir = baseDir;
    }

    /**
     * Gets the template groups.
     *
     * @return the templateGroups
     */
    public List<TemplateGroup> getTemplateGroups() {
        return templateGroups;
    }

    /**
     * Sets the template groups.
     *
     * @param templateGroups the templateGroups to set
     */
    public void setTemplateGroups(List<TemplateGroup> templateGroups) {
        this.templateGroups = templateGroups;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final int maxLen = 10;
        StringBuilder builder = new StringBuilder();
        builder.append("TemplateConfig [basePath=").append(basePath)
                .append(", templateGroups=")
                .append(templateGroups != null ? templateGroups.subList(0,
                        Math.min(templateGroups.size(), maxLen)) : null)
                .append("]");
        return builder.toString();
    }

}
