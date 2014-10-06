echo off

set DB_NAME=%1
set DB_HOST=localhost
set DB_PORT=5432
set DB_USER=postgres

:: File Path Constants
set TABLE_FILES=CREATE/TABLES
set FUNCTION_FILES=CREATE/FUNCTIONS
set TRIGGER_FILES=CREATE/TRIGGERS
set PERMISSIONS_FILES=PERMISSIONS
set LOAD_FILES=LOAD
set SCRIPT_FILES=SCRIPTS

echo -----------------------------------------------------------------------------------
echo CREATING SCHEMA 'public' IN THE DATABASE '%DB_NAME%'
echo -----------------------------------------------------------------------------------

:: Create tables
echo Creating tables in public schema.
psql -U %DB_USER% -q -f %TABLE_FILES%/academics.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TABLE_FILES%/application.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TABLE_FILES%/branch.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TABLE_FILES%/school.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TABLE_FILES%/class.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TABLE_FILES%/exim.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TABLE_FILES%/users.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TABLE_FILES%/employee.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TABLE_FILES%/student.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TABLE_FILES%/attendance.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TABLE_FILES%/exam.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TABLE_FILES%/notification.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TABLE_FILES%/apar.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TABLE_FILES%/misc.sql %DB_NAME% 

:: Create functions
echo Creating functions.
psql -U %DB_USER% -q -f %FUNCTION_FILES%/academics.sql %DB_NAME%
psql -U %DB_USER% -q -f %FUNCTION_FILES%/application.sql %DB_NAME% 
psql -U %DB_USER% -q -f %FUNCTION_FILES%/branch.sql %DB_NAME% 
psql -U %DB_USER% -q -f %FUNCTION_FILES%/school.sql %DB_NAME% 
psql -U %DB_USER% -q -f %FUNCTION_FILES%/class.sql %DB_NAME% 
psql -U %DB_USER% -q -f %FUNCTION_FILES%/users.sql %DB_NAME% 
psql -U %DB_USER% -q -f %FUNCTION_FILES%/employee.sql %DB_NAME% 
psql -U %DB_USER% -q -f %FUNCTION_FILES%/student.sql %DB_NAME% 
psql -U %DB_USER% -q -f %FUNCTION_FILES%/exam.sql %DB_NAME% 
psql -U %DB_USER% -q -f %FUNCTION_FILES%/notification.sql %DB_NAME%
psql -U %DB_USER% -q -f %FUNCTION_FILES%/attendance.sql %DB_NAME%

:: Create Triggers
echo Creating Triggers.
psql -U %DB_USER% -q -f %TRIGGER_FILES%/application.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TRIGGER_FILES%/branch.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TRIGGER_FILES%/school.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TRIGGER_FILES%/class.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TRIGGER_FILES%/users.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TRIGGER_FILES%/employee.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TRIGGER_FILES%/student.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TRIGGER_FILES%/exam.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TRIGGER_FILES%/notification.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TRIGGER_FILES%/attendance.sql %DB_NAME%

:: Set Permissions
echo Setting permissions
psql -U %DB_USER% -q -f %PERMISSIONS_FILES%/permissions_to_schema.sql %DB_NAME% 

:: Load Data
echo Loading Data
psql -U %DB_USER% -q -f %LOAD_FILES%/academic.sql %DB_NAME% 
psql -U %DB_USER% -q -f %LOAD_FILES%/application.sql %DB_NAME% 
psql -U %DB_USER% -q -f %LOAD_FILES%/notification.sql %DB_NAME% 
psql -U %DB_USER% -q -f %LOAD_FILES%/exim.sql %DB_NAME% 
psql -U %DB_USER% -q -f %LOAD_FILES%/users.sql %DB_NAME% 
psql -U %DB_USER% -q -f %LOAD_FILES%/attendance.sql %DB_NAME%

:: Execute scripts
echo Executing scripts
psql -U %DB_USER% -q -f %SCRIPT_FILES%/set_default_user_access.sql %DB_NAME% 
psql -U %DB_USER% -q -f %SCRIPT_FILES%/set_table_permissions.sql %DB_NAME% 

echo -----------------------------------------------------------------------------------
echo SUCCESSFULLY COMPLETED CREATION OF THE SCHEMA 'public' IN THE DATABASE '%DB_NAME%'
echo -----------------------------------------------------------------------------------
