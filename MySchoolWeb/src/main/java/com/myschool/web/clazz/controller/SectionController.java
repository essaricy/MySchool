package com.myschool.web.clazz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.clazz.dto.SectionDto;
import com.myschool.clazz.service.ClassService;
import com.myschool.clazz.service.SectionService;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.school.service.SchoolService;
import com.myschool.web.clazz.constants.ClazzViewNames;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.handler.ViewErrorHandler;
import com.myschool.web.framework.util.HttpUtil;

/**
 * The Class SectionController.
 */
@Controller
@RequestMapping("section")
public class SectionController {

    /** The section service. */
    @Autowired
    private SectionService sectionService;

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
        return ViewDelegationController.delegateWholePageView(request, ClazzViewNames.VIEW_SECTIONS);
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
        try {
            List<SectionDto> sections = sectionService.getAll();
            if (sections != null) {
                for(SectionDto section : sections) {
                    JSONArray row = new JSONArray();
                    row.put(section.getSectionId());
                    row.put(section.getSectionName());
                    data.put(row);
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
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
        return ViewDelegationController.delegateModelPageView(request, ClazzViewNames.MAINTAIN_SECTION);
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

        ResultDto result = new ResultDto();
        try {
            SectionDto section = validateAndGetSection(request);
            result.setSuccessful(sectionService.create(section));
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
     * Validate and get section.
     *
     * @param request the request
     * @return the section dto
     * @throws DataException the data exception
     */
    private SectionDto validateAndGetSection(HttpServletRequest request) throws DataException {
        SectionDto section = new SectionDto();
        String sectionName = request.getParameter("sectionName");
        viewErrorHandler.validate(sectionName, "sectionName", DataTypeValidator.ANY_CHARACTER, true);
        section.setSectionName(request.getParameter("sectionName"));
        return section;
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
        String sectionId = request.getParameter("sectionId");

        if (sectionId != null && sectionId.trim().length() != 0) {
            SectionDto section = sectionService.get(Integer.parseInt(sectionId));
            map.put("section", section);
        }
        return ViewDelegationController.delegateModelPageView(request, ClazzViewNames.MAINTAIN_SECTION, map);
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

        ResultDto result = new ResultDto();

        try {
            String sectionId = request.getParameter("sectionId");
            SectionDto section = validateAndGetSection(request);
            result.setSuccessful(sectionService.update(Integer.parseInt(sectionId), section));
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

        ResultDto result = new ResultDto();
        try {
            String sectionId = request.getParameter("sectionId");
            result.setSuccessful(sectionService.delete(Integer.parseInt(sectionId)));
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

}
