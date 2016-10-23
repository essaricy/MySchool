package com.myschool.template.dto;

import java.io.File;
import java.io.Serializable;

/**
 * The Class Template.
 */
public class Template implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    private String id;

    /** The template name. */
    private String templateName;

    /** The example name. */
    private String exampleName;

    /** The template relative path. */
    private String templateRelativePath;

    /** The example relative path. */
    private String exampleRelativePath;

    /** The template file. */
    private File templateFile;

    /** The example file. */
    private File exampleFile;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the template name.
     *
     * @return the templateName
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * Sets the template name.
     *
     * @param templateName the templateName to set
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * Gets the example name.
     *
     * @return the exampleName
     */
    public String getExampleName() {
        return exampleName;
    }

    /**
     * Sets the example name.
     *
     * @param exampleName the exampleName to set
     */
    public void setExampleName(String exampleName) {
        this.exampleName = exampleName;
    }

    /**
     * Gets the template relative path.
     *
     * @return the templateRelativePath
     */
    public String getTemplateRelativePath() {
        return templateRelativePath;
    }

    /**
     * Sets the template relative path.
     *
     * @param templateRelativePath the templateRelativePath to set
     */
    public void setTemplateRelativePath(String templateRelativePath) {
        this.templateRelativePath = templateRelativePath;
    }

    /**
     * Gets the example relative path.
     *
     * @return the exampleRelativePath
     */
    public String getExampleRelativePath() {
        return exampleRelativePath;
    }

    /**
     * Sets the example relative path.
     *
     * @param exampleRelativePath the exampleRelativePath to set
     */
    public void setExampleRelativePath(String exampleRelativePath) {
        this.exampleRelativePath = exampleRelativePath;
    }

    /**
     * Gets the template file.
     *
     * @return the templateFile
     */
    public File getTemplateFile() {
        return templateFile;
    }

    /**
     * Sets the template file.
     *
     * @param templateFile the templateFile to set
     */
    public void setTemplateFile(File templateFile) {
        this.templateFile = templateFile;
    }

    /**
     * Gets the example file.
     *
     * @return the exampleFile
     */
    public File getExampleFile() {
        return exampleFile;
    }

    /**
     * Sets the example file.
     *
     * @param exampleFile the exampleFile to set
     */
    public void setExampleFile(File exampleFile) {
        this.exampleFile = exampleFile;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Template [id=").append(id).append(", templateName=")
                .append(templateName).append(", exampleName=")
                .append(exampleName).append(", templateRelativePath=")
                .append(templateRelativePath).append(", exampleRelativePath=")
                .append(exampleRelativePath).append(", templateFile=")
                .append(templateFile).append(", exampleFile=")
                .append(exampleFile).append("]");
        return builder.toString();
    }

}
