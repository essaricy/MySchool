CREATE TABLE wavefire.ATTENDANCE_CODE (
    CODE char(2) NOT NULL,
    SHORT_DESCRIPTION varchar(32) NOT NULL,
    LONG_DESCRIPTION varchar(512) NULL,
    USE_IN_REFERENCE char(1) NOT NULL DEFAULT 'N',
    USE_IN_ASSIGNMENT char(1) NOT NULL DEFAULT 'N',
    PRIMARY KEY (CODE)
);
ALTER TABLE wavefire.ATTENDANCE_CODE ADD UNIQUE (CODE);
ALTER TABLE wavefire.ATTENDANCE_CODE ADD UNIQUE (SHORT_DESCRIPTION);

CREATE TABLE wavefire.ATTENDANCE_PROFILE (
    PROFILE_ID bigint NOT NULL,
    PROFILE_NAME varchar(128) NOT NULL,
    EFFECTIVE_ACADEMIC varchar(16) NOT NULL,
    PRIMARY KEY (PROFILE_ID)
);
ALTER TABLE wavefire.ATTENDANCE_PROFILE ADD UNIQUE (PROFILE_ID);
ALTER TABLE wavefire.ATTENDANCE_PROFILE ADD UNIQUE (PROFILE_NAME);
ALTER TABLE wavefire.ATTENDANCE_PROFILE ADD CONSTRAINT FK_EFFECTIVE_ACADEMIC FOREIGN KEY (EFFECTIVE_ACADEMIC) REFERENCES ACADEMICS (ACADEMIC_YEAR_NAME);


CREATE TABLE wavefire.ATTENDANCE_MONTH (
    ATTENDANCE_MONTH_ID bigint NOT NULL,
    ATTENDANCE_PROFILE_ID bigint NOT NULL,
    ATTENDANCE_YEAR int NOT NULL,
    MONTH int NOT NULL,
    DAY_1 char(2) NULL,
    DAY_2 char(2) NULL,
    DAY_3 char(2) NULL,
    DAY_4 char(2) NULL,
    DAY_5 char(2) NULL,
    DAY_6 char(2) NULL,
    DAY_7 char(2) NULL,
    DAY_8 char(2) NULL,
    DAY_9 char(2) NULL,
    DAY_10 char(2) NULL,
    DAY_11 char(2) NULL,
    DAY_12 char(2) NULL,
    DAY_13 char(2) NULL,
    DAY_14 char(2) NULL,
    DAY_15 char(2) NULL,
    DAY_16 char(2) NULL,
    DAY_17 char(2) NULL,
    DAY_18 char(2) NULL,
    DAY_19 char(2) NULL,
    DAY_20 char(2) NULL,
    DAY_21 char(2) NULL,
    DAY_22 char(2) NULL,
    DAY_23 char(2) NULL,
    DAY_24 char(2) NULL,
    DAY_25 char(2) NULL,
    DAY_26 char(2) NULL,
    DAY_27 char(2) NULL,
    DAY_28 char(2) NULL,
    DAY_29 char(2) NULL DEFAULT NULL,
    DAY_30 char(2) NULL DEFAULT NULL,
    DAY_31 char(2) NULL DEFAULT NULL,
    PRIMARY KEY (ATTENDANCE_MONTH_ID)
);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD UNIQUE (ATTENDANCE_MONTH_ID);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_ATTENDANCE_PROFILE_ID FOREIGN KEY (ATTENDANCE_PROFILE_ID) REFERENCES wavefire.ATTENDANCE_PROFILE (PROFILE_ID);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT CK_ATTENDANCE_PROFILE_YEAR_MONTH UNIQUE (ATTENDANCE_PROFILE_ID, ATTENDANCE_YEAR, MONTH);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_1_CODE FOREIGN KEY (DAY_1) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_2_CODE FOREIGN KEY (DAY_2) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_3_CODE FOREIGN KEY (DAY_3) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_4_CODE FOREIGN KEY (DAY_4) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_5_CODE FOREIGN KEY (DAY_5) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_6_CODE FOREIGN KEY (DAY_6) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_7_CODE FOREIGN KEY (DAY_7) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_8_CODE FOREIGN KEY (DAY_8) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_9_CODE FOREIGN KEY (DAY_9) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_10_CODE FOREIGN KEY (DAY_10) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_11_CODE FOREIGN KEY (DAY_11) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_12_CODE FOREIGN KEY (DAY_12) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_13_CODE FOREIGN KEY (DAY_13) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_14_CODE FOREIGN KEY (DAY_14) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_15_CODE FOREIGN KEY (DAY_15) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_16_CODE FOREIGN KEY (DAY_16) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_17_CODE FOREIGN KEY (DAY_17) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_18_CODE FOREIGN KEY (DAY_18) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_19_CODE FOREIGN KEY (DAY_19) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_20_CODE FOREIGN KEY (DAY_20) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_21_CODE FOREIGN KEY (DAY_21) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_22_CODE FOREIGN KEY (DAY_22) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_23_CODE FOREIGN KEY (DAY_23) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_24_CODE FOREIGN KEY (DAY_24) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_25_CODE FOREIGN KEY (DAY_25) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_26_CODE FOREIGN KEY (DAY_26) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_27_CODE FOREIGN KEY (DAY_27) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_28_CODE FOREIGN KEY (DAY_28) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_29_CODE FOREIGN KEY (DAY_29) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_30_CODE FOREIGN KEY (DAY_30) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE_MONTH ADD CONSTRAINT FK_DAY_31_CODE FOREIGN KEY (DAY_31) REFERENCES wavefire.ATTENDANCE_CODE (CODE);


