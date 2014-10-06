-- ## REF_USER_TYPE trigger functions
CREATE OR REPLACE FUNCTION fn_ref_user_type() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM default_user_access WHERE ref_user_type_id = OLD.user_type_id;
         DELETE FROM users WHERE ref_user_type_id = OLD.user_type_id;
         DELETE FROM ATTENDANCE WHERE USER_TYPE_ID = OLD.user_type_id;
         RETURN OLD;
     END IF;
     IF TG_OP = 'INSERT' THEN
         -- TODO
         RETURN NEW;
     END IF;
     IF TG_OP = 'UPDATE' THEN
         -- TODO
         RETURN NEW;
     END IF;
 END;
$BODY$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION fn_users() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM user_preferences WHERE user_id = OLD.user_id;
         DELETE FROM user_statistics WHERE user_id = OLD.user_id;
         DELETE FROM ATTENDANCE WHERE USER_TYPE_ID = OLD.ref_user_type_id AND USER_ID=OLD.user_id;
         RETURN OLD;
     END IF;
     IF TG_OP = 'INSERT' THEN
         INSERT INTO user_preferences (user_id) VALUES (NEW.user_id);
         INSERT INTO user_statistics (user_id) VALUES (NEW.user_id);
         RETURN NEW;
     END IF;
     IF TG_OP = 'UPDATE' THEN
         -- TODO
         RETURN NEW;
     END IF;
 END;
 $BODY$ LANGUAGE plpgsql;

