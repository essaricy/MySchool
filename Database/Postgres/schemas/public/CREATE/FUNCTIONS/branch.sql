- ## REGION trigger functions
CREATE OR REPLACE FUNCTION fn_ref_region() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM ATTENDANCE_PROFILE_REGION WHERE REGION_ID = OLD.REGION_ID;
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


-- ## Branch trigger functions
CREATE OR REPLACE FUNCTION fn_branch() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM school WHERE branch_id = OLD.branch_id;
         DELETE FROM ATTENDANCE_PROFILE_BRANCH WHERE BRANCH_ID = OLD.BRANCH_ID;
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
