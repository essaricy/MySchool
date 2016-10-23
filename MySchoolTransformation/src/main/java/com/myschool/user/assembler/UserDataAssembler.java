package com.myschool.user.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.common.util.ConversionUtil;
import com.myschool.common.util.JsonUtil;
import com.myschool.common.util.PasswordUtil;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.student.dto.StudentDto;
import com.myschool.user.constants.UserType;
import com.myschool.user.dto.ChangePasswordDto;
import com.myschool.user.dto.UserAccessDto;
import com.myschool.user.dto.UserPreference;
import com.myschool.user.dto.UserStatistics;
import com.myschool.user.dto.UserTheme;
import com.myschool.user.dto.UserTypeDto;
import com.myschool.user.dto.UsersDto;

/**
 * The Class UserDataAssembler.
 */
public class UserDataAssembler {

    /**
     * Creates the user.
     *
     * @param employee the employee
     * @return the users dto
     */
    public static UsersDto createUser(EmployeeDto employee) {
        UsersDto user = null;

        if (employee != null) {
            user = new UsersDto();
            user.setUserName(employee.getEmployeeNumber());
            user.setPassword(PasswordUtil.getInitialPassword(employee));
            user.setUserType(UserType.EMPLOYEE);
            user.setRefUserId(employee.getEmployeeId());
        }
        return user;
    }

    /**
     * Creates the user.
     *
     * @param student the student
     * @return the users dto
     */
    public static UsersDto createUser(StudentDto student) {
        UsersDto user = null;

        if (student != null) {
            user = new UsersDto();
            user.setUserName(student.getAdmissionNumber());
            user.setPassword(PasswordUtil.getInitialPassword(student));
            user.setUserType(UserType.STUDENT);
            user.setRefUserId(student.getStudentId());
        }
        return user;
    }

    /**
     * Creates the user.
     *
     * @param resultSet the result set
     * @return the users DTO
     * @throws SQLException the SQL exception
     */
    public static UsersDto createUser(ResultSet resultSet) throws SQLException {
        UsersDto usersDto = new UsersDto();
        usersDto.setId(resultSet.getInt("USER_ID"));
        usersDto.setUserName(resultSet.getString("USER_NAME"));
        usersDto.setPassword(resultSet.getString("PASSWORD"));
        usersDto.setUserType(createUserType(resultSet.getInt("REF_USER_TYPE_ID")));
        usersDto.setDisplayName(resultSet.getString("DISPLAY_NAME"));
        return usersDto;
    }

    /**
     * Creates the user type.
     *
     * @param userTypeId the user type id
     * @return the user type
     */
    public static UserType createUserType(int userTypeId) {
        UserType userType = null;
        if (userTypeId == UserType.ADMIN.getUserTypeValue()) {
            userType = UserType.ADMIN;
        } else if (userTypeId == UserType.EMPLOYEE.getUserTypeValue()) {
            userType = UserType.EMPLOYEE;
        } else if (userTypeId == UserType.STUDENT.getUserTypeValue()) {
            userType = UserType.STUDENT;
        }
        return userType;
    }

    /**
     * Creates the user preference.
     *
     * @param resultSet the result set
     * @return the user preference
     * @throws SQLException the sQL exception
     */
    public static UserPreference createUserPreference(ResultSet resultSet) throws SQLException {
        UserPreference userPreference = new UserPreference();
        userPreference.setAllowAds(ConversionUtil.toBoolean(resultSet.getString("ALLOW_ADS")));
        userPreference.setRecordsPerPage(resultSet.getInt("RECORDS_PER_PAGE"));
        //userPreference.setUserTheme(createUserTheme(resultSet.getString("THEME_NAME")));
        userPreference.setUserTheme(createUserTheme(resultSet, true));
        return userPreference;
    }

    /**
     * Creates the user theme.
     *
     * @param resultSet the result set
     * @param alias the alias
     * @return the user theme
     * @throws SQLException the SQL exception
     */
    public static UserTheme createUserTheme(ResultSet resultSet, boolean alias)
            throws SQLException {
        UserTheme userTheme = new UserTheme();
        if (alias) {
            userTheme.setCode(resultSet.getString("USER_THEME_CODE"));
            userTheme.setName(resultSet.getString("USER_THEME_NAME"));
        } else {
            userTheme.setCode(resultSet.getString("CODE"));
            userTheme.setName(resultSet.getString("NAME"));
        }
        return userTheme;
    }

