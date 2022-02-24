#UPDATE - procCode in Procedure Table : update every record with the same(old) procCode in Treated.
CREATE TRIGGER TRG_UPDATE_PROCEDURECODE AFTER UPDATE ON `Procedure` FOR EACH ROW
BEGIN
	UPDATE Treated
	SET procCode = NEW.procCode
    WHERE procCode = OLD.procCode; 
END