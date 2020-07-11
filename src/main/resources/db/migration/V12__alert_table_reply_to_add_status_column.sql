ALTER TABLE `community`.`reply`
ADD COLUMN `status` INT NULL DEFAULT 0 AFTER `be_replied`;