--Obtain the name and average salary of each department
SELECT
  E.departamentos.dnombre,
  avg(E.salario)
FROM
  Empleados E
GROUP BY
  E.departamentos

--Obtain the name and average salary of each department that has an average salary higher than 1500 
SELECT
  E.departamentos.dnombre,
  avg(E.salario)
FROM
  Empleados E
GROUP BY
  E.departamentos
HAVING
  avg(E.salario) > 1500

--Obtain all the employees whose salary is higher than the average salary of the company, ordered by salary

FROM Empleados 
--Obtain all the employees whose salary is higher than the average salary of their department


--1. Get a list of the surname and the city of work of each employee
SELECT
  apellido,
  departamentos.loc
FROM
  Empleados

--2. Get the most veteran employee
SELECT
  id,
  apellido
FROM
  Empleados
WHERE
  fechaAlta <= ALL(SELECT fechaAlta FROM Empleados)

--3. Get the most veteran of each department
FROM
  Empleados E
WHERE
  fechaAlta <= ALL(SELECT fechaAlta FROM Empleados E1 WHERE E.departamentos.id = E1.departamentos.id)

--4. Get the number of departments that are in each different city (property loc)
SELECT  
    COUNT(D.id)
FROM
    Departamentos D
GROUP BY 
    D.loc

--5. Get the total number of employees
SELECT
    COUNT(id)
FROM
    Empleados

--6. Get the number of employees in each department
SELECT
    COUNT(id)
FROM
    Empleados E
GROUP BY
    E.departamentos

--7. Get the salary range of the company (max salary – min salary)
SELECT
    MAX(salario)-MIN(salario)
FROM
    Empleados

--8. Get the departments with no employees
SELECT
  D.dnombre
FROM
  Departamentos D
WHERE
    D.id NOT IN (SELECT E.departamentos.id FROM Empleados E)

--9. Get the department name of all departments that have an employee called ‘Garcia’
SELECT
  D.dnombre
FROM
  Departamentos D
WHERE
    D.id IN (SELECT E.departamentos.id FROM Empleados E WHERE E.apellido = 'Garcia')


--10. Get the department name of all departments that DO NOT have an employee called ‘Garcia’
FROM Departamentos D WHERE D.id NOT IN (SELECT E.departamentos.id FROM Empleados E WHERE E.apellido = 'Garcia')