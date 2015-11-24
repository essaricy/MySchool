#!/bin/sh

TOMCAT_DIR=/usr/myschool/tomcat
ACTIVEMQ_DIR=/usr/myschool/activemq

TOMCAT_HOME=$TOMCAT_DIR

# Clean up previous copies
echo Cleaning previous working copies.

# Clean up webapp directory
rm -r $TOMCAT_HOME/webapps/demo

# Clean up work directory
rm -r $TOMCAT_HOME/work/Catalina/localhost/demo

# Clean up conf directory
rm -r $TOMCAT_HOME/conf/Catalina/localhost/demo.xml

# Clean up logs deployments
rm -r $TOMCAT_HOME/logs/*.*.log
echo > $TOMCAT_HOME/logs/catalina.out

# Setup permissions
chmod 775 $TOMCAT_HOME/webapps/demo.war

echo Starting Tomcat
# Start up tomcat
cd $TOMCAT_HOME/bin/
./startup.sh
