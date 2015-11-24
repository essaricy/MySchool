#!/bin/sh

TOMCAT_DIR=/usr/myschool/tomcat
ACTIVEMQ_DIR=/usr/myschool/activemq

TOMCAT_HOME=$TOMCAT_DIR

# Stop tomcat server
cd $TOMCAT_HOME/bin/
./shutdown.sh

# Clean up previous copies
echo Cleaning previous working copies.

# Clean up webapp directory
rm -r $TOMCAT_HOME/webapps/demo

# Clean up work directory
rm -r $TOMCAT_HOME/work/Catalina/localhost/demo

# Clean up conf directory
rm -r $TOMCAT_HOME/conf/Catalina/localhost/demo.xml
