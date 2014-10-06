package com.myschool.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.application.domain.ProfileManager;
import com.myschool.application.dto.MySchoolProfileDto;
import com.myschool.application.dto.OrganizationProfileDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;

/**
 * The Class ProfileServiceImpl.
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    /** The profile manager. */
    @Autowired
    private ProfileManager profileManager;

    /* (non-Javadoc)
     * @see com.myschool.application.service.ProfileService#getOrganizationProfile()
     */
    @Override
    public OrganizationProfileDto getOrganizationProfile() throws ServiceException {
        OrganizationProfileDto organizationProfile = null;
        try {
            organizationProfile = profileManager.getOrganizationProfile();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return organizationProfile;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.ProfileService#getMySchoolProfile()
     */
    @Override
    public MySchoolProfileDto getMySchoolProfile() throws ServiceException {
        MySchoolProfileDto mySchoolProfile = null;
        try {
            mySchoolProfile = profileManager.getMyschoolProfile();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return mySchoolProfile;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.ProfileService#update(com.myschool.common.dto.OrganizationProfileDto)
     */
    @Override
    public boolean update(OrganizationProfileDto organaizationProfile) throws ServiceException {
        boolean updated = false;
        try {
            updated = profileManager.update(organaizationProfile);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return updated;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.ProfileService#update(com.myschool.application.dto.MySchoolProfileDto)
     */
    @Override
    public boolean update(MySchoolProfileDto mySchoolProfile) throws ServiceException {
        boolean updated = false;
        try {
            updated = profileManager.update(mySchoolProfile);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return updated;
    }

}
