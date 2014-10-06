package com.myschool.exam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.exam.assembler.ExamGradeDataAssembler;
import com.myschool.exam.dto.ExamGradeDto;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class ExamGradeDaoImpl.
 */
@Repository
public class ExamGradeDaoImpl implements ExamGradeDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.exam.dao.ExamGradeDao#getGrades()
     */
    @Override
    public List<ExamGradeDto> getGrades() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<ExamGradeDto> examNames = null;

        try {
            String examGradeSql = ExamGradeDaoSql.getExamGradeSql(false);
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(examGradeSql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (examNames == null) {
                    examNames = new ArrayList<ExamGradeDto>();
                }
                examNames.add(ExamGradeDataAssembler.createExamGrade(resultSet));
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
        return examNames;
    }

    /* (non-Javadoc)
     * @see com.myschool.exam.dao.ExamGradeDao#create(com.myschool.exam.dto.ExamGradeDto)
     */
    @Override
    public int create(ExamGradeDto examGrade) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String insertExamGradeSql = ExamGradeDaoSql.getInsertExamGradeSql();
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(insertExamGradeSql);
            nextId = databaseAgent.getNextId("REF_EXAM_GRADE", "EXAM_GRADE_ID");
            preparedStatement.setInt(1, nextId);
            preparedStatement.setString(2, examGrade.getGradeName());
            preparedStatement.setInt(3, examGrade.getQualifyingPercentage());
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
     * @see com.myschool.exam.dao.ExamGradeDao#get(java.lang.String)
     */
    @Override
    public ExamGradeDto get(String examGradeName) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        ExamGradeDto examGrade = null;

        try {
            String selectExamGradeSql = ExamGradeDaoSql.getSelectExamGradeSql(examGradeName);
            connection = databaseAgent.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectExamGradeSql);
            if (resultSet.next()) {
                examGrade = ExamGradeDataAssembler.createExamGrade(resultSet);
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, statement, resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return examGrade;
    }

    /* (non-Javadoc)
     * @see com.myschool.exam.dao.ExamGradeDao#delete(int)
     */
    @Override
    public boolean delete(int examGradeId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String deleteExamGradeSql = ExamGradeDaoSql.buildDeleteExamGradeSql();
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(deleteExamGradeSql);
            preparedStatement.setInt(1, examGradeId);
            deleted = (preparedStatement.executeUpdate() > 0) ? true : false;
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
        return deleted;
    }

    /* (non-Javadoc)
     * @see com.myschool.exam.dao.ExamGradeDao#get(int)
     */
    @Override
    public ExamGradeDto get(int examGradeId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ExamGradeDto examGrade = null;

        try {
            String examGradeSql = ExamGradeDaoSql.getExamGradeSql(true);
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(examGradeSql);
            preparedStatement.setInt(1, examGradeId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                examGrade = ExamGradeDataAssembler.createExamGrade(resultSet);
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
        return examGrade;
    }

    /* (non-Javadoc)
     * @see com.myschool.exam.dao.ExamGradeDao#update(int, com.myschool.exam.dto.ExamGradeDto)
     */
    @Override
    public boolean update(int examGradeId, ExamGradeDto examGrade) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String examGradeUpdateSql = ExamGradeDaoSql.buildExamGradeUpdateSql();
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(examGradeUpdateSql);
            preparedStatement.setString(1, examGrade.getGradeName());
            preparedStatement.setInt(2, examGrade.getQualifyingPercentage());
            preparedStatement.setInt(3, examGradeId);
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
