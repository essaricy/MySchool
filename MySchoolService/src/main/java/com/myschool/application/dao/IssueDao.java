package com.myschool.application.dao;

import java.util.List;

import com.myschool.application.dto.DateValueDto;
import com.myschool.application.dto.IssueDto;
import com.myschool.application.dto.IssueSearchCriteriaDto;
import com.myschool.common.exception.DaoException;
import com.myschool.graph.constant.ToDateType;
import com.myschool.user.constants.UserType;

/**
 * The Interface IssueDao.
 */
public interface IssueDao {

    /**
     * Creates the.
     *
     * @param issue the issue
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(IssueDto issue) throws DaoException;

    /**
     * Gets the all.
     * 
     * @param issueSearchCriteria the issue search criteria
     * @return the all
     * @throws DaoException the dao exception
     */
    List<IssueDto> getAll(IssueSearchCriteriaDto issueSearchCriteria) throws DaoException;

    /**
     * Gets the.
     *
     * @param issueId the issue id
     * @return the issue dto
     * @throws DaoException the dao exception
     */
    IssueDto get(int issueId) throws DaoException;

    /**
     * Update.
     *
     * @param issueId the issue id
     * @param issue the issue
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int issueId, IssueDto issue) throws DaoException;

    /**
     * Gets the issues to date.
     * 
     * @param userType the user type
     * @param toDateType the to date type
     * @return the issues to date
     * @throws DaoException the dao exception
     */
    List<DateValueDto> getIssuesToDate(UserType userType, ToDateType toDateType) throws DaoException;

}
