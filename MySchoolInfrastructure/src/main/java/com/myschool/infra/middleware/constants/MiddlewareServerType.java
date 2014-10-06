package com.myschool.infra.middleware.constants;

/**
 * The Enum MiddlewareServerType.
 */
public enum MiddlewareServerType {

    /** The ACTIVE_MQ. */
    ACTIVE_MQ;

    /**
     * Gets the middleware server type.
     *
     * @param middlewareServerName the middleware server name
     * @return the middleware server type
     */
    public static MiddlewareServerType getMiddlewareServerType(String middlewareServerName) {
        MiddlewareServerType returnMiddlewareServerType = null;
        if (middlewareServerName != null) {
            for (MiddlewareServerType middlewareServerType : values()) {
                if (middlewareServerName.trim().toUpperCase().equals(middlewareServerType.toString())) {
                    returnMiddlewareServerType = middlewareServerType;
                    break;
                }
            }
        }
        return returnMiddlewareServerType;
    }

}
