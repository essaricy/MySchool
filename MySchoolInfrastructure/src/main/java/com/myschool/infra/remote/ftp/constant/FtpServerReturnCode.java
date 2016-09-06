package com.myschool.infra.remote.ftp.constant;

public class FtpServerReturnCode {

    public static final int CODE_100_REQUESTED_ACTION_INITIATED = 100;
    public static final int CODE_110_RESTART_MARKER_REPLAY = 110;
    public static final int CODE_120_SERVICE_READY_IN_NNN_MINUTES = 120;
    public static final int CODE_125_DATA_CONNECTION_ALREADY_OPEN_XFER_STARTING = 125;
    public static final int CODE_150_FILE_STATUS_OK_ABOUT_TO_OPEN_CONNECTION = 150;

    public static final int CODE_200_COMMAND_OKAY = 200;
    public static final int CODE_202_COMMAND_NOT_IMPLEMENTED_AT_THIS_SITE = 202;
    public static final int CODE_211_SYSTEM_STATUS_OR_HELP_REPLY = 211;
    public static final int CODE_212_DIRECTORY_STATUS = 212;
    public static final int CODE_213_FILE_STATUS = 213;
    public static final int CODE_214_HELP_MESSAGE = 214;
    public static final int CODE_215_NAME_SYSTEM_TYPE = 215;
    public static final int CODE_220_SERVICE_READ_FOR_NEW_USER = 220;
    public static final int CODE_221_SERVICE_CLOSING_CONTROL_CONNECTION = 221;
    public static final int CODE_225_DATA_CONNECTION_OPEN_NO_XFER_IN_PROGRESS = 225;
    public static final int CODE_226_CLOSING_DATA_CONNECTION = 226;
    public static final int CODE_227_ENTERING_PASSIVE_MODE = 227;
    public static final int CODE_228_ENTERING_LONG_PASSIVE_MODE = 228;
    public static final int CODE_229_ENTERING_EXTENDED_PASSIVE_MODE = 229;
    public static final int CODE_230_USER_LOGGED_IN_PROCEED = 230;
    public static final int CODE_231_USER_LOGGED_OUT_SERVICE_TERMINATED = 231;
    public static final int CODE_232_LOGOUT_COMMAND_NOTED_WILL_COMPLETE_WHEN_XFER_DONE = 232;
    public static final int CODE_250_REQUESTED_FILE_ACTION_OKAY_COMPLETED = 250;
    public static final int CODE_257_PATHNAME_CREATED = 257;

    public static final int CODE_331_USERNAME_OK_NEED_PASSWORD = 331;
    public static final int CODE_332_NEED_ACCOUNT_FOR_LOGIN = 332;
    public static final int CODE_350_REQUESTED_FILE_ACTION_PENDING_FURTHER_INFO = 350;

    public static final int CODE_421_SERVICE_NOT_AVAILABLE = 421;
    public static final int CODE_425_CANT_OPEN_DATA_CONNECTION = 425;
    public static final int CODE_426_CONNECTION_CLOSED_XFER_ABORTED = 426;
    public static final int CODE_434_REQUESTED_HOST_UNAVAILABLE = 434;
    public static final int CODE_450_REQUESTED_FILE_ACTION_NOT_TAKEN = 450;
    public static final int CODE_451_REQUESTED_ACTION_ABORTED = 451;
    public static final int CODE_452_REQUESTED_ACTION_NOT_TAKEN_INSUFFICIENT_STORAGE = 452;

    public static final int CODE_500_SYNTAX_ERROR_COMMAND_UNRECOGNIZED = 500;
    public static final int CODE_501_SYNTAX_ERROR_IN_PARAMETERS = 501;
    public static final int CODE_502_COMMAND_NOT_IMPLEMENTED = 502;
    public static final int CODE_503_BAD_SEQUENCE_OF_COMMANDS = 503;
    public static final int CODE_504_COMMAND_NOT_IMPLEMENTED_FOR_PARAMETER = 504;
    public static final int CODE_530_NOT_LOGGED_IN = 530;
    public static final int CODE_532_NEED_ACCOUNT_FOR_STORING_FILES = 532;
    public static final int CODE_550_REQUESTED_ACTION_NOT_TAKEN_FILE_NOT_FOUND = 550;
    public static final int CODE_551_REQUESTED_ACTION_ABORTED_PAGE_TYPE_UNKNOWN = 551;
    public static final int CODE_552_ACTION_ABORTED_EXCEEDED_STORAGE_ALLOCATION = 552;
    public static final int CODE_553_ACTION_NOT_TAKEN_FILENAME_NOT_ALLOWED = 553;
}