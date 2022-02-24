CREATE PROCEDURE `p_insertDiagCodeLoop`()
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
END