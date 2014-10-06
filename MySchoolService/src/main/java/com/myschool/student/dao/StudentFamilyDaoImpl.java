package com.myschool.student.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.common.dto.FamilyMemberDto;
import com.myschool.common.dto.Relationship;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.common.util.ConversionUtil;
import com.myschool.infra.database.agent.DatabaseAgent;
import com.myschool.student.assembler.StudentFamilyDataAssembler;

/**
 * The Class StudentFamilyDaoImpl.
 */
@Repository
public class StudentFamilyDaoImpl implements StudentFamilyDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.student.dao.StudentFamilyDao#get(int)
     */
    @Override
    public FamilyMemberDto get(int familyMemberId) throws DaoException {
        FamilyMemberDto familyMember = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentFamilyDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, familyMemberId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                familyMember = StudentFamilyDataAssembler.create(resultSet);
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
        return familyMember;
    }

    /* (non-Javadoc)
     * @see com.myschool.student.dao.StudentFamilyDao#create(int, com.myschool.common.dto.FamilyMemberDto)
     */
    @Override
    public int create(int studentId, FamilyMemberDto familyMember) throws DaoException {
        int familyMemberId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            familyMemberId = databaseAgent.getNextId("STUDENT_FAMILY", "ID");
            preparedStatement = connection.prepareStatement(StudentFamilyDaoSql.INSERT);
            preparedStatement.setInt(1, familyMemberId);
            preparedStatement.setInt(2, studentId);
            preparedStatement.setString(3, familyMember.getRelationship().getCode());
            preparedStatement.setString(4, familyMember.getName());
            preparedStatement.setString(5, familyMember.getOccupation());
            preparedStatement.setString(6, familyMember.getMobileNumber());
            preparedStatement.setString(7, familyMember.getEmailId());
            preparedStatement.setString(8, ConversionUtil.toYN(familyMember.isAvailSMS()));
            preparedStatement.setString(9, ConversionUtil.toYN(familyMember.isAvailEmail()));
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(), connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return familyMemberId;
    }

    /* (non-Javadoc)
     * @see com.myschool.student.dao.StudentFamilyDao#create(int, java.util.List)
     */
    @Override
    public boolean create(int studentId, List<FamilyMemberDto> familyMembers) throws DaoException {
        boolean studentFamilyCreated = false;
        int familyMemberId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentFamilyDaoSql.INSERT);
            if (familyMembers != null && familyMembers.size() != 0) {
                for (FamilyMemberDto familyMember : familyMembers) {
                    if (familyMemberId == 0) {
                        familyMemberId = databaseAgent.getNextId("STUDENT_FAMILY", "ID");
                    } else {
                        familyMemberId++;
                    }
                    preparedStatement.setInt(1, familyMemberId);
                    preparedStatement.setInt(2, studentId);
                    preparedStatement.setString(3, familyMember.getRelationship().getCode());
                    preparedStatement.setString(4, familyMember.getName());
                    preparedStatement.setString(5, familyMember.getOccupation());
                    preparedStatement.setString(6, familyMember.getMobileNumber());
                    preparedStatement.setString(7, familyMember.getEmailId());
                    preparedStatement.setString(8, ConversionUtil.toYN(familyMember.isAvailSMS()));
                    preparedStatement.setString(9, ConversionUtil.toYN(familyMember.isAvailEmail()));
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                studentFamilyCreated = true;
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(), connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return studentFamilyCreated;
    }

    /* (non-Javadoc)
     * @see com.myschool.student.dao.StudentFamilyDao#update(int, com.myschool.common.dto.FamilyMemberDto)
     */
    @Override
    public boolean update(int familyMemberId, FamilyMemberDto familyMember) throws DaoException {
        boolean studentFamilyUpdated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentFamilyDaoSql.UPDATE);
            preparedStatement.setString(1, familyMember.getRelationship().getCode());
            preparedStatement.setString(2, familyMember.getName());
            preparedStatement.setString(3, familyMember.getOccupation());
            preparedStatement.setString(4, familyMember.getMobileNumber());
            preparedStatement.setString(5, familyMember.getEmailId());
            preparedStatement.setString(6, ConversionUtil.toYN(familyMember.isAvailSMS()));
            preparedStatement.setString(7, ConversionUtil.toYN(familyMember.isAvailEmail()));
            preparedStatement.setInt(8, familyMemberId);
            studentFamilyUpdated = preparedStatement.executeUpdate() > 0;
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
        return studentFamilyUpdated;
    }

    /* (non-Javadoc)
     * @see com.myschool.student.dao.StudentFamilyDao#delete(int)
     */
    @Override
    public boolean delete(int familyMemberId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentFamilyDaoSql.DELETE);
            preparedStatement.setInt(1, familyMemberId);
            return preparedStatement.executeUpdate() > 0;
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
     * @see com.myschool.student.dao.StudentFamilyDao#getByStudent(java.lang.String)
     */
    @Override
    public List<FamilyMemberDto> getByStudent(String admissionNumber) throws DaoException {
        List<FamilyMemberDto> familyMembers = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentFamilyDaoSql.SELECT_BY_ADMISSION_NUMBER);
            preparedStatement.setString(1, admissionNumber);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (familyMembers == null) {
                    familyMembers = new ArrayList<FamilyMemberDto>();
                }
                familyMembers.add(StudentFamilyDataAssembler.create(resultSet));
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
        return familyMembers;
    }

    /* (non-Javadoc)
     * @see com.myschool.student.dao.StudentFamilyDao#get(int, com.myschool.common.dto.Relationship, java.lang.String)
     */
    @Override
    public FamilyMemberDto get(int studentId,
            Relationship relationship, String familyMemberName)
            throws DaoException {
        FamilyMemberDto familyMember = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentFamilyDaoSql.SELECT_BY_FAMILY_MEMBER);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, relationship.getCode());
            preparedStatement.setString(3, familyMemberName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                familyMember = StudentFamilyDataAssembler.create(resultSet);
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
        return familyMember;
    }

}
