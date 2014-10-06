#!/bin/sh

TOMCAT_DIR=/usr/myschool/tomcat
ACTIVEMQ_DIR=/usr/myschool/activemq

# Starting ActiveMQ
cd $ACTIVEMQ_DIR/bin
./activemq start
echo Active MQ started successfully.
