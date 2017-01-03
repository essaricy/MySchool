package com.myschool.exam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.exam.assembler.ExamDataAssembler;
import com.myschool.exam.dto.ExamDto;
import com.myschool.infra.database.agent.DatabaseAgent;
import com.quasar.core.util.ConversionUtil;

/**
 * The Class ExamDaoImpl.
 */
@Repository
public class ExamDaoImpl implements ExamDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.exam.dao.ExamDao#getByClass(int)
     */
    @Override
    public List<ExamDto> getByClass(int classId) throws DaoException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<ExamDto> examNames = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ExamDaoSql.SELECT_BY_CLASS);
            preparedStatement.setInt(1, classId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (examNames == null) {
                    examNames = new ArrayList<ExamDto>();
                }
                examNames.add(ExamDataAssembler.create(resultSet));
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
     * @see com.myschool.exam.dao.ExamDao#get(int)
     */
    @Override
    public ExamDto get(int examId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ExamDto exam = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ExamDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, examId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                exam = ExamDataAssembler.create(resultSet);
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
        return exam;
    }

    /* (non-Javadoc)
     * @see com.myschool.exam.dao.ExamDao#delete(int)
     */
    @Override
    public boolean delete(int examId) throws DaoException {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ExamDaoSql.DELETE);
            preparedStatement.setInt(1, examId);
            returnValue = preparedStatement.executeUpdate() > 0;
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
        return returnValue;
    }

    /* (non-Javadoc)
     * @see com.myschool.exam.dao.ExamDao#update(int, com.myschool.exam.dto.ExamDto)
     */
    @Override
    public boolean update(int examId, ExamDto exam) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            if (exam != null && exam.getSubjectExams() != null) {
                connection = databaseAgent.getConnection();
                preparedStatement = connection.prepareStatement(ExamDaoSql.UPDATE);
                preparedStatement.setString(1, exam.getExamName());
                preparedStatement.setDate(2, ConversionUtil.fromApplicationDateToStorageDate(exam.getExamDate()));
                preparedStatement.setString(3, ConversionUtil.toYN(exam.isExamCompleted()));
                preparedStatement.setInt(4, examId);
                return (preparedStatement.executeUpdate() > 0);
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
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.exam.dao.ExamDao#get(int, java.lang.String, java.lang.String)
     */
    @Override
    public ExamDto get(int classId, String examName, String examDate) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ExamDto exam = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ExamDaoSql.SELECT_BY_CLASS_EXAM);
            preparedStatement.setInt(1, classId);
            preparedStatement.setString(2, examName);
            preparedStatement.setString(3, examDate);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                exam = ExamDataAssembler.create(resultSet);
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
        return exam;
    }

    /* (non-Javadoc)
     * @see com.myschool.exam.dao.ExamDao#create(com.myschool.exam.dto.ExamDto)
     */
    @Override
    public int create(ExamDto exam) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            if (exam != null) {
                nextId = databaseAgent.getNextId("EXAM", "EXAM_ID");
                connection = databaseAgent.getConnection();
                preparedStatement = connection.prepareStatement(ExamDaoSql.INSERT);
                preparedStatement.setInt(1, nextId);
                preparedStatement.setInt(2, exam.getRegisteredClass().getClassId());
                preparedStatement.setString(3, exam.getExamName());
                preparedStatement.setDate(4, ConversionUtil.fromApplicationDateToStorageDate(exam.getExamDate()));
                preparedStatement.setString(5, ConversionUtil.toYN(exam.isExamCompleted()));
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
        return nextId;
    }

    /* (non-Javadoc)
     * @see com.myschool.exam.dao.ExamDao#get(int, java.lang.String)
     */
    @Override
    public ExamDto get(int classId, String examName) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ExamDto exam = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ExamDaoSql.SELECT_BY_EXAM);
            preparedStatement.setInt(1, classId);
            preparedStatement.setString(2, examName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                exam = ExamDataAssembler.create(resultSet);
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
        return exam;
    }

    /* (non-Javadoc)
     * @see com.myschool.exam.dao.ExamDao#getExamsInCurrentAcademicByClass(int)
     */
    @Override
    public List<ExamDto> getExamsInCurrentAcademicByClass(int classId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<ExamDto> examNames = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ExamDaoSql.SELECT_BY_CLASS_CURRENT_ACADEMIC);
            preparedStatement.setInt(1, classId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (examNames == null) {
                    examNames = new ArrayList<ExamDto>();
                }
                examNames.add(ExamDataAssembler.create(resultSet));
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
     * @see com.myschool.exam.dao.ExamDao#getExamsInCurrentAcademic()
     */
    @Override
    public List<ExamDto> getExamsInCurrentAcademic() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<ExamDto> examNames = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ExamDaoSql.SELECT_BY_CURRENT_ACADEMIC);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (examNames == null) {
                    examNames = new ArrayList<ExamDto>();
                }
                examNames.add(ExamDataAssembler.create(resultSet));
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
     * @see com.myschool.exam.dao.ExamDao#getLatestByClass(int)
     */
    @Override
    public ExamDto getLatestByClass(int classId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ExamDto exam = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ExamDaoSql.SELECT_LATEST);
            preparedStatement.setInt(1, classId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                exam = ExamDataAssembler.create(resultSet);
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
        return exam;
    }

}
