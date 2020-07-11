CREATE TABLE `community`.`question` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL,
  `description` TEXT(255) NULL,
  `creator` BIGINT(32) NULL,
  `comment_count` INT NULL DEFAULT 0,
  `view_count` INT NULL DEFAULT 0,
  `like_count` INT NULL DEFAULT 0,
  `tag` VARCHAR(255) NULL,
  `gmt_create` BIGINT(32) NULL,
  `gmt_modify` BIGINT(32) NULL,
  PRIMARY KEY (`id`));
