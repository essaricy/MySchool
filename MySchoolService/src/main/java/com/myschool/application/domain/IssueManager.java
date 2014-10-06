package com.myschool.application.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.assembler.StatisticsDataAssembler;
import com.myschool.application.constants.IssueStatus;
import com.myschool.application.dao.IssueDao;
import com.myschool.application.dto.DateValueDto;
import com.myschool.application.dto.IssueDto;
import com.myschool.application.dto.IssueSearchCriteriaDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.InvalidDataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.util.ConversionUtil;
import com.myschool.graph.constant.ToDateType;
import com.myschool.infra.middleware.agent.MiddlewareAgent;
import com.myschool.user.constants.UserType;

/**
 * The Class IssueManager.
 */
@Component
public class IssueManager {

    /** The issue dao. */
    @Autowired
    private IssueDao issueDao;

    /** The middleware agent. */
    @Autowired
    private MiddlewareAgent middlewareAgent;

    /** The profile manager. */
    @Autowired
    private ProfileManager profileManager;

    /** The issue validator. */
    @Autowired
    private IssueValidator issueValidator;

    /**
     * Creates the.
     *
     * @param issue the issue
     * @return the int
     * @throws DataException the data exception
     */
    public int create(IssueDto issue) throws DataException {
        int issueId = 0;
        try {
            issueValidator.validate(issue);
            issue.setIssueStatus(IssueStatus.OPEN);
            issue.setClosedDate(null);
            issueId = issueDao.create(issue);
            // send an email to one who raised issue and to admin if issue is created
            if (issueId > 0 ) {
                /*issue.setIssueId(issueId);
                MessageDto messageDto = null;//MessageDataAssembler.createMessage(organizationProfile, issue, cacheAgent.getMessage(ApplicationProperties.MAIL_FROM_SUPPORT));
                middlewareAgent.sendMessage(messageDto);*/
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }/* catch (MessageException messageException) {
            throw new DataException(messageException.getMessage(), messageException);
        }*/ catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
        return issueId;
    }

    /**
     * Gets the all.
     * 
     * @param issueSearchCriteria the issue search criteria
     * @return the all
     * @throws DataException the data exception
     */
    public List<IssueDto> getAll(IssueSearchCriteriaDto issueSearchCriteria) throws DataException {
        List<IssueDto> issues = null;
        try {
            if (issueSearchCriteria != null) {
                issueSearchCriteria.setReportedDateMin(ConversionUtil.toStorageDateFromApplicationDate(issueSearchCriteria.getReportedDateMin()));
                issueSearchCriteria.setReportedDateMax(ConversionUtil.toStorageDateFromApplicationDate(issueSearchCriteria.getReportedDateMax()));
                issueSearchCriteria.setClosedDateMin(ConversionUtil.toStorageDateFromApplicationDate(issueSearchCriteria.getClosedDateMin()));
                issueSearchCriteria.setClosedDateMax(ConversionUtil.toStorageDateFromApplicationDate(issueSearchCriteria.getClosedDateMax()));
            }
            issues = issueDao.getAll(issueSearchCriteria);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return issues;
    }

    /**
     * Gets the.
     *
     * @param issueId the issue id
     * @return the issue dto
     * @throws DataException the data exception
     */
    public IssueDto get(int issueId) throws DataException {
        IssueDto issue = null;
        try {
            issue = issueDao.get(issueId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return issue;
    }

    /**
     * Update.
     *
     * @param issueId the issue id
     * @param issue the issue
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int issueId, IssueDto issue) throws DataException {
        boolean updated = false;
        try {
            // Validate the issue
            if (issue == null) {
                throw new InvalidDataException("Issue details are not present.");
            }
            IssueStatus issueStatus = issue.getIssueStatus();
            if (issueStatus == IssueStatus.CLOSED) {
                throw new InvalidDataException("Issue with status " + IssueStatus.CLOSED + " cannot be updated.");
            }
            updated = issueDao.update(issueId, issue);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return updated;
    }

    /**
     * Gets the issues to date.
     * 
     * @param toDateType the to date type
     * @return the issues to date
     * @throws DataException the data exception
     */
    public Map<UserType, List<DateValueDto>> getIssuesToDate(
            ToDateType toDateType) throws DataException {
        List<DateValueDto> dateValues = null;
        Map<UserType, List<DateValueDto>> map = new HashMap<UserType, List<DateValueDto>>();
        try {
            // get the type of users.
            // for each user get the statistics. 
            for (UserType userType : UserType.values()) {
                dateValues = issueDao.getIssuesToDate(userType, toDateType);
                if (dateValues == null) {
                    dateValues = new ArrayList<DateValueDto>();
                }
                StatisticsDataAssembler.fillEmptyDates(dateValues, toDateType);
                map.put(userType, dateValues);
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return map;
    }

}
