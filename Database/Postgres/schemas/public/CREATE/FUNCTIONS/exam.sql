-- ## Exam trigger function
CREATE OR REPLACE FUNCTION fn_exam() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
	 IF TG_OP = 'DELETE' THEN
		 DELETE FROM subject_exam WHERE exam_id = OLD.exam_id;
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

-- ## Subject Exam trigger function
CREATE OR REPLACE FUNCTION fn_subject_exam() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
	 IF TG_OP = 'DELETE' THEN
		 DELETE FROM student_exam WHERE subject_exam_id = OLD.subject_exam_id;
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
