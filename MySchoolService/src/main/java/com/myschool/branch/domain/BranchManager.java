package com.myschool.branch.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.branch.dao.BranchDao;
import com.myschool.branch.dto.BranchDto;
import com.myschool.common.exception.DaoException;
import com.quasar.core.exception.DataException;
import com.quasar.core.exception.InsufficientInputException;
import com.quasar.core.exception.InvalidDataException;

/**
 * The Class BranchManagerImpl.
 */
@Component
public class BranchManager {

    /** The branch dao. */
    @Autowired
    private BranchDao branchDao;

    /**
     * Creates the.
     *
     * @param branch the branch dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(BranchDto branch) throws DataException {
        boolean created = false;
        try {
            if (branch == null) {
                throw new InsufficientInputException("branchDto is null");
            }
            String branchCode = branch.getBranchCode();
            if (branchCode == null || branchCode.trim().length() == 0) {
                throw new InsufficientInputException("branch code is a required value.");
            }
            String description = branch.getDescription();
            if (description == null || description.trim().length() == 0) {
                throw new InsufficientInputException("description is a required value.");
            }
            String address = branch.getAddress();
            if (address == null || address.trim().length() == 0) {
                throw new InsufficientInputException("branch address is a required value.");
            }
            // Email ID is not mandatory
            created = (branchDao.create(branch) > 0)? true: false;
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }

    /**
     * Delete.
     *
     * @param branchId the branch id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int branchId) throws DataException {
        boolean deleted = false;
        try {
            if (branchId <= 0) {
                throw new InvalidDataException("Invalid Branch ID.");
            }
            deleted = branchDao.delete(branchId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return deleted;
    }

    /**
     * Gets the.
     *
     * @param branchId the branch id
     * @return the branch dto
     * @throws DataException the data exception
     */
    public BranchDto get(int branchId) throws DataException {
        BranchDto branch = null;
        try {
            if (branchId <= 0) {
                throw new InvalidDataException("Invalid Branch ID.");
            }
            branch = branchDao.get(branchId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return branch;
    }

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DataException the data exception
     */
    public List<BranchDto> getAll() throws DataException {
        List<BranchDto> branches = null;
        try {
            branches = branchDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return branches;
    }

    /**
     * Update.
     *
     * @param branchId the branch id
     * @param branchDto the branch dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int branchId, BranchDto branchDto) throws DataException {
        boolean created = false;
        try {
            if (branchDto == null) {
                throw new InsufficientInputException("branchDto is null");
            }
            if (branchId <= 0) {
                throw new InvalidDataException("Invalid Branch ID.");
            }
            String branchCode = branchDto.getBranchCode();
            if (branchCode == null || branchCode.trim().length() == 0) {
                throw new InsufficientInputException("branch code is a required value.");
            }
            String description = branchDto.getDescription();
            if (description == null || description.trim().length() == 0) {
                throw new InsufficientInputException("description is a required value.");
            }
            String address = branchDto.getAddress();
            if (address == null || address.trim().length() == 0) {
                throw new InsufficientInputException("branch address is a required value.");
            }
            // Email ID is not mandatory
            created = branchDao.updateBranch(branchId, branchDto);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }

    /**
     * Gets the all.
     * 
     * @param branchCriteria the branch criteria
     * @return the all
     * @throws DataException the data exception
     */
    public List<BranchDto> getAll(BranchDto branchCriteria) throws DataException {
        List<BranchDto> branches = null;
        try {
            branches = branchDao.getAll(branchCriteria);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return branches;
    }

    /**
     * Gets the by region.
     * 
     * @param regionId the region id
     * @return the by region
     * @throws DataException the data exception
     */
    public List<BranchDto> getByRegion(int regionId) throws DataException {
        List<BranchDto> branches = null;
        try {
            branches = branchDao.getByRegion(regionId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return branches;
    }
}
