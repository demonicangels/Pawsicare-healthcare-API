-- CREATE TABLE pets (
--                       id INT AUTO_INCREMENT PRIMARY KEY,
--                       birthday INT,
--                       information VARCHAR(100),
--                       name VARCHAR(50),
--                       owner_Id INT,
--                       FOREIGN KEY (owner_Id) REFERENCES pawsicare_users(id)
-- );
--
-- DELIMITER //
-- CREATE TRIGGER before_create_pet
--     BEFORE INSERT ON pets
--     FOR EACH ROW
-- BEGIN
--     DECLARE user_role INT;
--
--     SELECT role INTO user_role FROM pawsicare_users WHERE id = NEW.owner_Id;
--
--     IF user_role <> 0 THEN
--         SIGNAL SQLSTATE '45000'
--         SET MESSAGE_TEXT = 'Foreign key constraint failed: owner user must have role 0.';
-- END IF;
-- END;
-- //
-- DELIMITER ;

CREATE TABLE test (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      birthday INT,
                      information VARCHAR(100),
                      name VARCHAR(50)
);