package com.myschool.web.application.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.application.service.ImageService;
import com.myschool.common.util.StringUtil;
import com.myschool.infra.image.constants.ImageSize;
import com.myschool.web.application.constants.ApplicationViewNames;
import com.myschool.web.common.util.HttpUtil;
import com.myschool.web.common.util.ViewDelegationController;

/**
 * The Class ImageController.
 */
@Controller
@RequestMapping("image")
public class ImageController {

    /** The image service. */
    @Autowired
    private ImageService imageService;

    /**
     * Slideshow.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="slideshow")
    public ModelAndView slideshow(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        List<String> galleryItemNames = null;
        Map<String, Object> map = new HashMap<String, Object>();
        String galleryName = request.getParameter("GalleryName");
        if (!StringUtil.isNullOrBlank(galleryName)) {
            galleryItemNames = imageService.getGalleryItemNames(galleryName);
        }
        map.put("GalleryName", galleryName);
        map.put("GalleryItemNames", galleryItemNames);

        String selectedItem = request.getParameter("Selection");
        if (StringUtil.isNullOrBlank(selectedItem)) {
            map.put("Selection", 0);
        } else {
            map.put("Selection", selectedItem);
        }
        return ViewDelegationController.delegateModelPageView(request, ApplicationViewNames.VIEW_SLIDE_SHOW, map);
    }

    /**
     * Gets the image.
     *
     * @param request the request
     * @param response the response
     * @return the image
     * @throws Exception the exception
     */
    @RequestMapping(value="getImage")
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
            } else /*if (type.equals("no-image")) {
                // First try to get the image from the File server.
                file = imageService.getNoImage();
            } else*/ if (type.equals("student")) {
                file = imageService.getStudentImage(contentId, ImageSize.getImageType(imageSize));
            } else if (type.equals("employee")) {
                file = imageService.getEmployeeImage(contentId, ImageSize.getImageType(imageSize));
            } else if (type.equals("org")) {
                file = imageService.getOrgImage(contentId, ImageSize.getImageType(imageSize));
            } else if (type.equals("gallery")) {
                if (contentId.contains("?")) {
                    contentId= contentId.substring(0, contentId.indexOf("?"));
                }
                file = imageService.getGalleryItem(contentId, ImageSize.getImageType(imageSize));
            }
            if (file == null) {
                file = imageService.getNoImage();
            }
            System.out.println("Image Type = " + type + ", contentId = " + contentId);
            if (file != null) {
                HttpUtil.writeToResponse(response, file);
            }
        }
        return null;
    }

}
