package com.myschool.clazz.service;

import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.exception.ServiceException;

/**
 * The Interface RegisteredClassService.
 */
public interface RegisteredClassService extends Servicable<RegisteredClassDto> {

    /**
     * Gets the by school.
     *
     * @param schoolId the school id
     * @return the by school
     * @throws ServiceException the service exception
     */
    List<RegisteredClassDto> getBySchool(int schoolId) throws ServiceException;

    /**
     * Update by school.
     *
     * @param schoolId the school id
     * @param registeredClasses the registered classes
     * @throws ServiceException the service exception
     */
    void updateBySchool(int schoolId, List<RegisteredClassDto> registeredClasses) throws ServiceException;

    /**
     * Gets the all.
     * 
     * @return the operating classes
     * @throws ServiceException the service exception
     */
    List<RegisteredClassDto> getAll() throws ServiceException;

    /**
     * Gets the.
     * 
     * @param classId the class id
     * @return the operating class
     * @throws ServiceException the service exception
     */
    RegisteredClassDto get(int classId) throws ServiceException;

    /**
     * Creates the.
     * 
     * @param registeredClassDto the registered class dto
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean create(RegisteredClassDto registeredClassDto) throws ServiceException;

    /**
     * Update.
     * 
     * @param registeredClassId the registered class id
     * @param registeredClassDto the registered class dto
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean update(int registeredClassId, RegisteredClassDto registeredClassDto) throws ServiceException;

    /**
     * Delete.
     * 
     * @param registeredClassId the registered class id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean delete(int registeredClassId) throws ServiceException;

}
