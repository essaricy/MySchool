package com.myschool.application.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.application.domain.IssueManager;
import com.myschool.application.dto.DateValueDto;
import com.myschool.application.dto.IssueDto;
import com.myschool.application.dto.IssueSearchCriteriaDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.graph.constant.ToDateType;
import com.myschool.user.constants.UserType;
import com.quasar.core.exception.DataException;

/**
 * The Class IssueServiceImpl.
 */
@Service
public class IssueServiceImpl implements IssueService {

    /** The issue manager. */
    @Autowired
    private IssueManager issueManager;

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(IssueDto issue) throws ServiceException {
        try {
            return issueManager.create(issue) > 0;
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#getAll()
     */
    @Override
    public List<IssueDto> getAll() throws ServiceException {
        try {
            return issueManager.getAll(null);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#get(int)
     */
    @Override
    public IssueDto get(int issueId) throws ServiceException {
        try {
            return issueManager.get(issueId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int issueId, IssueDto issue) throws ServiceException {
        try {
            return issueManager.update(issueId, issue);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#delete(int)
     */
    @Override
    public boolean delete(int issueId) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.IssueService#getAll(com.myschool.application.dto.IssueSearchCriteriaDto)
     */
    @Override
    public List<IssueDto> getAll(IssueSearchCriteriaDto issueSearchCriteria)
            throws ServiceException {
        try {
            return issueManager.getAll(issueSearchCriteria);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.IssueService#getIssuesToDate(com.myschool.graph.constant.ToDateType)
     */
    @Override
    public Map<UserType, List<DateValueDto>> getIssuesToDate(
            ToDateType toDateType) throws ServiceException {
        try {
            return issueManager.getIssuesToDate(toDateType);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

}
