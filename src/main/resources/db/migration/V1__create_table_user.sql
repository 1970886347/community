CREATE TABLE `community`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `account_id` BIGINT(32) NULL,
  `name` VARCHAR(50) NULL,
  `token` CHAR(50) NULL,
  `gmt_create` BIGINT(32) NULL,
  `gmt_modify` BIGINT(32) NULL,
  PRIMARY KEY (`id`));

