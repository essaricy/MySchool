--CREATE OR REPLACE FUNCTION wavefire_audit.set_table_permissions() RETURNS void AS
DO
$BODY$
  DECLARE
    curRecord RECORD;
  BEGIN

    FOR curRecord IN 
        SELECT table_name from information_schema.tables
        WHERE table_catalog='demo'
          AND table_schema='wavefire_audit'
	  ORDER BY table_name ASC
    LOOP
      EXECUTE format('GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE wavefire_audit.%I TO appuser', curRecord.table_name);
      RAISE INFO 'Granted SELECT, INSERT, UPDATE, DELETE permissions to appuser ON "wavefire_audit.%"', curRecord.table_name;
    END LOOP;
  END
$BODY$ LANGUAGE plpgsql;
