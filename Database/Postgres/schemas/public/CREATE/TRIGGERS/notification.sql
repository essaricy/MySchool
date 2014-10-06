-- ## trigger on notification header table
CREATE TRIGGER tr_before_delete_notification_header
BEFORE DELETE ON notification_header
FOR EACH ROW EXECUTE PROCEDURE fn_notification_header();

