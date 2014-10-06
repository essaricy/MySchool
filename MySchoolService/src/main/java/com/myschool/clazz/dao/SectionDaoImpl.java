package com.myschool.clazz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.clazz.assembler.SectionDataAssembler;
import com.myschool.clazz.dto.SectionDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class SectionDaoImpl.
 */
@Repository
public class SectionDaoImpl implements SectionDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.SectionDao#createSection(com.myschool.clazz.dto.SectionDto)
     */
    @Override
    public int createSection(SectionDto sectionDto) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String insertSql = SectionDaoSql.buildInsertSql();
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(insertSql);
            nextId = databaseAgent.getNextId("REF_SECTION", "SECTION_ID");
            preparedStatement.setInt(1, nextId);
            preparedStatement.setString(2, sectionDto.getSectionName());
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
     * @see com.myschool.clazz.dao.SectionDao#deleteSection(int)
     */
    @Override
    public boolean deleteSection(int sectionId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            String query = SectionDaoSql.buildDeleteSql();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, sectionId);
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
     * @see com.myschool.clazz.dao.SectionDao#get(int)
     */
    @Override
    public SectionDto get(int sectionId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        SectionDto sectionDto = null;

        try {
            connection = databaseAgent.getConnection();
            String query = SectionDaoSql.buildSelectSql(true);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, sectionId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                sectionDto = SectionDataAssembler.create(resultSet);
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
        return sectionDto;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.SectionDao#getAll()
     */
    @Override
    public List<SectionDto> getAll() throws DaoException {
        List<SectionDto> sections = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        SectionDto sectionDto = null;

        try {
            connection = databaseAgent.getConnection();
            String query = SectionDaoSql.buildSelectSql(false);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sectionDto = SectionDataAssembler.create(resultSet);
                if (sections == null){
                    sections = new ArrayList<SectionDto>();
                }
                sections.add(sectionDto);
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
        return sections;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.SectionDao#updateSection(int, com.myschool.clazz.dto.SectionDto)
     */
    @Override
    public boolean updateSection(int sectionId, SectionDto sectionDto)
            throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            String query = SectionDaoSql.buildUpdateSql();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, sectionDto.getSectionName());
            preparedStatement.setInt(2, sectionId);
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
     * @see com.myschool.clazz.dao.SectionDao#create(java.lang.String)
     */
    @Override
    public int create(String sectionName) throws DaoException {
        SectionDto section = new SectionDto();
        section.setSectionName(sectionName);
        return createSection(section);
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.SectionDao#get(java.lang.String)
     */
    @Override
    public SectionDto get(String sectionName) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        SectionDto sectionDto = null;

        try {
            String selectSectionSql = SectionDaoSql.getSelectSectionSql(sectionName);
            connection = databaseAgent.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectSectionSql);
            if (resultSet.next()) {
                sectionDto = SectionDataAssembler.create(resultSet);
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
        return sectionDto;
    }

}
