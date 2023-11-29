
DELIMITER //
CREATE TRIGGER before_create_pet
    BEFORE INSERT ON pets
    FOR EACH ROW
BEGIN
    DECLARE user_role INT;

    SELECT role INTO user_role FROM pawsicare_users WHERE id = NEW.owner;

    IF user_role <> 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Foreign key constraint failed: owner user must have role 0.';
END IF;
END;
//
DELIMITER ;