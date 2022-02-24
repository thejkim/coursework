#UPDATE - diagCode in Diagnosis Table : 
#1. update every record with the same(old) diagCode in Diagnosed.
#2. update every record in HospitalCase where admitDiagCode is equal to the same(old) diagCode.
CREATE TRIGGER TRG_UPDATE_DIAGNOSISCODE AFTER UPDATE ON `Diagnosis` FOR EACH ROW
BEGIN
	UPDATE Diagnosed
    SET diagCode = NEW.diagCode
    WHERE diagCode = OLD.diagCode;
    
    UPDATE HospitalCase
    SET admitDiagCode = NEW.diagCode
    WHERE admitDiagCode = OLD.diagCode;
END