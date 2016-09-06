package com.myschool.infra.remote.ftp.constant;

public class MySchoolFTPConstant {

    public static final String OP_READ = "READ";

    public static final String OP_DOWNLOAD = "DOWNLOAD";

    public static final String OP_MKDIR = "MKDIR";

    public static final String OP_STORE = "STORE";
    
    public static final String OP_DELETE = "DELETE";

    public static final String OP_REMDIR = "REMDIR";

    public static final String STATUS_OK = "OK";

    public static final String STATUS_EXISTS = "EXISTS";

    public static final String NOT_EXISTS = "NOT_EXISTS";

    public static final String STATUS_FAILED = "FAILED";

    public static final String STATUS_ERRORED = "ERRORED";

    public static final String OP_SUCCESS = "{0} OP={1}, Status={2}, Attempt={3}, Time={4}, File={5}";

    public static final String OP_FAILED = "{0} OP={1}, Status={2}, Attempt={3}, Time={4}, File={5}, Error={6}";

    public static final String OP_MAX_ATTEMPTS = "{0} OP={1}, Status=GIVING_UP, File={2}";

}
