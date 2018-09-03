use accesscontroldb;

CREATE TABLE `users` (
	`userId` INT NOT NULL AUTO_INCREMENT,
	`firstName` VARCHAR(255) NOT NULL,
	`secondName` VARCHAR(255) NOT NULL,
	`phoneNumber` VARCHAR(255) NOT NULL,
	`email` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`userId`)
);

CREATE TABLE `cards` (
	`cardID` INT NOT NULL AUTO_INCREMENT,
	`cardNumber` VARCHAR(255) NOT NULL,
	`userID` INT NOT NULL,
	PRIMARY KEY (`cardID`)
);

CREATE TABLE `devices` (
	`deviceID` INT NOT NULL AUTO_INCREMENT,
	`lastPacketID` INT NOT NULL,
	PRIMARY KEY (`deviceID`)
);

CREATE TABLE `access_messages` (
	`accessMessID` INT NOT NULL AUTO_INCREMENT,
	`cardID` INT NOT NULL,
	`deviceID` INT NOT NULL,
	`eventID` INT NOT NULL,
	`description` VARCHAR(255) NOT NULL,
	`Date` DATETIME NOT NULL,
	PRIMARY KEY (`accessMessID`)
);

CREATE TABLE `info_messages` (
	`infoMessID` INT NOT NULL AUTO_INCREMENT,
	`deviceID` INT NOT NULL,
	`eventID` INT NOT NULL,
	`description` VARCHAR(255) NOT NULL,
	`Date` DATETIME NOT NULL,
	PRIMARY KEY (`infoMessID`)
);

CREATE TABLE `event_types` (
	`eventID` INT NOT NULL AUTO_INCREMENT,
	`description` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`eventID`)
);

CREATE TABLE `rules` (
	`ruleID` INT NOT NULL AUTO_INCREMENT,
	`cardID` INT NOT NULL,
	`devideID` INT NOT NULL,
	`ruleTypeID` INT NOT NULL,
	`DateBegin` DATETIME NOT NULL,
	`DateEnd` DATETIME NOT NULL,
	PRIMARY KEY (`ruleID`)
);

CREATE TABLE `rules_types` (
	`ruleTypeId` INT NOT NULL AUTO_INCREMENT,
	`description` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`ruleTypeId`)
);

CREATE TABLE `event_messages` (
	`eventMessID` INT NOT NULL AUTO_INCREMENT,
	`deviceID` INT NOT NULL,
	`eventID` INT NOT NULL,
	`description` VARCHAR(255) NOT NULL,
	`Date` DATETIME NOT NULL,
	`baseAccessMessID` INT NOT NULL,
	`ruleID` INT NOT NULL,
	PRIMARY KEY (`eventMessID`)
);

ALTER TABLE `cards` ADD CONSTRAINT `cards_fk0` FOREIGN KEY (`userID`) REFERENCES `users`(`userId`);

ALTER TABLE `access_messages` ADD CONSTRAINT `access_messages_fk0` FOREIGN KEY (`cardID`) REFERENCES `cards`(`cardID`);

ALTER TABLE `access_messages` ADD CONSTRAINT `access_messages_fk1` FOREIGN KEY (`deviceID`) REFERENCES `devices`(`deviceID`);

ALTER TABLE `access_messages` ADD CONSTRAINT `access_messages_fk2` FOREIGN KEY (`eventID`) REFERENCES `event_types`(`eventID`);

ALTER TABLE `info_messages` ADD CONSTRAINT `info_messages_fk0` FOREIGN KEY (`deviceID`) REFERENCES `devices`(`deviceID`);

ALTER TABLE `info_messages` ADD CONSTRAINT `info_messages_fk1` FOREIGN KEY (`eventID`) REFERENCES `event_types`(`eventID`);

ALTER TABLE `rules` ADD CONSTRAINT `rules_fk0` FOREIGN KEY (`cardID`) REFERENCES `cards`(`cardID`);

ALTER TABLE `rules` ADD CONSTRAINT `rules_fk1` FOREIGN KEY (`devideID`) REFERENCES `devices`(`deviceID`);

ALTER TABLE `rules` ADD CONSTRAINT `rules_fk2` FOREIGN KEY (`ruleTypeID`) REFERENCES `rules_types`(`ruleTypeId`);

ALTER TABLE `event_messages` ADD CONSTRAINT `event_messages_fk0` FOREIGN KEY (`deviceID`) REFERENCES `devices`(`deviceID`);

ALTER TABLE `event_messages` ADD CONSTRAINT `event_messages_fk1` FOREIGN KEY (`eventID`) REFERENCES `event_types`(`eventID`);

ALTER TABLE `event_messages` ADD CONSTRAINT `event_messages_fk2` FOREIGN KEY (`baseAccessMessID`) REFERENCES `access_messages`(`accessMessID`);

ALTER TABLE `event_messages` ADD CONSTRAINT `event_messages_fk3` FOREIGN KEY (`ruleID`) REFERENCES `rules`(`ruleID`);
