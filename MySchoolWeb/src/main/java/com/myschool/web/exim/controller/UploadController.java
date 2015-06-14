package com.myschool.web.exim.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.exception.InsufficientInputException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.exim.assembler.UploadTrackerDataAssembler;
import com.myschool.exim.dto.EximDto;
import com.myschool.exim.dto.UploadFileTrackerDto;
import com.myschool.exim.dto.UploadRecordTrackerDto;
import com.myschool.exim.dto.UploadTrackerDto;
import com.myschool.exim.service.EximService;
import com.myschool.exim.service.UploadService;
import com.myschool.infra.filesystem.agent.TempFileSystem;
import com.myschool.infra.filesystem.util.FileUtil;
import com.myschool.user.constants.UserType;
import com.myschool.user.dto.UserContext;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.exim.bean.UploadDataFileBean;
import com.myschool.web.exim.constants.EximViewNames;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.handler.ViewErrorHandler;
import com.myschool.web.framework.util.HttpUtil;

/**
 * The Class UploadController.
 */
@Controller
@RequestMapping("upload")
public class UploadController {

    /** The exim service. */
    @Autowired
    private EximService eximService;

    /** The upload service. */
    @Autowired
    private UploadService uploadService;

    /** The temp file system. */
    @Autowired
    private TempFileSystem tempFileSystem;

    /** The resource bundle util. */
    @Autowired
    private ViewErrorHandler viewErrorHandler;

    /**
     * List.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="listDataUpload")
    public ModelAndView listDataUpload(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<EximDto> exims = eximService.getAllImports();
        map.put("exims", exims);
        return ViewDelegationController.delegateMultipartView(request, EximViewNames.UPLOAD_DATA, map);
    }

    /**
     * Generate upload tracker.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping("generateUploadTracker")
    public ModelAndView generateUploadTracker(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONObject jsonResponse = new JSONObject();
        try {
            HttpSession session = HttpUtil.getExistingSession(request);
            Object userContextObject = session.getAttribute(WebConstants.USER_CONTEXT);
            if (userContextObject instanceof UserContext) {
                UserContext userContext = (UserContext) userContextObject;
                int loginId = userContext.getLogin().getId();
                int uploadTrackerId = uploadService.createUploadTracker(loginId);
                jsonResponse.put("uploadTrackerId", uploadTrackerId);
            }
        } finally {
            HttpUtil.writeJson(response, jsonResponse);
        }
        return null;
    }

    /**
     * Upload image.
     * 
     * @param response the response
     * @param uploadDataFileBean the upload data file bean
     * @param bindingResult the binding result
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping("uploadImage")
    public ModelAndView uploadImage(
            HttpServletResponse response,
            @ModelAttribute("UploadDataFileBean") UploadDataFileBean uploadDataFileBean,
            BindingResult bindingResult) throws Exception {
        ResultDto result = new ResultDto();
        try {
            if (uploadDataFileBean == null) {
                throw new InsufficientInputException("There is no data to upload.");
            }
            String module = uploadDataFileBean.getUploadName();
            MultipartFile multipartFile = uploadDataFileBean.getUploadFile();

            if (multipartFile == null) {
                throw new InsufficientInputException("There is no data to upload.");
            }
            long size = multipartFile.getSize();
            String originalFilename = multipartFile.getOriginalFilename();
            System.out.println("fileName: " + originalFilename + ", Module: " + module + ", MIME: " + multipartFile.getContentType() + ", SIZE: " + size);

            if (size == 0) {
                throw new InsufficientInputException("There is no data to upload.");
            }
            // Create a temp file to transfer multipart contents.
            File imageTempFile = tempFileSystem.createTempFile(originalFilename);
            multipartFile.transferTo(imageTempFile);
            imageTempFile = uploadService.createTempImage(module.trim(), imageTempFile);
            if (imageTempFile == null) {
                throw new ServiceException("Unable to upload the image now.");
            }
            String referenceNumber = imageTempFile.getName();
            System.out.println("referenceNumber " + referenceNumber);
            result.setSuccessful(ResultDto.SUCCESS);
            result.setReferenceNumber(String.valueOf(referenceNumber));
        } catch (DataException dataException) {
            result.setStatusMessage(viewErrorHandler.getMessage(dataException.getMessage()));
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Upload data file.
     * 
     * @param response the response
     * @param uploadDataFileBean the upload data file bean
     * @param bindingResult the binding result
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping("uploadDataFile")
    public ModelAndView uploadDataFile(
            HttpServletResponse response,
            @ModelAttribute("UploadDataFileBean") UploadDataFileBean uploadDataFileBean,
            BindingResult bindingResult) throws Exception {
        ResultDto result = new ResultDto();
        try {
            UploadFileTrackerDto uploadFileTracker = validateAndGetUploadFileTracker(uploadDataFileBean);
            String multiUploadId = uploadDataFileBean.getMultiUploadId();
            int uploadFileTrackerId = uploadService.createUploadFileTracker(
                    Integer.parseInt(multiUploadId), uploadFileTracker);
            result.setSuccessful(ResultDto.SUCCESS);
            result.setReferenceNumber(String.valueOf(uploadFileTrackerId));
        } catch (DataException dataException) {
            result.setStatusMessage(viewErrorHandler.getMessage(dataException.getMessage()));
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Gets the upload trackers.
     * 
     * @param request the request
     * @param response the response
     * @return the upload trackers
     * @throws Exception the exception
     */
    @RequestMapping("getUploadTrackers")
    public ModelAndView getUploadTrackers(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONArray data = null;
        try {
            HttpSession session = HttpUtil.getExistingSession(request);
            Object userContextObject = session.getAttribute(WebConstants.USER_CONTEXT);
            if (userContextObject instanceof UserContext) {
                UserContext userContext = (UserContext) userContextObject;
                // If admin is logged in, then show all the upload trackers.
                UserType userType = userContext.getUserType();
                List<UploadTrackerDto> uploadTrackers = null;
                if (userType == UserType.ADMIN) {
                    uploadTrackers = uploadService.getUploadTrackers();
                } else {
                    int loginId = userContext.getLogin().getId();
                    uploadTrackers = uploadService.getUploadTrackers(loginId);
                }
                if (uploadTrackers != null && !uploadTrackers.isEmpty()) {
                    data = new JSONArray();
                    for (UploadTrackerDto uploadTracker : uploadTrackers) {
                        data.put(UploadTrackerDataAssembler.getUploadTracker(uploadTracker));
                    }
                }
            }
        } finally {
            HttpUtil.wrapAndWriteJson(response, "UploadTrackers", data);
        }
        return null;
    }

