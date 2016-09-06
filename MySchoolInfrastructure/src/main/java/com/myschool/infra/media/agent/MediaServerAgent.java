package com.myschool.infra.media.agent;

import java.util.List;

import org.springframework.stereotype.Component;

import com.myschool.application.dto.ResourceDto;
import com.myschool.infra.agent.AbstractAgent;
import com.myschool.infra.media.dto.MediaServerConfigDto;
import com.myschool.infra.media.exception.ResourceException;

/**
 * The Class MediaServerAgent.
 */
@Deprecated
@Component
public abstract class MediaServerAgent extends AbstractAgent {

    /** The Constant BROCHURES. */
    public static final String BROCHURES = "brochures";

    /** The Constant FEATURES. */
    public static final String FEATURES = "features";

    /** The Constant LOGO. */
    public static final String LOGO = "logo";

    /** The Constant NO_IMAGE. */
    public static final String NO_IMAGE = "no_image";

    /** The Constant DIRECTOR. */
    public static final String DIRECTOR = "director";

    /** The Constant ORGANIZATION. */
    public static final String ORGANIZATION = "organization";

    /** The Constant GALLERY. */
    public static final String GALLERY = "gallery";

    /** The Constant EMPLOYEE_PORTAL. */
    public static final String EMPLOYEE_PORTAL = "employee_portal";

    /** The Constant EMPLOYEE_REGISTERED. */
    public static final String EMPLOYEE_REGISTERED = "employee_registered";

    /** The Constant STUDENT_PORTAL. */
    public static final String STUDENT_PORTAL = "student_portal";

    /** The Constant STUDENT_REGISTERED. */
    public static final String STUDENT_REGISTERED = "student_registered";

    /** The Constant GREETINGS. */
    public static final String GREETINGS = "greetings";

    /** The Constant PRODUCT. */
    public static final String PRODUCT = "product";

    /** The Constant RESOURCE_NAME. */
    protected static final String RESOURCE_NAME = "@RESOURCE_NAME@";

    /** The Constant PARENT_URL. */
    protected static final String PARENT_URL = "@PARENT_URL@";

    /** The Constant DESC_FILE_SUFFIX. */
    protected static final String DESC_FILE_SUFFIX="-desc.xml";

    /** The media server config. */
    protected MediaServerConfigDto mediaServerConfig;

    /**
     * Gets the resource items.
     * 
     * @param resourceId the resource id
     * @return the resource items
     * @throws ResourceException the resource exception
     */
    protected abstract List<ResourceDto> getResourceItems(String resourceId) throws ResourceException;

    /**
     * Gets the resource items.
     * 
     * @param resourceId the resource id
     * @param childResourceName the child resource name
     * @return the resource items
     * @throws ResourceException the resource exception
     */
    protected abstract List<ResourceDto> getResourceItems(String resourceId, String childResourceName) throws ResourceException;

    /**
     * Gets the resource items.
     * 
     * @param resourceUrl the resource url
     * @param excludes the excludes
     * @return the resource items
     * @throws ResourceException the resource exception
     */
    protected abstract List<ResourceDto> getResourceItems(String resourceUrl, List<String> excludes) throws ResourceException;

    /**
     * Fill desc.
     * 
     * @param resources the resources
     * @param url the url
     * @throws ResourceException the resource exception
     */
    protected abstract void fillDesc(List<ResourceDto> resources, String url) throws ResourceException;

    /**
     * Gets the resource.
     * 
     * @param resourceId the resource id
     * @return the resource
     * @throws ResourceException the resource exception
     */
    public abstract ResourceDto getResource(String resourceId) throws ResourceException;

    /**
     * Gets the brochures.
     * 
     * @return the brochures
     * @throws ResourceException the resource exception
     */
    public List<ResourceDto> getBrochures() throws ResourceException {
        return getResourceItems(BROCHURES);
    }

    /**
     * Gets the features.
     * 
     * @return the features
     * @throws ResourceException the resource exception
     */
    public List<ResourceDto> getFeatures() throws ResourceException {
        return getResourceItems(FEATURES);
    }

    /**
     * Gets the logo.
     * 
     * @return the logo
     * @throws ResourceException the resource exception
     */
    public ResourceDto getLogo() throws ResourceException {
        return getResource(LOGO);
    }

    /**
     * Gets the no image.
     * 
     * @return the no image
     * @throws ResourceException the resource exception
     */
    public ResourceDto getNoImage() throws ResourceException {
        return getResource(NO_IMAGE);
    }

    /**
     * Gets the organization image.
     * 
     * @return the organization image
     * @throws ResourceException the resource exception
     */
    public ResourceDto getOrganizationImage() throws ResourceException {
        return getResource(ORGANIZATION);
    }

    /**
     * Gets the director image.
     * 
     * @return the director image
     * @throws ResourceException the resource exception
     */
    public ResourceDto getDirectorImage() throws ResourceException {
        return getResource(DIRECTOR);
    }

    /**
     * Gets the gallery.
     * 
     * @return the gallery
     * @throws ResourceException the resource exception
     */
    public List<ResourceDto> getGallery() throws ResourceException {
        return getResourceItems(GALLERY);
    }

    /**
     * Gets the gallery items.
     * 
     * @param galleryName the gallery name
     * @return the gallery items
     * @throws ResourceException the resource exception
     */
    public List<ResourceDto> getGalleryItems(String galleryName) throws ResourceException {
        return getResourceItems(GALLERY, galleryName);
    }

    /**
     * Gets the greetings.
     * 
     * @return the greetings
     * @throws ResourceException the resource exception
     */
    public List<ResourceDto> getGreetings() throws ResourceException {
        return getResourceItems(GREETINGS);
    }

    public List<ResourceDto> getPortalEmployees() throws ResourceException {
        return getResourceItems(EMPLOYEE_PORTAL);
    }

    public List<ResourceDto> getRegisteredEmployees() throws ResourceException {
        return getResourceItems(EMPLOYEE_REGISTERED);
    }

    public List<ResourceDto> getPortalStudents() throws ResourceException {
        return getResourceItems(STUDENT_PORTAL);
    }

    public List<ResourceDto> getRegisteredStudents() throws ResourceException {
        return getResourceItems(STUDENT_REGISTERED);
    }

}
