package com.myschool.web.user.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.application.assembler.StatisticsDataAssembler;
import com.myschool.application.dto.DateValueDto;
import com.myschool.application.dto.NumberNameValueDto;
import com.myschool.application.service.IssueService;
import com.myschool.graph.assembler.ChartDataAssembler;
import com.myschool.graph.constant.ToDateType;
import com.myschool.graph.dto.LineChartDto;
import com.myschool.user.constants.UserType;
import com.myschool.user.service.UserService;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.framework.util.HttpUtil;

/**
 * The Class AdminDashboardController.
 */
@Controller
@RequestMapping("admin-dashboard")
public class AdminDashboardController {

    /** The Constant USAGE_BY_USER_TYPE. */
    private static final String USAGE_BY_USER_TYPE = "USAGE_BY_USER_TYPE";

    /** The Constant ISSUES_BY_USER_TYPE. */
    private static final String ISSUES_BY_USER_TYPE = "ISSUES_BY_USER_TYPE";

    /** The user service. */
    @Autowired
    private UserService userService;

    /** The issue service. */
    @Autowired
    private IssueService issueService;

    /**
     * Gets the logins to date.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="getLoginsToDate")
    public ModelAndView getLoginsToDate(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        JSONObject data = null;
        try {
            String keyInSession = null;
            HttpSession session = HttpUtil.getExistingSession(request);

            ToDateType toDateType = ToDateType.get(request.getParameter("ToDateType"));
            if (toDateType != null) {
                keyInSession = USAGE_BY_USER_TYPE + toDateType;

                data = (JSONObject) session.getAttribute(keyInSession);
                if (data == null) {
                    Map<UserType, List<DateValueDto>> loginsToDateByUserType = userService.getLoginsToDate(toDateType);
					Map<UserType, List<NumberNameValueDto>> nameNumberValuesByUserTypeMap = 
							StatisticsDataAssembler.getNameNumberValuesByUserTypeMap(loginsToDateByUserType);
                    LineChartDto lineChart = StatisticsDataAssembler.create(nameNumberValuesByUserTypeMap);
                    data = ChartDataAssembler.create(lineChart);
                    session.setAttribute(keyInSession, data);
                }
            }
        } finally {
            HttpUtil.wrapAndWriteJson(response, WebConstants.CHART_DATA, data);
        }
        return null;
    }

    /**
     * Gets the issues to date.
     * 
     * @param request the request
     * @param response the response
     * @return the issues to date
     * @throws Exception the exception
     */
    @RequestMapping(value="getIssuesToDate")
    public ModelAndView getIssuesToDate(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String keyInSession = null;
        JSONObject data = null;
        try {
            HttpSession session = HttpUtil.getExistingSession(request);
            ToDateType toDateType = ToDateType.get(request.getParameter("ToDateType"));
            if (toDateType != null) {
                keyInSession = ISSUES_BY_USER_TYPE + toDateType;
                data = (JSONObject) session.getAttribute(keyInSession);
                if (data == null) {
                    Map<UserType, List<DateValueDto>> issuesToDateByUserType = issueService.getIssuesToDate(toDateType);
                    Map<UserType, List<NumberNameValueDto>> nameNumberValuesByUserTypeMap = 
							StatisticsDataAssembler.getNameNumberValuesByUserTypeMap(issuesToDateByUserType);
                    LineChartDto lineChart = StatisticsDataAssembler.create(nameNumberValuesByUserTypeMap);
                    data = ChartDataAssembler.create(lineChart);
                    session.setAttribute(keyInSession, data);
                }
            }
        } finally {
            HttpUtil.wrapAndWriteJson(response, WebConstants.CHART_DATA, data);
        }
        return null;
    }

}
