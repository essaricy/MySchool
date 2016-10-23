package com.myschool.organization.service;

import com.myschool.common.exception.ServiceException;
import com.myschool.organization.dto.Organization;
import com.myschool.organization.dto.OrganizationManifest;
import com.myschool.organization.dto.OrganizationPreferences;

/**
 * The Interface OrganizationService.
 */
public interface OrganizationService {

    /**
     * Gets the organization.
     *
     * @return the organization
     * @throws ServiceException the service exception
     */
    Organization getOrganization() throws ServiceException;

    /**
     * Update.
     *
     * @param organization the organization
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean update(Organization organization) throws ServiceException;

    /**
     * Gets the organization preferences.
     *
     * @return the organization preferences
     * @throws ServiceException the service exception
     */
    OrganizationPreferences getOrganizationPreferences() throws ServiceException;

    /**
     * Update notification settings.
     *
     * @param organizationPreferences the organization preferences
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean updateNotificationSettings(OrganizationPreferences organizationPreferences) throws ServiceException;

    /**
     * Update display settings.
     *
     * @param organizationPreferences the organization preferences
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean updateDisplaySettings(OrganizationPreferences organizationPreferences) throws ServiceException;

    /**
     * Update self service settings.
     *
     * @param organizationPreferences the organization preferences
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean updateSelfServiceSettings(OrganizationPreferences organizationPreferences) throws ServiceException;

    /**
     * Update default theme.
     *
     * @param themeName the theme name
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean updateDefaultTheme(String themeName) throws ServiceException;

    /**
     * Pin gallery.
     *
     * @param gallery the gallery
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean pinGallery(String gallery) throws ServiceException;

    /**
     * Gets the organization manifest.
     *
     * @return the organization manifest
     * @throws ServiceException the service exception
     */
    OrganizationManifest getOrganizationManifest() throws ServiceException;

    /**
     * Roll academic year.
     *
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean rollAcademicYear() throws ServiceException;

    /**
     * Sets the academic year end process.
     *
     * @param onOff the on off
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean setAcademicYearEndProcess(boolean onOff) throws ServiceException;

}
