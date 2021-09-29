CONNECT  'jdbc:derby:C:\Users\juamb\Documents\Estudios\Data Access\005. Derby\db; create=true';

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
    Dept_num INT,

    FOREIGN KEY (Dept_num) REFERENCES Departments (Dept_num) 
);

INSERT INTO Departments VALUES (10,'INFORMÁTICA','DESPA6');
INSERT INTO Departments VALUES (20,'COMERCIO','DESPA7');
INSERT INTO Departments VALUES (30,'ADMINISTRATIVO','DESPA8');
INSERT INTO Departments VALUES (40,'FOL','DESPA5');

INSERT INTO Teachers VALUES (1,'Luz','Martinez','luz.martinez@iesabastos.org','01/01/1990',10);
INSERT INTO Teachers VALUES (2,'Cristina','Ausina','c.ausina@iesabastos.org','01/02/1990',10);
INSERT INTO Teachers VALUES (3,'Imma','Cabanes','i.cabanes@iesabastos.org','01/03/1990',10);
INSERT INTO Teachers VALUES (4,'Mercedes','Sánchez','m.sanchez@iesabastos.org',NULL,40);


--Show the teachers (name, surname and email) that started 20 or more years ago.
--SELECT Name, Surname, Email FROM Teachers WHERE Start_date < (GETDATE()-20);
SELECT Name, Surname, Email FROM Teachers WHERE Start_date < '09/29/2001';

--Show all teachers in the office DESPA6.
SELECT T.Name FROM Teachers T INNER JOIN Departments D ON D.Dept_num = T.Dept_num WHERE D.Office = 'DESPA6';

--For all teachers of the department of FOL , update their start date to 01/09/2000.
UPDATE Teachers  SET Start_date='01/09/2000' WHERE Dept_num=40;
--SELECT * FROM Teachers;