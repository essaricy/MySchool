#!/bin/sh

TOMCAT_DIR=/usr/myschool/tomcat
ACTIVEMQ_DIR=/usr/myschool/activemq

TOMCAT_HOME=$TOMCAT_DIR

# Clean up previous copies
echo Cleaning previous working copies.

# Clean up webapp directory
rm -r $TOMCAT_HOME/webapps/ratnam
rm -r $TOMCAT_HOME/webapps/demo
rm -r $TOMCAT_HOME/webapps/scm

# Clean up work directory
rm -r $TOMCAT_HOME/work/Catalina/localhost/ratnam
rm -r $TOMCAT_HOME/work/Catalina/localhost/demo
rm -r $TOMCAT_HOME/work/Catalina/localhost/scm

# Clean up conf directory
rm -r $TOMCAT_HOME/conf/Catalina/localhost/ratnam.xml
rm -r $TOMCAT_HOME/conf/Catalina/localhost/demo.xml
rm -r $TOMCAT_HOME/conf/Catalina/localhost/scm.xml
rm -r $TOMCAT_HOME/webapps/ratnam

# Clean up logs deployments
rm -r $TOMCAT_HOME/logs/*.*.log
echo > $TOMCAT_HOME/logs/catalina.out

# Setup permissions
chmod 775 $TOMCAT_HOME/webapps/ratnam.war
chmod 775 $TOMCAT_HOME/webapps/demo.war
chmod 775 $TOMCAT_HOME/webapps/scm.war

echo Starting Tomcat
# Start up tomcat
cd $TOMCAT_HOME/bin/
./startup.sh
