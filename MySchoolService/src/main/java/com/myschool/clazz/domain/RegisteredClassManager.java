package com.myschool.clazz.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.clazz.dao.RegisteredClassDao;
import com.myschool.clazz.dto.ClassDto;
import com.myschool.clazz.dto.MediumDto;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.clazz.dto.SectionDto;
import com.myschool.common.exception.DaoException;
import com.quasar.core.exception.DataException;

/**
 * The Class RegisteredClassManager.
 */
@Component
public class RegisteredClassManager {

    /** The registered class dao. */
    @Autowired
    private RegisteredClassDao registeredClassDao;

    /**
     * Gets the by school.
     *
     * @param schoolId the school id
     * @return the by school
     * @throws DataException the data exception
     */
    public List<RegisteredClassDto> getBySchool(int schoolId) throws DataException {
        List<RegisteredClassDto> registeredClasses = null;
        try {
            registeredClasses = registeredClassDao.getBySchool(schoolId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return registeredClasses;
    }

    /**
     * Creates the.
     * 
     * @param registeredClassDto the registered class dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(RegisteredClassDto registeredClassDto) throws DataException {
        boolean created = false;
        try {
            if (registeredClassDto == null) {
                throw new DataException("registeredClassDto must not be null");
            }
            int schoolId = registeredClassDto.getSchool().getSchoolId();
            if (schoolId <= 0) {
                throw new DataException("Invalid school Id " + schoolId);
            }
            int classId = registeredClassDto.getClassDto().getClassId();
            if (classId <= 0) {
                throw new DataException("Invalid class Id " + classId);
            }
            int mediumId = registeredClassDto.getMedium().getMediumId();
            if (mediumId <= 0) {
                throw new DataException("Invalid medium Id " + mediumId);
            }
            int sectionId = registeredClassDto.getSection().getSectionId();
            if (sectionId <= 0) {
                throw new DataException("Invalid section Id " + sectionId);
            }
            List<RegisteredClassDto> classesInSchool = registeredClassDao.getBySchool(schoolId);
            if (classesInSchool != null && !classesInSchool.isEmpty()) {
                for (RegisteredClassDto classInSchool : classesInSchool) {
                    if (classInSchool != null) {
                        ClassDto classDto = classInSchool.getClassDto();
                        MediumDto medium = classInSchool.getMedium();
                        SectionDto section = classInSchool.getSection();

                        if (classDto.getClassId() == classId
                                && medium.getMediumId() == mediumId
                                && section.getSectionId() == sectionId) {
                            throw new DataException("[" + classDto.getClassName()
                                    + ", " + medium.getDescription() + ", "
                                    + section.getSectionName()
                                    + "] already exists.");
                        }
                    }
                }
            }
            created = registeredClassDao.createBySchool(schoolId, registeredClassDto);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }

    /**
     * Update.
     * 
     * @param registeredClassId the registered class id
     * @param registeredClassDto the registered class dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int registeredClassId,
            RegisteredClassDto registeredClassDto) throws DataException {
        boolean updated = false;

        try {
            if (registeredClassId <= 0) {
                throw new DataException("Invalid registered class Id " + registeredClassId);
            }
            if (registeredClassDto == null) {
                throw new DataException("registeredClassDto must not be null");
            }
            int schoolId = registeredClassDto.getSchool().getSchoolId();
            if (schoolId <= 0) {
                throw new DataException("Invalid school Id " + schoolId);
            }
            int classId = registeredClassDto.getClassDto().getClassId();
            if (classId <= 0) {
                throw new DataException("Invalid class Id " + classId);
            }
            int mediumId = registeredClassDto.getMedium().getMediumId();
            if (mediumId <= 0) {
                throw new DataException("Invalid medium Id " + mediumId);
            }
            int sectionId = registeredClassDto.getSection().getSectionId();
            if (sectionId <= 0) {
                throw new DataException("Invalid section Id " + sectionId);
            }
            List<RegisteredClassDto> classesInSchool = registeredClassDao.getBySchool(schoolId);
            if (classesInSchool != null && !classesInSchool.isEmpty()) {
                for (RegisteredClassDto classInSchool : classesInSchool) {
                    if (classInSchool != null) {
                        ClassDto classDto = classInSchool.getClassDto();
                        MediumDto medium = classInSchool.getMedium();
                        SectionDto section = classInSchool.getSection();

                        if (registeredClassId != classInSchool.getClassId()
                                && (classDto.getClassId() == classId
                                    && medium.getMediumId() == mediumId
                                    && section.getSectionId() == sectionId)) {
                            throw new DataException("[" + classDto.getClassName()
                                    + ", " + medium.getDescription() + ", "
                                    + section.getSectionName()
                                    + "] already exists.");
                        }
                    }
                }
            }
            updated = registeredClassDao.update(registeredClassId, registeredClassDto);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return updated;
    }

    /**
     * Delete.
     * 
     * @param registeredClassId the registered class id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int registeredClassId) throws DataException {
        boolean deleted = false;
        try {
            if (registeredClassId <= 0) {
                throw new DataException("Invalid registered class Id " + registeredClassId);
            }
            deleted = registeredClassDao.delete(registeredClassId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return deleted;
    }

    /**
     * Update by school.
     *
     * @param schoolId the school id
     * @param updatedClasses the registered classes
     * @throws DataException the data exception
     */
    public void updateBySchool(int schoolId,
            List<RegisteredClassDto> updatedClasses) throws DataException {

        try {
            if (updatedClasses != null) {
                // Delete the unspecified classes here.
                List<RegisteredClassDto> classesInSchool = registeredClassDao.getBySchool(schoolId);
                if (classesInSchool != null && !classesInSchool.isEmpty()) {
                    for (RegisteredClassDto classInSchool : classesInSchool) {
                        if (classInSchool != null) {
                            boolean classRetained = false;
                            for (RegisteredClassDto updatedClass : updatedClasses) {
                                if (updatedClass.getClassId() == classInSchool.getClassId()) {
                                    classRetained = true;
                                    break;
                                }
                            }
                            if (!classRetained) {
                                registeredClassDao.delete(classInSchool.getClassId());
                            }
                        }
                    }
                }
                for (RegisteredClassDto updatedClass : updatedClasses) {
                    if (updatedClass != null) {
                        if (updatedClass.getClassId() == 0) {
                            registeredClassDao.createBySchool(schoolId, updatedClass);
                        } else {
                            registeredClassDao.update(updatedClass.getClassId(), updatedClass);
                        }
                    }
                }
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }

    }

    /**
     * Gets the all.
     * 
     * @return the all
     * @throws DataException the data exception
     */
    public List<RegisteredClassDto> getAll() throws DataException {
        List<RegisteredClassDto> operatingClasses = null;
        try {
            operatingClasses = registeredClassDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return operatingClasses;
    }

    /**
     * Gets the.
     * 
     * @param registeredClassId the registered class id
     * @return the registered class dto
     * @throws DataException the data exception
     */
    public RegisteredClassDto get(int registeredClassId) throws DataException {
        RegisteredClassDto operatingClass = null;
        try {
            operatingClass = registeredClassDao.get(registeredClassId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return operatingClass;
    }

}
