package com.myschool.organization.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.util.StringUtil;
import com.myschool.infra.captcha.agent.CaptchaAgent;
import com.myschool.organization.dto.Organization;
import com.myschool.organization.dto.OrganizationManifest;
import com.myschool.organization.dto.OrganizationPreferences;
import com.myschool.organization.validator.OrganizationValidator;

/**
 * The Class OrganizationManager.
 */
@Component
public class OrganizationManager {

    @Autowired
    private CaptchaAgent captchaAgent;

    /** The organization dao. */
    @Autowired
    private OrganizationDao organizationDao;

    /** The organization validator. */
    @Autowired
    private OrganizationValidator organizationValidator;

    /**
     * Gets the organization.
     *
     * @return the organization
     * @throws DataException the data exception
     */
    public Organization getOrganization() throws DataException {
        try {
            return organizationDao.getOrganization();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Update.
     *
     * @param organization the organization
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(Organization organization) throws DataException {
        boolean updated = false;
        try {
            organizationValidator.validate(organization);
            Organization existing = getOrganization();
            existing.setAddress(organization.getAddress());
            existing.setPhoneNumber(organization.getPhoneNumber());
            existing.setFaxNumber(organization.getFaxNumber());

            updated = organizationDao.update(existing);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
        return updated;
    }

    /**
     * Gets the organization preferences.
     *
     * @return the organization preferences
     * @throws DataException the data exception
     */
    public OrganizationPreferences getOrganizationPreferences() throws DataException {
        try {
            return organizationDao.getOrganizationPreferences();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Update notification settings.
     *
     * @param organizationPreferences the organization preferences
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean updateNotificationSettings(
            OrganizationPreferences organizationPreferences)
                    throws DataException {
        boolean updated = false;
        try {
            OrganizationPreferences existing = getOrganizationPreferences();
            boolean emailActive = organizationPreferences.isEmailActive();
            existing.setEmailActive(emailActive);
            if (!emailActive) {
                existing.setEmailEmployees(false);
                existing.setEmailStudents(false);
            }
            boolean smsActive = organizationPreferences.isSmsActive();
            existing.setSmsActive(smsActive);
            if (!smsActive) {
                existing.setSmsEmployees(false);
                existing.setSmsStudents(false);
            }
            updated = organizationDao.updateNotificationSettings(existing);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return updated;
    }

    /**
     * Update display settings.
     *
     * @param organizationPreferences the organization preferences
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean updateDisplaySettings(
            OrganizationPreferences organizationPreferences)
                    throws DataException {
        boolean updated = false;
        try {
            OrganizationPreferences existing = getOrganizationPreferences();
            existing.setUseMenuIcons(organizationPreferences.isUseMenuIcons());

            String defaultGallery = organizationPreferences.getDefaultGallery();
            if (!StringUtil.isNullOrBlank(defaultGallery)) {
                existing.setDefaultGallery(defaultGallery);
            }
            String defaultTheme = organizationPreferences.getDefaultTheme();
            if (!StringUtil.isNullOrBlank(defaultTheme)) {
                existing.setDefaultTheme(defaultTheme);
            }
            updated = organizationDao.updateDisplaySettings(existing);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return updated;
    }

    /**
     * Update self service settings.
     *
     * @param organizationPreferences the organization preferences
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean updateSelfServiceSettings(
            OrganizationPreferences organizationPreferences)
                    throws DataException {
        boolean updated = false;
        try {
            OrganizationPreferences existing = getOrganizationPreferences();
            existing.setUseEmployeeSelfSubmit(organizationPreferences.isUseEmployeeSelfSubmit());
            existing.setUseStudentSelfSubmit(organizationPreferences.isUseStudentSelfSubmit());

            updated = organizationDao.updateSelfServiceSettings(existing);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return updated;
    }

    /**
     * Update default theme.
     *
     * @param themeName the theme name
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean updateDefaultTheme(String themeName) throws DataException {
        boolean updated = false;
        try {
            OrganizationPreferences existing = getOrganizationPreferences();

            if (!StringUtil.isNullOrBlank(themeName)) {
                existing.setDefaultTheme(themeName);
            }
            updated = organizationDao.updateDisplaySettings(existing);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return updated;
    }

    /**
     * Pin gallery.
     *
     * @param gallery the gallery
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean pinGallery(String gallery) throws DataException {
        boolean updated = false;
        try {
            OrganizationPreferences existing = getOrganizationPreferences();

            if (!StringUtil.isNullOrBlank(gallery)) {
                existing.setDefaultGallery(gallery);
            }
            updated = organizationDao.updateDisplaySettings(existing);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return updated;
    }

    /**
     * Gets the organization manifest.
     *
     * @return the organization manifest
     * @throws DataException the data exception
     */
    public OrganizationManifest getOrganizationManifest() throws DataException {
        OrganizationManifest organizationManifest = null;
        try {
            organizationManifest = organizationDao.getOrganizationManifest();
            organizationManifest.setCaptchaKey(captchaAgent.getClientKey());
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return organizationManifest;
    }

    /**
     * Roll academic year.
     *
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean rollAcademicYear() throws DataException {
        try {
            return organizationDao.rollAcademicYear();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Sets the academic year end process.
     *
     * @param onOff the on off
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean setAcademicYearEndProcess(boolean onOff) throws DataException {
        try {
            return organizationDao.setAcademicYearEndProcess(onOff);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

}
