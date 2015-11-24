@echo off

:: ######################################################################################
:: @SAUTIL.MODULE.DESCRIPTION@
:: ######################################################################################

set MYSCHOOL_DIR=@@APPL.LOCAL.DIR@@
set DEPLOY_DIR=%MYSCHOOL_DIR%/deploy
set CONFIG_DIR=%DEPLOY_DIR%/config
set FILESYSTEM_DIR=%CONFIG_DIR%/filesystem
set FILESYSTEM_PROPERTIES=%FILESYSTEM_DIR%/FileSystem.properties
set CONFIG_LOG_DIR=%CONFIG_DIR%/logs
set LIB_DIR=%DEPLOY_DIR%/lib

:: Application specific Jars
set CLASSPATH=%LIB_DIR%/MySchoolSautil.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/MySchoolBase.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/MySchoolService.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/MySchoolTransformation.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/MySchoolInfrastructure.jar

:: Third party jars
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/activemq-all-5.6.0.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/aopalliance.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/asm-3.3.1.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/aspectjrt-1.6.11.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/aspectjweaver-1.6.11.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/cglib-2.2.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/commons-codec.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/commons-httpclient.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/commons-logging-1.1.1.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/concurrent-1.3.3.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/dynamicreports-core-2.4.1.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/jasperreports-4.5.1.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/jcs-1.3.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/jettison-1.3.3.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/log4j.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/mail.jar
:: Spring Jars
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/org.springframework.aop-3.0.5.RELEASE.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/org.springframework.asm-3.0.5.RELEASE.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/org.springframework.beans-3.0.5.RELEASE.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/org.springframework.context-3.0.5.RELEASE.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/org.springframework.context.support-3.0.5.RELEASE.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/org.springframework.core-3.0.5.RELEASE.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/org.springframework.expression-3.0.5.RELEASE.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/org.springframework.jdbc-3.0.5.RELEASE.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/poi-3.7.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/postgresql-9.1-901.jdbc4.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/quartz-2.2.0.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/thumbnailator-0.4.5-all.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/xstream-1.4.4.jar
set CLASSPATH=%CLASSPATH%;%LIB_DIR%/xpp3-1.1.4c.jar

:: Other files to be in classpath
set CLASSPATH=%CLASSPATH%;%CONFIG_DIR%

java -Dlog4j.configuration="file:%CONFIG_LOG_DIR%/@SAUTIL.MODULE.LOG.CONFIG@" -Dfilesystem.properties=%FILESYSTEM_PROPERTIES% com.myschool.sautil.base.StandAloneUtility --utility-name @SAUTIL.MODULE.CLASS.NAME@ %* --stay @SAUTIL.MODULE.STAY@
