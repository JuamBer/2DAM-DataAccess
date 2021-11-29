CREATE DATABASE act4_1;
USE act4_1;
CREATE TABLE departments (
  dept_num INT NOT NULL PRIMARY KEY,
  name VARCHAR(20),
  office VARCHAR(20)
);
CREATE TABLE teachers(
  id INT PRIMARY KEY,
  name VARCHAR(15),
  surname VARCHAR(40),
  email VARCHAR(50),
  start_date DATE,
  dept_num INT,
  salary INT,
  FOREIGN KEY (dept_num) REFERENCES departments(dept_num)
);
INSERT INTO
  departments
VALUES
  (10, 'INFORMATICA', 'DESPA6');
INSERT INTO
  departments
VALUES
  (20, 'COMERCIO', 'DESPA7');
INSERT INTO
  departments
VALUES
  (30, 'ADMINISTRATIVO', 'DESPA8');
INSERT INTO
  departments
VALUES
  (40, 'FOL', 'DESPA5');
INSERT INTO
  teachers
VALUES
  (
    1,
    'Luz',
    'Martinez',
    'luz.martinez@iesabastos.org',
    '1990-01-01',
    10,
    1500
  );
INSERT INTO
  teachers
VALUES
  (
    2,
    'Cristina',
    'Ausina',
    'c.ausina@iesabastos.org',
    '1990-02-01',
    10,
    1200
  );
INSERT INTO
  teachers
VALUES
  (
    3,
    'Imma',
    'Cabanes',
    'i.cabanes@iesabastos.org',
    '2000-03-01',
    10,
    NULL
  );
INSERT INTO
  teachers
VALUES
  (
    4,
    'Mercedes',
    'Sánchez',
    'm.sanchez@iesabastos.org',
    NULL,
    40,
    1000
  );