CREATE TABLE wavefire.ATTENDANCE_PROFILE_SCHOOL (
    ATTENDANCE_PROFILE_ID bigint NOT NULL,
    SCHOOL_ID smallint NOT NULL
);
ALTER TABLE wavefire.ATTENDANCE_PROFILE_SCHOOL ADD CONSTRAINT CK_ATTENDANCE_PROFILE_SCHOOL UNIQUE (ATTENDANCE_PROFILE_ID, SCHOOL_ID);
ALTER TABLE wavefire.ATTENDANCE_PROFILE_SCHOOL ADD CONSTRAINT FK_ATTENDANCE_PROFILE_ID FOREIGN KEY (ATTENDANCE_PROFILE_ID) REFERENCES wavefire.ATTENDANCE_PROFILE (PROFILE_ID);
ALTER TABLE wavefire.ATTENDANCE_PROFILE_SCHOOL ADD CONSTRAINT FK_SCHOOL_ID FOREIGN KEY (SCHOOL_ID) REFERENCES wavefire.SCHOOL (SCHOOL_ID);


CREATE TABLE wavefire.ATTENDANCE_PROFILE_CLASS (
    ATTENDANCE_PROFILE_ID bigint NOT NULL,
    CLASS_ID integer NOT NULL
);
ALTER TABLE wavefire.ATTENDANCE_PROFILE_CLASS ADD CONSTRAINT CK_ATTENDANCE_PROFILE_CLASS UNIQUE (ATTENDANCE_PROFILE_ID, CLASS_ID);
ALTER TABLE wavefire.ATTENDANCE_PROFILE_CLASS ADD CONSTRAINT FK_ATTENDANCE_PROFILE_ID FOREIGN KEY (ATTENDANCE_PROFILE_ID) REFERENCES wavefire.ATTENDANCE_PROFILE (PROFILE_ID);
ALTER TABLE wavefire.ATTENDANCE_PROFILE_CLASS ADD CONSTRAINT FK_CLASS_ID FOREIGN KEY (CLASS_ID) REFERENCES wavefire.CLASS (CLASS_ID);


