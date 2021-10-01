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


--All teachers from INFORMATICA department.
SELECT *  FROM Teachers WHERE Dept_num = 10; 

--For each department, obtain all its data and the number of teachers it has.
SELECT  D.Dept_num, D.Name, D.Office, COUNT(T.id) AS Num_Teachers FROM Departments D INNER JOIN Teachers T ON T.Dept_num = D.Dept_num GROUP BY D.Dept_num, D.Name, D.Office; 

--Show the name and surname of each teacher and their department, ordered alphabetically by surname.

SELECT T.Name,T.Surname,D.Name FROM Teachers AS T INNER JOIN Departments AS D
ON T.Dept_num = D.Dept_num ORDER BY T.Surname ASC;