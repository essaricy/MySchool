package com.myschool.attendance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.attendance.assembler.AttendanceProfileDataAssembler;
import com.myschool.attendance.dto.AttendanceCode;
import com.myschool.attendance.dto.AttendanceDay;
import com.myschool.attendance.dto.AttendanceMonth;
import com.myschool.attendance.dto.AttendanceProfileDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class AttendanceProfileDaoImpl.
 */
@Repository
public class AttendanceProfileDaoImpl implements AttendanceProfileDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceProfileDao#getAll()
     */
    @Override
    public List<AttendanceProfileDto> getAll() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<AttendanceProfileDto> attendanceProfiles = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AttendanceProfileDaoSql.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (attendanceProfiles == null) {
                    attendanceProfiles = new ArrayList<AttendanceProfileDto>();
                }
                attendanceProfiles.add(AttendanceProfileDataAssembler.create(resultSet));
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(), connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement, resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return attendanceProfiles;
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceProfileDao#getAll(java.lang.String)
     */
    @Override
    public List<AttendanceProfileDto> getAll(String academicYearName) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<AttendanceProfileDto> attendanceProfiles = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AttendanceProfileDaoSql.SELECT_BY_ACADEMIC);
            preparedStatement.setString(1, academicYearName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (attendanceProfiles == null) {
                    attendanceProfiles = new ArrayList<AttendanceProfileDto>();
                }
                attendanceProfiles.add(AttendanceProfileDataAssembler.create(resultSet));
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement, resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return attendanceProfiles;
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceProfileDao#get(int)
     */
    @Override
    public AttendanceProfileDto get(int attendanceProfileId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        AttendanceProfileDto attendanceProfile = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AttendanceProfileDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, attendanceProfileId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                attendanceProfile = AttendanceProfileDataAssembler.create(resultSet);
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement, resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return attendanceProfile;
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceProfileDao#get(java.lang.String)
     */
    @Override
    public AttendanceProfileDto get(String attendanceProfileName) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        AttendanceProfileDto attendanceProfile = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AttendanceProfileDaoSql.SELECT_BY_NAME);
            preparedStatement.setString(1, attendanceProfileName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                attendanceProfile = AttendanceProfileDataAssembler.create(resultSet);
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement, resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return attendanceProfile;
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceProfileDao#getAttendanceMonths(int)
     */
    @Override
    public List<AttendanceMonth> getAttendanceMonths(int attendanceProfileId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<AttendanceMonth> attendanceProfiles = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AttendanceMonthDaoSql.SELECT_BY_PROFILE_ID);
            preparedStatement.setInt(1, attendanceProfileId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (attendanceProfiles == null) {
                    attendanceProfiles = new ArrayList<AttendanceMonth>();
                }
                attendanceProfiles.add(AttendanceProfileDataAssembler.createAttendanceMonth(resultSet));
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement, resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return attendanceProfiles;
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceProfileDao#create(com.myschool.attendance.dto.AttendanceProfileDto)
     */
    @Override
    public int create(AttendanceProfileDto attendanceProfile)
            throws DaoException {
        int attendanceProfileId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            attendanceProfileId = databaseAgent.getNextId("ATTENDANCE_PROFILE", "PROFILE_ID");
            preparedStatement = connection.prepareStatement(AttendanceProfileDaoSql.INSERT);
            preparedStatement.setInt(1, attendanceProfileId);
            preparedStatement.setString(2, attendanceProfile.getProfileName().trim());
            preparedStatement.setString(3, attendanceProfile.getEffectiveAcademic().getAcademicYearName());

            attendanceProfileId = (preparedStatement.executeUpdate() > 0) ? attendanceProfileId : 0;
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return attendanceProfileId;
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceProfileDao#create(int, java.util.List)
     */
    @Override
    public void create(int attendanceProfileId, List<AttendanceMonth> monthAttendances)
            throws DaoException {
        int attendanceMonthId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            if (monthAttendances != null && !monthAttendances.isEmpty()) {
                connection = databaseAgent.getConnection();
                preparedStatement = connection.prepareStatement(AttendanceMonthDaoSql.INSERT);
                for (AttendanceMonth monthAttendance : monthAttendances) {
                    int index = 0;
                    if (attendanceMonthId == 0) {
                        attendanceMonthId = databaseAgent.getNextId("ATTENDANCE_MONTH", "ATTENDANCE_MONTH_ID");
                    } else {
                        attendanceMonthId++;
                    }
                    preparedStatement.setInt(++index, attendanceMonthId);
                    preparedStatement.setInt(++index, attendanceProfileId);
                    preparedStatement.setInt(++index, monthAttendance.getYear());
                    preparedStatement.setInt(++index, monthAttendance.getMonthNumber());
                    List<AttendanceDay> attendanceDays = monthAttendance.getAttendanceDays();
                    for (AttendanceDay attendanceDay : attendanceDays) {
                        if (attendanceDay == null || attendanceDay.getReference() == null) {
                            preparedStatement.setString(++index, null);
                        } else {
                            preparedStatement.setString(++index, attendanceDay.getReference().getCode());
                        }
                    }
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceProfileDao#create(int, com.myschool.attendance.dto.MonthAttendance)
     */
    @Override
    public void create(int attendanceProfileId, AttendanceMonth monthAttendance)
            throws DaoException {
        int index = 0;
        int attendanceMonthId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AttendanceMonthDaoSql.INSERT);
            attendanceMonthId = databaseAgent.getNextId("ATTENDANCE_MONTH", "ATTENDANCE_MONTH_ID");

            preparedStatement.setInt(++index, attendanceMonthId);
            preparedStatement.setInt(++index, attendanceProfileId);
            preparedStatement.setInt(++index, monthAttendance.getYear());
            preparedStatement.setInt(++index, monthAttendance.getMonthNumber());
            List<AttendanceDay> attendanceDays = monthAttendance.getAttendanceDays();
            for (AttendanceDay attendanceDay : attendanceDays) {
                AttendanceCode attendanceCode = attendanceDay.getReference();
                if (attendanceCode == null) {
                    preparedStatement.setString(++index, null);
                } else {
                    preparedStatement.setString(++index, attendanceCode.getCode());
                }
            }
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceProfileDao#update(int, com.myschool.attendance.dto.AttendanceProfileDto)
     */
    @Override
    public boolean update(int attendanceProfileId,
            AttendanceProfileDto attendanceProfile) throws DaoException {
        boolean attendanceProfileUpdated = false;
        int index = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AttendanceProfileDaoSql.UPDATE);
            preparedStatement.setString(++index, attendanceProfile.getProfileName().trim());
            preparedStatement.setString(++index, attendanceProfile.getEffectiveAcademic().getAcademicYearName());
            preparedStatement.setInt(++index, attendanceProfileId);

            attendanceProfileUpdated = (preparedStatement.executeUpdate() > 0) ? true : false;
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return attendanceProfileUpdated;
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceProfileDao#update(int, com.myschool.attendance.dto.MonthAttendance)
     */
    @Override
    public void update(int attendanceMonthId, AttendanceMonth monthAttendance)
            throws DaoException {
        int index = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AttendanceMonthDaoSql.UPDATE);

            preparedStatement.setInt(++index, monthAttendance.getYear());
            preparedStatement.setInt(++index, monthAttendance.getMonthNumber());
            List<AttendanceDay> attendanceDays = monthAttendance.getAttendanceDays();
            for (AttendanceDay attendanceDay : attendanceDays) {
                AttendanceCode attendanceCode = attendanceDay.getReference();
                if (attendanceCode == null) {
                    preparedStatement.setString(++index, null);
                } else {
                    preparedStatement.setString(++index, attendanceCode.getCode());
                }
            }
            preparedStatement.setInt(++index, attendanceMonthId);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceProfileDao#delete(int)
     */
    @Override
    public boolean delete(int attendanceProfileId) throws DaoException {
        boolean attendanceProfileDeleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AttendanceProfileDaoSql.DELETE);
            preparedStatement.setInt(1, attendanceProfileId);

            attendanceProfileDeleted = (preparedStatement.executeUpdate() > 0) ? true : false;
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return attendanceProfileDeleted;
    }

}
