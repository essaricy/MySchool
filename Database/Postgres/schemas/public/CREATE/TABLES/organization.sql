CREATE TABLE ORGANIZATION (
    ID int NOT NULL,
    NAME varchar(24) NOT NULL,
    ADDRESS varchar(128) NOT NULL,
    PHONE_NUMBER varchar(16) NOT NULL,
    FAX_NUMBER varchar(16) NULL,
    PRIMARY KEY (ID)
);
ALTER TABLE ORGANIZATION ADD UNIQUE (ID);
ALTER TABLE ORGANIZATION ADD CONSTRAINT ORGANIZATION_ONE_ROW CHECK(ID = 1);


CREATE TABLE ORGANIZATION_PREFERENCES (
    ORGANIZATION_ID int NOT NULL,
    /* Notification related columns */
    NOTIF_USE_EMAILS char(1) NOT NULL DEFAULT 'N',
    NOTIF_EMAIL_EMPLOYEES char(1) NOT NULL DEFAULT 'N',
    NOTIF_EMAIL_STUDENTS char(1) NOT NULL DEFAULT 'N',
    NOTIF_USE_TEXT char(1) NOT NULL DEFAULT 'N',
    NOTIF_TEXT_EMPLOYEES char(1) NOT NULL DEFAULT 'N',
    NOTIF_TEXT_STUDENTS char(1) NOT NULL DEFAULT 'N',

    /* Display Preferences */
    USE_MENU_ICONS char(1) NOT NULL DEFAULT 'N',
    DEFAULT_THEME varchar(128) NOT NULL,
    PINNED_GALLERY varchar(128) NULL,

    /* public data management */
    USE_EMPLOYEE_SELF_SUBMIT char(1) NOT NULL DEFAULT 'N',
    USE_STUDENT_SELF_SUBMIT char(1) NOT NULL DEFAULT 'N'
);
ALTER TABLE ORGANIZATION_PREFERENCES ADD UNIQUE (ORGANIZATION_ID);
ALTER TABLE ORGANIZATION_PREFERENCES ADD CONSTRAINT FK_ORGANIZATION_PREFERENCES_ID FOREIGN KEY (ORGANIZATION_ID) REFERENCES ORGANIZATION (ID);


CREATE TABLE ORGANIZATION_MANIFEST (
    ORGANIZATION_ID int NOT NULL,
    CURRENT_AY_NAME varchar(16) NOT NULL,
    MAP_URL varchar(512) NULL DEFAULT NULL,
    AYE_IN_PROGRESS char(1) NOT NULL DEFAULT 'N'
);
ALTER TABLE ORGANIZATION_MANIFEST ADD UNIQUE (ORGANIZATION_ID);
ALTER TABLE ORGANIZATION_MANIFEST ADD CONSTRAINT FK_ORGANIZATION_MANIFEST_ID FOREIGN KEY (ORGANIZATION_ID) REFERENCES ORGANIZATION (ID);
ALTER TABLE ORGANIZATION_MANIFEST ADD CONSTRAINT FK_ORGANIZATION_MANIFEST_ACADEMICS FOREIGN KEY (CURRENT_AY_NAME) REFERENCES ACADEMICS (ACADEMIC_YEAR_NAME);

