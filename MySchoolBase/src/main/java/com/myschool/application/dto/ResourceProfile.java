package com.myschool.application.dto;

import java.io.Serializable;

/**
 * The Class ResourceProfile.
 */
public class ResourceProfile implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The brochures. */
    private ResourceDto brochures;

    /** The features. */
    private ResourceDto features;

    /** The logo. */
    private ResourceDto logo;

    /** The no image. */
    private ResourceDto noImage;

    /** The organization. */
    private ResourceDto organization;

    /** The director. */
    private ResourceDto director;

    /** The greetings. */
    private ResourceDto greetings;

    /** The product. */
    private ResourceDto product;

    /** The gallery. */
    private ResourceDto gallery;

    /** The employee portal. */
    private ResourceDto employeePortal;

    /** The employee registered. */
    private ResourceDto employeeRegistered;

    /** The student portal. */
    private ResourceDto studentPortal;

    /** The student registered. */
    private ResourceDto studentRegistered;

    /**
     * Gets the brochures.
     * 
     * @return the brochures
     */
    public ResourceDto getBrochures() {
        return brochures;
    }

    /**
     * Sets the brochures.
     * 
     * @param brochures the new brochures
     */
    public void setBrochures(ResourceDto brochures) {
        this.brochures = brochures;
    }

    /**
     * Gets the features.
     * 
     * @return the features
     */
    public ResourceDto getFeatures() {
        return features;
    }

    /**
     * Sets the features.
     * 
     * @param features the new features
     */
    public void setFeatures(ResourceDto features) {
        this.features = features;
    }

    /**
     * Gets the logo.
     * 
     * @return the logo
     */
    public ResourceDto getLogo() {
        return logo;
    }

    /**
     * Sets the logo.
     * 
     * @param logo the new logo
     */
    public void setLogo(ResourceDto logo) {
        this.logo = logo;
    }

    /**
     * Gets the no image.
     * 
     * @return the no image
     */
    public ResourceDto getNoImage() {
        return noImage;
    }

    /**
     * Sets the no image.
     * 
     * @param noImage the new no image
     */
    public void setNoImage(ResourceDto noImage) {
        this.noImage = noImage;
    }

    /**
     * Gets the organization.
     * 
     * @return the organization
     */
    public ResourceDto getOrganization() {
        return organization;
    }

    /**
     * Sets the organization.
     * 
     * @param organization the new organization
     */
    public void setOrganization(ResourceDto organization) {
        this.organization = organization;
    }

    /**
     * Gets the director.
     * 
     * @return the director
     */
    public ResourceDto getDirector() {
        return director;
    }

    /**
     * Sets the director.
     * 
     * @param director the new director
     */
    public void setDirector(ResourceDto director) {
        this.director = director;
    }

    /**
     * Gets the greetings.
     * 
     * @return the greetings
     */
    public ResourceDto getGreetings() {
        return greetings;
    }

    /**
     * Sets the greetings.
     * 
     * @param greetings the new greetings
     */
    public void setGreetings(ResourceDto greetings) {
        this.greetings = greetings;
    }

    /**
     * Gets the product.
     * 
     * @return the product
     */
    public ResourceDto getProduct() {
        return product;
    }

    /**
     * Sets the product.
     * 
     * @param product the new product
     */
    public void setProduct(ResourceDto product) {
        this.product = product;
    }

    /**
     * Gets the gallery.
     * 
     * @return the gallery
     */
    public ResourceDto getGallery() {
        return gallery;
    }

    /**
     * Sets the gallery.
     * 
     * @param gallery the new gallery
     */
    public void setGallery(ResourceDto gallery) {
        this.gallery = gallery;
    }

    /**
     * Gets the employee portal.
     * 
     * @return the employee portal
     */
    public ResourceDto getEmployeePortal() {
        return employeePortal;
    }

    /**
     * Sets the employee portal.
     * 
     * @param employeePortal the new employee portal
     */
    public void setEmployeePortal(ResourceDto employeePortal) {
        this.employeePortal = employeePortal;
    }

    /**
     * Gets the employee registered.
     * 
     * @return the employee registered
     */
    public ResourceDto getEmployeeRegistered() {
        return employeeRegistered;
    }

    /**
     * Sets the employee registered.
     * 
     * @param employeeRegistered the new employee registered
     */
    public void setEmployeeRegistered(ResourceDto employeeRegistered) {
        this.employeeRegistered = employeeRegistered;
    }

    /**
     * Gets the student portal.
     * 
     * @return the student portal
     */
    public ResourceDto getStudentPortal() {
        return studentPortal;
    }

    /**
     * Sets the student portal.
     * 
     * @param studentPortal the new student portal
     */
    public void setStudentPortal(ResourceDto studentPortal) {
        this.studentPortal = studentPortal;
    }

    /**
     * Gets the student registered.
     * 
     * @return the student registered
     */
    public ResourceDto getStudentRegistered() {
        return studentRegistered;
    }

    /**
     * Sets the student registered.
     * 
     * @param studentRegistered the new student registered
     */
    public void setStudentRegistered(ResourceDto studentRegistered) {
        this.studentRegistered = studentRegistered;
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
        retValue.append("ResourceProfile ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("brochures = ").append(this.brochures).append(SEPARATOR)
            .append("features = ").append(this.features).append(SEPARATOR)
            .append("logo = ").append(this.logo).append(SEPARATOR)
            .append("noImage = ").append(this.noImage).append(SEPARATOR)
            .append("organization = ").append(this.organization).append(SEPARATOR)
            .append("director = ").append(this.director).append(SEPARATOR)
            .append("greetings = ").append(this.greetings).append(SEPARATOR)
            .append("product = ").append(this.product).append(SEPARATOR)
            .append("gallery = ").append(this.gallery).append(SEPARATOR)
            .append("employeePortal = ").append(this.employeePortal).append(SEPARATOR)
            .append("employeeRegistered = ").append(this.employeeRegistered).append(SEPARATOR)
            .append("studentPortal = ").append(this.studentPortal).append(SEPARATOR)
            .append("studentRegistered = ").append(this.studentRegistered).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