CREATE TABLE wavefire.ATTENDANCE (
    ATTENDANCE_ID bigint NOT NULL,
    ATTENDANCE_MONTH_ID bigint NULL,
    USER_TYPE_ID smallint NOT NULL,
    USER_ID integer NOT NULL,
    LOCKED varchar(1) NOT NULL DEFAULT 'N',
    DAY_1 char(2) NULL,
    DAY_2 char(2) NULL,
    DAY_3 char(2) NULL,
    DAY_4 char(2) NULL,
    DAY_5 char(2) NULL,
    DAY_6 char(2) NULL,
    DAY_7 char(2) NULL,
    DAY_8 char(2) NULL,
    DAY_9 char(2) NULL,
    DAY_10 char(2) NULL,
    DAY_11 char(2) NULL,
    DAY_12 char(2) NULL,
    DAY_13 char(2) NULL,
    DAY_14 char(2) NULL,
    DAY_15 char(2) NULL,
    DAY_16 char(2) NULL,
    DAY_17 char(2) NULL,
    DAY_18 char(2) NULL,
    DAY_19 char(2) NULL,
    DAY_20 char(2) NULL,
    DAY_21 char(2) NULL,
    DAY_22 char(2) NULL,
    DAY_23 char(2) NULL,
    DAY_24 char(2) NULL,
    DAY_25 char(2) NULL,
    DAY_26 char(2) NULL,
    DAY_27 char(2) NULL,
    DAY_28 char(2) NULL,
    DAY_29 char(2) NULL DEFAULT NULL,
    DAY_30 char(2) NULL DEFAULT NULL,
    DAY_31 char(2) NULL DEFAULT NULL,
    PRIMARY KEY (ATTENDANCE_ID)
);
ALTER TABLE wavefire.ATTENDANCE ADD UNIQUE (ATTENDANCE_ID);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT CK_ATTENDANCE_MONTH_USER UNIQUE (ATTENDANCE_MONTH_ID, USER_TYPE_ID, USER_ID);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_ATTENDANCE_MONTH_ID FOREIGN KEY (ATTENDANCE_MONTH_ID) REFERENCES ATTENDANCE_MONTH (ATTENDANCE_MONTH_ID);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_USER_TYPE_ID FOREIGN KEY (USER_TYPE_ID) REFERENCES REF_USER_TYPE (USER_TYPE_ID);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_1_CODE FOREIGN KEY (DAY_1) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_2_CODE FOREIGN KEY (DAY_2) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_3_CODE FOREIGN KEY (DAY_3) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_4_CODE FOREIGN KEY (DAY_4) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_5_CODE FOREIGN KEY (DAY_5) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_6_CODE FOREIGN KEY (DAY_6) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_7_CODE FOREIGN KEY (DAY_7) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_8_CODE FOREIGN KEY (DAY_8) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_9_CODE FOREIGN KEY (DAY_9) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_10_CODE FOREIGN KEY (DAY_10) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_11_CODE FOREIGN KEY (DAY_11) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_12_CODE FOREIGN KEY (DAY_12) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_13_CODE FOREIGN KEY (DAY_13) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_14_CODE FOREIGN KEY (DAY_14) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_15_CODE FOREIGN KEY (DAY_15) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_16_CODE FOREIGN KEY (DAY_16) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_17_CODE FOREIGN KEY (DAY_17) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_18_CODE FOREIGN KEY (DAY_18) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_19_CODE FOREIGN KEY (DAY_19) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_20_CODE FOREIGN KEY (DAY_20) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_21_CODE FOREIGN KEY (DAY_21) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_22_CODE FOREIGN KEY (DAY_22) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_23_CODE FOREIGN KEY (DAY_23) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_24_CODE FOREIGN KEY (DAY_24) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_25_CODE FOREIGN KEY (DAY_25) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_26_CODE FOREIGN KEY (DAY_26) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_27_CODE FOREIGN KEY (DAY_27) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_28_CODE FOREIGN KEY (DAY_28) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_29_CODE FOREIGN KEY (DAY_29) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_30_CODE FOREIGN KEY (DAY_30) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
ALTER TABLE wavefire.ATTENDANCE ADD CONSTRAINT FK_DAY_31_CODE FOREIGN KEY (DAY_31) REFERENCES wavefire.ATTENDANCE_CODE (CODE);
