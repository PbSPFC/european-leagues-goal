-- Table: reports.DW_r_euro_league

-- DROP TABLE reports.DW_r_euro_league;

CREATE TABLE reports.dw_r_euro_league
(
  game_date character varying(30),
  clube_more_goals character varying(30),
  highest_score int,
  total_goals int
)
WITH (APPENDONLY=true, COMPRESSLEVEL=1, ORIENTATION=row, COMPRESSTYPE=quicklz, 
  OIDS=FALSE
)
DISTRIBUTED BY (create_date)
;
ALTER TABLE reports.DW_r_euro_league
  OWNER TO udbd_load;
GRANT ALL ON TABLE reports.DW_r_euro_league TO udbd_load;
GRANT SELECT, REFERENCES ON TABLE reports.DW_r_euro_league TO sas_va_loader;
GRANT SELECT ON TABLE reports.DW_r_euro_league TO iis_user;
COMMENT ON TABLE reports.DW_r_euro_league
  IS 'Tabela para mostrar a gols das ligas europeias.';
  
CREATE EXTERNAL TABLE ext.DW_r_euro_league
(
  game_date text,
  clube_more_goals text,
  highest_score text,
  total_goals text
)
 LOCATION (
    'gphdfs://silver.timbrasil.com.br/silver/cdr/reports/euro_league/gpdb_staging/*'
)
 FORMAT 'text' (delimiter '|' null '' escape 'off')
ENCODING 'UTF8';
ALTER TABLE ext.DW_r_euro_league
  OWNER TO udbd_load;
GRANT SELECT ON TABLE ext.DW_r_euro_league TO udbd_load;
GRANT SELECT ON TABLE ext.DW_r_euro_league TO iis_user;