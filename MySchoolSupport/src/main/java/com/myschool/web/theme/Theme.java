package com.myschool.web.theme;

/**
 * The Class Theme.
 */
public class Theme {

    /** The code. */
    private String code;

    /** The name. */
    private String name;

    /** The primary. */
    private String primary;

    /** The secondary. */
    private String secondary;

    /** The primary variant. */
    private String primaryVariant;

    /** The secondary variant. */
    private String secondaryVariant;

    /** The grayed out. */
    private String grayedOut;

    /**
     * Instantiates a new theme.
     * 
     * @return the name
     */
    /*public Theme(String code, String name, String primary, String primaryVariant, String secondary, String secondaryVariant, String grayedOut) {
        this.name=name;
        this.primary=primary;
        this.secondary=secondary;
        this.primaryVariant=primaryVariant;
        this.secondaryVariant=secondaryVariant;
        this.grayedOut=grayedOut;
    }*/

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * Gets the code.
     * 
     * @return the code
     */
    public String getCode() {
        return code;
    }


    /**
     * Sets the code.
     * 
     * @param code the new code
     */
    public void setCode(String code) {
        this.code = code;
    }


    /**
     * Sets the name.
     * 
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the primary.
     * 
     * @return the primary
     */
    public String getPrimary() {
        return primary;
    }

    /**
     * Sets the primary.
     * 
     * @param primary the new primary
     */
    public void setPrimary(String primary) {
        this.primary = primary;
    }

    /**
     * Gets the secondary.
     * 
     * @return the secondary
     */
    public String getSecondary() {
        return secondary;
    }

    /**
     * Sets the secondary.
     * 
     * @param secondary the new secondary
     */
    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }

    /**
     * Gets the primary variant.
     * 
     * @return the primary variant
     */
    public String getPrimaryVariant() {
        return primaryVariant;
    }

    /**
     * Sets the primary variant.
     * 
     * @param primaryVariant the new primary variant
     */
    public void setPrimaryVariant(String primaryVariant) {
        this.primaryVariant = primaryVariant;
    }

    /**
     * Gets the secondary variant.
     * 
     * @return the secondary variant
     */
    public String getSecondaryVariant() {
        return secondaryVariant;
    }

    /**
     * Sets the secondary variant.
     * 
     * @param secondaryVariant the new secondary variant
     */
    public void setSecondaryVariant(String secondaryVariant) {
        this.secondaryVariant = secondaryVariant;
    }

    /**
     * Gets the grayed out.
     * 
     * @return the grayed out
     */
    public String getGrayedOut() {
        return grayedOut;
    }

    /**
     * Sets the grayed out.
     * 
     * @param grayedOut the new grayed out
     */
    public void setGrayedOut(String grayedOut) {
        this.grayedOut = grayedOut;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Theme [code=").append(code).append(", name=")
                .append(name).append(", primary=").append(primary)
                .append(", secondary=").append(secondary)
                .append(", primaryVariant=").append(primaryVariant)
                .append(", secondaryVariant=").append(secondaryVariant)
                .append(", grayedOut=").append(grayedOut).append("]");
        return builder.toString();
    }

}
