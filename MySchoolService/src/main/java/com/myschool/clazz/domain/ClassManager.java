package com.myschool.clazz.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.clazz.dao.ClassDao;
import com.myschool.clazz.dto.ClassDto;
import com.myschool.common.exception.DaoException;
import com.quasar.core.exception.DataException;
import com.quasar.core.exception.InsufficientInputException;
import com.quasar.core.exception.InvalidDataException;

/**
 * The Class ClassManager.
 */
@Component
public class ClassManager {

    /** The class dao. */
    @Autowired
    private ClassDao classDao;

    /**
     * Creates the.
     *
     * @param classDto the class dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(ClassDto classDto) throws DataException {
        boolean created = false;
        try {
            if (classDto == null) {
                throw new InsufficientInputException("classDto is null");
            }
            String className = classDto.getClassName();
            if (className == null || className.trim().length() == 0) {
                throw new InsufficientInputException("Class Name is a required value.");
            }
            int promotionOrder = classDto.getPromotionOrder();
            List<ClassDto> allClasses = classDao.getAll();
            if (allClasses != null) {
                for (ClassDto existingClassDto : allClasses) {
                    if (existingClassDto != null) {
                        if (existingClassDto.getClassName().trim().toLowerCase().equals(
                                classDto.getClassName().trim().toLowerCase())) {
                            throw new DataException("Class ("
                                    + existingClassDto.getClassName()
                                    + ") is already defined.");
                        }
                        if (promotionOrder == existingClassDto.getPromotionOrder()) {
                            throw new DataException("A Class (" + existingClassDto.getClassName()
                                    + ") is already defined with the same promotion order (" + promotionOrder + ").");
                        }
                    }
                }
            }
            created = classDao.create(classDto) > 0;
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }

    /**
     * Delete.
     *
     * @param classId the class id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int classId) throws DataException {
        boolean deleted = false;
        try {
            if (classId <= 0) {
                throw new InvalidDataException("Invalid Class ID.");
            }
            deleted = classDao.delete(classId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return deleted;
    }

    /**
     * Gets the.
     *
     * @param classId the class id
     * @return the class dto
     * @throws DataException the data exception
     */
    public ClassDto get(int classId) throws DataException {
        ClassDto classDto = null;
        try {
            if (classId <= 0) {
                throw new InvalidDataException("Invalid Class ID.");
            }
            classDto = classDao.get(classId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return classDto;
    }

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DataException the data exception
     */
    public List<ClassDto> getAll() throws DataException {
        List<ClassDto> classes = null;
        try {
            classes = classDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return classes;
    }

    /**
     * Update.
     *
     * @param classId the class id
     * @param classDto the class dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int classId, ClassDto classDto) throws DataException {
        boolean created = false;
        try {
            if (classDto == null) {
                throw new InsufficientInputException("classDto is null");
            }
            String className = classDto.getClassName();
            if (className == null || className.trim().length() == 0) {
                throw new InsufficientInputException("Class Name is a required value.");
            }
            int promotionOrder = classDto.getPromotionOrder();
            List<ClassDto> allClasses = classDao.getAll();
            if (allClasses != null) {
                for (ClassDto existingClassDto : allClasses) {
                    if (existingClassDto != null) {
                        if (classId != existingClassDto.getClassId()
                                && existingClassDto.getClassName().trim().toLowerCase().equals(
                                        classDto.getClassName().trim().toLowerCase())) {
                            throw new DataException("Class ("
                                    + existingClassDto.getClassName()
                                    + ") is already defined.");
                        }
                        if (classId != existingClassDto.getClassId()
                                && promotionOrder == existingClassDto.getPromotionOrder()) {
                            throw new DataException("A Class (" + existingClassDto.getClassName()
                                    + ") is already defined with the same promotion order (" + promotionOrder + ").");
                        }
                    }
                }
            }
            created = classDao.update(classId, classDto);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }

}
