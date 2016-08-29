

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for affiliate_engine_api
CREATE DATABASE IF NOT EXISTS `affiliate_engine_api` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `affiliate_engine_api`;


-- Dumping structure for table affiliate_engine_api.advertisement
CREATE TABLE IF NOT EXISTS `advertisement` (
  `ad_id` int(11) NOT NULL AUTO_INCREMENT,
  `ad_image_url` varchar(100) NOT NULL DEFAULT '0',
  `ad_text` varchar(100) DEFAULT '0',
  `destination_url` varchar(500) NOT NULL,
  PRIMARY KEY (`ad_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Dumping data for table affiliate_engine_api.advertisement: ~3 rows (approximately)
/*!40000 ALTER TABLE `advertisement` DISABLE KEYS */;
INSERT INTO `advertisement` (`ad_id`, `ad_image_url`, `ad_text`, `destination_url`) VALUES
	(2, 'http://172.16.25.33:7000/330x200-HIndi-Flight-10042015.jpg', '0', 'http://www.makemytrip.com'),
	(4, 'http://172.16.43.137/wordpress/wp-includes/images/bali_package.jpg', 'MMT Bali Packages', 'https://www.makemytrip.com/holidays-international/bali-vacation-tour-packages.html'),
	(6, 'http://172.16.43.137/wordpress/wp-includes/images/goa_hotels_ad.jpg', 'GOA hotel ads', 'https://www.makemytrip.com/hotels/goa-hotels.html');
/*!40000 ALTER TABLE `advertisement` ENABLE KEYS */;


-- Dumping structure for table affiliate_engine_api.ad_selector_rules
CREATE TABLE IF NOT EXISTS `ad_selector_rules` (
  `ad_id` int(11) NOT NULL,
  `city_id` int(11) NOT NULL,
  `travel_type` int(11) NOT NULL,
  `other` varchar(100) NOT NULL,
  PRIMARY KEY (`city_id`,`travel_type`,`other`),
  KEY `FK_ad_selector_rules_advertisement` (`ad_id`),
  KEY `FK_ad_selector_rules_travel_types` (`travel_type`),
  CONSTRAINT `FK_ad_selector_rules_advertisement` FOREIGN KEY (`ad_id`) REFERENCES `advertisement` (`ad_id`),
  CONSTRAINT `FK_ad_selector_rules_city` FOREIGN KEY (`city_id`) REFERENCES `city` (`city_id`),
  CONSTRAINT `FK_ad_selector_rules_travel_types` FOREIGN KEY (`travel_type`) REFERENCES `travel_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table affiliate_engine_api.ad_selector_rules: ~2 rows (approximately)
/*!40000 ALTER TABLE `ad_selector_rules` DISABLE KEYS */;
INSERT INTO `ad_selector_rules` (`ad_id`, `city_id`, `travel_type`, `other`) VALUES
	(4, 4, 4, ''),
	(6, 8, 6, '');
/*!40000 ALTER TABLE `ad_selector_rules` ENABLE KEYS */;


-- Dumping structure for table affiliate_engine_api.city
CREATE TABLE IF NOT EXISTS `city` (
  `city_name` varchar(100) NOT NULL,
  `city_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='one city id can have many names. For example city names bombay and mumbai will have the same city id';

-- Dumping data for table affiliate_engine_api.city: ~8 rows (approximately)
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` (`city_name`, `city_id`) VALUES
	('MUMBAI', 1),
	('DELHI', 2),
	('BALI', 4),
	('CHENNAI', 6),
	('GOA', 8),
	('MANALI', 10),
	('SHIMLA', 12),
	('KUFRI', 14);
/*!40000 ALTER TABLE `city` ENABLE KEYS */;


-- Dumping structure for table affiliate_engine_api.crawled_info
CREATE TABLE IF NOT EXISTS `crawled_info` (
  `page_id` varchar(200) NOT NULL,
  `travel_type_ids` varchar(500) DEFAULT NULL COMMENT '_separated travel_type_ids',
  `city_names` varchar(500) DEFAULT NULL COMMENT '_separated city names',
  `other_keywords` varchar(500) DEFAULT NULL COMMENT '_separated other keywords',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `crawl_period` time NOT NULL DEFAULT '00:00:00',
  PRIMARY KEY (`page_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table affiliate_engine_api.crawled_info: ~3 rows (approximately)
/*!40000 ALTER TABLE `crawled_info` DISABLE KEYS */;
INSERT INTO `crawled_info` (`page_id`, `travel_type_ids`, `city_names`, `other_keywords`, `timestamp`, `crawl_period`) VALUES
	('http://172.16.43.137/wordpress/2016/08/26/essential-bali-travel-tips-what-to-know-before-you-go/', 'holidays,hotels', '4', '\'Honeymoons and romance\',\' Coasts and islands\',\' Beaches\',\' Festivals and events\',\' Art and culture\',\' Ecotourism\',\' Diving and snorkelling\'', '2016-08-27 05:51:13', '05:35:00'),
	('http://172.16.43.137/wordpress/2016/08/26/top-5-resorts-in-goa-near-the-beach/', 'holidays,hotels', '4', '\'The best of the best hotels in Goa that are not only luxurious but also unique.\'', '2016-08-27 06:27:19', '05:35:00'),
	('http://scrapboktravelblog.blogspot.in/p/backpacking-in-india-shimla.html', 'holidays,hotels', '12,14', '\'\'', '2016-08-27 06:58:37', '05:35:00');
/*!40000 ALTER TABLE `crawled_info` ENABLE KEYS */;


-- Dumping structure for table affiliate_engine_api.dashboard
CREATE TABLE IF NOT EXISTS `dashboard` (
  `partner_url` varchar(500) NOT NULL DEFAULT '0',
  `clicks` int(11) DEFAULT '0',
  `impressions` int(11) DEFAULT '0',
  PRIMARY KEY (`partner_url`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table affiliate_engine_api.dashboard: ~1 rows (approximately)
/*!40000 ALTER TABLE `dashboard` DISABLE KEYS */;
INSERT INTO `dashboard` (`partner_url`, `clicks`, `impressions`) VALUES
	('http://scrapboktravelblog.blogspot.in/p/backpacking-in-india-shimla.html', 0, 4);
/*!40000 ALTER TABLE `dashboard` ENABLE KEYS */;


-- Dumping structure for table affiliate_engine_api.partner_info
CREATE TABLE IF NOT EXISTS `partner_info` (
  `partner_id` int(11) NOT NULL AUTO_INCREMENT,
  `partner_name` varchar(100) NOT NULL,
  PRIMARY KEY (`partner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Dumping data for table affiliate_engine_api.partner_info: ~1 rows (approximately)
/*!40000 ALTER TABLE `partner_info` DISABLE KEYS */;
INSERT INTO `partner_info` (`partner_id`, `partner_name`) VALUES
	(1, 'Google'),
	(2, 'Trippathon'),
	(4, 'Scrap Bok');
/*!40000 ALTER TABLE `partner_info` ENABLE KEYS */;


-- Dumping structure for table affiliate_engine_api.partner_urls
CREATE TABLE IF NOT EXISTS `partner_urls` (
  `partner_id` int(11) NOT NULL,
  `partner_url` varchar(200) NOT NULL,
  `default_ad_id` int(11) NOT NULL,
  PRIMARY KEY (`partner_url`),
  KEY `FK_partner_urls_partner_info` (`partner_id`),
  KEY `FK_partner_urls_advertisement` (`default_ad_id`),
  CONSTRAINT `FK_partner_urls_advertisement` FOREIGN KEY (`default_ad_id`) REFERENCES `advertisement` (`ad_id`),
  CONSTRAINT `FK_partner_urls_partner_info` FOREIGN KEY (`partner_id`) REFERENCES `partner_info` (`partner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table affiliate_engine_api.partner_urls: ~4 rows (approximately)
/*!40000 ALTER TABLE `partner_urls` DISABLE KEYS */;
INSERT INTO `partner_urls` (`partner_id`, `partner_url`, `default_ad_id`) VALUES
	(2, 'http://172.16.43.137/wordpress/2016/08/26/essential-bali-travel-tips-what-to-know-before-you-go/', 4),
	(2, 'http://172.16.43.137/wordpress/2016/08/26/top-5-resorts-in-goa-near-the-beach/', 6),
	(1, 'http://googletravel.blogspot.in/', 2),
	(4, 'http://scrapboktravelblog.blogspot.in/p/backpacking-in-india-shimla.html', 4);
/*!40000 ALTER TABLE `partner_urls` ENABLE KEYS */;


-- Dumping structure for table affiliate_engine_api.travel_types
CREATE TABLE IF NOT EXISTS `travel_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `travel_type_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Dumping data for table affiliate_engine_api.travel_types: ~2 rows (approximately)
/*!40000 ALTER TABLE `travel_types` DISABLE KEYS */;
INSERT INTO `travel_types` (`id`, `travel_type_name`) VALUES
	(2, 'flights'),
	(4, 'hotels'),
	(6, 'holidays');
/*!40000 ALTER TABLE `travel_types` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
