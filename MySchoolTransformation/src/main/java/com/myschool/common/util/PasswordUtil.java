package com.myschool.common.util;

import org.springframework.stereotype.Component;

import com.myschool.common.dto.PersonalDetailsDto;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.student.dto.StudentDto;
import com.quasar.core.util.DateUtil;

/**
 */
@Component
public class PasswordUtil {

    /**
     * Gets the initial password.
     *
     * @param student the student
     * @return the initial password
     */
    public static String getInitialPassword(StudentDto student) {
        String initialPassword = null;
        PersonalDetailsDto personalDetails = null;

        if (student != null) {
            personalDetails = student.getPersonalDetails();
            if (personalDetails != null) {
                String firstName = personalDetails.getFirstName();
                String dateOfBirth = personalDetails.getDateOfBirth();
                if (firstName != null && dateOfBirth != null) {
                    initialPassword = Encryptor.getInstance().encrypt(firstName + DateUtil.getYearFromApplicationDate(dateOfBirth));
                }
            }
        }
        return initialPassword;
    }

    /**
     * Gets the initial password.
     *
     * @param employee the employee
     * @return the initial password
     */
    public static String getInitialPassword(EmployeeDto employee) {
        String initialPassword = null;

        if (employee != null) {
            String firstName = employee.getFirstName();
            String dateOfBirth = employee.getDateOfBirth();
            if (firstName != null && dateOfBirth != null) {
                initialPassword = Encryptor.getInstance().encrypt(firstName + DateUtil.getYearFromApplicationDate(dateOfBirth));
            }
        }
        return initialPassword;
    }

}
