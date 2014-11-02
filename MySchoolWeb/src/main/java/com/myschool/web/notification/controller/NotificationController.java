package com.myschool.web.notification.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.application.constants.Device;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.notification.assembler.NotificationDataAssembler;
import com.myschool.notification.constants.NotificationEndPoint;
import com.myschool.notification.constants.NotificationMode;
import com.myschool.notification.constants.NotificationType;
import com.myschool.notification.dto.NotificationCriteriaDto;
import com.myschool.notification.dto.NotificationDto;
import com.myschool.notification.service.NotificationService;
import com.myschool.web.common.util.HttpUtil;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.common.util.ViewErrorHandler;
import com.myschool.web.notification.constants.NotificationViewNames;

/**
 * The Class NotificationController.
 */
@Controller
@RequestMapping("notification")
public class NotificationController {

    /** The notification service. */
    @Autowired
    private NotificationService notificationService;

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
        return ViewDelegationController.delegateWholePageView(
                request, NotificationViewNames.VIEW_NOTIFICATION_TEMPLATES);
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
        List<NotificationDto> notifications = null;
        try {
            String notificationCriteriaValue = request.getParameter("NotificationCriteria");
            if (StringUtil.isNullOrBlank(notificationCriteriaValue)) {
                notifications = notificationService.getAll();
            } else {
                NotificationCriteriaDto notificationCriteria = NotificationDataAssembler.createNotification(new JSONObject(notificationCriteriaValue));
                notifications = notificationService.getAll(notificationCriteria);
            }
            
            if (notifications != null) {
                for(NotificationDto notification : notifications) {
                    JSONArray row = new JSONArray();
                    row.put(notification.getNotificationId());
                    row.put(notification.getNotificationEndPoint().getValue());
                    row.put(notification.getNotificationMode().toString());
                    row.put(notification.getNotificationType().getValue());
                    data.put(row);
                }
            }
        } finally {
            HttpUtil.wrapAndWriteJson(response, "NotificationTemplates", data);
        }
        return null;
    }

    @RequestMapping(value="jsonListNotificationEndPoints")
    public ModelAndView jsonListNotificationEndPoints(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONArray data = new JSONArray();
        try {
            NotificationEndPoint[] notificationEndPoints = NotificationEndPoint.values();
            if (notificationEndPoints != null) {
                for(NotificationEndPoint notificationEndPoint : notificationEndPoints) {
                    JSONArray row = new JSONArray();
                    row.put(notificationEndPoint.getId());
                    row.put(notificationEndPoint.getValue());
                    data.put(row);
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
        return null;
    }

    @RequestMapping(value="jsonListNotificationModes")
    public ModelAndView jsonListNotificationModes(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONArray data = new JSONArray();
        try {
            NotificationMode[] notificationModes = NotificationMode.values();
            if (notificationModes != null) {
                for(NotificationMode notificationMode : notificationModes) {
                    JSONArray row = new JSONArray();
                    row.put(notificationMode.getId());
                    row.put(notificationMode.toString());
                    data.put(row);
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
        return null;
    }

    @RequestMapping(value="jsonListNotificationTypes")
    public ModelAndView jsonListNotificationTypes(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONArray data = new JSONArray();
        try {
            NotificationType[] notificationTypes = NotificationType.values();
            if (notificationTypes != null) {
                for(NotificationType notificationType : notificationTypes) {
                    JSONArray row = new JSONArray();
                    row.put(notificationType.getId());
                    row.put(notificationType.getValue());
                    data.put(row);
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
        return null;
    }

    /**
     * List students.
     *
     * @deprecated move to student.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="listStudents")
    public ModelAndView listStudents(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateWholePageView(request, NotificationViewNames.VIEW_NOTIFICATION_TO_STUDENTS);
    }

    /**
     * @deprecated move to student
     * Json list parents.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonListParents")
    public ModelAndView jsonListParents(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONArray data = new JSONArray();
        try {
            List<NotificationEndPoint> notificationEndPoints = new ArrayList<NotificationEndPoint>();
            notificationEndPoints.add(NotificationEndPoint.STUDENT);
            notificationEndPoints.add(NotificationEndPoint.NOTICE_BOARD);
            List<NotificationDto> notifications = notificationService.getAll(notificationEndPoints);
            if (notifications != null) {
                for(NotificationDto notification : notifications) {
                    JSONArray row = new JSONArray();
                    row.put(notification.getNotificationId());
                    row.put(notification.getNotificationEndPoint().getValue());
                    row.put(notification.getNotificationMode().toString());
                    row.put(notification.getNotificationType().getValue());
                    data.put(row);
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
        return null;
    }

    /**
     * @deprecated move to employee
     * List employees.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="listEmployees")
    public ModelAndView listEmployees(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateWholePageView(request, NotificationViewNames.VIEW_NOTIFICATION_TO_EMPLOYEES);
    }

    /**
     * @deprecated move to employee
     * Json list employees.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonListEmployees")
    public ModelAndView jsonListEmployees(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONArray data = new JSONArray();
        try {
            List<NotificationEndPoint> notificationEndPoints = new ArrayList<NotificationEndPoint>();
            notificationEndPoints.add(NotificationEndPoint.EMPLOYEE);
            notificationEndPoints.add(NotificationEndPoint.NOTICE_BOARD);
            
            List<NotificationDto> notifications = notificationService.getAll(notificationEndPoints);
            
            if (notifications != null) {
                for(NotificationDto notification : notifications) {
                    JSONArray row = new JSONArray();
                    row.put(notification.getNotificationId());
                    row.put(notification.getNotificationEndPoint().getValue());
                    row.put(notification.getNotificationMode().toString());
                    row.put(notification.getNotificationType().getValue());
                    data.put(row);
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
        return null;
    }

    /**
     * Gets the template.
     *
     * @param request the request
     * @param response the response
     * @return the template
     */
    @RequestMapping(value="getTemplate")
    public ModelAndView getTemplate(HttpServletRequest request,
            HttpServletResponse response) {
        String templateText = null;
        try {
            String templateIdValue = request.getParameter("templateId");
            String deviceValue = request.getParameter("device");
            if (!StringUtil.isNullOrBlank(templateIdValue)) {
                int templateId = Integer.parseInt(templateIdValue);
                Device device = Device.getDevice(deviceValue);
                if (device == null) {
                    templateText = notificationService.getTemplateText(templateId);
                } else {
                    templateText = notificationService.getTextInDevice(device, templateId);
                }
                if (templateText != null) {
                    HttpUtil.writeToResponse(response, templateText);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Notify end points.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="notifyEndPoints")
    public ModelAndView notifyEndPoints(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
            String notificationTemplateIdValue = request.getParameter("notificationTemplateId");
            if (!StringUtil.isNullOrBlank(notificationTemplateIdValue)) {
                int notificationTemplateId = Integer.parseInt(notificationTemplateIdValue);
                NotificationDto notification = notificationService.get(notificationTemplateId);
                if (notification != null) {
                    String sendToIds = request.getParameter("sendToIds");
                    List<Integer> notifyingIds = StringUtil.toCollectionsOfIntegers(sendToIds);
                    if (notifyingIds != null && !notifyingIds.isEmpty()) {
                        notification.setNotifyingIds(notifyingIds);
                        result.setSuccessful(notificationService.create(notification));
                    }
                }
            }
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

}
