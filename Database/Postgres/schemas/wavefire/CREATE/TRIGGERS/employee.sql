-- ## trigger on document
CREATE TRIGGER tr_before_delete_document
BEFORE DELETE ON wavefire.document
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_document();

-- ## trigger on employee
CREATE TRIGGER tr_before_delete_designation
BEFORE DELETE ON wavefire.ref_designation
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_designation();

-- ## trigger on employee
CREATE TRIGGER tr_before_delete_employment_status
BEFORE DELETE ON wavefire.employment_status
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_employment_status();

-- ## trigger on employee
CREATE TRIGGER tr_before_delete_employee
BEFORE DELETE ON wavefire.employee
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_employee();