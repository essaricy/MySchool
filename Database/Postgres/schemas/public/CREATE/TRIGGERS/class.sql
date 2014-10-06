-- ## trigger on ref_class
CREATE TRIGGER tr_before_delete_ref_class
BEFORE DELETE ON ref_class
FOR EACH ROW EXECUTE PROCEDURE fn_ref_class();

-- ## trigger on class
CREATE TRIGGER tr_before_delete_class
BEFORE DELETE ON class
FOR EACH ROW EXECUTE PROCEDURE fn_class();

-- ## trigger on subject
CREATE TRIGGER tr_before_delete_subject
BEFORE DELETE ON subject
FOR EACH ROW EXECUTE PROCEDURE fn_subject();

