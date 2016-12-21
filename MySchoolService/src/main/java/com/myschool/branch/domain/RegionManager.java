package com.myschool.branch.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.branch.dao.RegionDao;
import com.myschool.branch.dto.RegionDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;

/**
 * The Class RegionManager.
 */
@Component
public class RegionManager {

    /** The region dao. */
    @Autowired
    private RegionDao regionDao;

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DataException the data exception
     */
    public List<RegionDto> getAll() throws DataException {
        List<RegionDto> regions = null;
        try {
            regions = regionDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return regions;
    }

    /**
     * Gets the.
     * 
     * @param regionId the region id
     * @return the region dto
     * @throws DataException the data exception
     */
    public RegionDto get(int regionId) throws DataException {
        RegionDto region = null;
        try {
            region = regionDao.get(regionId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return region;
    }

}
