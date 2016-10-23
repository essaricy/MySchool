package com.myschool.template.dto;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * The Class TemplateGroup.
 */
public class TemplateGroup implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The name. */
    private String name;

    /** The delivery method. */
    private String deliveryMethod;

    /** The usage. */
    private String usage;

    /** The group file. */
    private File groupFile;

    /** The template file name pattern. */
    private String templateFileNamePattern;

    /** The example file name pattern. */
    private String exampleFileNamePattern;

    /** The templates. */
    private List<Template> templates;

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the delivery method.
     *
     * @return the deliveryMethod
     */
    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    /**
     * Sets the delivery method.
     *
     * @param deliveryMethod the deliveryMethod to set
     */
    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    /**
     * Gets the usage.
     *
     * @return the usage
     */
    public String getUsage() {
        return usage;
    }

    /**
     * Sets the usage.
     *
     * @param usage the usage to set
     */
    public void setUsage(String usage) {
        this.usage = usage;
    }

    /**
     * Gets the group file.
     *
     * @return the groupFile
     */
    public File getGroupFile() {
        return groupFile;
    }

    /**
     * Sets the group file.
     *
     * @param groupFile the groupFile to set
     */
    public void setGroupFile(File groupFile) {
        this.groupFile = groupFile;
    }

    /**
     * Gets the template file name pattern.
     *
     * @return the templateFileNamePattern
     */
    public String getTemplateFileNamePattern() {
        return templateFileNamePattern;
    }

    /**
     * Sets the template file name pattern.
     *
     * @param templateFileNamePattern the templateFileNamePattern to set
     */
    public void setTemplateFileNamePattern(String templateFileNamePattern) {
        this.templateFileNamePattern = templateFileNamePattern;
    }

    /**
     * Gets the example file name pattern.
     *
     * @return the exampleFileNamePattern
     */
    public String getExampleFileNamePattern() {
        return exampleFileNamePattern;
    }

    /**
     * Sets the example file name pattern.
     *
     * @param exampleFileNamePattern the exampleFileNamePattern to set
     */
    public void setExampleFileNamePattern(String exampleFileNamePattern) {
        this.exampleFileNamePattern = exampleFileNamePattern;
    }

    /**
     * Gets the templates.
     *
     * @return the templates
     */
    public List<Template> getTemplates() {
        return templates;
    }

    /**
     * Sets the templates.
     *
     * @param templates the templates to set
     */
    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final int maxLen = 10;
        StringBuilder builder = new StringBuilder();
        builder.append("TemplateGroup [name=").append(name)
                .append(", deliveryMethod=").append(deliveryMethod)
                .append(", usage=").append(usage).append(", groupFile=")
                .append(groupFile).append(", templateFileNamePattern=")
                .append(templateFileNamePattern).append(
                        ", exampleFileNamePattern=")
                .append(exampleFileNamePattern)
                .append(", templates=").append(
                        templates != null
                                ? templates.subList(0,
                                        Math.min(templates.size(), maxLen))
                                : null)
                .append("]");
        return builder.toString();
    }

}
