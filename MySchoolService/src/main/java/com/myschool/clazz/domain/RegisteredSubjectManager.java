package com.myschool.clazz.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.clazz.dao.RegisteredSubjectDao;
import com.myschool.clazz.dto.RegisteredSubjectDto;
import com.myschool.clazz.dto.SubjectDto;
import com.myschool.common.exception.DaoException;
import com.quasar.core.exception.DataException;
import com.quasar.core.exception.InsufficientInputException;
import com.quasar.core.exception.InvalidDataException;

/**
 * The Class RegisteredSubjectManager.
 */
@Component
public class RegisteredSubjectManager {

    /** The registered subject dao. */
    @Autowired
    private RegisteredSubjectDao registeredSubjectDao;

    /**
     * Gets the.
     *
     * @param registeredSubjectId the registered subject id
     * @return the registered subject dto
     * @throws DataException the data exception
     */
    public RegisteredSubjectDto get(int registeredSubjectId) throws DataException {
        RegisteredSubjectDto registeredSubject = null;
        try {
            registeredSubject = registeredSubjectDao.get(registeredSubjectId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return registeredSubject;
    }

    /**
     * Gets the by class.
     *
     * @param classId the class id
     * @return the by class
     * @throws DataException the data exception
     */
    public List<RegisteredSubjectDto> getByClass(int classId) throws DataException {
        List<RegisteredSubjectDto> subjects = null;
        try {
            subjects = registeredSubjectDao.getByClass(classId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return subjects;
    }

    /**
     * Creates the.
     *
     * @param registeredSubject the registered subject
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(RegisteredSubjectDto registeredSubject)
            throws DataException {
        try {
            validateRegisteredSubject(registeredSubject);
            int classId = registeredSubject.getRegisteredClass().getClassId();
            int subjectId = registeredSubject.getSubject().getSubjectId();
            List<RegisteredSubjectDto> registeredSubjects = registeredSubjectDao.getByClass(classId);
            if (registeredSubjects != null && !registeredSubjects.isEmpty()) {
                for (RegisteredSubjectDto existingRegisteredSubject : registeredSubjects) {
                    SubjectDto subject = existingRegisteredSubject.getSubject();
                    if (subject.getSubjectId() == subjectId) {
                        throw new DataException(subject.getSubjectName() + " already present in the class.");
                    }
                }
            }
            return registeredSubjectDao.create(registeredSubject);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Update.
     *
     * @param registeredSubjectId the registered subject id
     * @param registeredSubject the registered subject
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int registeredSubjectId, RegisteredSubjectDto registeredSubject) throws DataException {
        boolean created = false;
        try {
            if (registeredSubjectId <=0 ) {
                throw new InvalidDataException("Invalid Registered Subject ID");
            }
            validateRegisteredSubject(registeredSubject);
            int classId = registeredSubject.getRegisteredClass().getClassId();
            int subjectId = registeredSubject.getSubject().getSubjectId();
            List<RegisteredSubjectDto> registeredSubjects = registeredSubjectDao.getByClass(classId);
            if (registeredSubjects != null && !registeredSubjects.isEmpty()) {
                for (RegisteredSubjectDto existingRegisteredSubject : registeredSubjects) {
                    SubjectDto subject = existingRegisteredSubject.getSubject();
                    if (subject.getSubjectId() == subjectId) {
                        throw new DataException(subject.getSubjectName() + " already present in the class.");
                    }
                }
            }
            created = registeredSubjectDao.update(registeredSubjectId, registeredSubject);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }

    /**
     * Validate registered subject.
     *
     * @param registeredSubject the registered subject
     * @throws InvalidDataException the invalid data exception
     * @throws InsufficientInputException the insufficient input exception
     */
    private void validateRegisteredSubject(RegisteredSubjectDto registeredSubject)
            throws InvalidDataException, InsufficientInputException {

        if (registeredSubject == null) {
            throw new InsufficientInputException("Registered Subject is null");
        }
        SubjectDto subject = registeredSubject.getSubject();
        if (subject == null) {
            throw new InsufficientInputException("Subject is null");
        }
        int subjectId = subject.getSubjectId();
        if (subjectId <=0 ) {
            throw new InvalidDataException("Invalid Subject ID");
        }
    }

    /**
     * Delete.
     *
     * @param registeredSubjectId the registered subject id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int registeredSubjectId) throws DataException {
        try {
            return registeredSubjectDao.delete(registeredSubjectId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

}
