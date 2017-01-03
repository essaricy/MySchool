package com.myschool.web.user.controller;

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

import com.myschool.application.assembler.StatisticsDataAssembler;
import com.myschool.application.dto.NumberNameValueDto;
import com.myschool.graph.assembler.ChartDataAssembler;
import com.myschool.graph.dto.LineChartDto;
import com.myschool.user.constants.UserType;
import com.myschool.user.dto.UsageCount;
import com.myschool.user.dto.UserSession;
import com.myschool.user.dto.UserStatistics;
import com.myschool.user.dto.UsersDto;
import com.myschool.user.service.UsageService;
import com.myschool.user.service.UserService;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.util.HttpUtil;
import com.myschool.web.user.constants.UserViewNames;
import com.quasar.core.util.ConversionUtil;
import com.quasar.core.util.StringUtil;

/**
 * The Class UserController.
 */
@Controller
@RequestMapping("usage")
public class UsageController {

	/** The usage service. */
	@Autowired
	private UsageService usageService;

	/** The user service. */
	@Autowired
	private UserService userService;

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
        return ViewDelegationController.delegateWholePageView(request, UserViewNames.VIEW_USAGE_STATISTICS);
    }

    /**
     * Gets the active sessions.
     *
     * @param request the request
     * @param response the response
     * @return the active sessions
     * @throws Exception the exception
     */
    @RequestMapping(value="getActiveSessions")
    public ModelAndView getActiveSessions(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        JSONArray data = null;
        try {
            data = new JSONArray();
            List<UserSession> userSessions = usageService.getActiveSessions();

            if (userSessions != null) {
                for(UserSession userSession : userSessions) {
                	int userId = userSession.getUserId();

                	JSONArray row = new JSONArray();
                	row.put(userSession.getSessionId());
                	if (userId != 0) {
                		UsersDto userDto = userService.get(userId);
                		if (userDto != null) {
                			row.put(userDto.getUserType().toString());
                			row.put(userDto.getId());
                			row.put(userDto.getRefUserId());
                			row.put(userDto.getUserName());
                			row.put(userDto.getDisplayName());
                		} else {
                			row.put("N/A");
                			row.put(" ");
                			row.put(" ");
                			row.put(" ");
                			row.put(" ");
                		}
                	} else {
            			row.put("N/A");
            			row.put(" ");
            			row.put(" ");
            			row.put(" ");
            			row.put(" ");
            		}
                	row.put(ConversionUtil.toApplicationTime(userSession.getSessionStartTime()));
                	row.put(userSession.getIpAddress());
                	row.put(userSession.getDeviceInformation());
                	row.put(userSession.getBrowserInformation());
                	data.put(row);
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
        return null;
    }

    /**
     * Gets the usage count.
     *
     * @param request the request
     * @param response the response
     * @return the usage count
     * @throws Exception the exception
     */
    @RequestMapping(value="getUsageCount")
    public ModelAndView getUsageCount(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        JSONArray data = null;
        try {
            data = new JSONArray();
            List<UsageCount> usageCounts = usageService.getUsageCount();

            if (usageCounts != null) {
                for(UsageCount usageCount : usageCounts) {
                	UsersDto user = usageCount.getUser();
                	UserStatistics userStatistics = usageCount.getUserStatistics();

                	JSONArray row = new JSONArray();
                	row.put(user.getId());
                	row.put(user.getUserType().toString());
                	row.put(user.getUserName());
                	row.put(userStatistics.getNumberOfVisits());
                	row.put(userStatistics.getLastVisitOn());
                	data.put(row);
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
        return null;
    }

    @RequestMapping(value="getLoginsTrend")
    public ModelAndView getLoginsTrend(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	JSONObject data = null;
    	LineChartDto lineChart = null;
    	try {
    		String trendType = request.getParameter("TrendType");
    		if (!StringUtil.isNullOrBlank(trendType)) {
    			Map<UserType, List<NumberNameValueDto>> loginsByMonthByUserType = usageService.getLoginsTrend(trendType);
    			lineChart = StatisticsDataAssembler.create(loginsByMonthByUserType);
    		}
    		data = ChartDataAssembler.create(lineChart);
    	} finally {
    		HttpUtil.wrapAndWriteJson(response, WebConstants.CHART_DATA, data);
    	}
    	return null;
    }

    /*@RequestMapping(value="getLoginsMonthWise")
    public ModelAndView getLoginsMonthWise(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	JSONObject data = null;
    	try {
    		Map<UserType, List<MonthValueDto>> loginsByMonthByUserType = usageService.getLoginsMonthWise();
    		LineChartDto lineChart = StatisticsDataAssembler.createChartForMonthValue(loginsByMonthByUserType);
    		data = ChartDataAssembler.create(lineChart);
    	} finally {
    		HttpUtil.wrapAndWriteJson(response, WebConstants.CHART_DATA, data);
    	}
    	return null;
    }*/

}