package com.myschool.academic.service;

import com.myschool.academic.dto.AcademicDto;
import com.myschool.application.service.Servicable;
import com.myschool.common.exception.ServiceException;

/**
 * The Interface AcademicService.
 */
public interface AcademicService extends Servicable<AcademicDto> {

    /**
     * Gets the current academic.
     *
     * @return the current academic
     * @throws ServiceException the service exception
     */
    AcademicDto getCurrentAcademic() throws ServiceException;

    /**
     * Gets the next academic.
     *
     * @return the next academic
     * @throws ServiceException the service exception
     */
    AcademicDto getNextAcademic() throws ServiceException;

    /**
     * Gets the.
     * 
     * @param academicYearName the academic year name
     * @return the academic dto
     * @throws ServiceException the service exception
     */
    AcademicDto get(String academicYearName) throws ServiceException;

    /**
     * Update.
     * 
     * @param academicYearName the academic year name
     * @param academic the academic
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean update(String academicYearName, AcademicDto academic) throws ServiceException;

    /**
     * Delete.
     * 
     * @param academicYearName the academic year name
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean delete(String academicYearName) throws ServiceException;

}
