package com.myschool.web.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.common.util.ConversionUtil;
import com.myschool.user.dto.UserSession;
import com.myschool.user.dto.UsersDto;
import com.myschool.user.service.UsageService;
import com.myschool.user.service.UserService;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.util.HttpUtil;
import com.myschool.web.user.constants.UserViewNames;

/**
 * The Class UserController.
 */
@Controller
@RequestMapping("usage")
public class UsageController {

	@Autowired
	private UsageService usageService;

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

    @RequestMapping(value="getActiveSessions")
    public ModelAndView getActiveSessions(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        JSONArray data = null;
        try {
        	System.out.println("getActiveSessions()");
            data = new JSONArray();
            List<UserSession> userSessions = usageService.getActiveSessions();
            System.out.println("#### userSessions " + userSessions);

            if (userSessions != null) {
            	System.out.println("Found " + userSessions.size() + " active sessions.");
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

    // TODO complete this method.
    @RequestMapping(value="getActiveSessionsByUserType")
    public ModelAndView getActiveSessionsByUserType(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        JSONArray data = null;
        try {
        	System.out.println("getActiveSessions()");
            data = new JSONArray();
            List<UserSession> userSessions = usageService.getActiveSessions();
            System.out.println("#### userSessions " + userSessions);

            if (userSessions != null) {
            	System.out.println("Found " + userSessions.size() + " active sessions.");
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

}