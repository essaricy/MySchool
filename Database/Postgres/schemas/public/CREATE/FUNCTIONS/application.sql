-- ## REF_MODULE trigger functions
CREATE OR REPLACE FUNCTION fn_ref_module() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM function WHERE ref_module_id = OLD.module_id;
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
$BODY$
LANGUAGE plpgsql;

-- ## FUNCTION trigger functions
CREATE OR REPLACE FUNCTION fn_function() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM default_user_access WHERE function_id = OLD.function_id;
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
$BODY$
LANGUAGE plpgsql;

-- ## REF_CLASS trigger functions
CREATE OR REPLACE FUNCTION fn_ref_relationship() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM employee_contact WHERE emergency_contact_relationship = OLD.code;
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

 -- ## Student trigger function
CREATE OR REPLACE FUNCTION fn_upload_tracker() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM upload_file_tracker WHERE tracker_id = OLD.tracker_id;
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
 
  -- ## Student trigger function
CREATE OR REPLACE FUNCTION fn_upload_file_tracker() RETURNS TRIGGER AS $BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM upload_record_tracker WHERE upload_file_id = OLD.upload_file_id;
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
