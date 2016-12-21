-- ## trigger on ref_region
CREATE TRIGGER tr_before_delete_ref_region
BEFORE DELETE ON ref_region
FOR EACH ROW EXECUTE PROCEDURE fn_ref_region();

-- ## trigger on branch
CREATE TRIGGER tr_before_delete_branch
BEFORE DELETE ON branch
FOR EACH ROW EXECUTE PROCEDURE fn_branch();
