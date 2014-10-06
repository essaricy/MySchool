#!/bin/sh
################################################################
# This script creates the schema name specified
################################################################

DB_NAME=$1
DB_HOST=localhost
DB_PORT=5432
DB_USER=postgres

#File Path Constants
SCHEMA_FILES=CREATE/SCHEMAS
TABLE_FILES=CREATE/TABLES
FUNCTION_FILES=CREATE/FUNCTIONS
TRIGGER_FILES=CREATE/TRIGGERS
PERMISSIONS_FILES=PERMISSIONS
LOAD_FILES=LOAD
SCRIPT_FILES=SCRIPTS

echo -----------------------------------------------------------------------------------
echo CREATING SCHEMA 'wavefire' IN THE DATABASE "$DB_NAME"
echo -----------------------------------------------------------------------------------

# Create schema
psql -U $DB_USER -q -f $SCHEMA_FILES/schema.sql $DB_NAME

# Create tables
echo Creating tables in wavefire schema.
psql -U $DB_USER -q -f $TABLE_FILES/academics.sql $DB_NAME
psql -U $DB_USER -q -f $TABLE_FILES/application.sql $DB_NAME
psql -U $DB_USER -q -f $TABLE_FILES/branch.sql $DB_NAME
psql -U $DB_USER -q -f $TABLE_FILES/school.sql $DB_NAME
psql -U $DB_USER -q -f $TABLE_FILES/class.sql $DB_NAME
psql -U $DB_USER -q -f $TABLE_FILES/exim.sql $DB_NAME
psql -U $DB_USER -q -f $TABLE_FILES/users.sql $DB_NAME
psql -U $DB_USER -q -f $TABLE_FILES/employee.sql $DB_NAME
psql -U $DB_USER -q -f $TABLE_FILES/student.sql $DB_NAME
psql -U $DB_USER -q -f $TABLE_FILES/attendance.sql $DB_NAME
psql -U $DB_USER -q -f $TABLE_FILES/exam.sql $DB_NAME
psql -U $DB_USER -q -f $TABLE_FILES/notification.sql $DB_NAME
psql -U $DB_USER -q -f $TABLE_FILES/apar.sql $DB_NAME
psql -U $DB_USER -q -f $TABLE_FILES/misc.sql $DB_NAME

# Create Functions
echo Creating functions in wavefire schema.
psql -U $DB_USER -q -f $FUNCTION_FILES/academics.sql $DB_NAME
psql -U $DB_USER -q -f $FUNCTION_FILES/application.sql $DB_NAME
psql -U $DB_USER -q -f $FUNCTION_FILES/branch.sql $DB_NAME
psql -U $DB_USER -q -f $FUNCTION_FILES/school.sql $DB_NAME
psql -U $DB_USER -q -f $FUNCTION_FILES/class.sql $DB_NAME
psql -U $DB_USER -q -f $FUNCTION_FILES/users.sql $DB_NAME
psql -U $DB_USER -q -f $FUNCTION_FILES/employee.sql $DB_NAME
psql -U $DB_USER -q -f $FUNCTION_FILES/student.sql $DB_NAME
psql -U $DB_USER -q -f $FUNCTION_FILES/exam.sql $DB_NAME
psql -U $DB_USER -q -f $FUNCTION_FILES/notification.sql $DB_NAME
psql -U $DB_USER -q -f $FUNCTION_FILES/attendance.sql $DB_NAME

# Create Triggers
echo Creating Triggers in wavefire schema.
psql -U $DB_USER -q -f $TRIGGER_FILES/application.sql $DB_NAME
psql -U $DB_USER -q -f $TRIGGER_FILES/branch.sql $DB_NAME
psql -U $DB_USER -q -f $TRIGGER_FILES/school.sql $DB_NAME
psql -U $DB_USER -q -f $TRIGGER_FILES/class.sql $DB_NAME
psql -U $DB_USER -q -f $TRIGGER_FILES/users.sql $DB_NAME
psql -U $DB_USER -q -f $TRIGGER_FILES/employee.sql $DB_NAME
psql -U $DB_USER -q -f $TRIGGER_FILES/student.sql $DB_NAME
psql -U $DB_USER -q -f $TRIGGER_FILES/exam.sql $DB_NAME
psql -U $DB_USER -q -f $TRIGGER_FILES/notification.sql $DB_NAME
psql -U $DB_USER -q -f $TRIGGER_FILES/attendance.sql $DB_NAME

# Set Permissions
echo Setting permissions in wavefire schema.
psql -U $DB_USER -q -f $PERMISSIONS_FILES/permissions_to_schema.sql $DB_NAME

# Load Data
echo Loading Data to in wavefire schema.
psql -U $DB_USER -q -f $LOAD_FILES/academic.sql $DB_NAME
psql -U $DB_USER -q -f $LOAD_FILES/application.sql $DB_NAME
psql -U $DB_USER -q -f $LOAD_FILES/notification.sql $DB_NAME
psql -U $DB_USER -q -f $LOAD_FILES/exim.sql $DB_NAME
psql -U $DB_USER -q -f $LOAD_FILES/users.sql $DB_NAME
psql -U $DB_USER -q -f $LOAD_FILES/attendance.sql $DB_NAME

# Execute scripts
echo Executing  scripts in wavefire schema.
psql -U $DB_USER -q -f $SCRIPT_FILES/set_default_user_access.sql $DB_NAME
psql -U $DB_USER -q -f $SCRIPT_FILES/set_table_permissions.sql $DB_NAME

echo -----------------------------------------------------------------------------------
echo SUCCESSFULLY COMPLETED CREATION OF THE SCHEMA 'wavefire' IN THE DATABASE "$DB_NAME"
echo -----------------------------------------------------------------------------------
