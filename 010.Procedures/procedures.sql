--java -cp sqltool.jar;hsqldb.jar org.hsqldb.cmdline.SqlTool --inlineRc url=jdbc:hsqldb:file:CC:\Users\7JDAM\Documents\DataAccess-2DAM\005.HSQLDB\db

CREATE TABLE Departments (
    Dept_num INT PRIMARY KEY,
    Name VARCHAR(20),
    Office VARCHAR(20)
);

CREATE TABLE Teachers (
    id INT PRIMARY KEY,
    Name VARCHAR(15),
    Surname VARCHAR(40),
    Email VARCHAR(50),
    Start_date DATE,
    Dept_num INT REFERENCES Departments (Dept_num) ,
);

INSERT INTO Departments VALUES (10,'INFORMÁTICA','DESPA6');
INSERT INTO Departments VALUES (20,'COMERCIO','DESPA7');
INSERT INTO Departments VALUES (30,'ADMINISTRATIVO','DESPA8');
INSERT INTO Departments VALUES (40,'FOL','DESPA5');

INSERT INTO Teachers VALUES (1,'Luz','Martinez','luz.martinez@iesabastos.org','1990-01-01',10);
INSERT INTO Teachers VALUES (2,'Cristina','Ausina','c.ausina@iesabastos.org','1990-02-01',10);
INSERT INTO Teachers VALUES (3,'Imma','Cabanes','i.cabanes@iesabastos.org','1990-01-03',10);
INSERT INTO Teachers VALUES (4,'Mercedes','Sánchez','m.sanchez@iesabastos.org',NULL,40);

CREATE PROCEDURE changeoffice(offi VARCHAR(20))
    modifies sql data 
    UPDATE departments SET office = offi
.;
CALL changeoffice('PROCEDIMIENTO1');

/*
DEPT_NUM  NAME            OFFICE
--------  --------------  --------------
      10  INFORMµTICA     PROCEDIMIENTO1
      20  COMERCIO        PROCEDIMIENTO1
      30  ADMINISTRATIVO  PROCEDIMIENTO1
      40  FOL             PROCEDIMIENTO1
*/

CREATE PROCEDURE changeoffice2(offi VARCHAR(20))
    modifies sql data 
    UPDATE departments SET office = offi WHERE name = 'INFORMÁTICA'
.;

CALL changeoffice2('PROCEDIMIENTO2');

/*
DEPT_NUM  NAME            OFFICE
--------  --------------  --------------
      10  INFORMµTICA     PRECEDIMIENTO2
      20  COMERCIO        PROCEDIMIENTO1
      30  ADMINISTRATIVO  PROCEDIMIENTO1
      40  FOL             PROCEDIMIENTO1
*/

CREATE PROCEDURE changeoffice3(pattern VARCHAR(20), offi VARCHAR(20))
    modifies sql data 
    UPDATE departments SET office = offi WHERE name LIKE pattern
.;

CALL changeoffice3('%O','PROCEDIMIENTO3');

/*
DEPT_NUM  NAME            OFFICE
--------  --------------  --------------
      10  INFORMµTICA     PRECEDIMIENTO2
      20  COMERCIO        PROCEDIMIENTO3
      30  ADMINISTRATIVO  PROCEDIMIENTO3
      40  FOL             PROCEDIMIENTO1
*/