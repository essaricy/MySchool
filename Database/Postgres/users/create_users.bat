echo off

set DB_NAME=%1
set DB_HOST=localhost
set DB_PORT=5432
set DB_USER=postgres

:: File Path Constants
set USERS_FILES=users

::set DBADMIN_USER=dbadmin
::set APP_USER=appuser

:: Create database administrator
psql -U %DB_USER% -q -f create_users.sql %DB_NAME% 
psql -U %DB_USER% -q -f permissions_to_database.sql %DB_NAME% 


echo -----------------------------------------------------------------------------------
echo ALL USERS CREATED SUCCESSFULLY IN THE DATABASE '%DB_NAME%'
echo -----------------------------------------------------------------------------------
