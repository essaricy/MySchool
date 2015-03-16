#!/bin/sh
######################################################################################
## @SAUTIL.MODULE.DESCRIPTION@
######################################################################################

MYSCHOOL_DIR=@MYSCHOOL.DIR@
CONFIG_DIR=$MYSCHOOL_DIR/config
FILESYSTEM_DIR=$CONFIG_DIR/filesystem
FILESYSTEM_PROPERTIES=$FILESYSTEM_DIR/FileSystem.properties
CONFIG_LOG_DIR=$CONFIG_DIR/logs
LIB_DIR=$MYSCHOOL_DIR/lib

# Application specific Jars
CLASSPATH=$LIB_DIR/MySchoolSautil.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/MySchoolBase.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/MySchoolService.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/MySchoolTransformation.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/MySchoolInfrastructure.jar

# Third party jars
CLASSPATH=$CLASSPATH;$LIB_DIR/activemq-all-5.6.0.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/batik-xml-1.6.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/commons-codec.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/commons-httpclient.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/commons-logging-1.1.1.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/commons-pool-1.6.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/concurrent-1.3.3.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/dynamicreports-core-2.4.1.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/jcs-1.3.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/jettison-1.3.3.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/jasperreports-4.5.1.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/log4j.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/mail.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/poi-3.7.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/postgresql-9.1-901.jdbc4.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/quartz-2.2.0.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/thumbnailator-0.4.5-all.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/xstream-1.4.4.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/xpp3-1.1.4c.jar

# Spring Jars
CLASSPATH=$CLASSPATH;$LIB_DIR/org.springframework.context-3.0.5.RELEASE.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/org.springframework.beans-3.0.5.RELEASE.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/org.springframework.core-3.0.5.RELEASE.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/org.springframework.asm-3.0.5.RELEASE.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/org.springframework.expression-3.0.5.RELEASE.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/org.springframework.jdbc-3.0.5.RELEASE.jar

CLASSPATH=$CLASSPATH;$LIB_DIR/aopalliance.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/aspectjrt-1.6.11.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/aspectjweaver-1.6.11.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/org.springframework.aop-3.0.5.RELEASE.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/org.springframework.context.support-3.0.5.RELEASE.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/cglib-2.2.jar
CLASSPATH=$CLASSPATH;$LIB_DIR/asm-3.3.1.jar

# Other files to be in classpath
CLASSPATH=$CLASSPATH;$CONFIG_DIR

java -Dlog4j.configuration="file:$CONFIG_LOG_DIR/@SAUTIL.MODULE.LOG.CONFIG@" -Dfilesystem.properties=$FILESYSTEM_PROPERTIES com.myschool.sautil.base.StandAloneUtility --utility-name @SAUTIL.MODULE.CLASS.NAME@ $*
