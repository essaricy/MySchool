-- ## trigger on ref_user_type
CREATE TRIGGER tr_before_delete_ref_user_type
BEFORE DELETE ON wavefire.ref_user_type
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_ref_user_type();

-- ## trigger on USERS table
CREATE TRIGGER tr_before_delete_users
BEFORE DELETE ON wavefire.users
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_users();

CREATE TRIGGER tr_after_insert_users
AFTER INSERT ON wavefire.users
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_users();

