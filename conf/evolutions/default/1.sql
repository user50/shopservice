SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `shopservice` ;
CREATE SCHEMA IF NOT EXISTS `shopservice` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `shopservice` ;

-- -----------------------------------------------------
-- Table `shopservice`.`ClinetSettings`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shopservice`.`ClinetSettings` ;

CREATE  TABLE IF NOT EXISTS `shopservice`.`ClinetSettings` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `siteName` VARCHAR(100) NULL ,
  `siteUrl` VARCHAR(100) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shopservice`.`ProductIDs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shopservice`.`ProductIDs` ;

CREATE  TABLE IF NOT EXISTS `shopservice`.`ProductIDs` (
  `productIds` VARCHAR(100) NULL ,
  `clientSettingsId` INT NOT NULL ,
  `id` INT NOT NULL ,
  PRIMARY KEY (`clientSettingsId`, `id`) ,
  INDEX `fk_ProductIDs_ClinetSettings_idx` (`clientSettingsId` ASC) ,
  CONSTRAINT `fk_ProductIDs_ClinetSettings`
    FOREIGN KEY (`clientSettingsId` )
    REFERENCES `shopservice`.`ClinetSettings` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
