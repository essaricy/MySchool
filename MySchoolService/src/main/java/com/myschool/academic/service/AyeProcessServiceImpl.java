package com.myschool.academic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.academic.domain.AyeProcessManager;
import com.myschool.academic.dto.AcademicYearClosureDto;
import com.myschool.academic.dto.AyeProcessCriteriaDto;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;

/**
 * The Class AyeProcessServiceImpl.
 */
@Service
public class AyeProcessServiceImpl implements AyeProcessService {

    /** The aye process manager. */
    @Autowired
    private AyeProcessManager ayeProcessManager;

    /* (non-Javadoc)
     * @see com.myschool.academic.service.AyeProcessService#getAcademicYearClosure()
     */
    @Override
    public AcademicYearClosureDto getAcademicYearClosure() throws ServiceException {
        try {
            return ayeProcessManager.getAcademicYearClosure();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.academic.service.AyeProcessService#initiateAcademicYearClosure(com.myschool.academic.dto.AyeProcessCriteriaDto)
     */
    @Override
    public List<ResultDto> initiateAcademicYearClosure(
            AyeProcessCriteriaDto ayeProcessCriteria) throws ServiceException {
        try {
            return ayeProcessManager.initiateAcademicYearClosure(ayeProcessCriteria);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

}
