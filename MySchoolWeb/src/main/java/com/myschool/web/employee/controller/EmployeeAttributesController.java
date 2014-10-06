package com.myschool.web.employee.controller;

import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
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

import com.myschool.clazz.dto.RegisteredSubjectDto;
import com.myschool.clazz.service.RegisteredSubjectService;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.employee.assembler.EmployeeDocumentDataAssembler;
import com.myschool.employee.assembler.EmployeeEducationDataAssembler;
import com.myschool.employee.assembler.EmployeeExperienceDataAssembler;
import com.myschool.employee.assembler.EmployeePromotionDataAssembler;
import com.myschool.employee.assembler.EmployeeSubjectDataAssembler;
import com.myschool.employee.dto.EmployeeDocument;
import com.myschool.employee.dto.EmployeeEducation;
import com.myschool.employee.dto.EmployeeExperience;
import com.myschool.employee.dto.EmployeePromotion;
import com.myschool.employee.dto.EmployeeSubjectDto;
import com.myschool.employee.service.EmployeeDocumentService;
import com.myschool.employee.service.EmployeeEducationService;
import com.myschool.employee.service.EmployeeExperienceService;
import com.myschool.employee.service.EmployeePromotionService;
import com.myschool.employee.service.EmployeeService;
import com.myschool.employee.service.EmployeeSubjectService;
import com.myschool.infra.web.constants.MimeTypes;
import com.myschool.web.common.parser.ResponseParser;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.employee.constants.EmployeeViewNames;

/**
 * The Class EmployeeAttributesController.
 */
@Controller
@RequestMapping("employee-attribute")
public class EmployeeAttributesController {

    /** The Constant EMPLOYEE_DOCUMENT. */
    private static final String EMPLOYEE_DOCUMENT = "EmployeeDocument";

    /** The Constant EMPLOYEE_EDUCATION. */
    private static final String EMPLOYEE_EDUCATION = "EmployeeEducation";

    /** The Constant EMPLOYEE_EXPERIENCE. */
    private static final String EMPLOYEE_EXPERIENCE = "EmployeeExperience";

    /** The Constant EMPLOYEE_PROMOTION. */
    private static final String EMPLOYEE_PROMOTION = "EmployeePromotion";

    /** The Constant EMPLOYEE_TEACHING_SUBJECT. */
    private static final String EMPLOYEE_TEACHING_SUBJECT = "EmployeeTeachingSubject";

    /** The employee service. */
    @Autowired
    private EmployeeService employeeService;

    /** The employee document service. */
    @Autowired
    private EmployeeDocumentService employeeDocumentService;

    /** The employee education service. */
    @Autowired
    private EmployeeEducationService employeeEducationService;

    /** The employee experience service. */
    @Autowired
    private EmployeeExperienceService employeeExperienceService;

    /** The employee promotion service. */
    @Autowired
    private EmployeePromotionService employeePromotionService;

    /** The employee subject service. */
    @Autowired
    private EmployeeSubjectService employeeSubjectService;

    @Autowired
    private RegisteredSubjectService registeredSubjectService;

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
        String viewName = null;
        Object attributeObject = null;
        Map<String, Object> map = new HashMap<String, Object>();

        String employeeNumber = request.getParameter("EmployeeNumber");
        String attribute = request.getParameter("attribute");
        String attributeIdVal = request.getParameter("attributeId");

