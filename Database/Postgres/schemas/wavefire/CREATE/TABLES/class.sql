CREATE TABLE wavefire.REF_CLASS (
    CLASS_ID smallint NOT NULL,
    CLASS_NAME varchar(16) NOT NULL,
    PROMOTION_ORDER integer NOT NULL,
    PRIMARY KEY (CLASS_ID)
);
ALTER TABLE wavefire.REF_CLASS ADD UNIQUE (CLASS_ID);
ALTER TABLE wavefire.REF_CLASS ADD UNIQUE (CLASS_NAME);
ALTER TABLE wavefire.REF_CLASS ADD UNIQUE (PROMOTION_ORDER);


CREATE TABLE wavefire.REF_MEDIUM (
    MEDIUM_ID smallint NOT NULL,
    DESCRIPTION varchar(16) NULL,
    PRIMARY KEY (MEDIUM_ID)
);
ALTER TABLE wavefire.REF_MEDIUM ADD UNIQUE (MEDIUM_ID);


CREATE TABLE wavefire.REF_SECTION (
    SECTION_ID smallint NOT NULL,
    SECTION_NAME varchar(32) NOT NULL,
    PRIMARY KEY (SECTION_ID)
);
ALTER TABLE wavefire.REF_SECTION ADD UNIQUE (SECTION_ID);
ALTER TABLE wavefire.REF_SECTION ADD UNIQUE (SECTION_NAME);


CREATE TABLE wavefire.CLASS (
    CLASS_ID integer NOT NULL,
    SCHOOL_ID smallint NOT NULL,
    REF_CLASS_ID smallint NOT NULL,
    REF_MEDIUM_ID smallint NOT NULL,
    REF_SECTION_ID smallint NOT NULL,
    PRIMARY KEY (CLASS_ID)
);
ALTER TABLE wavefire.CLASS ADD UNIQUE (CLASS_ID);
ALTER TABLE wavefire.CLASS ADD CONSTRAINT FK_SCHOOL_ID FOREIGN KEY (SCHOOL_ID) REFERENCES wavefire.SCHOOL (SCHOOL_ID);
ALTER TABLE wavefire.CLASS ADD CONSTRAINT FK_REF_CLASS_ID FOREIGN KEY (REF_CLASS_ID) REFERENCES wavefire.REF_CLASS (CLASS_ID);
ALTER TABLE wavefire.CLASS ADD CONSTRAINT FK_REF_MEDIUM_ID FOREIGN KEY (REF_MEDIUM_ID) REFERENCES wavefire.REF_MEDIUM (MEDIUM_ID);
ALTER TABLE wavefire.CLASS ADD CONSTRAINT FK_REF_SECTION_ID FOREIGN KEY (REF_SECTION_ID) REFERENCES wavefire.REF_SECTION (SECTION_ID);
ALTER TABLE wavefire.CLASS ADD CONSTRAINT CK_SCHOOL_CLASS_MEDIUM_SECTION UNIQUE (SCHOOL_ID, REF_CLASS_ID, REF_MEDIUM_ID, REF_SECTION_ID);


CREATE TABLE wavefire.REF_SUBJECT (
    SUBJECT_ID integer NOT NULL,
    SUBJECT_NAME varchar(24) NOT NULL,
    PRIMARY KEY (SUBJECT_ID)
);
ALTER TABLE wavefire.REF_SUBJECT ADD UNIQUE (SUBJECT_ID);
ALTER TABLE wavefire.REF_SUBJECT ADD UNIQUE (SUBJECT_NAME);


CREATE TABLE wavefire.SUBJECT (
    SUBJECT_ID integer NOT NULL,
    REF_SUBJECT_ID integer NOT NULL,
    CLASS_ID integer NOT NULL,
    PRIMARY KEY (SUBJECT_ID)
);
ALTER TABLE wavefire.SUBJECT ADD UNIQUE (SUBJECT_ID);
ALTER TABLE wavefire.SUBJECT ADD CONSTRAINT FK_CLASS_ID FOREIGN KEY (CLASS_ID) REFERENCES wavefire.CLASS (CLASS_ID);
ALTER TABLE wavefire.SUBJECT ADD CONSTRAINT FK_REF_SUBJECT_ID FOREIGN KEY (REF_SUBJECT_ID) REFERENCES wavefire.REF_SUBJECT (SUBJECT_ID);
ALTER TABLE wavefire.SUBJECT ADD UNIQUE (REF_SUBJECT_ID, CLASS_ID);
