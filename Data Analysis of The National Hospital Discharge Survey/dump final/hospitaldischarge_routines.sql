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
-- Dumping events for database 'hospitaldischarge'
--

--
-- Dumping routines for database 'hospitaldischarge'
--
/*!50003 DROP PROCEDURE IF EXISTS `p_averageDaysOfStayByRegion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`joeun`@`50.75.222.202` PROCEDURE `p_averageDaysOfStayByRegion`(IN region INT, OUT `avg` DOUBLE)
BEGIN
	SELECT y.estimatedDaysOfCare/x.estimatedDischarge AS AvgLengthOfStay
    INTO `avg`
	FROM (SELECT SUM(analysisWeight) AS estimatedDischarge
		FROM HospitalCase hc JOIN Hospital h ON hc.hospitalID = h.hospitalID 
		WHERE h.geoRegion = region) x,
		(SELECT SUM(analysisWeight*daysOfCare) AS estimatedDaysOfCare
		FROM HospitalCase hc JOIN Hospital h ON hc.hospitalID = h.hospitalID 
		WHERE h.geoRegion = region) y;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `p_countDischargeStatus` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`joeun`@`50.75.222.202` PROCEDURE `p_countDischargeStatus`(IN cntD INT, IN cntP INT)
BEGIN
IF cntD < 1 THEN
	SELECT "Patient must have at least one diagnosis record." AS ErrorMessage;
ELSEIF cntD > 0 AND cntP > 0 THEN
	SELECT dischargeStatus, COUNT(caseID) AS `count`
	FROM HospitalCase 
	WHERE caseID IN (SELECT d.caseID
					FROM Diagnosed d
					INNER JOIN (SELECT caseID
								FROM Treated
								GROUP BY caseID
								HAVING COUNT(procCode) = cntP) t
					ON d.caseID = t.caseID
					GROUP BY d.caseID
					HAVING COUNT(d.diagCode) = cntD)
	GROUP BY dischargeStatus;
ELSE 
    SELECT dischargeStatus, COUNT(caseID) AS `count`
	FROM HospitalCase
	WHERE caseID IN (SELECT d.caseID
					FROM Diagnosed d
					INNER JOIN (SELECT h.caseID
								FROM HospitalCase h
								LEFT OUTER JOIN Treated t
								ON h.caseID = t.caseID
								WHERE t.caseID IS NULL
								GROUP BY h.caseID) t
					ON d.caseID = t.caseID
					GROUP BY d.caseID
					HAVING COUNT(d.diagCode) = cntD)
	GROUP BY dischargeStatus;
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `p_countPatientByAge` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`joeun`@`50.75.222.202` PROCEDURE `p_countPatientByAge`(IN ageUnit INT, IN age INT, INOUT `count` INT)
BEGIN
	DECLARE cnt INT DEFAULT 0;
    
    SELECT COUNT(caseID)
    INTO cnt
    FROM Patient p
    WHERE p.ageUnit = ageUnit 
    AND p.age = age;
    
    SET `count` = `count` + cnt;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `p_insertDiagCode` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `p_insertDiagCode`(IN seq INT, IN pos INT, IN len INT)
BEGIN
    DECLARE finished INT DEFAULT 0;

	DECLARE caseID INT;
    DECLARE diagCode VARCHAR(5);
	
    DECLARE raw_cursor CURSOR FOR
    SELECT ID, Substring(`Data`, pos, len) FROM SurveyRawData LIMIT 0, 100;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;
    
    OPEN raw_cursor;
    
    FETCH raw_cursor INTO caseID, diagCode;
    WHILE finished = 0 DO
		IF diagCode <> "     " THEN
			INSERT INTO `Diagnosed` VALUE (caseID, seq, diagCode);
		END IF;
        
        FETCH raw_cursor INTO caseID, diagCode;
	END WHILE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `p_insertDiagCodeLoop` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `p_insertDiagCodeLoop`()
BEGIN
	DECLARE cnt INT DEFAULT 1;
    DECLARE initPos INT DEFAULT 28;
    DECLARE len INT DEFAULT 5;
    #TRUNCATE `LOG`;

    looop: LOOP
		IF cnt > 15 THEN
			LEAVE looop;
		END IF;
        CALL p_insertDiagCode(cnt, initPos, len);
        SET cnt = cnt + 1;
        SET initPos = initPos + len;
    END LOOP;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `p_insertPayment` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `p_insertPayment`()
BEGIN    
    DECLARE finished INT DEFAULT 0;

	DECLARE caseID INT;
    DECLARE payment1 VARCHAR(2);
    DECLARE payment2 VARCHAR(2);
	
    DECLARE raw_cursor CURSOR FOR
    SELECT ID, Substring(`Data`, 135, 2), Substring(`Data`, 137, 2)
    FROM SurveyRawData
	ORDER BY ID;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;
    
    OPEN raw_cursor;
    
    FETCH raw_cursor INTO caseID, payment1, payment2;
    WHILE finished = 0 DO
		# Principal Payment - Do I need null checking?
		IF payment1 <> "  " THEN
			INSERT INTO Payment  (caseID, paymentType, expectedSrc)
            VALUES (caseID, 1, payment1);
		END IF;
        
        # Secondary Payment
		IF payment2 <> "  " THEN
			INSERT INTO Payment  (caseID, paymentType, expectedSrc)
            VALUES (caseID, 2, payment2);
		END IF;
        
        FETCH raw_cursor INTO caseID, payment1, payment2;
	END WHILE;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `p_insertProcCode` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `p_insertProcCode`(IN seq INT, IN pos INT, IN len INT)
BEGIN
	DECLARE cnt INT DEFAULT 0;
    DECLARE finished INT DEFAULT 0;

	DECLARE caseID INT;
    DECLARE procCode VARCHAR(5);
	
    DECLARE raw_cursor CURSOR FOR
    SELECT ID, Substring(`Data`, pos, len) FROM SurveyRawData LIMIT 0, 100;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;
    
    OPEN raw_cursor;
    
    #WHILE cnt < 8 DO
    FETCH raw_cursor INTO caseID, procCode;
    WHILE finished = 0 DO
		IF procCode <> "    " THEN
			INSERT INTO `Treated` VALUE (caseID, seq, procCode);
		END IF;
        
        FETCH raw_cursor INTO caseID, procCode;
        #SET cnt = cnt + 1;
	END WHILE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `p_insertProcCodeLoop` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `p_insertProcCodeLoop`()
BEGIN
	DECLARE cnt INT DEFAULT 1;
    DECLARE initPos INT DEFAULT 103;
    DECLARE len INT DEFAULT 4;
    #TRUNCATE `LOG`;

    looop: LOOP
		IF cnt > 8 THEN
			LEAVE looop;
		END IF;
        CALL p_insertProcCode(cnt, initPos, len);
        SET cnt = cnt + 1;
        SET initPos = initPos + len;
    END LOOP;
END ;;
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

-- Dump completed on 2019-05-18 22:41:08
