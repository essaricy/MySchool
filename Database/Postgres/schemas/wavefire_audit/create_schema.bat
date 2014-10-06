echo off

set DB_NAME=%1
set DB_HOST=localhost
set DB_PORT=5432
set DB_USER=postgres

:: File Path Constants
set SCHEMA_FILES=CREATE/SCHEMAS
set TABLE_FILES=CREATE/TABLES
set TRIGGER_FILES=CREATE/TRIGGERS
set PERMISSIONS_FILES=PERMISSIONS
set SCRIPT_FILES=SCRIPTS

echo -----------------------------------------------------------------------------------
echo CREATING SCHEMA 'wavefire_audit' IN THE DATABASE '%DB_NAME%'
echo -----------------------------------------------------------------------------------

:: Create schema
echo Creating schema for Audit tables
psql -U %DB_USER% -q -f %SCHEMA_FILES%/schema.sql %DB_NAME% 

:: Create tables
echo Creating tables for Audit.
psql -U %DB_USER% -q -f %TABLE_FILES%/add_audit_columns.sql %DB_NAME% 
psql -U %DB_USER% -q -f %TABLE_FILES%/create_audit_tables.sql %DB_NAME% 

:: Set Permissions
echo Setting permissions
psql -U %DB_USER% -q -f %PERMISSIONS_FILES%/permissions_to_schema.sql %DB_NAME% 
psql -U %DB_USER% -q -f %SCRIPT_FILES%/set_table_permissions.sql %DB_NAME% 

echo -----------------------------------------------------------------------------------
echo SUCCESSFULLY COMPLETED CREATION OF THE SCHEMA 'wavefire_audit' IN THE DATABASE '%DB_NAME%'
echo -----------------------------------------------------------------------------------
