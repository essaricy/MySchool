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

import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.DivisionDto;
import com.myschool.branch.dto.RegionDto;
import com.myschool.branch.dto.StateDto;
import com.myschool.clazz.dto.ClassDto;
import com.myschool.clazz.dto.MediumDto;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.clazz.dto.SectionDto;
import com.myschool.clazz.service.ClassService;
import com.myschool.clazz.service.MediumService;
import com.myschool.clazz.service.RegisteredClassService;
import com.myschool.clazz.service.SectionService;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.school.dto.SchoolDto;
import com.myschool.school.service.SchoolService;
import com.myschool.web.clazz.constants.ClazzViewNames;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.handler.ViewErrorHandler;
import com.myschool.web.framework.util.HttpUtil;

/**
 * The Class ClassController.
 */
@Controller
@RequestMapping("class")
public class ClassController {

    /** The class service. */
    @Autowired
    private ClassService classService;

    /** The registered class service. */
    @Autowired
    private RegisteredClassService registeredClassService;

    /** The school service. */
    @Autowired
    private SchoolService schoolService;

    /** The medium service. */
    @Autowired
    private MediumService mediumService;
    
    /** The section service. */
    @Autowired
    private SectionService sectionService;

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
        return ViewDelegationController.delegateWholePageView(request, ClazzViewNames.VIEW_CLASSES);
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
            List<ClassDto> classes = classService.getAll();
            if (classes != null) {
                for(ClassDto classDto : classes) {
                    JSONArray row = new JSONArray();
                    row.put(classDto.getClassId()).put(classDto.getClassName()).put(classDto.getPromotionOrder());
                    data.put(row);
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
        return null;
    }

    /*@RequestMapping(value="jsonListRegistered")
    public ModelAndView jsonListRegistered(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONArray data = new JSONArray();
        JSONObject jsonResponse = new JSONObject();
        List<RegisteredClassDto> registeredClasses = registeredClassService.getAll();
        if (registeredClasses != null) {
            for(RegisteredClassDto registeredClass : registeredClasses) {
                ClassDto classDto = registeredClass.getClassDto();
                MediumDto medium = registeredClass.getMedium();
                SectionDto section = registeredClass.getSection();
                SchoolDto school = registeredClass.getSchool();
                JSONArray row = new JSONArray();
                row.put(registeredClass.getClassId());
                row.put(classDto.getClassId());
                row.put(classDto.getClassName());
                row.put(medium.getMediumId());
                row.put(medium.getDescription());
                row.put(section.getSectionId());
                row.put(section.getSectionName());
                row.put(school.getSchoolId());
                row.put(school.getSchoolName());
                data.put(row);
            }
        }
        jsonResponse.put(DataTypeValidator.AA_DATA, data);
        response.setContentType(MimeTypes.APPLICATION_JSON);
        return null;
    }*/

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
        return ViewDelegationController.delegateModelPageView(request, ClazzViewNames.MAINTAIN_CLASS);
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

        ResultDto result = new ResultDto();

        try {
            ClassDto classDto = validateAndGetClass(request);
            result.setSuccessful(classService.create(classDto));
        } catch (DataException dataException) {
            result.setStatusMessage(viewErrorHandler.getMessage(dataException.getMessage()));
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Validate and get class.
     *
     * @param request the request
     * @return the class dto
     * @throws DataException the data exception
     */
    private ClassDto validateAndGetClass(HttpServletRequest request) throws DataException {
        ClassDto classDto = new ClassDto();
        String className = request.getParameter("className");
        String promotionOrder = request.getParameter("promotionOrder");

        viewErrorHandler.validate(className, "className", DataTypeValidator.ANY_CHARACTER, true);
        viewErrorHandler.validate(promotionOrder, "promotionOrder", DataTypeValidator.INTEGER, true);

        classDto.setClassName(className);
        classDto.setPromotionOrder(Integer.parseInt(promotionOrder));
        return classDto;
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
        String classId = request.getParameter("classId");

        if (classId != null && classId.trim().length() != 0) {
            ClassDto classDto = classService.get(Integer.parseInt(classId));
            map.put("classDto", classDto);
        }
        return ViewDelegationController.delegateModelPageView(request, ClazzViewNames.MAINTAIN_CLASS, map);
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

        ResultDto result = new ResultDto();

        try {
            String classId = request.getParameter("classId");
            ClassDto classDto = validateAndGetClass(request);
            result.setSuccessful(classService.update(Integer.parseInt(classId), classDto));
        } catch (DataException dataException) {
            result.setStatusMessage(viewErrorHandler.getMessage(dataException.getMessage()));
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
            String classId = request.getParameter("classId");
            result.setSuccessful(classService.delete(Integer.parseInt(classId)));
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * List by school.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="listRegisteredClasses")
    public ModelAndView listRegisteredClasses(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateWholePageView(request, ClazzViewNames.VIEW_REGISTERED_CLASSES);
    }

    /**
     * Json list registered classes.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonListRegistered")
    public ModelAndView jsonListRegisteredClasses(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        boolean getAll = true;
        JSONArray data = new JSONArray();
        List<RegisteredClassDto> registeredClasses = null;
        try {
            String schoolId = request.getParameter("SchoolId");
            if (schoolId != null && !StringUtil.isEmpty(schoolId)) {
                registeredClasses = registeredClassService.getBySchool(Integer.parseInt(schoolId));
                getAll = false;
            }
            if (getAll) {
                registeredClasses = registeredClassService.getAll();
            }
            if (registeredClasses != null) {
                for(RegisteredClassDto registeredClassDto : registeredClasses) {
                    JSONArray row = new JSONArray();
                    ClassDto classDto = registeredClassDto.getClassDto();
                    MediumDto medium = registeredClassDto.getMedium();
                    SectionDto section = registeredClassDto.getSection();
                    SchoolDto school = registeredClassDto.getSchool();
                    BranchDto branch = school.getBranch();
                    DivisionDto division = school.getDivision();
                    RegionDto region = branch.getRegion();

                    row.put(registeredClassDto.getClassId()); // 0
                    row.put(classDto.getClassId());  // 1
                    row.put(classDto.getClassName()); // 2
                    row.put(medium.getMediumId()); // 3
                    row.put(medium.getDescription()); // 
                    row.put(section.getSectionId());
                    row.put(section.getSectionName());
                    row.put(school.getSchoolId());
                    row.put(school.getSchoolName());
                    row.put(branch.getBranchId()); // 10
                    row.put(branch.getBranchCode());
                    if (region == null) {
                        row.put(0);
                        row.put("");
                        row.put(0);
                        row.put(""); //15
                    } else {
                        row.put(region.getRegionId());
                        row.put(region.getRegionName());

                        StateDto state = region.getState();
                        if (state == null) {
                            row.put(0);
                            row.put(""); // 15
                        } else {
                            row.put(state.getStateId());
                            row.put(state.getStateName()); //15
                        }
                    }
                    if (division == null) {
                        row.put(0);
                        row.put("");
                    } else {
                        row.put(division.getDivisionId());
                        row.put(division.getDivisionCode());
                    }
                    data.put(row);
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
        return null;
    }

    /**
     * Launch new registered.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="launchNewRegistered")
    public ModelAndView launchNewRegistered(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String schoolId = request.getParameter("schoolId");
        if (schoolId != null && !StringUtil.isEmpty(schoolId)) {
            SchoolDto schoolDto = schoolService.get(Integer.parseInt(schoolId));
            map.put("school", schoolDto);
        }
        map.put("classes", classService.getAll());
        map.put("mediums", mediumService.getAll());
        map.put("sections", sectionService.getAll());
        return ViewDelegationController.delegateModelPageView(request, ClazzViewNames.MAINTAIN_REGISTERED_CLASS, map);
    }

    /**
     * Launch update registered.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="launchUpdateRegistered")
    public ModelAndView launchUpdateRegistered(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        String registeredClassId = request.getParameter("registeredClassId");

        String schoolId = request.getParameter("schoolId");
        if (schoolId != null && !StringUtil.isEmpty(schoolId)) {
            SchoolDto schoolDto = schoolService.get(Integer.parseInt(schoolId));
            map.put("school", schoolDto);
        }
        if (registeredClassId != null && !StringUtil.isEmpty(registeredClassId)) {
            RegisteredClassDto registeredClassDto = registeredClassService.get(Integer.parseInt(registeredClassId));
            map.put("registeredClass", registeredClassDto);
        }
        map.put("classes", classService.getAll());
        map.put("mediums", mediumService.getAll());
        map.put("sections", sectionService.getAll());
        return ViewDelegationController.delegateModelPageView(request, ClazzViewNames.MAINTAIN_REGISTERED_CLASS, map);
    }

    /**
     * Do create registered.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doCreateRegistered")
    public ModelAndView doCreateRegistered(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
            RegisteredClassDto registeredClassDto = validateAndGetRegisteredClass(request);
            result.setSuccessful(registeredClassService.create(registeredClassDto));
        } catch (DataException dataException) {
            result.setStatusMessage(viewErrorHandler.getMessage(dataException.getMessage()));
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Do update registered.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doUpdateRegistered")
    public ModelAndView doUpdateRegistered(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
            String registeredClassId = request.getParameter("registeredClassId");
            viewErrorHandler.validate(registeredClassId, "className", DataTypeValidator.INTEGER, true);

            if (registeredClassId != null && !StringUtil.isEmpty(registeredClassId)) {
                RegisteredClassDto registeredClassDto = validateAndGetRegisteredClass(request);
                result.setSuccessful(registeredClassService.update(
                        Integer.parseInt(registeredClassId), registeredClassDto));
            }
        } catch (DataException dataException) {
            result.setStatusMessage(viewErrorHandler.getMessage(dataException.getMessage()));
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Do delete registered.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doDeleteRegistered")
    public ModelAndView doDeleteRegistered(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ResultDto result = new ResultDto();
        try {
            String registeredClassId = request.getParameter("registeredClassId");
            viewErrorHandler.validate(registeredClassId, "className", DataTypeValidator.INTEGER, true);

            if (registeredClassId != null && !StringUtil.isEmpty(registeredClassId)) {
                result.setSuccessful(registeredClassService.delete(Integer.parseInt(registeredClassId)));
            }
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Validate and get registered class.
     * 
     * @param request the request
     * @return the registered class dto
     * @throws DataException the data exception
     */
    private RegisteredClassDto validateAndGetRegisteredClass(
            HttpServletRequest request) throws DataException {
        RegisteredClassDto registeredClassDto = new RegisteredClassDto();
        String schoolId = request.getParameter("schoolId");
        String classId = request.getParameter("classId");
        String mediumId = request.getParameter("mediumId");
        String sectionId = request.getParameter("sectionId");

        viewErrorHandler.validate(schoolId, "schoolName", DataTypeValidator.INTEGER, true);
        viewErrorHandler.validate(classId, "className", DataTypeValidator.INTEGER, true);
        viewErrorHandler.validate(mediumId, "mediumName", DataTypeValidator.INTEGER, true);
        viewErrorHandler.validate(sectionId, "sectionName", DataTypeValidator.INTEGER, true);

        SchoolDto school = new SchoolDto();
        school.setSchoolId(Integer.parseInt(schoolId));

        ClassDto classDto = new ClassDto();
        classDto.setClassId(Integer.parseInt(classId));

        MediumDto mediumDto = new MediumDto();
        mediumDto.setMediumId(Integer.parseInt(mediumId));

        SectionDto sectionDto = new SectionDto();
        sectionDto.setSectionId(Integer.parseInt(sectionId));

        registeredClassDto.setSchool(school);
        registeredClassDto.setClassDto(classDto);
        registeredClassDto.setMedium(mediumDto);
        registeredClassDto.setSection(sectionDto);
        return registeredClassDto;
    }

    /**
     * Lookup class.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="lookupClass")
    public ModelAndView lookupClass(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = null;
        String registeredClassId = request.getParameter("RegisteredClassId");
        if (!StringUtil.isNullOrBlank(registeredClassId)) {
            map = new HashMap<String, Object>();
            map.put("RegisteredClass", registeredClassService.get(Integer.parseInt(registeredClassId)));
        }
        return ViewDelegationController.delegateModelPageView(request, ClazzViewNames.LOOKUP_CLASS, map);
    }

}
