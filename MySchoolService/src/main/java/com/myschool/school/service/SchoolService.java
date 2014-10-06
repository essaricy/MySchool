package com.myschool.school.service;

import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.common.exception.ServiceException;
import com.myschool.school.dto.SchoolDto;

public interface SchoolService extends Servicable<SchoolDto> {

    /**
     * Gets the by branch.
     *
     * @param branchId the branch id
     * @return the by branch
     * @throws ServiceException the service exception
     */
    List<SchoolDto> getByBranch(int branchId) throws ServiceException;

}
