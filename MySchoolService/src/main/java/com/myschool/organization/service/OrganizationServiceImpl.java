package com.myschool.organization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.ServiceException;
import com.myschool.organization.dao.OrganizationManager;
import com.myschool.organization.dto.Organization;
import com.myschool.organization.dto.OrganizationManifest;
import com.myschool.organization.dto.OrganizationPreferences;
import com.quasar.core.exception.DataException;

/**
 * The Class OrganizationServiceImpl.
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    /** The organization manager. */
    @Autowired
    private OrganizationManager organizationManager;

    /* (non-Javadoc)
     * @see com.myschool.organization.service.OrganizationService#getOrganization()
     */
    @Override
    public Organization getOrganization() throws ServiceException {
        Organization organization = null;
        try {
            organization = organizationManager.getOrganization();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return organization;
    }

    /* (non-Javadoc)
     * @see com.myschool.organization.service.OrganizationService#update(com.myschool.organization.dto.Organization)
     */
    @Override
    public boolean update(Organization organization) throws ServiceException {
        try {
            return organizationManager.update(organization);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.organization.service.OrganizationService#getOrganizationPreferences()
     */
    @Override
    public OrganizationPreferences getOrganizationPreferences()
            throws ServiceException {
        OrganizationPreferences preferences = null;
        try {
            preferences = organizationManager.getOrganizationPreferences();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return preferences;
    }

    /* (non-Javadoc)
     * @see com.myschool.organization.service.OrganizationService#updateNotificationSettings(com.myschool.organization.dto.OrganizationPreferences)
     */
    @Override
    public boolean updateNotificationSettings(
            OrganizationPreferences organizationPreferences)
                    throws ServiceException {
        try {
            return organizationManager.updateNotificationSettings(organizationPreferences);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.organization.service.OrganizationService#updateDisplaySettings(com.myschool.organization.dto.OrganizationPreferences)
     */
    @Override
    public boolean updateDisplaySettings(
            OrganizationPreferences organizationPreferences)
                    throws ServiceException {
        try {
            return organizationManager.updateDisplaySettings(organizationPreferences);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.organization.service.OrganizationService#updateSelfServiceSettings(com.myschool.organization.dto.OrganizationPreferences)
     */
    @Override
    public boolean updateSelfServiceSettings(
            OrganizationPreferences organizationPreferences)
                    throws ServiceException {
        try {
            return organizationManager.updateSelfServiceSettings(organizationPreferences);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.organization.service.OrganizationService#updateDefaultTheme(java.lang.String)
     */
    @Override
    public boolean updateDefaultTheme(String themeName)
            throws ServiceException {
        try {
            return organizationManager.updateDefaultTheme(themeName);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.organization.service.OrganizationService#pinGallery(java.lang.String)
     */
    @Override
    public boolean pinGallery(String gallery) throws ServiceException {
        try {
            return organizationManager.updateDefaultTheme(gallery);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.organization.service.OrganizationService#getOrganizationManifest()
     */
    @Override
    public OrganizationManifest getOrganizationManifest()
            throws ServiceException {
        OrganizationManifest manifest = null;
        try {
            manifest = organizationManager.getOrganizationManifest();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return manifest;
    }

    /* (non-Javadoc)
     * @see com.myschool.organization.service.OrganizationService#rollAcademicYear()
     */
    @Override
    public boolean rollAcademicYear() throws ServiceException {
        try {
            return organizationManager.rollAcademicYear();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.organization.service.OrganizationService#setAcademicYearEndProcess(boolean)
     */
    @Override
    public boolean setAcademicYearEndProcess(boolean onOff)
            throws ServiceException {
        try {
            return organizationManager.setAcademicYearEndProcess(onOff);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

}
