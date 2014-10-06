package com.myschool.branch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.branch.domain.BranchManager;
import com.myschool.branch.dto.BranchDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;

/**
 * The Class BranchServiceImpl.
 */
@Service
public class BranchServiceImpl implements BranchService {

    /** The branch manager. */
    @Autowired
    private BranchManager branchManager;

    /*
     * (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(BranchDto branchDto) throws ServiceException {
        boolean created = false;
        try {
            created = branchManager.create(branchDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return created;
    }

    /*
     * (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int branchId) throws ServiceException {
        boolean deleted = false;
        try {
            deleted = branchManager.delete(branchId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return deleted;
    }

    /*
     * (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public BranchDto get(int branchId) throws ServiceException {
        BranchDto branch = null;
        try {
            branch = branchManager.get(branchId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return branch;
    }

    /*
     * (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<BranchDto> getAll() throws ServiceException {
        List<BranchDto> branches = null;
        try {
            branches = branchManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return branches;
    }

    /*
     * (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int,
     * java.lang.Object)
     */
    @Override
    public boolean update(int branchId, BranchDto branchDto)
            throws ServiceException {
        boolean updated = false;
        try {
            updated = branchManager.update(branchId, branchDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return updated;
    }

    /* (non-Javadoc)
     * @see com.myschool.branch.service.BranchService#getByRegion(int)
     */
    @Override
    public List<BranchDto> getByRegion(int regionId) throws ServiceException {
        List<BranchDto> branches = null;
        try {
            branches = branchManager.getByRegion(regionId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return branches;
    }

}
