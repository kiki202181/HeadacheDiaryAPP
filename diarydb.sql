-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: diarydb
-- ------------------------------------------------------
-- Server version	5.7.36-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `diary_tb`
--

use heroku_33b53fc17063b2b;

DROP TABLE IF EXISTS `diary_tb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diary_tb` (
  `diary_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `menstruation` varchar(20) DEFAULT NULL,
  `morning_condition` varchar(20) DEFAULT NULL,
  `noon_condition` varchar(20) DEFAULT NULL,
  `night_condition` varchar(20) DEFAULT NULL,
  `morning_medicine` varchar(20) DEFAULT NULL,
  `noon_medicine` varchar(20) DEFAULT NULL,
  `night_medicine` varchar(20) DEFAULT NULL,
  `impact` varchar(20) DEFAULT NULL,
  `throbbing_pain` varchar(20) DEFAULT NULL,
  `nausea` varchar(20) DEFAULT NULL,
  `vomiting` varchar(20) DEFAULT NULL,
  `memo` varchar(140) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`diary_id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diary_tb`
--

LOCK TABLES `diary_tb` WRITE;
/*!40000 ALTER TABLE `diary_tb` DISABLE KEYS */;
INSERT INTO `diary_tb` VALUES (82,'2022-05-01',NULL,'fine','fine','fine',NULL,NULL,NULL,'zero',NULL,NULL,NULL,'',1),(83,'2022-05-02',NULL,'fine','fine','fine',NULL,NULL,NULL,'zero',NULL,NULL,NULL,'',1),(84,'2022-05-03',NULL,'fine','fine','fine',NULL,NULL,NULL,'zero',NULL,NULL,NULL,NULL,1),(92,'2022-04-08',NULL,'fine','fine','fine',NULL,NULL,NULL,'zero',NULL,NULL,NULL,NULL,1),(93,'2022-04-08',NULL,'fine','fine','fine',NULL,NULL,NULL,'zero',NULL,NULL,NULL,NULL,1),(98,'2022-05-31',NULL,'fine','fine','fine',NULL,NULL,NULL,'zero',NULL,NULL,NULL,'',1),(99,'2022-04-28',NULL,'fine','fine','bad',NULL,NULL,NULL,'zero',NULL,NULL,NULL,'',1),(103,'2022-05-01',NULL,'bad','fine','fine',NULL,NULL,NULL,'zero',NULL,NULL,NULL,'',1),(104,'2022-05-25','on','not_good','bad','not_good','on','on','on','two','脈打つ痛み','吐き気','嘔吐','aa',1),(105,'2022-04-20','on','not_good','fine','not_good',NULL,'on',NULL,'zero',NULL,NULL,NULL,'',1),(106,'2022-04-07',NULL,'not_good','bad','not_good',NULL,NULL,NULL,'zero','脈打つ痛み','吐き気','嘔吐','',1);
/*!40000 ALTER TABLE `diary_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_tb`
--

DROP TABLE IF EXISTS `user_tb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_tb` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `authority` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_tb`
--

LOCK TABLES `user_tb` WRITE;
/*!40000 ALTER TABLE `user_tb` DISABLE KEYS */;
INSERT INTO `user_tb` VALUES (1,'a','a','user'),(11,'aa','aaa','USER');
/*!40000 ALTER TABLE `user_tb` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-01 14:29:39
