-- ## trigger on exam
CREATE TRIGGER tr_before_delete_exam
BEFORE DELETE ON exam
FOR EACH ROW EXECUTE PROCEDURE fn_exam();

-- ## trigger on subject_exam
CREATE TRIGGER tr_before_delete_subject_exam
BEFORE DELETE ON subject_exam
FOR EACH ROW EXECUTE PROCEDURE fn_subject_exam();

