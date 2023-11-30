--make a trigger so the same user doesn't save twoce in the auth token database table
DELIMITER //
    CREATE PROCEDURE CreateTriggerForAuthTokensTableIfNoneExists()
    BEGIN
    DECLARE trigger_count INT;
        SELECT COUNT(*)
        INTO trigger_count
        FROM information_schema.triggers
        WHERE TRIGGER_SCHEMA = 'pawsicare' AND TRIGGER_NAME = 'no_token_repeat';

    IF trigger_count = 0 THEN
        SET @sql = '
            CREATE TRIGGER no_token_repeat
            BEFORE INSERT ON auth_tokens
            FOR EACH ROW
            BEGIN
                DECLARE users_id INT;
                SELECT user_id INTO users_id FROM auth_tokens WHERE id = NEW.users_id;
                IF users_id <> 0 THEN
                    SIGNAL SQLSTATE "45000";
                END IF;
            END';
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE stmt;
        END IF;
        END
    //
DELIMITER ;
