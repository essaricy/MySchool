package com.myschool.branch.service;

import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.branch.dto.BranchDto;
import com.myschool.common.exception.ServiceException;

/**
 * The Interface BranchService.
 */
public interface BranchService extends Servicable<BranchDto> {

    /**
     * Gets the by region.
     * 
     * @param regionId the region id
     * @return the by region
     * @throws ServiceException the service exception
     */
    List<BranchDto> getByRegion(int regionId) throws ServiceException;

}
