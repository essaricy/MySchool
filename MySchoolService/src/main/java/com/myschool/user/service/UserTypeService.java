package com.myschool.user.service;

import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.common.exception.ServiceException;
import com.myschool.user.dto.UserTypeDto;
import com.myschool.user.dto.UsersDto;

public interface UserTypeService extends Servicable<UserTypeDto> {

    /**
     * Gets the users.
     *
     * @param parseInt the parse int
     * @return the users
     * @throws ServiceException the service exception
     */
    List<UsersDto> getUsers(int parseInt) throws ServiceException;

}
