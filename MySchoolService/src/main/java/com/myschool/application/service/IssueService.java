package com.myschool.application.service;

import java.util.List;
import java.util.Map;

import com.myschool.application.dto.DateValueDto;
import com.myschool.application.dto.IssueDto;
import com.myschool.application.dto.IssueSearchCriteriaDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.graph.constant.ToDateType;
import com.myschool.user.constants.UserType;

/**
 * The Interface IssueService.
 */
public interface IssueService extends Servicable<IssueDto>{

    /**
     * Gets the all.
     * 
     * @param issueSearchCriteria the issue search criteria
     * @return the all
     * @throws ServiceException the service exception
     */
    List<IssueDto> getAll(IssueSearchCriteriaDto issueSearchCriteria) throws ServiceException;

    /**
     * Gets the issues to date.
     * 
     * @param toDateType the to date type
     * @return the issues to date
     * @throws ServiceException the service exception
     */
    Map<UserType, List<DateValueDto>> getIssuesToDate(ToDateType toDateType) throws ServiceException;

}
