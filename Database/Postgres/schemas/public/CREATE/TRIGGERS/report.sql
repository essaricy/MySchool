-- ## trigger on ref_module
CREATE TRIGGER tr_before_delete_report
BEFORE DELETE ON report
FOR EACH ROW EXECUTE PROCEDURE fn_report();
