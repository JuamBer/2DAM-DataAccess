CREATE DATABASE ejemplojuan;
USE ejemplojuan;

CREATE TABLE departamentos (
 dept_NO INT NOT NULL PRIMARY KEY,
 dnombre VARCHAR (15),
 loc VARCHAR (15));
 
CREATE TABLE empleados (
Emp_no INT NOT NULL PRIMARY KEY,
apellido VARCHAR ( 20) ,
oficio VARCHAR ( 15) ,
dir INT,
fecha_alta DATE,
salario FLOAT (6,2),
comision FLOAT(6,2),
dept_NO INT,
FOREIGN KEY(dept_NO) REFERENCES departamentos(dept_NO));

INSERT INTO departamentos VALUES (1, 'Ventas', 'VALENCIA');
INSERT INTO departamentos VALUES (2, 'Administracion', 'MADRID');
INSERT INTO departamentos VALUES (3, 'Ingenieria', 'BARCELONA');
INSERT INTO departamentos VALUES (4, 'Fabricacion', 'BARCELONA');


INSERT INTO empleados VALUES (1, 'Garcia', 'Comercial', 1, '2019-01-01', 1200, 20, 1);
INSERT INTO empleados VALUES (2, 'Martinez', 'Comercial', 1, '2020-01-01', 1800, 15, 1);
INSERT INTO empleados VALUES (3, 'Torres', 'Tecnico Com', 1, '2020-01-01', 1800, 15, 1);
INSERT INTO empleados VALUES (4, 'Pérez', 'Administrativo', 1, '2019-02-01', 1300, 0, 2);
INSERT INTO empleados VALUES (5, 'López', 'Ing Jefe', 1, '2019-01-01', 2200, 5, 3);
INSERT INTO empleados VALUES (6, 'Sánchez', 'Ingeniero', 1, '2019-01-01', 1800, 5, 3);