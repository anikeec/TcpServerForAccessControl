use accesscontroldb;

CREATE TABLE `user_role` (
	`user_role_id` INT NOT NULL AUTO_INCREMENT,
	`description` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`user_role_id`)
);

CREATE TABLE `userrole_user` (
	`uru_id` INT NOT NULL AUTO_INCREMENT,
	`user_role_id` INT,
	`user_id` INT,
	PRIMARY KEY (`uru_id`)
);

CREATE TABLE `system_user` (
	`user_id` INT NOT NULL AUTO_INCREMENT,
	`first_name` VARCHAR(255),
	`second_name` VARCHAR(255),
	`phone_number` VARCHAR(255),
	`email` VARCHAR(255),
	`password` VARCHAR(255),
	`active` BOOLEAN NOT NULL DEFAULT FALSE,
	PRIMARY KEY (`user_id`)
);

CREATE TABLE `card` (
	`card_id` INT NOT NULL AUTO_INCREMENT,
	`card_number` VARCHAR(255) NOT NULL,
	`user_id` INT NOT NULL,
	`active` BOOLEAN NOT NULL DEFAULT FALSE,
	PRIMARY KEY (`card_id`)
);

CREATE TABLE `device` (
	`device_id` INT NOT NULL AUTO_INCREMENT,
	`device_number` INT NOT NULL,
	`last_packet_id` INT  NOT NULL DEFAULT 0,
	`active` BOOLEAN NOT NULL DEFAULT FALSE,
	PRIMARY KEY (`device_id`)
);

CREATE TABLE `access_message` (
	`access_mess_id` INT NOT NULL AUTO_INCREMENT,
	`card_id` INT,
	`device_id` INT,
	`event_id` INT,
	`description` VARCHAR(255),
	`date` DATETIME,
	PRIMARY KEY (`access_mess_id`)
);

CREATE TABLE `info_message` (
	`info_mess_id` INT NOT NULL AUTO_INCREMENT,
	`device_id` INT,
	`event_id` INT,
	`description` VARCHAR(255),
	`date` DATETIME,
	PRIMARY KEY (`info_mess_id`)
);

CREATE TABLE `event_type` (
	`event_id` INT NOT NULL AUTO_INCREMENT,
	`description` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`event_id`)
);

CREATE TABLE `rule` (
	`rule_id` INT NOT NULL AUTO_INCREMENT,
	`card_id` INT,
	`device_id` INT,
	`event_id` INT,
	`rule_type_id` INT,
	`date_begin` DATETIME,
	`date_end` DATETIME,
	`active` BOOLEAN DEFAULT FALSE,
	PRIMARY KEY (`rule_id`)
);

CREATE TABLE `rule_type` (
	`rule_type_id` INT NOT NULL AUTO_INCREMENT,
	`description` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`rule_type_id`)
);

CREATE TABLE `event_message` (
	`event_mess_id` INT NOT NULL AUTO_INCREMENT,
	`device_id` INT,
	`event_id` INT,
	`description` VARCHAR(255),
	`date` DATETIME,
	`base_access_mess_id` INT,
	`rule_id` INT,
	PRIMARY KEY (`event_mess_id`)
);

CREATE TABLE `access_message_wrong` (
	`access_mess_id` INT NOT NULL AUTO_INCREMENT,
	`card_number` VARCHAR(255),
	`device_number` INT,
	`event_id` INT,
	`description` VARCHAR(255),
	`date` DATETIME,
	PRIMARY KEY (`access_mess_id`)
);

ALTER TABLE `userrole_user` ADD CONSTRAINT `userrole_user_fk0` FOREIGN KEY (`user_role_id`) REFERENCES `user_role`(`user_role_id`);

ALTER TABLE `userrole_user` ADD CONSTRAINT `userrole_user_fk1` FOREIGN KEY (`user_id`) REFERENCES `system_user`(`user_id`);

ALTER TABLE `card` ADD CONSTRAINT `card_fk0` FOREIGN KEY (`user_id`) REFERENCES `system_user`(`user_id`);

ALTER TABLE `access_message` ADD CONSTRAINT `access_message_fk0` FOREIGN KEY (`card_id`) REFERENCES `card`(`card_id`);

ALTER TABLE `access_message` ADD CONSTRAINT `access_message_fk1` FOREIGN KEY (`device_id`) REFERENCES `device`(`device_id`);

ALTER TABLE `access_message` ADD CONSTRAINT `access_message_fk2` FOREIGN KEY (`event_id`) REFERENCES `event_type`(`event_id`);

ALTER TABLE `info_message` ADD CONSTRAINT `info_message_fk0` FOREIGN KEY (`device_id`) REFERENCES `device`(`device_id`);

ALTER TABLE `info_message` ADD CONSTRAINT `info_message_fk1` FOREIGN KEY (`event_id`) REFERENCES `event_type`(`event_id`);

ALTER TABLE `rule` ADD CONSTRAINT `rule_fk0` FOREIGN KEY (`card_id`) REFERENCES `card`(`card_id`);

ALTER TABLE `rule` ADD CONSTRAINT `rule_fk1` FOREIGN KEY (`device_id`) REFERENCES `device`(`device_id`);

ALTER TABLE `rule` ADD CONSTRAINT `rule_fk2` FOREIGN KEY (`event_id`) REFERENCES `event_type`(`event_id`);

ALTER TABLE `rule` ADD CONSTRAINT `rule_fk3` FOREIGN KEY (`rule_type_id`) REFERENCES `rule_type`(`rule_type_id`);

ALTER TABLE `event_message` ADD CONSTRAINT `event_message_fk0` FOREIGN KEY (`device_id`) REFERENCES `device`(`device_id`);

ALTER TABLE `event_message` ADD CONSTRAINT `event_message_fk1` FOREIGN KEY (`event_id`) REFERENCES `event_type`(`event_id`);

ALTER TABLE `event_message` ADD CONSTRAINT `event_message_fk2` FOREIGN KEY (`base_access_mess_id`) REFERENCES `access_message`(`access_mess_id`);

ALTER TABLE `event_message` ADD CONSTRAINT `event_message_fk3` FOREIGN KEY (`rule_id`) REFERENCES `rule`(`rule_id`);
