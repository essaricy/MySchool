package com.myschool.web.student.controller;

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

import com.myschool.common.dto.FamilyMemberDto;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.student.assembler.StudentDocumentDataAssembler;
import com.myschool.student.assembler.StudentFamilyDataAssembler;
import com.myschool.student.dto.StudentDocument;
import com.myschool.student.service.StudentDocumentService;
import com.myschool.student.service.StudentFamilyService;
import com.myschool.student.service.StudentService;
import com.myschool.web.common.util.HttpUtil;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.student.constants.StudentViewNames;

/**
 * The Class StudentAttributesController.
 */
@Controller
@RequestMapping("student-attribute")
public class StudentAttributesController {

    /** The Constant STUDENT_FAMILY_MEMBER. */
    private static final String STUDENT_FAMILY_MEMBER = "StudentFamilyMember";

    /** The Constant STUDENT_DOCUMENT. */
    private static final String STUDENT_DOCUMENT = "StudentDocument";

    /** The student service. */
    @Autowired
    private StudentService studentService;

    /** The student document service. */
    @Autowired
    private StudentDocumentService studentDocumentService;

    /** The student family service. */
    @Autowired
    private StudentFamilyService studentFamilyService;

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

        String admissionNumber = request.getParameter("AdmissionNumber");
        String attribute = request.getParameter("attribute");
        String attributeIdVal = request.getParameter("attributeId");

        if (!StringUtil.isNullOrBlank(attribute)) {
            int attributeId = 0;
            if (!StringUtil.isNullOrBlank(attributeIdVal)) {
                attributeId = Integer.parseInt(attributeIdVal);
            }
            if (!StringUtil.isNullOrBlank(admissionNumber)) {
                map.put("Student", studentService.get(admissionNumber));
            }
            if (attribute.equals(STUDENT_DOCUMENT)) {
                viewName = StudentViewNames.MAINTAIN_STUDENT_DOCUMENT;
                if (attributeId == 0) {
                    JSONObject jsonObject = getJson(request.getParameter(attribute));
                    attributeObject = getAttributeObject(attribute, jsonObject);
                } else {
                    attributeObject = studentDocumentService.get(attributeId);
                }
            } else if (attribute.equals(STUDENT_FAMILY_MEMBER)) {
                viewName = StudentViewNames.MAINTAIN_STUDENT_FAMILY_MEMBER;
                if (attributeId == 0) {
                    JSONObject jsonObject = getJson(request.getParameter(attribute));
                    attributeObject = getAttributeObject(attribute, jsonObject);
                } else {
                    attributeObject = studentFamilyService.get(attributeId);
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
        JSONArray data = null;
        try {
            String admissionNumber = request.getParameter("AdmissionNumber");
            String attribute = request.getParameter("attribute");
            System.out.println("AdmissionNumber " + admissionNumber);
            if (!StringUtil.isNullOrBlank(attribute) && !StringUtil.isNullOrBlank(admissionNumber)) {
                if (attribute.equals(STUDENT_DOCUMENT)) {
                    data = StudentDocumentDataAssembler.create(
                            studentDocumentService.getByStudent(admissionNumber));
                } else if (attribute.equals(STUDENT_FAMILY_MEMBER)) {
                    data = StudentFamilyDataAssembler.create(
                            studentFamilyService.getByStudent(admissionNumber));
                }
            }
            if (data == null) {
                data = new JSONArray();
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
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
            System.out.println("attributeName " + attributeName);
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
                    if (attributeName.equals(STUDENT_DOCUMENT)) {
                        List<StudentDocument> studentDocuments = new ArrayList<StudentDocument>();
                        for (Object attributeObject : attributeObjects) {
                            studentDocuments.add((StudentDocument) attributeObject);
                        }
                        studentDocumentService.validate(studentDocuments);
                    } else if (attributeName.equals(STUDENT_FAMILY_MEMBER)) {
                        List<FamilyMemberDto> familyMembers = new ArrayList<FamilyMemberDto>();
                        for (Object attributeObject : attributeObjects) {
                            familyMembers.add((FamilyMemberDto) attributeObject);
                        }
                        studentFamilyService.validate(familyMembers);
                    }
                    attributeObjects.clear();
                }
                result.setSuccessful(Boolean.TRUE);
            }
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
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
            System.out.println("attribute " + attribute);
            if (attribute != null && !StringUtil.isEmpty(attribute)) {
                Object attributeObject = getAttributeObject(attribute, getJson(request.getParameter(attribute)));
                if (attribute.equals(STUDENT_DOCUMENT)) {
                    StudentDocument studentDocument = (StudentDocument) attributeObject;
                    updated = studentDocumentService.update(studentDocument.getStudentDocumentId(), studentDocument);
                    message = "Student Document has been updated successfully.";
                } else if (attribute.equals(STUDENT_FAMILY_MEMBER)) {
                    FamilyMemberDto familyMember = (FamilyMemberDto) attributeObject;
                    updated = studentFamilyService.update(familyMember.getFamilyMemberId(), familyMember);
                    message = "Family Member has been updated successfully.";
                }
            }
            if (updated) {
                result.setSuccessful(Boolean.TRUE);
                result.setStatusMessage(message);
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
        boolean deleted = false;
        String message = null;
        ResultDto result = new ResultDto();
        try {
            String attribute = request.getParameter("attribute");
            String attributeId = request.getParameter("attributeId");
            if (attribute != null && !StringUtil.isEmpty(attribute)
                    && attributeId != null && !StringUtil.isEmpty(attributeId)) {
                int studentAttributeId = Integer.parseInt(attributeId);
                if (attribute.equals(STUDENT_DOCUMENT)) {
                    if (studentAttributeId > 0) {
                        deleted = studentDocumentService.delete(studentAttributeId);
                    }
                    message = "Student Document has been deleted successfully.";
                } else if (attribute.equals(STUDENT_FAMILY_MEMBER)) {
                    if (studentAttributeId > 0) {
                        deleted = studentFamilyService.delete(studentAttributeId);
                    }
                    message = "Family Member has been deleted successfully.";
                }
                if (studentAttributeId == 0) {
                    deleted = true;
                }
            }
            if (deleted) {
                result.setSuccessful(Boolean.TRUE);
                result.setStatusMessage(message);
            }
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
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
        if (attribute.equals(STUDENT_DOCUMENT)) {
            attributeObject = StudentDocumentDataAssembler.create(jsonObject);
        } else if (attribute.equals(STUDENT_FAMILY_MEMBER)) {
            attributeObject = StudentFamilyDataAssembler.create(jsonObject);
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
