package com.myschool.employee.validator;

import org.springframework.stereotype.Component;

import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.AbstractValidator;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.employee.dto.EmployeeExperience;
import com.quasar.core.exception.InvalidDataException;
import com.quasar.core.util.DateUtil;

/**
 * The Class EmployeeExperienceValidator.
 */
@Component
public class EmployeeExperienceValidator extends AbstractValidator<EmployeeExperience> {

    /* (non-Javadoc)
     * @see com.myschool.common.validator.AbstractValidator#doValidate(java.lang.Object)
     */
    @Override
    protected void doValidate(EmployeeExperience employeeExperience)
            throws ValidationException {
        try {
            String employer = employeeExperience.getEmployer();
            validate(employer, "Employer", DataTypeValidator.ANY_CHARACTER, true);

            String jobTitle = employeeExperience.getJobTitle();
            validate(jobTitle, "Job Title", DataTypeValidator.ANY_CHARACTER, true);

            String fromDate = employeeExperience.getFromDate();
            validate(fromDate, "From Date", DataTypeValidator.DATE, true);
            if (!DateUtil.isPastDate(fromDate)) {
                throw new ValidationException("From Date must be a date in past");
            }

            String toDate = employeeExperience.getToDate();
            DateUtil.checkFromDateToDateSequence(fromDate, toDate);
            employeeExperience.setExperieceInMonth(
                    DateUtil.dateDiffInMonths(fromDate, toDate));
        } catch (InvalidDataException invalidDataException) {
            throw new ValidationException(invalidDataException.getMessage(), invalidDataException);
        }
    }

}
