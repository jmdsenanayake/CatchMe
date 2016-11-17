/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 10.1.13-MariaDB : Database - a7530468_ihack
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`u763730107_catch` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `u763730107_catch`;

/*Table structure for table `bus_locations` */

DROP TABLE IF EXISTS `bus_locations`;

CREATE TABLE `bus_locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bus` int(11) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `lattitude` double DEFAULT NULL,
  `is_started` tinyint(1) DEFAULT '0',
  `is_closed` tinyint(1) DEFAULT '0',
  `crowd` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

/*Data for the table `bus_locations` */

insert  into `bus_locations`(`id`,`bus`,`date_time`,`longitude`,`lattitude`,`is_started`,`is_closed`,`crowd`) values (1,1,'2016-09-23 10:49:42',79.927337,6.978019,1,0,56),(2,2,'2016-09-23 10:52:45',79.924612,6.975655,1,0,0),(12,3,'2016-09-24 07:38:56',79.86589,6.939704,1,0,0),(11,9,'2016-09-24 07:38:55',79.900094,6.966957,1,0,0),(10,10,'2016-09-24 07:38:53',79.907347,6.967045,1,0,0),(9,11,'2016-09-23 10:50:54',79.987337,6.979019,1,0,0),(8,12,'2016-09-24 01:11:54',79.916706,6.971945,1,0,0),(13,13,'2016-09-24 07:38:58',79.887941,6.911078,1,0,0),(14,14,'2016-09-24 07:39:00',79.887426,6.911078,1,0,0),(15,15,'2016-09-24 07:39:02',79.899635,6.911078,1,0,0),(16,16,'2016-09-24 07:39:04',79.86311,6.929996,1,0,0),(17,17,'2016-09-24 07:39:06',NULL,NULL,1,0,0),(18,18,'2016-09-24 07:39:08',NULL,NULL,1,0,0),(19,19,'2016-09-24 07:39:10',NULL,NULL,1,0,0),(20,20,'2016-09-24 07:39:13',NULL,NULL,1,0,0),(21,0,'2016-10-08 18:23:27',79.86117,6.90210333333333,1,0,0),(22,0,'2016-10-08 18:27:30',79.861385,6.90215666666667,1,0,0),(23,0,'2016-10-08 18:27:48',79.8612166666667,6.90208,1,0,0),(24,0,'2016-10-08 23:33:18',79.860075,6.90199666666667,1,0,0);

/*Table structure for table `busses` */

DROP TABLE IF EXISTS `busses`;

