ALTER TABLE `community`.`sub_reply`
ADD COLUMN `status` INT NULL DEFAULT 0 AFTER `gmt_create`;