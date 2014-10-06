--CREATE OR REPLACE FUNCTION wavefire_audit.add_audit_columns() RETURNS void AS
DO
$BODY$
  DECLARE
    curRecord RECORD;
  BEGIN

  FOR curRecord IN 
        SELECT table_name from information_schema.tables
        WHERE table_catalog='demo'
          AND table_schema='wavefire'
	  ORDER BY table_name ASC
    LOOP
      EXECUTE format('ALTER TABLE wavefire.%I ADD COLUMN LAST_MODIFIED_BY int NOT NULL DEFAULT -1', curRecord.table_name);
      RAISE INFO 'Added inline Audit columns to table "wavefire.%"', curRecord.table_name;
    END LOOP;
  END
$BODY$ LANGUAGE plpgsql;
