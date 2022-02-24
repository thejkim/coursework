CREATE DATABASE  IF NOT EXISTS `hospitaldischarge` /*!40100 DEFAULT CHARACTER SET utf16 */;
USE `hospitaldischarge`;
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: hospitaldischarge
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hospitalcase`
--

DROP TABLE IF EXISTS `hospitalcase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hospitalcase` (
  `caseID` int(11) NOT NULL AUTO_INCREMENT,
  `hospitalID` int(11) NOT NULL,
  `surveyYear` year(4) DEFAULT NULL,
  `dischargeMonth` tinyint(2) DEFAULT NULL,
  `dischargeStatus` tinyint(1) NOT NULL,
  `daysOfCare` smallint(4) DEFAULT NULL,
  `stayLength_f` tinyint(1) DEFAULT NULL,
  `analysisWeight` mediumint(5) NOT NULL,
  `DRG` smallint(3) DEFAULT NULL,
  `admissionType` tinyint(1) DEFAULT NULL,
  `admissionSrc` tinyint(2) DEFAULT NULL,
  `admitDiagCode` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`caseID`),
  UNIQUE KEY `IDX_HOSPITALCASE_HOSPITALID` (`hospitalID`),
  KEY `IDX_HOSPITALCASE_DAYSOFCARE` (`daysOfCare`),
  KEY `IDX_HOSPITALCASE_DRG` (`DRG`),
  CONSTRAINT `FK_HospitalCaseHospital` FOREIGN KEY (`hospitalID`) REFERENCES `hospital` (`hospitalID`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf16;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TRG_DELETE_CASE` BEFORE DELETE ON `hospitalcase` FOR EACH ROW BEGIN
	DELETE FROM Patient WHERE caseID = OLD.caseID;
    DELETE FROM Diagnosed WHERE caseID = OLD.caseID;
    DELETE FROM Treated WHERE caseID = OLD.caseID;
    DELETE FROM Payment WHERE caseID = OLD.caseID;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-18 22:41:06
