package com.myschool.web.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.application.service.ImageService;
import com.myschool.application.service.ProfileService;
import com.myschool.web.application.constants.ApplicationViewNames;
import com.myschool.web.framework.controller.ViewDelegationController;

/**
 * The Class NoticeBoardController.
 */
@Controller
@RequestMapping("noticeBoard")
public class NoticeBoardController {

    /** The profile service. */
    @Autowired
    private ProfileService profileService;

    /** The image service. */
    @Autowired
    private ImageService imageService;

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
        return ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.VIEW_NOTICE_BOARD);
    }

    /**
     * Gallery.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     *//*
    @RequestMapping(value="gallery")
    public ModelAndView gallery(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return new ModelAndView(ApplicationViewNames.VIEW_GALLERY);
    }*/

    /**
     * Director.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="director")
    public ModelAndView director(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.VIEW_ABOUT_DIRECTOR);
    }

    /**
     * organization.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="organization")
    public ModelAndView organization(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.VIEW_ABOUT_ORGANIZATION);
    }

    /**
     * Achievements.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="achievements")
    public ModelAndView achievements(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.VIEW_ACHIEVEMENTS);
    }

    /**
     * Upcoming events.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="upcomingEvents")
    public ModelAndView upcomingEvents(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.VIEW_UPCOMING_EVENTS);
    }

    /**
     * Upcoming exams.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="upcomingExams")
    public ModelAndView upcomingExams(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.VIEW_UPCOMING_EXAMS);
    }

}
