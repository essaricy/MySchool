-- ## REF_USER_TYPE trigger functions
CREATE OR REPLACE FUNCTION wavefire.fn_ref_user_type() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM wavefire.default_user_access WHERE ref_user_type_id = OLD.user_type_id;
         DELETE FROM wavefire.users WHERE ref_user_type_id = OLD.user_type_id;
         DELETE FROM wavefire.ATTENDANCE WHERE USER_TYPE_ID = OLD.user_type_id;
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


CREATE OR REPLACE FUNCTION wavefire.fn_users() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM wavefire.user_preferences WHERE user_id = OLD.user_id;
         DELETE FROM wavefire.user_session WHERE user_id = OLD.user_id;
         DELETE FROM wavefire.ATTENDANCE WHERE USER_TYPE_ID = OLD.ref_user_type_id AND USER_ID=OLD.user_id;
         RETURN OLD;
     END IF;
     IF TG_OP = 'INSERT' THEN
         INSERT INTO wavefire.user_preferences (user_id) VALUES (NEW.user_id);
         RETURN NEW;
     END IF;
     IF TG_OP = 'UPDATE' THEN
         -- TODO
         RETURN NEW;
     END IF;
 END;
 $BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION wavefire.fn_user_session() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM wavefire.user_activity WHERE session_id = OLD.user_id;
         RETURN OLD;
     END IF;
     IF TG_OP = 'INSERT' THEN
         RETURN NEW;
     END IF;
     IF TG_OP = 'UPDATE' THEN
         -- TODO
         RETURN NEW;
     END IF;
 END;
 $BODY$ LANGUAGE plpgsql;
