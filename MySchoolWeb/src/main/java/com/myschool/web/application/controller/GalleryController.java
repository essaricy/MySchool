package com.myschool.web.application.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.application.assembler.GalleryDataAssembler;
import com.myschool.application.dto.GalleryDetailDto;
import com.myschool.application.service.GalleryService;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.InsufficientInputException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.infra.filesystem.agent.TempFileSystem;
import com.myschool.web.application.constants.ApplicationViewNames;
import com.myschool.web.exim.bean.UploadDataFileBean;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.util.HttpUtil;

/**
 * The Class GalleryController.
 */
@Controller
@RequestMapping("gallery")
public class GalleryController {

    /** The gallery service. */
    @Autowired
    private GalleryService galleryService;

    /** The temp file system. */
    @Autowired
    private TempFileSystem tempFileSystem;

	/**
	 * Launch gallery.
	 *
	 * @param request the request
	 * @param response the response
	 * @return the model and view
	 * @throws Exception the exception
	 */
    @RequestMapping(value="launchGallery")
    public ModelAndView launchGallery(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return new ModelAndView(ApplicationViewNames.VIEW_GALLERY);
    }

    /**
     * Launch manage gallery.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="launchManageGallery")
    public ModelAndView launchManageGallery(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateModelPageView(request, ApplicationViewNames.MANAGE_GALLERY);
    }

    /**
     * Json list.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonListGalleries")
    public ModelAndView jsonListGalleries(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONArray data = null;
        try {
            List<GalleryDetailDto> galleryDetails = galleryService.getAll();
            data = GalleryDataAssembler.createJSON(galleryDetails);
        } finally {
        	HttpUtil.wrapAndWriteJson(response, "Galleries", data);
        }
        return null;
    }

    /**
     * Json list gallery.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonGalleryDetail")
    public ModelAndView jsonGalleryDetail(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONObject data = null;
        try {
        	String galleryName = request.getParameter("GalleryName");
            if (!StringUtil.isNullOrBlank(galleryName)) {
            	GalleryDetailDto galleryDetails = galleryService.get(galleryName);
            	data = GalleryDataAssembler.createJSON(galleryDetails);
            }
        } finally {
        	HttpUtil.wrapAndWriteJson(response, "Gallery", data);
        }
        return null;
    }

    /**
     * Gets the latest gallery.
     *
     * @param request the request
     * @param response the response
     * @return the latest gallery
     * @throws Exception the exception
     */
    @RequestMapping(value="getLatestGallery")
    public ModelAndView getLatestGallery(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONObject data = new JSONObject();
        try {
        	GalleryDetailDto galleryDetail = galleryService.getPinned();
        	data = GalleryDataAssembler.createJSON(galleryDetail);
        } finally {
            HttpUtil.wrapAndWriteJson(response, "Gallery", data);
        }
        return null;
    }

