package com.myschool.web.user.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.application.assembler.GalleryDataAssembler;
import com.myschool.application.service.ImageService;
import com.myschool.application.service.ProfileService;
import com.myschool.common.util.StringUtil;
import com.myschool.infra.web.constants.MimeTypes;
import com.myschool.web.application.constants.ApplicationViewNames;
import com.myschool.web.common.util.ViewDelegationController;

/**
 * The Class NoticeBoardController.
 */
@Controller
@RequestMapping("noticeBoard")
public class NoticeBoardController {

    /** The profile service. */
    @Autowired
    private ProfileService profileService;

    /** The gallery file system. */
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
     */
    @RequestMapping(value="gallery")
    public ModelAndView gallery(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return new ModelAndView(ApplicationViewNames.VIEW_GALLERY);
    }

    /**
     * Json gallery names.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonGalleryNames")
    public ModelAndView jsonGalleryNames(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONObject jsonResponse = new JSONObject();
        List<String> galleryNames = imageService.getGalleryNames();
        jsonResponse.put("GalleryNames", GalleryDataAssembler.create(galleryNames));
        response.setContentType(MimeTypes.APPLICATION_JSON);
        PrintWriter writer = response.getWriter();
        writer.print(jsonResponse.toString());
        writer.close();
        return null;
    }

    /**
     * Gets the latest gallery name.
     * 
     * @param request the request
     * @param response the response
     * @return the latest gallery name
     * @throws Exception the exception
     */
    @RequestMapping(value="getLatestGalleryName")
    public ModelAndView getLatestGalleryName(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONObject jsonResponse = new JSONObject();
        String galleryName = imageService.getLatestGalleryName();
        jsonResponse.put("GalleryName", galleryName);
        response.setContentType(MimeTypes.APPLICATION_JSON);
        PrintWriter writer = response.getWriter();
        writer.print(jsonResponse.toString());
        writer.close();
        return null;
    }

    /**
     * Json gallery item names.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonGalleryItemNames")
    public ModelAndView jsonGalleryItemNames(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONArray jsonArray = null;
        JSONObject jsonResponse = new JSONObject();
        String galleryName = request.getParameter("GalleryName");
        if (!StringUtil.isNullOrBlank(galleryName)) {
            List<String> galleryNames = imageService.getGalleryItemNames(galleryName);
            jsonArray = GalleryDataAssembler.create(galleryNames);
        }
        jsonResponse.put("GalleryItemNames", jsonArray);
        response.setContentType(MimeTypes.APPLICATION_JSON);
        PrintWriter writer = response.getWriter();
        writer.print(jsonResponse.toString());
        writer.close();
        return null;
    }

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
