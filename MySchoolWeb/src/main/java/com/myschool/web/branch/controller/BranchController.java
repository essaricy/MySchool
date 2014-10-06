package com.myschool.web.branch.controller;

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
import com.myschool.branch.dto.RegionDto;
import com.myschool.branch.service.BranchService;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.infra.web.constants.MimeTypes;
import com.myschool.web.branch.constants.BranchViewNames;
import com.myschool.web.common.parser.ResponseParser;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.common.util.ViewErrorHandler;

/**
 * The Class BranchController.
 */
@Controller
@RequestMapping("branch")
public class BranchController {

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
        return ViewDelegationController.delegateWholePageView(request, BranchViewNames.VIEW_BRANCHES);
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
        List<BranchDto> branches = branchService.getAll();

        String regionIdval = request.getParameter("RegionId");
        if (StringUtil.isNullOrBlank(regionIdval)) {
            branches = branchService.getAll();
        } else {
            branches = branchService.getByRegion(Integer.parseInt(regionIdval));
        }
        if (branches != null) {
            for (BranchDto branch : branches) {
                JSONArray row = new JSONArray();
                row.put(branch.getBranchId()).put(branch.getMapUrl())
                .put(branch.getBranchCode()).put(branch.getDescription())
                .put(branch.getAddress()).put(branch.getRegion().getRegionName())
                .put(branch.getPhoneNumber()).put(branch.getEmailId());
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
        return ViewDelegationController.delegateModelPageView(request, BranchViewNames.MAINTAIN_BRANCH);
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
            BranchDto branchDto = validateAndGetBranch(request);
            resultDto.setSuccessful(branchService.create(branchDto));
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
     * Validate and get branch.
     *
     * @param request the request
     * @return the branch dto
     * @throws DataException the data exception
     */
    private BranchDto validateAndGetBranch(HttpServletRequest request) throws DataException {
        BranchDto branchDto = new BranchDto();

        String branchCode = request.getParameter("branchCode");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phoneNumber");
        String emailId = request.getParameter("emailId");

        viewErrorHandler.validate(branchCode, "branchCode", DataTypeValidator.ANY_CHARACTER, true);
        viewErrorHandler.validate(description, "description", DataTypeValidator.ANY_CHARACTER, true);
        viewErrorHandler.validate(address, "address", DataTypeValidator.ANY_CHARACTER, true);
        viewErrorHandler.validate(phoneNumber, "phoneNumber", DataTypeValidator.PHONE_NUMBER, true);
        viewErrorHandler.validate(emailId, "emailId", DataTypeValidator.EMAIL_ID, false);

        RegionDto region = new RegionDto();
        region.setRegionId(Integer.parseInt(request.getParameter("regionId")));
        branchDto.setRegion(region);
        branchDto.setBranchCode(branchCode);
        branchDto.setDescription(description);
        branchDto.setAddress(address);
        branchDto.setPhoneNumber(phoneNumber);
        branchDto.setEmailId(emailId);
        return branchDto;
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
        String branchId = request.getParameter("branchId");

        if (branchId != null && branchId.trim().length() != 0) {
            BranchDto branchDto = branchService.get(Integer.parseInt(branchId));
            map.put("branch", branchDto);
        }
        return ViewDelegationController.delegateModelPageView(request, BranchViewNames.MAINTAIN_BRANCH, map);
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
            String branchId = request.getParameter("branchId");
            BranchDto branchDto = validateAndGetBranch(request);
            resultDto.setSuccessful(branchService.update(Integer.parseInt(branchId), branchDto));
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
            String branchId = request.getParameter("branchId");
            resultDto.setSuccessful(branchService.delete(Integer.parseInt(branchId)));
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
            resultDto.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeResponse(response, resultDto);
        }
        return null;
    }

}
