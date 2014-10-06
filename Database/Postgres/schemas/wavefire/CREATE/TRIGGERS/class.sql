-- ## trigger on ref_class
CREATE TRIGGER tr_before_delete_ref_class
BEFORE DELETE ON wavefire.ref_class
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_ref_class();

-- ## trigger on class
CREATE TRIGGER tr_before_delete_class
BEFORE DELETE ON wavefire.class
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_class();

-- ## trigger on subject
CREATE TRIGGER tr_before_delete_subject
BEFORE DELETE ON wavefire.subject
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_subject();

