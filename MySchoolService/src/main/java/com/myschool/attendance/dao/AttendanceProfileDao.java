package com.myschool.attendance.dao;

import java.util.List;

import com.myschool.attendance.dto.AttendanceMonth;
import com.myschool.attendance.dto.AttendanceProfileDto;
import com.myschool.common.exception.DaoException;

/**
 * The Interface AttendanceProfileDao.
 */
public interface AttendanceProfileDao {

    /**
     * Gets the all.
     * 
     * @return the all
     * @throws DaoException the dao exception
     */
    List<AttendanceProfileDto> getAll() throws DaoException;

    /**
     * Gets the all.
     * 
     * @param academicYearName the academic year name
     * @return the all
     * @throws DaoException the dao exception
     */
    List<AttendanceProfileDto> getAll(String academicYearName) throws DaoException;

    /**
     * Gets the.
     * 
     * @param attendanceProfileId the attendance profile id
     * @return the attendance profile dto
     * @throws DaoException the dao exception
     */
    AttendanceProfileDto get(int attendanceProfileId) throws DaoException;

    /**
     * Gets the.
     * 
     * @param attendanceProfileName the attendance profile name
     * @return the attendance profile dto
     * @throws DaoException the dao exception
     */
    AttendanceProfileDto get(String attendanceProfileName) throws DaoException;

    /**
     * Gets the attendance months.
     * 
     * @param attendanceProfileId the attendance profile id
     * @return the attendance months
     * @throws DaoException the dao exception
     */
    List<AttendanceMonth> getAttendanceMonths(int attendanceProfileId) throws DaoException;

    /**
     * Creates the.
     * 
     * @param attendanceProfile the attendance profile
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(AttendanceProfileDto attendanceProfile) throws DaoException;

    /**
     * Creates the.
     * 
     * @param attendanceProfileId the attendance profile id
     * @param yearAttendance the year attendance
     * @throws DaoException the dao exception
     */
    void create(int attendanceProfileId, List<AttendanceMonth> yearAttendance) throws DaoException;

    /**
     * Creates the.
     * 
     * @param attendanceProfileId the attendance profile id
     * @param monthAttendance the month attendance
     * @throws DaoException the dao exception
     */
    void create(int attendanceProfileId, AttendanceMonth monthAttendance) throws DaoException;

    /**
     * Update.
     * 
     * @param attendanceProfileId the attendance profile id
     * @param attendanceProfile the attendance profile
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int attendanceProfileId, AttendanceProfileDto attendanceProfile) throws DaoException;

    /**
     * Update.
     * 
     * @param attendanceMonthId the attendance month id
     * @param monthAttendance the month attendance
     * @throws DaoException the dao exception
     */
    void update(int attendanceMonthId, AttendanceMonth monthAttendance) throws DaoException;

    /**
     * Delete.
     * 
     * @param attendanceProfileId the attendance profile id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(int attendanceProfileId) throws DaoException;

}