    /**
     * Gets the upload file trackers.
     * 
     * @param request the request
     * @param response the response
     * @return the upload file trackers
     * @throws Exception the exception
     */
    @RequestMapping("getUploadFileTrackers")
    public ModelAndView getUploadFileTrackers(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONArray data = null;
        try {
            String uploadTrackerIdVal = request.getParameter("uploadTrackerId");
            if (!StringUtil.isNullOrBlank(uploadTrackerIdVal)) {
                List<UploadFileTrackerDto> uploadFileTrackerByTracker = 
                        uploadService.getUploadFileTrackerByTracker(
                                Integer.parseInt(uploadTrackerIdVal));
                if (uploadFileTrackerByTracker != null && !uploadFileTrackerByTracker.isEmpty()) {
                    data = new JSONArray();
                    for (UploadFileTrackerDto uploadFileTracker : uploadFileTrackerByTracker) {
                        data.put(UploadTrackerDataAssembler.getUploadFileTracker(uploadFileTracker));
                    }
                }
            }
        } finally {
            HttpUtil.wrapAndWriteJson(response, "UploadFileTrackers", data);
        }
        return null;
    }

    /**
     * Launch upload record trackers.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping("launchUploadRecordTrackers")
    public ModelAndView launchUploadRecordTrackers(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String fileTrackerIdVal = request.getParameter("fileTrackerId");
        map.put("fileTrackerId", fileTrackerIdVal);
        return ViewDelegationController.delegateModelPageView(request, EximViewNames.UPLOAD_RECORD_STATUS, map);
    }

    /**
     * Gets the upload record trackers.
     * 
     * @param request the request
     * @param response the response
     * @return the upload record trackers
     * @throws Exception the exception
     */
    @RequestMapping("getUploadRecordTrackers")
    public ModelAndView getUploadRecordTrackers(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        String fileTrackerIdVal = request.getParameter("fileTrackerId");
        if (!StringUtil.isNullOrBlank(fileTrackerIdVal)) {
            List<UploadRecordTrackerDto> uploadRecordTrackerByTracker =
                    uploadService.getUploadRecordTrackerByFileTracker(Integer.parseInt(fileTrackerIdVal));
            map.put("UploadRecordTrackers", uploadRecordTrackerByTracker);
        }
        return ViewDelegationController.delegateModelPageView(request, EximViewNames.UPLOAD_RECORD_STATUS, map);
    }

