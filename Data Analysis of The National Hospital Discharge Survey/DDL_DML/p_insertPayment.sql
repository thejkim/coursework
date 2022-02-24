CREATE PROCEDURE `p_insertPayment`()
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

END