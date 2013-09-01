# --- !Ups

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `shopservice` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `shopservice` ;

-- -----------------------------------------------------
-- Table `shopservice`.`ClientSettings`
-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `shopservice`.`ClientSettings` (
  `id` VARCHAR(100) NOT NULL ,
  `siteName` VARCHAR(100) NULL ,
  `siteUrl` VARCHAR(100) NULL ,
  `databaseUrl` VARCHAR(200) NULL ,
  PRIMARY KEY (`id`) )
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shopservice`.`ProductIDs`
-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `shopservice`.`ProductIDs` (
  `productIds` VARCHAR(100) NULL ,
  `clientSettingsId` VARCHAR(100) NOT NULL ,
  `id` INT NOT NULL ,
  PRIMARY KEY (`clientSettingsId`, `id`) ,
  INDEX `fk_ProductIDs_ClinetSettings_idx` (`clientSettingsId` ASC) ,
  CONSTRAINT `fk_ProductIDs_ClinetSettings`
    FOREIGN KEY (`clientSettingsId` )
    REFERENCES `shopservice`.`ClientSettings` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

# --- !Downs


SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP TABLE IF EXISTS `shopservice`.`ClientSettings` ;

DROP TABLE IF EXISTS `shopservice`.`ProductIDs` ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;