package com.myschool.attendance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.branch.assembler.BranchDataAssembler;
import com.myschool.branch.assembler.RegionDataAssembler;
import com.myschool.branch.assembler.StateDataAssembler;
import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.RegionDto;
import com.myschool.branch.dto.StateDto;
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
     * @see com.myschool.attendance.dao.AttendanceAssignmentsDao#getAssignedStates(int)
     */
    @Override
    public List<StateDto> getAssignedStates(int attendanceProfileId)
            throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<StateDto> states = null;

        try {
            connection = databaseAgent.getConnection();
            System.out.println("AttendanceProfileDaoSql.SELECT_ALL_STATES_BY_PROFILE " + AttendanceProfileDaoSql.SELECT_ALL_STATES_BY_PROFILE);
            preparedStatement = connection.prepareStatement(AttendanceProfileDaoSql.SELECT_ALL_STATES_BY_PROFILE);
            preparedStatement.setInt(1, attendanceProfileId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (states == null) {
                    states = new ArrayList<StateDto>();
                }
                states.add(StateDataAssembler.create(resultSet));
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
        return states;
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceAssignmentsDao#getAssignedRegions(int)
     */
    @Override
    public List<RegionDto> getAssignedRegions(int attendanceProfileId)
            throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<RegionDto> regions = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AttendanceProfileDaoSql.SELECT_ALL_REGIONS_BY_PROFILE);
            preparedStatement.setInt(1, attendanceProfileId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (regions == null) {
                    regions = new ArrayList<RegionDto>();
                }
                regions.add(RegionDataAssembler.create(resultSet));
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
        return regions;
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceAssignmentsDao#getAssignedBranches(int)
     */
    @Override
    public List<BranchDto> getAssignedBranches(int attendanceProfileId)
            throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<BranchDto> branches = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AttendanceProfileDaoSql.SELECT_ALL_BRANCHES_BY_PROFILE);
            preparedStatement.setInt(1, attendanceProfileId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (branches == null) {
                    branches = new ArrayList<BranchDto>();
                }
                branches.add(BranchDataAssembler.create(resultSet));
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
        return branches;
    }

    /* (non-Javadoc)
     * @see com.myschool.attendance.dao.AttendanceAssignmentsDao#getAssignedSchools(int)
     */
    @Override
    public List<SchoolDto> getAssignedSchools(int attendanceProfileId)
            throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<SchoolDto> schools = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AttendanceProfileDaoSql.SELECT_ALL_SCHOOLS_BY_PROFILE);
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
    public List<RegisteredClassDto> getAssignedClasses(int attendanceProfileId)
            throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<RegisteredClassDto> registeredClasses = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AttendanceProfileDaoSql.SELECT_ALL_CLASSES_BY_PROFILE);
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
        String query = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            if (assignments != null && !assignments.isEmpty()) {
                connection = databaseAgent.getConnection();

                for (T assignment : assignments) {
                    int index = 0;
                    if (assignment instanceof StateDto) {
                        StateDto state = (StateDto) assignment;
                        if (query == null) {
                            query = AttendanceAssignmentsDaoSql.INSERT_ATTENDANCE_STATE;
                            preparedStatement = connection.prepareStatement(query);
                        }
                        preparedStatement.setInt(++index, attendanceProfileId);
                        preparedStatement.setInt(++index, state.getStateId());
                    } else if (assignment instanceof RegionDto) {
                        RegionDto region = (RegionDto) assignment;
                        if (query == null) {
                            query = AttendanceAssignmentsDaoSql.INSERT_ATTENDANCE_REGION;
                            preparedStatement = connection.prepareStatement(query);
                        }
                        preparedStatement.setInt(++index, attendanceProfileId);
                        preparedStatement.setInt(++index, region.getRegionId());
                    } else if (assignment instanceof BranchDto) {
                        BranchDto branch = (BranchDto) assignment;
                        if (query == null) {
                            query = AttendanceAssignmentsDaoSql.INSERT_ATTENDANCE_BRANCH;
                            preparedStatement = connection.prepareStatement(query);
                        }
                        preparedStatement.setInt(++index, attendanceProfileId);
                        preparedStatement.setInt(++index, branch.getBranchId());
                    } else if (assignment instanceof SchoolDto) {
                        SchoolDto school = (SchoolDto) assignment;
                        if (query == null) {
                            query = AttendanceAssignmentsDaoSql.INSERT_ATTENDANCE_SCHOOL;
                            preparedStatement = connection.prepareStatement(query);
                        }
                        preparedStatement.setInt(++index, attendanceProfileId);
                        preparedStatement.setInt(++index, school.getSchoolId());
                    } else if (assignment instanceof RegisteredClassDto) {
                        RegisteredClassDto registeredClass = (RegisteredClassDto) assignment;
                        if (query == null) {
                            query = AttendanceAssignmentsDaoSql.INSERT_ATTENDANCE_REGISTERED_CLASS;
                            preparedStatement = connection.prepareStatement(query);
                        }
                        preparedStatement.setInt(++index, attendanceProfileId);
                        preparedStatement.setInt(++index, registeredClass.getClassId());
                    }
                    if (query != null) {
                        preparedStatement.addBatch();
                    }
                }
                if (query != null) {
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
     * @see com.myschool.attendance.dao.AttendanceAssignmentsDao#delete(int, java.lang.Object)
     */
    @Override
    public void delete(int attendanceProfileId, Class<? extends Object> assignment) throws DaoException {
        int index = 0;
        String query = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            if (assignment == StateDto.class) {
                query = AttendanceAssignmentsDaoSql.DELETE_ATTENDANCE_STATE;
            } else if (assignment == RegionDto.class) {
                query = AttendanceAssignmentsDaoSql.DELETE_ATTENDANCE_REGION;
            } else if (assignment == BranchDto.class) {
                query = AttendanceAssignmentsDaoSql.DELETE_ATTENDANCE_BRANCH;
            } else if (assignment == SchoolDto.class) {
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
