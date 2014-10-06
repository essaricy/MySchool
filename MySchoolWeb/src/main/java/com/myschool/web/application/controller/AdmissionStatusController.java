package com.myschool.web.application.controller;

import java.io.PrintWriter;
import java.util.HashMap;
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

import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.infra.web.constants.MimeTypes;
import com.myschool.student.dto.AdmissionStatus;
import com.myschool.student.service.AdmissionStatusService;
import com.myschool.web.common.parser.ResponseParser;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.student.constants.StudentViewNames;

/**
 */
@Controller
@RequestMapping("admission-status")
public class AdmissionStatusController {

    @Autowired
    private AdmissionStatusService admissionStatusService;

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
        return ViewDelegationController.delegateWholePageView(request, StudentViewNames.VIEW_ADMISSION_STATUS);
    }

    /**
     * Json list.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonList")
    public ModelAndView jsonList(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONArray data = new JSONArray();
        JSONObject jsonResponse = new JSONObject();
        List<AdmissionStatus> admissionStatusList = admissionStatusService.getAll();

        if (admissionStatusList != null) {
            for(AdmissionStatus admissionStatus : admissionStatusList) {
                JSONArray row = new JSONArray();
                row.put(admissionStatus.getStatusId())
                .put(admissionStatus.getDescription());
                data.put(row);
            }
        }
        jsonResponse.put(DataTypeValidator.AA_DATA, data);
        response.setContentType(MimeTypes.APPLICATION_JSON);
        PrintWriter writer = response.getWriter();
        writer.print(jsonResponse.toString());
        writer.close();
        return null;
    }

    /**
     * Launch.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="launch")
    public ModelAndView launch(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        String admissionStatusId = request.getParameter("AdmissionStatusId");

        if (!StringUtil.isNullOrBlank(admissionStatusId)) {
            AdmissionStatus admissionStatus = admissionStatusService.get(Integer.parseInt(admissionStatusId));
            map.put("AdmissionStatus", admissionStatus);
        }
        return ViewDelegationController.delegateModelPageView(request, StudentViewNames.MAINTAIN_ADMISSION_STATUS, map);
    }

    /**
     * Do create.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doCreate")
    public ModelAndView doCreate(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ResultDto resultDto = new ResultDto();

        try {
            AdmissionStatus admissionStatus = validateAndGetAdmissionStatus(request);
            resultDto.setSuccessful(admissionStatusService.create(admissionStatus));
            resultDto.setStatusMessage("Admission Status has been created successfully.");
        } catch (DataException dataException) {
            resultDto.setStatusMessage(dataException.getMessage());
        } catch (ServiceException serviceException) {
            resultDto.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeResponse(response, resultDto);
        }
        return null;
    }

    /**
     * Do update.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doUpdate")
    public ModelAndView doUpdate(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ResultDto resultDto = new ResultDto();

        try {
            String admissionStatusId = request.getParameter("AdmissionStatusId");
            if (!StringUtil.isNullOrBlank(admissionStatusId)) {
                AdmissionStatus admissionStatus = validateAndGetAdmissionStatus(request);
                resultDto.setSuccessful(admissionStatusService.update(Integer.parseInt(admissionStatusId), admissionStatus));
                resultDto.setStatusMessage("Admission Status has been updated.");
            }
        } catch (DataException dataException) {
            resultDto.setStatusMessage(dataException.getMessage());
        } catch (ServiceException serviceException) {
            resultDto.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeResponse(response, resultDto);
        }
        return null;
    }

    /**
     * Do delete.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doDelete")
    public ModelAndView doDelete(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ResultDto resultDto = new ResultDto();
        try {
            String admissionStatusId = request.getParameter("AdmissionStatusId");
            resultDto.setSuccessful(admissionStatusService.delete(Integer.parseInt(admissionStatusId)));
            resultDto.setStatusMessage("Admission Status has been deleted successfully.");
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
            resultDto.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeResponse(response, resultDto);
        }
        return null;
    }

    /**
     * 
     * @param request the request
     * @throws DataException the data exception
     */
    private AdmissionStatus validateAndGetAdmissionStatus(HttpServletRequest request) throws DataException {
        AdmissionStatus admissionStatus = new AdmissionStatus();
        admissionStatus.setDescription(request.getParameter("Description"));
        return admissionStatus;
    }

}
