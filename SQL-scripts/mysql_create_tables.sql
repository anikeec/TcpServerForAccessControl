use accesscontroldb;

CREATE TABLE `user` (
	`userId` INT NOT NULL AUTO_INCREMENT,
	`firstName` VARCHAR(255) NOT NULL,
	`secondName` VARCHAR(255) NOT NULL,
	`phoneNumber` VARCHAR(255) NOT NULL,
	`email` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`userId`)
);

CREATE TABLE `card` (
	`cardID` INT NOT NULL AUTO_INCREMENT,
	`cardNumber` VARCHAR(255) NOT NULL,
	`userID` INT NOT NULL,
	PRIMARY KEY (`cardID`)
);

CREATE TABLE `device` (
	`deviceID` INT NOT NULL AUTO_INCREMENT,
	`lastPacketID` INT NOT NULL,
	PRIMARY KEY (`deviceID`)
);

CREATE TABLE `access_message` (
	`accessMessID` INT NOT NULL AUTO_INCREMENT,
	`cardID` INT NOT NULL,
	`deviceID` INT NOT NULL,
	`eventID` INT NOT NULL,
	`description` VARCHAR(255) NOT NULL,
	`Date` DATETIME NOT NULL,
	PRIMARY KEY (`accessMessID`)
);

CREATE TABLE `info_message` (
	`infoMessID` INT NOT NULL AUTO_INCREMENT,
	`deviceID` INT NOT NULL,
	`eventID` INT NOT NULL,
	`description` VARCHAR(255) NOT NULL,
	`Date` DATETIME NOT NULL,
	PRIMARY KEY (`infoMessID`)
);

CREATE TABLE `event_type` (
	`eventID` INT NOT NULL AUTO_INCREMENT,
	`description` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`eventID`)
);

CREATE TABLE `rule` (
	`ruleID` INT NOT NULL AUTO_INCREMENT,
	`cardID` INT NOT NULL,
	`devideID` INT NOT NULL,
	`ruleTypeID` INT NOT NULL,
	`DateBegin` DATETIME NOT NULL,
	`DateEnd` DATETIME NOT NULL,
	PRIMARY KEY (`ruleID`)
);

CREATE TABLE `rules_type` (
	`ruleTypeId` INT NOT NULL AUTO_INCREMENT,
	`description` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`ruleTypeId`)
);

CREATE TABLE `event_message` (
	`eventMessID` INT NOT NULL AUTO_INCREMENT,
	`deviceID` INT NOT NULL,
	`eventID` INT NOT NULL,
	`description` VARCHAR(255) NOT NULL,
	`Date` DATETIME NOT NULL,
	`baseAccessMessID` INT NOT NULL,
	`ruleID` INT NOT NULL,
	PRIMARY KEY (`eventMessID`)
);

ALTER TABLE `card` ADD CONSTRAINT `card_fk0` FOREIGN KEY (`userID`) REFERENCES `user`(`userId`);

ALTER TABLE `access_message` ADD CONSTRAINT `access_message_fk0` FOREIGN KEY (`cardID`) REFERENCES `card`(`cardID`);

ALTER TABLE `access_message` ADD CONSTRAINT `access_message_fk1` FOREIGN KEY (`deviceID`) REFERENCES `device`(`deviceID`);

ALTER TABLE `access_message` ADD CONSTRAINT `access_message_fk2` FOREIGN KEY (`eventID`) REFERENCES `event_type`(`eventID`);

ALTER TABLE `info_message` ADD CONSTRAINT `info_message_fk0` FOREIGN KEY (`deviceID`) REFERENCES `device`(`deviceID`);

ALTER TABLE `info_message` ADD CONSTRAINT `info_message_fk1` FOREIGN KEY (`eventID`) REFERENCES `event_type`(`eventID`);

ALTER TABLE `rule` ADD CONSTRAINT `rule_fk0` FOREIGN KEY (`cardID`) REFERENCES `card`(`cardID`);

ALTER TABLE `rule` ADD CONSTRAINT `rule_fk1` FOREIGN KEY (`devideID`) REFERENCES `device`(`deviceID`);

ALTER TABLE `rule` ADD CONSTRAINT `rule_fk2` FOREIGN KEY (`ruleTypeID`) REFERENCES `rules_type`(`ruleTypeId`);

ALTER TABLE `event_message` ADD CONSTRAINT `event_message_fk0` FOREIGN KEY (`deviceID`) REFERENCES `device`(`deviceID`);

ALTER TABLE `event_message` ADD CONSTRAINT `event_message_fk1` FOREIGN KEY (`eventID`) REFERENCES `event_type`(`eventID`);

ALTER TABLE `event_message` ADD CONSTRAINT `event_message_fk2` FOREIGN KEY (`baseAccessMessID`) REFERENCES `access_message`(`accessMessID`);

ALTER TABLE `event_message` ADD CONSTRAINT `event_message_fk3` FOREIGN KEY (`ruleID`) REFERENCES `rule`(`ruleID`);
