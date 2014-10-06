-- admission status trigger function
CREATE FUNCTION fn_admission_status() RETURNS TRIGGER AS $tr_admission_status$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM student WHERE admission_status_id = OLD.status_id;
         RETURN OLD;
     END IF;
 END;
$tr_admission_status$ LANGUAGE plpgsql;

-- ## Student trigger function
CREATE OR REPLACE FUNCTION fn_student() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM users WHERE ref_user_type_id = 3 and ref_user_id = OLD.student_id;
         DELETE FROM student_family WHERE student_id = OLD.student_id;
         DELETE FROM student_document WHERE student_id = OLD.student_id;
         DELETE FROM student_exam WHERE student_id = OLD.student_id;
         DELETE FROM student_attendance WHERE student_id = OLD.student_id;
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
