-- ## trigger on ref_region
CREATE TRIGGER tr_before_delete_ref_region
BEFORE DELETE ON wavefire.ref_region
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_ref_region();

-- ## trigger on branch
CREATE TRIGGER tr_before_delete_branch
BEFORE DELETE ON wavefire.branch
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_branch();
