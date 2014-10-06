package com.myschool.employee.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.branch.dto.BranchDto;
import com.myschool.clazz.assembler.RegisteredSubjectDataAssembler;
import com.myschool.clazz.dto.ClassDto;
import com.myschool.clazz.dto.MediumDto;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.clazz.dto.RegisteredSubjectDto;
import com.myschool.clazz.dto.SectionDto;
import com.myschool.clazz.dto.SubjectDto;
import com.myschool.common.util.StringUtil;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.dto.EmployeeSubjectDto;
import com.myschool.school.dto.SchoolDto;

/**
 * The Class EmployeeSubjectDataAssembler.
 */
public class EmployeeSubjectDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the employee subject dto
     * @throws SQLException the sQL exception
     */
    public static EmployeeSubjectDto create(ResultSet resultSet) throws SQLException {
        EmployeeSubjectDto employeeSubject = new EmployeeSubjectDto();
        EmployeeDto employee = new EmployeeDto();
        employee.setEmployeeId(resultSet.getInt("EMPLOYEE_ID"));
        employeeSubject.setEmployee(employee);
        employeeSubject.setEmployeeSubjectId(resultSet.getInt("EMPLOYEE_SUBJECT_ID"));
        employeeSubject.setRegisteredSubject(RegisteredSubjectDataAssembler.create(resultSet));
        return employeeSubject;
    }

    /**
     * Creates the.
     * 
     * @param jsonObjectArray the json object array
     * @return the list
     */
    public static List<EmployeeSubjectDto> create(JSONArray jsonObjectArray) {
        List<EmployeeSubjectDto> employeeSubjects = null;
        if (jsonObjectArray != null && jsonObjectArray.length() != 0) {
            employeeSubjects = new ArrayList<EmployeeSubjectDto>();
            for (int index = 0; index < jsonObjectArray.length(); index++) {
                JSONObject jsonObject = (JSONObject) jsonObjectArray.get(index);
                if (jsonObject != null) {
                    employeeSubjects.add(create(jsonObject));
                }
            }
        }
        return employeeSubjects;
    }

    /**
     * Creates the.
     * 
     * @param employeeSubjectData the employee subject data
     * @return the employee subject dto
     */
    public static EmployeeSubjectDto create(JSONObject employeeSubjectData) {
        EmployeeSubjectDto employeeSubject = null;
        if (employeeSubjectData != null) {
            employeeSubject = new EmployeeSubjectDto();

            String employeeSubjectId = employeeSubjectData.getString("EmployeeSubjectId");
            if (!StringUtil.isNullOrBlank(employeeSubjectId)) {
                employeeSubject.setEmployeeSubjectId(Integer.parseInt(employeeSubjectId));
            }
            String registeredSubjectId = employeeSubjectData.getString("RegisteredSubjectId");
            RegisteredSubjectDto registeredSubject = new RegisteredSubjectDto();
            registeredSubject.setSubjectId(Integer.parseInt(registeredSubjectId));
            employeeSubject.setRegisteredSubject(registeredSubject);
        }
        return employeeSubject;
    }

    /**
     * Creates the.
     * 
     * @param employeeSubjects the employee subjects
     * @return the jSON array
     */
    public static JSONArray create(List<EmployeeSubjectDto> employeeSubjects) {
        JSONArray jsonArray = null;
        if (employeeSubjects != null && !employeeSubjects.isEmpty()) {
            jsonArray = new JSONArray();
            for (EmployeeSubjectDto employeeSubject : employeeSubjects) {
                jsonArray.put(create(employeeSubject));
            }
        }
        return jsonArray;
    }

    /**
     * Creates the.
     * 
     * @param employeeSubject the employee subject
     * @return the jSON array
     */
    private static JSONArray create(EmployeeSubjectDto employeeSubject) {
        JSONArray jsonArray = null;
        if (employeeSubject != null) {
            RegisteredSubjectDto registeredSubject = employeeSubject.getRegisteredSubject();
            SubjectDto subject = registeredSubject.getSubject();
            RegisteredClassDto registeredClass = registeredSubject.getRegisteredClass();
            ClassDto classDto = registeredClass.getClassDto();
            SectionDto section = registeredClass.getSection();
            MediumDto medium = registeredClass.getMedium();
            SchoolDto school = registeredClass.getSchool();
            BranchDto branch = school.getBranch();

            jsonArray = new JSONArray();
            jsonArray.put(employeeSubject.getEmployeeSubjectId());
            jsonArray.put(registeredSubject.getSubjectId());
            jsonArray.put(branch.getBranchId());
            jsonArray.put(branch.getBranchCode());
            jsonArray.put(school.getSchoolId());
            jsonArray.put(school.getSchoolName());
            jsonArray.put(registeredClass.getClassId());
            jsonArray.put(classDto.getClassId());
            jsonArray.put(classDto.getClassName());
            jsonArray.put(section.getSectionId());
            jsonArray.put(section.getSectionName());
            jsonArray.put(medium.getMediumId());
            jsonArray.put(medium.getDescription());
            jsonArray.put(subject.getSubjectId());
            jsonArray.put(subject.getSubjectName());
        }
        return jsonArray;
    }

}
