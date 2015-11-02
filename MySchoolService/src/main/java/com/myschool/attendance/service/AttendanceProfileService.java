package com.myschool.attendance.service;

import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.attendance.dto.AttendanceProfileDto;
import com.myschool.common.exception.ServiceException;

/**
 * The Interface AttendanceProfileService.
 */
public interface AttendanceProfileService extends Servicable<AttendanceProfileDto> {

    AttendanceProfileDto getInDetail(int attendanceProfileId) throws ServiceException;

    /**
     * Gets the all.
     * 
     * @param academicYearName the academic year name
     * @return the list
     * @throws ServiceException the service exception
     */
    List<AttendanceProfileDto> getAll(String academicYearName) throws ServiceException;

    /**
     * Gets the blank.
     * 
     * @param academicYearName the academic year name
     * @return the blank
     * @throws ServiceException the service exception
     */
    AttendanceProfileDto getBlank(String academicYearName) throws ServiceException;

    AttendanceProfileDto getBlank() throws ServiceException;

}
