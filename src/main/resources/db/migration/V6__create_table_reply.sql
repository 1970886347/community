CREATE TABLE `community`.`reply` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NULL,
  `questionId` INT NULL,
  `content` TEXT(255) NULL,
  PRIMARY KEY (`id`));