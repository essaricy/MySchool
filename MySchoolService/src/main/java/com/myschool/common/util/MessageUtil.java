package com.myschool.common.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * The Class MessageUtil.
 */
@Component
public class MessageUtil {

    /** The application context. */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Gets the message.
     *
     * @param key the key
     * @return the message
     */
    public String getMessage(String key) {
        return applicationContext.getMessage(key, null, Locale.getDefault());
    }

    /**
     * Gets the message.
     *
     * @param key the key
     * @param values the values
     * @return the message
     */
    public String getMessage(String key, Object[] values) {
        return applicationContext.getMessage(key, values, Locale.getDefault());
    }

}
