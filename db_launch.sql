SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


DROP SCHEMA IF EXISTS `exhibitions_db`;
CREATE SCHEMA `exhibitions_db`;
USE `exhibitions_db` ;


CREATE TABLE IF NOT EXISTS `exhibition`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `theme` NVARCHAR(255) NOT NULL,
    `description` NVARCHAR(255) NOT NULL,
    `start_date` DATE NOT NULL,
    `end_date` DATE NOT NULL,
    `start_time` TIME NOT NULL,
    `end_time` TIME NOT NULL,
    `price` DECIMAL(19,2) NOT NULL,
    PRIMARY KEY (`id`)
    )
    ENGINE = InnoDB;



CREATE TABLE IF NOT EXISTS `hall` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` NVARCHAR(255) NOT NULL,
    `description` NVARCHAR(1024) NOT NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `exhibitions_halls` (
   `exhibition_id` BIGINT NOT NULL,
   `hall_id` BIGINT NOT NULL,
    FOREIGN KEY (`hall_id`)
    REFERENCES `hall` (`id`)
    ON DELETE CASCADE,
    FOREIGN KEY (`exhibition_id`)
    REFERENCES `exhibition` (`id`)
    ON DELETE CASCADE)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `role` (
    `id` INT NOT NULL,
    `name` NVARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `email` NVARCHAR(255) NOT NULL,
    `login` NVARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `role_id` INT NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`role_id`)
    REFERENCES `role` (`id`))
    ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `orders` (
    `exhibition_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE,
    FOREIGN KEY (`exhibition_id`)
    REFERENCES `exhibition` (`id`)
    ON DELETE CASCADE)
    ENGINE = InnoDB;

INSERT INTO role VALUES(0, 'ADMIN');
INSERT INTO role VALUES(1, 'USER');
INSERT INTO role VALUES(2, 'GUEST');

INSERT INTO user VALUES(1, 'admin@gmail.com', 'admin', '202cb962ac59075b964b07152d234b70', 0);
INSERT INTO user VALUES(2, 'ivanko@gmail.com', 'Ivan', '202cb962ac59075b964b07152d234b70', 1);
INSERT INTO user VALUES(3, 'vovast@gmail.com', 'VovaST', '202cb962ac59075b964b07152d234b70', 1);
INSERT INTO user VALUES(4, 'vladsurkov@gmail.com', 'VladSurkov', '202cb962ac59075b964b07152d234b70', 1);
INSERT INTO user VALUES(5, 'blessin@gmail.com', 'blessin', '202cb962ac59075b964b07152d234b70', 1);

INSERT INTO hall VALUES(1, 'OldHall', 'This is the oldest hall out of all the halls. It was opened in 2001. It has hosted about 800 exhibitions throughout its existence.');
INSERT INTO hall VALUES(2, 'RedHall', 'This is the biggest hall of ours. It can host more than 5 exhibitions at the same time. It is know worldwide known for its size.');
INSERT INTO hall VALUES(3, 'LivingHall', 'This is the newest hall. It was built in 2012. It is most known for its exhibitions connected to animal world.');


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
