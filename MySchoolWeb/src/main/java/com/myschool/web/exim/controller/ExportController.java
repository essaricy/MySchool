package com.myschool.web.exim.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.exim.dto.EximDto;
import com.myschool.exim.service.EximService;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.exim.constants.EximViewNames;

/**
 * The Class ExportController.
 */
@Controller
@RequestMapping("export")
public class ExportController {

    @Autowired
    private EximService eximService;

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
        Map<String, Object> map = new HashMap<String, Object>();
        List<EximDto> exims = eximService.getAllExports();
        map.put("exims", exims);
        return ViewDelegationController.delegateMultipartView(request, EximViewNames.EXPORT_DATA, map);
    }

}
