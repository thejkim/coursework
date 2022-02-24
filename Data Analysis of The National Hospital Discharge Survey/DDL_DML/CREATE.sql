## CIS-495
## Spring 2019
## Final Project
## Member: Jo Eun Kim, Jinho Kang

##############################################################
## FIRST, CREATE SCHEMA "HospitalDischarge" with UTF-16 General CI ##
##############################################################

USE HospitalDischarge;

# CREATE raw_data TABLE 
CREATE TABLE IF NOT EXISTS SurveyRawData (
	ID INT NOT NULL AUTO_INCREMENT,
    data VARCHAR(150),
    PRIMARY KEY (ID)
) ENGINE=InnoDB;

# CREATE Decoding Value Table
CREATE TABLE IF NOT EXISTS SurveyDataItem (
	itemNumber INT NOT NULL,
    description VARCHAR(255),
    PRIMARY KEY (ItemNumber)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS SurveyDataCode (
	itemNumber INT,
    code INT,
    startLocation INT,
    length INT,
    value VARCHAR(45),
    CONSTRAINT PK PRIMARY KEY (ItemNumber, Code)
) ENGINE=InnoDB;

# CREATE Hospital
CREATE TABLE IF NOT EXISTS Hospital (
	hospitalID INT NOT NULL AUTO_INCREMENT,
    geoRegion TINYINT(1),
    beds TINYINT(1),
    ownership TINYINT(1),
    PRIMARY KEY (HospitalID)
) ENGINE=InnoDB;

# CREATE HospitalCase
CREATE TABLE IF NOT EXISTS HospitalCase (
	caseID	INT NOT NULL AUTO_INCREMENT,
	hospitalID	INT NOT NULL,
	surveyYear	YEAR(4),
	dischargeMonth	TINYINT(2),
	dischargeStatus	TINYINT(1) NOT NULL,
	daysOfCare	SMALLINT(4),
	stayLength_f	TINYINT(1),
	analysisWeight	MEDIUMINT(5) NOT NULL,
	DRG	SMALLINT(3),
	admissionType	TINYINT(1),
	admissionSrc	TINYINT(2),
	admitDiagCode	VARCHAR(5),
    PRIMARY KEY (caseID)
) ENGINE=InnoDB;

# CREATE Patient
CREATE TABLE IF NOT EXISTS Patient (
	caseID					INT NOT NULL,
	newbornStatus	TINYINT(1),
	ageUnit				TINYINT(1) NOT NULL,
	age						TINYINT(2) NOT NULL,
	sex						TINYINT(1) NOT NULL,
	race						TINYINT(1),
	maritalStatus		TINYINT(1),
    PRIMARY KEY (caseID)
) ENGINE=InnoDB;

# CREATE Diagnosed
CREATE TABLE IF NOT EXISTS Diagnosed (
	caseID			INT NOT NULL,
	sequence	INT NOT NULL,
	diagCode		VARCHAR(5) NOT NULL,
    CONSTRAINT PK_DIAGNOSED PRIMARY KEY (caseID, sequence, diagCode)
) ENGINE=InnoDB;

# CREATE Diagnosis
CREATE TABLE IF NOT EXISTS Diagnosis (
	diagCode		VARCHAR(5) NOT NULL,
	description	VARCHAR(255),
    PRIMARY KEY (diagCode)
) ENGINE=InnoDB;

# CREATE Treated
CREATE TABLE IF NOT EXISTS Treated (
	caseID			INT NOT NULL,
	sequence	INT NOT NULL,
	procCode	VARCHAR(4) NOT NULL,
    CONSTRAINT PK_DIAGNOSED PRIMARY KEY (caseID, sequence, procCode)
) ENGINE=InnoDB;

# CREATE Procedure
CREATE TABLE IF NOT EXISTS `Procedure` (
	procCode	VARCHAR(4) NOT NULL,
	description	VARCHAR(255),
    PRIMARY KEY (procCode)
) ENGINE=InnoDB;

# CREATE Payment
CREATE TABLE IF NOT EXISTS Payment (
	caseID				INT NOT NULL,
	paymentType	TINYINT(1) NOT NULL,
	expectedSrc	TINYINT(2) NOT NULL,
	CONSTRAINT PK_PAYMENT PRIMARY KEY (caseID, paymentType)
) ENGINE=InnoDB;









