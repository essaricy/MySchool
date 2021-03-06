INSERT INTO wavefire.REF_USER_TYPE (USER_TYPE_ID, DESCRIPTION) VALUES(1,'Admin');
INSERT INTO wavefire.REF_USER_TYPE (USER_TYPE_ID, DESCRIPTION) VALUES(2,'Employee');
INSERT INTO wavefire.REF_USER_TYPE (USER_TYPE_ID, DESCRIPTION) VALUES(3,'Student');

INSERT INTO wavefire.REF_RELATIONSHIP (CODE, NAME) VALUES ('B', 'Brother');
INSERT INTO wavefire.REF_RELATIONSHIP (CODE, NAME) VALUES ('D', 'Daughter');
INSERT INTO wavefire.REF_RELATIONSHIP (CODE, NAME) VALUES ('F', 'Father');
INSERT INTO wavefire.REF_RELATIONSHIP (CODE, NAME) VALUES ('G', 'Guardian');
INSERT INTO wavefire.REF_RELATIONSHIP (CODE, NAME) VALUES ('H', 'Husband');
INSERT INTO wavefire.REF_RELATIONSHIP (CODE, NAME) VALUES ('M', 'Mother');
INSERT INTO wavefire.REF_RELATIONSHIP (CODE, NAME) VALUES ('O', 'Son');
INSERT INTO wavefire.REF_RELATIONSHIP (CODE, NAME) VALUES ('S', 'Sister');
INSERT INTO wavefire.REF_RELATIONSHIP (CODE, NAME) VALUES ('W', 'Wife');

INSERT INTO wavefire.REF_CREDIT_TYPE (CODE, DESCRIPTION) VALUES ('C', 'Credit');
INSERT INTO wavefire.REF_CREDIT_TYPE (CODE, DESCRIPTION) VALUES ('D', 'Debit');

INSERT INTO wavefire.ISSUE_STATUS( STATUS_ID, DESCRIPTION) VALUES (1, 'OPEN');
INSERT INTO wavefire.ISSUE_STATUS( STATUS_ID, DESCRIPTION) VALUES (2, 'CLOSED');

INSERT INTO wavefire.UPLOAD_STATUS (UPLOAD_STATUS_ID, DESCRIPTION) VALUES ((SELECT COALESCE(MAX(UPLOAD_STATUS_ID)+1, 1) FROM wavefire.UPLOAD_STATUS), 'Not Started');
INSERT INTO wavefire.UPLOAD_STATUS (UPLOAD_STATUS_ID, DESCRIPTION) VALUES ((SELECT COALESCE(MAX(UPLOAD_STATUS_ID)+1, 1) FROM wavefire.UPLOAD_STATUS), 'Started');
INSERT INTO wavefire.UPLOAD_STATUS (UPLOAD_STATUS_ID, DESCRIPTION) VALUES ((SELECT COALESCE(MAX(UPLOAD_STATUS_ID)+1, 1) FROM wavefire.UPLOAD_STATUS), 'Completed');
INSERT INTO wavefire.UPLOAD_STATUS (UPLOAD_STATUS_ID, DESCRIPTION) VALUES ((SELECT COALESCE(MAX(UPLOAD_STATUS_ID)+1, 1) FROM wavefire.UPLOAD_STATUS), 'Failed');

INSERT INTO wavefire.REF_MODULE (MODULE_ID, MODULE_NAME, CAN_ADMIN_ACCESS, CAN_EMPLOYEE_ACCESS, CAN_STUDENT_ACCESS, ACCESS_URL) VALUES (
    (SELECT COALESCE(MAX(MODULE_ID)+1, 1) FROM wavefire.REF_MODULE), 'Admin', 'Y', 'N', 'N', '#');
INSERT INTO wavefire.REF_MODULE (MODULE_ID, MODULE_NAME, CAN_ADMIN_ACCESS, CAN_EMPLOYEE_ACCESS, CAN_STUDENT_ACCESS, ACCESS_URL) VALUES (
    (SELECT COALESCE(MAX(MODULE_ID)+1, 1) FROM wavefire.REF_MODULE), 'Master', 'Y', 'N', 'N', '#');
