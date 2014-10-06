-- ## trigger on ref_module
CREATE TRIGGER tr_before_delete_ref_module
BEFORE DELETE ON ref_module
FOR EACH ROW EXECUTE PROCEDURE fn_ref_module();

-- ## trigger on function
CREATE TRIGGER tr_before_delete_function
BEFORE DELETE ON function
FOR EACH ROW EXECUTE PROCEDURE fn_function();

-- ## trigger on ref_user_relationship
CREATE TRIGGER tr_before_delete_ref_relationship
BEFORE DELETE ON ref_relationship
FOR EACH ROW EXECUTE PROCEDURE fn_ref_relationship();

-- ## trigger on upload_tracker
CREATE TRIGGER tr_before_delete_upload_tracker
BEFORE DELETE ON upload_tracker
FOR EACH ROW EXECUTE PROCEDURE fn_upload_tracker();

-- ## trigger on upload_file_tracker
CREATE TRIGGER tr_before_delete_upload_file_tracker
BEFORE DELETE ON upload_file_tracker
FOR EACH ROW EXECUTE PROCEDURE fn_upload_file_tracker();
