#!/bin/bash
################################################################################
# This Script is responsible for processing the report of EURO LEAGUES
#
# 01.00.00
################################################################################

source /data/apps/env/setup.sh
project_configuration=$HOME/resources/configuration.xml
logging_configuration=$HOME/resources/log4j.properties
SYSTEM_PROPERTIES="$SYSTEM_PROPERTIES -Dlog4j=$logging_configuration -Dconfiguration=$project_configuration "

java $JAVA_OPTS $SYSTEM_PROPERTIES -cp $CM_CLASSPATH br.com.tim.mapreduce.EuroLeaguesDriver $@

if [ $? -ne 0 ]
then
    exit 1
fi