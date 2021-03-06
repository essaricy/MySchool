CREATE TABLE ACADEMICS (
    ACADEMIC_YEAR_NAME varchar(16) NOT NULL,
    AY_START_DATE date NOT NULL,
    AY_END_DATE date NOT NULL,
    PRIMARY KEY (ACADEMIC_YEAR_NAME)
);
ALTER TABLE ACADEMICS ADD UNIQUE (ACADEMIC_YEAR_NAME);
ALTER TABLE ACADEMICS ADD UNIQUE (AY_START_DATE);
ALTER TABLE ACADEMICS ADD UNIQUE (AY_END_DATE);

CREATE TABLE HOLIDAYS (
    HOLIDAY_ID bigint NOT NULL,
    HOLIDAY_NAME varchar(32) NOT NULL,
    START_DATE date NOT NULL,
    END_DATE date NOT NULL,
    PRIMARY KEY (HOLIDAY_ID)
);
ALTER TABLE HOLIDAYS ADD UNIQUE (HOLIDAY_ID);
