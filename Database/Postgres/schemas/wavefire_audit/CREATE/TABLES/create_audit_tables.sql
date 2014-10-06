--CREATE OR REPLACE FUNCTION wavefire_audit.create_audit_tables() RETURNS void AS
DO
$BODY$
  DECLARE
    curRecord RECORD;
    curColRecord RECORD;
  BEGIN

    FOR curRecord IN 
        SELECT table_name from information_schema.tables
        WHERE table_catalog='demo'
          AND table_schema='wavefire'
	  ORDER BY table_name ASC
    LOOP
      EXECUTE format('CREATE TABLE wavefire_audit.AUDIT_%I (AUDIT_ID BIGSERIAL)', curRecord.table_name);
      EXECUTE format('ALTER TABLE wavefire_audit.AUDIT_%I ADD COLUMN AUDIT_CODE char(1) NOT NULL', curRecord.table_name);
      EXECUTE format('ALTER TABLE wavefire_audit.AUDIT_%I ADD COLUMN ACT_BY int NOT NULL', curRecord.table_name);
      EXECUTE format('ALTER TABLE wavefire_audit.AUDIT_%I ADD COLUMN ACT_TIMESTAMP timestamp NOT NULL default current_timestamp', curRecord.table_name);

      FOR curColRecord IN 
        SELECT attname, atttypid, attlen, atttypmod
        FROM pg_attribute
        WHERE attrelid = concat('wavefire.', curRecord.table_name) ::regclass
        AND attnum > 0
	AND attname <> 'last_modified_by'
        AND NOT attisdropped
        ORDER BY attnum ASC
      LOOP
        IF (curColRecord.atttypid = 20) THEN
          EXECUTE format('ALTER TABLE wavefire_audit.AUDIT_%I ADD COLUMN %I bigint', curRecord.table_name, curColRecord.attname);
        ELSIF (curColRecord.atttypid = 21) THEN
          EXECUTE format('ALTER TABLE wavefire_audit.AUDIT_%I ADD COLUMN %I smallint', curRecord.table_name, curColRecord.attname);
        ELSIF (curColRecord.atttypid = 23) THEN
          EXECUTE format('ALTER TABLE wavefire_audit.AUDIT_%I ADD COLUMN %I integer', curRecord.table_name, curColRecord.attname);
        ELSIF (curColRecord.atttypid = 701) THEN
          EXECUTE format('ALTER TABLE wavefire_audit.AUDIT_%I ADD COLUMN %I double precision', curRecord.table_name, curColRecord.attname);
        ELSIF (curColRecord.atttypid = 1042) THEN
	  EXECUTE 'ALTER TABLE wavefire_audit.AUDIT_' || curRecord.table_name || ' ADD COLUMN ' || curColRecord.attname || ' char(' || curColRecord.atttypmod || ')';
        ELSIF (curColRecord.atttypid = 1043) THEN
	  EXECUTE 'ALTER TABLE wavefire_audit.AUDIT_' || curRecord.table_name || ' ADD COLUMN ' || curColRecord.attname || ' varchar(' || curColRecord.atttypmod || ')';
        ELSIF (curColRecord.atttypid = 1082) THEN
          EXECUTE format('ALTER TABLE wavefire_audit.AUDIT_%I ADD COLUMN %I date', curRecord.table_name, curColRecord.attname);
        ELSIF (curColRecord.atttypid = 1114) THEN
          EXECUTE format('ALTER TABLE wavefire_audit.AUDIT_%I ADD COLUMN %I timestamp', curRecord.table_name, curColRecord.attname);
	ELSE
	  RAISE EXCEPTION 'Datatype of code % needs to be addressed', curColRecord.atttypid;
        END IF;
        RAISE INFO 'Added column "%" to Audit table "AUDIT_%"', curColRecord.attname, curRecord.table_name;
      END LOOP;

      RAISE INFO 'Created Audit table wavefire_audit.AUDIT_% to "%"', curRecord.table_name, curRecord.table_name;
    END LOOP;

  END
$BODY$ LANGUAGE plpgsql;
