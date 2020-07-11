ALTER TABLE `community`.`reply`
ADD COLUMN `gmt_create` BIGINT(64) NULL AFTER `content`;