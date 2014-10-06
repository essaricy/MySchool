package com.myschool.web.user.controller;

import java.io.PrintWriter;
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
import com.myschool.application.service.IssueService;
import com.myschool.graph.assembler.ChartDataAssembler;
import com.myschool.graph.constant.ToDateType;
import com.myschool.graph.dto.LineChartDto;
import com.myschool.infra.web.constants.MimeTypes;
import com.myschool.user.constants.UserType;
import com.myschool.user.service.UserService;

/**
 * The Class AdminDashboardController.
 */
@Controller
@RequestMapping("admin-dashboard")
public class AdminDashboardController {

    /** The Constant CHART_DATA. */
    private static final String CHART_DATA = "CHART_DATA";

    /** The Constant USAGE_BY_USER_TYPE. */
    private static final String USAGE_BY_USER_TYPE = "USAGE_BY_USER_TYPE";

    /** The Constant ISSUES_BY_USER_TYPE. */
    private static final String ISSUES_BY_USER_TYPE = "ISSUES_BY_USER_TYPE";

    /** The user service. */
    @Autowired
    private UserService userService;

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

        String keyInSession = null;
        JSONObject jsonResponse = new JSONObject();
        HttpSession session = request.getSession();

        ToDateType toDateType = ToDateType.get(request.getParameter("ToDateType"));
        if (toDateType != null) {
            keyInSession = USAGE_BY_USER_TYPE + toDateType;

            JSONObject jsonObject = (JSONObject) session.getAttribute(keyInSession);
            if (jsonObject == null) {
                Map<UserType, List<DateValueDto>> loginsToDateByUserType = userService.getLoginsToDate(toDateType);
                LineChartDto lineChart = StatisticsDataAssembler.create(loginsToDateByUserType);
                jsonObject = ChartDataAssembler.create(lineChart);
                session.setAttribute(keyInSession, jsonObject);
            }

            jsonResponse.put(CHART_DATA, jsonObject);
            response.setContentType(MimeTypes.APPLICATION_JSON);
            PrintWriter writer = response.getWriter();
            writer.print(jsonResponse.toString());
            writer.close();
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
        JSONObject jsonResponse = new JSONObject();
        HttpSession session = request.getSession();

        ToDateType toDateType = ToDateType.get(request.getParameter("ToDateType"));
        if (toDateType != null) {
            keyInSession = ISSUES_BY_USER_TYPE + toDateType;

            JSONObject jsonObject = (JSONObject) session.getAttribute(keyInSession);
            if (jsonObject == null) {
                Map<UserType, List<DateValueDto>> issuesToDateByUserType = issueService.getIssuesToDate(toDateType);
                LineChartDto lineChart = StatisticsDataAssembler.create(issuesToDateByUserType);
                jsonObject = ChartDataAssembler.create(lineChart);
                session.setAttribute(keyInSession, jsonObject);
            }

            jsonResponse.put(CHART_DATA, jsonObject);
            response.setContentType(MimeTypes.APPLICATION_JSON);
            PrintWriter writer = response.getWriter();
            writer.print(jsonResponse.toString());
            writer.close();
        }
        return null;
    }

}
