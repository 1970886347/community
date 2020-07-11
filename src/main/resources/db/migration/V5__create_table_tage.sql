CREATE TABLE `community`.`tag` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `question_id` INT NULL,
  `content` VARCHAR(15) NULL,
  PRIMARY KEY (`id`));