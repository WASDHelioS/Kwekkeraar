CREATE DATABASE  IF NOT EXISTS `kwekkeraar` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `kwekkeraar`;
-- MySQL dump 10.13  Distrib 5.6.11, for Win32 (x86)
--
-- Host: localhost    Database: kwekkeraar
-- ------------------------------------------------------
-- Server version	5.6.13

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
-- Table structure for table `bericht`
--

DROP TABLE IF EXISTS `bericht`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bericht` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `inhoud` varchar(255) DEFAULT NULL,
  `poster` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_POSTER_idx` (`poster`),
  KEY `FKF2F3D8B5C9AB9EDE` (`poster`),
  CONSTRAINT `FK_POSTER` FOREIGN KEY (`poster`) REFERENCES `gebruiker` (`naam`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bericht`
--

LOCK TABLES `bericht` WRITE;
/*!40000 ALTER TABLE `bericht` DISABLE KEYS */;
INSERT INTO `bericht` VALUES (1,'qqqqaaaa','WASDHelioS'),(2,'aaaaqqqq','WASDHelioS'),(3,'qqqqwwwwqqqqwwww','WASDHelioS'),(4,'asdqweasdqwe','WASDHelioS');
/*!40000 ALTER TABLE `bericht` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bericht_liked`
--

DROP TABLE IF EXISTS `bericht_liked`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bericht_liked` (
  `naam` varchar(45) NOT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`naam`,`id`),
  KEY `FK_BERICHT_idx` (`id`),
  CONSTRAINT `FK_BERICHT` FOREIGN KEY (`id`) REFERENCES `bericht` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_GEBRUIKER` FOREIGN KEY (`naam`) REFERENCES `gebruiker` (`naam`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bericht_liked`
--

LOCK TABLES `bericht_liked` WRITE;
/*!40000 ALTER TABLE `bericht_liked` DISABLE KEYS */;
/*!40000 ALTER TABLE `bericht_liked` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gebruiker`
--

DROP TABLE IF EXISTS `gebruiker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gebruiker` (
  `naam` varchar(45) NOT NULL,
  `wachtwoord` varchar(128) NOT NULL,
  `rol` enum('gebruiker','moderator') NOT NULL DEFAULT 'gebruiker',
  `accountsoort` enum('normaal','platinum') NOT NULL DEFAULT 'normaal',
  `salt` varchar(16) NOT NULL,
  PRIMARY KEY (`naam`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gebruiker`
--

LOCK TABLES `gebruiker` WRITE;
/*!40000 ALTER TABLE `gebruiker` DISABLE KEYS */;
INSERT INTO `gebruiker` VALUES ('Nick','57d579c3b515791d8da9f8a118b9f8fc7a64d28ffaab1e33b86f4214cc73fd42e5a5ffdd6634ffedf6f8d62f375dcc3fe1a3b79e80847d075c21b0d12ddcacce','gebruiker','normaal','778236dc0f14c5f9'),('Remco','d539d5e77487cf9ace10c2435a99930544940ea92ea85e7db9cbfa48c4cb471b6446e6b321772e628f54c41538e015d5a65e1a1eb980ed17517541807cc87ef7','gebruiker','normaal','22a3ef6fcad3200b'),('WASDHelioS','d899e89e394a117a2cc18ea7ffe382f2b45b25ea4b63d679208d24558b9d378f0c6926087a708f99a12bdbf70ebc8867474a80ebfe5a7d7b1f2ea0df6ea3853a','gebruiker','normaal','1b5bd72d5c31c304');
/*!40000 ALTER TABLE `gebruiker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gebruiker_favoriet`
--

DROP TABLE IF EXISTS `gebruiker_favoriet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gebruiker_favoriet` (
  `naam` varchar(45) NOT NULL,
  `favoriet` varchar(45) NOT NULL,
  PRIMARY KEY (`naam`,`favoriet`),
  KEY `FK_FAVORIET_idx` (`favoriet`),
  CONSTRAINT `FK_FAVORIET` FOREIGN KEY (`favoriet`) REFERENCES `gebruiker` (`naam`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_GEBRUIKER2` FOREIGN KEY (`naam`) REFERENCES `gebruiker` (`naam`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gebruiker_favoriet`
--

LOCK TABLES `gebruiker_favoriet` WRITE;
/*!40000 ALTER TABLE `gebruiker_favoriet` DISABLE KEYS */;
/*!40000 ALTER TABLE `gebruiker_favoriet` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-10-27 18:52:30
