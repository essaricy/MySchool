-- ## REF_CLASS trigger functions
CREATE OR REPLACE FUNCTION fn_ref_class() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM class WHERE ref_class_id = OLD.class_id;
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

-- ## Class trigger function
CREATE OR REPLACE FUNCTION fn_class() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM ref_attendance WHERE class_id = OLD.class_id;
         DELETE FROM subject WHERE class_id = OLD.class_id;
         DELETE FROM student WHERE class_id = OLD.class_id;
         DELETE FROM exam WHERE class_id = OLD.class_id;
         DELETE FROM ATTENDANCE_PROFILE_CLASS WHERE BRANCH_ID = OLD.CLASS_ID;
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


-- ## Subject trigger function
CREATE OR REPLACE FUNCTION fn_subject() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM employee_subject WHERE subject_id = OLD.subject_id;
         DELETE FROM subject_exam WHERE subject_id = OLD.subject_id;
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
