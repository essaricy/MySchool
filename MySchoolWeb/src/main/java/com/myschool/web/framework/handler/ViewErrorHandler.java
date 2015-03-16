package com.myschool.web.framework.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DataException;
import com.myschool.common.util.MessageUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.common.validator.DataTypeValidator;

/**
 * The Class ResourceBundleUtil.
 */
@Component
public class ViewErrorHandler {

    /** The message util. */
    @Autowired
    private MessageUtil messageUtil;

    /**
     * Validate.
     *
     * @param fieldValue the field value
     * @param fieldName the field name
     * @param dataType the data type
     * @param required the required
     * @throws DataException the data exception
     */
    public void validate(String fieldValue, String fieldName, String dataType, boolean required) throws DataException {
        if (required) {
            if (StringUtil.isNullOrBlank(fieldValue)) {
                throw new DataException(fieldName + ".required");
            }
        }
        if (!StringUtil.isNullOrBlank(fieldValue) && !DataTypeValidator.validate(fieldValue, dataType)) {
            throw new DataException(fieldName + ".invalid");
        }
    }

    /**
     * Gets the message.
     *
     * @param errorMessage the error message
     * @return the message
     */
    public String getMessage(String errorMessage) {
        return messageUtil.getMessage(errorMessage);
    }

    /**
     * Gets the message.
     *
     * @param message the message
     * @param values the values
     * @return the message
     */
    public String getMessage(String message, Object[] values) {
        return messageUtil.getMessage(message, values);
    }

}
