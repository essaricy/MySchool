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

import com.myschool.clazz.dao.SubjectExamDaoSql;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.exam.assembler.SubjectExamsDataAssembler;
import com.myschool.exam.dto.SubjectExamDto;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class SubjectExamDaoImpl.
 */
@Repository
public class SubjectExamDaoImpl implements SubjectExamDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.exam.dao.SubjectExamDao#create(int, java.util.List)
     */
    @Override
    public void create(int examId, List<SubjectExamDto> subjectExams) throws DaoException {
        int nextId = 0;
        String createSubjectExamSql = null;
        Connection connection = null;
        Statement statement = null;

        try {
            if (subjectExams != null) {
                connection = databaseAgent.getConnection();
                statement = connection.createStatement();

                for (SubjectExamDto subjectExam : subjectExams) {
                    if (nextId == 0) {
                        nextId = databaseAgent.getNextId("SUBJECT_EXAM", "SUBJECT_EXAM_ID");
                    } else {
                        nextId++;
                    }
                    createSubjectExamSql = SubjectExamDaoSql.getInsertSubjectExamSql(nextId, examId, subjectExam);
                    statement.addBatch(createSubjectExamSql);
                }
                statement.executeBatch();
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, statement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exam.dao.SubjectExamDao#delete(java.util.List)
     */
    public void delete(List<SubjectExamDto> subjectExams) throws DaoException {
        String deleteExamsSql = null;
        Connection connection = null;
        Statement statement = null;

        try {
            if (subjectExams != null) {
                connection = databaseAgent.getConnection();
                statement = connection.createStatement();

                for (SubjectExamDto subjectExam : subjectExams) {
                    deleteExamsSql = SubjectExamDaoSql.getDeleteExamsSql(subjectExam.getSubjectExamId());
                    statement.addBatch(deleteExamsSql);
                }
                statement.executeBatch();
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, statement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exam.dao.SubjectExamDao#update(java.util.List)
     */
    @Override
    public void update(List<SubjectExamDto> subjectExams) throws DaoException {
        String updateExamsSql = null;
        Connection connection = null;
        Statement statement = null;

        try {
            if (subjectExams != null) {
                connection = databaseAgent.getConnection();
                statement = connection.createStatement();

                for (SubjectExamDto subjectExam : subjectExams) {
                    updateExamsSql = SubjectExamDaoSql.getUpdateExamsSql(subjectExam);
                    statement.addBatch(updateExamsSql);
                }
                statement.executeBatch();
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, statement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exam.dao.SubjectExamDao#getSubjectExams(int)
     */
    @Override
    public List<SubjectExamDto> getSubjectExams(int examId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<SubjectExamDto> subjectExams = null;

        try {
            String subjectExamsSql = SubjectExamDaoSql.getSubjectExamsSql(examId);
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(subjectExamsSql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (subjectExams == null) {
                    subjectExams = new ArrayList<SubjectExamDto>();
                }
                subjectExams.add(SubjectExamsDataAssembler.createSubjectExam(resultSet));
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
        return subjectExams;
    }

    /* (non-Javadoc)
     * @see com.myschool.exam.dao.SubjectExamDao#getSubjectExam(int, int)
     */
    @Override
    public SubjectExamDto getSubjectExam(int examId, int registeredSubjectId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        SubjectExamDto subjectExam = null;

        try {
            String subjectExamsSql = SubjectExamDaoSql.getSubjectExamsSql(examId, registeredSubjectId);
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(subjectExamsSql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                subjectExam = SubjectExamsDataAssembler.createSubjectExam(resultSet);
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
        return subjectExam;
    }

    /* (non-Javadoc)
     * @see com.myschool.exam.dao.SubjectExamDao#create(int, int, int)
     */
    @Override
    public int create(int examId, int registeredSubjectId, int maximumMarks) throws DaoException {
        int nextId = 0;
        String createSubjectExamSql = null;
        Connection connection = null;
        Statement statement = null;

        try {
            nextId = databaseAgent.getNextId("SUBJECT_EXAM", "SUBJECT_EXAM_ID");
            createSubjectExamSql = SubjectExamDaoSql.getInsertSubjectExamSql(nextId, examId, registeredSubjectId, maximumMarks);
            connection = databaseAgent.getConnection();
            statement = connection.createStatement();

            if (statement.executeUpdate(createSubjectExamSql) == 0) {
                nextId = 0;
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, statement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return nextId;
    }

    /* (non-Javadoc)
     * @see com.myschool.exam.dao.SubjectExamDao#update(int, int, int, int)
     */
    @Override
    public boolean update(int subjectExamId, int examId, int registeredSubjectId,
            int maximumMarks) throws DaoException {
        boolean updated = false;
        String updateSubjectExamSql = null;
        Connection connection = null;
        Statement statement = null;

        try {
            updateSubjectExamSql = SubjectExamDaoSql.getUpdateSubjectExamSql(subjectExamId, examId, registeredSubjectId, maximumMarks);
            connection = databaseAgent.getConnection();
            statement = connection.createStatement();

            updated = (statement.executeUpdate(updateSubjectExamSql) > 0) ? true : false;
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, statement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return updated;
    }
}
