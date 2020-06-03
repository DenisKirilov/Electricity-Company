SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema electro
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `electro` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `electro` ;

-- -----------------------------------------------------
-- Table `electro`.`company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electro`.`company` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `foundation_date` DATE NULL DEFAULT NULL,
  `initial_capital` DECIMAL(19,2) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 174
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `electro`.`customers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electro`.`customers` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL DEFAULT NULL,
  `last_name` VARCHAR(45) NULL DEFAULT NULL,
  `customer_type` VARCHAR(45) NULL DEFAULT NULL,
  `company_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_customer_company1_idx` (`company_id` ASC) VISIBLE,
  CONSTRAINT `fk_customer_company1`
    FOREIGN KEY (`company_id`)
    REFERENCES `electro`.`company` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 173
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `electro`.`employees`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electro`.`employees` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `birth_date` DATE NULL DEFAULT NULL,
  `hire_date` DATE NULL DEFAULT NULL,
  `salary` DECIMAL(19,2) NULL DEFAULT NULL,
  `company_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_employee_company_idx` (`company_id` ASC) VISIBLE,
  CONSTRAINT `fk_employee_company`
    FOREIGN KEY (`company_id`)
    REFERENCES `electro`.`company` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 90
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `electro`.`finances`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electro`.`finances` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `income` DECIMAL(19,2) NULL DEFAULT NULL,
  `expenses` DECIMAL(19,2) NULL DEFAULT NULL,
  `balance` DECIMAL(19,2) NOT NULL,
  `profit` DECIMAL(19,2) NULL DEFAULT NULL,
  `company_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_properties_company1_idx` (`company_id` ASC) VISIBLE,
  CONSTRAINT `fk_properties_company1`
    FOREIGN KEY (`company_id`)
    REFERENCES `electro`.`company` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 147
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `electro`.`payments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electro`.`payments` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `date` DATE NULL DEFAULT NULL,
  `payment` DOUBLE NULL DEFAULT NULL,
  `comment` VARCHAR(255) NULL DEFAULT NULL,
  `quantity` INT NULL DEFAULT NULL,
  `customer_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK45dp0030s8e3myd8n6ky4e79g` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `FK45dp0030s8e3myd8n6ky4e79g`
    FOREIGN KEY (`customer_id`)
    REFERENCES `electro`.`customers` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 212
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
