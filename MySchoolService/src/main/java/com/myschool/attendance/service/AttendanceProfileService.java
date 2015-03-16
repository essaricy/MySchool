package com.myschool.attendance.service;

import com.myschool.application.service.Servicable;
import com.myschool.attendance.dto.AttendanceProfileDto;
import com.myschool.common.exception.ServiceException;

/**
 * The Interface AttendanceProfileService.
 */
public interface AttendanceProfileService extends Servicable<AttendanceProfileDto> {

    /**
     * Gets the.
     * 
     * @param attendanceProfileId the attendance profile id
     * @param academicYearName the academic year name
     * @return the attendance profile dto
     * @throws ServiceException the service exception
     */
    AttendanceProfileDto get(int attendanceProfileId,
            String academicYearName) throws ServiceException;

}
