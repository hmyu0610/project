-- DROP TABLE
DROP TABLE IF EXISTS TB_USER;
DROP TABLE IF EXISTS TB_SEARCH_KEYWORD_HISTORY;

-- CREATE TABLE
CREATE TABLE TB_USER (
  `USER_UUID` VARCHAR(32) NOT NULL,
  `EMAIL` VARCHAR(100) NOT NULL,
  `NAME` VARCHAR(45) NOT NULL,
  `MOD_TIME` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `REG_TIME` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (`USER_UUID`));

CREATE TABLE TB_SEARCH_KEYWORD_HISTORY (
  `IDX` INT NOT NULL,
  `USER_UUID` VARCHAR(32) NOT NULL,
  `KEYWORD` VARCHAR(45) NOT NULL,
  `SEARCH_DATE` VARCHAR(8) NOT NULL,
  `SEARCH_TIME` VARCHAR(6) NOT NULL,
  `REG_TIME` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (`IDX`));
