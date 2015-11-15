package com.myschool.application.service;

import com.myschool.application.dto.MySchoolProfileDto;
import com.myschool.application.dto.OrganizationProfileDto;
import com.myschool.application.dto.ResourceProfile;
import com.myschool.common.exception.ServiceException;

/**
 * The Interface ProfileService.
 */
public interface ProfileService {

    /**
     * Gets the organization profile.
     * 
     * @return the organization profile
     * @throws ServiceException the service exception
     */
    OrganizationProfileDto getOrganizationProfile() throws ServiceException;

    /**
     * Gets the my school profile.
     * 
     * @return the my school profile
     * @throws ServiceException the service exception
     */
    MySchoolProfileDto getMySchoolProfile() throws ServiceException;

    /**
     * Update.
     * 
     * @param organaizationProfile the organaization profile
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean update(OrganizationProfileDto organaizationProfile) throws ServiceException;

    /**
     * Update.
     * 
     * @param mySchoolProfile the my school profile
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean update(MySchoolProfileDto mySchoolProfile) throws ServiceException;

    /**
     * Gets the resource profile.
     * 
     * @return the resource profile
     * @throws ServiceException the service exception
     */
    ResourceProfile getResourceProfile() throws ServiceException;

}
