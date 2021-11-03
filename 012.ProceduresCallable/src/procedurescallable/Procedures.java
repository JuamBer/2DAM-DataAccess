package procedurescallable;

import java.sql.SQLException;
import java.sql.Statement;


public class Procedures {
    
    /**Create all procedures of the database*/
    public static void createProcedures(Statement stt) throws SQLException {
        String sql;

        System.out.println("Creating Procedures...");

        System.out.println(" ...Creating Procedure: set_new_department");
        sql = "CREATE PROCEDURE set_new_department(IN id INT, IN name VARCHAR(15), IN office VARCHAR(15)) "
                + "MODIFIES SQL DATA "
                + "BEGIN ATOMIC "
                + " INSERT INTO departments VALUES id,name,office; "
                + "END; ";
        stt.executeUpdate(sql);

        System.out.println(" ...Creating Procedure: set_new_teacher");
        sql = "CREATE PROCEDURE set_new_teacher(IN id INT, IN name VARCHAR(15), IN surname VARCHAR(15), IN email VARCHAR(15), IN start_date DATE, IN id_department INT, IN salary INT) "
                + "MODIFIES SQL DATA "
                + "BEGIN ATOMIC "
                + " INSERT INTO teachers VALUES id,name,surname,email,start_date,id_department,salary; "
                + "END; ";
        stt.executeUpdate(sql);

        System.out.println(" ...Creating Procedure: set_salary");
        sql = "CREATE PROCEDURE set_salary(IN salar INT) "
                + "MODIFIES SQL DATA "
                + "BEGIN ATOMIC "
                + " UPDATE teachers SET teachers.salary=salar; "
                + "END; ";
        stt.executeUpdate(sql);

        System.out.println(" ...Creating Procedure: rise_salary_prct");
        sql = "CREATE PROCEDURE rise_salary_prct(IN p FLOAT) "
                + "MODIFIES SQL DATA "
                + "BEGIN ATOMIC "
                + " DECLARE OP FLOAT; "
                + " SET OP = p/100; "
                + " UPDATE teachers SET salary=salary+(salary*OP); "
                + "END; ";
        stt.executeUpdate(sql);

        System.out.println(" ...Creating Procedure: rise_salary_per_dept");
        sql = "CREATE PROCEDURE rise_salary_per_dept(p FLOAT, dept_nam VARCHAR(15)) "
                + "MODIFIES SQL DATA "
                + "BEGIN ATOMIC "
                + " DECLARE OP INT; "
                + " SET OP = p/10; "
                + " UPDATE teachers SET salary=salary+(salary*OP) WHERE teachers.dept_num LIKE dept_nam; "
                + "END; ";
        stt.executeUpdate(sql);

        System.out.println(" ...Creating Procedure: get_newest_teacher");
        sql = "CREATE PROCEDURE get_newest_teacher(OUT res VARCHAR(15)) "
                + "MODIFIES SQL DATA "
                + "BEGIN ATOMIC "
                + " DECLARE QUERYDATE DATE; "
                + " DECLARE QUERYNAME VARCHAR(15); "
                + " SET QUERYDATE = SELECT MAX(start_date) FROM teachers;"
                + " SET QUERYNAME = SELECT name FROM teachers WHERE start_date=QUERYDATE LIMIT 1;"
                + " SET res = QUERYNAME;"
                + "END; ";
        stt.executeUpdate(sql);

        System.out.println(" ...Creating Procedure: count_teachers");
        sql = "CREATE PROCEDURE count_teachers(IN dept_nam VARCHAR(15), OUT res INT) "
                + "READS SQL DATA "
                + "BEGIN ATOMIC "
                + " DECLARE NUMTEACHERS INT; "
                + " SET NUMTEACHERS = SELECT COUNT(name) FROM teachers WHERE teachers.dept_num=dept_nam;"
                + " SET res = NUMTEACHERS; "
                + "END; ";
        stt.executeUpdate(sql);

    }
    
    
    /**Drop all procedures of the database */
    public static void dropProcedures(Statement stt) throws SQLException {
        String sql;

        System.out.println("Droping Procedures...");

        sql = "DROP PROCEDURE set_new_department IF EXISTS";
        stt.executeUpdate(sql);
        sql = "DROP PROCEDURE set_new_teacher IF EXISTS";
        stt.executeUpdate(sql);
        sql = "DROP PROCEDURE set_salary IF EXISTS";
        stt.executeUpdate(sql);
        sql = "DROP PROCEDURE rise_salary_prct IF EXISTS";
        stt.executeUpdate(sql);
        sql = "DROP PROCEDURE rise_salary_per_dept IF EXISTS";
        stt.executeUpdate(sql);
        sql = "DROP PROCEDURE get_newest_teacher IF EXISTS";
        stt.executeUpdate(sql);
        sql = "DROP PROCEDURE count_teachers IF EXISTS";
        stt.executeUpdate(sql);
    }
}
