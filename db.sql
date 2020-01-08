-- MySQL dump 10.13  Distrib 5.7.28, for Linux (x86_64)
--
-- Host: localhost    Database: travelmanager
-- ------------------------------------------------------
-- Server version	5.7.28-0ubuntu0.18.04.4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `travelmanager`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `travelmanager` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `travelmanager`;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `id` int(11) NOT NULL,
  `manager_id` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (0,NULL,'test'),(1,NULL,'test'),(2,NULL,'test'),(3,NULL,'test'),(50,NULL,'???');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (52);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_application`
--

DROP TABLE IF EXISTS `payment_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_application` (
  `id` int(11) NOT NULL,
  `applicant_id` int(11) NOT NULL,
  `apply_time` datetime(6) NOT NULL,
  `department_id` int(11) NOT NULL,
  `food_payment` float NOT NULL,
  `hotel_payment` float NOT NULL,
  `invoiceurls` varchar(255) DEFAULT NULL,
  `other_payment` float NOT NULL,
  `status` int(11) NOT NULL,
  `travel_id` int(11) NOT NULL,
  `vehicle_payment` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_application`
--

LOCK TABLES `payment_application` WRITE;
/*!40000 ALTER TABLE `payment_application` DISABLE KEYS */;
INSERT INTO `payment_application` VALUES (1,0,'2020-01-03 06:05:13.000000',0,1,4,NULL,2,3,42,3);
/*!40000 ALTER TABLE `payment_application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `picture`
--

DROP TABLE IF EXISTS `picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `picture` (
  `id` int(11) NOT NULL,
  `upload_time` datetime(6) NOT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `picture`
--

LOCK TABLES `picture` WRITE;
/*!40000 ALTER TABLE `picture` DISABLE KEYS */;
/*!40000 ALTER TABLE `picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `travel_application`
--

DROP TABLE IF EXISTS `travel_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `travel_application` (
  `id` int(11) NOT NULL,
  `applicant_id` int(11) NOT NULL,
  `apply_time` datetime(6) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `department_id` int(11) NOT NULL,
  `end_time` datetime(6) NOT NULL,
  `food_budget` float NOT NULL,
  `hotel_budget` float NOT NULL,
  `other_budget` float NOT NULL,
  `paid` bit(1) NOT NULL,
  `province` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `start_time` datetime(6) NOT NULL,
  `status` int(11) NOT NULL,
  `vehicle_budget` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_application`
--

LOCK TABLES `travel_application` WRITE;
/*!40000 ALTER TABLE `travel_application` DISABLE KEYS */;
INSERT INTO `travel_application` VALUES (42,25,'2020-01-02 20:53:27.588000','Wuhan',1,'2018-01-30 18:00:00.000000',100,10000,50,_binary '\0','Hubei','????','2018-01-30 18:00:00.000000',3,100),(43,25,'2020-01-02 20:53:34.273000','Wuhan',0,'2018-01-30 18:00:00.000000',100,10000,50,_binary '\0','Hubei','????','2018-01-30 18:00:00.000000',3,100),(45,27,'2020-01-03 06:05:45.991000','string',0,'2020-01-03 06:05:13.595000',0,0,0,_binary '\0','string','string','2020-01-03 06:05:13.596000',3,0),(46,27,'2020-01-03 06:08:19.221000','Xianning',0,'2020-01-03 06:05:13.595000',1,2,3,_binary '\0','Hubei','string','2020-01-03 06:05:13.596000',3,4);
/*!40000 ALTER TABLE `travel_application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `department_id` int(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password_hash` varchar(255) NOT NULL,
  `role` int(11) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `work_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (24,'???',NULL,NULL,NULL,0,'qq.com','d58b105e06dd038e8890dc6ef8fe6aa1',0,_binary '','123','ccnu2020'),(25,'??',NULL,NULL,NULL,50,'qq.com','f8d63cc0499b8462613ad91560fbee0f',2,_binary '','123','manager'),(26,'????',NULL,NULL,NULL,0,'qq.com','f8d63cc0499b8462613ad91560fbee0f',1,_binary '','123','department_manager'),(27,'??',NULL,NULL,NULL,0,'qq.com','f8d63cc0499b8462613ad91560fbee0f',0,_binary '','123','employee'),(44,'??',NULL,NULL,NULL,NULL,'qq.com','d58b105e06dd038e8890dc6ef8fe6aa1',0,_binary '','123','test1'),(47,'admin',NULL,NULL,NULL,NULL,'test','f8d63cc0499b8462613ad91560fbee0f',3,_binary '','test','admin'),(48,'???test',NULL,NULL,NULL,NULL,'qq.com','f8d63cc0499b8462613ad91560fbee0f',0,_binary '','123','test'),(51,'Chenglei Yuan',NULL,NULL,NULL,NULL,'Chenglei.Y@outlook.com','9ee796d2d0f4da0668e83ef4834cbe7b',0,_binary '\0','123','forgetpassword');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-08  9:28:26
