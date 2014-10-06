package com.myschool.attendance.dao;

import com.myschool.attendance.dto.AttendanceDto;
import com.myschool.attendance.dto.ReferenceAttendanceDto;
import com.myschool.attendance.dto.StudentAttendanceDto;
import com.myschool.common.exception.DaoException;

/**
 * The Interface AttendanceDao.
 */
public interface AttendanceDao {

    /**
     * Gets the reference attendance.
     *
     * @param classId the class id
     * @param year the year
     * @param month the month
     * @return the reference attendance
     * @throws DaoException the dao exception
     */
    ReferenceAttendanceDto getReferenceAttendance(int classId, int year,
            int month) throws DaoException;

    /**
     * Creates the reference attendance.
     *
     * @param referenceAttendance the reference attendance
     * @return the int
     * @throws DaoException the dao exception
     */
    int createReferenceAttendance(ReferenceAttendanceDto referenceAttendance) throws DaoException;

    /**
     * Update reference attendance.
     *
     * @param referenceAttendanceId the reference attendance id
     * @param referenceAttendance the reference attendance
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean updateReferenceAttendance(int referenceAttendanceId,
            ReferenceAttendanceDto referenceAttendance) throws DaoException;

    /**
     * Gets the student attendance.
     *
     * @param studentId the student id
     * @param year the year
     * @param month the month
     * @return the student attendance
     * @throws DaoException the dao exception
     */
    AttendanceDto getStudentAttendance(int studentId, int year, int month)
            throws DaoException;

    /**
     * Creates the student attendance.
     *
     * @param referenceAttendance the reference attendance
     * @param studentAttendance the student attendance
     * @return the int
     * @throws DaoException the dao exception
     */
    int createStudentAttendance(ReferenceAttendanceDto referenceAttendance,
            StudentAttendanceDto studentAttendance) throws DaoException;

    /**
     * Update student attendance.
     *
     * @param monthAttendanceId the month attendance id
     * @param referenceAttendance the reference attendance
     * @param studentAttendance the student attendance
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean updateStudentAttendance(int monthAttendanceId,
            ReferenceAttendanceDto referenceAttendance,
            StudentAttendanceDto studentAttendance) throws DaoException;

}
