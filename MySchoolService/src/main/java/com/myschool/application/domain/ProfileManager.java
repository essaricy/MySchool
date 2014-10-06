package com.myschool.application.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.dao.ProfileDao;
import com.myschool.application.dto.MySchoolProfileDto;
import com.myschool.application.dto.OrganizationProfileDto;
import com.myschool.application.validator.MySchoolProfileValidator;
import com.myschool.application.validator.OrganizationProfileValidator;
import com.myschool.common.constants.CacheKeyConstants;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.infra.application.ApplicationLoader;
import com.myschool.infra.cache.agent.InMemoryCacheAgent;

/**
 * The Class ProfileManager.
 */
@Component
public class ProfileManager {

    /** The in memory cache agent. */
    @Autowired
    private InMemoryCacheAgent inMemoryCacheAgent;

    /** The agents. */
    @Autowired
    private ApplicationLoader applicationLoader;

    /** The profile dao. */
    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private MySchoolProfileValidator mySchoolProfileValidator;

    @Autowired
    private OrganizationProfileValidator organizationProfileValidator;

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

}
