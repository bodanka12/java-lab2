-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema payments
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `payments`;

-- -----------------------------------------------------
-- Schema payments
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `payments` DEFAULT CHARACTER SET utf8;
USE `payments`;

-- -----------------------------------------------------
-- Table `payments`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `payments`.`role`;

CREATE TABLE IF NOT EXISTS `payments`.`role`
(
    `role_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`role_name`)
)
    ENGINE = InnoDB;

INSERT INTO `payments`.`role` (role_name)
VALUES ('USER'),
       ('ADMIN');

-- -----------------------------------------------------
-- Table `payments`.`user_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `payments`.`user_status`;

CREATE TABLE IF NOT EXISTS `payments`.`user_status`
(
    `status_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`status_name`)
)
    ENGINE = InnoDB;

INSERT INTO `payments`.`user_status` (status_name)
VALUES ('ACTIVE'),
       ('BLOCKED');

-- -----------------------------------------------------
-- Table `payments`.`account_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `payments`.`account_status`;

CREATE TABLE IF NOT EXISTS `payments`.`account_status`
(
    `status_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`status_name`)
)
    ENGINE = InnoDB;

INSERT INTO `payments`.`account_status` (status_name)
VALUES ('ACTIVE'),
       ('BLOCKED');

-- -----------------------------------------------------
-- Table `payments`.`payment_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `payments`.`payment_status`;

CREATE TABLE IF NOT EXISTS `payments`.`payment_status`
(
    `status_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`status_name`)
)
    ENGINE = InnoDB;

INSERT INTO `payments`.`payment_status` (status_name)
VALUES ('PREPARED'),
       ('SENT'),
       ('DECLINED');

-- -----------------------------------------------------
-- Table `payments`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `payments`.`user`;

CREATE TABLE IF NOT EXISTS `payments`.`user`
(
    `id`               INT         NOT NULL AUTO_INCREMENT,
    `phone_number`     VARCHAR(45) NOT NULL,
    `first_name`       VARCHAR(45) NOT NULL,
    `last_name`        VARCHAR(45) NOT NULL,
    `email`            VARCHAR(45) NULL,
    `password`         VARCHAR(45) NOT NULL,
    `age`              INT         NULL,
    `role_name`        VARCHAR(45) NOT NULL,
    `user_status_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_user_role_idx` (`role_name` ASC) VISIBLE,
    INDEX `fk_user_user_status1_idx` (`user_status_name` ASC) VISIBLE,
    CONSTRAINT `fk_user_role`
        FOREIGN KEY (`role_name`)
            REFERENCES `payments`.`role` (`role_name`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_user_user_status1`
        FOREIGN KEY (`user_status_name`)
            REFERENCES `payments`.`user_status` (`status_name`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `payments`.`credit_card`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `payments`.`credit_card`;

CREATE TABLE IF NOT EXISTS `payments`.`credit_card`
(
    `id`                  INT           NOT NULL AUTO_INCREMENT,
    `card_number`         VARCHAR(19)   NOT NULL,
    `balance`             DECIMAL(6, 2) NOT NULL,
    `account_status_name` VARCHAR(45)   NOT NULL,
    `user_id`             INT           NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_credit_card_account_status1_idx` (`account_status_name` ASC) VISIBLE,
    INDEX `fk_credit_card_user1_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_credit_card_account_status1`
        FOREIGN KEY (`account_status_name`)
            REFERENCES `payments`.`account_status` (`status_name`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_credit_card_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `payments`.`user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `payments`.`payments_history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `payments`.`payments_history`;

CREATE TABLE IF NOT EXISTS `payments`.`payments_history`
(
    `id`                    INT           NOT NULL AUTO_INCREMENT,
    `sum`                   DECIMAL(6, 2) NOT NULL,
    `from_credit_card_id`   INT           NOT NULL,
    `to_credit_card_number` VARCHAR(19)   NOT NULL,
    `payment_status_name`   VARCHAR(45)   NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_payments_history_credit_card1_idx` (`from_credit_card_id` ASC) VISIBLE,
    INDEX `fk_payments_history_payment_status1_idx` (`payment_status_name` ASC) VISIBLE,
    CONSTRAINT `fk_payments_history_credit_card1`
        FOREIGN KEY (`from_credit_card_id`)
            REFERENCES `payments`.`credit_card` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_payments_history_payment_status1`
        FOREIGN KEY (`payment_status_name`)
            REFERENCES `payments`.`payment_status` (`status_name`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
