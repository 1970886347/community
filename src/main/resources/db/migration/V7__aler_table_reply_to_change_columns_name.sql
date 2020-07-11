ALTER TABLE `community`.`reply`
CHANGE COLUMN `userId` `user_id` INT NULL DEFAULT NULL ,
CHANGE COLUMN `questionId` `question_id` INT NULL DEFAULT NULL ;
