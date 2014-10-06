package com.myschool.web.report.controller;

import java.io.File;
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

import com.myschool.application.service.ImageService;
import com.myschool.common.util.StringUtil;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.infra.web.constants.MimeTypes;
import com.myschool.report.assembler.ReportDataAssembler;
import com.myschool.report.dto.ReportCriteria;
import com.myschool.report.dto.ReportDto;
import com.myschool.report.service.ReportService;
import com.myschool.web.common.util.HttpUtil;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.report.constants.ReportViewNames;

/**
 * The Class ReportController.
 */
@Controller
@RequestMapping("reports")
public class ReportController {

    /** The report service. */
    @Autowired
    private ReportService reportService;

    /** The image service. */
    @Autowired
    private ImageService imageService;

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
        return ViewDelegationController.delegateWholePageView(request, ReportViewNames.VIEW_REPORTS);
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
        List<ReportDto> reports = reportService.getAll();

        if (reports != null) {
            for (ReportDto report : reports) {
                JSONArray row = new JSONArray();
                row.put(report.getReportKey());
                row.put(report.getReportName());
                row.put(report.isCanAdminView());
                row.put(report.isCanEmployeeView());
                row.put(report.isCanStudentView());
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
        String reportKey = request.getParameter("ReportKey");
        System.out.println("ReportKey " + reportKey);
        if (reportKey != null) {
            map.put("REPORT", reportService.get(reportKey));
        }
        return ViewDelegationController.delegateModelPageView(request, ReportViewNames.REPORT_CRITERIA, map);
    }

    /**
     * Generate report.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="generateReport")
    public ModelAndView generateReport(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        File reportFile = null;
        JSONObject reportData = null;
        ModelAndView modelAndView = null;
        String reportDataValue = request.getParameter("ReportData");
        if (!StringUtil.isNullOrBlank(reportDataValue)) {
            reportData = new JSONObject(reportDataValue);
            ReportCriteria reportCriteria = ReportDataAssembler.create(reportData);
            System.out.println("reportCriteria " + reportCriteria);
            if (reportCriteria != null) {
                reportFile = reportService.generateReport(reportCriteria);
                if (reportFile != null) {
                    HttpUtil.addAttachment(response, reportFile, MimeTypes.APPLICATION_PDF, false);
                }
            }
        }
        return modelAndView;
    }

}