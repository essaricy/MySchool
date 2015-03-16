-- ## trigger on ref_user_type
CREATE TRIGGER tr_before_delete_ref_user_type
BEFORE DELETE ON ref_user_type
FOR EACH ROW EXECUTE PROCEDURE fn_ref_user_type();

-- ## trigger on USERS table
CREATE TRIGGER tr_before_delete_users
BEFORE DELETE ON users
FOR EACH ROW EXECUTE PROCEDURE fn_users();

CREATE TRIGGER tr_after_insert_users
AFTER INSERT ON users
FOR EACH ROW EXECUTE PROCEDURE fn_users();

CREATE TRIGGER tr_before_delete_user_session
BEFORE DELETE ON user_session
FOR EACH ROW EXECUTE PROCEDURE fn_user_session();
