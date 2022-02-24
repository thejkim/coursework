CREATE DEFINER=`joeun`@`172.58.224.67` PROCEDURE `p_insertProcCode`(IN seq INT, IN pos INT, IN len INT)
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
END