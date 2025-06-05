-- Create tables for Studio project: CTG 
PRAGMA foreign_keys = OFF;
drop table if exists LGA16;
drop table if exists LGA21;
drop table if exists LGAPopulation16;
drop table if exists LGAPopulation21;
drop table if exists State;
drop table if exists LGALTHC21;
drop table if exists LGASchoolCompletion16;
drop table if exists LGASchoolCompletion21;
drop table if exists LGANonSchoolCompletion16;
drop table if exists LGANonSchoolCompletion21;
drop table if exists Persona;
drop table if exists Student;
PRAGMA foreign_keys = ON;


CREATE TABLE State(
stateCode INTEGER NOT NULL,
stateName TEXT NOT NULL,
PRIMARY KEY (stateCode)
);

CREATE TABLE LGA16 (
LGACode INTEGER NOT NULL,
name TEXT NOT NULL,
stateCode INTEGER NOT NULL,
PRIMARY KEY (LGACode)
FOREIGN KEY (stateCode) REFERENCES State(stateCode)
);

CREATE TABLE LGA21 (
LGACode INTEGER NOT NULL,
name TEXT NOT NULL,
stateCode INTEGER NOT NULL,
PRIMARY KEY (LGACode)
FOREIGN KEY (stateCode) REFERENCES State(stateCode)
);

CREATE TABLE LGAPopulation16 (
LGACode INTEGER NOT NULL,
indigenousStatus TEXT NOT NULL,
sex CHAR (1) NOT NULL,
ageCategory TEXT NOT NULL,
count INTEGER NOT NULL,
ageMin INTEGER,
ageMax INTEGER,
PRIMARY KEY (LGACode, indigenousStatus, sex, ageCategory)
FOREIGN KEY (LGACode) REFERENCES LGA16(LGACode)
);
CREATE TABLE LGAPopulation21 (
LGACode INTEGER NOT NULL,
indigenousStatus TEXT NOT NULL,
sex CHAR (1) NOT NULL,
ageCategory TEXT NOT NULL,
count INTEGER NOT NULL,
ageMin INTEGER,
ageMax INTEGER,
PRIMARY KEY (LGACode, indigenousStatus, sex, ageCategory)
FOREIGN KEY (LGACode) REFERENCES LGA21(LGACode)
);
CREATE TABLE LGALTHC21 (
LGACode INTEGER NOT NULL,
indigenousStatus TEXT NOT NULL,
sex CHAR (1) NOT NULL,
condition TEXT NOT NULL,
count INTEGER NOT NULL,
PRIMARY KEY (LGACode, indigenousStatus, sex, condition)
FOREIGN KEY (LGACode) REFERENCES LGA21(LGACode)
);
CREATE TABLE LGASchoolCompletion16 (
LGACode INTEGER NOT NULL,
indigenousStatus TEXT NOT NULL,
sex CHAR (1) NOT NULL,
schoolYear TEXT NOT NULL,
count INTEGER NOT NULL,
PRIMARY KEY (LGACode, indigenousStatus, sex, schoolYear)
FOREIGN KEY (LGACode) REFERENCES LGA16(LGACode)
);

CREATE TABLE LGASchoolCompletion21 (
LGACode INTEGER NOT NULL,
indigenousStatus TEXT NOT NULL,
sex CHAR (1) NOT NULL,
schoolYear TEXT NOT NULL,
count INTEGER NOT NULL,
PRIMARY KEY (LGACode, indigenousStatus, sex, schoolYear)
FOREIGN KEY (LGACode) REFERENCES LGA21(LGACode)
);


CREATE TABLE LGANonSchoolCompletion16 (
LGACode INTEGER NOT NULL,
indigenousStatus TEXT NOT NULL,
sex CHAR (1) NOT NULL,
nonSchoolBracket TEXT NOT NULL,
count INTEGER NOT NULL,
PRIMARY KEY (LGACode, indigenousStatus, sex, nonSchoolBracket)
FOREIGN KEY (LGACode) REFERENCES LGA16(LGACode)
);

CREATE TABLE LGANonSchoolCompletion21 (
LGACode INTEGER NOT NULL,
indigenousStatus TEXT NOT NULL,
sex CHAR(1) NOT NULL,
nonSchoolBracket TEXT NOT NULL,
count INTEGER NOT NULL,
PRIMARY KEY (LGACode, indigenousStatus, sex, nonSchoolBracket)
FOREIGN KEY (LGACode) REFERENCES LGA21(LGACode)
);

CREATE TABLE Persona (
num INTEGER NOT NULL,
name TEXT NOT NULL,
userQuotes TEXT NOT NULL,
background TEXT NOT NULL,
needsAndGoals TEXT NOT NULL,
skills TEXT NOT NULL,
PRIMARY KEY (num)
);

CREATE TABLE Student (
ID TEXT NOT NULL,
name TEXT NOT NULL,
email TEXT NOT NULL,
PRIMARY KEY (ID)
);










