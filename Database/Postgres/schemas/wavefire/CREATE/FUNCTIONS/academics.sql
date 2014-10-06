-- ## ACADEMICS trigger functions
CREATE OR REPLACE FUNCTION wavefire.fn_academics() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         -- delete all references in ATTENDANCE_MONTH
         DELETE FROM wavefire.ATTENDANCE_PROFILE WHERE EFFECTIVE_ACADEMIC = OLD.academic_year_name;
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