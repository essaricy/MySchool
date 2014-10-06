package com.myschool.application.service;

import java.util.List;

import com.myschool.common.dto.DocumentDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.user.constants.UserType;

/**
 * The Interface DocumentService.
 */
public interface DocumentService extends Servicable<DocumentDto> {

    List<DocumentDto> getAll(UserType userType) throws ServiceException;

}
