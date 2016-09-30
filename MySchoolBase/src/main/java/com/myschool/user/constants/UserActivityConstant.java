package com.myschool.user.constants;

/**
 * The Class UserActivityConstant.
 */
public class UserActivityConstant {

	/** The Constant USER_ACTIVITY_SUCCESS. */
	public static final String USER_ACTIVITY_SUCCESS = "{0} request={1}, ResponseTime={2} ms";

    /** The Constant USER_ACTIVITY_FAILED. */
    public static final String USER_ACTIVITY_FAILED = "{0} request={1}, ERROR={2}";

	/** The Constant USER_SESSION_CREATED. */
	public static final String USER_SESSION_CREATED = "Session Created={0}";

	/** The Constant USER_SESSION_DESTROYED. */
	public static final String USER_SESSION_DESTROYED = "Session Destroyed={0}";

	public static final String USER_DOES_NOT_EXIST = "{0} failed to Signin with ID {1}, ERROR=User does not exist in the system";

	public static final String SIGNUP_SUCCESS = "{0} successfully logged in with ID {1} and has a role of {2}";

	public static final String SIGNUP_FAILED = "{0} failed to Signin with ID {1}, ERROR={2}";

	/** The Constant USER_LOGOUT_SUCCESS. */
	public static final String USER_LOGOUT_SUCCESS = "{0} with Signin ID {1} has been invalidated";

}