    /**
     * Pin gallery.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="pinGallery")
    public ModelAndView pinGallery(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
            String galleryName = request.getParameter("GalleryName");
            if (!StringUtil.isNullOrBlank(galleryName)) {
            	galleryService.pin(galleryName);
                result.setSuccessful(ResultDto.SUCCESS);
                result.setStatusMessage("Gallery '" + galleryName + "' is pinned to the notice board and will be shown on the dashboard.");
            }
        } catch (ServiceException serviceException) {
        	result.setStatusMessage(serviceException.getMessage());
		} finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Do create gallery.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doCreateGallery")
    public ModelAndView doCreateGallery(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
        	String galleryName = request.getParameter("GalleryName");
        	boolean success = galleryService.create(galleryName);
        	result.setSuccessful(success);
        	if (success) {
        		result.setStatusMessage("Gallery '" + galleryName + "' created successfully");
        	} else {
        		result.setStatusMessage("Unable to create Gallery '" + galleryName + "'");
        	}
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Do update gallery name.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doUpdateGalleryName")
    public ModelAndView doUpdateGalleryName(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
        	String oldGalleryName = request.getParameter("OldGalleryName");
        	String newGalleryName = request.getParameter("NewGalleryName");
        	boolean success = galleryService.update(oldGalleryName, newGalleryName);
        	result.setSuccessful(success);
        	if (success) {
        		result.setStatusMessage("Gallery renamed from '" + oldGalleryName + "' to '" + newGalleryName + "'");
        	} else {
        		result.setStatusMessage("Unable to rename gallery from '" + oldGalleryName + "' to '" + newGalleryName + "'");
        	}
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Do delete gallery.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doDeleteGallery")
    public ModelAndView doDeleteGallery(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
        	String galleryName = request.getParameter("GalleryName");
        	boolean success = galleryService.delete(galleryName);
        	result.setSuccessful(success);
        	if (success) {
        		result.setStatusMessage("Gallery '" + galleryName + "' deleted successfully");
        	} else {
        		result.setStatusMessage("Unable to delete gallery '" + galleryName + "'");
        	}
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Do add gallery item.
     *
     * @param request the request
     * @param response the response
     * @param uploadDataFileBean the upload data file bean
     * @param bindingResult the binding result
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doAddGalleryItem")
    public ModelAndView doAddGalleryItem(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("UploadDataFileBean") UploadDataFileBean uploadDataFileBean,
            BindingResult bindingResult) throws Exception {
        ResultDto result = new ResultDto();
        try {
        	// UploadDataFileBean data setup
        	// multiUploadId = galleryName
        	// uploadName = galleryName
        	// uploadFile = multi-part file
        	if (uploadDataFileBean == null) {
                throw new InsufficientInputException("There is no data to upload.");
            }
            String galleryName = uploadDataFileBean.getUploadName();
            MultipartFile multipartFile = uploadDataFileBean.getUploadFile();

            if (multipartFile == null) {
                throw new InsufficientInputException("There is no data to upload.");
            }
            long size = multipartFile.getSize();
            String originalFilename = multipartFile.getOriginalFilename();
            System.out.println("fileName: " + originalFilename + ", Module: " + galleryName + ", MIME: " + multipartFile.getContentType() + ", SIZE: " + size);

            if (size == 0) {
                throw new InsufficientInputException("There is no data to upload.");
            }
            // Create a temp file to transfer multipart contents.
            File galleryItemTempFile = tempFileSystem.createTempFile(originalFilename);
            multipartFile.transferTo(galleryItemTempFile);
            if (galleryItemTempFile == null) {
                throw new ServiceException("Unable to upload the image now.");
            }
        	// create gallery item detail dto object from the uploaded file
        	GalleryDetailDto galleryDetail = GalleryDataAssembler.createGalleryDetail(galleryItemTempFile);
        	boolean success = galleryService.add(galleryName, GalleryDataAssembler.createGalleryDetail(galleryItemTempFile));
        	result.setSuccessful(success);
        	if (success) {
        		result.setStatusMessage("Image added to gallery");
        	} else {
        		result.setStatusMessage("Failed to add image to gallery");
        	}
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Do delete gallery items.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doDeleteGalleryItems")
    public ModelAndView doDeleteGalleryItems(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
        	String galleryName = request.getParameter("GalleryName");
        	String galleryItemNameValues = request.getParameter("GalleryItems");
        	// Get list of items to delete
        	List<String> galleryItemNames = GalleryDataAssembler.create(galleryItemNameValues);
        	List<String> messages = galleryService.delete(galleryName, galleryItemNames);
        	result.setSuccessful(true);
        	StringBuffer messageBuffer = new StringBuffer();
        	if (messages != null && !messages.isEmpty()) {
        		for (String message : messages) {
        			messageBuffer.append(message).append("<br/>");
				}
        	}
            result.setStatusMessage(messageBuffer.toString());
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

}