INSERT INTO wavefire.REF_MODULE (MODULE_ID, MODULE_NAME, CAN_ADMIN_ACCESS, CAN_EMPLOYEE_ACCESS, CAN_STUDENT_ACCESS, ACCESS_URL) VALUES (
    (SELECT COALESCE(MAX(MODULE_ID)+1, 1) FROM wavefire.REF_MODULE), 'Student', 'N', 'N', 'Y', '#');
INSERT INTO wavefire.REF_MODULE (MODULE_ID, MODULE_NAME, CAN_ADMIN_ACCESS, CAN_EMPLOYEE_ACCESS, CAN_STUDENT_ACCESS, ACCESS_URL) VALUES (
    (SELECT COALESCE(MAX(MODULE_ID)+1, 1) FROM wavefire.REF_MODULE), 'Employee', 'N', 'Y', 'N', '#');
INSERT INTO wavefire.REF_MODULE (MODULE_ID, MODULE_NAME, CAN_ADMIN_ACCESS, CAN_EMPLOYEE_ACCESS, CAN_STUDENT_ACCESS, ACCESS_URL) VALUES (
    (SELECT COALESCE(MAX(MODULE_ID)+1, 1) FROM wavefire.REF_MODULE), 'Notifications', 'Y', 'Y', 'N', '#');
INSERT INTO wavefire.REF_MODULE (MODULE_ID, MODULE_NAME, CAN_ADMIN_ACCESS, CAN_EMPLOYEE_ACCESS, CAN_STUDENT_ACCESS, ACCESS_URL) VALUES (
    (SELECT COALESCE(MAX(MODULE_ID)+1, 1) FROM wavefire.REF_MODULE), 'Downloads', 'Y', 'Y', 'Y', '#');
INSERT INTO wavefire.REF_MODULE (MODULE_ID, MODULE_NAME, CAN_ADMIN_ACCESS, CAN_EMPLOYEE_ACCESS, CAN_STUDENT_ACCESS, ACCESS_URL) VALUES (
    (SELECT COALESCE(MAX(MODULE_ID)+1, 1) FROM wavefire.REF_MODULE), 'Privileges', 'Y', 'N', 'N', '#');
INSERT INTO wavefire.REF_MODULE (MODULE_ID, MODULE_NAME, CAN_ADMIN_ACCESS, CAN_EMPLOYEE_ACCESS, CAN_STUDENT_ACCESS, ACCESS_URL) VALUES (
    (SELECT COALESCE(MAX(MODULE_ID)+1, 1) FROM wavefire.REF_MODULE), 'Statistics', 'Y', 'Y', 'N', '#');
INSERT INTO wavefire.REF_MODULE (MODULE_ID, MODULE_NAME, CAN_ADMIN_ACCESS, CAN_EMPLOYEE_ACCESS, CAN_STUDENT_ACCESS, ACCESS_URL) VALUES (
    (SELECT COALESCE(MAX(MODULE_ID)+1, 1) FROM wavefire.REF_MODULE), 'Web Admin', 'Y', 'N', 'N', '#');

-- Admin FUNCTIONS
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Academics',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Admin'), 'academic/list.htm', 'book.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Branches',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Admin'),'branch/list.htm', 'arrow_branch.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Master Classes',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Admin'), 'class/list.htm', 'projection_screen.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Designations',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Admin'), 'designation/list.htm', 'medal_gold.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Divisions',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Admin'), 'division/list.htm', 'arrow_divide.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Exam Grades',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Admin'), 'examGrade/list.htm', 'star.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Holidays',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Admin'), 'holiday/list.htm', 'film.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Mediums',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Admin'), 'medium/list.htm', 'style.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Regions',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Admin'), 'region/list.htm', 'shape_group.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Schools',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Admin'), 'school/list.htm', 'vcard.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Sections',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Admin'), 'section/list.htm', 'shape_flip_horizontal.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Master Subjects',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Admin'), 'subject/list.htm', 'book_open.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Documents',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Admin'), 'document/list.htm', 'page_white_stack.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Admission Status',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Admin'), 'admission-status/list.htm', 'status_online.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Employment Status',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Admin'), 'employment/list.htm', 'status_online.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Relationship Codes',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Admin'), 'relationship/list.htm', 'heart.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Process Academic Year Closure',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Admin'), 'aye/show.htm', 'flag_blue.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Profile Information',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Admin'), 'profile/list.htm', 'shield.png');

