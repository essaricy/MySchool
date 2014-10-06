-- ## trigger on exam
CREATE TRIGGER tr_before_delete_exam
BEFORE DELETE ON wavefire.exam
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_exam();

-- ## trigger on subject_exam
CREATE TRIGGER tr_before_delete_subject_exam
BEFORE DELETE ON wavefire.subject_exam
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_subject_exam();

