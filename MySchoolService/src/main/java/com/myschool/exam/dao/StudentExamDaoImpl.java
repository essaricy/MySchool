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
import com.myschool.exam.assembler.StudentExamDataAssembler;
import com.myschool.exam.dto.StudentExamDto;
import com.myschool.exam.dto.StudentInExamDto;
import com.myschool.exim.dao.StudentExamDao;
import com.myschool.infra.database.agent.DatabaseAgent;
import com.myschool.student.dto.StudentDto;

/**
 * The Class StudentExamDaoImpl.
 */
@Repository
public class StudentExamDaoImpl implements StudentExamDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.exim.dao.StudentExamDao#getStudentsInExam(int, int)
     */
    @Override
    public List<StudentInExamDto> getStudentsInExam(int examId, int classId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int studentId = 0;
        StudentInExamDto studentInExam = null;
        List<StudentInExamDto> studentsInExam = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentExamDaoSql.SELECT_BY_CLASS_EXAM);
            preparedStatement.setInt(1, examId);
            preparedStatement.setInt(2, classId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (studentsInExam == null) {
                    studentsInExam = new ArrayList<StudentInExamDto>();
                }
                studentId = resultSet.getInt("STUDENT_ID");
                studentInExam = getStudentExam(studentsInExam, studentId);
                if (studentInExam == null) {
                    studentInExam = StudentExamDataAssembler.createStudentInExam(resultSet);
                    studentsInExam.add(studentInExam);
                } else {
                    StudentExamDataAssembler.updateStudentSubjectExam(resultSet, studentInExam);
                }
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
        return studentsInExam;
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.dao.StudentExamDao#getStudentsInExam(int, int, int)
     */
    @Override
    public StudentInExamDto getStudentsInExam(int studentId, int examId,
            int classId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        StudentInExamDto studentInExam = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentExamDaoSql.SELECT_BY_CLASS_EXAM);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, examId);
            preparedStatement.setInt(3, classId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                studentId = resultSet.getInt("STUDENT_ID");
                if (studentInExam == null) {
                    studentInExam = StudentExamDataAssembler.createStudentInExam(resultSet);
                } else {
                    StudentExamDataAssembler.updateStudentSubjectExam(resultSet, studentInExam);
                }
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
        return studentInExam;
    }

    /**
     * Gets the student exam.
     *
     * @param studentsInExam the students in exam
     * @param studentId the student id
     * @return the student exam
     */
    private StudentInExamDto getStudentExam(
            List<StudentInExamDto> studentsInExam, int studentId) {
        StudentDto student = null;
        StudentInExamDto returningStudent = null;
        if (studentsInExam != null && !studentsInExam.isEmpty()) {
            for (StudentInExamDto studentInExam : studentsInExam) {
                if (studentInExam != null) {
                    student = studentInExam.getStudent();
                    if (student != null) {
                        if (studentInExam.getStudent().getStudentId() == studentId) {
                            returningStudent = studentInExam;
                            break;
                        }
                    }
                }
            }
        }
        return returningStudent;
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.dao.StudentExamDao#create(int, com.myschool.exam.dto.StudentExamDto)
     */
    @Override
    public int create(int studentId, StudentExamDto studentExam) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            if (studentExam != null) {
                nextId = databaseAgent.getNextId("STUDENT_EXAM", "STUDENT_EXAM_ID");
                connection = databaseAgent.getConnection();
                preparedStatement = connection.prepareStatement(StudentExamDaoSql.INSERT);
                preparedStatement.setInt(1, nextId);
                preparedStatement.setInt(2, studentId);
                preparedStatement.setInt(3, studentExam.getStudentExamId());
                preparedStatement.setInt(4, studentExam.getObtainedMarks());
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
     * @see com.myschool.exim.dao.StudentExamDao#update(com.myschool.exam.dto.StudentExamDto)
     */
    @Override
    public boolean update(StudentExamDto studentExam) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            if (studentExam != null) {
                connection = databaseAgent.getConnection();
                preparedStatement = connection.prepareStatement(StudentExamDaoSql.UPDATE);
                preparedStatement.setInt(1, studentExam.getObtainedMarks());
                preparedStatement.setInt(2, studentExam.getStudentExamId());
                updated = (preparedStatement.executeUpdate() > 0) ? true: false;
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
        return updated;
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.dao.StudentExamDao#getStudentExam(int, int)
     */
    @Override
    public StudentExamDto getStudentExam(int studentId, int subjectExamId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        StudentExamDto studentExam = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentExamDaoSql.SELECT_BY_STUDENT_SUBJECT);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, subjectExamId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                studentExam = StudentExamDataAssembler.createStudentExam(resultSet);
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
        return studentExam;
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.dao.StudentExamDao#getStudentSubjectMarksInExam(int, java.lang.String)
     */
    @Override
    public List<StudentExamDto> getStudentSubjectMarksInExam(int examId, String admissionNumber) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<StudentExamDto> studentExams = null;
        StudentExamDto studentExam = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentExamDaoSql.SELECT_STUDENT_IN_EXAM);
            preparedStatement.setString(1, admissionNumber);
            preparedStatement.setInt(2, examId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (studentExams == null) {
                    studentExams = new ArrayList<StudentExamDto>();
                }
                studentExam = StudentExamDataAssembler.createStudentInExamForExam(resultSet);
                studentExams.add(studentExam);
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
        return studentExams;
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.dao.StudentExamDao#getStudentSubjectMarks(java.lang.String, int)
     */
    @Override
    public List<StudentExamDto> getStudentSubjectMarks(String admissionNumber,
            int subjectId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<StudentExamDto> studentExams = null;
        StudentExamDto studentExam = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(StudentExamDaoSql.SELECT_CURRENT_ACADEMIC_STUDENT_SUBJECT_MARKS);
            preparedStatement.setString(1, admissionNumber);
            preparedStatement.setInt(2, subjectId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (studentExams == null) {
                    studentExams = new ArrayList<StudentExamDto>();
                }
                studentExam = StudentExamDataAssembler.createStudentExam(resultSet);
                studentExams.add(studentExam);
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
        return studentExams;
    }

}
