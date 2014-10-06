CREATE TABLE ADMISSION_STATUS (
    STATUS_ID integer NOT NULL,
    DESCRIPTION varchar(64) NOT NULL,
    PRIMARY KEY (STATUS_ID)
);
ALTER TABLE ADMISSION_STATUS ADD UNIQUE (STATUS_ID);
ALTER TABLE ADMISSION_STATUS ADD UNIQUE (DESCRIPTION);

CREATE TABLE STUDENT (
    STUDENT_ID integer NOT NULL,
    ADMISSION_NUMBER varchar(10) NOT NULL,
    CLASS_ID integer NOT NULL,
    ADMISSION_STATUS_ID int NULL,
    DATE_OF_JOINING date NOT NULL,
    FIRST_NAME varchar(16) NOT NULL,
    MIDDLE_NAME varchar(16) NULL,
    LAST_NAME varchar(16) NOT NULL,
    GENDER char(1) NOT NULL,
    DATE_OF_BIRTH date NOT NULL,
    RELIGION varchar(16) NULL,
    CASTE varchar(16) NULL,
    NATIONALITY varchar(8) NULL,
    MOTHER_TONGUE varchar(20) NULL,
    PERMANENT_ADDRESS varchar(128) NOT NULL,
    CORRESPONDENCE_ADDRESS varchar(128) NOT NULL,
    MOBILE_NUMBER varchar(10) NULL,
    IDENTIFICATION_MARKS varchar(64) NULL,
    BLOOD_GROUP varchar(8) NULL,
    REMARKS varchar(128) NULL,
    LAST_ACADEMIC_YEAR varchar(16) NULL,
    VERIFIED char(1) NOT NULL DEFAULT 'N',
    PRIMARY KEY (STUDENT_ID)
);
ALTER TABLE STUDENT ADD UNIQUE (STUDENT_ID);
ALTER TABLE STUDENT ADD UNIQUE (ADMISSION_NUMBER);
ALTER TABLE STUDENT ADD CONSTRAINT FK_CLASS_ID FOREIGN KEY (CLASS_ID) REFERENCES CLASS (CLASS_ID);
ALTER TABLE STUDENT ADD CONSTRAINT FK_ADMISSION_STATUS_ID FOREIGN KEY (ADMISSION_STATUS_ID) REFERENCES ADMISSION_STATUS (STATUS_ID);


CREATE TABLE STUDENT_FAMILY (
    ID integer NOT NULL,
    STUDENT_ID integer NOT NULL,
    RELATIONSHIP_CODE char(1) NOT NULL,
    NAME varchar(32) NOT NULL,
    --,DATE_OF_BIRTH         date NULL,
    OCCUPATION varchar(32) NULL,
    MOBILE_NUMBER varchar(10) NULL,
    EMAIL_ID varchar(32) NULL,
    AVAIL_SMS char(1) NULL,
    AVAIL_EMAIL char(1) NULL,
    PRIMARY KEY (ID)
);
ALTER TABLE STUDENT_FAMILY ADD UNIQUE (ID);
ALTER TABLE STUDENT_FAMILY ADD CONSTRAINT FK_STUDENT_ID FOREIGN KEY (STUDENT_ID) REFERENCES STUDENT (STUDENT_ID);
ALTER TABLE STUDENT_FAMILY ADD CONSTRAINT FK_RELATIONSHIP_CODE FOREIGN KEY (RELATIONSHIP_CODE) REFERENCES REF_RELATIONSHIP (CODE);


CREATE TABLE STUDENT_DOCUMENT (
    STUDENT_DOCUMENT_ID bigint NOT NULL,
    STUDENT_ID integer NOT NULL,
    DOCUMENT_ID integer NOT NULL,
    DOCUMENT_NUMBER varchar(32) NOT NULL,
    DOCUMENT_EXPIRY_DATE date NULL,
    ISSUED_BY varchar(80) NOT NULL,
    PRIMARY KEY (STUDENT_DOCUMENT_ID)
);
ALTER TABLE STUDENT_DOCUMENT ADD UNIQUE (STUDENT_DOCUMENT_ID);
ALTER TABLE STUDENT_DOCUMENT ADD CONSTRAINT FK_STUDENT_ID FOREIGN KEY (STUDENT_ID) REFERENCES STUDENT (STUDENT_ID);
ALTER TABLE STUDENT_DOCUMENT ADD CONSTRAINT FK_DOCUMENT_ID FOREIGN KEY (DOCUMENT_ID) REFERENCES DOCUMENT (DOCUMENT_ID);
