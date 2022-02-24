CREATE PROCEDURE `p_insertDiagCode`(IN seq INT, IN pos INT, IN len INT)
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
END