CREATE TABLE `busses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `number` varchar(20) DEFAULT NULL,
  `primary_route` int(11) DEFAULT NULL,
  `owner_name` varchar(100) DEFAULT NULL,
  `owner_nic` varchar(12) DEFAULT NULL,
  `owner_contact` varchar(20) DEFAULT NULL,
  `owner_mobile` varchar(20) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `uname` varchar(50) DEFAULT NULL,
  `pword` text,
  `bus_type` varchar(20) DEFAULT NULL,
  `seating_capacity` int(11) DEFAULT NULL,
  `approval` int(11) DEFAULT NULL,
  `rating` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

/*Data for the table `busses` */

insert  into `busses`(`id`,`name`,`number`,`primary_route`,`owner_name`,`owner_nic`,`owner_contact`,`owner_mobile`,`description`,`uname`,`pword`,`bus_type`,`seating_capacity`,`approval`,`rating`) values (1,'Anoo Travels','NA-1234',1,'Anojan','922063273V',NULL,NULL,NULL,'NA-1234','123','1',43,1,5),(2,'Abi Travels','WP ABI 1478',1,'Abinaya','926578423V',NULL,NULL,NULL,'ABI-1478','123','2',50,0,2),(3,'Malith Bus','WP AD 4578',1,'Malith',NULL,NULL,NULL,NULL,NULL,NULL,'3',43,NULL,0),(8,'Janaka Bus','WP NA 1235',1,'Janaka','921700440V',NULL,NULL,NULL,NULL,NULL,'1',46,1,0),(9,'Thimitha Bus','WP ND 1244',1,'Thimitha','91210295V',NULL,NULL,NULL,NULL,NULL,'1',42,NULL,0),(10,'Nalin Travels','NP XY 5698',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2',55,NULL,0),(11,'Saran','WP NA 1235',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',49,NULL,0),(12,'NCG','NP NA 7523',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2',41,NULL,0),(13,'Avro','WP NA 1235',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2',58,NULL,0),(14,'Ram Travels','WP NA 1235',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',48,NULL,0),(15,'Tharani RS','NP NA 7523',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',47,NULL,0),(16,'Sayanthan Travels','WP NA 1235',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'3',52,NULL,0),(17,'King Bus','WP NA 1235',3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'3',39,NULL,0),(18,'Yarl Travels','NP NA 7523',3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2',41,NULL,0),(19,'RVH','WP NA 1235',3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2',50,NULL,0),(20,'DWS','NP NA 7523',3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'3',47,NULL,0),(21,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,44,NULL,0),(22,'Saman Travels ','NA-123',154,'Malith','91101459V','sff',NULL,NULL,'NA-123','sff','semi',50,0,0),(23,'Saman Travels ','NA-123',154,'Malith','91101459V','sff',NULL,NULL,'NA-123','sff','semi',50,0,0),(24,'Saman Travels ','NA-123',154,'Malith','91101459V','sff',NULL,NULL,'NA-123','sff','semi',50,0,0),(25,'Saman Travels ','NA-123',154,'Malith','91101459V','sff',NULL,NULL,'NA-123','sff','semi',50,0,0),(26,'Mali','1',1,'x','x','x',NULL,NULL,'1','x','1',52,0,0),(27,'Mali','1',1,'x','x','x',NULL,NULL,'1','x','1',52,0,0);

/*Table structure for table `places` */

DROP TABLE IF EXISTS `places`;

CREATE TABLE `places` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `lattitude` double DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

/*Data for the table `places` */

insert  into `places`(`id`,`name`,`longitude`,`lattitude`,`description`) values (1,'Kadawatha',79.954669,7.005403,NULL),(2,'Pettah',79.850255,6.934507,NULL),(3,'Armor Street',79.86589,6.939704,NULL),(4,'Kelanitissa',79.87941,6.951654,NULL),(5,'Kelani Bridge',79.882345,6.954919,NULL),(6,'Thorana junction',79.900094,6.966957,NULL),(7,'Bulugaha junction',79.907347,6.967045,NULL),(8,'University of Kelaniya',79.916706,6.971945,NULL),(9,'Kiribathgoda',79.927466,6.978467,NULL),(11,'Union place',79.862458,6.918122,NULL),(12,'Town hall',79.862831,6.916393,NULL),(13,'Thummulla',79.859946,6.896652,NULL),(14,'Police Park',79.862226,6.89251,NULL),(15,'Nugegoda',79.888385,6.869857,NULL),(16,'Delkanda',79.901267,6.863041,NULL),(17,'Wijerama',79.908547,6.857653,NULL),(18,'Maharagama',79.923326,6.849717,NULL),(20,'BMICH',79.871677,6.903944,NULL),(21,'Borella',79.877559,6.914779,NULL),(22,'Orugodawaththa',79.878257,6.940907,NULL),(23,'Pannipitiya',79.961638,6.842635,NULL),(24,'Kottawa',79.964061,6.841664,NULL),(25,'Bambalapitiya',79.856772,6.896018,NULL);

/*Table structure for table `routes` */

DROP TABLE IF EXISTS `routes`;

CREATE TABLE `routes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `origin_place` int(11) DEFAULT NULL,
  `destination_place` int(11) DEFAULT NULL,
  `route_number` varchar(10) DEFAULT NULL,
  `estimated_distance` double DEFAULT '0',
  `estimated_hours` double DEFAULT '0',
  `description` varchar(100) DEFAULT NULL,
  `normal_ticket` double NOT NULL DEFAULT '0',
  `semilux_ticket` double NOT NULL DEFAULT '0',
  `lux_ticket` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `routes` */

insert  into `routes`(`id`,`origin_place`,`destination_place`,`route_number`,`estimated_distance`,`estimated_hours`,`description`,`normal_ticket`,`semilux_ticket`,`lux_ticket`) values (1,1,2,'138',13,0.75,NULL,0,0,0),(2,18,2,'123',0,0,NULL,0,0,0),(3,25,1,'154',0,0,NULL,0,0,0);

/*Table structure for table `routes_places` */

DROP TABLE IF EXISTS `routes_places`;

CREATE TABLE `routes_places` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `route_id` int(11) DEFAULT NULL,
  `place_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=42 DEFAULT CHARSET=latin1;

/*Data for the table `routes_places` */

insert  into `routes_places`(`id`,`route_id`,`place_id`) values (1,1,2),(2,1,3),(3,1,4),(4,1,5),(5,1,6),(6,1,7),(7,1,8),(8,1,9),(9,1,1),(11,2,2),(12,2,11),(13,2,12),(14,2,13),(15,2,14),(16,2,15),(17,2,16),(18,2,17),(19,2,18),(20,3,25),(21,3,13),(22,3,20),(23,3,21),(24,3,22),(25,3,5),(26,3,6),(27,3,7),(28,3,8),(29,3,9),(30,3,1),(31,4,2),(32,4,11),(33,4,12),(34,4,13),(35,4,14),(36,4,15),(37,4,16),(38,4,17),(39,4,18),(40,4,24),(41,4,23);

/*Table structure for table `seating` */

DROP TABLE IF EXISTS `seating`;

CREATE TABLE `seating` (
  `record_ID` int(11) NOT NULL AUTO_INCREMENT,
  `bus_no` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `sensor1` int(11) NOT NULL,
  `sensor2` int(11) NOT NULL,
  PRIMARY KEY (`record_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

