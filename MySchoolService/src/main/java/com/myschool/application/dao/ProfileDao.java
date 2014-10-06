package com.myschool.application.dao;

import com.myschool.application.dto.MySchoolProfileDto;
import com.myschool.application.dto.OrganizationProfileDto;
import com.myschool.common.exception.DaoException;

/**
 * The Interface ProfileDao.
 */
public interface ProfileDao {

    /**
     * Gets the organization profile.
     * 
     * @return the organization profile
     * @throws DaoException the dao exception
     */
    OrganizationProfileDto getOrganizationProfile() throws DaoException;

    /**
     * Gets the my school profile.
     * 
     * @return the my school profile
     * @throws DaoException the dao exception
     */
    MySchoolProfileDto getMySchoolProfile() throws DaoException;

    /**
     * Update.
     * 
     * @param organizationProfile the organization profile
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(OrganizationProfileDto organizationProfile) throws DaoException;

    /**
     * Update.
     * 
     * @param mySchoolProfileDto the my school profile dto
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(MySchoolProfileDto mySchoolProfileDto) throws DaoException;

    /**
     * Update aye progress.
     * 
     * @param ayeProgressStatus the aye progress status
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean updateAyeProgress(String ayeProgressStatus) throws DaoException;

}
