package com.myschool.user.util;

import com.myschool.user.dto.LoginDto;
import com.myschool.user.dto.UserContext;

/**
 * The Class ContextUtil.
 */
public class ContextUtil {

    /**
     * Creates the user context.
     * 
     * @param loginDetails the login details
     * @return the user context
     */
    public static UserContext createUserContext(LoginDto loginDetails) {
        UserContext context = null;
        if (loginDetails != null) {
            context = new UserContext();
            context.setLogin(loginDetails);
            context.setUserType(loginDetails.getUserType());
            context.setModuleAccess(loginDetails.getModuleAccess());
            context.setUserPreference(loginDetails.getUserPreference());
            context.setUserStatistics(loginDetails.getUserStatistics());
        }
        return context;
    }

}
