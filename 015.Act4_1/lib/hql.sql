--01. All the information of each department.
FROM
  Departments

--02. The name of the departments.
SELECT
  name
FROM
  Departments

--03. The name and office of the departments.
SELECT
  name,
  office
FROM
  Departments

--04. The name and surname of each teacher and the name of the department that belongs to.
SELECT
  name,
  surname,
  departments.name
FROM
  Teachers

--05. The teachers of the department of Informatica.
FROM
  Teachers
WHERE
  departments.name = 'INFORMATICA'

--06. The teachers that started after 01 / 01 / 2020.
FROM
  Teachers
WHERE
  start_date > '2020-01-01'

--07. The number of departments.
SELECT
  COUNT(*)
FROM
  Departments

--08. The number of teachers in each department (only departments with teachers).
SELECT
  departments.name,
  COUNT(*)
FROM
  Teachers
GROUP BY
  departments

--09. The departments without teachers.
FROM 
    Departments D
WHERE
    D.deptNum NOT IN (
        SELECT
            T.departments.deptNum
        FROM
            Teachers T
    )

--10. Departments that do not have an employee named INMA.
FROM
  Departments D
WHERE
    D.deptNum NOT IN (
        SELECT
            T.departments.deptNum
        FROM
            Teachers T
        WHERE
            name LIKE 'IMMA'
    )

--11. The average salary of each department.
SELECT
    T.departments.name,
    AVG(T.salary)
FROM
    Teachers T
GROUP BY
    T.departments