        if (!StringUtil.isNullOrBlank(attribute)) {
            int attributeId = 0;
            if (!StringUtil.isNullOrBlank(attributeIdVal)) {
                attributeId = Integer.parseInt(attributeIdVal);
            }
            if (!StringUtil.isNullOrBlank(employeeNumber)) {
                map.put("Employee", employeeService.get(employeeNumber));
            }
            if (attribute.equals(EMPLOYEE_DOCUMENT)) {
                viewName = EmployeeViewNames.MAINTAIN_EMPLOYEE_DOCUMENT;
                if (attributeId == 0) {
                    JSONObject jsonObject = getJson(request.getParameter(attribute));
                    attributeObject = getAttributeObject(attribute, jsonObject);
                } else {
                    attributeObject = employeeDocumentService.get(attributeId);
                }
            } else if (attribute.equals(EMPLOYEE_EDUCATION)) {
                viewName = EmployeeViewNames.MAINTAIN_EMPLOYEE_EDUCATION;
                if (attributeId == 0) {
                    JSONObject jsonObject = getJson(request.getParameter(attribute));
                    attributeObject = getAttributeObject(attribute, jsonObject);
                } else {
                    attributeObject = employeeEducationService.get(attributeId);
                }
            } else if (attribute.equals(EMPLOYEE_EXPERIENCE)) {
                viewName = EmployeeViewNames.MAINTAIN_EMPLOYEE_EXPERIENCE;
                if (attributeId == 0) {
                    JSONObject jsonObject = getJson(request.getParameter(attribute));
                    attributeObject = getAttributeObject(attribute, jsonObject);
                } else {
                    attributeObject = employeeExperienceService.get(attributeId);
                }
            } else if (attribute.equals(EMPLOYEE_PROMOTION)) {
                viewName = EmployeeViewNames.MAINTAIN_EMPLOYEE_PROMOTION;
                if (attributeId == 0) {
                    JSONObject jsonObject = getJson(request.getParameter(attribute));
                    attributeObject = getAttributeObject(attribute, jsonObject);
                } else {
                    attributeObject = employeePromotionService.get(attributeId);
                }
            } else if (attribute.equals(EMPLOYEE_TEACHING_SUBJECT)) {
                viewName = EmployeeViewNames.MAINTAIN_EMPLOYEE_TEACHING_SUBJECT;
                if (attributeId == 0) {
                    JSONObject jsonObject = getJson(request.getParameter(attribute));
                    attributeObject = getAttributeObject(attribute, jsonObject);
                } else {
                    attributeObject = employeeSubjectService.get(attributeId);
                }
            }
            map.put(attribute, attributeObject);
        }
        return ViewDelegationController.delegateModelPageView(request, viewName, map);
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
        JSONObject jsonResponse = new JSONObject();
        JSONArray jsonEmployeeAttributes = null;

        String employeeNumber = request.getParameter("EmployeeNumber");
        String attribute = request.getParameter("attribute");

