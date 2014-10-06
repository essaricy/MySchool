package com.myschool.clazz.service;

import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.clazz.dto.RegisteredSubjectDto;
import com.myschool.common.exception.ServiceException;

/**
 * The Interface RegisteredSubjectService.
 */
public interface RegisteredSubjectService extends Servicable<RegisteredSubjectDto> {

    /**
     * Gets the by class.
     *
     * @param classId the class id
     * @return the by class
     * @throws ServiceException the service exception
     */
    List<RegisteredSubjectDto> getByClass(int classId) throws ServiceException;

}
