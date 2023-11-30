DROP PROCEDURE IF EXISTS CreateTriggerIfNotExists;

DELIMITER //

CREATE PROCEDURE CreateTriggerIfNotExists()
BEGIN
DECLARE triggerCount INT;
SELECT COUNT(*)
INTO triggerCount
FROM information_schema.triggers
WHERE TRIGGER_SCHEMA = 'pawsicare' AND TRIGGER_NAME = 'before_create_pet';

IF triggerCount = 0 THEN
        SET @sql = '
            CREATE TRIGGER before_create_pet
            BEFORE INSERT ON pets
            FOR EACH ROW
            BEGIN
                DECLARE user_role INT;
                SELECT role INTO user_role FROM pawsicare_users WHERE id = NEW.owner;
                IF user_role <> 0 THEN
                    SIGNAL SQLSTATE "45000";
                END IF;
            END';

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
END IF;
END
//

DELIMITER ;
