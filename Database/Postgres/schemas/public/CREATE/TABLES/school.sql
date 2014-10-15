CREATE TABLE SCHOOL (
    SCHOOL_ID smallint NOT NULL,
    BRANCH_ID integer NOT NULL,
    REF_DIVISION_ID smallint NOT NULL,
    SCHOOL_NAME varchar(80) NOT NULL,
    ADDRESS varchar(128) NOT NULL,
    PRIMARY_PHONE_NUMBER varchar(16) NOT NULL,
    SECONDARY_PHONE_NUMBER varchar(16) NULL,
    MOBILE_NUMBER varchar(16) NULL,
    FAX_NUMBER varchar(16) NULL,
    EMAIL_ID varchar(32) NULL,
    MAP_URL varchar(512) NULL DEFAULT NULL,
    PRIMARY KEY (SCHOOL_ID)
);
ALTER TABLE SCHOOL ADD UNIQUE (SCHOOL_ID);
ALTER TABLE SCHOOL ADD CONSTRAINT FK_BRANCH_ID FOREIGN KEY (BRANCH_ID) REFERENCES BRANCH (BRANCH_ID);
ALTER TABLE SCHOOL ADD CONSTRAINT FK_REF_DIVISION_ID FOREIGN KEY (REF_DIVISION_ID) REFERENCES REF_DIVISION (DIVISION_ID);