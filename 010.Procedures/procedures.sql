CREATE PROCEDURE changeoffice(offi VARCHAR(20))
    modifies sql data 
    UPDATE departments SET office = offi
.;
CALL changeoffice1('PROCEDIMIENTO1');

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