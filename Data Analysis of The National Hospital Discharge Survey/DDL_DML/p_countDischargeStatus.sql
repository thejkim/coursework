CREATE DEFINER=`joeun`@`50.75.222.202` PROCEDURE `p_countDischargeStatus`(IN cntD INT, IN cntP INT)
BEGIN
IF cntD < 1 THEN
	SELECT "Patient must have at least one diagnosis record." AS ErrorMessage;
ELSEIF cntD > 0 AND cntP > 0 THEN
	SELECT dischargeStatus, COUNT(caseID) AS `count`
	FROM HospitalCase 
	WHERE caseID IN (SELECT d.caseID
					FROM Diagnosed d
					INNER JOIN (SELECT caseID
								FROM Treated
								GROUP BY caseID
								HAVING COUNT(procCode) = cntP) t
					ON d.caseID = t.caseID
					GROUP BY d.caseID
					HAVING COUNT(d.diagCode) = cntD)
	GROUP BY dischargeStatus;
ELSE 
    SELECT dischargeStatus, COUNT(caseID) AS `count`
	FROM HospitalCase
	WHERE caseID IN (SELECT d.caseID
					FROM Diagnosed d
					INNER JOIN (SELECT h.caseID
								FROM HospitalCase h
								LEFT OUTER JOIN Treated t
								ON h.caseID = t.caseID
								WHERE t.caseID IS NULL
								GROUP BY h.caseID) t
					ON d.caseID = t.caseID
					GROUP BY d.caseID
					HAVING COUNT(d.diagCode) = cntD)
	GROUP BY dischargeStatus;
END IF;
END