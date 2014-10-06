package com.myschool.attendance.dao;

import java.util.List;

import com.myschool.attendance.dto.AttendanceProfileDto;
import com.myschool.attendance.dto.MonthAttendance;
import com.myschool.common.exception.DaoException;

/**
 * The Interface AttendanceProfileDao.
 */
public interface AttendanceProfileDao {

    /**
     * Gets the.
     * 
     * @param attendanceProfileId the attendance profile id
     * @return the attendance profile dto
     * @throws DaoException the dao exception
     */
    AttendanceProfileDto get(int attendanceProfileId) throws DaoException;

    /**
     * Gets the all.
     * 
     * @return the all
     * @throws DaoException the dao exception
     */
    List<AttendanceProfileDto> getAll() throws DaoException;

    /**
     * Creates the.
     * 
     * @param attendanceProfile the attendance profile
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(AttendanceProfileDto attendanceProfile) throws DaoException;

    /**
     * Delete.
     * 
     * @param attendanceProfileId the attendance profile id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(int attendanceProfileId) throws DaoException;

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
     * Gets the profile attendance.
     * 
     * @param attendanceProfileId the attendance profile id
     * @return the profile attendance
     * @throws DaoException the dao exception
     */
    List<MonthAttendance> getProfileAttendance(int attendanceProfileId) throws DaoException;

    /**
     * Creates the.
     * 
     * @param attendanceProfileId the attendance profile id
     * @param yearAttendance the year attendance
     * @throws DaoException the dao exception
     */
    void create(int attendanceProfileId, List<MonthAttendance> yearAttendance) throws DaoException;

    /**
     * Creates the.
     * 
     * @param attendanceProfileId the attendance profile id
     * @param monthAttendance the month attendance
     * @throws DaoException the dao exception
     */
    void create(int attendanceProfileId, MonthAttendance monthAttendance) throws DaoException;

    /**
     * Update.
     * 
     * @param attendanceMonthId the attendance month id
     * @param monthAttendance the month attendance
     * @throws DaoException the dao exception
     */
    void update(int attendanceMonthId, MonthAttendance monthAttendance) throws DaoException;

    /**
     * Gets the.
     * 
     * @param profileName the profile name
     * @return the attendance profile dto
     * @throws DaoException the dao exception
     */
    AttendanceProfileDto get(String profileName) throws DaoException;

    List<AttendanceProfileDto> getAllExcluding(AttendanceProfileDto attendanceProfile) throws DaoException;

}
