CREATE DEFINER=`joeun`@`50.75.222.202` PROCEDURE `p_countPatientByAge`(IN ageUnit INT, IN age INT, INOUT `count` INT)
BEGIN
	DECLARE cnt INT DEFAULT 0;
    
    SELECT COUNT(caseID)
    INTO cnt
    FROM Patient p
    WHERE p.ageUnit = ageUnit 
    AND p.age = age;
    
    SET `count` = `count` + cnt;
END