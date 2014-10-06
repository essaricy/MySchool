echo off

set DB_NAME=%1
set DB_HOST=localhost
set DB_PORT=5432
set DB_USER=postgres

:: File Path Constants
set PUBLIC_SCHEMA=public
set WAVEFIRE_SCHEMA=wavefire
set WAVEFIRE_AUDIT_SCHEMA=wavefire_audit

:: Create public schema
cd public
call create_schema.bat %*
cd ..

:: Create wavefire schema
cd wavefire 
call create_schema.bat %*
cd ..

:: Create Audit schema
cd wavefire_audit
call create_schema.bat %*
cd ..

echo -----------------------------------------------------------------------------------
echo ALL SCHEMAS CREATED SUCCESSFULLY IN THE DATABASE '%DB_NAME%'
echo -----------------------------------------------------------------------------------
