package com.myschool.user.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.util.ConversionUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.employee.domain.EmployeeManager;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.student.domain.StudentManager;
import com.myschool.student.dto.StudentDto;
import com.myschool.user.constants.UserType;
import com.myschool.user.dao.LoginDao;
import com.myschool.user.dao.UserDao;
import com.myschool.user.dto.LoginDto;
import com.myschool.user.dto.ModuleAccessDto;

/**
 * The Class LoginManager.
 */
@Component
public class LoginManager {

    @Autowired
    private PrivilegesManager privilegesManager;

    /** The login dao. */
    @Autowired
    private LoginDao loginDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private StudentManager studentManager;

    @Autowired
    private EmployeeManager employeeManager;

    /**
     * Login.
     * 
     * @param login the login
     * @return the login dto
     * @throws DataException the data exception
     */
    public LoginDto login(LoginDto login) throws DataException {
        LoginDto loginDetails = null;
        try {
            if (login == null) {
                throw new DataException("Username or password is invalid.");
            }
            String loginId = login.getLoginId();
            String password = login.getPassword();
            if (StringUtil.isNullOrBlank(loginId) || StringUtil.isNullOrBlank(password)) {
                throw new DataException("Username or password is invalid.");
            }
            loginDetails = loginDao.getLoginDetails(login);
            if (loginDetails == null) {
                throw new DataException("Username or password is invalid.");
            } else {
                // Login credentials are valid
                int userId = loginDetails.getId();
                int refUserId = loginDetails.getRefUserId();
                UserType userType = loginDetails.getUserType();

                List<ModuleAccessDto> privileges = privilegesManager.getUserPrivileges(userId, userType.getUserTypeValue());
                loginDetails.setModuleAccess(privileges);

                if (userType == UserType.STUDENT) {
                    StudentDto student = studentManager.get(refUserId);
                    if (student == null || student.getStudentId() == 0) {
                        throw new DataException("Your profile does not exist.");
                    }
                    // check if the student is verified and active. If not, do not allow login.
                    if (!student.isVerified()) {
                        throw new DataException("Your profile has not been actived yet.");
                    }
                    loginDetails.setUserDetails(student);
                } else if (userType == UserType.STUDENT) {
                    EmployeeDto employee = employeeManager.get(refUserId);
                    if (employee == null || employee.getEmployeeId() == 0) {
                        throw new DataException("Your profile does not exist.");
                    }
                    // check if the student is verified and active. If not, do not allow login.
                    if (!employee.isVerified()) {
                        throw new DataException("Your profile has not been actived yet.");
                    }
                    Date employmentEndDate = ConversionUtil.fromApplicationDate(employee.getEmploymentEndDate());
                    if (employmentEndDate != null && employmentEndDate.before(Calendar.getInstance().getTime())) {
                        throw new DataException("Your profile has been deactived.");
                    }
                    loginDetails.setUserDetails(employee);
                }
                loginDetails.setUserPreference(userDao.getUserPreferences(userId));
                loginDetails.setUserStatistics(userDao.getUserStatistics(userId));
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return loginDetails;
    }

}
