-- ## trigger on attendance_code
CREATE TRIGGER tr_before_delete_attendance_code
BEFORE DELETE ON attendance_code
FOR EACH ROW EXECUTE PROCEDURE fn_attendance_code();

-- ## trigger on attendance_profile
CREATE TRIGGER tr_before_delete_attendance_profile
BEFORE DELETE ON attendance_profile
FOR EACH ROW EXECUTE PROCEDURE fn_attendance_profile();

-- ## trigger on attendance_month
CREATE TRIGGER tr_before_delete_attendance_month
BEFORE DELETE ON attendance_month
FOR EACH ROW EXECUTE PROCEDURE fn_attendance_month();
