CREATE TABLE `community`.`sub_reply` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `reply_id` INT NULL,
  `user_id` INT NULL,
  `be_replied` INT NULL,
  `question_id` INT NULL,
  `content` TEXT(255) NULL,
  `gmt_create` BIGINT(32) NULL,
  PRIMARY KEY (`id`));
