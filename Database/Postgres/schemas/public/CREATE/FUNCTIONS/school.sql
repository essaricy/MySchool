-- ## School trigger function
CREATE OR REPLACE FUNCTION fn_school() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM class WHERE school_id = OLD.school_id;
         DELETE FROM ATTENDANCE_PROFILE_SCHOOL WHERE BRANCH_ID = OLD.SCHOOL_ID;
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
 $BODY$ LANGUAGE plpgsql;
