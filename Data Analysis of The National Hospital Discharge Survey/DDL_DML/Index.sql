# Insurance Analysis
# caseID / age / days of care / DRG / Diag Code / Proc Code

#Patient
#CREATE UNIQUE INDEX IDX_PATIENT_PK ON Patient (caseID);
CREATE INDEX IDX_PATIENT_AGE ON Patient (age);

#HospitalCase
#CREATE UNIQUE INDEX IDX_HOSPITALCASE_PK ON HospitalCase (caseID);
CREATE UNIQUE INDEX IDX_HOSPITALCASE_HOSPITALID ON HospitalCase (hospitalID);
CREATE INDEX IDX_HOSPITALCASE_DAYSOFCARE ON HospitalCase (daysOfCare);
CREATE INDEX IDX_HOSPITALCASE_DRG ON HospitalCase (DRG);

#Diagnosed /
#CREATE UNIQUE INDEX IDX_DIAGNOSED_PK ON Diagnosed (caseID);

#Diagnosis
#CREATE UNIQUE INDEX IDX_DIAGNOSIS_PK ON Diagnosis (diagCode);

#Treated /
#CREATE UNIQUE INDEX IDX_TREATED_PK ON Treated (caseID);

#Procedure
#CREATE UNIQUE INDEX IDX_PROCEDURE_PK ON `Procedure` (procCode);

#Payment /
#CREATE UNIQUE INDEX IDX_PAYMENT_PK ON Payment (caseID);

#Hospital
#CREATE UNIQUE INDEX IDX_HOSPITAL_PK ON Hospital (hospitalID);