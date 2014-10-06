-- ## trigger on school
CREATE TRIGGER tr_before_delete_school
BEFORE DELETE ON school
FOR EACH ROW EXECUTE PROCEDURE fn_school();

