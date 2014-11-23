# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: localhost (MySQL 5.6.21)
# Database: Academy
# Generation Time: 2014-10-29 02:42:24 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

use 226_db;
# Dump of table Enrollment
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Enrollment`;

CREATE TABLE `Enrollment` (
  `studentID` int(11) NOT NULL,
  `subjectID` varchar(45) NOT NULL,
  `Grade` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`studentID`,`subjectID`),
  KEY `fk_Enrollment_1_idx` (`subjectID`),
  CONSTRAINT `enrollment_ibfk_1` FOREIGN KEY (`studentID`) REFERENCES `Student` (`studentID`),
  CONSTRAINT `enrollment_ibfk_2` FOREIGN KEY (`subjectID`) REFERENCES `Subject` (`subjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Enrollment` WRITE;
/*!40000 ALTER TABLE `Enrollment` DISABLE KEYS */;

INSERT INTO `Enrollment` (`studentID`, `subjectID`, `Grade`)
VALUES
	(9966111,'CMPE180-90','B'),
	(9966111,'CMPE180-92','B'),
	(9966111,'cmpe206','IPG'),
	(9966222,'CMPE180-92','C'),
	(9966222,'CMPE180-94','C'),
	(9966333,'CMPE180-90','B'),
	(9966333,'CMPE206','F');

/*!40000 ALTER TABLE `Enrollment` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Prerequisite
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Prerequisite`;

CREATE TABLE `Prerequisite` (
  `subjectID` varchar(50) NOT NULL,
  `prereqID` varchar(50) NOT NULL,
  PRIMARY KEY (`subjectID`,`prereqID`),
  CONSTRAINT `prerequisite_ibfk_1` FOREIGN KEY (`subjectID`) REFERENCES `adademy_1`.`Subject` (`subjectID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Prerequisite` WRITE;
/*!40000 ALTER TABLE `Prerequisite` DISABLE KEYS */;

INSERT INTO `Prerequisite` (`subjectID`, `prereqID`)
VALUES
	('CMPE200','CMPE180-90'),
	('CMPE202','CMPE180-92'),
	('CMPE202','CMPE180-94'),
	('CMPE208','CMPE206'),
	('CMPE209','CMPE206'),
	('CMPE220','CMPE180-92'),
	('CMPE220','CMPE180-94'),
	('CMPE226','CMPE272'),
	('CMPE240','CMPE180-90');

/*!40000 ALTER TABLE `Prerequisite` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Student
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Student`;

CREATE TABLE `Student` (
  `studentID` int(11) NOT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `stream` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `password` varchar(45) NOT NULL DEFAULT '',
  PRIMARY KEY (`studentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Student` WRITE;
/*!40000 ALTER TABLE `Student` DISABLE KEYS */;

INSERT INTO `Student` (`studentID`, `gender`, `stream`, `name`, `password`)
VALUES
	(9966111,'M','SE','Student1','pw_9966111'),
	(9966222,'F','CMPE','Student2','pw_9966222'),
	(9966333,'M','SE','Student3','pw_9966333');

/*!40000 ALTER TABLE `Student` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Subject
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Subject`;

CREATE TABLE `Subject` (
  `subjectID` varchar(50) NOT NULL,
  `subjectName` varchar(45) NOT NULL,
  `capacity` int(11) DEFAULT NULL,
  PRIMARY KEY (`subjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Subject` WRITE;
/*!40000 ALTER TABLE `Subject` DISABLE KEYS */;

INSERT INTO `Subject` (`subjectID`, `subjectName`, `capacity`)
VALUES
	('CMPE180-90','DIGITAL DESIGN',120),
	('CMPE180-92','OBJECT ORIENTED PROGRAMMING',120),
	('CMPE180-94','OPERATING SYSTEM',120),
	('CMPE180-96','DATABASE SYSTEM',120),
	('CMPE200','COMPUTER ARCHITECTURE',70),
	('CMPE202','SOFTWARE SYSTEMS ENGINEERING',70),
	('CMPE206','COMPUTER NETWORK DESIGN',70),
	('CMPE208','NETWORK ARCHITECTURE AND PROTOCOL',70),
	('CMPE209','NETWORK SECURITY',70),
	('CMPE220','SYSTEM SOFTWARE',70),
	('CMPE226','DATABASE SYSTEM DESIGN',70),
	('CMPE240','ADVANCED COMPUTER DESIGN',70),
	('CMPE272','ENTERPRISE SOFTWARE PLATFORMS',120);

/*!40000 ALTER TABLE `Subject` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Teacher
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Teacher`;

CREATE TABLE `Teacher` (
  `Tid` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`Tid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Teacher` WRITE;
/*!40000 ALTER TABLE `Teacher` DISABLE KEYS */;

INSERT INTO `Teacher` (`Tid`, `name`)
VALUES
	(1001,'Hungwen Li'),
	(1002,'Frank Lin'),
	(1003,'Rod Fatoohi'),
	(1004,'Michael Robinson');

/*!40000 ALTER TABLE `Teacher` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Teaches
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Teaches`;

CREATE TABLE `Teaches` (
  `tID` int(11) NOT NULL,
  `subjectID` varchar(50) NOT NULL,
  `deptName` varchar(45) NOT NULL,
  PRIMARY KEY (`tID`,`subjectID`),
  KEY `fk_Teaches_1_idx` (`subjectID`),
  CONSTRAINT `teaches_ibfk_1` FOREIGN KEY (`tID`) REFERENCES `adademy_1`.`Teacher` (`Tid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `teaches_ibfk_2` FOREIGN KEY (`subjectID`) REFERENCES `adademy_1`.`Subject` (`subjectID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Teaches` WRITE;
/*!40000 ALTER TABLE `Teaches` DISABLE KEYS */;

INSERT INTO `Teaches` (`tID`, `subjectID`, `deptName`)
VALUES
	(1001,'CMPE226','SE'),
	(1002,'CMPE180-94','CMPE'),
	(1003,'CMPE206','CMPE'),
	(1004,'CMPE200','CMPE');

/*!40000 ALTER TABLE `Teaches` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
