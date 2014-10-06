package com.myschool.web.academic.controller;

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

import com.myschool.academic.dto.HolidayDto;
import com.myschool.application.service.HolidayService;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.infra.web.constants.MimeTypes;
import com.myschool.web.academic.constants.AcademicViewNames;
import com.myschool.web.common.parser.ResponseParser;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.common.util.ViewErrorHandler;

/**
 * The Class HolidayController.
 */
@Controller
@RequestMapping("holiday")
public class HolidayController {

    /** The holiday service. */
    @Autowired
    private HolidayService holidayService;

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
        return ViewDelegationController.delegateWholePageView(request, AcademicViewNames.VIEW_HOLIDAYS);
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

        // TODO get only holidays in the current academic year.
        List<HolidayDto> holidays = holidayService.getAll();
        if (holidays != null) {
            for(HolidayDto holiday : holidays) {
                JSONArray row = new JSONArray();
                row.put(holiday.getHolidayId());
                row.put(holiday.getHolidayName());
                row.put(holiday.getStartDate());
                row.put(holiday.getEndDate());
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
        return ViewDelegationController.delegateModelPageView(request, AcademicViewNames.MAINTAIN_HOLIDAY);
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
            HolidayDto holiday = validateAndGetHoliday(request);
            resultDto.setSuccessful(holidayService.create(holiday));
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
     * Validate and get holiday.
     *
     * @param request the request
     * @return the holiday dto
     * @throws DataException the data exception
     */
    private HolidayDto validateAndGetHoliday(HttpServletRequest request) throws DataException {
        HolidayDto holiday = new HolidayDto();
        String holidayName = request.getParameter("holidayName");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        viewErrorHandler.validate(holidayName, "holidayName", DataTypeValidator.ANY_CHARACTER, true);
        viewErrorHandler.validate(startDate, "startDate", DataTypeValidator.ANY_CHARACTER, true);
        viewErrorHandler.validate(endDate, "endDate", DataTypeValidator.ANY_CHARACTER, true);

        holiday.setHolidayName(holidayName);
        holiday.setStartDate(startDate);
        holiday.setEndDate(endDate);
        return holiday;
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
        String holidayId = request.getParameter("holidayId");

        if (holidayId != null && holidayId.trim().length() != 0) {
            HolidayDto holiday = holidayService.get(Integer.parseInt(holidayId));
            map.put("holiday", holiday);
        }
        return ViewDelegationController.delegateModelPageView(request, AcademicViewNames.MAINTAIN_HOLIDAY, map);
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
            String holidayId = request.getParameter("holidayId");
            HolidayDto holiday = validateAndGetHoliday(request);
            resultDto.setSuccessful(holidayService.update(Integer.parseInt(holidayId), holiday));
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
            String holidayId = request.getParameter("holidayId");
            resultDto.setSuccessful(holidayService.delete(Integer.parseInt(holidayId)));
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
            resultDto.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeResponse(response, resultDto);
        }
        return null;
    }

}
