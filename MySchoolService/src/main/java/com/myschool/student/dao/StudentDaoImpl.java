package com.myschool.student.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.common.constants.RecordStatus;
import com.myschool.common.dto.PersonalDetailsDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;
import com.myschool.student.assembler.StudentDataAssembler;
import com.myschool.student.dto.StudentDto;
import com.myschool.student.dto.StudentSearchCriteriaDto;
import com.quasar.core.util.ConversionUtil;

/**
 * The Class StudentDaoImpl.
 */
@Repository
public class StudentDaoImpl implements StudentDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.student.dao.StudentDao#get(java.lang.String)
     */
    public StudentDto get(String admissionNumber) throws DaoException {
        StudentDto student = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentDaoSql.SELET_BY_ADMISSION_NUMBER);
            preparedStatement.setString(1, admissionNumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                student = StudentDataAssembler.create(resultSet);
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
        return student;
    }

    /* (non-Javadoc)
     * @see com.myschool.student.dao.StudentDao#update(int, com.myschool.student.dto.StudentDto)
     */
    public boolean update(int studentId, StudentDto student) throws DaoException {
        boolean studentUpdated = false;
        int index = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentDaoSql.UPDATE);
            PersonalDetailsDto personalDetails = student.getPersonalDetails();
            preparedStatement.setInt(++index, student.getRegisteredClassDto().getClassId());
            preparedStatement.setInt(++index, student.getAdmissionStatus().getStatusId());
            preparedStatement.setDate(++index, ConversionUtil.fromApplicationDateToStorageDate(
                    student.getDateOfJoining()));
            preparedStatement.setString(++index, personalDetails.getFirstName());
            preparedStatement.setString(++index, personalDetails.getMiddleName());
            preparedStatement.setString(++index, personalDetails.getLastName());
            preparedStatement.setString(++index, personalDetails.getGender());
            preparedStatement.setDate(++index, ConversionUtil.fromApplicationDateToStorageDate(
                    personalDetails.getDateOfBirth()));
            preparedStatement.setString(++index, personalDetails.getReligion());
            preparedStatement.setString(++index, personalDetails.getCaste());
            preparedStatement.setString(++index, personalDetails.getNationality());
            preparedStatement.setString(++index, personalDetails.getMotherTongue());
            preparedStatement.setString(++index, personalDetails.getPermanentAddress());
            preparedStatement.setString(++index, personalDetails.getCorrespondenceAddress());
            preparedStatement.setString(++index, personalDetails.getMobileNumber());
            preparedStatement.setString(++index, personalDetails.getIdentificationMarks());
            preparedStatement.setString(++index, personalDetails.getBloodGroup());
            preparedStatement.setString(++index, student.getRemarks());
            preparedStatement.setString(++index, ConversionUtil.toYN(student.isVerified()));
            preparedStatement.setInt(++index, studentId);
            studentUpdated = (preparedStatement.executeUpdate() > 0) ? true : false;
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
        return studentUpdated;
    }

    /* (non-Javadoc)
     * @see com.myschool.student.dao.StudentDao#create(com.myschool.student.dto.StudentDto)
     */
    public int create(StudentDto student) throws DaoException {
        boolean studentCreated = false;
        int index = 0;
        int studentId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            studentId = databaseAgent.getNextId("STUDENT", "STUDENT_ID");
            preparedStatement = connection.prepareStatement(StudentDaoSql.INSERT);
            PersonalDetailsDto personalDetails = student.getPersonalDetails();
            preparedStatement.setInt(++index, studentId);
            preparedStatement.setString(++index, student.getAdmissionNumber());
            preparedStatement.setInt(++index, student.getRegisteredClassDto().getClassId());
            preparedStatement.setInt(++index, student.getAdmissionStatus().getStatusId());
            preparedStatement.setDate(++index, ConversionUtil.fromApplicationDateToStorageDate(
                    student.getDateOfJoining()));
            preparedStatement.setString(++index, personalDetails.getFirstName());
            preparedStatement.setString(++index, personalDetails.getMiddleName());
            preparedStatement.setString(++index, personalDetails.getLastName());
            preparedStatement.setString(++index, personalDetails.getGender());
            preparedStatement.setDate(++index, ConversionUtil.fromApplicationDateToStorageDate(
                    personalDetails.getDateOfBirth()));
            preparedStatement.setString(++index, personalDetails.getReligion());
            preparedStatement.setString(++index, personalDetails.getCaste());
            preparedStatement.setString(++index, personalDetails.getNationality());
            preparedStatement.setString(++index, personalDetails.getMotherTongue());
            preparedStatement.setString(++index, personalDetails.getPermanentAddress());
            preparedStatement.setString(++index, personalDetails.getCorrespondenceAddress());
            preparedStatement.setString(++index, personalDetails.getMobileNumber());
            preparedStatement.setString(++index, personalDetails.getIdentificationMarks());
            preparedStatement.setString(++index, personalDetails.getBloodGroup());
            preparedStatement.setString(++index, student.getRemarks());
            preparedStatement.setString(++index, ConversionUtil.toYN(student.isVerified()));

            studentCreated = (preparedStatement.executeUpdate() > 0) ? true : false;
            if (!studentCreated) {
                studentId = 0;
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
        return studentId;
    }

    /* (non-Javadoc)
     * @see com.myschool.student.dao.StudentDao#delete(java.lang.String)
     */
    public void delete(String admissionNumber) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentDaoSql.DELETE);
            preparedStatement.setString(1, admissionNumber);
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
     * @see com.myschool.student.dao.StudentDao#getCurrentAyStudents(int)
     */
    @Override
    public List<StudentDto> getCurrentAyStudents(int classId) throws DaoException {
        List<StudentDto> students = null;
        StudentDto student = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String currentAyStudentsSql = StudentDaoSql.getCurrentAyStudentsSql(classId);
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(currentAyStudentsSql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                student = StudentDataAssembler.create(resultSet);
                if (students == null) {
                    students = new ArrayList<StudentDto>();
                }
                students.add(student);
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
        return students;
    }

    /* (non-Javadoc)
     * @see com.myschool.student.dao.StudentDao#promoteStudent(int, int, java.lang.String)
     */
    @Override
    public boolean promoteStudent(int studentId, int nextClassId, String nextAcademicYear) throws DaoException {
        boolean studentUpdated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String promoteStudentQuery = StudentDaoSql.getPromoteStudentQuery(studentId, nextClassId, nextAcademicYear);
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(promoteStudentQuery);
            studentUpdated = (preparedStatement.executeUpdate() > 0) ? true : false;
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
        return studentUpdated;
    }

    /* (non-Javadoc)
     * @see com.myschool.student.dao.StudentDao#get(int)
     */
    @Override
    public StudentDto get(int studentId) throws DaoException {
        StudentDto student = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, studentId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                student = StudentDataAssembler.create(resultSet);
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
        return student;
    }

    /* (non-Javadoc)
     * @see com.myschool.student.dao.StudentDao#terminateAdmission(java.lang.String)
     */
    @Override
    public boolean terminateAdmission(String admissionNumber) throws DaoException {
        boolean studentUnregistered = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentDaoSql.TERMINATE_STUDENT);
            preparedStatement.setString(1, admissionNumber);
            studentUnregistered = (preparedStatement.executeUpdate() > 0) ? true : false;
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
        return studentUnregistered;
    }

    /* (non-Javadoc)
     * @see com.myschool.student.dao.StudentDao#getLastAdmissionNumber()
     */
    @Override
    public String getLastAdmissionNumber() throws DaoException {
        String admissionNumber = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(StudentDaoSql.SELECT_LAST_ADMISSION_NUMBER);
            if (resultSet.next()) {
                admissionNumber = resultSet.getString("ADMISSION_NUMBER");
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
        return admissionNumber;
    }

    /* (non-Javadoc)
     * @see com.myschool.student.dao.StudentDao#search(com.myschool.student.dto.StudentSearchCriteriaDto)
     */
    @Override
    public List<StudentDto> search(
            StudentSearchCriteriaDto studentSearchCriteriaDto) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<StudentDto> students = null;
        StudentDto student = null;

        try {
            connection = databaseAgent.getConnection();
            String studentSearchQuery = StudentDaoSql.getStudentSearchQuery(studentSearchCriteriaDto);
            preparedStatement = connection.prepareStatement(studentSearchQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                student = StudentDataAssembler.create(resultSet);
                if (students == null) {
                    students = new ArrayList<StudentDto>();
                }
                students.add(student);
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
        return students;
    }

	/* (non-Javadoc)
	 * @see com.myschool.student.dao.StudentDao#getNextAdmissionNumber(java.lang.String, com.myschool.common.constants.RecordStatus)
	 */
	@Override
	public String getNextAdmissionNumber(String admissionNumber, RecordStatus recordStatus) throws DaoException {
		String query = null;
        String nextAdmissionNumber = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            if (recordStatus == RecordStatus.VERIFIED) {
            	query = StudentDaoSql.SELECT_NEXT_VERIFIED_ADMISSION_NUMBER;
            } else {
            	query = StudentDaoSql.SELECT_NEXT_UNVERIFIED_ADMISSION_NUMBER;
            }
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, admissionNumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            	nextAdmissionNumber = resultSet.getString("ADMISSION_NUMBER");
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
        return nextAdmissionNumber;
    }

	/* (non-Javadoc)
	 * @see com.myschool.student.dao.StudentDao#getPreviousAdmissionNumber(java.lang.String, com.myschool.common.constants.RecordStatus)
	 */
	@Override
	public String getPreviousAdmissionNumber(String admissionNumber, RecordStatus recordStatus) throws DaoException {
		String query = null;
        String previousAdmissionNumber = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            if (recordStatus == RecordStatus.VERIFIED) {
            	query = StudentDaoSql.SELECT_PREVIOUS_VERIFIED_ADMISSION_NUMBER;
            } else {
            	query = StudentDaoSql.SELECT_PREVIOUS_UNVERIFIED_ADMISSION_NUMBER;
            }
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, admissionNumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            	previousAdmissionNumber = resultSet.getString("ADMISSION_NUMBER");
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
        return previousAdmissionNumber;
    }

}
