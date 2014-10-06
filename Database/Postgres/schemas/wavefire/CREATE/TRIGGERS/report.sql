-- ## trigger on ref_module
CREATE TRIGGER tr_before_delete_report
BEFORE DELETE ON wavefire.report
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_report();
