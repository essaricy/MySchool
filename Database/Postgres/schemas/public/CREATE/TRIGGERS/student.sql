-- ## trigger on employee
CREATE TRIGGER tr_before_delete_admission_status
BEFORE DELETE ON admission_status
FOR EACH ROW EXECUTE PROCEDURE fn_admission_status();

-- ## trigger on student
CREATE TRIGGER tr_before_delete_student
BEFORE DELETE ON student
FOR EACH ROW EXECUTE PROCEDURE fn_student();
