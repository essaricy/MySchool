package com.myschool.web.branch.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.branch.dto.RegionDto;
import com.myschool.branch.service.RegionService;
import com.myschool.web.application.constants.ApplicationViewNames;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.util.HttpUtil;
import com.quasar.core.util.StringUtil;

/**
 * The Class RegionController.
 */
@Controller
@RequestMapping("region")
public class RegionController {

    /** The region service. */
    @Autowired
    private RegionService regionService;

    @RequestMapping(value="list")
    public ModelAndView list(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.VIEW_REGIONS);
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
        List<RegionDto> regions = null;
        JSONArray data = new JSONArray();
        try {
            regions = regionService.getAll();
            if (regions != null) {
                for(RegionDto region : regions) {
                    JSONArray row = new JSONArray();
                    row.put(region.getRegionId());
                    row.put(region.getRegionName());
                    data.put(row);
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
        return null;
    }

}
