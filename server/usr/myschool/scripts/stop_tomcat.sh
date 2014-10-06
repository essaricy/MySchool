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
