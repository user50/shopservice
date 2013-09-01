# --- !Ups

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Table `shopservice`.`ClientSettings`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ClientSettings` ;

CREATE  TABLE IF NOT EXISTS `ClientSettings` (
  `id` VARCHAR(100) NOT NULL ,
  `siteName` VARCHAR(100) NULL ,
  `siteUrl` VARCHAR(100) NULL ,
  `databaseUrl` VARCHAR(200) NULL ,
  `pathToProductImage` VARCHAR(200) NULL ,
  `pathToProductPage` VARCHAR(200) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shopservice`.`ProductIDs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ProductIDs` ;

CREATE  TABLE IF NOT EXISTS `ProductIDs` (
  `productIds` VARCHAR(100) NULL ,
  `clientSettingsId` VARCHAR(100) NOT NULL ,
  `id` INT NOT NULL AUTO_INCREMENT ,
  PRIMARY KEY (`id`, `clientSettingsId`) ,
  INDEX `fk_ProductIDs_ClinetSettings_idx` (`clientSettingsId` ASC) ,
  CONSTRAINT `fk_ProductIDs_ClinetSettings`
    FOREIGN KEY (`clientSettingsId` )
    REFERENCES `shopservice`.`ClientSettings` (`id` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;




SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

# --- !Downs


SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP TABLE IF EXISTS `ClientSettings` ;

DROP TABLE IF EXISTS `ProductIDs` ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;