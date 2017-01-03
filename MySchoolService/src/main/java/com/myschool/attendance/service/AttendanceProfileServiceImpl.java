package com.myschool.attendance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.attendance.domain.AttendanceProfileManager;
import com.myschool.attendance.dto.AttendanceProfileDto;
import com.myschool.common.exception.ServiceException;
import com.quasar.core.exception.DataException;

/**
 * The Class AttendanceProfileServiceImpl.
 */
@Service
public class AttendanceProfileServiceImpl implements AttendanceProfileService {

    /** The attendance profile manager. */
    @Autowired
    private AttendanceProfileManager attendanceProfileManager;

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#getAll()
     */
    @Override
    public List<AttendanceProfileDto> getAll() throws ServiceException {
        try {
            return attendanceProfileManager.getAll();
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(), dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.service.AttendanceProfileService#getAll(java.lang.String)
     */
    @Override
    public List<AttendanceProfileDto> getAll(String academicYearName) throws ServiceException {
        try {
            return attendanceProfileManager.getAll(academicYearName);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#get(int)
     */
    @Override
    public AttendanceProfileDto get(int attendanceProfileId) throws ServiceException {
        try {
            return attendanceProfileManager.get(attendanceProfileId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.service.AttendanceProfileService#getInDetail(int)
     */
    @Override
    public AttendanceProfileDto getInDetail(int attendanceProfileId) throws ServiceException {
        try {
            return attendanceProfileManager.getInDetail(attendanceProfileId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.service.AttendanceProfileService#getBlank(java.lang.String)
     */
    @Override
    public AttendanceProfileDto getBlank(String academicYearName)
            throws ServiceException {
        try {
            return attendanceProfileManager.getBlank(academicYearName);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.service.AttendanceProfileService#getBlank()
     */
    public AttendanceProfileDto getBlank() throws ServiceException {
        try {
            return attendanceProfileManager.getBlank();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(AttendanceProfileDto attendanceProfile) throws ServiceException {
        try {
            return attendanceProfileManager.create(attendanceProfile);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(), dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int attendanceProfileId, AttendanceProfileDto attendanceProfile)
            throws ServiceException {
        try {
            return attendanceProfileManager.update(attendanceProfileId, attendanceProfile);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(), dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#delete(int)
     */
    @Override
    public boolean delete(int attendanceProfileId) throws ServiceException {
        try {
            return attendanceProfileManager.delete(attendanceProfileId);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

}