    /**
     * Creates the user statistics.
     *
     * @param resultSet the result set
     * @return the user statistics
     * @throws SQLException the sQL exception
     */
    public static UserStatistics createUserStatistics(ResultSet resultSet) throws SQLException {
        UserStatistics userStatistics = new UserStatistics();
        userStatistics.setLastVisitOn(resultSet.getString("LAST_VISIT"));
        userStatistics.setNumberOfVisits(resultSet.getInt("NUMBER_OF_VISITS"));
        return userStatistics;
    }

    /**
     * Creates the change password.
     *
     * @param jsonObject the json object
     * @return the change password dto
     */
    public static ChangePasswordDto createChangePassword(JSONObject jsonObject) {
        ChangePasswordDto changePassword = new ChangePasswordDto();
        changePassword.setConfirmedPassword(JsonUtil.getString(jsonObject, "ConfirmedPassword"));
        changePassword.setCurrentPassword(JsonUtil.getString(jsonObject, "CurrentPassword"));
        changePassword.setNewPassword(JsonUtil.getString(jsonObject, "NewPassword"));
        changePassword.setUserId(JsonUtil.getInt(jsonObject, "UserId"));
        return changePassword;
    }

    /**
     * Creates the user preference.
     * 
     * @param jsonObject the json object
     * @return the user preference
     */
    public static UserPreference createUserPreference(JSONObject jsonObject) {
        UserPreference userPreference = new UserPreference();

        UserTheme userTheme = new UserTheme();
        userTheme.setCode(JsonUtil.getString(jsonObject, "ThemeCode"));
        userPreference.setUserTheme(userTheme);
        userPreference.setRecordsPerPage(JsonUtil.getInt(jsonObject, "RecordsPerPage"));
        userPreference.setAllowAds(ConversionUtil.toBoolean(JsonUtil.getString(jsonObject, "AllowAds")));
        userPreference.setUserId(JsonUtil.getInt(jsonObject, "UserId"));
        return userPreference;
    }

    /**
     * Creates the user type.
     *
     * @param resultSet the result set
     * @return the user type dto
     * @throws SQLException the sQL exception
     */
    public static UserTypeDto createUserType(ResultSet resultSet) throws SQLException {
        UserTypeDto userTypeDto =  null;
        
        userTypeDto =  new UserTypeDto();
        userTypeDto.setUserTypeId(resultSet.getInt("USER_TYPE_ID"));
        userTypeDto.setDescription(resultSet.getString("DESCRIPTION"));
        
        return userTypeDto;
    }

    /**
     * Creates the user access.
     *
     * @param resultSet the result set
     * @return the default user access dto
     * @throws SQLException the sQL exception
     */
    public static UserAccessDto createUserAccess(
            ResultSet resultSet) throws SQLException {
        UserAccessDto userAccess =  null;
        userAccess =  new UserAccessDto();
        userAccess.setView(ConversionUtil.toBoolean(resultSet.getString("CAN_VIEW")));
        userAccess.setCreate(ConversionUtil.toBoolean(resultSet.getString("CAN_CREATE")));
        userAccess.setUpdate(ConversionUtil.toBoolean(resultSet.getString("CAN_UPDATE")));
        userAccess.setDelete(ConversionUtil.toBoolean(resultSet.getString("CAN_DELETE")));
        userAccess.setFunction(ModuleDataAssembler.createFunction(resultSet, true));
        return userAccess;
    }

    /**
     * Creates the.
     * 
     * @param users the users
     * @return the jSON array
     */
    public static JSONArray create(List<UsersDto> users) {
        JSONArray jsonArray = null;
        if (users != null && !users.isEmpty()) {
            jsonArray = new JSONArray();
            for (UsersDto user : users) {
                jsonArray.put(create(user));
            }
        }
        return jsonArray;
    }

    /**
     * Creates the.
     * 
     * @param user the user
     * @return the jSON object
     */
    private static JSONObject create(UsersDto user) {
        JSONObject jsonObject = null;
        if (user != null) {
            jsonObject = new JSONObject();
            jsonObject.put("Id", user.getId());
            jsonObject.put("RefUserId", user.getRefUserId());
            jsonObject.put("DisplayName", user.getDisplayName());
            jsonObject.put("UserName", user.getUserName());
            jsonObject.put("UserType", create(user.getUserType()));
        }
        return jsonObject;
    }

    /**
     * Creates the.
     * 
     * @param userType the user type
     * @return the jSON object
     */
    private static JSONObject create(UserType userType) {
        JSONObject jsonObject = null;
        if (userType != null) {
            jsonObject = new JSONObject();
            jsonObject.put("Prefix", userType.getPrefix());
            jsonObject.put("UserTypeValue", userType.getUserTypeValue());
            jsonObject.put("UserType", userType.toString());
        }
        return jsonObject;
    }

}
