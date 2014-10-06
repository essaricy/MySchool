package com.myschool.attendance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.attendance.dto.AttendanceDto;
import com.myschool.attendance.dto.MonthAttendance;
import com.myschool.attendance.dto.ReferenceAttendanceDto;
import com.myschool.attendance.dto.StudentAttendanceDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class AttendanceDaoImpl.
 */
@Repository
public class AttendanceDaoImpl implements AttendanceDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceDao#getReferenceAttendance(int, int, int)
     */
    @Override
    public ReferenceAttendanceDto getReferenceAttendance(int classId, int year,
            int month) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ReferenceAttendanceDto referenceAttendance = null;

        try {
            connection = databaseAgent.getConnection();
            String query = AttendanceDaoSql.buildReferenceAttendanceQuery();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, year);
            preparedStatement.setInt(2, month);
            preparedStatement.setInt(3, classId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                //referenceAttendance = AttendanceAssembler.createReferenceAttendance(resultSet);
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        }/* catch (ValidationException validationException) {
            throw new DaoException(validationException.getMessage(),
                    validationException);
        }*/ finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement, resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return referenceAttendance;
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceDao#createReferenceAttendance(com.myschool.attendance.dto.ReferenceAttendanceDto)
     */
    @Override
    public int createReferenceAttendance(ReferenceAttendanceDto referenceAttendance) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            String referenceAttendanceInsertQuery = AttendanceDaoSql.buildReferenceAttendanceInsertQuery(referenceAttendance);
            preparedStatement = connection.prepareStatement(referenceAttendanceInsertQuery);
            nextId = databaseAgent.getNextId("REF_ATTENDANCE", "ATTENDANCE_ID");
            preparedStatement.setInt(1, nextId);
            if (preparedStatement.executeUpdate() == 0) {
                nextId = 0;
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
        return nextId;
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceDao#updateReferenceAttendance(int, com.myschool.attendance.dto.ReferenceAttendanceDto)
     */
    @Override
    public boolean updateReferenceAttendance(int referenceAttendanceId,
            ReferenceAttendanceDto referenceAttendance) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            String referenceAttendanceUpdateQuery = AttendanceDaoSql.buildReferenceAttendanceUpdateQuery(referenceAttendanceId, referenceAttendance);
            preparedStatement = connection.prepareStatement(referenceAttendanceUpdateQuery);
            updated = (preparedStatement.executeUpdate() > 0) ? true : false;
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
        return updated;
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceDao#getStudentAttendance(int, int, int)
     */
    @Override
    public AttendanceDto getStudentAttendance(int studentId, int year, int month)
            throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        MonthAttendance monthAttendance = null;

        try {
            connection = databaseAgent.getConnection();
            String query = AttendanceDaoSql.buildStudentAttendanceQuery();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, year);
            preparedStatement.setInt(2, month);
            preparedStatement.setInt(3, studentId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                //monthAttendance = AttendanceAssembler.createStudentAttendance(resultSet);
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } /*catch (ValidationException validationException) {
            throw new DaoException(validationException.getMessage(),
                    validationException);
        }*/ finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement, resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return monthAttendance;
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceDao#createStudentAttendance(com.myschool.attendance.dto.ReferenceAttendanceDto, com.myschool.attendance.dto.StudentAttendanceDto)
     */
    @Override
    public int createStudentAttendance(
            ReferenceAttendanceDto referenceAttendance,
            StudentAttendanceDto studentAttendance) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            String studentAttendanceInsertQuery = AttendanceDaoSql.buildStudentAttendanceInsertQuery(referenceAttendance, studentAttendance);
            preparedStatement = connection.prepareStatement(studentAttendanceInsertQuery);
            nextId = databaseAgent.getNextId("STUDENT_ATTENDANCE", "ATTENDANCE_ID");
            preparedStatement.setInt(1, nextId);
            if (preparedStatement.executeUpdate() == 0) {
                nextId = 0;
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
        return nextId;
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceDao#updateStudentAttendance(int, com.myschool.attendance.dto.ReferenceAttendanceDto, com.myschool.attendance.dto.StudentAttendanceDto)
     */
    @Override
    public boolean updateStudentAttendance(int monthAttendanceId,
            ReferenceAttendanceDto referenceAttendance,
            StudentAttendanceDto studentAttendance) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            String studentAttendanceUpdateQuery = AttendanceDaoSql.buildStudentAttendanceUpdateQuery(
                    monthAttendanceId, referenceAttendance, studentAttendance);
            preparedStatement = connection.prepareStatement(studentAttendanceUpdateQuery);
            updated = (preparedStatement.executeUpdate() > 0) ? true : false;
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
        return updated;
    }

}
