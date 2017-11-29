#!/bin/bash

source /data/apps/env/setup.sh

log_dir=${HOME}/logs
log_filename=euro_leagues_rollback.log

ddl_gpdb=${HOME}/sql/drop_tables.sql

. $LIBSH/sh/commons.sh

initializeLog

logInfo ""
logInfo "################################################################"
logInfo "             INICIANDO ROLLBACK: EURO LEAGUES"
logInfo "################################################################"

logInfo "----------------------------------------------------------------"
logInfo "Executando script de remoção das tabelas no GPDB: $ddl_gpdb"
psql -h $host -d $database -U $username -a -f "$ddl_gpdb"
. ${LIBSH}/sh/exit-if-ne-0.sh


logInfo "----------------------------------------------------------------"
logInfo "################################################################"
logInfo "            ROLLBACK FINALIZADO: EURO LEAGUES"
logInfo "################################################################"