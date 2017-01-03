package com.myschool.web.application.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.application.dto.GalleryDetailDto;
import com.myschool.application.service.GalleryService;
import com.myschool.employee.service.EmployeeService;
import com.myschool.image.constant.ImageSize;
import com.myschool.student.service.StudentService;
import com.myschool.web.application.constants.ApplicationViewNames;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.util.HttpUtil;
import com.quasar.core.util.StringUtil;

/**
 * The Class ImageController.
 */
@Controller
@RequestMapping("image")
public class ImageController {

    /** The gallery service. */
    @Autowired
    private GalleryService galleryService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private StudentService studentService;

    /**
     * Slideshow.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value = "slideshow")
    public ModelAndView slideshow(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String galleryName = request.getParameter("GalleryName");
        if (!StringUtil.isNullOrBlank(galleryName)) {
            GalleryDetailDto galleryDetail = galleryService.get(galleryName);
            if (galleryDetail != null) {
                map.put("GalleryName", galleryName);
                map.put("GalleryItems", galleryDetail.getGalleryItems());
            }
        }
        String selectedItem = request.getParameter("Selection");
        if (StringUtil.isNullOrBlank(selectedItem)) {
            map.put("Selection", 0);
        } else {
            map.put("Selection", selectedItem);
        }
        return ViewDelegationController.delegateModelPageView(request,
                ApplicationViewNames.VIEW_SLIDE_SHOW, map);
    }

    @RequestMapping(value = "getEvanescentImage")
    public ModelAndView getEvanescentImage(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        File file = null;
        String type = request.getParameter("type");
        String contentId = request.getParameter("contentId");
        String imageSize = request.getParameter("imageSize");

        if (type != null) {
            if (type.equalsIgnoreCase("employee")) {
                file = employeeService.getEvanescentImage(contentId, ImageSize.getImageType(imageSize));
            } else if (type.equalsIgnoreCase("student")) {
                file = studentService.getEvanescentImage(contentId, ImageSize.getImageType(imageSize));
            }
            System.out.println("file=" + file);
            if (file != null) {
                HttpUtil.writeToResponse(response, file);
            }
        }
        return null;
    }

    /**
     * Gets the image.
     * 
     * @param request the request
     * @param response the response
     * @return the image
     * @throws Exception the exception
     *//*
    @RequestMapping(value = "getImage")
    public ModelAndView getImage(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        File file = null;

        String type = request.getParameter("type");
        String contentId = request.getParameter("contentId");
        String imageSize = request.getParameter("imageSize");

        if (type != null) {
            if (type.equals("logo")) {
                // First try to get the image from the File server.
                file = imageService.getLogo();
            } else if (type.equals("no-image")) {
                // First try to get the image from the File server.
                file = imageService.getNoImage();
            } else if (type.equals("student")) {
                file = imageService.getStudentImage(contentId,
                        ImageSize.getImageType(imageSize));
            } else if (type.equals("employee")) {
                file = imageService.getEmployeeImage(contentId,
                        ImageSize.getImageType(imageSize));
            } else if (type.equals("org")) {
                file = imageService.getOrgImage(contentId,
                        ImageSize.getImageType(imageSize));
            }
            if (file == null) {
                file = imageService.getNoImage();
            }
            if (file != null) {
                HttpUtil.writeToResponse(response, file);
            }
        }
        return null;
    }*/

}
