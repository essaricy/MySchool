-- ## trigger on ref_module
CREATE TRIGGER tr_before_delete_ref_module
BEFORE DELETE ON wavefire.ref_module
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_ref_module();

-- ## trigger on function
CREATE TRIGGER tr_before_delete_function
BEFORE DELETE ON wavefire.function
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_function();

-- ## trigger on ref_user_relationship
CREATE TRIGGER tr_before_delete_ref_relationship
BEFORE DELETE ON wavefire.ref_relationship
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_ref_relationship();

-- ## trigger on upload_tracker
CREATE TRIGGER tr_before_delete_upload_tracker
BEFORE DELETE ON wavefire.upload_tracker
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_upload_tracker();

-- ## trigger on upload_file_tracker
CREATE TRIGGER tr_before_delete_upload_file_tracker
BEFORE DELETE ON wavefire.upload_file_tracker
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_upload_file_tracker();
