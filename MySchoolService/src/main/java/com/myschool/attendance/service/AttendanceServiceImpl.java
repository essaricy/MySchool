package com.myschool.attendance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.attendance.domain.AttendanceManager;
import com.myschool.attendance.dto.AttendanceCriteria;
import com.myschool.attendance.dto.AttendanceDto;
import com.myschool.attendance.dto.MonthAttendance;
import com.myschool.attendance.dto.ReferenceAttendanceDto;
import com.myschool.attendance.dto.StudentAttendanceDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;

/**
 * The Class AttendanceServiceImpl.
 */
@Component
public class AttendanceServiceImpl implements AttendanceService {

    /** The attendance manager. */
    @Autowired
    private AttendanceManager attendanceManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(AttendanceDto dto) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<AttendanceDto> getAll() throws ServiceException {
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public AttendanceDto get(int id) throws ServiceException {
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int id, AttendanceDto dto) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int id) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.AttendanceService#getReferenceAttendance(com.myschool.base.common.dto.AttendanceCriteria)
     */
    @Override
    public MonthAttendance getReferenceAttendance(AttendanceCriteria attendanceCriteria) throws ServiceException {
        try {
            return attendanceManager.getReferenceAttendance(attendanceCriteria);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.AttendanceService#getStudentAttendances(int, com.myschool.base.common.dto.AttendanceCriteria)
     */
    @Override
    public List<StudentAttendanceDto> getStudentAttendances(int classId,
            AttendanceCriteria attendanceCriteria) throws ServiceException {
        try {
            return attendanceManager.getStudentAttendances(classId, attendanceCriteria);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.AttendanceService#update(com.myschool.base.common.dto.ReferenceAttendanceDto, java.util.List)
     */
    @Override
    public boolean update(ReferenceAttendanceDto referenceAttendance,
            List<StudentAttendanceDto> studentsAttendance)
            throws ServiceException {
        try {
            return attendanceManager.update(referenceAttendance, studentsAttendance);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

}
