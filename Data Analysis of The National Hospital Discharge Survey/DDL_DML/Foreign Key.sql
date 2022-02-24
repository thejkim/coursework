ALTER TABLE Patient 
ADD CONSTRAINT FK_PatientHospitalCase
FOREIGN KEY (caseID) REFERENCES HospitalCase(caseID);

ALTER TABLE HospitalCase
ADD CONSTRAINT FK_HospitalCaseHospital
FOREIGN KEY (hospitalID) REFERENCES Hospital(hospitalID);

ALTER TABLE Diagnosed
ADD CONSTRAINT FK_DiagnosedHospitalCase 
FOREIGN KEY (caseID) REFERENCES HospitalCase(caseID),
ADD CONSTRAINT FK_DaignosedDiagnosis 
FOREIGN KEY (diagCode) REFERENCES Diagnosis(diagCode);

ALTER TABLE Treated
ADD CONSTRAINT FK_TreatedHospitalCase
FOREIGN KEY (caseID) REFERENCES HospitalCase(caseID),
ADD CONSTRAINT FK_TreatedProcedures
FOREIGN KEY (procCode) REFERENCES `Procedure`(procCode);

ALTER TABLE Payment
ADD CONSTRAINT FK_PaymentHospitalCase
FOREIGN KEY (caseID) REFERENCES HospitalCase(caseID);

/*
ALTER TABLE HospitalCase
ADD CONSTRAINT FK_HospitalCaseDiagnosis
FOREIGN KEY (admitDiagCode) REFERENCES Diagnosis(diagCode);
*/