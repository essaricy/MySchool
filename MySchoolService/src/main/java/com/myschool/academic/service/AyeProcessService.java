package com.myschool.academic.service;

import java.util.List;

import com.myschool.academic.dto.AcademicYearClosureDto;
import com.myschool.academic.dto.AyeProcessCriteriaDto;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.ServiceException;

/**
 * The Interface AyeProcessService.
 */
public interface AyeProcessService {

    /**
     * Gets the academic year closure.
     * 
     * @return the academic year closure
     * @throws ServiceException the service exception
     */
    AcademicYearClosureDto getAcademicYearClosure() throws ServiceException;

    /**
     * Initiate academic year closure.
     * 
     * @param ayeProcessCriteria the aye process criteria
     * @return the list
     * @throws ServiceException the service exception
     */
    List<ResultDto> initiateAcademicYearClosure(AyeProcessCriteriaDto ayeProcessCriteria) throws ServiceException;


}
