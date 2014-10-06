package com.myschool.web.school.controller;

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

import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.DivisionDto;
import com.myschool.branch.service.BranchService;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.infra.web.constants.MimeTypes;
import com.myschool.school.dto.SchoolDto;
import com.myschool.school.service.SchoolService;
import com.myschool.web.common.parser.ResponseParser;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.common.util.ViewErrorHandler;
import com.myschool.web.school.constants.SchoolViewNames;

/**
 * The Class SchoolController.
 */
@Controller
@RequestMapping("school")
public class SchoolController {

    /** The school service. */
    @Autowired
    private SchoolService schoolService;

    /** The branch service. */
    @Autowired
    private BranchService branchService;

    /** The view error handler. */
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
    @RequestMapping(value="list")
    public ModelAndView list(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateWholePageView(request, SchoolViewNames.VIEW_SCHOOLS);
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

        List<SchoolDto> schools = null;
        String branchId = request.getParameter("branchId");
        if (branchId != null && !StringUtil.isEmpty(branchId)) {
            schools = schoolService.getByBranch(Integer.parseInt(branchId));
        } else {
            schools = schoolService.getAll();
        }

        if (schools != null) {
            for (SchoolDto school : schools) {
                JSONArray row = new JSONArray();
                row.put(school.getSchoolId());
                row.put(school.getMapUrl());
                row.put(school.getSchoolName());
                row.put(school.getAddress());
                row.put(school.getPrimaryPhoneNumber());
                row.put(school.getSecondaryPhoneNumber());
                row.put(school.getMobileNumber());
                row.put(school.getFaxNumber());
                row.put(school.getEmailId());
                row.put(school.getBranch().getBranchCode());
                row.put(school.getDivision().getDivisionCode());
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
     * Launch new.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="launchNew")
    public ModelAndView launchNew(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateModelPageView(request, SchoolViewNames.MAINTAIN_SCHOOL);
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
            SchoolDto schoolDto = validateAndGetSchool(request);
            resultDto.setSuccessful(schoolService.create(schoolDto));
        } catch (DataException dataException) {
            resultDto.setStatusMessage(viewErrorHandler.getMessage(dataException.getMessage()));
        } catch (ServiceException serviceException) {
            resultDto.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeResponse(response, resultDto);
        }
        return null;
    }

    /**
     * Validate and get school.
     *
     * @param request the request
     * @return the school dto
     * @throws DataException the data exception
     */
    private SchoolDto validateAndGetSchool(HttpServletRequest request) throws DataException {
        SchoolDto school = new SchoolDto();
        BranchDto branch = new BranchDto();
        branch.setBranchId(Integer.parseInt(request.getParameter("branchId")));
        school.setBranch(branch);
        DivisionDto division = new DivisionDto();
        division.setDivisionId(Integer.parseInt(request.getParameter("divisionId")));
        school.setDivision(division);

        String schoolName = request.getParameter("schoolName");
        String address = request.getParameter("address");
        String primaryPhoneNumber = request.getParameter("primaryPhoneNumber");
        String secondaryPhoneNumber = request.getParameter("secondaryPhoneNumber");
        String mobileNumber = request.getParameter("mobileNumber");
        String faxNumber = request.getParameter("faxNumber");
        String emailId = request.getParameter("emailId");

        viewErrorHandler.validate(schoolName, "schoolName", DataTypeValidator.ANY_CHARACTER, true);
        viewErrorHandler.validate(address, "address", DataTypeValidator.ANY_CHARACTER, true);
        viewErrorHandler.validate(primaryPhoneNumber, "primaryPhoneNumber", DataTypeValidator.PHONE_NUMBER, true);
        viewErrorHandler.validate(secondaryPhoneNumber, "secondaryPhoneNumber", DataTypeValidator.PHONE_NUMBER, false);
        viewErrorHandler.validate(mobileNumber, "mobileNumber", DataTypeValidator.PHONE_NUMBER, false);
        viewErrorHandler.validate(faxNumber, "faxNumber", DataTypeValidator.PHONE_NUMBER, false);
        viewErrorHandler.validate(emailId, "emailId", DataTypeValidator.EMAIL_ID, false);
        
        school.setSchoolName(schoolName);    
        school.setAddress(address);
        school.setPrimaryPhoneNumber(primaryPhoneNumber);
        school.setSecondaryPhoneNumber(secondaryPhoneNumber);
        school.setMobileNumber(mobileNumber);
        school.setFaxNumber(faxNumber);
        school.setEmailId(emailId);
        return school;
    }

    /**
     * Launch update.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="launchUpdate")
    public ModelAndView launchUpdate(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        String schoolId = request.getParameter("schoolId");

        if (schoolId != null && schoolId.trim().length() != 0) {
            SchoolDto schoolDto = schoolService.get(Integer.parseInt(schoolId));
            map.put("school", schoolDto);
        }
        return ViewDelegationController.delegateModelPageView(request, SchoolViewNames.MAINTAIN_SCHOOL, map);
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
            String schoolId = request.getParameter("schoolId");
            SchoolDto schoolDto = validateAndGetSchool(request);

            resultDto.setSuccessful(schoolService.update(Integer.parseInt(schoolId), schoolDto));
        } catch (DataException dataException) {
            resultDto.setStatusMessage(viewErrorHandler.getMessage(dataException.getMessage()));
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
            String schoolId = request.getParameter("schoolId");
            resultDto.setSuccessful(schoolService.delete(Integer.parseInt(schoolId)));
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
            resultDto.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeResponse(response, resultDto);
        }
        return null;
    }

}
