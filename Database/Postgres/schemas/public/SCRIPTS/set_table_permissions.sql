--CREATE OR REPLACE FUNCTION set_table_permissions() RETURNS void AS
DO
$BODY$
  DECLARE
    curRecord RECORD;
  BEGIN

    FOR curRecord IN 
        SELECT table_name from information_schema.tables
        WHERE table_catalog='demo'
          AND table_schema='public'
	  ORDER BY table_name ASC
    LOOP
      EXECUTE format('GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE %I TO appuser', curRecord.table_name);
      RAISE INFO 'Granted SELECT, INSERT, UPDATE, DELETE permissions to appuser ON "%"', curRecord.table_name;
    END LOOP;
  END
$BODY$ LANGUAGE plpgsql;
