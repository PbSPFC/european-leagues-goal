#!/bin/bash

source /data/apps/env/setup.sh

log_dir=${HOME}/logs
log_filename=euro_leagues_install.log

path_staging=${ENV_BASE}/cdr/reports/euro_leagues/gpdb_staging/
path_input=${ENV_BASE}/cdr/reports/euro_leagues/gpdb_input/

ddl_gpdb=${HOME}/sql/create_dw_r_euro_leagues.sql

. $LIBSH/sh/commons.sh

initializeLog

logInfo ""
logInfo "################################################################"
logInfo "           INICIANDO INSTALAÇÃO: EURO LEAGUES"
logInfo "################################################################"

logInfo "----------------------------------------------------------------"
logInfo "Checking if path exists: $path_input"
`hadoop fs -test -d $path_input`
if [ $? -eq 0 ] ;
then
    logInfo "It's ok!"
else
    `hadoop fs -mkdir -p $path_input`
    logInfo "The $path_input path was created successfully!"
fi

logInfo "----------------------------------------------------------------"
logInfo "Checking if path exists: $path_staging"
`hadoop fs -test -d $path_staging`
if [ $? -eq 0 ] ;
then
    logInfo "It's ok!"
else
    `hadoop fs -mkdir -p $path_staging`
    logInfo "The $path_staging path was created successfully!"
fi



logInfo "----------------------------------------------------------------"
logInfo "Executando script de instalação de tabela no GPDB: $ddl_gpdb"
psql -h $host -d $database -U $username -a -f "$ddl_gpdb"
. ${LIBSH}/sh/exit-if-ne-0.sh


logInfo "----------------------------------------------------------------"
logInfo "################################################################"
logInfo "         INSTALADO COM SUCESSO: EURO LEAGUES"
logInfo "################################################################"