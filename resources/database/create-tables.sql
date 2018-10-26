
/*Creating Database*******************************/
CREATE DATABASE IF NOT EXISTS stockwatchdb;
USE market-watch-db;


/* Creating Data Tables ******************************** START ********************************/


CREATE TABLE IF NOT EXISTS `data_quandl_daily`(
	`recorddate` DATETIME NOT NULL,
	`symbol` varchar(256) NOT NULL,
	`open` decimal(12,4) unsigned NOT NULL,
	`high` decimal(12,4) unsigned NOT NULL,
	`low` decimal(12,4) unsigned NOT NULL,
	`close` decimal(12,4) unsigned NOT NULL,
	`volume` bigint(40) unsigned NOT NULL,
	PRIMARY KEY (recorddate,symbol)
);

/* Creating Data Tables ******************************** END ********************************/

/* Creating Watchlist Tables *************************** START ******************************/

CREATE TABLE IF NOT EXISTS `watchlist_liquid_options`(
	`symbol` varchar(256) NOT NULL,
	`company_name` varchar(256),
	`sector` varchar(256),
	PRIMARY KEY (sl_no,symbol)
);
 
/* Creating Watchlist Tables *************************** END *******************************/

/* Creating Calculation Tables *************************** START ******************************/

CREATE TABLE IF NOT EXISTS `calculations_quandl_daily` (
  `symbol` varchar(10) NOT NULL,
  `market` varchar(10) NOT NULL DEFAULT 'NSE',
  `recorddate` date NOT NULL,
  `open` decimal(8,2) unsigned NOT NULL,
  `high` decimal(8,2) unsigned NOT NULL,
  `low` decimal(8,2) unsigned NOT NULL,
  `close` decimal(8,2) unsigned NOT NULL,
  `prevclose` decimal(8,2) unsigned NOT NULL,
  `volume` bigint(20) unsigned NOT NULL,
  `candle_body` decimal(8,2) unsigned ,
  `candle_height` decimal(8,2) unsigned ,
  `candle_type` varchar(50),
  `change_value` decimal(8,2) NOT NULL ,
  `change_percent` decimal(8,2) NOT NULL,
  `volavg50` bigint(20) unsigned NOT NULL,
  `ma20` decimal(8,2) unsigned NOT NULL,
  `ma50` decimal(8,2) unsigned NOT NULL,
  `avg_candle_body_50` decimal(8,2) unsigned NOT NULL,
  `avg_candle_height_50` decimal(8,2) unsigned NOT NULL,
   PRIMARY KEY (symbol,market, recorddate)
);

/* Creating Calculation Tables *************************** END ******************************/

