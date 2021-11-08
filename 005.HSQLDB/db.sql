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

--1. Show the teachers of the department of FOL.
SELECT Name FROM Teachers WHERE Dept_num = 40;

--2. Show all teachers in the office DESPA6.
SELECT T.Name FROM Teachers T INNER JOIN Departments D ON D.Dept_num = T.Dept_num WHERE D.Office = 'DESPA6';

--3. Add a new column to the table Teachers, called Salary
ALTER TABLE Teachers ADD Salary INT;
--SELECT * FROM Teachers;