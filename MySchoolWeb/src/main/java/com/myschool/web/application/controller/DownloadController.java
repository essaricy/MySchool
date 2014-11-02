package com.myschool.web.application.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.download.dto.BrochureDto;
import com.myschool.download.service.BrochureService;
import com.myschool.web.application.constants.DownloadViewNames;
import com.myschool.web.common.util.HttpUtil;
import com.myschool.web.common.util.ViewDelegationController;

/**
 * The Class DownloadController.
 */
@Controller
@RequestMapping("download")
public class DownloadController {

    /** The brochure service. */
    @Autowired
    private BrochureService brochureService;

    /**
     * List.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="brochures")
    public ModelAndView list(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateWholePageView(request, DownloadViewNames.VIEW_BROCHURES);
    }

    /**
     * Json brochures list.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonBrochuresList")
    public ModelAndView jsonBrochuresList(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONArray data = new JSONArray();
        try {
            List<BrochureDto> brochures = brochureService.getAll();
            if (brochures != null) {
                for(BrochureDto brochure : brochures){
                    JSONArray row = new JSONArray();
                    row.put(brochure.getBrochureFile().getName());
                    row.put(brochure.getBrochureName());
                    row.put(brochure.getBrochureType());
                    row.put(brochure.getLastUpdatedOn());
                    data.put(row);
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
        return null;
    }

    /**
     * Gets the brochure.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="getBrochure")
    public ModelAndView getBrochure(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String brochureFileName = request.getParameter("brochureFileName");
        BrochureDto brochure = brochureService.getBrochure(brochureFileName);
        if (brochure != null) {
            File file = brochure.getBrochureFile();
            if (file != null) {
               HttpUtil.writeToResponse(response, file);
            } 
        }
        return null;
    }

}
