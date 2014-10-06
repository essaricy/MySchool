echo off

set PGSQL_PATH=C:\Program Files\PostgreSQL\9.1\bin

:: postgres path and options
set PATH=%PATH%;%PGSQL_PATH%

set DB_NAME=%1
set DB_HOST=localhost
set DB_PORT=5432
set DB_USER=postgres

:: Drop and create the database
echo Deleting database named '%DB_NAME%'
dropdb -h %DB_HOST% -p %DB_PORT% -U %DB_USER% "%DB_NAME%"
createdb -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -O %DB_USER% %DB_NAME%
echo Database '%DB_NAME%' is created.

:: Create all schemas
cd schemas
call create_all_schemas.bat %*
cd ..

cd users
call create_users.bat %*
cd ..

echo ----------------------------------------------------------
echo DATABASE CREATION SCRIPTS FOR '%DB_NAME%' HAVE BEEN COMPLETED SUCCESSFULLY.
