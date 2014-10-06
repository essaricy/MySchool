package com.myschool.branch.service;

import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.branch.dto.RegionDto;
import com.myschool.common.exception.ServiceException;

/**
 * The Interface RegionService.
 */
public interface RegionService extends Servicable<RegionDto> {

    /**
     * Gets the by state.
     * 
     * @param stateId the state id
     * @return the by state
     * @throws ServiceException the service exception
     */
    List<RegionDto> getByState(int stateId) throws ServiceException;

}
