package com.myschool.attendance.validator;

import java.util.List;

import com.myschool.attendance.dto.Month;
import com.myschool.attendance.dto.Year;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.Validator;

/**
 * The Class YearValidator.
 */
public class YearValidator implements Validator<Year> {

    /** The Constant MINIMUM_POSSIBLE_DAYS. */
    public static final int MINIMUM_POSSIBLE_DAYS = 365;

    /** The Constant MAXIMUM_POSSIBLE_DAYS. */
    public static final int MAXIMUM_POSSIBLE_DAYS = 3366;

    /* (non-Javadoc)
     * @see com.myschool.base.interfaces.Validator#validate(java.lang.Object)
     */
    @Override
    public boolean validate(Year year) throws ValidationException {
        if (year == null) {
            throw new ValidationException("Year is null");
        }
        List<Month> months = year.getMonths();
        if (months != null) {
            MonthValidator monthValidator = new MonthValidator();
            for (Month month : months) {
                monthValidator.validate(month);
            }
        }
        return true;
    }

}
