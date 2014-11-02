package com.myschool.web.academic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.web.common.util.HttpUtil;

/**
 * The Class StudentController.
 */
@Controller
@RequestMapping("dummy")
public class DummyController {

    /**
     * Json search.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonList")
    public ModelAndView jsonList(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        HttpUtil.wrapAndWriteAsAAData(response, new JSONArray());
        return null;
    }

}
