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
   IF NOT EXISTS (
      SELECT *
      FROM   pg_catalog.pg_user
      WHERE  usename = 'appuser') THEN
        CREATE USER appuser WITH PASSWORD 'taz92DpT';
	RAISE INFO 'Created User "appuser"';
   END IF;
END
$body$
