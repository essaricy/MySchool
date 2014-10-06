-- Procedure to create default user access records.
--CREATE OR REPLACE FUNCTION set_default_user_access() RETURNS void AS
DO
$BODY$
  DECLARE
    curUserType RECORD;
    curRecord RECORD;
    numberOfRecords integer;
    can_view char(1);
    can_create char(1);
    can_update char(1);
    can_delete char(1);
  BEGIN
    numberOfRecords := 0;

    FOR curUserType IN 
      SELECT user_type_id, description as user_type_desc
      from ref_user_type
    LOOP
      DELETE FROM default_user_access WHERE ref_user_type_id = curUserType.user_type_id;

      FOR curRecord IN 
        SELECT ref_module.module_id, ref_module.module_name, function.function_id
        from FUNCTION
        inner join ref_module on ref_module.module_id= function.ref_module_id
      LOOP
        -- ADMIN default access
        IF (curUserType.user_type_desc = 'Admin') THEN
          IF (curRecord.module_name = 'Admin')
            OR (curRecord.module_name = 'Master')
            OR (curRecord.module_name = 'Notifications')
            OR (curRecord.module_name = 'Notice Board')
            OR (curRecord.module_name = 'Downloads')
            OR (curRecord.module_name = 'Privileges')
            OR (curRecord.module_name = 'Statistics')
            OR (curRecord.module_name = 'Find Us')
            OR (curRecord.module_name = 'Web Admin')
          THEN
            can_view := 'Y';
            can_create := 'Y';
            can_update := 'Y';
            can_delete := 'Y';
          END IF;

          IF (curRecord.module_name = 'Student')
            OR (curRecord.module_name = 'Employee')
          THEN
            can_view := 'N';
            can_create := 'N';
            can_update := 'N';
            can_delete := 'N';
          END IF;
        END IF;

        -- EMPLOYEE default access
        IF (curUserType.user_type_desc = 'Employee') THEN
          IF (curRecord.module_name = 'Admin')
            OR (curRecord.module_name = 'Master')
            OR (curRecord.module_name = 'Employee')
            OR (curRecord.module_name = 'Notifications')
            OR (curRecord.module_name = 'Notice Board')
            OR (curRecord.module_name = 'Downloads')
            OR (curRecord.module_name = 'Statistics')
            OR (curRecord.module_name = 'Find Us')
            OR (curRecord.module_name = 'Web Admin')
          THEN
            can_view := 'Y';
            can_create := 'N';
            can_update := 'N';
            can_delete := 'N';
          END IF;
          IF (curRecord.module_name = 'Student')
            OR (curRecord.module_name = 'Privileges')
          THEN
            can_view := 'N';
            can_create := 'N';
            can_update := 'N';
            can_delete := 'N';
          END IF;
        END IF;

        -- STUDENT default access
        IF (curUserType.user_type_desc = 'Student') THEN
          IF (curRecord.module_name = 'Student')
            OR (curRecord.module_name = 'Notice Board')
            OR (curRecord.module_name = 'Downloads')
            OR (curRecord.module_name = 'Find Us')
          THEN
            can_view := 'Y';
            can_create := 'N';
            can_update := 'N';
            can_delete := 'N';
          END IF;
          IF (curRecord.module_name = 'Admin')
            OR (curRecord.module_name = 'Master')
            OR (curRecord.module_name = 'Employee')
            OR (curRecord.module_name = 'Notifications')
            OR (curRecord.module_name = 'Privileges')
            OR (curRecord.module_name = 'Statistics')
            OR (curRecord.module_name = 'Web Admin')
          THEN
            can_view := 'N';
            can_create := 'N';
            can_update := 'N';
            can_delete := 'N';
          END IF;
        END IF;

        --RAISE INFO '%, %, %, %, %, %', 1, curRecord.function_id, can_view, can_create, can_update, can_delete;
        INSERT INTO DEFAULT_USER_ACCESS (REF_USER_TYPE_ID, FUNCTION_ID, CAN_VIEW, CAN_CREATE, CAN_UPDATE, CAN_DELETE)
          VALUES (curUserType.user_type_id, curRecord.function_id, can_view, can_create, can_update, can_delete);
              
        numberOfRecords := numberOfRecords + 1;
      END LOOP;
      RAISE INFO 'Created % records for the user %', numberOfRecords, curUserType.user_type_desc;
      numberOfRecords := 0;
    END LOOP;
  END
$BODY$ LANGUAGE plpgsql;