        if (!StringUtil.isNullOrBlank(attribute) && !StringUtil.isNullOrBlank(employeeNumber)) {
            if (attribute.equals(EMPLOYEE_DOCUMENT)) {
                jsonEmployeeAttributes = EmployeeDocumentDataAssembler.create(
                        employeeDocumentService.getByEmployee(employeeNumber));
            } else if (attribute.equals(EMPLOYEE_EDUCATION)) {
                jsonEmployeeAttributes = EmployeeEducationDataAssembler.create(
                        employeeEducationService.getByEmployee(employeeNumber));
            } else if (attribute.equals(EMPLOYEE_EXPERIENCE)) {
                jsonEmployeeAttributes = EmployeeExperienceDataAssembler.create(
                        employeeExperienceService.getByEmployee(employeeNumber));
            } else if (attribute.equals(EMPLOYEE_PROMOTION)) {
                jsonEmployeeAttributes = EmployeePromotionDataAssembler.create(
                        employeePromotionService.getByEmployee(employeeNumber));
            } else if (attribute.equals(EMPLOYEE_TEACHING_SUBJECT)) {
                jsonEmployeeAttributes = EmployeeSubjectDataAssembler.create(
                        employeeSubjectService.getByEmployee(employeeNumber));
            }
        }
        if (jsonEmployeeAttributes == null) {
            jsonEmployeeAttributes = new JSONArray();
        }
        jsonResponse.put(DataTypeValidator.AA_DATA, jsonEmployeeAttributes);
        response.setContentType(MimeTypes.APPLICATION_JSON);
        PrintWriter writer = response.getWriter();
        writer.print(jsonResponse.toString());
        writer.close();
        return null;
    }

    /**
     * Validate.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doValidate")
    public ModelAndView doValidate(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
            String attributeName = request.getParameter("AttributeName");
            if (attributeName != null && !StringUtil.isEmpty(attributeName)) {
                JSONArray jsonObjectArray = new JSONArray(request.getParameter("AttributeData"));
                if (jsonObjectArray != null && jsonObjectArray.length() != 0) {
                    List<Object> attributeObjects = new ArrayList<Object>();
                    for (int index = 0; index < jsonObjectArray.length(); index++) {
                        JSONObject jsonObject = (JSONObject) jsonObjectArray.get(index);
                        if (jsonObject != null) {
                            Object attributeObject = getAttributeObject(attributeName, jsonObject);
                            attributeObjects.add(attributeObject);
                        }
                    }
                    if (attributeName.equals(EMPLOYEE_DOCUMENT)) {
                        List<EmployeeDocument> employeeDocuments = new ArrayList<EmployeeDocument>();
                        for (Object attributeObject : attributeObjects) {
                            employeeDocuments.add((EmployeeDocument) attributeObject);
                        }
                        employeeDocumentService.validate(employeeDocuments);
                    } else if (attributeName.equals(EMPLOYEE_EDUCATION)) {
                        List<EmployeeEducation> employeeEducations = new ArrayList<EmployeeEducation>();
                        for (Object attributeObject : attributeObjects) {
                            employeeEducations.add((EmployeeEducation) attributeObject);
                        }
                        employeeEducationService.validate(employeeEducations);
                    } else if (attributeName.equals(EMPLOYEE_EXPERIENCE)) {
                        List<EmployeeExperience> employeeExperiences = new ArrayList<EmployeeExperience>();
                        for (Object attributeObject : attributeObjects) {
                            employeeExperiences.add((EmployeeExperience) attributeObject);
                        }
                        employeeExperienceService.validate(employeeExperiences);
                    } else if (attributeName.equals(EMPLOYEE_PROMOTION)) {
                        List<EmployeePromotion> employeePromotions = new ArrayList<EmployeePromotion>();
                        for (Object attributeObject : attributeObjects) {
                            employeePromotions.add((EmployeePromotion) attributeObject);
                        }
                        employeePromotionService.validate(employeePromotions);
                    } else if (attributeName.equals(EMPLOYEE_TEACHING_SUBJECT)) {
                        List<EmployeeSubjectDto> employeeSubjects = new ArrayList<EmployeeSubjectDto>();
                        for (Object attributeObject : attributeObjects) {
                            employeeSubjects.add((EmployeeSubjectDto) attributeObject);
                        }
                        employeeSubjectService.validate(employeeSubjects);
                    }
                    attributeObjects.clear();
                }
                result.setSuccessful(Boolean.TRUE);
            }
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeJson(response, result);
        }
        return null;
    }

    /**
     * Update.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doUpdate")
    public ModelAndView update(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        boolean updated = false;
        String message = null;
        ResultDto result = new ResultDto();
        try {
            String attribute = request.getParameter("attribute");
            if (attribute != null && !StringUtil.isEmpty(attribute)) {
                Object attributeObject = getAttributeObject(attribute, getJson(request.getParameter(attribute)));
                if (attribute.equals(EMPLOYEE_DOCUMENT)) {
                    EmployeeDocument employeeDocument = (EmployeeDocument) attributeObject;
                    updated = employeeDocumentService.update(employeeDocument.getEmployeeDocumentId(), employeeDocument);
                    message = "Employee Document has been updated successfully.";
                } else if (attribute.equals(EMPLOYEE_EDUCATION)) {
                    EmployeeEducation employeeEducation = (EmployeeEducation) attributeObject;
                    updated = employeeEducationService.update(employeeEducation.getEducationId(), employeeEducation);
                    message = "Employee Education has been updated successfully.";
                } else if (attribute.equals(EMPLOYEE_EXPERIENCE)) {
                    EmployeeExperience employeeExperience = (EmployeeExperience) attributeObject;
                    updated = employeeExperienceService.update(employeeExperience.getExperienceId(), employeeExperience);
                    message = "Employee Experience has been updated successfully.";
                } else if (attribute.equals(EMPLOYEE_PROMOTION)) {
                    EmployeePromotion employeePromotion = (EmployeePromotion) attributeObject;
                    updated = employeePromotionService.update(employeePromotion.getPromotionId(), employeePromotion);
                    message = "Employee Promotion has been updated successfully.";
                } else if (attribute.equals(EMPLOYEE_TEACHING_SUBJECT)) {
                    EmployeeSubjectDto employeeSubject = (EmployeeSubjectDto) attributeObject;
                    RegisteredSubjectDto newRegisteredSubject = new RegisteredSubjectDto();
                    newRegisteredSubject.setSubjectId(Integer.parseInt(request.getParameter("NewRegististeredSubjectId")));
                    updated = employeeSubjectService.update(employeeSubject.getEmployeeSubjectId(), employeeSubject);
                    message = "Employee Subject has been updated successfully.";
                }
            }
            if (updated) {
                result.setSuccessful(Boolean.TRUE);
                result.setStatusMessage(message);
            }
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeResponse(response, result);
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
        boolean deleted = false;
        String message = null;
        ResultDto result = new ResultDto();
        try {
            String attribute = request.getParameter("attribute");
            String attributeId = request.getParameter("attributeId");
            if (attribute != null && !StringUtil.isEmpty(attribute)
                    && attributeId != null && !StringUtil.isEmpty(attributeId)) {
                int employeeAttributeId = Integer.parseInt(attributeId);
                if (attribute.equals(EMPLOYEE_DOCUMENT)) {
                    if (employeeAttributeId > 0) {
                        deleted = employeeDocumentService.delete(employeeAttributeId);
                    }
                    message = "Employee Document has been deleted successfully.";
                } else if (attribute.equals(EMPLOYEE_EDUCATION)) {
                    if (employeeAttributeId > 0) {
                        deleted = employeeEducationService.delete(employeeAttributeId);
                    }
                    message = "Employee Education has been deleted successfully.";
                } else if (attribute.equals(EMPLOYEE_EXPERIENCE)) {
                    if (employeeAttributeId > 0) {
                        deleted = employeeExperienceService.delete(employeeAttributeId);
                    }
                    message = "Employee Experience has been deleted successfully.";
                } else if (attribute.equals(EMPLOYEE_PROMOTION)) {
                    if (employeeAttributeId > 0) {
                        deleted = employeePromotionService.delete(employeeAttributeId);
                    }
                    message = "Employee Promotion has been deleted successfully.";
                } else if (attribute.equals(EMPLOYEE_TEACHING_SUBJECT)) {
                    if (employeeAttributeId > 0) {
                        RegisteredSubjectDto registeredSubject = new RegisteredSubjectDto();
                        registeredSubject.setSubjectId(employeeAttributeId);
                        deleted = employeeSubjectService.delete(Integer.parseInt(attributeId));
                    }
                    message = "Employee Subject has been deleted successfully.";
                }
                if (employeeAttributeId == 0) {
                    deleted = true;
                }
            }
            if (deleted) {
                result.setSuccessful(Boolean.TRUE);
                result.setStatusMessage(message);
            }
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeResponse(response, result);
        }
        return null;
    }

    /**
     * Gets the attribute object.
     * 
     * @param attribute the attribute
     * @param jsonObject the json object
     * @return the attribute object
     */
    private Object getAttributeObject(String attribute, JSONObject jsonObject) {
        Object attributeObject = null;
        if (attribute.equals(EMPLOYEE_DOCUMENT)) {
            attributeObject = EmployeeDocumentDataAssembler.create(jsonObject);
        } else if (attribute.equals(EMPLOYEE_EDUCATION)) {
            attributeObject = EmployeeEducationDataAssembler.create(jsonObject);
        } else if (attribute.equals(EMPLOYEE_EXPERIENCE)) {
            attributeObject = EmployeeExperienceDataAssembler.create(jsonObject);
        } else if (attribute.equals(EMPLOYEE_PROMOTION)) {
            attributeObject = EmployeePromotionDataAssembler.create(jsonObject);
        } else if (attribute.equals(EMPLOYEE_TEACHING_SUBJECT)) {
            attributeObject = EmployeeSubjectDataAssembler.create(jsonObject);
        }
        return attributeObject;
    }

    /**
     * Gets the json.
     * 
     * @param parameterValue the parameter value
     * @return the json
     * @throws ParseException the parse exception
     */
    private JSONObject getJson(String parameterValue) throws ParseException {
        JSONObject jsonObject = null;
        if (!StringUtil.isNullOrBlank(parameterValue)) {
            jsonObject = new JSONObject(parameterValue);
        }
        return jsonObject;
    }

}
