DO
$body$
BEGIN
   IF NOT EXISTS (
      SELECT *
      FROM   pg_catalog.pg_user
      WHERE  usename = 'dbadmin') THEN
        CREATE USER dbadmin WITH PASSWORD 'MHZcvM2a';
	RAISE INFO 'Created User "dbadmin"';
   END IF;
END
$body$
