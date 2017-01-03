package com.myschool.sautil.security;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.ServiceException;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.service.EmployeeService;
import com.myschool.sautil.base.StandAloneUtility;
import com.myschool.student.dto.StudentDto;
import com.myschool.student.service.StudentService;
import com.myschool.user.constants.UserType;
import com.myschool.user.service.UserService;
import com.quasar.core.util.CollectionUtil;

/**
 * The Class RestorePasswords.
 */
@Component
public class RestorePasswords extends StandAloneUtility {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(RestorePasswords.class);

    /** The Constant OPTION_USER_TYPE. */
    public static final String OPTION_USER_TYPE = "--user-type";

    /** The Constant OPTION_ADMISSION_NUMBER. */
    public static final String OPTION_ADMISSION_NUMBER = "--admission-number";

    /** The Constant OPTION_EMPLOYEE_NUMBER. */
    public static final String OPTION_EMPLOYEE_NUMBER = "--employee-number";

    /** The Constant OPTION_VALUE_ALL. */
    private static final String OPTION_VALUE_ALL = "ALL";

    /** The user service. */
    @Autowired
    private UserService userService;

    /** The student service. */
    @Autowired
    private StudentService studentService;

    /** The employee service. */
    @Autowired
    private EmployeeService employeeService;

    /* (non-Javadoc)
     * @see com.myschool.sautil.base.StandAloneUtility#validateParameters()
     */
    @Override
    public void validateParameters() throws ConfigurationException {
        String userTypeValue = executionProperties.getProperty(OPTION_USER_TYPE);
        if (userTypeValue == null) {
            throw new ConfigurationException("Must specify '" + OPTION_USER_TYPE + "' option. Valid Values are: " + CollectionUtil.toString(UserType.values()));
        }
        if (!userTypeValue.equalsIgnoreCase(OPTION_VALUE_ALL)) {
            UserType userType = UserType.get(userTypeValue);
            LOGGER.info("UserType " + userType);
            if (userType == null) {
                throw new ConfigurationException("Invalid value for " + OPTION_USER_TYPE + ". Valid Values are: " + CollectionUtil.toString(UserType.values()));
            } else if (userType == UserType.STUDENT) {
                // Validate admission number
                String admissionNumberValue = executionProperties.getProperty(OPTION_ADMISSION_NUMBER);
                LOGGER.info("Admission Number=" + admissionNumberValue);
                if (admissionNumberValue == null) {
                    throw new ConfigurationException("Must specify '" + OPTION_ADMISSION_NUMBER + "' option.");
                }
            } else if (userType == UserType.EMPLOYEE) {
                // Validate employee number
                String employeeNumberValue = executionProperties.getProperty(OPTION_EMPLOYEE_NUMBER);
                LOGGER.info("Employee Number=" + employeeNumberValue);
                if (employeeNumberValue == null) {
                    throw new ConfigurationException("Must specify '" + OPTION_EMPLOYEE_NUMBER + "' option.");
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.sautil.base.StandAloneUtility#startProcess()
     */
    public void startProcess() throws Exception {
        LOGGER.info("RestorePassword program started at " + new Date());
        // Validate module
        String userTypeValue = executionProperties.getProperty(OPTION_USER_TYPE);

        if (userTypeValue.equalsIgnoreCase(OPTION_VALUE_ALL)) {
            restoreStudentPasswords(studentService.getAll());
            restoreEmployeePasswords(employeeService.getAll());
        } else {
            UserType userType = UserType.get(userTypeValue);
            if (userType == UserType.STUDENT) {
                // Validate admission number
                String admissionNumberValue = executionProperties.getProperty(OPTION_ADMISSION_NUMBER);
                if (admissionNumberValue.equalsIgnoreCase(OPTION_VALUE_ALL)) {
                    // Restore password for all students.
                    LOGGER.info("Restoring password for all students.");
                    restoreStudentPasswords(studentService.getAll());
                } else {
                    // Restore password for this student
                    LOGGER.info("Restoring password for student " + admissionNumberValue);
                    restorePassword(studentService.get(admissionNumberValue));
                }
            } else if (userType == UserType.EMPLOYEE) {
                // Validate employee number
                String employeeNumberValue = executionProperties.getProperty(OPTION_EMPLOYEE_NUMBER);
                if (employeeNumberValue.equalsIgnoreCase(OPTION_VALUE_ALL)) {
                    // Restore password for all employees.
                    LOGGER.info("Restoring password for all employees.");
                    restoreEmployeePasswords(employeeService.getAll());
                } else {
                    LOGGER.info("Restoring password for employee " + employeeNumberValue);
                    // Restore password for this employee
                    restorePassword(employeeService.get(employeeNumberValue));
                }
            }
        }
    }

    /**
     * Restore student passwords.
     *
     * @param students the students
     */
    private void restoreStudentPasswords(List<StudentDto> students) {
        if (students == null || students.isEmpty()) {
            LOGGER.info("Not a single student found.");
        } else {
            LOGGER.info("Found " + students.size() + " students.");
            for (StudentDto student : students) {
                try {
                    restorePassword(student);
                } catch (ServiceException serviceException) {
                    LOGGER.error("Unable to set the password for the student " + student.getAdmissionNumber() + ". ERROR: " + serviceException);
                }
            }
        }
    }

    /**
     * Restore password.
     *
     * @param student the student
     * @throws ServiceException the service exception
     */
    private void restorePassword(StudentDto student) throws ServiceException {
        if (student != null) {
            LOGGER.debug("Resetting password for student " + student.getAdmissionNumber());
            if (userService.restorePassword(student)) {
                LOGGER.info("Password has been successfully restored for the student " + student.getAdmissionNumber());
            } else {
                LOGGER.error("Unable to set the password for the student " + student.getAdmissionNumber());
            }
        }
    }

    /**
     * Restore employee passwords.
     *
     * @param employees the employees
     */
    private void restoreEmployeePasswords(List<EmployeeDto> employees) {
        if (employees == null || employees.isEmpty()) {
            LOGGER.info("Not a single employee found.");
        } else {
            for (EmployeeDto employee : employees) {
                try {
                    restorePassword(employee);
                } catch (ServiceException serviceException) {
                    LOGGER.error("Unable to set the password for the employee " + employee.getEmployeeNumber() + ". ERROR: " + serviceException);
                }
            }
        }
    }

    /**
     * Restore password.
     *
     * @param employee the employee
     * @throws ServiceException the service exception
     */
    private void restorePassword(EmployeeDto employee) throws ServiceException {
        if (employee != null) {
            LOGGER.debug("Resetting password for employee " + employee.getEmployeeNumber());
            if (userService.restorePassword(employee)) {
                LOGGER.info("Password has been successfully restored for the employee " + employee.getEmployeeNumber());
            } else {
                LOGGER.error("Unable to set the password for the employee " + employee.getEmployeeNumber());
            }
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.sautil.base.StandAloneUtility#getUsageText()
     */
    public String getUsageText() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Usage: ").append(this.getClass().getName()).append(" [OPTIONS] \n");
        buffer.append("Valid Options are\n");
        buffer.append(String.format(PADDING_WIDTH, OPTION_HELP)).append("For Help\n");
        buffer.append(String.format(PADDING_WIDTH, OPTION_USER_TYPE)).append("User Type.\n");
        buffer.append(String.format(PADDING_WIDTH, OPTION_ADMISSION_NUMBER)).append("Admission Number.\n");
        buffer.append(String.format(PADDING_WIDTH, OPTION_EMPLOYEE_NUMBER)).append("Employee Number.\n");
        return buffer.toString();
    }

}
