package com.myschool.employee.validator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DataException;
import com.myschool.common.exception.InvalidDataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.util.DateUtil;
import com.myschool.common.validator.AbstractValidator;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.employee.domain.EmployeeEducationManager;
import com.myschool.employee.dto.EmployeeEducation;
import com.myschool.exim.domain.EmployeeEximManager;

/**
 * The Class EmployeeEducationValidator.
 */
@Component
public class EmployeeEducationValidator extends AbstractValidator<EmployeeEducation> {

    /** The employee Education manager. */
    @Autowired
    private EmployeeEducationManager employeeEducationManager;

    /** The employee exim manager. */
    @Autowired
    private EmployeeEximManager employeeEximManager;

    /* (non-Javadoc)
     * @see com.myschool.common.validator.AbstractValidator#doValidate(java.lang.Object)
     */
    @Override
    protected void doValidate(EmployeeEducation employeeEducation) throws ValidationException {
        try {
            // check if the Education is valid
            String degree = employeeEducation.getDegree();
            validate(degree, "Degree", DataTypeValidator.ANY_CHARACTER, true);
            String specialization = employeeEducation.getSpecialization();
            validate(specialization, "Specialization", DataTypeValidator.ANY_CHARACTER, false);
            String college = employeeEducation.getCollege();
            validate(college, "College", DataTypeValidator.ANY_CHARACTER, true);
            String university = employeeEducation.getUniversity();
            validate(university, "University", DataTypeValidator.ANY_CHARACTER, true);
            int yearOfGraduation = employeeEducation.getYearOfGraduation();
            validate(String.valueOf(yearOfGraduation), "Year Of Graduation", DataTypeValidator.INTEGER, true);
            Date date = DateUtil.getDate(0, 0, yearOfGraduation);
            if (date == null) {
                throw new ValidationException("Year Of Graduation is a required value.");
            }
            if (!DateUtil.isPastDate(date)) {
                throw new ValidationException("Year Of Graduation must be an year in the past.");
            }
            int percentage = employeeEducation.getPercentage();
            validate(String.valueOf(percentage), "Percentage", DataTypeValidator.INTEGER, true);
            if (percentage == 0 || percentage > 100) {
                throw new ValidationException("Percentage must be between 1% and 100%");
            }
        } catch (InvalidDataException invalidDataException) {
            throw new ValidationException(invalidDataException.getMessage(), invalidDataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.common.validator.AbstractValidator#validate(int)
     */
    @Override
    public void validate(int employeeEducationId) throws ValidationException {
        try {
            super.validate(employeeEducationId);
            EmployeeEducation existingEmployeeEducation = employeeEducationManager.get(employeeEducationId);
            if (existingEmployeeEducation == null) {
                throw new ValidationException("There is no such Education for employee to update.");
            }
        } catch (DataException dataException) {
            throw new ValidationException(dataException.getMessage(), dataException);
        }
    }
}
