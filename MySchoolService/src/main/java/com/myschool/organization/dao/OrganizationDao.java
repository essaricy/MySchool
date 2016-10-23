package com.myschool.organization.dao;

import com.myschool.common.exception.DaoException;
import com.myschool.organization.dto.Organization;
import com.myschool.organization.dto.OrganizationManifest;
import com.myschool.organization.dto.OrganizationPreferences;

/**
 * The Interface OrganizationDao.
 */
public interface OrganizationDao {

    /**
     * Gets the organization.
     *
     * @return the organization
     * @throws DaoException the dao exception
     */
    Organization getOrganization() throws DaoException;

    /**
     * Update.
     *
     * @param organization the organization
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(Organization organization) throws DaoException;

    /**
     * Gets the organization preferences.
     *
     * @return the organization preferences
     * @throws DaoException the dao exception
     */
    OrganizationPreferences getOrganizationPreferences() throws DaoException;

    /**
     * Update notification settings.
     *
     * @param organizationPreferences the organization preferences
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean updateNotificationSettings(OrganizationPreferences organizationPreferences) throws DaoException;

    /**
     * Update display settings.
     *
     * @param organizationPreferences the organization preferences
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean updateDisplaySettings(OrganizationPreferences organizationPreferences) throws DaoException;

    /**
     * Update self service settings.
     *
     * @param organizationPreferences the organization preferences
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean updateSelfServiceSettings(OrganizationPreferences organizationPreferences) throws DaoException;

    /**
     * Update default theme.
     *
     * @param themename the themename
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean updateDefaultTheme(String themename) throws DaoException;

    /**
     * Pin gallery.
     *
     * @param gallery the gallery
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean pinGallery(String gallery) throws DaoException;

    /**
     * Gets the organization manifest.
     *
     * @return the organization manifest
     * @throws DaoException the dao exception
     */
    OrganizationManifest getOrganizationManifest() throws DaoException;

    /**
     * Roll academic year.
     *
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean rollAcademicYear() throws DaoException;

    /**
     * Sets the academic year end process.
     *
     * @param onOff the on off
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean setAcademicYearEndProcess(boolean onOff) throws DaoException;

}
