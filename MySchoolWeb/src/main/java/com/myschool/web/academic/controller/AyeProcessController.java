package com.myschool.web.academic.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.academic.dto.AyeProcessCriteriaDto;
import com.myschool.academic.service.AyeProcessService;
import com.myschool.common.assembler.ResultDataAssembler;
import com.myschool.common.dto.ResultDto;
import com.myschool.infra.web.constants.MimeTypes;
import com.myschool.web.academic.constants.AcademicViewNames;
import com.myschool.web.common.util.ViewDelegationController;

/**
 * The Class AyeProcessController.
 */
@Controller
@RequestMapping("aye")
public class AyeProcessController {

    /** The aye process service. */
    @Autowired
    private AyeProcessService ayeProcessService;

    /**
     * Show.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="show")
    public ModelAndView show(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("AcademicYearClosure", ayeProcessService.getAcademicYearClosure());
        return ViewDelegationController.delegateWholePageView(request, AcademicViewNames.PROCESS_ACADEMIC_YEAR_CLOSURE, map);
    }

    /**
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="initiate")
    public ModelAndView initiate(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        List<ResultDto> resultList = null;
        JSONObject jsonResponse = new JSONObject();

        try {
            resultList = ayeProcessService.initiateAcademicYearClosure(validateAndGetAcademicYearEnd(request));
            JSONArray jsonArray = ResultDataAssembler.create(resultList);
            jsonResponse.put("Response", jsonArray);
        } catch (Exception exception) {
            jsonResponse.put("Response", (Object)null);
        } finally {
            response.setContentType(MimeTypes.APPLICATION_JSON);
            PrintWriter writer = response.getWriter();
            writer.print(jsonResponse.toString());
            writer.close();
        }
        return null;
    }

    /**
     * Validate and get academic year end.
     *
     * @param request the request
     * @return the aye process criteria dto
     */
    private AyeProcessCriteriaDto validateAndGetAcademicYearEnd(HttpServletRequest request) {
        AyeProcessCriteriaDto ayeProcessCriteria = new AyeProcessCriteriaDto();
        // TODO add criteria required to run academic year end process.
        return ayeProcessCriteria;
    }

}
