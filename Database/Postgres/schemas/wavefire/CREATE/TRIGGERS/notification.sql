-- ## trigger on notification header table
CREATE TRIGGER tr_before_delete_notification_header
BEFORE DELETE ON wavefire.notification_header
FOR EACH ROW EXECUTE PROCEDURE wavefire.fn_notification_header();

