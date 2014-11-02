package com.myschool.web.application.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.web.common.util.HttpUtil;

/**
 * The Class AnnouncementController.
 */
@Controller
@RequestMapping("announcement")
public class AnnouncementController {

    /**
     * Json public announcements.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonPublicAnnouncements")
    public ModelAndView jsonPublicAnnouncements(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONArray data = new JSONArray();
        try {
            data.put("[Public] New Branch has been created.");
            data.put("[Public] Schools in Hyderabad will be closed on Monday.");
            data.put("[Public] Science Exhibition to be held in bangalore in December.");
            data.put("[Public] Third quarterly exams to begin from December 10th.");
            data.put("[Public] Employees submit their pending leave requests by this weekend.");
            data.put("[Public] MySchool selected as the school of the award for the year 2013.");
        } finally {
            HttpUtil.writeJson(response, data);
        }
        return null;
    }

}
