package com.myschool.web.clazz.controller;

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

import com.myschool.clazz.dto.MediumDto;
import com.myschool.clazz.service.ClassService;
import com.myschool.clazz.service.MediumService;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.infra.web.constants.MimeTypes;
import com.myschool.school.service.SchoolService;
import com.myschool.web.clazz.constants.ClazzViewNames;
import com.myschool.web.common.parser.ResponseParser;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.common.util.ViewErrorHandler;

/**
 * The Class MediumController.
 */
@Controller
@RequestMapping("medium")
public class MediumController {

    /** The medium service. */
    @Autowired
    private MediumService mediumService;

    /** The school service. */
    @Autowired
    private SchoolService schoolService;

    /** The class service. */
    @Autowired
    private ClassService classService;

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
        return ViewDelegationController.delegateWholePageView(request, ClazzViewNames.VIEW_MEDIUMS);
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

        List<MediumDto> mediums = mediumService.getAll();
        if (mediums != null) {
            for(MediumDto medium : mediums) {
                JSONArray row = new JSONArray();
                row.put(medium.getMediumId());
                row.put(medium.getDescription());
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
        return ViewDelegationController.delegateModelPageView(request, ClazzViewNames.MAINTAIN_MEDIUM);
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
            MediumDto mediumDto = validateAndGetMedium(request);
            resultDto.setSuccessful(mediumService.create(mediumDto));
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
     * Validate and get medium.
     *
     * @param request the request
     * @return the medium dto
     * @throws DataException the data exception
     */
    private MediumDto validateAndGetMedium(HttpServletRequest request) throws DataException {
        MediumDto medium = new MediumDto();
        String description = request.getParameter("description");
        viewErrorHandler.validate(description, "description", DataTypeValidator.ANY_CHARACTER, true);
        medium.setDescription(description);
        return medium;
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
        String mediumId = request.getParameter("mediumId");

        if (mediumId != null && mediumId.trim().length() != 0) {
            MediumDto mediumDto = mediumService.get(Integer.parseInt(mediumId));
            map.put("medium", mediumDto);
        }
        return ViewDelegationController.delegateModelPageView(request, ClazzViewNames.MAINTAIN_MEDIUM, map);
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
            String mediumId = request.getParameter("mediumId");
            MediumDto mediumDto = validateAndGetMedium(request);
            resultDto.setSuccessful(mediumService.update(Integer.parseInt(mediumId), mediumDto));
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
            String mediumId = request.getParameter("mediumId");
            resultDto.setSuccessful(mediumService.delete(Integer.parseInt(mediumId)));
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
            resultDto.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeResponse(response, resultDto);
        }
        return null;
    }

}
