-- ## REPORT trigger functions
CREATE OR REPLACE FUNCTION wavefire.fn_report() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM wavefire.report_criteria WHERE report_key = OLD.report_key;
         RETURN OLD;
     END IF;
     IF TG_OP = 'INSERT' THEN
         -- TODO
         RETURN NEW;
     END IF;
     IF TG_OP = 'UPDATE' THEN
         -- TODO
         RETURN NEW;
     END IF;
 END;
$BODY$
LANGUAGE plpgsql;
