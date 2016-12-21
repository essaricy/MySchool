package com.myschool.branch.dao;

import java.util.List;

import com.myschool.branch.dto.RegionDto;
import com.myschool.common.exception.DaoException;

/**
 * The Interface RegionDao.
 */
public interface RegionDao {

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DaoException the dao exception
     */
    public List<RegionDto> getAll() throws DaoException;

    /**
     * Gets the.
     *
     * @param regionId the region id
     * @return the region dto
     * @throws DaoException the dao exception
     */
    public RegionDto get(int regionId) throws DaoException;

    /**
     * Update region.
     *
     * @param regionId the region id
     * @param regionDto the region dto
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public boolean updateRegion(int regionId, RegionDto regionDto) throws DaoException;

    /**
     * Delete region.
     *
     * @param regionId the region id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public boolean deleteRegion(int regionId) throws DaoException;

    /**
     * Gets the.
     *
     * @param regionName the region name
     * @return the region dto
     * @throws DaoException the dao exception
     */
    public RegionDto get(String regionName) throws DaoException;

    /**
     * Creates the.
     *
     * @param regionName the region name
     * @return the int
     * @throws DaoException the dao exception
     */
    public int create(String regionName) throws DaoException;

}
