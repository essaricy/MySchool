package com.myschool.attendance.service;

import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.attendance.dto.AttendanceCriteria;
import com.myschool.attendance.dto.AttendanceDto;
import com.myschool.attendance.dto.MonthAttendance;
import com.myschool.attendance.dto.ReferenceAttendanceDto;
import com.myschool.attendance.dto.StudentAttendanceDto;
import com.myschool.common.exception.ServiceException;

/**
 * The Interface AttendanceService.
 */
public interface AttendanceService extends Servicable<AttendanceDto> {

    /**
     * Gets the reference attendance.
     *
     * @param attendanceCriteria the attendance criteria
     * @return the reference attendance
     * @throws ServiceException the service exception
     */
    MonthAttendance getReferenceAttendance(AttendanceCriteria attendanceCriteria)
            throws ServiceException;

    /**
     * Gets the student attendances.
     *
     * @param classId the class id
     * @param attendanceCriteria the attendance criteria
     * @return the student attendances
     * @throws ServiceException the service exception
     */
    List<StudentAttendanceDto> getStudentAttendances(int classId,
            AttendanceCriteria attendanceCriteria) throws ServiceException;

    /**
     * Update.
     *
     * @param referenceAttendance the reference attendance
     * @param studentsAttendance the students attendance
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean update(ReferenceAttendanceDto referenceAttendance,
            List<StudentAttendanceDto> studentsAttendance)
            throws ServiceException;

}
