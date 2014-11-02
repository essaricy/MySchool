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

import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.clazz.dto.RegisteredSubjectDto;
import com.myschool.clazz.dto.SubjectDto;
import com.myschool.clazz.service.ClassService;
import com.myschool.clazz.service.RegisteredClassService;
import com.myschool.clazz.service.RegisteredSubjectService;
import com.myschool.clazz.service.SubjectService;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.web.clazz.constants.ClazzViewNames;
import com.myschool.web.common.util.HttpUtil;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.common.util.ViewErrorHandler;

/**
 * The Class RegisteredSubjectController.
 */
@Controller
@RequestMapping("registeredSubject")
public class RegisteredSubjectController {

    /** The subject service. */
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private RegisteredSubjectService registeredSubjectService;

    /** The class service. */
    @Autowired
    private ClassService classService;

    @Autowired
    private RegisteredClassService registeredClassService;

    /** The view error handler. */
    @Autowired
    private ViewErrorHandler viewErrorHandler;

    /**
     * List registered subjects.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="list")
    public ModelAndView listRegisteredSubjects(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateWholePageView(request, ClazzViewNames.VIEW_REGISTERED_SUBJECTS);
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
            String classId = request.getParameter("classId");
            if (classId != null && !StringUtil.isEmpty(classId)) {
                List<RegisteredSubjectDto> registeredSubjects = registeredSubjectService.getByClass(Integer.parseInt(classId));
                if (registeredSubjects != null) {
                    for(RegisteredSubjectDto registeredSubject : registeredSubjects) {
                        JSONArray row = new JSONArray();
                        row.put(registeredSubject.getSubjectId());
                        SubjectDto subject = registeredSubject.getSubject();
                        row.put(subject.getSubjectId());
                        row.put(subject.getSubjectName());
                        data.put(row);
                    }
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
        return null;
    }

    /**
     * Launch create registered subject.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
   @RequestMapping(value="launchNew")
   public ModelAndView launchCreateRegisteredSubject(HttpServletRequest request,
           HttpServletResponse response) throws Exception {
       Map<String, Object> map = new HashMap<String, Object>();
       String classId = request.getParameter("classId");
       if (!StringUtil.isNullOrBlank(classId)) {
           map.put("registeredClass", registeredClassService.get(Integer.parseInt(classId)));
           map.put("availableSubjects", subjectService.getAll());
       }
       return ViewDelegationController.delegateModelPageView(request, ClazzViewNames.MAINTAIN_REGISTERED_SUBJECT, map);
   }

   /**
    * Launch update registered subject.
    *
    * @param request the request
    * @param response the response
    * @return the model and view
    * @throws Exception the exception
    */
   @RequestMapping(value="launchUpdate")
   public ModelAndView launchUpdateRegisteredSubject(HttpServletRequest request,
           HttpServletResponse response) throws Exception {
       Map<String, Object> map = new HashMap<String, Object>();
       String classId = request.getParameter("classId");
       if (!StringUtil.isNullOrBlank(classId)) {
           map.put("registeredClass", registeredClassService.get(Integer.parseInt(classId)));
           map.put("availableSubjects", subjectService.getAll());

           String registeredSubjectId = request.getParameter("registeredSubjectId");
           if (!StringUtil.isNullOrBlank(registeredSubjectId)) {
               map.put("registeredSubject", registeredSubjectService.get(Integer.parseInt(registeredSubjectId)));
           }
       }
       return ViewDelegationController.delegateModelPageView(request, ClazzViewNames.MAINTAIN_REGISTERED_SUBJECT, map);
   }

   /**
    * Do create registered subject.
    *
    * @param request the request
    * @param response the response
    * @return the model and view
    * @throws Exception the exception
    */
   @RequestMapping(value="doCreate")
   public ModelAndView doCreateRegisteredSubject(HttpServletRequest request,
           HttpServletResponse response) throws Exception {

       ResultDto result = new ResultDto();
       try {
           RegisteredSubjectDto registeredSubject = getRegisteredSubject(0, request);
           boolean success = registeredSubjectService.create(registeredSubject);
           if (success) {
               result.setSuccessful(ResultDto.SUCCESS);
               result.setStatusMessage(viewErrorHandler.getMessage("subject.created"));
           }
       } catch (ServiceException serviceException) {
           result.setStatusMessage(serviceException.getMessage());
       } finally {
           HttpUtil.writeAsJson(response, result);
       }
       return null;
   }

   /**
    * Do update registered subject.
    *
    * @param request the request
    * @param response the response
    * @return the model and view
    * @throws Exception the exception
    */
   @RequestMapping(value="doUpdate")
   public ModelAndView doUpdateRegisteredSubject(HttpServletRequest request,
           HttpServletResponse response) throws Exception {

       ResultDto result = new ResultDto();
       try {
           String registeredSubjectIdValue = request.getParameter("RegisteredSubjectId");
           if (!StringUtil.isNullOrBlank(registeredSubjectIdValue)) {
               int registeredSubjectId = Integer.parseInt(registeredSubjectIdValue);
               if (registeredSubjectId > 0) {
                   RegisteredSubjectDto registeredSubject = getRegisteredSubject(registeredSubjectId, request);
                   boolean success = registeredSubjectService.update(registeredSubjectId, registeredSubject);
                   if (success) {
                       result.setSuccessful(ResultDto.SUCCESS);
                       result.setStatusMessage(viewErrorHandler.getMessage("subject.updated"));
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
           String registeredSubjectIdValue = request.getParameter("RegisteredSubjectId");
           if (!StringUtil.isNullOrBlank(registeredSubjectIdValue)) {
               int registeredSubjectId = Integer.parseInt(registeredSubjectIdValue);
               if (registeredSubjectId > 0) {
                   boolean success = registeredSubjectService.delete(registeredSubjectId);
                   if (success) {
                       result.setSuccessful(ResultDto.SUCCESS);
                       result.setStatusMessage(viewErrorHandler.getMessage("subject.deleted"));
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

   /**
    * Gets the registered subject.
    *
    * @param registeredSubjectId the registered subject id
    * @param request the request
    * @return the registered subject
    */
   private RegisteredSubjectDto getRegisteredSubject(int registeredSubjectId, HttpServletRequest request) {
       RegisteredSubjectDto registeredSubject = new RegisteredSubjectDto();
       RegisteredClassDto registeredClass = new RegisteredClassDto();
       registeredClass.setClassId(Integer.parseInt(request.getParameter("RegisteredClassId")));
       registeredSubject.setRegisteredClass(registeredClass);
       registeredSubject.setSubjectId(registeredSubjectId);
       SubjectDto subject = new SubjectDto();
       subject.setSubjectId(Integer.parseInt(request.getParameter("SubjectId")));
       registeredSubject.setSubject(subject);
       return registeredSubject;
   }

}
