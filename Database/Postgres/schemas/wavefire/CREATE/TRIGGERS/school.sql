-- ## trigger on school
CREATE TRIGGER tr_before_delete_school
BEFORE DELETE ON wavefire.school
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_school();

