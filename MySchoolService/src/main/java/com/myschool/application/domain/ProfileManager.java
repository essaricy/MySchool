package com.myschool.application.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.dao.ProfileDao;
import com.myschool.application.dto.MySchoolProfileDto;
import com.myschool.application.dto.OrganizationProfileDto;
import com.myschool.application.dto.ResourceProfile;
import com.myschool.application.validator.MySchoolProfileValidator;
import com.myschool.application.validator.OrganizationProfileValidator;
import com.myschool.common.constants.CacheKeyConstants;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.infra.cache.agent.InMemoryCacheAgent;
import com.myschool.infra.media.agent.MediaServerAgent;
import com.myschool.infra.media.exception.ResourceException;

/**
 * The Class ProfileManager.
 */
@Component
public class ProfileManager {

    /** The in memory cache agent. */
    @Autowired
    private InMemoryCacheAgent inMemoryCacheAgent;

    /** The profile dao. */
    @Autowired
    private ProfileDao profileDao;

    /** The my school profile validator. */
    @Autowired
    private MySchoolProfileValidator mySchoolProfileValidator;

    /** The organization profile validator. */
    @Autowired
    private OrganizationProfileValidator organizationProfileValidator;

    /** The media server agent. */
    @Autowired
    private MediaServerAgent mediaServerAgent;

    /**
     * Gets the.
     *
     * @return the organization profile dto
     * @throws DataException the data exception
     */
    public OrganizationProfileDto getOrganizationProfile() throws DataException {
        OrganizationProfileDto organizationProfile = null;
        try {
            Object entry = inMemoryCacheAgent.getEntry(CacheKeyConstants.ORGANIZATION_PROFILE);
            if (entry == null) {
                organizationProfile = profileDao.getOrganizationProfile();
                inMemoryCacheAgent.putEntry(CacheKeyConstants.ORGANIZATION_PROFILE, organizationProfile);
            } else {
                organizationProfile = (OrganizationProfileDto) entry;
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return organizationProfile;
    }

    /**
     * Gets the myschool profile.
     * 
     * @return the myschool profile
     * @throws DataException the data exception
     */
    public MySchoolProfileDto getMyschoolProfile() throws DataException {
        MySchoolProfileDto mySchoolProfile = null;
        try {
            //Object entry = inMemoryCacheAgent.getEntry(CacheKeyConstants.MY_SCHOOL_PROFILE);
            //if (entry == null) {
                mySchoolProfile = profileDao.getMySchoolProfile();
                //inMemoryCacheAgent.putEntry(CacheKeyConstants.MY_SCHOOL_PROFILE, mySchoolProfile);
            //} else {
                //mySchoolProfile = (MySchoolProfileDto) entry;
            //}
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return mySchoolProfile;
    }

    /**
     * Update.
     *
     * @param organizationProfile the organization profile
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(OrganizationProfileDto organizationProfile) throws DataException {
        boolean updated = false;
        try {
            organizationProfileValidator.validate(organizationProfile);
            OrganizationProfileDto existingOrganizationProfile = getOrganizationProfile();
            existingOrganizationProfile.setAddress(organizationProfile.getAddress());
            existingOrganizationProfile.setPhoneNumber(organizationProfile.getPhoneNumber());
            existingOrganizationProfile.setFaxNumber(organizationProfile.getFaxNumber());
            updated = profileDao.update(existingOrganizationProfile);
            if (updated) {
                inMemoryCacheAgent.putEntry(CacheKeyConstants.ORGANIZATION_PROFILE, existingOrganizationProfile);
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
        return updated;
    }

    /**
     * Update.
     * 
     * @param mySchoolProfile the my school profile
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(MySchoolProfileDto mySchoolProfile) throws DataException {
        boolean updated = false;
        try {
            mySchoolProfileValidator.validate(mySchoolProfile);
            updated = profileDao.update(mySchoolProfile);
            if (updated) {
                inMemoryCacheAgent.putEntry(CacheKeyConstants.MY_SCHOOL_PROFILE, mySchoolProfile);
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
        return updated;
    }

    /**
     * Pin gallery.
     * 
     * @param galleryName the gallery name
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean pinGallery(String galleryName) throws DataException {
        boolean updated = false;
        try {
            updated = profileDao.pinGallery(galleryName);
            if (updated) {
                MySchoolProfileDto mySchoolProfile = (MySchoolProfileDto) inMemoryCacheAgent.getEntry(CacheKeyConstants.MY_SCHOOL_PROFILE);
                if (mySchoolProfile != null) {
                    mySchoolProfile.setPinnedGallery(galleryName);
                    inMemoryCacheAgent.putEntry(CacheKeyConstants.MY_SCHOOL_PROFILE, mySchoolProfile);
                }
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return updated;
    }

    /**
     * Gets the resource profile.
     * 
     * @return the resource profile
     * @throws DataException the data exception
     */
    public ResourceProfile getResourceProfile() throws DataException {
        ResourceProfile resourceProfile = new ResourceProfile();
        try {
            resourceProfile.setBrochures(mediaServerAgent.getResource(MediaServerAgent.BROCHURES));
            resourceProfile.setFeatures(mediaServerAgent.getResource(MediaServerAgent.FEATURES));
            resourceProfile.setLogo(mediaServerAgent.getResource(MediaServerAgent.LOGO));
            resourceProfile.setNoImage(mediaServerAgent.getResource(MediaServerAgent.NO_IMAGE));
            resourceProfile.setDirector(mediaServerAgent.getResource(MediaServerAgent.DIRECTOR));
            resourceProfile.setOrganization(mediaServerAgent.getResource(MediaServerAgent.ORGANIZATION));
            resourceProfile.setGallery(mediaServerAgent.getResource(MediaServerAgent.GALLERY));
            resourceProfile.setEmployeePortal(mediaServerAgent.getResource(MediaServerAgent.EMPLOYEE_PORTAL));
            resourceProfile.setEmployeeRegistered(mediaServerAgent.getResource(MediaServerAgent.EMPLOYEE_REGISTERED));
            resourceProfile.setStudentPortal(mediaServerAgent.getResource(MediaServerAgent.STUDENT_PORTAL));
            resourceProfile.setStudentRegistered(mediaServerAgent.getResource(MediaServerAgent.STUDENT_REGISTERED));
            resourceProfile.setGreetings(mediaServerAgent.getResource(MediaServerAgent.GREETINGS));
            resourceProfile.setProduct(mediaServerAgent.getResource(MediaServerAgent.PRODUCT));
        } catch (ResourceException resourceException) {
            throw new DataException(resourceException.getMessage(), resourceException);
        }
        System.out.println("resourceProfile " + resourceProfile);
        return resourceProfile;
    }

}
