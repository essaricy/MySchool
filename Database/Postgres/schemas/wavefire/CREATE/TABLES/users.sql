CREATE TABLE wavefire.REF_USER_TYPE (
    USER_TYPE_ID smallint NOT NULL,
    DESCRIPTION varchar(16) NOT NULL,
    PRIMARY KEY (USER_TYPE_ID)
);
ALTER TABLE wavefire.REF_USER_TYPE ADD UNIQUE (USER_TYPE_ID);
ALTER TABLE wavefire.REF_USER_TYPE ADD UNIQUE (DESCRIPTION);


CREATE TABLE wavefire.USERS (
    USER_ID bigint NOT NULL,
    USER_NAME varchar(32) NOT NULL,
    PASSWORD varchar(128) NOT NULL,
    REF_USER_TYPE_ID smallint NOT NULL,
    REF_USER_ID integer NOT NULL,
    PRIMARY KEY (USER_ID)
);
ALTER TABLE wavefire.USERS ADD UNIQUE (USER_ID);
ALTER TABLE wavefire.USERS ADD UNIQUE (USER_NAME);
ALTER TABLE wavefire.USERS ADD CONSTRAINT FK_REF_USER_TYPE_ID FOREIGN KEY (REF_USER_TYPE_ID) REFERENCES wavefire.REF_USER_TYPE (USER_TYPE_ID);
ALTER TABLE wavefire.USERS ADD UNIQUE (REF_USER_TYPE_ID, REF_USER_ID);


CREATE TABLE wavefire.USER_PREFERENCES (
    USER_ID bigint NOT NULL,
    THEME_NAME varchar(32) NOT NULL DEFAULT 'BLUE',
    RECORDS_PER_PAGE int NOT NULL DEFAULT 10,
    ALLOW_ADS char(1) NOT NULL DEFAULT 'Y',
    PRIMARY KEY (USER_ID)
);
ALTER TABLE wavefire.USER_PREFERENCES ADD UNIQUE (USER_ID);
ALTER TABLE wavefire.USER_PREFERENCES ADD CONSTRAINT FK_USER_ID FOREIGN KEY (USER_ID) REFERENCES wavefire.USERS (USER_ID);


CREATE TABLE wavefire.USER_STATISTICS (
    USER_ID bigint NOT NULL,
    NUMBER_OF_VISITS int NOT NULL DEFAULT 0,
    LAST_VISIT timestamp NULL
);
ALTER TABLE wavefire.USER_STATISTICS ADD UNIQUE (USER_ID);
ALTER TABLE wavefire.USER_STATISTICS ADD CONSTRAINT FK_USER_ID FOREIGN KEY (USER_ID) REFERENCES wavefire.USERS (USER_ID);


CREATE TABLE wavefire.DEFAULT_USER_ACCESS (
    REF_USER_TYPE_ID smallint NOT NULL,
    FUNCTION_ID bigint NOT NULL,
    CAN_VIEW char(1) DEFAULT 'N',
    CAN_CREATE char(1) DEFAULT 'N',
    CAN_UPDATE char(1) DEFAULT 'N',
    CAN_DELETE char(1) DEFAULT 'N',
    PRIMARY KEY (REF_USER_TYPE_ID, FUNCTION_ID)
);
ALTER TABLE wavefire.DEFAULT_USER_ACCESS ADD CONSTRAINT CK_DEFAULT_USER_FUNCTION UNIQUE (REF_USER_TYPE_ID, FUNCTION_ID);
ALTER TABLE wavefire.DEFAULT_USER_ACCESS ADD CONSTRAINT FK_FUNCTION_ID FOREIGN KEY (FUNCTION_ID) REFERENCES wavefire.FUNCTION (FUNCTION_ID);
ALTER TABLE wavefire.DEFAULT_USER_ACCESS ADD CONSTRAINT FK_REF_USER_TYPE_ID FOREIGN KEY (REF_USER_TYPE_ID) REFERENCES wavefire.REF_USER_TYPE (USER_TYPE_ID);


CREATE TABLE wavefire.USER_ACCESS (
    USER_ID bigint NOT NULL,
    FUNCTION_ID bigint NOT NULL,
    CAN_VIEW char(1) DEFAULT 'N',
    CAN_CREATE char(1) DEFAULT 'N',
    CAN_UPDATE char(1) DEFAULT 'N',
    CAN_DELETE char(1) DEFAULT 'N'
);
ALTER TABLE wavefire.USER_ACCESS ADD CONSTRAINT CK_USER_FUNCTION UNIQUE (USER_ID, FUNCTION_ID);
