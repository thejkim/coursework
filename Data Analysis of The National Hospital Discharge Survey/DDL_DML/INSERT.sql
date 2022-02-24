USE HospitalDischarge;

# INSERT Patient Data
INSERT INTO Patient (caseID, newbornStatus, ageUnit, age, sex, race, maritalStatus)
SELECT ID, Substring(`Data`, 3, 1), Substring(`Data`, 4, 1), Substring(`Data`, 5, 2), Substring(`Data`, 7, 1), Substring(`Data`, 8, 1), Substring(`Data`, 9, 1)
FROM SurveyRawData
ORDER BY ID;
#151551 row(s) affected Records: 151551  Duplicates: 0  Warnings: 0
SELECT Count(caseID) FROM Patient ORDER BY caseID;
SELECT * FROM Patient ORDER BY caseID LIMIT 100;

# INSERT HospitalCase Data
INSERT INTO HospitalCase (caseID, hospitalID, surveyYear, dischargeMonth, dischargeStatus, daysOfCare, stayLength_f, analysisWeight, DRG, admissionType, admissionSrc, admitDiagCode)
SELECT ID, ID, Concat(Substring(`Data`, 26, 2), Substring(`Data`, 1, 2)), Substring(`Data`, 10, 2), Substring(`Data`, 12, 1), Substring(`Data`, 13, 4), Substring(`Data`, 17, 1), Substring(`Data`, 21, 5), Substring(`Data`, 139, 3), Substring(`Data`, 142, 1), Substring(`Data`, 143, 2), Substring(`Data`, 145, 5)
FROM SurveyRawData
ORDER BY ID;
#151551 row(s) affected Records: 151551  Duplicates: 0  Warnings: 0
SELECT Count(caseID) FROM HospitalCase ORDER BY caseID;
SELECT * FROM HospitalCase ORDER BY caseID LIMIT 100;

# INSERT Hospital Data
# 18 (1) : Geographic region
# 19 (1) : Number of beds, recode
# 20 (1) : Hospital ownership
INSERT INTO Hospital (hospitalID, geoRegion, beds, ownership)
SELECT ID, Substring(`Data`, 18, 1), Substring(`Data`, 19, 1), Substring(`Data`, 20, 1)
FROM SurveyRawData
ORDER BY ID;
SELECT Count(hospitalID) FROM Hospital ORDER BY hospitalID;
SELECT * FROM Hospital ORDER BY hospitalID LIMIT 100;

# INSERT Payment Data
call p_insertPayment();
#Error Code: 2013. Lost connection to MySQL server during query
SELECT Count(caseID) FROM Payment ORDER BY caseID;
SELECT * FROM Payment ORDER BY caseID LIMIT 100;

call p_insertDiagCodeLoop();
SELECT Count(caseID) FROM Diagnosed ORDER BY caseID;
call p_insertProcCodeLoop();
SELECT Count(caseID) FROM Treated ORDER BY caseID;


INSERT INTO Diagnosis
SELECT diagCode, "Desc" FROM Diagnosed GROUP BY diagCode;

INSERT INTO `Procedure`
SELECT procCode, "Desc" FROM Treated GROUP BY procCode;
