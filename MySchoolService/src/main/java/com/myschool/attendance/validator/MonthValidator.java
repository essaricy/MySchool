package com.myschool.attendance.validator;

import java.util.List;

import com.myschool.attendance.dto.Day;
import com.myschool.attendance.dto.Month;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.Validator;

/**
 * The Class MonthValidator.
 */
public class MonthValidator implements Validator<Month> {

    /** The Constant START_MONTH. */
    private static final int START_MONTH = 1;

    /** The Constant END_MONTH. */
    private static final int END_MONTH = 12;

    /** The Constant MINIMUM_POSSIBLE_DAYS. */
    private static final int MINIMUM_POSSIBLE_DAYS = 28;

    /** The Constant MAXIMUM_POSSIBLE_DAYS. */
    private static final int MAXIMUM_POSSIBLE_DAYS = 31;

    /* (non-Javadoc)
     * @see com.myschool.Validator#validate(java.lang.Object)
     */
    @Override
    public boolean validate(Month month) throws ValidationException {
        if (month == null) {
            throw new ValidationException("Month is null");
        }
        validateNumber(month.getNumber());
        List<Day> days = month.getDays();
        if (days != null) {
            int numberOfDays = days.size();
            if (numberOfDays < MINIMUM_POSSIBLE_DAYS || numberOfDays > MAXIMUM_POSSIBLE_DAYS) {
                throw new ValidationException("There cannot be less than " + MINIMUM_POSSIBLE_DAYS + " or greater than " + MAXIMUM_POSSIBLE_DAYS + " in a month.");
            }
            DayValidator dateValidator = new DayValidator();
            for (Day day : days) {
                dateValidator.validate(day);
            }
        }
        return true;
    }

    /**
     * Validate number.
     *
     * @param monthNumber the month number
     * @throws ValidationException the validation exception
     */
    public void validateNumber(int monthNumber) throws ValidationException {
        if (monthNumber < START_MONTH || monthNumber > END_MONTH) {
            throw new ValidationException("Invalid month number: " + monthNumber);
        }
    }

}
