package com.myschool.branch.dao;

import java.util.List;

import com.myschool.branch.dto.BranchDto;
import com.myschool.common.exception.DaoException;

/**
 * The Interface BranchDao.
 */
public interface BranchDao {

    /**
     * Creates the.
     * 
     * @param branchDto the branch dto
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(BranchDto branchDto) throws DaoException;

    /**
     * Gets the all.
     * 
     * @return the all
     * @throws DaoException the dao exception
     */
    List<BranchDto> getAll() throws DaoException;

    /**
     * Gets the all.
     * 
     * @param branchCriteria the branch criteria
     * @return the all
     * @throws DaoException the dao exception
     */
    List<BranchDto> getAll(BranchDto branchCriteria) throws DaoException;

    /**
     * Gets the.
     *
     * @param branchId the branch id
     * @return the branch dto
     * @throws DaoException the dao exception
     */
    BranchDto get(int branchId) throws DaoException;

    /**
     * Update branch.
     *
     * @param branchId the branch id
     * @param branchDto the branch dto
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean updateBranch(int branchId, BranchDto branchDto) throws DaoException;

    /**
     * Delete.
     * 
     * @param branchId the branch id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(int branchId) throws DaoException;

    /**
     * Gets the.
     *
     * @param branchCode the branch code
     * @return the branch dto
     * @throws DaoException the dao exception
     */
    BranchDto get(String branchCode) throws DaoException;

    /**
     * Gets the by region.
     * 
     * @param regionId the region id
     * @return the by region
     * @throws DaoException the dao exception
     */
    List<BranchDto> getByRegion(int regionId) throws DaoException;

}
