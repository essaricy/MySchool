echo off

set DB_NAME=%1
set DB_HOST=localhost
set DB_PORT=5432
set DB_USER=postgres
set USER_NAME=appuser

:: File Path Constants
set CREATE_FILES=CREATE
set PERMISSIONS_FILES=PERMISSIONS
set SCHEMA_PERMISSIONS_FILES=PERMISSIONS/SCHEMAS

set SCHEMA_PUBLIC_PERMISSIONS_FILES=PERMISSIONS/public
set SCHEMA_WAVEFIRE_PERMISSIONS_FILES=PERMISSIONS/wavefire
set SCHEMA_WAVEFIRE_AUDIT_PERMISSIONS_FILES=PERMISSIONS/wavefire_audit

echo -----------------------------------------------------------------------------------
echo CREATING USER '%USER_NAME%' AND GRANTING PERMISSIONS IN THE DATABASE '%DB_NAME%'
echo -----------------------------------------------------------------------------------

:: Create user
echo Creating user in public schema.
psql -U %DB_USER% -q -f %CREATE_FILES%/create_user.sql %DB_NAME%
 
:: Create functions
echo Granting permissions to the database.
psql -U %DB_USER% -q -f %PERMISSIONS_FILES%/database.sql %DB_NAME%

echo Granting permissions to the schemas.
psql -U %DB_USER% -q -f %SCHEMA_PERMISSIONS_FILES%/schemas.sql %DB_NAME%

echo Granting permissions to the 'public' schema.
psql -U %DB_USER% -q -f %SCHEMA_PUBLIC_PERMISSIONS_FILES%/tables.sql %DB_NAME%
psql -U %DB_USER% -q -f %SCHEMA_PUBLIC_PERMISSIONS_FILES%/functions.sql %DB_NAME%

echo Granting permissions to the 'wavefire' schema.
psql -U %DB_USER% -q -f %SCHEMA_WAVEFIRE_PERMISSIONS_FILES%/tables.sql %DB_NAME%
psql -U %DB_USER% -q -f %SCHEMA_WAVEFIRE_PERMISSIONS_FILES%/functions.sql %DB_NAME%

echo Granting permissions to the 'wavefire_audit' schema.
psql -U %DB_USER% -q -f %SCHEMA_WAVEFIRE_AUDIT_PERMISSIONS_FILES%/tables.sql %DB_NAME%
psql -U %DB_USER% -q -f %SCHEMA_WAVEFIRE_AUDIT_PERMISSIONS_FILES%/functions.sql %DB_NAME%

echo -----------------------------------------------------------------------------------
echo SUCCESSFULLY COMPLETED CREATION OF THE USER '%USER_NAME%' IN THE DATABASE '%DB_NAME%'
echo -----------------------------------------------------------------------------------
