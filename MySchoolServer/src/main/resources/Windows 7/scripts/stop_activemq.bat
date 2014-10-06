#!/bin/sh

TOMCAT_DIR=/usr/myschool/tomcat
ACTIVEMQ_DIR=/usr/myschool/activemq

# Stopping ActiveMQ
cd $ACTIVEMQ_DIR/bin
./activemq stop
echo Active MQ stopped successfully.
