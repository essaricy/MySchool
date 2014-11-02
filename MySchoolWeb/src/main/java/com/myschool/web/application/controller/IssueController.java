package com.myschool.web.application.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.application.assembler.IssueDataAssembler;
import com.myschool.application.constants.IssueStatus;
import com.myschool.application.dto.IssueDto;
import com.myschool.application.dto.IssueSearchCriteriaDto;
import com.myschool.application.service.IssueService;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.web.application.constants.ApplicationViewNames;
import com.myschool.web.common.util.HttpUtil;
import com.myschool.web.common.util.JCaptchaUtil;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.common.util.ViewErrorHandler;

/**
 * The Class IssueController.
 */
@Controller
@RequestMapping("issue")
public class IssueController {

    /** The issue service. */
    @Autowired
    private IssueService issueService;

    /** The view error handler. */
    @Autowired
    private ViewErrorHandler viewErrorHandler;

    /**
     * List.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="list")
    public ModelAndView list(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.VIEW_ISSUES);
    }

    /**
     * Json open issues.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonOpenIssues")
    private ModelAndView jsonOpenIssues(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        IssueSearchCriteriaDto issueSearchCriteria = new IssueSearchCriteriaDto();
        issueSearchCriteria.setIssueStatus(IssueStatus.OPEN);
        searchIssues(response, issueSearchCriteria);
        return null;
    }

    /**
     * Search issues.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="searchIssues")
    private ModelAndView searchIssues(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        IssueSearchCriteriaDto issueSearchCriteria = null;
        String issueSearchCriteriaValue = request.getParameter("IssueSearchCriteria");
        if (!StringUtil.isNullOrBlank(issueSearchCriteriaValue)) {
            JSONObject issueSearchCriteriaJSON = new JSONObject(issueSearchCriteriaValue);
            issueSearchCriteria = IssueDataAssembler.createIssueSearchCriteria(issueSearchCriteriaJSON);
            searchIssues(response, issueSearchCriteria);
        }
        return null;
    }

    /**
     * Do update.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doUpdate")
    public ModelAndView doUpdate(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ResultDto result = new ResultDto();

        try {
            String issueId = request.getParameter("issueId");
            String issueDataValue = request.getParameter("IssueData");
            if (!StringUtil.isNullOrBlank(issueDataValue) && !StringUtil.isNullOrBlank(issueId)) {
                IssueDto issue = IssueDataAssembler.create(new JSONObject(issueDataValue));
                result.setSuccessful(issueService.update(Integer.parseInt(issueId), issue));
            }
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Launch issue.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="launchIssue")
    public ModelAndView launchIssue(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String issueId = request.getParameter("IssueId");
        Map<String, Object> map = new HashMap<String, Object>();
        if (!StringUtil.isNullOrBlank(issueId)) {
            map.put("issue", issueService.get(Integer.parseInt(issueId)));
        }
        return ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.REPORT_AN_ISSUE, map);
    }

    /**
     * Do create.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doCreate")
    public ModelAndView doCreate(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ResultDto result = new ResultDto();
        try {
            String issueDataValue = request.getParameter("IssueData");
            if (!StringUtil.isNullOrBlank(issueDataValue)) {
                JSONObject issueJsonObject = new JSONObject(issueDataValue);
                String userCaptchaResponse = issueJsonObject.getString("Captcha_UserFeed");
                JCaptchaUtil.validateCaptcha(request, userCaptchaResponse);
                IssueDto issue = IssueDataAssembler.create(issueJsonObject);
                result.setSuccessful(issueService.create(issue));
            }
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Launch update.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="launchUpdate")
    public ModelAndView launchUpdate(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String issueId = request.getParameter("issueId");
        if (!StringUtil.isNullOrBlank(issueId)) {
            IssueDto issue = issueService.get(Integer.parseInt(issueId));
            map.put("Issue", issue);
        }
        return ViewDelegationController.delegateModelPageView(request, ApplicationViewNames.UPDATE_ISSUE, map);
    }

    /**
     * Search issues.
     * 
     * @param response the response
     * @param issueSearchCriteria the issue search criteria
     * @throws Exception the exception
     */
    private void searchIssues(HttpServletResponse response,
            IssueSearchCriteriaDto issueSearchCriteria) throws Exception {
        JSONArray data = new JSONArray();
        try {
            if (issueSearchCriteria != null) {
                List<IssueDto> issues = issueService.getAll(issueSearchCriteria);
                if (issues != null && !issues.isEmpty()) {
                    for (IssueDto issue : issues) {
                        data.put(IssueDataAssembler.create(issue));
                    }
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
    }

}
