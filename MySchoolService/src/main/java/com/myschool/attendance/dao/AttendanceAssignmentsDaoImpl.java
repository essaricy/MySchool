package com.myschool.attendance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.clazz.assembler.RegisteredClassDataAssembler;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;
import com.myschool.school.assembler.SchoolDataAssembler;
import com.myschool.school.dto.SchoolDto;

/**
 * The Class AttendanceAssignmentsDaoImpl.
 */
@Repository
public class AttendanceAssignmentsDaoImpl implements AttendanceAssignmentsDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceAssignmentsDao#getAssignedSchools(int)
     */
    @Override
    public List<SchoolDto> getAssignedSchools(int attendanceProfileId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<SchoolDto> schools = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AttendanceProfileAssignmentsDaoSql.SELECT_PROFILE_ASSIGNED_SCHOOLS);
            preparedStatement.setInt(1, attendanceProfileId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (schools == null) {
                    schools = new ArrayList<SchoolDto>();
                }
                schools.add(SchoolDataAssembler.create(resultSet));
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
        return schools;
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceAssignmentsDao#getAssignedClasses(int)
     */
    @Override
    public List<RegisteredClassDto> getAssignedClasses(int attendanceProfileId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<RegisteredClassDto> registeredClasses = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AttendanceProfileAssignmentsDaoSql.SELECT_PROFILE_ASSIGNED_CLASSES);
            preparedStatement.setInt(1, attendanceProfileId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (registeredClasses == null) {
                    registeredClasses = new ArrayList<RegisteredClassDto>();
                }
                registeredClasses.add(RegisteredClassDataAssembler.create(resultSet));
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
        return registeredClasses;
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceAssignmentsDao#create(int, java.util.List)
     */
    @Override
    public <T> void create(int attendanceProfileId, List<T> assignments) throws DaoException {
        int assignmentId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            if (assignments != null && !assignments.isEmpty()) {
                connection = databaseAgent.getConnection();

                for (T assignment : assignments) {
                    if (assignment instanceof SchoolDto) {
                        if (preparedStatement == null) {
                            preparedStatement = connection.prepareStatement(AttendanceAssignmentsDaoSql.INSERT_ATTENDANCE_SCHOOL);
                        }
                        SchoolDto school = (SchoolDto) assignment;
                        assignmentId = school.getSchoolId();
                    } else if (assignment instanceof RegisteredClassDto) {
                        if (preparedStatement == null) {
                            preparedStatement = connection.prepareStatement(AttendanceAssignmentsDaoSql.INSERT_ATTENDANCE_REGISTERED_CLASS);
                        }
                        RegisteredClassDto registeredClass = (RegisteredClassDto) assignment;
                        assignmentId = registeredClass.getClassId();
                    }
                    if (preparedStatement != null) {
                        preparedStatement.setInt(1, attendanceProfileId);
                        preparedStatement.setInt(2, assignmentId);
                        preparedStatement.addBatch();
                    }
                }
                if (preparedStatement != null) {
                    preparedStatement.executeBatch();
                }
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
     * @see com.myschool.attendance.dao.AttendanceAssignmentsDao#delete(int, java.lang.Class)
     */
    @Override
    public void delete(int attendanceProfileId,
            Class<? extends Object> assignment) throws DaoException {
        int index = 0;
        String query = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            if (assignment == SchoolDto.class) {
                query = AttendanceAssignmentsDaoSql.DELETE_ATTENDANCE_SCHOOL;
            } else if (assignment == RegisteredClassDto.class) {
                query = AttendanceAssignmentsDaoSql.DELETE_ATTENDANCE_REGISTERED_CLASS;
            }
            if (query != null) {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(++index, attendanceProfileId);
                preparedStatement.executeUpdate();
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

}
