CREATE DEFINER=`joeun`@`50.75.222.202` PROCEDURE `p_averageDaysOfStayByRegion`(IN region INT, OUT `avg` DOUBLE)
BEGIN
	SELECT y.estimatedDaysOfCare/x.estimatedDischarge AS AvgLengthOfStay
    INTO `avg`
	FROM (SELECT SUM(analysisWeight) AS estimatedDischarge
		FROM HospitalCase hc JOIN Hospital h ON hc.hospitalID = h.hospitalID 
		WHERE h.geoRegion = region) x,
		(SELECT SUM(analysisWeight*daysOfCare) AS estimatedDaysOfCare
		FROM HospitalCase hc JOIN Hospital h ON hc.hospitalID = h.hospitalID 
		WHERE h.geoRegion = region) y;
END