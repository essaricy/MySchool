#!/bin/sh

if [ ! -d /usr/myschool ]; then
  mkdir /usr/myschool
fi

if [ ! -d /usr/myschool/scripts ]; then
  mkdir /usr/myschool/scripts
fi

# Make default softwares
ln -sf /opt/softwares/tomcat/apache-tomcat-6.0.35 /opt/softwares/tomcat/default
ln -sf /opt/softwares/jboss/jboss-5.1.0.GA /opt/softwares/jboss/default
ln -sf /usr/lib/postgresql/9.1 /opt/softwares/postgres/default
ln -sf /opt/softwares/activemq/apache-activemq-5.6.0 /opt/softwares/activemq/default
echo Created symlinks for the default softwares

# make symlinks to /usr/myschool directory
ln -sf /opt/softwares/tomcat/default /usr/myschool/tomcat
ln -sf /opt/softwares/jboss/default /usr/myschool/jboss
ln -sf /opt/softwares/postgres/default /usr/myschool/postgres
ln -sf /opt/softwares/activemq/default /usr/myschool/activemq
echo Created software symlinks to /usr/myschool directory.
