package com.myschool.attendance.validator;

import com.myschool.attendance.dto.Day;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.Validator;

/**
 * The Class DayValidator.
 */
public class DayValidator implements Validator<Day> {

    /** The Constant START_DATE. */
    public static final int START_DATE = 1;

    /** The Constant END_DATE. */
    public static final int END_DATE = 31;

    /* (non-Javadoc)
     * @see com.myschool.Validator#validate(java.lang.Object)
     */
    @Override
    public boolean validate(Day day) throws ValidationException {
        if (day == null) {
            throw new ValidationException("Day is null");
        }
        validateDate(day.getDate());
        return true;
    }
    
    /**
     * Validate date.
     *
     * @param date the date
     * @throws ValidationException the validation exception
     */
    public void validateDate(int date) throws ValidationException {
        if (date < START_DATE || date > END_DATE) {
            throw new ValidationException("Invalid Date: " + date);
        }
    }

}
