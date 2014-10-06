#!/bin/sh
################################################################
# This script is used for creating a new database for the school.
################################################################

DB_NAME=$1
DB_HOST=localhost
DB_PORT=5432
DB_USER=postgres

# Drop and create the database
echo Deleting database named "$DB_NAME"
dropdb -h $DB_HOST -p $DB_PORT -U $DB_USER "$DB_NAME"
createdb -h $DB_HOST -p $DB_PORT -U $DB_USER -O $DB_USER $DB_NAME
echo Database "$DB_NAME" is created.

# Create all schemas
cd schemas
sh ./create_all_schemas.sh $*
cd ..

cd users
sh ./create_users.sh $*
cd ..

echo ----------------------------------------------------------
echo DATABASE CREATION SCRIPTS FOR "$DB_NAME" HAVE BEEN COMPLETED SUCCESSFULLY.