    /**
     * Json get upload record trackers.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping("jsonGetUploadRecordTrackers")
    public ModelAndView jsonGetUploadRecordTrackers(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONArray data = null;
        try {
            String fileTrackerIdVal = request.getParameter("fileTrackerId");
            if (!StringUtil.isNullOrBlank(fileTrackerIdVal)) {
                List<UploadRecordTrackerDto> uploadRecordTrackerByTracker =
                        uploadService.getUploadRecordTrackerByFileTracker(Integer.parseInt(fileTrackerIdVal));
                if (uploadRecordTrackerByTracker != null && !uploadRecordTrackerByTracker.isEmpty()) {
                    data = new JSONArray();
                    for (UploadRecordTrackerDto uploadRecordTracker : uploadRecordTrackerByTracker) {
                        data.put(UploadTrackerDataAssembler.getUploadRecordTracker(uploadRecordTracker));
                    }
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
        return null;
    }

    /**
     * Start upload data process.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping("startUploadDataProcess")
    public ModelAndView startUploadDataProcess(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String uploadTrackerIdVal = request.getParameter("uploadTrackerId");
        if (!StringUtil.isNullOrBlank(uploadTrackerIdVal)) {
            uploadService.startUploadDataProcess(Integer.parseInt(uploadTrackerIdVal));
        }
        return null;
    }

    /**
     * Validate and get upload file tracker.
     * 
     * @param uploadDataFileBean the upload data file bean
     * @return the upload file tracker dto
     * @throws DataException the data exception
     * @throws ServiceException the service exception
     * @throws FileSystemException the file system exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private UploadFileTrackerDto validateAndGetUploadFileTracker(
            UploadDataFileBean uploadDataFileBean) throws DataException,
            ServiceException, FileSystemException, IOException {
        UploadFileTrackerDto uploadFileTracker = null;
        if (uploadDataFileBean == null) {
            throw new DataException("Upload data file information is not present.");
        } else {
            String multiUploadId = uploadDataFileBean.getMultiUploadId();
            if (StringUtil.isNullOrBlank(multiUploadId)) {
                throw new DataException("Upload tracker id is not present.");
            } else {
                UploadTrackerDto uploadTracker = uploadService.getUploadTracker(Integer.parseInt(multiUploadId));
                if (uploadTracker == null) {
                    throw new DataException("Upload tracker id is not created.");
                } else {
                    MultipartFile multipartFile = uploadDataFileBean.getUploadFile();
                    String uploadName = uploadDataFileBean.getUploadName();
                    uploadFileTracker = new UploadFileTrackerDto();
                    File uploadTrackerDirectory = tempFileSystem.getUploadFile(multiUploadId);
                    File importFile = FileUtil.createFile(uploadTrackerDirectory, multipartFile.getOriginalFilename());
                    multipartFile.transferTo(importFile);
                    uploadFileTracker.setUploadFile(importFile);
                    uploadFileTracker.setFileName(multipartFile.getOriginalFilename());
                    uploadFileTracker.setUploadType(uploadName);
                }
            }
        }
        return uploadFileTracker;
    }

    /**
     * Download template.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="downloadTemplate")
    public ModelAndView downloadTemplate(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        File importFile = null;
        String importKey = request.getParameter("importKey");
        try {
            if (importKey != null) {
                importFile = uploadService.getTemplate(importKey);
                if (importFile != null) {
                    HttpUtil.addAttachment(response, importFile);
                }
            }
        } catch (NumberFormatException numberFormatException) {
            throw new ServiceException(numberFormatException.getMessage(), numberFormatException);
        }
        return null;
    }
}
