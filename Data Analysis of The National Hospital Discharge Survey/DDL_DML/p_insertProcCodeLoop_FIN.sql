CREATE PROCEDURE `p_insertProcCodeLoop`()
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
END