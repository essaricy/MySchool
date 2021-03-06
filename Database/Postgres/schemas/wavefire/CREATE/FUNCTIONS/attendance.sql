-- ## ATTENDANCE_CODE trigger functions
CREATE OR REPLACE FUNCTION wavefire.fn_attendance_code() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         -- delete all references in ATTENDANCE_MONTH
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_1 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_2 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_3 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_4 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_5 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_6 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_7 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_8 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_9 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_10 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_11 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_12 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_13 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_14 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_15 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_16 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_17 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_18 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_19 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_20 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_21 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_22 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_23 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_24 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_25 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_26 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_27 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_28 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_29 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_30 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE DAY_31 = OLD.code;

         -- delete all references from attendance
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_1 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_2 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_3 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_4 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_5 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_6 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_7 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_8 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_9 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_10 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_11 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_12 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_13 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_14 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_15 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_16 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_17 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_18 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_19 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_20 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_21 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_22 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_23 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_24 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_25 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_26 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_27 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_28 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_29 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_30 = OLD.code;
         DELETE FROM wavefire.ATTENDANCE WHERE DAY_31 = OLD.code;

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


-- ## ATTENDANCE_PROFILE trigger functions
CREATE OR REPLACE FUNCTION wavefire.fn_attendance_profile() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         -- delete all references in ATTENDANCE_MONTH
         DELETE FROM wavefire.ATTENDANCE_MONTH WHERE ATTENDANCE_PROFILE_ID = OLD.profile_id;
         DELETE FROM wavefire.ATTENDANCE_PROFILE_SCHOOL WHERE ATTENDANCE_PROFILE_ID = OLD.profile_id;
         DELETE FROM wavefire.ATTENDANCE_PROFILE_CLASS WHERE ATTENDANCE_PROFILE_ID = OLD.profile_id;
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

-- ## ATTENDANCE_MONTH trigger functions
CREATE OR REPLACE FUNCTION wavefire.fn_attendance_month() RETURNS TRIGGER AS
$BODY$
 DECLARE
 BEGIN
     IF TG_OP = 'DELETE' THEN
         -- delete all references in ATTENDANCE_MONTH
         DELETE FROM wavefire.ATTENDANCE WHERE ATTENDANCE_MONTH_ID = OLD.attendance_month_id;
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
