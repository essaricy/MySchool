-- ## Employee document trigger function
CREATE FUNCTION fn_document() RETURNS TRIGGER AS $tr_document$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM employee_document WHERE document_id = OLD.document_id;
         RETURN OLD;
     END IF;
 END;
$tr_document$ LANGUAGE plpgsql;

-- designation trigger function
CREATE FUNCTION fn_designation() RETURNS TRIGGER AS $tr_designation$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM employee WHERE designation_id = OLD.designation_id;
         RETURN OLD;
     END IF;
 END;
$tr_designation$ LANGUAGE plpgsql;

-- employment status trigger function
CREATE FUNCTION fn_employment_status() RETURNS TRIGGER AS $tr_employment_status$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM employee WHERE employment_status_id = OLD.status_id;
         RETURN OLD;
     END IF;
 END;
$tr_employment_status$ LANGUAGE plpgsql;

 -- ## Employee trigger function
CREATE FUNCTION fn_employee() RETURNS TRIGGER AS $tr_employee$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         DELETE FROM EMPLOYEE_CONTACT WHERE employee_id = OLD.employee_id;
         DELETE FROM EMPLOYEE_DOCUMENT WHERE employee_id = OLD.employee_id;
         DELETE FROM EMPLOYEE_EDUCATION WHERE employee_id = OLD.employee_id;
         DELETE FROM EMPLOYEE_EXPERIENCE WHERE employee_id = OLD.employee_id;
         DELETE FROM EMPLOYEE_PROMOTION WHERE employee_id = OLD.employee_id;
         DELETE FROM EMPLOYEE_SUBJECT WHERE employee_id = OLD.employee_id;
         DELETE FROM users WHERE ref_user_type_id = 2 and ref_user_id = OLD.employee_id;
         RETURN OLD;
     END IF;
 END;
$tr_employee$ LANGUAGE plpgsql;
