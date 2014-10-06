-- admission status trigger function
CREATE FUNCTION wavefire.fn_admission_status() RETURNS TRIGGER AS $tr_admission_status$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM wavefire.student WHERE admission_status_id = OLD.status_id;
         RETURN OLD;
     END IF;
 END;
$tr_admission_status$ LANGUAGE plpgsql;

-- ## Student trigger function
CREATE OR REPLACE FUNCTION wavefire.fn_student() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM wavefire.users WHERE ref_user_type_id = 3 and ref_user_id = OLD.student_id;
         DELETE FROM wavefire.student_family WHERE student_id = OLD.student_id;
         DELETE FROM wavefire.student_document WHERE student_id = OLD.student_id;
         DELETE FROM wavefire.student_exam WHERE student_id = OLD.student_id;
         DELETE FROM wavefire.student_attendance WHERE student_id = OLD.student_id;
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
