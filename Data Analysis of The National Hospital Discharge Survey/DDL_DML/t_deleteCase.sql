#DELETE - a record from HospitalCase : delete every record with the same caseID from Patient, Diagnosed, Treated, and Payment.
CREATE TRIGGER TRG_DELETE_CASE BEFORE DELETE ON `HospitalCase` FOR EACH ROW
BEGIN
	DELETE FROM Patient WHERE caseID = OLD.caseID;
    DELETE FROM Diagnosed WHERE caseID = OLD.caseID;
    DELETE FROM Treated WHERE caseID = OLD.caseID;
    DELETE FROM Payment WHERE caseID = OLD.caseID;
END