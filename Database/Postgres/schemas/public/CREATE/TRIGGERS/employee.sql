-- ## trigger on document
CREATE TRIGGER tr_before_delete_document
BEFORE DELETE ON document
FOR EACH ROW EXECUTE PROCEDURE fn_document();

-- ## trigger on employee
CREATE TRIGGER tr_before_delete_designation
BEFORE DELETE ON ref_designation
FOR EACH ROW EXECUTE PROCEDURE fn_designation();

-- ## trigger on employee
CREATE TRIGGER tr_before_delete_employment_status
BEFORE DELETE ON employment_status
FOR EACH ROW EXECUTE PROCEDURE fn_employment_status();

-- ## trigger on employee
CREATE TRIGGER tr_before_delete_employee
BEFORE DELETE ON employee
FOR EACH ROW EXECUTE PROCEDURE fn_employee();