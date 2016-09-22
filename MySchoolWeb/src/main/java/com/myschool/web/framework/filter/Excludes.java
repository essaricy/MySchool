package com.myschool.web.framework.filter;

import javax.servlet.http.HttpServletRequest;

public class Excludes {

    /** The GLOBAL_EXCLUDES. */
    private static String[] GLOBAL_EXCLUDES = {
        // Common excludes
        "/themes", "/scripts", "/styles", "/image", "/images", "/widgets", "/jcaptcha",
    };

    /** The PUBLIC_EXCLUDES. */
    private static String[] PUBLIC_EXCLUDES = {
        // screens that login does not require
        "/public", "/acl",
        // Gallery Excludes
        "/gallery/getLatestGallery.htm", "/gallery/jsonListGalleries.htm",
        // login excludes
        //"/log",

        // TODO below all are deprecated
        "/download", "/portal",
        "/announcement",
        // Gallery related excludes
        //"/gallery/launchGallery.htm", "/gallery/jsonListGalleries.htm", "/gallery/jsonGalleryDetail.htm", "/gallery/getLatestGallery.htm",
        // Issue related excludes
        "/issue/launchIssue.htm", "/issue/doCreate.htm",
        // Portal excludes
        "/portal-employee",
        "/portal-student"
    };

    /** The FUNCTIONAL_URL. */
    private static String[] FUNCTIONAL_URL = {
        "/admission-status/jsonList.htm",
        // Branch related excludes
        "/branch/jsonList.htm",
        // Class related excludes
        "/class/lookupClass.htm",
        "class/jsonListRegistered.htm",
        "/school/jsonList.htm",
        "/registeredSubject/jsonList.htm",
        // designation related excludes
        "/designation/jsonList.htm",
        "/document/jsonList.htm",
        // Relationship Excludes
        "/relationship/jsonList.htm",
        // Employee related excludes
        "/employment/jsonList.htm",
        "/employee/verifiedEmployeesJSONList.htm",
        "/employee-attribute/launch.htm",
        "/employee-attribute/doValidate.htm",
        "/portal-employee/doDelete.htm",
        // Student related excludes
        "/student-attribute/launch.htm",
        "/student-attribute/doValidate.htm",
        "/portal-student/doDelete.htm",
        // Upload image excludes
        "/upload/uploadImage.htm",
    };

    public static boolean isGlobalExclude(HttpServletRequest request) {
    	boolean exclude = false;
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String actualRequest = requestURI.substring(requestURI.indexOf(contextPath) + contextPath.length(), requestURI.length());

        for (String excludeRequest : GLOBAL_EXCLUDES) {
            if (actualRequest.equals(excludeRequest) || actualRequest.indexOf(excludeRequest) != -1) {
                exclude = true;
                break;
            }
        }
        return exclude;
    }

    public static boolean isPublicExclude(HttpServletRequest request) {
    	boolean exclude = false;
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String actualRequest = requestURI.substring(requestURI.indexOf(contextPath) + contextPath.length(), requestURI.length());

        if (!exclude) {
            for (String excludeRequest : PUBLIC_EXCLUDES) {
                if (actualRequest.equals(excludeRequest) || actualRequest.indexOf(excludeRequest) != -1) {
                    exclude = true;
                    break;
                }
            }
        }
        return exclude;
    }

    public static boolean isFunctionalExclude(HttpServletRequest request) {
    	boolean exclude = false;
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String actualRequest = requestURI.substring(requestURI.indexOf(contextPath) + contextPath.length(), requestURI.length());

        if (!exclude) {
            for (String excludeRequest : FUNCTIONAL_URL) {
                if (actualRequest.equals(excludeRequest) || actualRequest.indexOf(excludeRequest) != -1) {
                    exclude = true;
                    break;
                }
            }
        }
        return exclude;
    }

    public static boolean isGlobalOrPublicExclude(HttpServletRequest request) {
        boolean exclude = false;
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String actualRequest = requestURI.substring(requestURI.indexOf(contextPath) + contextPath.length(), requestURI.length());

        for (String excludeRequest : GLOBAL_EXCLUDES) {
            if (actualRequest.equals(excludeRequest) || actualRequest.indexOf(excludeRequest) != -1) {
                exclude = true;
                break;
            }
        }
        if (!exclude) {
            for (String excludeRequest : PUBLIC_EXCLUDES) {
                if (actualRequest.equals(excludeRequest) || actualRequest.indexOf(excludeRequest) != -1) {
                    exclude = true;
                    break;
                }
            }
        }
        return exclude;
    }

    public static boolean isAnyExclude(HttpServletRequest request) {
        boolean exclude = false;
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String actualRequest = requestURI.substring(requestURI.indexOf(contextPath) + contextPath.length(), requestURI.length());

        for (String excludeRequest : GLOBAL_EXCLUDES) {
            if (actualRequest.equals(excludeRequest) || actualRequest.indexOf(excludeRequest) != -1) {
                exclude = true;
                break;
            }
        }
        if (!exclude) {
            for (String excludeRequest : PUBLIC_EXCLUDES) {
                if (actualRequest.equals(excludeRequest) || actualRequest.indexOf(excludeRequest) != -1) {
                    exclude = true;
                    break;
                }
            }
        }
        if (!exclude) {
            for (String excludeRequest : FUNCTIONAL_URL) {
                if (actualRequest.equals(excludeRequest) || actualRequest.indexOf(excludeRequest) != -1) {
                    exclude = true;
                    break;
                }
            }
        }
        return exclude;
    }

}
