#!/bin/bash

source /data/apps/env/setup.sh

input=${ENV_BASE}/cdr/reports/euro_leagues/gpdb_input
staging=${ENV_BASE}/cdr/reports/euro_leagues/gpdb_staging
log_dir=${HOME}/logs
log_filename=gpdb-rel-euro-leagues.log

#Quantidade de diretórios para copiar (copy_data) e carregar (load_data)
qtde_dirs=1

reports_table=reports.dw_r_euro_leagues
ext_table=ext.dw_r_euro_leagues

. $LIBSH/sh/commons.sh
. $LIBSH/sh/hive_commons.sh
. $LIBSH/sh/gpdb_commons.sh
. $LIBSH/sh/gpdb_copy_data.sh
. $LIBSH/sh/gpdb_load_data.sh
. $LIBSH/sh/gpdb_partition_creation.sh

insert="INSERT INTO $reports_table 
SELECT
game_date::character::varying(30),
clube_more_goals::character::varying(30),
highest_score::int,
total_goals::int
FROM $ext_table;"

#iniciando arquivo de Log
initializeLog

logInfo ""
logInfo "####################################################"
logInfo "INICIO DO PROCESSO: ingestão GPDB do Relatório EURO LEAGUE"


listDirs=$(getListDirHiveInput ${input})

logInfo "Listando diretorios encontrados: $listDirs"

if [ ! -z "$listDirs" ]; then

	while [ ! -z "$listDirs" ]; do

		#cria a partition diaria da tabela
		logInfo "Criando partição da tabela: $reports_table"
		createPartitionDiaria $reports_table 
		. ${LIBSH}/sh/exit-if-ne-0.sh

		#move arquivos para o staging
		logInfo "Movendo os diretórios de input: $input para o staging: $staging"
		moveToStaging $qtde_dirs $input $staging
		. ${LIBSH}/sh/exit-if-ne-0.sh

		#Truncando as partições
		logInfo "Truncando particoes da tabela: $reports_table"
		truncatePartitionReprocess $reports_table $staging
		. ${LIBSH}/sh/exit-if-ne-0.sh

		#Insere na tabela gerenciada os valores da tabela externa;
		logInfo "Inserindo na tabela gerenciada utilizando a query: $insert"
		loadData "$insert"
		. ${LIBSH}/sh/exit-if-ne-0.sh

		#Remove arquivos do diretório staging
		logInfo "Removendo arquivos do diretório staging: $staging"
		limpaStaging $staging
		
		listDirs=$(getListDirHiveInput ${input})
	
	done

else
	logInfo "Nao existem diretorios para serem procesados"
fi

logInfo ""
logInfo "FIM DO PROCESSO: ingestão GPDB do Relatório EURO LEAGUE"
logInfo "###############################################################################"
