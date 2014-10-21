echo off

set DB_NAME=%1
set DB_HOST=localhost
set DB_PORT=5432
set DB_USER=postgres

:: File Path Constants
set USERS_FILES=users

set DBADMIN_USER=dbadmin
set APP_USER=appuser

:: Execute scripts related to dbadmin
cd dbadmin
call create_grant_user.bat %*
cd ..

:: Execute scripts related to appuser
cd appuser
call create_grant_user.bat %*
cd ..

echo -----------------------------------------------------------------------------------
echo ALL USERS CREATED SUCCESSFULLY IN THE DATABASE '%DB_NAME%'
echo -----------------------------------------------------------------------------------
