CREATE TABLE wavefire.REPORT (
    REPORT_KEY varchar(32) NOT NULL,
    REPORT_NAME varchar(64) NOT NULL,
    CAN_ADMIN_VIEW char(1) DEFAULT 'N',
    CAN_EMPLOYEE_VIEW char(1) DEFAULT 'N',
    CAN_STUDENT_VIEW char(1) DEFAULT 'N',
    PRIMARY KEY (REPORT_KEY)
);
ALTER TABLE wavefire.REPORT ADD UNIQUE (REPORT_KEY);

CREATE TABLE wavefire.REPORT_CRITERIA (
    REPORT_KEY varchar(32) NOT NULL,
    CRITERIA_NAME varchar(64) NOT NULL,
    CONTROL_TYPE varchar(32) NOT NULL,
    REFERENCE varchar(32) NULL,
    USE varchar(16) NULL,
    PRIMARY KEY (REPORT_KEY, CRITERIA_NAME)
);
ALTER TABLE wavefire.REPORT_CRITERIA ADD UNIQUE (REPORT_KEY, CRITERIA_NAME);