/*Data for the table `seating` */

insert  into `seating`(`record_ID`,`bus_no`,`sensor1`,`sensor2`) values (1,'1',1,0),(2,'0',0,0);

/*Table structure for table `tickets` */

DROP TABLE IF EXISTS `tickets`;

CREATE TABLE `tickets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bus` int(11) DEFAULT NULL,
  `route` int(11) DEFAULT NULL,
  `origin` int(11) DEFAULT NULL,
  `destination` int(11) DEFAULT NULL,
  `quantity` int(11) NOT NULL DEFAULT '0',
  `amount` double NOT NULL DEFAULT '0',
  `tdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=46 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

/*Data for the table `tickets` */

insert  into `tickets`(`id`,`bus`,`route`,`origin`,`destination`,`quantity`,`amount`,`tdate`) values (1,1,1,1,2,1,100,'2016-10-08 07:11:44'),(2,1,1,1,2,1,100,'2016-10-08 08:00:46'),(3,0,0,1,2,1,20,'2016-10-08 08:10:54'),(4,0,0,1,2,1,20,'2016-10-08 08:11:52'),(5,0,0,1,2,1,20,'2016-10-08 08:47:08'),(6,0,0,1,4,1,20,'2016-10-08 08:47:34'),(7,0,0,1,4,1,20,'2016-10-08 08:49:10'),(8,0,0,1,2,1,20,'2016-10-08 08:57:24'),(9,0,0,1,2,1,20,'2016-10-08 12:19:29'),(10,0,0,1,2,1,20,'2016-10-08 13:25:45'),(11,0,0,1,2,1,20,'2016-10-08 14:48:23'),(12,0,0,1,2,1,20,'2016-10-08 14:56:05'),(13,0,0,1,2,1,20,'2016-10-08 15:00:08'),(14,0,0,1,2,1,20,'2016-10-08 15:00:39'),(15,0,0,1,2,1,20,'2016-10-08 15:01:16'),(16,0,0,1,2,1,20,'2016-10-08 15:04:37'),(17,0,0,1,2,1,20,'2016-10-08 15:05:01'),(18,0,0,1,2,1,20,'2016-10-08 15:05:10'),(19,0,0,1,2,1,20,'2016-10-08 15:05:18'),(20,0,0,1,2,1,20,'2016-10-08 15:05:27'),(21,0,0,1,2,1,20,'2016-10-08 15:08:30'),(22,0,0,1,2,1,20,'2016-10-08 15:08:40'),(23,0,0,1,2,1,20,'2016-10-08 15:08:46'),(24,0,0,1,2,1,20,'2016-10-08 15:08:53'),(25,0,0,1,2,1,20,'2016-10-08 15:08:59'),(26,0,0,4,2,1,20,'2016-10-08 20:14:00'),(27,1,1,1,2,1,20,'2016-10-08 21:02:36'),(28,1,1,1,2,1,20,'2016-10-08 21:02:44'),(29,0,0,1,2,1,20,'2016-10-08 21:02:54'),(30,1,1,9,2,1,20,'2016-10-08 21:03:00'),(31,1,1,1,2,1,20,'2016-10-08 22:39:19'),(32,1,1,1,2,1,20,'2016-10-09 10:44:45'),(33,1,1,1,2,1,20,'2016-10-09 11:10:59'),(34,1,1,1,2,1,20,'2016-10-09 11:11:13'),(35,1,1,1,2,1,20,'2016-10-09 13:41:05'),(36,1,1,1,2,1,20,'2016-10-09 13:41:16'),(37,1,1,1,2,1,20,'2016-10-20 13:56:04'),(38,1,1,1,2,1,20,'2016-10-20 13:56:18'),(39,1,1,1,2,1,20,'2016-10-20 15:23:53'),(40,1,1,1,2,1,20,'2016-10-20 15:23:59'),(41,1,1,1,2,1,20,'2016-10-23 10:27:25'),(42,1,1,1,2,1,20,'2016-10-23 10:27:50'),(43,1,1,1,2,1,20,'2016-11-16 17:55:46'),(44,1,1,1,2,1,20,'2016-11-16 17:56:09'),(45,1,1,1,2,1,20,'2016-11-16 17:56:09');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
