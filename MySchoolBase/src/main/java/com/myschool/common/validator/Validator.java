package com.myschool.common.validator;

import com.myschool.common.exception.ValidationException;

/**
 * The Interface Validator.
 *
 * @param <T> the generic type
 */
public interface Validator<T> {

    /**
     * Validate.
     *
     * @param t the t
     * @return true, if successful
     * @throws ValidationException the validation exception
     */
    public boolean validate(T t) throws ValidationException;
}
