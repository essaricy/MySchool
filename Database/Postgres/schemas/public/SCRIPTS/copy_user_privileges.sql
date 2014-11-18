CREATE OR REPLACE FUNCTION copy_user_privileges(from_user_id INT, to_user_ids INT[]) RETURNS VOID AS
$BODY$
DECLARE
  -- declarations
  from_user_type_id INT;
  to_user_type_id INT;
  to_user_id INT;
BEGIN
  RAISE NOTICE 'Copy from user id % to user ids %', from_user_id, to_user_ids;

  SELECT ref_user_type_id INTO from_user_type_id FROM users WHERE user_id = from_user_id;
  RAISE NOTICE 'User Type ID of "From User" is % ', from_user_type_id;
  -- get user_access for from_user_id

  FOREACH to_user_id IN ARRAY to_user_ids
  LOOP
    IF (from_user_id = to_user_id) THEN
      RAISE EXCEPTION 'Cannot copy from privileges to the same user (%)', to_user_id;
    END IF;

    SELECT ref_user_type_id INTO to_user_type_id FROM users WHERE user_id = to_user_id;
    RAISE NOTICE 'User Type ID of "To User" is % ', to_user_type_id;
    -- Check if the user type ids are same of fromUserId
    -- if from user id != to user id
    IF (to_user_type_id IS NULL) THEN
      RAISE EXCEPTION 'User ID % does not exist.', to_user_id;
    END IF;
    IF (from_user_type_id != to_user_type_id) THEN
      RAISE EXCEPTION 'User Privileges can only be copied if they are belong to the same user type.';
    END IF;
  END LOOP;

  -- Delete existing user priviges for toUserIds
  RAISE NOTICE 'Deleting existing privileges for the users % ', to_user_ids;
  DELETE FROM user_access WHERE user_id = ANY (to_user_ids);

  -- Insert records
  RAISE NOTICE 'Copying privileges to the users % ', to_user_ids;
  FOREACH to_user_id IN ARRAY to_user_ids
  LOOP
    INSERT INTO user_access (SELECT to_user_id, function_id, can_view, can_create, can_update, can_delete FROM user_access WHERE user_id = from_user_id);
    RAISE NOTICE 'Copied privileges to the user % ', to_user_id;
  END LOOP;

END;
$BODY$
LANGUAGE plpgsql;