-- Mater Functions
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Export Data',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Master'), 'export/list.htm', 'document_small_upload.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Upload Data',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Master'), 'upload/listDataUpload.htm', 'upload.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Classes',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Master'), 'class/listRegisteredClasses.htm', 'projection_screen_present.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Student Marks',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Master'), 'student-exam/viewExamResults.htm', 'clipboard_paste_image.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Employee Attendance',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Master'), 'attendance/employeeAttendance.htm', 'color_swatch.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Attendance Profiles',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Master'), 'attendance/list.htm', 'color_swatch.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Student Attendance',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Master'), 'attendance/studentAttendance.htm', 'color_swatch.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Employees',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Master'), 'employee/search.htm', 'group_green.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Exams',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Master'), 'exam/list.htm', 'clipboard_text.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Students',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Master'), 'student/search.htm', 'group_blue.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Subjects',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Master'), 'registeredSubject/list.htm', 'bookmark_book_open.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Students (Portal)',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Master'), 'registeredSubject/list.htm', 'bookmark_book_open.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Manage Employees (Portal)',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Master'), 'registeredSubject/list.htm', 'bookmark_book_open.png');

-- Student Functions
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'My Admission Information',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Student'), 'student/viewMyAdmission.htm', NULL);
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'My Attendance',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Student'), 'student/viewMyAttendance.htm', NULL);
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'My Class Details',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Student'), 'student/viewMyClass.htm', NULL);
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'My Documents Submitted',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Student'), 'student/viewMyDocuments.htm', NULL);
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'My Family Details',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Student'), 'student/viewMyFamily.htm', NULL);
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'My Fee Information',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Student'), 'student/viewMyFee.htm', NULL);
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'My Personal Information',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Student'), 'student/viewMyPersonal.htm', NULL);
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'My Progress Report',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Student'), 'student/viewMyProgress.htm', NULL);

-- Employee Functions
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'My Employment Information',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Employee'), 'employee/viewMyEmployment.htm', NULL);
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'My Attendance',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Employee'), 'employee/viewMyAttendance.htm', NULL);
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'My Contact Information',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Employee'), 'employee/viewMyContact.htm', NULL);
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'My Designation and Promotions',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Employee'), 'employee/viewMyPromotions.htm', NULL);
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'My Documents',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Employee'), 'employee/viewMyDocuments.htm', NULL);
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'My Education Information',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Employee'), 'employee/viewMyEducation.htm', NULL);
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'My Experience Information',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Employee'), 'employee/viewMyExperience.htm', NULL);
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'My Classes and Subjects',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Employee'), 'employee/viewMyClasses.htm', NULL);

-- Notification Functions
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'View Notification Templates',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Notifications'), 'notification/list.htm', 'doc_table.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Notify Students',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Notifications'), 'notification/listStudents.htm', 'bell.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Notify Employees',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Notifications'), 'notification/listEmployees.htm', 'bell.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Notification History',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Notifications'), 'notification/history.htm', 'clock.png');

-- DOWNLOAD Functions
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Brochures',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Downloads'), 'download/brochures.htm', 'doc_pdf.png');

-- Privileges Functions
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Default Privileges',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Privileges'), 'privileges/defaultPrivileges.htm', 'group_green_edit.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'User Privileges',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Privileges'), 'privileges/userPrivileges.htm', 'group_green_new.png');

-- Statistics Functions
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Reports',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Statistics'), 'reports/list.htm', 'report.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Graphs',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Statistics'), 'graphs/list.htm', 'chart_bar.png');

-- Web Admin Functions
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'View Issues',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Web Admin'), 'issue/list.htm', 'bug.png');
INSERT INTO wavefire.FUNCTION (FUNCTION_ID, FUNCTION_NAME, REF_MODULE_ID, ACCESS_URL, ICON_URL) VALUES (
    (SELECT COALESCE(MAX(FUNCTION_ID)+1, 1) FROM wavefire.FUNCTION), 'Usage Statistics',
    (SELECT MODULE_ID FROM wavefire.REF_MODULE WHERE MODULE_NAME = 'Web Admin'), 'usage/list.htm', 'calculator.png');
