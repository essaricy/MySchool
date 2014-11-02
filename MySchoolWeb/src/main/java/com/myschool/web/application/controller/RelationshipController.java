package com.myschool.web.application.controller;

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

import com.myschool.application.service.RelationshipService;
import com.myschool.common.dto.Relationship;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.web.application.constants.ApplicationViewNames;
import com.myschool.web.common.util.HttpUtil;
import com.myschool.web.common.util.ViewDelegationController;

/**
 * The Class RelationshipController.
 */
@Controller
@RequestMapping("relationship")
public class RelationshipController {

    /** The relationship service. */
    @Autowired
    private RelationshipService relationshipService;

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
        return ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.VIEW_RELATIONSHIPS);
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
            List<Relationship> relationships = relationshipService.getAll();
            if (relationships != null) {
                for(Relationship relationship : relationships) {
                    JSONArray row = new JSONArray();
                    row.put(relationship.getCode())
                    .put(relationship.getName());
                    data.put(row);
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
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
        String relationshipCode = request.getParameter("code");

        if (!StringUtil.isNullOrBlank(relationshipCode)) {
            Relationship relationship = relationshipService.get(relationshipCode);
            map.put("relationship", relationship);
        }
        return ViewDelegationController.delegateModelPageView(request, ApplicationViewNames.MAINTAIN_RELATIONSHIP, map);
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
            Relationship relationship = validateAndGetRelationship(request);
            result.setSuccessful(relationshipService.create(relationship));
        } catch (DataException dataException) {
            result.setStatusMessage(dataException.getMessage());
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
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

        ResultDto result = new ResultDto();

        try {
            Relationship relationship = validateAndGetRelationship(request);
            result.setSuccessful(relationshipService.update(relationship.getCode(), relationship));
        } catch (DataException dataException) {
            result.setStatusMessage(dataException.getMessage());
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
            String code = request.getParameter("code");
            result.setSuccessful(relationshipService.delete(code));
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * 
     * @param request the request
     * @throws DataException the data exception
     */
    private Relationship validateAndGetRelationship(HttpServletRequest request) throws DataException {
        Relationship relationship = new Relationship();
        relationship.setCode(request.getParameter("code"));
        relationship.setName(request.getParameter("name"));
        return relationship;
    }

}
